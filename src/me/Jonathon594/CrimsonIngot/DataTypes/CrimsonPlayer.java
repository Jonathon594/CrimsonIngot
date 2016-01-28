package me.Jonathon594.CrimsonIngot.DataTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CrimsonPlayer {
	private final CrimsonIngot			plugin;
	private final Player				player;
	private final UUID					uuid;
	private ArrayList<PlayerAttribute>	playerAttributes	= new ArrayList<PlayerAttribute>();
	private final ConfigAccessor		playerConfig;
	private RoleplayProfile				profile;
	private int							playerMythicality;

	public CrimsonPlayer(final CrimsonIngot plugin, final UUID uuid, final Player player) {
		this.uuid = uuid;
		this.plugin = plugin;
		this.player = player;
		profile = new RoleplayProfile(plugin, player.getUniqueId());
		playerConfig = new ConfigAccessor(plugin, "/players/" + uuid + ".yml");
	}

	public void addKnowledge(final Knowledge p) {
		if (!playerAttributes.contains(p)) playerAttributes.add(p);
	}

	public void applyAllEffects() {
		final PermissionUser pu = PermissionsEx.getUser(player);
		for (final String p : plugin.getObjectManager().getBlockedPermissions()) {
			pu.removePermission(p);
			pu.addPermission("-" + p);
		}
		for (final PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());

		final int health = 20;

		for (final PlayerAttribute pa : playerAttributes) {
			for (final PotionEffect pe : pa.getEffects()) {
				player.removePotionEffect(pe.getType());
				player.addPotionEffect(pe);
			}
			for (final String pm : pa.getPermissions()) {
				pu.removePermission("-" + pm);
				pu.addPermission(pm);
			}
		}

		player.setMaxHealth(health);
	}

	public int getMythicality() {
		return playerMythicality;
	}

	public Player getPlayer() {
		final Player player = Bukkit.getPlayer(uuid);
		return player;
	}

	public ConfigAccessor getPlayerConfig() {
		return playerConfig;
	}

	public List<Knowledge> getPlayerKnowledge() {
		final ArrayList<Knowledge> knowledge = new ArrayList<Knowledge>();
		for (final PlayerAttribute pa : playerAttributes)
			if (pa instanceof Knowledge) knowledge.add((Knowledge) pa);
		return knowledge;
	}

	public RoleplayProfile getProfile() {
		return profile;
	}

	public void LoadData() {
		final List<String> attributeNames = playerConfig.getConfig().getStringList("Attributes");

		playerMythicality = playerConfig.getConfig().getInt("Mythicality");

		UpdateLists(attributeNames);

		final String fName = playerConfig.getConfig().getString("Profile.FirstName");
		final String mName = playerConfig.getConfig().getString("Profile.MiddleName");
		final String lName = playerConfig.getConfig().getString("Profile.LastName");
		final Date birthDay = new Date(plugin.getTimeManager());
		birthDay.setMGD(playerConfig.getConfig().getInt("Profile.Birthday"));
		final boolean made = playerConfig.getConfig().getBoolean("Profile.Made");

		profile = new RoleplayProfile(plugin, player.getUniqueId());
		profile.setFirstName(fName);
		profile.setMiddleName(mName);
		profile.setLastName(lName);
		profile.setProfileBirthday(birthDay);
		profile.setMade(made);
	}

	public void removeAllPermissions() {
		final PermissionUser pu = PermissionsEx.getUser(player);
		for (final String p : plugin.getObjectManager().getBlockedPermissions()) {
			pu.removePermission(p);
			pu.removePermission("-" + p);
		}
	}

	public void SaveData() {
		final List<String> attributeNames = new ArrayList<String>();
		for (final PlayerAttribute pa : playerAttributes)
			attributeNames.add(pa.getName());

		playerConfig.getConfig().set("Attributes", attributeNames);

		playerConfig.getConfig().set("Mythicality", playerMythicality);

		String fName = "None";
		String mName = "None";
		String lName = "None";
		Date birthDay = new Date(plugin.getTimeManager());

		if (profile != null) {
			fName = profile.getFirstName();
			mName = profile.getMiddleName();
			lName = profile.getLastName();
			birthDay = profile.getBirthDay();
		}

		playerConfig.getConfig().set("Profile.FirstName", fName);
		playerConfig.getConfig().set("Profile.MiddleName", mName);
		playerConfig.getConfig().set("Profile.LastName", lName);
		playerConfig.getConfig().set("Profile.Birthday", birthDay.getMGD());
		playerConfig.getConfig().set("Profile.Made", profile.isMade());

		playerConfig.saveConfig();
	}

	public void setMythicality(final int i) {
		playerMythicality = i;
	}

	public void soulReform() {
		playerMythicality /= 2;
		applyAllEffects();
		SaveData();
		CrimsonIngotUtil.sendMythriaMessage(player, CrimsonIngotConstants.mainColor + CrimsonIngotConstants.soulReform);
	}

	private void UpdateLists(final List<String> attributeNames) {
		playerAttributes = new ArrayList<PlayerAttribute>();
		if (attributeNames != null) for (final String pa : attributeNames) {
			final Knowledge knowledge = plugin.getObjectManager().getKnowledge(pa);

			if (knowledge != null) if (!playerAttributes.contains(knowledge)) playerAttributes.add(knowledge);
		}
	}
}
