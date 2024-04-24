package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2library.serial.recipe.CustomShapelessBuilder;
import net.minecraft.world.level.ItemLike;

public class CombineItemRecipeBuilder extends CustomShapelessBuilder<CombineItemRecipe> {

	public CombineItemRecipeBuilder(ItemLike result, int count) {
		super(BBQDRecipes.COMBINE, result, count);
	}

}
