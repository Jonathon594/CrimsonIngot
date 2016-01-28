package me.Jonathon594.CrimsonIngot.Conversation.Prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;

public class ProfileFinishedPrompt extends MessagePrompt {

	@Override
	protected Prompt getNextPrompt(final ConversationContext context) {
		if (context.getPlugin() instanceof CrimsonIngot) {
			final CrimsonIngot plugin = (CrimsonIngot) context.getPlugin();
			if (context.getForWhom() instanceof Player) {
				final Player p = (Player) context.getForWhom();

				final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
				mp.getProfile().setFirstName((String) context.getSessionData("FirstName"));
				mp.getProfile().setMiddleName((String) context.getSessionData("MiddleName"));
				mp.getProfile().setLastName((String) context.getSessionData("LastName"));
				mp.getProfile().setProfileBirthday((int) context.getSessionData("Age"),
						(int) context.getSessionData("BirthMonth") - 1, (int) context.getSessionData("BirthDay"));
				mp.getProfile().setMade(true);
				mp.SaveData();
			}
		}
		return Prompt.END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(final ConversationContext arg0) {
		return "Your profile is complete.";
	}

}
