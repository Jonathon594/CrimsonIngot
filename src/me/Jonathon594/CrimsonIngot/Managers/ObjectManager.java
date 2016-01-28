package me.Jonathon594.CrimsonIngot.Managers;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Material;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.Creed;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonClass;
import me.Jonathon594.CrimsonIngot.DataTypes.Knowledge;
import me.Jonathon594.CrimsonIngot.DataTypes.PlayerAttribute;

public class ObjectManager {
	private ArrayList<Knowledge>	crimsonKnowledge;
	private ArrayList<Creed>		crimsonCreeds;
	private ArrayList<CrimsonClass>	crimsonClasses;

	private ArrayList<Material>		crimsonKnowledgeMaterials;
	private ArrayList<Material>		crimsonBreakableKnowledgeMaterials;

	private ArrayList<Material>		crimsonPlaceableKnowledgeMaterials;

	public PlayerAttribute getAttribute(final String name) {
		for (final PlayerAttribute pa : getCrimsonAttributes())
			if (pa.getName().equalsIgnoreCase(name)) return pa;

		return null;
	}

	public ArrayList<PlayerAttribute> getCrimsonAttributes() {
		final ArrayList<PlayerAttribute> attributes = new ArrayList<PlayerAttribute>();
		attributes.addAll(crimsonKnowledge);
		attributes.addAll(crimsonCreeds);
		attributes.addAll(crimsonClasses);
		return attributes;
	}

	public ArrayList<Material> getCrimsonBreakableKnowledgeMaterials() {
		return crimsonBreakableKnowledgeMaterials;
	}

	public ArrayList<CrimsonClass> getCrimsonClasses() {
		return crimsonClasses;
	}

	public ArrayList<Creed> getCrimsonCreeds() {
		return crimsonCreeds;
	}

	public ArrayList<Knowledge> getCrimsonKnowledge() {
		return crimsonKnowledge;
	}

	public ArrayList<Material> getCrimsonKnowledgeMaterials() {
		return crimsonKnowledgeMaterials;
	}

	public ArrayList<Material> getCrimsonPlaceableKnowledgeMaterials() {
		return crimsonPlaceableKnowledgeMaterials;
	}

	public Knowledge getKnowledge(final String name) {
		for (final Knowledge tp : crimsonKnowledge)
			if (tp.getName().equalsIgnoreCase(name)) return tp;
		return null;
	}
	
	public Creed getCreed(final String name) {
		for (final Creed tp : crimsonCreeds)
			if (tp.getName().equalsIgnoreCase(name)) return tp;
		return null;
	}
	
	public CrimsonClass getCrimsonClass(final String name) {
		for (final CrimsonClass tp : crimsonClasses)
			if (tp.getName().equalsIgnoreCase(name)) return tp;
		return null;
	}

	public void Initialize(final ConfigManager configManager, final CrimsonIngot plugin) {
		crimsonKnowledge = new ArrayList<Knowledge>();
		crimsonCreeds = new ArrayList<Creed>();
		crimsonClasses = new ArrayList<CrimsonClass>();
		crimsonKnowledgeMaterials = new ArrayList<Material>();
		crimsonBreakableKnowledgeMaterials = new ArrayList<Material>();
		crimsonPlaceableKnowledgeMaterials = new ArrayList<Material>();

		final Set<String> tpks = configManager.getKnowledgeConfig().getConfig().getConfigurationSection("Knowledge")
				.getKeys(false);
		for (final String tp : tpks) {
			final Knowledge ntp = new Knowledge(tp);
			crimsonKnowledge.add(ntp);
			ntp.loadKnowledge(plugin, configManager.getKnowledgeConfig());
		}

		final Set<String> crks = configManager.getCreedConfig().getConfig().getConfigurationSection("Creeds")
				.getKeys(false);
		for (final String cr : crks) {
			final Creed ntp = new Creed(cr);
			crimsonCreeds.add(ntp);
			ntp.loadCreed(plugin, configManager.getCreedConfig());
		}

		final Set<String> clks = configManager.getClassConfig().getConfig().getConfigurationSection("Classes")
				.getKeys(false);
		for (final String cl : clks) {
			final CrimsonClass ntp = new CrimsonClass(cl);
			crimsonClasses.add(ntp);
			ntp.loadClass(plugin, configManager.getClassConfig());
		}
	}
}
