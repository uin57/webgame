package com.pwrd.war.common.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 功能开关配置
 * @author jiliang.lu
 *
 */
public class FunctionSwitches {
	/** 开关列表 */
	private boolean[] switches = new boolean[FunctionSwitchType.values().length];
	
	public FunctionSwitches(){
		//注入默认值
		FunctionSwitchType[] _all = FunctionSwitchType.values();
		for(int i=0;i< switches.length;i++){
			switches[i] = _all[i].getDefaultState();
		}
	}

	public boolean isChargeEnabled(){
		return switches[FunctionSwitchType.CHARGE.getIndex()];
	}
	
	public void setChargeEnabled(boolean chargeEnabled){
		switches[FunctionSwitchType.CHARGE.getIndex()] = chargeEnabled;
	}
	public boolean isTestGiftEnabled(){
		return switches[FunctionSwitchType.TEST_GIFT.getIndex()];
	}
	public void setTestGiftEnabled(boolean enabled){
		switches[FunctionSwitchType.TEST_GIFT.getIndex()] = enabled;
	}
	/**
	 * 战报文件输出开关
	 * @return
	 */
	public boolean isBattleReportFileOutput() {
		return switches[FunctionSwitchType.BATTLE_REPORT_FILE_OUTPUT.getIndex()];
	}
	
	public void setBattleReportFileOutput(boolean reportOutput) {
		switches[FunctionSwitchType.BATTLE_REPORT_FILE_OUTPUT.getIndex()] = reportOutput;
	}
	
	
	/**
	 * 根据索引设置开关
	 * @param index
	 * @param Enabled
	 */
	public void setSwitchByIndex(int index,boolean enabled){
		if(index < switches.length){
			switches[index] = enabled;
		}
	}
	
	/**
	 * 指定索引的开关是否打开了
	 * @param index
	 * @return
	 */
	public boolean isSwitchEnabled(int index){
		rangeCheck(index);
		return switches[index];
	}
	
	/**
	 * 转换成map对象
	 * @return
	 */
	public Map<Integer,Boolean> toMap(){
		Map<Integer,Boolean> _map = new HashMap<Integer,Boolean>(switches.length);
		for(int i=0;i<switches.length;i++){
			_map.put(i, switches[i]);
		}
		return _map;
	}
	
	/**
	 * 从map对象进行构造
	 * @param switchMap
	 */
	public void fromMap(Map<Integer,Boolean> switchMap){
		for(Entry<Integer,Boolean> _entry : switchMap.entrySet()){
			this.setSwitchByIndex(_entry.getKey(), _entry.getValue());
		}
	}
	
	/**
	 * bool列表转换成位串
	 * @return
	 */
	public String toBitStr(){
		StringBuffer _buf = new StringBuffer();
		for(boolean _bit : switches){
			_buf.append(_bit?'1':'0');
		}
		return _buf.toString();
	}
	
	/**
	 * 位串转换成bool列表
	 * @param bitStr
	 */
	public void fromBitStr(String bitStr){
		for(int i=0;i<bitStr.length();i++){
			boolean _bit = bitStr.charAt(i) == '1' ? true : false;
			if(i < switches.length){
				switches[i] = _bit;
			}
		}
	}
	
	/**
	 * 检查下标范围
	 * @param index
	 */
	private void rangeCheck(int index){
		if(index < 0 || index >= FunctionSwitchType.values().length){
			throw new IllegalArgumentException("ServerMergeSwitch paramter error!");
		}
	}
}
