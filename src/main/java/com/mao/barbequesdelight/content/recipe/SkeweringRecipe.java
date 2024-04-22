package com.mao.barbequesdelight.content.recipe;

import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;

@SerialClass
public abstract class SkeweringRecipe<T extends SkeweringRecipe<T>> extends BaseRecipe<T, SkeweringRecipe<?>, SimpleContainer> {

	public SkeweringRecipe(ResourceLocation id, RecType<T, SkeweringRecipe<?>, SimpleContainer> fac) {
		super(id, fac);
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

}
