package me.Jonathon594.CrimsonIngot.DataTypes;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;

public class CrimsonClass extends PlayerAttribute {
	
	private int classHealth;

	public CrimsonClass(final String n) {
		super(n);
	}

	public void LoadClass(CrimsonIngot plugin, ConfigAccessor ca){
		classHealth = ca.getConfig().getInt("Classes." + getName() + ".Health");
		
		loadData(plugin, "Classes", ca);
	}

	public int getClassHealth() {
		return classHealth;
	}
}
