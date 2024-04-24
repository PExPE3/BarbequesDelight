package com.mao.barbequesdelight.init.data;

import com.mao.barbequesdelight.content.recipe.CombineItemRecipeBuilder;
import com.mao.barbequesdelight.content.recipe.GrillingRecipeBuilder;
import com.mao.barbequesdelight.content.recipe.SkeweringRecipeBuilder;
import com.mao.barbequesdelight.init.food.BBQSkewers;
import com.mao.barbequesdelight.init.registrate.BBQDBlocks;
import com.mao.barbequesdelight.init.registrate.BBQDItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.function.BiFunction;

public class BBQRecipeGen {

	public static void genRecipe(RegistrateRecipeProvider pvd) {
		grillSkewer(pvd, BBQSkewers.COD, 300);
		grillSkewer(pvd, BBQSkewers.SALMON, 300);
		grillSkewer(pvd, BBQSkewers.CHICKEN, 260);
		grillSkewer(pvd, BBQSkewers.RABBIT, 280);
		grillSkewer(pvd, BBQSkewers.LAMB, 320);
		grillSkewer(pvd, BBQSkewers.PORK_SAUSAGE, 340);
		grillSkewer(pvd, BBQSkewers.POTATO, 280);

		craftSkewer(pvd, BBQSkewers.COD, Ingredient.of(ForgeTags.RAW_FISHES_COD), Ingredient.of(ForgeTags.VEGETABLES_TOMATO));
		craftSkewer(pvd, BBQSkewers.SALMON, Ingredient.of(ForgeTags.RAW_FISHES_SALMON), Ingredient.of(ForgeTags.VEGETABLES_TOMATO));
		craftSkewer(pvd, BBQSkewers.CHICKEN, Ingredient.of(ForgeTags.RAW_CHICKEN), Ingredient.of(ForgeTags.VEGETABLES_ONION));
		craftSkewer(pvd, BBQSkewers.RABBIT, Ingredient.of(Items.RABBIT), Ingredient.of(ForgeTags.SALAD_INGREDIENTS_CABBAGE));
		craftSkewer(pvd, BBQSkewers.LAMB, Ingredient.of(ForgeTags.RAW_MUTTON));
		craftSkewer(pvd, BBQSkewers.PORK_SAUSAGE, Ingredient.of(ForgeTags.RAW_PORK));
		craftSkewer(pvd, BBQSkewers.POTATO, Ingredient.of(Items.POTATO), Ingredient.of(ForgeTags.RAW_BACON));

		unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BBQDBlocks.GRILL.get())::unlockedBy, Items.IRON_INGOT)
				.pattern("ICI").pattern("I I").pattern("I I")
				.define('I', Items.IRON_INGOT)
				.define('C', Items.IRON_TRAPDOOR)
				.save(pvd);

		unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BBQDBlocks.BASIN.get())::unlockedBy, Items.IRON_INGOT)
				.pattern("I I").pattern(" I ")
				.define('I', ItemTags.WOODEN_SLABS)
				.save(pvd);

		unlock(pvd, new CombineItemRecipeBuilder(BBQDItems.KEBAB_SANDWICH.get(), 1)::unlockedBy, Items.BREAD)
				.requires(ForgeTags.BREAD).requires(BBQTagGen.GRILLED_SKEWERS).requires(BBQTagGen.GRILLED_SKEWERS)
				.requires(ForgeTags.SALAD_INGREDIENTS_CABBAGE).requires(ForgeTags.VEGETABLES_ONION)
				.save(pvd);

		unlock(pvd, new CombineItemRecipeBuilder(BBQDItems.KEBAB_WRAP.get(), 1)::unlockedBy, Items.BREAD)
				.requires(ForgeTags.BREAD).requires(BBQTagGen.GRILLED_SKEWERS).requires(ForgeTags.SALAD_INGREDIENTS_CABBAGE)
				.save(pvd);

	}

	private static void grillSkewer(RegistrateRecipeProvider pvd, BBQSkewers skewer, int time) {
		unlock(pvd, new GrillingRecipeBuilder(Ingredient.of(skewer.item.get()), skewer.skewer.asStack(), time)::unlockedBy, skewer.item.get())
				.save(pvd, skewer.skewer.getId());
	}

	private static void craftSkewer(RegistrateRecipeProvider pvd, BBQSkewers skewer, Ingredient main) {
		unlock(pvd, new SkeweringRecipeBuilder(Ingredient.of(Items.STICK), main, 4, skewer.item.asStack())::unlockedBy, Items.STICK)
				.save(pvd, skewer.item.getId());
	}

	private static void craftSkewer(RegistrateRecipeProvider pvd, BBQSkewers skewer, Ingredient main, Ingredient side) {
		unlock(pvd, new SkeweringRecipeBuilder(Ingredient.of(Items.STICK), main, 2, side, 2, skewer.item.asStack())::unlockedBy, Items.STICK)
				.save(pvd, skewer.item.getId());
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}


}
