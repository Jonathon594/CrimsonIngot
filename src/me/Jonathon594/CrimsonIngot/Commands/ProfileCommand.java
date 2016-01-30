package me.Jonathon594.CrimsonIngot.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.Creed;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonClass;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class ProfileCommand {

	private static ChatColor	mc	= CrimsonIngotConstants.mainColor;
	private static ChatColor	cc	= CrimsonIngotConstants.contColor;

	private static String getClassString(final CrimsonPlayer mp) {
		String classString = "";
		for (final CrimsonClass c : mp.getPlayerClasses())
			classString += c.getName() + " ";
		return classString;
	}

	private static String getCreedString(final CrimsonPlayer mp) {
		String creedString = "";
		for (final Creed c : mp.getPlayerCreeds())
			creedString += c.getName() + " ";
		return creedString;
	}

	public static void perform(final CommandSender sender, final Command cmd, final String label, final String[] args,
			final CrimsonIngot plugin) {

		if (args.length > 1) {
			if (args[1].equalsIgnoreCase("set")) if (sender instanceof Player) {
				final Player p = (Player) sender;
				final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
				if (mp.getProfile().isMade()) {
					CrimsonIngotUtil.sendCrimsonMessage(sender, CrimsonIngotConstants.alreadyProfile);
					return;
				}
				plugin.getConversationManager().getProfileFactory().buildConversation((Conversable) sender).begin();
			}
			if (args[1].equalsIgnoreCase("show")) {
				if (args.length > 2) {
					final Player target = Bukkit.getPlayer(args[2]);
					if (target == null) {
						CrimsonIngotUtil.sendCrimsonMessage(sender, CrimsonIngotConstants.noPlayer);
						return;
					}
					ShowProfile(sender, plugin, target);
					return;
				}
				if (sender instanceof Player) {
					ShowProfile(sender, plugin, (Player) sender);
					return;
				}
				CrimsonIngotUtil.sendCrimsonMessage(sender, CrimsonIngotConstants.console);
			}
		} else
			sendHelpText(sender);
	}

	public static void sendHelpText(final CommandSender sender) {
		final ArrayList<String> lines = new ArrayList<String>();
		lines.add(mc + "------[" + cc + "Profile Help" + mc + "]------");
		lines.add(mc + "/cm profile show " + cc + "See your own profile.");
		lines.add(mc + "/cm profile show [username] " + cc + "See someone elses profile.");
		lines.add(mc + "/cm profile set " + cc + "Set your profile.");
		CrimsonIngotUtil.sendListAsPluginMessage(lines, sender);
	}

	public static void ShowProfile(final CommandSender sender, final CrimsonIngot plugin, final Player t) {
		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(t.getUniqueId());
		final String fullName = mp.getProfile().getFullName();
		final ArrayList<String> lines = new ArrayList<String>();
		lines.add(mc + "------[Profile for " + cc + t.getDisplayName() + mc + "]------");
		lines.add(mc + "Name: " + cc + fullName);
		lines.add(mc + "Creed: " + cc + getCreedString(mp));
		lines.add(mc + "Class: " + cc + getClassString(mp));
		lines.add(mc + "Birdthday: " + cc + mp.getProfile().getBirthDay().getLongDateString());
		lines.add(cc + mp.getProfile().getBirthDay().getSimpleDateString());
		lines.add(mc + "Age: " + cc + mp.getProfile().getAge());
		CrimsonIngotUtil.sendListAsPluginMessage(lines, sender);
	}
}
