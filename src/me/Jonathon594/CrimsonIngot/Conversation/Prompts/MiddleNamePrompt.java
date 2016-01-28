package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class MiddleNamePrompt extends StringPrompt {

	@Override
	public Prompt acceptInput(final ConversationContext context, final String s) {
		if (s.length() > 3) {
			context.setSessionData("MiddleName", s);
			return new LastNamePrompt();
		}
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(final ConversationContext context) {
		return "What is your middle name?";
	}

}
