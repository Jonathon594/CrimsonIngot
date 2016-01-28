package me.Jonathon594.CrimsonIngot.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonClass;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.DataTypes.PlayerAttribute;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;

public class SetCommand {
	private static ChatColor	mc	= CrimsonIngotConstants.mainColor;
	private static ChatColor	cc	= CrimsonIngotConstants.contColor;

	public static boolean performAdd(final CommandSender sender, final Command cmd, final String label,
			final String[] args, final CrimsonIngot plugin) {
		if(args.length > 1){
			switch(args[1].toLowerCase()){
				case "class":
					if(args.length == 4){
						return addPlayerClass(sender, args, plugin);
					}
			}
		}
		
		return false;
	}

	private static boolean addPlayerClass(CommandSender sender, String[] args, CrimsonIngot plugin) {
		Player target = Bukkit.getPlayer(args[2]);
		if(target == null)
			return false;
		
		CrimsonClass crimsonClass = plugin.getObjectManager().getCrimsonClass(args[3]);
		
		if(crimsonClass == null)
			return false;
		
		CrimsonPlayer cp = plugin.getCrimsonPlayerManager().getPlayerByUUID(target.getUniqueId());
		
		if(cp == null)
			return false;
		
		cp.addAttribute(crimsonClass);
		
		return true;
	}
}
