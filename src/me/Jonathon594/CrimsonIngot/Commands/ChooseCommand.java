package me.Jonathon594.CrimsonIngot.Commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.Creed;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonClass;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.DataTypes.IconMenu;
import me.Jonathon594.CrimsonIngot.DataTypes.PlayerAttribute;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class ChooseCommand {

	private static ChatColor	mc	= CrimsonIngotConstants.mainColor;
	private static IconMenu		perkIconMenu;

	private static ArrayList<PlayerAttribute> getAttributes(final CrimsonIngot plugin) {
		final ArrayList<PlayerAttribute> attributes = new ArrayList<PlayerAttribute>();
		attributes.addAll(plugin.getObjectManager().getCrimsonClasses());
		attributes.addAll(plugin.getObjectManager().getCrimsonCreeds());

		return attributes;
	}

	private static void giveAttribute(final PlayerAttribute tp, final Player player, final CrimsonIngot plugin) {
		if (tp == null) return;

		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(player.getUniqueId());
		if (mp == null) return;

		if (mp.getPlayerAttributes().contains(tp)) {
			if (tp instanceof CrimsonClass) player.sendMessage(mc + CrimsonIngotConstants.alreadyClass);
			if (tp instanceof CrimsonClass) player.sendMessage(mc + CrimsonIngotConstants.alreadyCreed);
			return;
		}

		if (tp instanceof CrimsonClass && mp.hasClass()) return;

		if (tp instanceof Creed && mp.hasCreed()) return;

		mp.addAttribute(tp);
		mp.applyAllEffects();
	}

	private static void InitPerkMenu(final CrimsonIngot plugin) {
		int size = 0;
		final ArrayList<PlayerAttribute> attributes = getAttributes(plugin);
		for (final PlayerAttribute pa : attributes)
			size = Math.max(pa.getMenuY(), size);

		perkIconMenu = new IconMenu("Class / Creed List", size * 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(final IconMenu.OptionClickEvent event) {
				final String chosenName = ChatColor.stripColor(event.getName());
				final PlayerAttribute tp = plugin.getObjectManager().getAttribute(chosenName);
				if (tp == null) return;

				giveAttribute(tp, event.getPlayer(), plugin);
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, plugin);
	}

	public static boolean perform(final CommandSender sender, final Command cmd, final String label,
			final String[] args, final CrimsonIngot plugin) {
		if (sender instanceof Player) {
			ShowPerkMenu((Player) sender, plugin);
			return true;
		}
		return false;
	}

	private static void ShowPerkMenu(final Player player, final CrimsonIngot plugin) {
		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(player.getUniqueId());
		if (mp == null) return;

		InitPerkMenu(plugin);

		for (int i = 0; i < getAttributes(plugin).size(); i++) {
			final PlayerAttribute tp = getAttributes(plugin).get(i);

			ChatColor itemColor = ChatColor.WHITE;
			if (mp.getPlayerAttributes().contains(tp)) itemColor = ChatColor.GREEN;
			else if (tp instanceof CrimsonClass && mp.hasClass() || tp instanceof Creed && mp.hasCreed())
				itemColor = ChatColor.RED;
			else
				itemColor = ChatColor.BLUE;

			final ItemStack menuIcon = new ItemStack(tp.getMenuIcon());

			final ArrayList<String> lore = new ArrayList<String>();
			for (final String s : CrimsonIngotUtil.wordWrap(tp.getDescription()))
				lore.add(mc + s);

			final int position = (tp.getMenuY() - 1) * 9 + tp.getMenuX() - 1;

			perkIconMenu.setOption(position, menuIcon, itemColor + tp.getName(), lore);
		}

		perkIconMenu.open(player);
	}
}
