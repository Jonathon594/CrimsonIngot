package me.Jonathon594.CrimsonIngot.DataTypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;

public class Knowledge extends PlayerAttribute {
	private final ArrayList<Material>	craftable	= new ArrayList<Material>();
	private final ArrayList<Material>	breakable	= new ArrayList<Material>();
	private final ArrayList<Material>	placeable	= new ArrayList<Material>();

	private final List<String>			flags		= new ArrayList<String>();

	public Knowledge(final String n) {
		super(n);
	}

	public ArrayList<Material> getCraftable() {
		return craftable;
	}

	public List<String> getFlags() {
		return flags;
	}

	public ArrayList<Material> getMinable() {
		return breakable;
	}

	public ArrayList<Material> getPlaceable() {
		return placeable;
	}

	public void loadKnowledge(final CrimsonIngot plugin, final ConfigAccessor ca) {
		loadData(plugin, "Knowledge", ca);

		final List<?> fls = ca.getConfig().getList("Knowledge." + getName() + ".Flags");
		if (fls != null) for (final Object f : fls) {
			final String flag = (String) f;
			flags.add(flag);
		}

		final List<String> crafts = ca.getConfig().getStringList("Knowledge." + getName() + ".Craftable");
		for (final Object c : crafts) {
			final String craft = (String) c;
			craftable.add(Material.getMaterial(craft));
			if (Material.getMaterial(craft) == null) Bukkit.broadcastMessage("NULL! " + craft);
		}
		final List<String> breaks = ca.getConfig().getStringList("Knowledge." + getName() + ".Breakable");
		for (final Object c : breaks) {
			final String br = (String) c;
			breakable.add(Material.getMaterial(br));
		}
		final List<String> places = ca.getConfig().getStringList("Knowledge." + getName() + ".Placeable");
		for (final Object p : places) {
			final String pl = (String) p;
			placeable.add(Material.getMaterial(pl));
		}
		plugin.getObjectManager().getCrimsonKnowledgeMaterials().addAll(craftable);
		plugin.getObjectManager().getCrimsonBreakableKnowledgeMaterials().addAll(breakable);
		plugin.getObjectManager().getCrimsonPlaceableKnowledgeMaterials().addAll(placeable);
	}
}
