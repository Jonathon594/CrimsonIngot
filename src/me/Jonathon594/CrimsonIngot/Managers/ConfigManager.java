package me.Jonathon594.CrimsonIngot.Managers;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
	ConfigAccessor	knowledgeConfig;
	ConfigAccessor	timeConfig;
	ConfigAccessor	skillConfig;
	ConfigAccessor	geneticConfig;

	public ConfigManager(final JavaPlugin plugin) {
		knowledgeConfig = new ConfigAccessor(plugin, "knowledge.yml");
		timeConfig = new ConfigAccessor(plugin, "time.yml");
		skillConfig = new ConfigAccessor(plugin, "skills.yml");
		geneticConfig = new ConfigAccessor(plugin, "genetics.yml");

		knowledgeConfig.saveDefaultConfig();
		timeConfig.saveDefaultConfig();
		skillConfig.saveDefaultConfig();
		geneticConfig.saveDefaultConfig();
	}

	public ConfigAccessor getGeneticConfig() {
		return geneticConfig;
	}

	public ConfigAccessor getKnowledgeConfig() {
		return knowledgeConfig;
	}

	public ConfigAccessor getSkillConfig() {
		return skillConfig;
	}

	public ConfigAccessor getTimeConfig() {
		return timeConfig;
	}
}
