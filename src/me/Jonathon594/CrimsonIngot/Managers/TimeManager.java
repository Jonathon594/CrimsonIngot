package me.Jonathon594.CrimsonIngot.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.Date;
import me.Jonathon594.CrimsonIngot.DataTypes.Month;

public class TimeManager {
	public List<String>				DayNames	= new ArrayList<String>();
	public List<Month>				Months		= new ArrayList<Month>();
	public CrimsonIngot				main;
	private final Date				currentDate;
	private final ConfigAccessor	timeConfig;
	private int						daysPerYear	= 0;
	private List<String>			yearNames	= new ArrayList<String>();

	public TimeManager(final CrimsonIngot plugin, final ConfigAccessor timeConfig) {
		main = plugin;
		this.timeConfig = timeConfig;
		currentDate = new Date(this);
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public List<String> getDayNames() {
		return DayNames;
	}

	public int getDaysPerYear() {
		return daysPerYear;
	}

	public List<Month> getMonths() {
		return Months;
	}

	public List<String> getYearNames() {
		return yearNames;
	}

	public void LoadData() {
		DayNames = timeConfig.getConfig().getStringList("DayNames");
		yearNames = timeConfig.getConfig().getStringList("YearNames");

		final Set<String> mks = timeConfig.getConfig().getConfigurationSection("Months").getKeys(false);
		for (final String k : mks) {
			final int mLength = timeConfig.getConfig().getInt("Months." + k + ".Days");
			final Month mn = new Month(k, mLength);
			Months.add(mn);
		}

		currentDate.setMGD(timeConfig.getConfig().getInt("Current.Date"));
		for (int i = 0; i < Months.size(); i++)
			daysPerYear += Months.get(i).getLength();
	}

	public void NewDay() {

		currentDate.IncDay();
		SaveData();
	}

	public void SaveData() {
		timeConfig.getConfig().set("Current.Date", currentDate.getMGD());
	}
}
