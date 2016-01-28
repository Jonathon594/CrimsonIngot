package me.Jonathon594.CrimsonIngot.Conversation;

import org.bukkit.conversations.ConversationFactory;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Conversation.Prompts.FirstNamePrompt;

public class ConversationManager {
	private final ConversationFactory profileFactory;

	public ConversationManager(final CrimsonIngot plugin) {
		profileFactory = new ConversationFactory(plugin).withModality(true).withPrefix(new ProfileConversationPrefix())
				.withFirstPrompt(new FirstNamePrompt()).withEscapeSequence("/quit").withTimeout(10)
				.thatExcludesNonPlayersWithMessage("Go away evil console!");
	}

	public ConversationFactory getProfileFactory() {
		return profileFactory;
	}
}
