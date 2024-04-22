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
public class SimpleSkeweringRecipe extends SkeweringRecipe<SimpleSkeweringRecipe> {

	@SerialClass.SerialField
	public Ingredient tool, ingredient, side;
	@SerialClass.SerialField
	public int ingredientCount, sideCount;
	@SerialClass.SerialField
	public ItemStack output;

	public SimpleSkeweringRecipe(ResourceLocation id) {
		super(id, BBQDRecipes.RS_SKR.get());
	}

	@Override
	public boolean matches(SimpleContainer cont, Level level) {
		return cont.getContainerSize() == 3 &&
				tool.test(cont.getItem(0)) &&
				ingredient.test(cont.getItem(1)) &&
				cont.getItem(1).getCount() >= ingredientCount &&
				side.test(cont.getItem(2)) &&
				cont.getItem(2).getCount() >= sideCount;
	}

	@Override
	public ItemStack assemble(SimpleContainer cont, RegistryAccess level) {
		cont.getItem(0).shrink(1);
		cont.getItem(1).shrink(ingredientCount);
		cont.getItem(2).shrink(sideCount);
		return output.copy();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess level) {
		return output;
	}

}
