package me.Jonathon594.CrimsonIngot.DataTypes;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;

public class Creed extends PlayerAttribute {

	public Creed(final String n) {
		super(n);
	}

	public void loadCreed(final CrimsonIngot plugin, final ConfigAccessor ca) {
		loadData(plugin, "Creeds", ca);
	}
}
