package com.mao.barbequesdelight.content.recipe;

import com.mao.barbequesdelight.content.item.BBQSandwichItem;
import com.mao.barbequesdelight.init.data.BBQTagGen;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2library.serial.recipe.AbstractShapelessRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class CombineItemRecipe extends AbstractShapelessRecipe<CombineItemRecipe> {

	public CombineItemRecipe(ResourceLocation rl, String group, ItemStack result, NonNullList<Ingredient> ingredients) {
		super(rl, group, result, ingredients);
	}

	@Override
	public ItemStack assemble(CraftingContainer cont, RegistryAccess access) {
		ItemStack ans = super.assemble(cont, access);
		ListTag tag = new ListTag();
		for (int i = 0; i < cont.getContainerSize(); i++) {
			ItemStack skewer = cont.getItem(i).copyWithCount(1);
			if (skewer.is(BBQTagGen.GRILLED_SKEWERS)) {
				tag.add(skewer.save(new CompoundTag()));
			}
		}
		ans.getOrCreateTag().put(BBQSandwichItem.KEY, tag);
		return ans;
	}

	@Override
	public Serializer<CombineItemRecipe> getSerializer() {
		return BBQDRecipes.COMBINE.get();
	}
}
