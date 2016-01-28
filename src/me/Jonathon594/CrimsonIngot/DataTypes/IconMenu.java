package me.Jonathon594.CrimsonIngot.DataTypes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class IconMenu implements Listener {

	public class OptionClickEvent {
		private final Player	player;
		private final int		position;
		private final String	name;
		private boolean			close;
		private boolean			destroy;

		public OptionClickEvent(final Player player, final int position, final String name) {
			this.player = player;
			this.position = position;
			this.name = name;
			close = true;
			destroy = false;
		}

		public String getName() {
			return name;
		}

		public Player getPlayer() {
			return player;
		}

		public int getPosition() {
			return position;
		}

		public void setWillClose(final boolean close) {
			this.close = close;
		}

		public void setWillDestroy(final boolean destroy) {
			this.destroy = destroy;
		}

		public boolean willClose() {
			return close;
		}

		public boolean willDestroy() {
			return destroy;
		}
	}

	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	private final String			name;
	private final int				size;

	private OptionClickEventHandler	handler;
	private Plugin					plugin;

	private String[]				optionNames;

	private ItemStack[]				optionIcons;

	public IconMenu(final String name, final int size, final OptionClickEventHandler handler, final Plugin plugin) {
		this.name = name;
		this.size = size;
		this.handler = handler;
		this.plugin = plugin;
		optionNames = new String[size];
		optionIcons = new ItemStack[size];
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
		plugin = null;
		optionNames = null;
		optionIcons = null;
	}

	public int getSize() {
		return size;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClick(final InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			event.setCancelled(true);
			final int slot = event.getRawSlot();
			if (slot >= 0 && slot < size && optionNames[slot] != null) {
				final Plugin plugin = this.plugin;
				final OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot,
						optionNames[slot]);
				handler.onOptionClick(e);
				if (e.willClose()) {
					final Player p = (Player) event.getWhoClicked();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							p.closeInventory();
						}
					}, 1);
				}
				if (e.willDestroy()) destroy();
			}
		}
	}

	public void open(final Player player) {
		final Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++)
			if (optionIcons[i] != null) inventory.setItem(i, optionIcons[i]);
		player.openInventory(inventory);
	}

	private ItemStack setItemNameAndLore(final ItemStack item, final String name, final ArrayList<String> lore) {
		final ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}

	public IconMenu setOption(final int position, final ItemStack icon, final String name,
			final ArrayList<String> lore) {
		optionNames[position] = name;
		optionIcons[position] = setItemNameAndLore(icon, name, lore);
		return this;
	}

}