package me.Jonathon594.CrimsonIngot.DataTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.Spellbook;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ConfigAccessor;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CrimsonPlayer {
	private final CrimsonIngot			plugin;
	private final Player				player;
	private final UUID					uuid;
	private ArrayList<PlayerAttribute>	playerAttributes	= new ArrayList<PlayerAttribute>();
	private final ConfigAccessor		playerConfig;
	private RoleplayProfile				profile;

	public CrimsonPlayer(final CrimsonIngot plugin, final UUID uuid, final Player player) {
		this.uuid = uuid;
		this.plugin = plugin;
		this.player = player;
		profile = new RoleplayProfile(plugin, player.getUniqueId());
		playerConfig = new ConfigAccessor(plugin, "/players/" + uuid + ".yml");
	}

	public void addAttribute(final PlayerAttribute p) {
		if (!playerAttributes.contains(p)) {
			if(p.getOverwitenAttributes()!=null){
				for(String s : p.getOverwitenAttributes()){
					PlayerAttribute opa = plugin.getObjectManager().getAttribute(s);
					if(playerAttributes.contains(opa)){
						playerAttributes.remove(opa);
					}
				}
			}
			playerAttributes.add(p);
		}
	}

	public void applyAllEffects() {
		final PermissionUser pu = PermissionsEx.getUser(player);

		for (final PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());

		int health = 20;
		int mana = 0;

		for (final PlayerAttribute pa : playerAttributes) {
			health = Math.max(pa.getAttributeHealth(), health);
			mana = Math.max(pa.getMana(), mana);
			for (final PotionEffect pe : pa.getEffects()) {
				player.removePotionEffect(pe.getType());
				player.addPotionEffect(pe);
			}
			for (final String pm : pa.getPermissions())
				pu.addPermission(pm);
			pu.save();
			for (final SpellSet ss : pa.getSpellSets())
				if (playerAttributes.contains(ss.getRequiredAttribute()) || ss.getRequiredAttribute() == null)
					for (final Spell sp : ss.getSpellList()) {
					final Spellbook sb = MagicSpells.getSpellbook(player);
					sb.addSpell(sp);
					sb.save();
				}
		}

		player.setMaxHealth(health);
		MagicSpells.getManaHandler().setMaxMana(player, mana);
	}

	public Player getPlayer() {
		final Player player = Bukkit.getPlayer(uuid);
		return player;
	}

	public ArrayList<PlayerAttribute> getPlayerAttributes() {
		return playerAttributes;
	}

	public List<CrimsonClass> getPlayerClasses() {
		final ArrayList<CrimsonClass> crimsonClass = new ArrayList<CrimsonClass>();
		for (final PlayerAttribute pa : playerAttributes)
			if (pa instanceof CrimsonClass) crimsonClass.add((CrimsonClass) pa);
		return crimsonClass;
	}

	public ConfigAccessor getPlayerConfig() {
		return playerConfig;
	}

	public List<Creed> getPlayerCreeds() {
		final ArrayList<Creed> creed = new ArrayList<Creed>();
		for (final PlayerAttribute pa : playerAttributes)
			if (pa instanceof Creed) creed.add((Creed) pa);
		return creed;
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

	public boolean hasClass() {
		for (final PlayerAttribute pa : playerAttributes)
			if (pa instanceof CrimsonClass) return true;
		return false;
	}

	public boolean hasCreed() {
		for (final PlayerAttribute pa : playerAttributes)
			if (pa instanceof Creed) return true;
		return false;
	}

	public void LoadData() {
		final List<String> attributeNames = playerConfig.getConfig().getStringList("Attributes");

		UpdateLists(attributeNames);

		final String fName = playerConfig.getConfig().getString("Profile.FirstName");
		final String mName = playerConfig.getConfig().getString("Profile.MiddleName");
		final String lName = playerConfig.getConfig().getString("Profile.LastName");
		final String title = playerConfig.getConfig().getString("Profile.Title");
		final Date birthDay = new Date(plugin.getTimeManager());
		birthDay.setMGD(playerConfig.getConfig().getInt("Profile.Birthday"));
		final boolean made = playerConfig.getConfig().getBoolean("Profile.Made");

		profile = new RoleplayProfile(plugin, player.getUniqueId());
		profile.setFirstName(fName);
		profile.setMiddleName(mName);
		profile.setLastName(lName);
		profile.setTitle(title);
		profile.setProfileBirthday(birthDay);
		profile.setMade(made);
	}

	public void removeAllEffects() {
		final PermissionUser pu = PermissionsEx.getUser(player);
		for (final PlayerAttribute pa : playerAttributes) {
			for (final PotionEffect pe : pa.getEffects())
				player.removePotionEffect(pe.getType());
			for (final SpellSet ss : pa.getSpellSets())
				if (playerAttributes.contains(ss.getRequiredAttribute()) || ss.getRequiredAttribute() == null)
					for (final Spell sp : ss.getSpellList()) {
					final Spellbook sb = MagicSpells.getSpellbook(player);
					sb.removeSpell(sp);
					sb.save();
				}
		}

		for (final PlayerAttribute pa : plugin.getObjectManager().getCrimsonAttributes())
			for (final String perm : pa.getPermissions()) {
				pu.removePermission(perm);
				pu.save();
			}
	}

	public void removeAttribute(final PlayerAttribute p) {
		if (playerAttributes.contains(p)) playerAttributes.remove(p);
	}

	public void SaveData() {
		final List<String> attributeNames = new ArrayList<String>();
		for (final PlayerAttribute pa : playerAttributes)
			attributeNames.add(pa.getName());

		playerConfig.getConfig().set("Attributes", attributeNames);

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
		playerConfig.getConfig().set("Profile.Title", profile.getTitle());

		playerConfig.saveConfig();
	}

	private void UpdateLists(final List<String> attributeNames) {
		playerAttributes = new ArrayList<PlayerAttribute>();
		if (attributeNames != null) for (final String pa : attributeNames) {
			final Knowledge knowledge = plugin.getObjectManager().getKnowledge(pa);
			final Creed creed = plugin.getObjectManager().getCreed(pa);
			final CrimsonClass crimsonClass = plugin.getObjectManager().getCrimsonClass(pa);

			if (knowledge != null) if (!playerAttributes.contains(knowledge)) playerAttributes.add(knowledge);
			if (creed != null) if (!playerAttributes.contains(creed)) playerAttributes.add(creed);
			if (crimsonClass != null) if (!playerAttributes.contains(crimsonClass)) playerAttributes.add(crimsonClass);
		}
	}
}
