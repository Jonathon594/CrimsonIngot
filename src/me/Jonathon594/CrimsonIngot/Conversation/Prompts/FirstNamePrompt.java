package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class FirstNamePrompt extends StringPrompt {

	@Override
	public Prompt acceptInput(final ConversationContext context, final String s) {
		if (s.length() > 3) {
			context.setSessionData("FirstName", s);
			return new MiddleNamePrompt();
		}
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(final ConversationContext context) {
		return "What is your first name?";
	}

}
