package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;

public class MonthPrompt extends NumericPrompt {

	@Override
	protected Prompt acceptValidatedInput(final ConversationContext context, final Number n) {
		final CrimsonIngot plugin = (CrimsonIngot) context.getPlugin();
		final int maxMonth = plugin.getTimeManager().getMonths().size();
		if ((int) n > maxMonth || (int) n < 1) return new MonthPrompt();
		context.setSessionData("BirthMonth", n);
		return new DayPrompt();
	}

	@Override
	public String getPromptText(final ConversationContext arg0) {
		final CrimsonIngot plugin = (CrimsonIngot) arg0.getPlugin();
		final int maxMonth = plugin.getTimeManager().getMonths().size();
		return "What is your birth month? (1 - " + maxMonth + ")";
	}

}
