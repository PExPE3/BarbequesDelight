package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.content.item.DCListStack;
import com.mao.barbequesdelight.init.data.BBQTagGen;
import com.mao.barbequesdelight.init.registrate.BBQDItems;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2core.serial.recipe.AbstractShapelessRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class CombineItemRecipe extends AbstractShapelessRecipe<CombineItemRecipe> {

	public CombineItemRecipe(String group, ItemStack result, NonNullList<Ingredient> ingredients) {
		super(group, result, ingredients);
	}

	@Override
	public ItemStack assemble(CraftingInput cont, HolderLookup.Provider access) {
		ItemStack ans = super.assemble(cont, access);
		List<ItemStack> tag = new ArrayList<>();
		for (int i = 0; i < cont.size(); i++) {
			ItemStack skewer = cont.getItem(i).copyWithCount(1);
			if (skewer.is(BBQTagGen.GRILLED_SKEWERS)) {
				tag.add(skewer);
			}
		}
		BBQDItems.CONTENTS.set(ans, new DCListStack(tag));
		return ans;
	}

	@Override
	public Serializer<CombineItemRecipe> getSerializer() {
		return BBQDRecipes.COMBINE.get();
	}
}
