package me.Jonathon594.CrimsonIngot.Commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class CrimsonIngotCommandExecutor implements CommandExecutor {

	private final ChatColor		mc	= CrimsonIngotConstants.mainColor;
	private final ChatColor		cc	= CrimsonIngotConstants.contColor;

	private final CrimsonIngot	plugin;

	public CrimsonIngotCommandExecutor(final CrimsonIngot plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (args.length == 0) sendDefaultInfo(sender);

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("profile")) ProfileCommand.perform(sender, cmd, label, args, plugin);
			if (args[0].equalsIgnoreCase("knowledge")) KnowledgeCommand.perform(sender, cmd, label, args, plugin);
			if (args[0].equalsIgnoreCase("player")) PlayerCommand.perform(sender, cmd, label, args, plugin);
			if (args[0].equalsIgnoreCase("choose")) ChooseCommand.perform(sender, cmd, label, args, plugin);
		}
		return true;
	}

	private void sendDefaultInfo(final CommandSender sender) {
		final ArrayList<String> lines = new ArrayList<String>();
		lines.add(mc + "------[" + cc + "CrimsonIngot" + mc + "]------");
		lines.add(mc + "Welcome to CrimsonIngot");
		lines.add("The current date is: " + cc + plugin.getTimeManager().getCurrentDate().getLongDateString());
		lines.add(cc + plugin.getTimeManager().getCurrentDate().getSimpleDateString());
		lines.add(mc + "Commands:");
		if (sender.isOp()) lines.add(mc + "/cm player [add,remove] [pusername] [attribute]");
		lines.add(mc + "/cm profile " + cc + "[set, show [username]");
		lines.add(mc + "/cm knowledge");

		CrimsonIngotUtil.sendListAsPluginMessage(lines, sender);
	}

}
