package me.Jonathon594.CrimsonIngot.DataTypes;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;

public class Creed extends PlayerAttribute {

	public Creed(String n) {
		super(n);
	}
	
	public void LoadClass(CrimsonIngot plugin, ConfigAccessor ca){
		loadData(plugin, "Creeds", ca);
	}
}
