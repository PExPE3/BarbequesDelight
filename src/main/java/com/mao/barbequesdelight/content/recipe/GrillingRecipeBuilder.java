package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2library.serial.recipe.BaseRecipeBuilder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class GrillingRecipeBuilder extends BaseRecipeBuilder<
		GrillingRecipeBuilder, SimpleGrillingRecipe,
		GrillingRecipe<?>, SimpleContainer> {

	public GrillingRecipeBuilder(Ingredient ing, ItemStack result, int time) {
		super(BBQDRecipes.RS_BBQ.get());
		recipe.output = result;
		recipe.ingredient = ing;
		recipe.barbecuingTime = time;
	}


}
