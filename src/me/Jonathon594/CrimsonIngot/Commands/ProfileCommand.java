package me.Jonathon594.CrimsonIngot.Commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class ProfileCommand {

	private static ChatColor	mc	= CrimsonIngotConstants.mainColor;
	private static ChatColor	cc	= CrimsonIngotConstants.contColor;

	public static boolean perform(final CommandSender sender, final Command cmd, final String label,
			final String[] args, final CrimsonIngot plugin) {
		if (sender instanceof Player) if (args.length > 0) {
			final Player p = (Player) sender;
			if (args[1].equalsIgnoreCase("set")) {
				final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
				if (mp.getProfile().isMade()) return false;
				plugin.getConversationManager().getProfileFactory().buildConversation((Conversable) sender).begin();
				return true;
			}
			if (args[1].equalsIgnoreCase("show")) ShowProfile((Player) sender, plugin);
		}
		return false;
	}

	public static void ShowProfile(final Player p, final CrimsonIngot plugin) {
		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
		final String fullName = mp.getProfile().getFullName();
		final ArrayList<String> lines = new ArrayList<String>();
		lines.add(mc + "Name: " + cc + fullName);
		lines.add(mc + "Birdthday: " + cc + mp.getProfile().getBirthDay().GetDateString());
		lines.add(mc + "Age: " + cc + mp.getProfile().getAge());
		CrimsonIngotUtil.sendListAsPluginMessage(lines, p);
	}
}
