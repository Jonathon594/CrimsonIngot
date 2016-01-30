package me.Jonathon594.CrimsonIngot.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.DataTypes.PlayerAttribute;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;
import me.Jonathon594.CrimsonIngot.Util.Permissions;

public class PlayerCommand {
	private static boolean modPlayerAttribute(final CommandSender sender, final String[] args,
			final CrimsonIngot plugin, final boolean add) {
		final Player target = Bukkit.getPlayer(args[2]);
		if (target == null) return false;
		final PlayerAttribute attribute = plugin.getObjectManager().getAttribute(args[3]);
		if (attribute == null) return false;
		final CrimsonPlayer cp = plugin.getCrimsonPlayerManager().getPlayerByUUID(target.getUniqueId());
		if (cp == null) return false;
		if (add) {
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
		return true;
	}

	private static boolean modPlayerTitle(final CommandSender sender, final String[] args, final CrimsonIngot plugin,
			final String title) {
		final Player target = Bukkit.getPlayer(args[2]);
		if (target == null) return false;
		final CrimsonPlayer cp = plugin.getCrimsonPlayerManager().getPlayerByUUID(target.getUniqueId());
		if (cp == null) return false;
		cp.getProfile().setTitle(title);

		if (title != "") {
			CrimsonIngotUtil.sendCrimsonMessage(sender,
					"Player " + target.getName() + "'s title has been set to " + title);
			return true;
		} else {
			CrimsonIngotUtil.sendCrimsonMessage(sender, "Player " + target.getName() + "'s title has been reset");
			return true;
		}
	}

	public static boolean perform(final CommandSender sender, final Command cmd, final String label,
			final String[] args, final CrimsonIngot plugin) {
		if (args.length > 1) switch (args[1].toLowerCase()) {
			case "add":
				if (Permissions.isAdmin(sender) || sender.isOp())
					if (args.length == 4) return modPlayerAttribute(sender, args, plugin, true);
				break;
			case "remove":
				if (Permissions.isAdmin(sender) || sender.isOp())
					if (args.length == 4) return modPlayerAttribute(sender, args, plugin, false);
				break;
			case "title":
				if (Permissions.isAdmin(sender) || sender.isOp()) {
					if (args.length == 4) return modPlayerTitle(sender, args, plugin, args[3]);
					if (args.length == 3) return modPlayerTitle(sender, args, plugin, "");
				}
				break;
		}

		return false;
	}
}
