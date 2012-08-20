package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class BattleLog extends BaseLogMessage{
       private int mapId;
       private String mapName;
       private long battleTime;
       private int battleResult;
       private int attackLoss;
       private int defenceLoss;
    
    public BattleLog() {    	
    }

    public BattleLog(
                   long logTime,
                   int regionId,
                   int serverId,
                   String accountId,
                   String accountName,
                   String charId,
                   String charName,
                   int level,
                   int allianceId,
                   int vipLevel,
                   int reason,
                   String sceneId,
                   int x,
                   int y,
                   String param,
			int mapId,			String mapName,			long battleTime,			int battleResult,			int attackLoss,			int defenceLoss            ) {
        super(MessageType.LOG_BATTLE_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.mapId =  mapId;
            this.mapName =  mapName;
            this.battleTime =  battleTime;
            this.battleResult =  battleResult;
            this.attackLoss =  attackLoss;
            this.defenceLoss =  defenceLoss;
    }

       public int getMapId() {
	       return mapId;
       }
       public String getMapName() {
	       return mapName;
       }
       public long getBattleTime() {
	       return battleTime;
       }
       public int getBattleResult() {
	       return battleResult;
       }
       public int getAttackLoss() {
	       return attackLoss;
       }
       public int getDefenceLoss() {
	       return defenceLoss;
       }
        
       public void setMapId(int mapId) {
	       this.mapId = mapId;
       }
       public void setMapName(String mapName) {
	       this.mapName = mapName;
       }
       public void setBattleTime(long battleTime) {
	       this.battleTime = battleTime;
       }
       public void setBattleResult(int battleResult) {
	       this.battleResult = battleResult;
       }
       public void setAttackLoss(int attackLoss) {
	       this.attackLoss = attackLoss;
       }
       public void setDefenceLoss(int defenceLoss) {
	       this.defenceLoss = defenceLoss;
       }
    
    @Override
    protected boolean readLogContent() {
	        mapId =  readInt();
	        mapName =  readString();
	        battleTime =  readLong();
	        battleResult =  readInt();
	        attackLoss =  readInt();
	        defenceLoss =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(mapId);
	        writeString(mapName);
	        writeLong(battleTime);
	        writeInt(battleResult);
	        writeInt(attackLoss);
	        writeInt(defenceLoss);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_BATTLE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_BATTLE_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.mapId));
		            list.add(String.valueOf(this.mapName));
		            list.add(String.valueOf(this.battleTime));
		            list.add(String.valueOf(this.battleResult));
		            list.add(String.valueOf(this.attackLoss));
		            list.add(String.valueOf(this.defenceLoss));
				return list;
	}
}