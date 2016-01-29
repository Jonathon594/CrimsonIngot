package me.Jonathon594.CrimsonIngot.DataTypes;

import java.util.ArrayList;
import java.util.List;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;

public class SpellSet {
	private final String			name;
	private final ArrayList<Spell>	spellList	= new ArrayList<Spell>();
	private PlayerAttribute			requiredAttribute;

	public SpellSet(final String name) {
		super();
		this.name = name;
	}

	public PlayerAttribute getRequiredAttribute() {
		return requiredAttribute;
	}

	public ArrayList<Spell> getSpellList() {
		return spellList;
	}

	public void loadData(final String key, final ConfigAccessor ca, final CrimsonIngot plugin) {
		final List<String> spellNames = ca.getConfig().getStringList(key + "." + name + ".Spells");
		for (final String s : spellNames) {
			final Spell sp = MagicSpells.getSpellByInGameName(s);
			if (sp == null) break;
			spellList.add(sp);
		}

		final String raName = ca.getConfig().getString(key + "." + name + ".Requires");
		requiredAttribute = plugin.getObjectManager().getAttribute(raName);
	}
}
