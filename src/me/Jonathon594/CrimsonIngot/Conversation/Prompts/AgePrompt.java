package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class AgePrompt extends NumericPrompt {

	@Override
	protected Prompt acceptValidatedInput(final ConversationContext context, final Number n) {
		context.setSessionData("Age", n);
		return new ProfileFinishedPrompt();
	}

	@Override
	public String getPromptText(final ConversationContext arg0) {
		return "How old are you?";
	}

}
