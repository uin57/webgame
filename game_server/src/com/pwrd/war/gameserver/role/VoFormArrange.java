package com.pwrd.war.gameserver.role;

import org.apache.commons.lang.StringUtils;


public class VoFormArrange {

	private String id;
	private boolean buser;//True为用户id，false为武将id
	
	public VoFormArrange(){}
	public VoFormArrange(String id, boolean buser){
		this.id = id;
		this.buser = buser;
	}
	public boolean isEmpty(){
		return StringUtils.isEmpty(id);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isBuser() {
		return buser;
	}

	public void setBuser(boolean buser) {
		this.buser = buser;
	}

//	public static void main(String[] args){
//		List<VoFormArrange> list = new ArrayList<VoFormArrange>();
//		for(int i = 0;i<5;i++){
//			VoFormArrange v = new VoFormArrange();
//			v.setId("");
//			v.setBuser(false);
//			list.add(v);
//		}
//		String s = Utils.listToJSON(list);
//		System.out.println(s);
//		JSONArray arr = Utils.str2JSONArray(s);
//		List<VoFormArrange> res = new ArrayList<VoFormArrange>();
//		for(int i = 0; i< arr.length(); i++){
//			VoFormArrange vo = new VoFormArrange();
//			JSONObject t = arr.optJSONObject(i);
//			vo.setId(t.optString("id"));
//			vo.setBuser(t.optBoolean("buser"));
//			res.add(vo);
//			System.out.println(vo.isBuser());
//		}
//		System.out.println(res);
//		
//	}
}
