/**
 * 
 */
package com.pwrd.war.gameserver.promptButton.handler;


/**
 * @author dendgan
 *
 */
public class PromptButtonHandlerFactory {

private static final PromptButtonHandler promptButtonHandler = new PromptButtonHandler();
	
	public static PromptButtonHandler getHandler(){
		return promptButtonHandler;
	}
}
