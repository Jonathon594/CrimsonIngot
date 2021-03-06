package me.Jonathon594.CrimsonIngot.Util.Migration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.PlayerAttribute;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class PEXUserMigrator {
	public static void MigrateFromPEX(final CrimsonIngot plugin, final Logger log) {
		final ConfigAccessor ca = new ConfigAccessor(plugin, "permissions.yml");

		final Set<String> keys = ca.getConfig().getConfigurationSection("users").getKeys(false);

		CrimsonIngotUtil.logMessage("Loaded PEX File", log);
		CrimsonIngotUtil.logMessage(keys.size() + " users loaded.", log);
		CrimsonIngotUtil.logMessage("Beginning Migrations...", log);

		int index = 0;
		final ArrayList<String> warnings = new ArrayList<String>();
		for (final String key : keys) {
			CrimsonIngotUtil.logMessage("Processing user " + index + "/" + keys.size(), log);
			final ConfigAccessor nc = new ConfigAccessor(plugin, "/players/" + key + ".yml");
			final String path = "users." + key;
			final ArrayList<PlayerAttribute> attributes = new ArrayList<PlayerAttribute>();
			final List<String> groups = ca.getConfig().getStringList(path + ".group");
			CrimsonIngotUtil.logMessage(groups.size() + " groups loaded.", log);
			for (final String group : groups) {
				final PlayerAttribute pa = plugin.getObjectManager().getAttribute(group);
				if (pa != null) {
					if (attributes.contains(pa)) break;
					attributes.add(pa);
					CrimsonIngotUtil.logMessage("Succesfully found Attribute: " + pa.getName(), log);
				} else {
					CrimsonIngotUtil.logMessage("Could not find Attribute for: " + group, log);
					warnings.add("Could not find Attribute for: " + group);
				}
			}
			final ArrayList<String> attributeNames = new ArrayList<String>();
			for (final PlayerAttribute pa : attributes)
				attributeNames.add(pa.getName());
			nc.getConfig().set("Attributes", attributeNames);
			nc.saveConfig();
			index++;
		}
		CrimsonIngotUtil.logMessage("Process Completed with " + warnings.size() + " warnings.", log);
		CrimsonIngotUtil.logMessage("Listing Warnings.", log);
		for (final String warn : warnings)
			CrimsonIngotUtil.logMessage(warn, log);
	}
}
