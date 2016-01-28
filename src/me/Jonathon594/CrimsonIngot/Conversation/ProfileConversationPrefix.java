package me.Jonathon594.CrimsonIngot.Conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;

import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;

public class ProfileConversationPrefix implements ConversationPrefix {

	@Override
	public String getPrefix(final ConversationContext arg0) {
		return CrimsonIngotConstants.mainColor + "[" + CrimsonIngotConstants.contColor + "Profile Manager"
				+ CrimsonIngotConstants.mainColor + "] ";
	}

}
