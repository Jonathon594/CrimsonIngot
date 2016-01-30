package me.Jonathon594.CrimsonIngot.Managers;

import java.util.UUID;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.Date;
import me.Jonathon594.CrimsonIngot.DataTypes.RoleplayProfile;

public class ProfileArchiveManager {
	public static void archiveProfile(final CrimsonIngot plugin, final RoleplayProfile profile) {
		final ConfigAccessor ca = new ConfigAccessor(plugin,
				"/profiles/" + profile.getFullName().replace(' ', '-') + ".yml");
		ca.getConfig().set("FirstName", profile.getFirstName());
		ca.getConfig().set("MiddleName", profile.getMiddleName());
		ca.getConfig().set("LastName", profile.getLastName());
		ca.getConfig().set("Title", profile.getTitle());
		ca.getConfig().set("Birthday", profile.getBirthDay().getMGD());
		ca.getConfig().set("Roleplayer", profile.getRoleplayer());
		ca.getConfig().set("DeathDate", plugin.getTimeManager().getCurrentDate().getMGD());
		ca.getConfig().set("DeathAge", profile.getAge());
		ca.saveConfig();
	}

	public static RoleplayProfile getProfile(final CrimsonIngot plugin, final String fullName) {
		final ConfigAccessor ca = new ConfigAccessor(plugin, "/profiles/" + fullName.replace(' ', '-') + ".yml");
		final String fName = ca.getConfig().getString("FirstName");
		final String mName = ca.getConfig().getString("MiddleName");
		final String lName = ca.getConfig().getString("LastName");
		final String title = ca.getConfig().getString("Title");
		if (fName != null && mName != null && lName != null) {
			final Date birthDay = new Date(plugin.getTimeManager());
			birthDay.setMGD(ca.getConfig().getInt("Birthday"));
			final boolean made = true;
			final String roleplayer = ca.getConfig().getString("Roleplayer");

			final RoleplayProfile profile = new RoleplayProfile(plugin, UUID.fromString(roleplayer));
			profile.setFirstName(fName);
			profile.setMiddleName(mName);
			profile.setLastName(lName);
			profile.setProfileBirthday(birthDay);
			profile.setMade(made);
			profile.setTitle(title);

			return profile;
		}

		return null;
	}
}
