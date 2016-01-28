package me.Jonathon594.CrimsonIngot.DataTypes;

import java.util.UUID;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.Managers.ProfileArchiveManager;

public class RoleplayProfile {
	private String				firstName	= "";
	private String				middleName	= "";
	private String				lastName	= "";

	private boolean				made		= false;

	private Date				birthDay;
	private final CrimsonIngot	plugin;
	private final UUID			roleplayer;

	public RoleplayProfile(final CrimsonIngot plugin, final UUID uuid) {
		this.plugin = plugin;
		birthDay = new Date(plugin.getTimeManager());
		roleplayer = uuid;
	}

	public void archive() {
		if (isMade()) ProfileArchiveManager.archiveProfile(plugin, this);
	}

	public int getAge() {
		final Date currentDate = plugin.getTimeManager().getCurrentDate();
		final int age = currentDate.getYear() - birthDay.getYear();
		if (currentDate.getMonth() <= birthDay.getMonth())
			if (currentDate.getDayInMonth() < birthDay.getDayInMonth()) return age - 1;
		return age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFullName() {
		if (middleName != "") return firstName + " " + middleName + " " + lastName;

		return firstName + " " + lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public UUID getRoleplayer() {
		return roleplayer;
	}

	public boolean isMade() {
		return made;
	}

	public void reset() {
		firstName = "";
		middleName = "";
		lastName = "";
		birthDay = new Date(plugin.getTimeManager());
		made = false;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public void setMade(final boolean made) {
		this.made = made;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public void setProfileBirthday(final Date birthday) {
		birthDay = birthday;
	}

	public void setProfileBirthday(int age, final int month, final int day) {
		final Date currentDate = plugin.getTimeManager().getCurrentDate();

		final int currentYear = currentDate.getYear();
		birthDay.setMGDFromDayMonthYear(day, month, currentYear);
		if (currentDate.getMonth() >= birthDay.getMonth())
			if (currentDate.getDayInMonth() >= birthDay.getDayInMonth()) age -= 1;
		birthDay.setMGD(birthDay.getMGD() - age * plugin.getTimeManager().getDaysPerYear());
	}
}
