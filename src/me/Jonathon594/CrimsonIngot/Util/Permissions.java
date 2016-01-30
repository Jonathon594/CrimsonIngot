package me.Jonathon594.CrimsonIngot.Util;

import org.bukkit.permissions.Permissible;

public class Permissions {
	public static boolean isAdmin(final Permissible p) {
		return p.hasPermission("crimson.admin");
	}
}
