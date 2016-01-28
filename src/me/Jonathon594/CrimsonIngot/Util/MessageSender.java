package me.Jonathon594.CrimsonIngot.Util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageSender {
	private static ChatColor mainColor = ChatColor.DARK_PURPLE;

	public static void Send(final Player p, final List<String> lines) {
		for (final String l : lines)
			p.sendMessage(mainColor + l);
	}

	public static void SendList(final Player p, final List<String> items) {
		String message = "";
		for (final String s : items)
			message = message + s + ", ";
		p.sendMessage(message);
	}
}
