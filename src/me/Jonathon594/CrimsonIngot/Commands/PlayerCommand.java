package me.Jonathon594.CrimsonIngot.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.DataTypes.PlayerAttribute;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;
import me.Jonathon594.CrimsonIngot.Util.Permissions;

public class PlayerCommand {
	private static ChatColor	mc	= CrimsonIngotConstants.mainColor;
	private static ChatColor	cc	= CrimsonIngotConstants.contColor;

	private static void modPlayerAttribute(final CommandSender sender, final String[] args, final CrimsonIngot plugin,
			final boolean add) {
		final Player target = Bukkit.getPlayer(args[2]);
		if (target == null) return;
		final PlayerAttribute attribute = plugin.getObjectManager().getAttribute(args[3]);
		if (attribute == null) return;
		final CrimsonPlayer cp = plugin.getCrimsonPlayerManager().getPlayerByUUID(target.getUniqueId());
		if (cp == null) return;
		if (add) {
			if (attribute.getRequiredAttribute() != null) {
				final PlayerAttribute ra = plugin.getObjectManager().getAttribute(attribute.getRequiredAttribute());
				if (ra != null) if (!cp.getPlayerAttributes().contains(ra)) {
					CrimsonIngotUtil.sendCrimsonMessage(sender, "That player cannot learn that Attribute.");
					return;
				}
			}
			cp.addAttribute(attribute);
			cp.applyAllEffects();
			CrimsonIngotUtil.sendCrimsonMessage(sender,
					"Attribute " + attribute.getName() + " has been added to " + target.getDisplayName());
		} else {
			cp.removeAttribute(attribute);
			cp.applyAllEffects();
			CrimsonIngotUtil.sendCrimsonMessage(sender,
					"Attribute " + attribute.getName() + " has been removed from " + target.getDisplayName());
		}
	}

	private static void modPlayerTitle(final CommandSender sender, final String[] args, final CrimsonIngot plugin,
			final String title) {

		final Player target = Bukkit.getPlayer(args[2]);
		if (target == null) return;
		final CrimsonPlayer cp = plugin.getCrimsonPlayerManager().getPlayerByUUID(target.getUniqueId());
		if (cp == null) return;
		cp.getProfile().setTitle(title);

		if (title != "") CrimsonIngotUtil.sendCrimsonMessage(sender,
				"Player " + target.getName() + "'s title has been set to " + title);
		else
			CrimsonIngotUtil.sendCrimsonMessage(sender, "Player " + target.getName() + "'s title has been reset");
	}

	public static boolean perform(final CommandSender sender, final Command cmd, final String label,
			final String[] args, final CrimsonIngot plugin) {
		if (Permissions.isAdmin(sender) || sender.isOp()) {
			if (args.length > 1) switch (args[1].toLowerCase()) {
				case "add":
					if (args.length == 4) modPlayerAttribute(sender, args, plugin, true);
					break;
				case "remove":
					if (args.length == 4) modPlayerAttribute(sender, args, plugin, false);
					break;
				case "title":
					if (args.length == 4) modPlayerTitle(sender, args, plugin, args[3]);
					if (args.length == 3) modPlayerTitle(sender, args, plugin, "");
					break;
			}
			else
				sendHelpText(sender);
		} else
			CrimsonIngotUtil.sendCrimsonMessage(sender, CrimsonIngotConstants.noPerm);

		return false;
	}

	private static void sendHelpText(final CommandSender sender) {
		final ArrayList<String> lines = new ArrayList<String>();
		lines.add(mc + "------[" + cc + "Player Help" + mc + "]------");
		lines.add(mc + "/cm player add [username] [attribute] " + cc + "Add an Attribute to a Player.");
		lines.add(mc + "/cm player remove [username] [attribute] " + cc + "Remove an Attribute from a Player.");
		lines.add(mc + "/cm player title [username] [title] " + cc + "Set a Player's Title");
		lines.add(mc + "/cm player title [username] " + cc + "Reset a Player's Title");
		CrimsonIngotUtil.sendListAsPluginMessage(lines, sender);
	}
}
