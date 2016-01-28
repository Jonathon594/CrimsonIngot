package me.Jonathon594.CrimsonIngot.DataTypes;

public class Month {
	private final String	Name;
	private final int		Length;

	public Month(final String name, final int length) {
		Name = name;
		Length = length;
	}

	public int getLength() {
		return Length;
	}

	public String getName() {
		return Name;
	}
}
