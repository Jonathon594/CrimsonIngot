package me.Jonathon594.CrimsonIngot.Util;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

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

	public static String makeNumberString(final String number) {
		final char[] chars = number.toCharArray();
		String num = Character.toString(chars[chars.length - 1]);
		String leading = "";
		if (chars.length > 1) leading = number.substring(0, number.length() - 1);

		switch (num) {
			case "0":
				num = "0th";
				break;
			case "1":
				num = "1st";
				break;
			case "2":
				num = "2nd";
				break;
			case "3":
				num = "3rd";
				break;
			case "4":
				num = "4th";
				break;
			case "5":
				num = "5th";
				break;
			case "6":
				num = "6th";
				break;
			case "7":
				num = "7th";
				break;
			case "8":
				num = "8th";
				break;
			case "9":
				num = "9th";
				break;
		}

		return leading + num;
	}

	public static int RountToMultiple(final double i, final int v) {
		return (int) (Math.ceil(i / v) * v);
	}

	public static void sendCrimsonMessage(final CommandSender sender, final String s) {
		sender.sendMessage(CrimsonIngotConstants.messagePrefix + s);
	}

	public static void sendListAsPluginMessage(final ArrayList<String> lines, final CommandSender sender) {
		for (final String s : lines)
			sender.sendMessage(CrimsonIngotConstants.messagePrefix + s);
	}

	public static String[] wordWrap(final String s) {
		if (s == null) return new String[0];
		return s.split("(?<=\\G.{60})");
	}

	public static int WrapInt(int kX, final int kLowerBound, final int kUpperBound) {
		final int range_size = kUpperBound - kLowerBound + 1;

		if (kX < kLowerBound) kX += range_size * ((kLowerBound - kX) / range_size + 1);

		return kLowerBound + (kX - kLowerBound) % range_size;
	}

	public static void logMessage(String s, Logger log) {
		log.info(CrimsonIngotConstants.messagePrefix + s);
	}
}
