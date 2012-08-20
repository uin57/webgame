package com.pwrd.war.gameserver.fight.handler;


 
public class FightHandlerFactory {
	
	private static FightMessageHandler handler = new FightMessageHandler( );
	
	public static FightMessageHandler getHandler() {
		return handler;
	}
}
