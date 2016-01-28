package me.Jonathon594.CrimsonIngot.DataTypes;

import org.bukkit.ChatColor;

import me.Jonathon594.CrimsonIngot.Managers.TimeManager;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotConstants;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class Date {
	private static ChatColor	mc	= CrimsonIngotConstants.mainColor;
	private static ChatColor	cc	= CrimsonIngotConstants.contColor;
	private int					MGD	= 1;
	private final TimeManager	timeManager;

	public Date(final TimeManager timeManager) {
		this.timeManager = timeManager;
	}

	public String GetDateString() {
		final int Year = getYear();
		String yearString;
		if (Year >= 0) yearString = Year + " C.E";
		else
			yearString = Math.abs(Year) + "B.C.E";
		final int month = getMonth();
		final String monthName = timeManager.getMonths().get(month).getName();
		final int monthDay = getDayInMonth();
		final String dayName = getDayName();
		return cc + dayName + mc + ", " + cc + monthDay + mc + "-" + cc + monthName + mc + "-" + cc + yearString;
	}

	public int getDayInMonth() {
		return getYearDay() - getDaysToMonthX(getMonth());
	}

	private String getDayName() {
		final int index = CrimsonIngotUtil.WrapInt(MGD, 1, timeManager.getDayNames().size());
		return timeManager.getDayNames().get(index - 1);
	}

	private int getDaysToMonthX(final int x) {
		int daysToMonthX = 0;
		for (int i = 0; i < x; i++)
			daysToMonthX += timeManager.getMonths().get(i).getLength();

		return daysToMonthX;
	}

	public int getMGD() {
		return MGD;
	}

	public int getMonth() {
		int monthIndex = 0;
		for (int i = 0; i < timeManager.getMonths().size(); i++) {
			final int daysToMonthX = getDaysToMonthX(i);
			if (getYearDay() > daysToMonthX) monthIndex = i;
		}
		return monthIndex;
	}

	public int getYear() {
		return Math.floorDiv(MGD, timeManager.getDaysPerYear());
	}

	private int getYearDay() {
		return CrimsonIngotUtil.WrapInt(MGD, 1, timeManager.getDaysPerYear());
	}

	public void IncDay() {
		MGD++;
	}

	public void setMGD(final int mGD) {
		MGD = mGD;
	}

	public void setMGDFromDayMonthYear(final int day, final int month, final int year) {
		int mGD = 0;
		mGD += timeManager.getDaysPerYear() * (year - 1);
		mGD += getDaysToMonthX(month);
		mGD += day;
		setMGD(mGD);
	}
}
