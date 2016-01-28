package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.Managers.ProfileArchiveManager;

public class LastNamePrompt extends StringPrompt {

	@Override
	public Prompt acceptInput(final ConversationContext context, final String s) {
		if (s.length() > 3) context.setSessionData("LastName", s);
		final CrimsonIngot plugin = (CrimsonIngot) context.getPlugin();
		if (context.getForWhom() instanceof Player) {
			final Player p = (Player) context.getForWhom();
			final CrimsonPlayer mp = plugin.getMythriaPlayerManager().getPlayerByUUID(p.getUniqueId());
			final String fullName = context.getSessionData("FirstName") + " " + context.getSessionData("MiddleName")
					+ " " + context.getSessionData("LastName");

			if (ProfileArchiveManager.getProfile(plugin, fullName) != null) {
				mp.getProfile().reset();
				return new NameInUsePrompt();
			}
		}
		return new MonthPrompt();
	}

	@Override
	public String getPromptText(final ConversationContext context) {
		return "What is your last name?";
	}

}
