package me.Jonathon594.CrimsonIngot.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.DataTypes.IconMenu;
import me.Jonathon594.CrimsonIngot.DataTypes.Knowledge;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class KnowledgeCommand {

	private static ChatColor	mc	= CrimsonIngotConstants.mainColor;
	private static ChatColor	cc	= CrimsonIngotConstants.contColor;

	private static IconMenu		perkIconMenu;

	private static void InitPerkMenu(final CrimsonIngot plugin) {
		int size = 0;
		for (final Knowledge kn : plugin.getObjectManager().getCrimsonKnowledge())
			size = Math.max(kn.getMenuY(), size);

		perkIconMenu = new IconMenu("Knowledge List", size * 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(final IconMenu.OptionClickEvent event) {
				final String chosenName = ChatColor.stripColor(event.getName());
				final Knowledge tp = plugin.getObjectManager().getKnowledge(chosenName);
				if (tp == null) return;

				LearnPerk(tp, event.getPlayer(), plugin);
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, plugin);
	}

	private static void LearnPerk(final Knowledge tp, final Player player, final CrimsonIngot plugin) {
		if (tp == null) return;

		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(player.getUniqueId());
		if (mp == null) return;

		if (mp.getPlayerKnowledge().contains(tp)) {
			CrimsonIngotUtil.sendCrimsonMessage(player, mc + CrimsonIngotConstants.alreadyPerk);
			return;
		}

		final List<String> perkNames = new ArrayList<String>();
		for (final Knowledge perk : mp.getPlayerKnowledge())
			perkNames.add(perk.getName());

		if (tp.getRequiredAttribute() != null && !perkNames.contains(tp.getRequiredAttribute())) {
			CrimsonIngotUtil.sendCrimsonMessage(player, mc + CrimsonIngotConstants.noReqKnowledge);
			return;
		}

		final int cost = tp.getCost();

		if (mp.getPlayer().getLevel() < cost) {
			player.sendMessage(mc + CrimsonIngotConstants.noLevels);
			return;
		}

		player.setLevel(player.getLevel() - cost);
		mp.addAttribute(tp);
		mp.applyAllEffects();
		CrimsonIngotUtil.sendCrimsonMessage(player,
				mc + "You have learned a new Knowledge! (" + cc + tp.getName() + mc + ")");
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

		for (int i = 0; i < plugin.getObjectManager().getCrimsonKnowledge().size(); i++) {
			final Knowledge tp = plugin.getObjectManager().getCrimsonKnowledge().get(i);
			final Knowledge rp = plugin.getObjectManager().getKnowledge(tp.getRequiredAttribute());

			ChatColor itemColor = ChatColor.WHITE;
			if (mp.getPlayerKnowledge().contains(tp)) itemColor = ChatColor.GREEN;
			else if (rp == null || mp.getPlayerKnowledge().contains(rp)) itemColor = ChatColor.BLUE;
			else
				itemColor = ChatColor.RED;

			final ItemStack menuIcon = new ItemStack(tp.getMenuIcon());
			String requiredPerk = "None";
			if (tp.getRequiredAttribute() != null) requiredPerk = tp.getRequiredAttribute();

			final ArrayList<String> lore = new ArrayList<String>();
			for (final String s : CrimsonIngotUtil.wordWrap(tp.getDescription()))
				lore.add(mc + s);
			lore.add(mc + "Cost: " + cc + tp.getCost() + " Levels");
			lore.add(mc + "Requires: " + cc + requiredPerk);
			if (tp.getCraftable().size() > 0) {
				lore.add(mc + "Craftable: ");
				for (final Material m : tp.getCraftable())
					lore.add(cc + m.name());
			}
			if (tp.getPlaceable().size() > 0) {
				lore.add(mc + "Placeable: ");
				for (final Material m : tp.getPlaceable())
					lore.add(cc + m.name());
			}
			if (tp.getMinable().size() > 0) {
				lore.add(mc + "Breakable: ");
				for (final Material m : tp.getMinable())
					lore.add(cc + m.name());
			}

			final int position = (tp.getMenuY() - 1) * 9 + tp.getMenuX() - 1;

			perkIconMenu.setOption(position, menuIcon, itemColor + tp.getName(), lore);
		}

		perkIconMenu.open(player);
	}
}
