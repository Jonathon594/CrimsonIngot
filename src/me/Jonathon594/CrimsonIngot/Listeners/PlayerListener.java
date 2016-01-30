package me.Jonathon594.CrimsonIngot.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.DataTypes.Knowledge;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;

public class PlayerListener implements Listener {
	CrimsonIngot plugin;

	public PlayerListener(final CrimsonIngot p) {
		plugin = p;
	}

	private void HandlePerkBreaking(final BlockBreakEvent event) {
		final Material blockType = event.getBlock().getType();
		final Player p = event.getPlayer();
		if (p == null) return;
		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
		if (mp == null) return;
		for (final Knowledge tp : mp.getPlayerKnowledge())
			if (tp.getMinable().contains(blockType)) return;
		if (!plugin.getObjectManager().getCrimsonBreakableKnowledgeMaterials().contains(blockType)) return;
		event.setCancelled(true);
		p.sendMessage(CrimsonIngotConstants.mainColor + CrimsonIngotConstants.cantBreak);
	}

	public void HandlePerkCrafting(final CraftItemEvent event) {
		final Material itemType = event.getRecipe().getResult().getType();
		if (!plugin.getObjectManager().getCrimsonAttributeMaterials().contains(itemType)) return;

		for (final HumanEntity he : event.getViewers()) {
			if (he instanceof Player == false) return;
			final Player p = (Player) he;
			final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
			if (mp == null) return;
			for (final Knowledge tp : mp.getPlayerKnowledge())
				if (tp.getCraftable().contains(itemType)) return;
			event.setCancelled(true);
			p.sendMessage(CrimsonIngotConstants.mainColor + CrimsonIngotConstants.cantCraft);
		}
	}

	public void HandlePerkPlacing(final BlockPlaceEvent event) {
		final Material blockType = event.getBlockPlaced().getType();
		final Player p = event.getPlayer();
		if (p == null) return;
		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
		if (mp == null) return;
		for (final Knowledge tp : mp.getPlayerKnowledge())
			if (tp.getPlaceable().contains(blockType)) return;
		if (!plugin.getObjectManager().getCrimsonPlaceableKnowledgeMaterials().contains(blockType)) return;
		event.setCancelled(true);
		p.sendMessage(CrimsonIngotConstants.mainColor + CrimsonIngotConstants.cantPlace);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.getPlayer().isOp()) return;
		HandlePerkBreaking(event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(final BlockPlaceEvent event) {
		if (event.getPlayer().isOp()) return;
		HandlePerkPlacing(event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCraft(final CraftItemEvent event) {
		HandlePerkCrafting(event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDisconnect(final PlayerQuitEvent event) {
		final Player p = event.getPlayer();
		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
		if (mp == null) return;
		mp.removeAllEffects();
		mp.SaveData();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnchant(final EnchantItemEvent event) {
		for (final HumanEntity he : event.getViewers())
			if (he instanceof Player) {
				final Player p = (Player) he;
				final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
				if (mp != null) {
					for (final Knowledge tp : mp.getPlayerKnowledge())
						if (tp.getFlags().contains("Enchanting")) return;
					event.setCancelled(true);
					p.sendMessage(CrimsonIngotConstants.mainColor + CrimsonIngotConstants.cantEnchant);
				}
			}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(final PlayerLoginEvent event) {
		final Player p = event.getPlayer();
		CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(p.getUniqueId());
		if (mp == null) {
			mp = plugin.getCrimsonPlayerManager().addPlayer(plugin, p.getUniqueId(), p);
			mp.LoadData();
			mp.SaveData();
		}

		mp.LoadData();
		mp.applyAllEffects();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerConsume(final PlayerItemConsumeEvent event) {
		if (event.getItem().getType() == Material.MILK_BUCKET) event.setCancelled(true);

		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(event.getPlayer().getUniqueId());
		if (mp == null) return;
		mp.applyAllEffects();
	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		final CrimsonPlayer mp = plugin.getCrimsonPlayerManager().getPlayerByUUID(player.getUniqueId());
		if (mp == null) return;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				mp.applyAllEffects();
			}
		}, 10);
	}
}
