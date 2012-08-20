package com.pwrd.war.gameserver.scene;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.util.JdomUtils;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO.SceneType;

public class LoadSceneServiceImpl implements LoadSceneService {
	/** key为地图id的所有地图 **/
	private static Map<String, SceneInfoVO> sceneIdAll = new HashMap<String, SceneInfoVO>();

	private Logger log = Loggers.mapLogger;

	public LoadSceneServiceImpl(String fullPath) {
		log.info("开始加载地图信息..." + fullPath);

		File dir = new File(fullPath);
		if (dir.exists() && dir.isDirectory()) {
			File[] subs = dir.listFiles();
			for (File file : subs) {
				if (file.isFile()) {
					log.info("发现文件：" + file.getName());
					// 地图文件
					String sceneId = file.getName();
					sceneId = sceneId.replace(".xml", "");
					if (!isNumeric(sceneId)) {
						continue;
					}
					SceneInfoVO scene = new SceneInfoVO();
					scene.setSceneId(sceneId);

					sceneIdAll.put(sceneId, scene);

					Element root = JdomUtils.getRootElemet(file
							.getAbsolutePath());
					// 类型
					// String stype = root.getAttributeValue("type");
					String stype = sceneId.substring(0, 1);
					int type = Integer.valueOf(stype);
					SceneType sceneType = SceneType.getByType(type);
					scene.setSceneType(sceneType);
					scene.setStartX(Integer.valueOf(root
							.getAttributeValue("defaultpos_x")));
					scene.setStartY(Integer.valueOf(root
							.getAttributeValue("defaultpos_y")));
					// 处理lib文件
					String lib = root.getAttributeValue("lib");
					lib = lib.replace("maps", "");// 多余的前缀
					log.info(lib);
					// 内部配置文件
					File libFile = new File(fullPath + lib);
					// 处理切换点
					List<Element> children = root.getChildren("layer");
					for (Element e : children) {
						// 切换点在11层
						if ("11".equals(e.getAttributeValue("l"))) {
							List<Element> items = e.getChildren();// 1的所有item
							for (Element item : items) {
								String url = item.getAttributeValue("url");
								Element libE = this.getItemFromLibByUrl(
										libFile, url);
								String tp = libE.getAttributeValue("tp");
								if (StringUtils.isEmpty(tp)) {
									// blocks，阻挡点
									String block = libE
											.getAttributeValue("block");
									log.info(block);
									if (!StringUtils.isEmpty(block)) {
										String[] blocks = block.split(";");
										for (String b : blocks) {
											String[] bd = b.split(",");
											SceneInfoVO.Block bvo = new SceneInfoVO.Block();
											bvo.x = Integer.valueOf(bd[0]);
											bvo.y = Integer.valueOf(bd[1]);
											bvo.type = Integer.valueOf(bd[2]);
											scene.addBlock(bvo);
										}
									}
								} else if (StringUtils.isEquals(tp, "switch")
										|| StringUtils.isEquals(tp,
												"repEntrance")) {
									// ext,获取切换目标
									if (!StringUtils.isEmpty(item
											.getAttributeValue("ext"))) {
										// 获取该点的下一个地图
										String ext = item
												.getAttributeValue("ext");
										 
										int x = Integer.valueOf(item
												.getAttributeValue("x"));
										int y = Integer.valueOf(item
												.getAttributeValue("y"));
										SceneInfoVO.SwitchInfo s = new SceneInfoVO.SwitchInfo();
										s.x = x;
										s.y = y;
										s.id = item.getAttributeValue("id");
										s.nextId = ext.split(",")[1];// 0是名字
										s.nextSceneId = s.nextId
												.substring(0, 3) + "000";// 去尾部1加0
										if(ext.split(",").length == 4){
											s.toX = Integer.valueOf(ext.split(",")[2]);
											s.toY = Integer.valueOf(ext.split(",")[3]);
										}
										scene.getNextMapId().put(s.id, s);
										log.info("发现切换点：" + s);
									}
								}

							}
						}
					}

				}
			}
		}

	}

	private Element getItemFromLibByUrl(File libFile, String url) {
		if (libFile.isFile() && libFile.exists()) {
			Element root = JdomUtils.getRootElemet(libFile.getAbsolutePath());
			log.info(root.getName());
			List<Element> list = root.getChildren();
			for (Element c : list) {
				List<Element> c1s = c.getChildren();
				for (Element c1 : c1s) {
					if (url.equals(c1.getAttributeValue("url"))) {
						return c1;
					}
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, SceneInfoVO> getAllScenes() {
		return sceneIdAll;
	}

	public SceneInfoVO getSceneById(String sceneId) {
		return sceneIdAll.get(sceneId);
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// // String path =
		// LoadSceneServiceImpl.class.getClassLoader().getResource("/").getPath();
		// System.out.println(LoadSceneServiceImpl.class.getClassLoader().getResource("."));
		// LoadSceneServiceImpl impl = new
		// LoadSceneServiceImpl("D:\\work\\svn\\sanguo\\code\\server\\trunk\\resources\\maps");
	}
}
