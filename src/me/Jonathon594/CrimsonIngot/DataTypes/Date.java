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

	public String getLongDateString() {
		final String yearMonthString = getYearMonthString();
		final String dayName = getDayName();
		final String mdString = CrimsonIngotUtil.makeNumberString(getNThWeekDayInMonth());
		return cc + mdString + " " + dayName + " of the " + yearMonthString;
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

	public int getNThWeekDayInMonth() {
		final int dim = getDayInMonth();
		final int r = Math.floorDiv(dim, timeManager.getDayNames().size()) + 1;

		return r;
	}

	public String getSimpleDateString() {
		final String yearString = getSimpleYearString();
		final String monthName = timeManager.getMonths().get(getMonth()).getName();
		final int monthDay = getDayInMonth();
		final String dayName = getDayName();
		return cc + dayName + mc + ", " + cc + monthDay + mc + "-" + cc + monthName + mc + "-" + cc + yearString;
	}

	private String getSimpleYearString() {
		final int year = getYear();
		if (year < 0) return Math.abs(year) + " B.S.D";
		return Integer.toString(year);
	}

	public int getYear() {
		return Math.floorDiv(MGD, timeManager.getDaysPerYear());
	}

	private int getYearDay() {
		return CrimsonIngotUtil.WrapInt(MGD, 1, timeManager.getDaysPerYear());
	}

	public String getYearMonthString() {
		final int year = Math.abs(getYear());
		final String yearText = Integer.toString(year);
		final char[] chars = yearText.toCharArray();
		final int ones = Integer.parseInt(Character.toString(chars[chars.length - 1]));
		int tens = 0;
		int r = 0;
		if (year > 9) tens = Integer.parseInt(Character.toString(chars[chars.length - 2]));
		if (year > 99) r = Integer.parseInt(yearText.substring(0, yearText.length() - 2));
		final String onesText = getYearName(ones);
		String tensText = getYearName(tens);
		if (tensText == onesText) tensText = "Crimson";
		final String rText = CrimsonIngotUtil.makeNumberString(r + 1);
		String negString = "";
		if (getYear() < 0) negString = " B.S.D";
		final String monthName = timeManager.getMonths().get(getMonth()).getName();
		if (Math.abs(getYear()) < 10) return rText + " " + monthName + " " + onesText + negString;
		return rText + " " + monthName + " " + tensText + "-" + onesText + negString;
	}

	private String getYearName(final int index) {
		// index = CrimsonIngotUtil.WrapInt(index, 1,
		// timeManager.getYearNames().size());
		return timeManager.getYearNames().get(index);
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
