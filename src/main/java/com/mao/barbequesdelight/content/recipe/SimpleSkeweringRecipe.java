package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

@SerialClass
public class SimpleSkeweringRecipe extends SkeweringRecipe<SimpleSkeweringRecipe> {

	@SerialField
	public Ingredient tool, ingredient, side;
	@SerialField
	public int ingredientCount, sideCount;
	@SerialField
	public ItemStack output;

	public SimpleSkeweringRecipe() {
		super(BBQDRecipes.RS_SKR.get());
	}

	@Override
	public boolean matches(SkeweringInput cont, Level level) {
		return tool.test(cont.stick()) &&
				ingredient.test(cont.ingredient()) && cont.ingredient().getCount() >= ingredientCount &&
				(sideCount == 0 || side.test(cont.side()) && cont.side().getCount() >= sideCount);
	}

	@Override
	public ItemStack assemble(SkeweringInput cont, HolderLookup.Provider level) {
		cont.stick().shrink(1);
		cont.ingredient().shrink(ingredientCount);
		if (sideCount > 0) {
			cont.side().shrink(sideCount);
		}
		return output.copy();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider level) {
		return output;
	}

}
