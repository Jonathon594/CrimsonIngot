package me.Jonathon594.CrimsonIngot.Util;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrimsonIngotUtil {
	public static boolean canParse(final String input) {
		boolean parsable = true;
		try {
			Integer.parseInt(input);
		} catch (final NumberFormatException e) {
			parsable = false;
		}
		return parsable;
	}

	public static int RountToMultiple(final double i, final int v) {
		return (int) (Math.ceil(i / v) * v);
	}

	public static void sendListAsPluginMessage(final ArrayList<String> lines, final CommandSender sender) {
		for (final String s : lines)
			sender.sendMessage(CrimsonIngotConstants.messagePrefix + s);
	}

	public static void sendMythriaMessage(final Player p, final String s) {
		p.sendMessage(CrimsonIngotConstants.messagePrefix + s);
	}

	public static int WrapInt(int kX, final int kLowerBound, final int kUpperBound) {
		final int range_size = kUpperBound - kLowerBound + 1;

		if (kX < kLowerBound) kX += range_size * ((kLowerBound - kX) / range_size + 1);

		return kLowerBound + (kX - kLowerBound) % range_size;
	}
}
