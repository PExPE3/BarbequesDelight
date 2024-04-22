package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

@SerialClass
public class SimpleGrillingRecipe extends GrillingRecipe<SimpleGrillingRecipe> {

	@SerialClass.SerialField
	public Ingredient ingredient;
	@SerialClass.SerialField
	public ItemStack output;
	@SerialClass.SerialField
	public int barbecuingTime;

	public SimpleGrillingRecipe(ResourceLocation id) {
		super(id, BBQDRecipes.RS_BBQ.get());
	}

	@Override
	public int getBarbecuingTime() {
		return barbecuingTime;
	}

	@Override
	public boolean matches(SimpleContainer cont, Level level) {
		return cont.getContainerSize() == 1 && ingredient.test(cont.getItem(0));
	}

	@Override
	public ItemStack assemble(SimpleContainer cont, RegistryAccess level) {
		return output.copy();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess level) {
		return output;
	}

}
