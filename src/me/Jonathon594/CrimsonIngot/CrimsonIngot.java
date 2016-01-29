package me.Jonathon594.CrimsonIngot;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.Jonathon594.CrimsonIngot.Commands.CrimsonIngotCommandExecutor;
import me.Jonathon594.CrimsonIngot.Conversation.ConversationManager;
import me.Jonathon594.CrimsonIngot.Listeners.PlayerListener;
import me.Jonathon594.CrimsonIngot.Listeners.TimeListener;
import me.Jonathon594.CrimsonIngot.Managers.ConfigManager;
import me.Jonathon594.CrimsonIngot.Managers.CrimsonPlayerManager;
import me.Jonathon594.CrimsonIngot.Managers.ObjectManager;
import me.Jonathon594.CrimsonIngot.Managers.TimeManager;
import net.milkbowl.vault.permission.Permission;

public class CrimsonIngot extends JavaPlugin implements Listener {

	public static Permission		perms	= null;
	private final Logger			log		= Logger.getLogger("Minecraft");
	private CrimsonPlayerManager	crimsonPlayerManager;
	private ConfigManager			configManager;
	private TimeManager				timeManager;
	private TimeListener			timeListener;
	private ConversationManager		conversationManager;
	private ObjectManager			objectManager;

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public ConversationManager getConversationManager() {
		return conversationManager;
	}

	public CrimsonPlayerManager getCrimsonPlayerManager() {
		return crimsonPlayerManager;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public TimeManager getTimeManager() {
		return timeManager;
	}

	public void Initialize() {
		configManager = new ConfigManager(this);
		timeManager = new TimeManager(this, configManager.getTimeConfig());
		timeManager.LoadData();
		timeListener = new TimeListener();
		timeListener.run(this, timeManager);
		conversationManager = new ConversationManager(this);
		crimsonPlayerManager = new CrimsonPlayerManager(log);
		objectManager = new ObjectManager();
		objectManager.Initialize(configManager, this);
	}

	@Override
	public void onDisable() {
		final PluginDescriptionFile pdfFile = getDescription();
		log.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now disabled!");
		crimsonPlayerManager.removeAllEffects();
		crimsonPlayerManager.SavePlayers(this);
	}

	@Override
	public void onEnable() {
		final PluginDescriptionFile pdfFile = getDescription();
		log.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now enabled!");

		saveDefaultConfig();
		reloadConfig();
		setupPermissions();

		Initialize();

		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

		// getCommand("knowledge").setExecutor(new
		// KnowledgeCommandExecutor(this));
		// getCommand("skill").setExecutor(new SkillCommandExecutor(this));
		// getCommand("profile").setExecutor(new ProfileCommandExecutor(this));
		// getCommand("genetic").setExecutor(new GeneticCommandExecutor(this));
		getCommand("crimson").setExecutor(new CrimsonIngotCommandExecutor(this));
	}

	public void onReload() {
		onDisable();
		onEnable();
	}

	private boolean setupPermissions() {
		final RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager()
				.getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
}
