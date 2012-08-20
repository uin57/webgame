package com.pwrd.war.gameserver.form.handler;


public class FormHandlerFactory {
	private static final FormHandler handler = new FormHandler();
	
	public static FormHandler getHandler() {
		return handler;
	}
}
