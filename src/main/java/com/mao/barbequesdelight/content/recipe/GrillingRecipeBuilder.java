package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2core.serial.recipe.BaseRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class GrillingRecipeBuilder extends BaseRecipeBuilder<
		GrillingRecipeBuilder, SimpleGrillingRecipe,
		GrillingRecipe<?>, SingleRecipeInput> {

	public GrillingRecipeBuilder(Ingredient ing, ItemStack result, int time) {
		super(BBQDRecipes.RS_BBQ.get(), result.getItem());
		recipe.output = result;
		recipe.ingredient = ing;
		recipe.barbecuingTime = time;
	}


}
