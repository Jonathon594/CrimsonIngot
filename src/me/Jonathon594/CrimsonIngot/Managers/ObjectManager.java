package me.Jonathon594.CrimsonIngot.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.Knowledge;

public class ObjectManager {
	private ArrayList<Knowledge>	mythriaKnowledge;
	private ArrayList<Material>		mythriaKnowledgeMaterials;
	private ArrayList<Material>		mythriaBreakableKnowledgeMaterials;
	private ArrayList<Material>		mythriaPlaceableKnowledgeMaterials;
	private final List<String>		blockedPermissions	= new ArrayList<String>();

	public void addBlockedPermissions(final String str) {
		blockedPermissions.add(str);
	}

	public List<String> getBlockedPermissions() {
		return blockedPermissions;
	}

	public Knowledge getKnowledge(final String name) {
		for (final Knowledge tp : mythriaKnowledge)
			if (tp.getName().equalsIgnoreCase(name)) return tp;
		return null;
	}

	public ArrayList<Material> getMythriaBreakableKnowledgeMaterials() {
		return mythriaBreakableKnowledgeMaterials;
	}

	public ArrayList<Knowledge> getMythriaKnowledge() {
		return mythriaKnowledge;
	}

	public ArrayList<Material> getMythriaKnowledgeMaterials() {
		return mythriaKnowledgeMaterials;
	}

	public ArrayList<Material> getMythriaPlaceableKnowledgeMaterials() {
		return mythriaPlaceableKnowledgeMaterials;
	}

	public void Initialize(final ConfigManager configManager, final CrimsonIngot plugin) {
		mythriaKnowledge = new ArrayList<Knowledge>();
		mythriaKnowledgeMaterials = new ArrayList<Material>();
		mythriaBreakableKnowledgeMaterials = new ArrayList<Material>();
		mythriaPlaceableKnowledgeMaterials = new ArrayList<Material>();

		final Set<String> tpks = configManager.getKnowledgeConfig().getConfig().getConfigurationSection("Knowledge")
				.getKeys(false);
		for (final String tp : tpks) {
			final Knowledge ntp = new Knowledge(tp);
			mythriaKnowledge.add(ntp);
			ntp.loadKnowledge(plugin, configManager.getKnowledgeConfig());
		}
	}
}
