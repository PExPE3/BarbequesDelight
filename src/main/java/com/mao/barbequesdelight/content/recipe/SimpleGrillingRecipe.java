package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

@SerialClass
public class SimpleGrillingRecipe extends GrillingRecipe<SimpleGrillingRecipe> {

	@SerialField
	public Ingredient ingredient;
	@SerialField
	public ItemStack output;
	@SerialField
	public int barbecuingTime;

	public SimpleGrillingRecipe() {
		super(BBQDRecipes.RS_BBQ.get());
	}

	@Override
	public int getBarbecuingTime() {
		return barbecuingTime;
	}

	@Override
	public boolean matches(SingleRecipeInput cont, Level level) {
		return ingredient.test(cont.getItem(0));
	}

	@Override
	public ItemStack assemble(SingleRecipeInput cont, HolderLookup.Provider level) {
		return output.copy();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider level) {
		return output;
	}

}
