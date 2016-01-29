package me.Jonathon594.CrimsonIngot.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.DataTypes.PlayerAttribute;

public class PlayerCommand {
	private static boolean modPlayerAttribute(final CommandSender sender, final String[] args,
			final CrimsonIngot plugin, final boolean add) {
		final Player target = Bukkit.getPlayer(args[2]);
		if (target == null) return false;
		final PlayerAttribute attribute = plugin.getObjectManager().getAttribute(args[3]);
		if (attribute == null) return false;
		final CrimsonPlayer cp = plugin.getCrimsonPlayerManager().getPlayerByUUID(target.getUniqueId());
		if (cp == null) return false;
		if (add) cp.addAttribute(attribute);
		else
			cp.removeAttribute(attribute);
		return true;
	}

	public static boolean perform(final CommandSender sender, final Command cmd, final String label,
			final String[] args, final CrimsonIngot plugin) {
		if (args.length > 1) switch (args[1].toLowerCase()) {
			case "add":
				if (args.length == 4) return modPlayerAttribute(sender, args, plugin, true);
				break;
			case "remove":
				if (args.length == 4) return modPlayerAttribute(sender, args, plugin, false);
				break;
		}

		return false;
	}
}
