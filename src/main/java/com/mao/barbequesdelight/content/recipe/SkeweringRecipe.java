package com.mao.barbequesdelight.content.recipe;

import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.marker.SerialClass;

@SerialClass
public abstract class SkeweringRecipe<T extends SkeweringRecipe<T>> extends BaseRecipe<T, SkeweringRecipe<?>, SkeweringInput> {

	public SkeweringRecipe(RecType<T, SkeweringRecipe<?>, SkeweringInput> fac) {
		super(fac);
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

}
