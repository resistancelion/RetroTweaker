/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc147.chat;

import minetweaker.api.chat.IChatMessage;

/**
 *
 * @author Stan
 */
public class MCChatMessage implements IChatMessage {
	private final String data;
	
	public MCChatMessage(String message) {
		data = message;
	}
	
	@Override
	public IChatMessage add(IChatMessage other) {
		return new MCChatMessage(data + other.getInternal());
	}

	@Override
	public Object getInternal() {
		return data;
	}
}
