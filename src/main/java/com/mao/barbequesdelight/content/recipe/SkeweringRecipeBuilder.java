package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2core.serial.recipe.BaseRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class SkeweringRecipeBuilder extends BaseRecipeBuilder<
		SkeweringRecipeBuilder, SimpleSkeweringRecipe,
		SkeweringRecipe<?>, SkeweringInput> {

	public SkeweringRecipeBuilder(Ingredient tool, Ingredient main, int count, ItemStack result) {
		this(tool, main, count, Ingredient.EMPTY, 0, result);
	}

	public SkeweringRecipeBuilder(Ingredient tool, Ingredient main, int count, Ingredient side, int sideCount, ItemStack result) {
		super(BBQDRecipes.RS_SKR.get(), result.getItem());
		recipe.tool = tool;
		recipe.ingredient = main;
		recipe.ingredientCount = count;
		recipe.side = side;
		recipe.sideCount = sideCount;
		recipe.output = result;
	}

}
