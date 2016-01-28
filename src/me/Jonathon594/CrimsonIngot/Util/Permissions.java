package me.Jonathon594.CrimsonIngot.Util;

import org.bukkit.permissions.Permissible;

public class Permissions {
	public static boolean hasImmortality(final Permissible p) {
		return p.hasPermission("mythria.immortality");
	}

	public static boolean hasSoulReform(final Permissible p) {
		return p.hasPermission("mythria.soulreform");
	}
}
