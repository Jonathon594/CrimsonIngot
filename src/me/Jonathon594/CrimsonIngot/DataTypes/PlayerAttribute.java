package me.Jonathon594.CrimsonIngot.DataTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;

public class PlayerAttribute {
	private final String				name;
	private final List<PotionEffect>	potionEffects			= new ArrayList<PotionEffect>();
	private String						attributeDescription	= "";
	private final List<String>			attributePermissions	= new ArrayList<String>();
	private Material					menuIcon				= Material.AIR;
	private int							menuX					= 0;
	private int							menuY					= 0;
	private boolean						hidden					= false;
	private int							cost;
	private String						requiredAttribute;
	private final ArrayList<Spell>		attributeSpells			= new ArrayList<Spell>();
	private int							mana;
	private int							attributeHealth;

	private final ArrayList<SpellSet>	spellSets				= new ArrayList<SpellSet>();

	public PlayerAttribute(final String n) {
		name = n;
	}

	public int getAttributeHealth() {
		return attributeHealth;
	}

	public int getCost() {
		return cost;
	}

	public String getDescription() {
		return attributeDescription;
	}

	public List<PotionEffect> getEffects() {
		return potionEffects;
	}

	public int getMana() {
		return mana;
	}

	public Material getMenuIcon() {
		return menuIcon;
	}

	public int getMenuX() {
		return menuX;
	}

	public int getMenuY() {
		return menuY;
	}

	public String getName() {
		return name;
	}

	public List<String> getPermissions() {
		return attributePermissions;
	}

	public String getRequiredAttribute() {
		return requiredAttribute;
	}

	public ArrayList<SpellSet> getSpellSets() {
		return spellSets;
	}

	public boolean isHidden() {
		return hidden;
	}

	protected void loadData(final CrimsonIngot plugin, final String key, final ConfigAccessor config) {
		attributeDescription = config.getConfig().getString(key + "." + name + ".Description");
		cost = config.getConfig().getInt(key + "." + name + ".Cost");
		attributeHealth = config.getConfig().getInt(key + "." + name + ".Health");
		mana = config.getConfig().getInt(key + "." + name + ".Mana");
		requiredAttribute = config.getConfig().getString(key + "." + name + ".Requires");

		final List<String> spellNames = config.getConfig().getStringList(key + "." + name + ".Spells");
		for (final String s : spellNames) {
			final Spell sp = MagicSpells.getSpellByInGameName(s);
			if (sp == null) break;
			attributeSpells.add(sp);
		}

		final ConfigurationSection cs = config.getConfig().getConfigurationSection(key + "." + name + ".SpellSets");
		Set<String> spse = null;
		if (cs != null) spse = cs.getKeys(false);

		if (spse != null) for (final String sskey : spse) {
			final SpellSet ss = new SpellSet(sskey);
			ss.loadData(key + "." + name + ".SpellSets", config, plugin);
			spellSets.add(ss);
		}

		final List<String> effs = config.getConfig().getStringList(key + "." + name + ".PotionEffects");
		for (final Object e : effs) {
			final String effect = (String) e;
			if (effect.equalsIgnoreCase("None")) break;

			final String[] Parts = effect.split(" ");
			if (Parts.length != 2) break;

			final PotionEffectType pe = PotionEffectType.getByName(Parts[0]);
			if (pe == null) break;

			final int level = Integer.parseInt(Parts[1]);
			potionEffects.add(new PotionEffect(pe, Integer.MAX_VALUE, level));
		}

		final String iconName = config.getConfig().getString(key + "." + name + ".Menu.Icon");
		final Material mi = Material.getMaterial(iconName);
		if (mi != null) menuIcon = mi;

		menuX = config.getConfig().getInt(key + "." + name + ".Menu.X");
		menuY = config.getConfig().getInt(key + "." + name + ".Menu.Y");

		final List<String> perms = config.getConfig().getStringList(key + "." + name + ".Permissions");
		if (perms != null) for (final String p : perms) {
			final String perm = p;
			attributePermissions.add(perm);
		}

		hidden = plugin.getConfig().getBoolean(key + "." + name + ".Hidden");
	}

	public void setHidden(final boolean hidden) {
		this.hidden = hidden;
	}
}
