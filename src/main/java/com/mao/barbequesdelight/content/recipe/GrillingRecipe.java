package com.mao.barbequesdelight.content.recipe;

import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.world.item.crafting.SingleRecipeInput;

@SerialClass
public abstract class GrillingRecipe<T extends GrillingRecipe<T>> extends BaseRecipe<T, GrillingRecipe<?>, SingleRecipeInput> {

	public GrillingRecipe(RecType<T, GrillingRecipe<?>, SingleRecipeInput> fac) {
		super(fac);
	}

	public abstract int getBarbecuingTime();

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

}
