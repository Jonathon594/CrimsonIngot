package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;

public class NameInUsePrompt extends MessagePrompt {

	@Override
	protected Prompt getNextPrompt(final ConversationContext arg0) {
		return new FirstNamePrompt();
	}

	@Override
	public String getPromptText(final ConversationContext arg0) {
		return CrimsonIngotConstants.nameInUse;
	}

}
