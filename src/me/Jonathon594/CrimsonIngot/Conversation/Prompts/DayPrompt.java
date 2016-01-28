package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;

public class DayPrompt extends NumericPrompt {

	@Override
	protected Prompt acceptValidatedInput(final ConversationContext context, final Number n) {
		final CrimsonIngot plugin = (CrimsonIngot) context.getPlugin();
		final int maxDay = plugin.getTimeManager().getMonths().get((int) context.getSessionData("BirthMonth") - 1)
				.getLength();
		if ((int) n > maxDay || (int) n < 1) return new DayPrompt();
		context.setSessionData("BirthDay", n);
		return new AgePrompt();
	}

	@Override
	public String getPromptText(final ConversationContext arg0) {
		final CrimsonIngot plugin = (CrimsonIngot) arg0.getPlugin();
		final int maxDay = plugin.getTimeManager().getMonths().get((int) arg0.getSessionData("BirthMonth") - 1)
				.getLength();
		return "What is your birth day? (1 - " + maxDay + ")";
	}

}
