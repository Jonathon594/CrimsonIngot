package me.Jonathon594.CrimsonIngot.Managers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAccessor {

	private final String		fileName;
	private final JavaPlugin	plugin;

	private final File			configFile;
	private FileConfiguration	fileConfiguration;

	public ConfigAccessor(final JavaPlugin plugin, final String fileName) {
		if (plugin == null) throw new IllegalArgumentException("plugin cannot be null");
		this.plugin = plugin;
		this.fileName = fileName;
		final File dataFolder = plugin.getDataFolder();
		if (dataFolder == null) throw new IllegalStateException();
		configFile = new File(plugin.getDataFolder(), fileName);
	}

	public FileConfiguration getConfig() {
		if (fileConfiguration == null) reloadConfig();
		return fileConfiguration;
	}

	public void reloadConfig() {
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		final InputStream defConfigStream = plugin.getResource(fileName);
		if (defConfigStream != null) {
			final YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(new InputStreamReader(defConfigStream));
			fileConfiguration.setDefaults(defConfig);
		}
	}

	public void saveConfig() {
		if (fileConfiguration != null && configFile != null) try {
			getConfig().save(configFile);
		} catch (final IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
		}
	}

	public void saveDefaultConfig() {
		if (!configFile.exists()) plugin.saveResource(fileName, false);
	}

}