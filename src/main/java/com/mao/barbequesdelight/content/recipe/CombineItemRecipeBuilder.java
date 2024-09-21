package com.mao.barbequesdelight.content.recipe;

import dev.xkmc.l2core.serial.recipe.CustomShapelessBuilder;
import net.minecraft.world.level.ItemLike;

public class CombineItemRecipeBuilder extends CustomShapelessBuilder<CombineItemRecipe> {

	public CombineItemRecipeBuilder(ItemLike result, int count) {
		super(CombineItemRecipe::new, result, count);
	}

}
