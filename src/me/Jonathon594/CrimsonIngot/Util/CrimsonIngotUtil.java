package me.Jonathon594.CrimsonIngot.Util;

import java.util.ArrayList;
import java.util.logging.Logger;

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

	public static void logMessage(final String s, final Logger log) {
		log.info(CrimsonIngotConstants.messagePrefix + s);
	}

	public static String makeNumberString(int number) {
		String numtext = Integer.toString(number);
		String num = "";
		final char[] chars = numtext.toCharArray();
		String leading = "";
		if(number>19){
			if (chars.length > 1) leading = numtext.substring(0, numtext.length() - 1);
		}

		switch (numtext) {
			case "0": num = "0th";break;
			case "1": num = "1st";break;
			case "2": num = "2nd";break;
			case "3": num = "3rd";break;
			case "4": num = "4th";break;
			case "5": num = "5th";break;
			case "6": num = "6th";break;
			case "7": num = "7th";break;
			case "8": num = "8th";break;
			case "10": num = "10th";break;
			case "11": num = "11th";break;
			case "12": num = "12th";break;
			case "13": num = "13th";break;
			case "14": num = "14th";break;
			case "15": num = "15th";break;
			case "16": num = "16th";break;
			case "17": num = "17th";break;
			case "18": num = "18th";break;
			case "19": num = "19th";break;
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
}
