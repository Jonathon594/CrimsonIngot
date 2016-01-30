package me.Jonathon594.CrimsonIngot.Managers;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
	ConfigAccessor	knowledgeConfig;
	ConfigAccessor	timeConfig;
	ConfigAccessor	classConfig;
	ConfigAccessor	creedConfig;
	ConfigAccessor  specialConfig;

	public ConfigManager(final JavaPlugin plugin) {
		knowledgeConfig = new ConfigAccessor(plugin, "knowledge.yml");
		timeConfig = new ConfigAccessor(plugin, "time.yml");
		classConfig = new ConfigAccessor(plugin, "class.yml");
		creedConfig = new ConfigAccessor(plugin, "creed.yml");
		specialConfig = new ConfigAccessor(plugin, "special.yml");

		knowledgeConfig.saveDefaultConfig();
		timeConfig.saveDefaultConfig();
		classConfig.saveDefaultConfig();
		creedConfig.saveDefaultConfig();
		specialConfig.saveDefaultConfig();
	}

	public ConfigAccessor getSpecialConfig() {
		return specialConfig;
	}

	public ConfigAccessor getClassConfig() {
		return classConfig;
	}

	public ConfigAccessor getCreedConfig() {
		return creedConfig;
	}

	public ConfigAccessor getKnowledgeConfig() {
		return knowledgeConfig;
	}

	public ConfigAccessor getTimeConfig() {
		return timeConfig;
	}
}
