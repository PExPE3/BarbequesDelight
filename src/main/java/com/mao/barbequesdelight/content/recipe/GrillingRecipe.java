package com.mao.barbequesdelight.content.recipe;

import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;

@SerialClass
public abstract class GrillingRecipe<T extends GrillingRecipe<T>> extends BaseRecipe<T, GrillingRecipe<?>, SimpleContainer> {

	public GrillingRecipe(ResourceLocation id, RecType<T, GrillingRecipe<?>, SimpleContainer> fac) {
		super(id, fac);
	}

	public abstract int getBarbecuingTime();

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

}
