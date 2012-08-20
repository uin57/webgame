package com.pwrd.war.gameserver.pet.handler;


public class PetHandlerFactory {

	private static final PetMessageHandler handler = new PetMessageHandler();

	public static PetMessageHandler getHandler() {
		return handler;
	}
}
