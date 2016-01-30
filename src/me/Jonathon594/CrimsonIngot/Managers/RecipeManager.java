package me.Jonathon594.CrimsonIngot.Managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;

public class RecipeManager {
	public static void setupRecipes(final CrimsonIngot plugin) {
		// ShapedRecipe netherBrickRecipe = new ShapedRecipe(new
		// ItemStack(Material.NETHER_BRICK, 4))
		// .shape("aa", "aa")
		// .setIngredient('b', Material.NETHERRACK);

		final ShapedRecipe chainHelmet = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET, 1))
				.shape("sis", "i i").setIngredient('s', Material.STRING).setIngredient('i', Material.IRON_INGOT);
		final ShapedRecipe chainChest = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1))
				.shape("i i", "sis", "isi").setIngredient('s', Material.STRING).setIngredient('i', Material.IRON_INGOT);
		final ShapedRecipe chainLegs = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1))
				.shape("isi", "s s", "i i").setIngredient('s', Material.STRING).setIngredient('i', Material.IRON_INGOT);
		final ShapedRecipe chainBoots = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS, 1))
				.shape("   ", "s s", "i i").setIngredient('s', Material.STRING).setIngredient('i', Material.IRON_INGOT);

		// plugin.getServer().addRecipe(netherBrickRecipe);
		plugin.getServer().addRecipe(chainHelmet);
		plugin.getServer().addRecipe(chainChest);
		plugin.getServer().addRecipe(chainLegs);
		plugin.getServer().addRecipe(chainBoots);
	}
}
