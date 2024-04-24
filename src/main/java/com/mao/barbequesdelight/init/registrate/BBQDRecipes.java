package com.mao.barbequesdelight.init.registrate;

import com.mao.barbequesdelight.content.recipe.*;
import com.mao.barbequesdelight.init.BarbequesDelight;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2library.serial.recipe.AbstractShapelessRecipe;
import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;

public class BBQDRecipes {

	public static final RegistryEntry<RecipeType<GrillingRecipe<?>>> RT_BBQ = BarbequesDelight.REGISTRATE.recipe("grilling");
	public static final RegistryEntry<RecipeType<SkeweringRecipe<?>>> RT_SKR = BarbequesDelight.REGISTRATE.recipe("skewering");

	public static final RegistryEntry<BaseRecipe.RecType<SimpleGrillingRecipe, GrillingRecipe<?>, SimpleContainer>>
			RS_BBQ = reg("grilling", () -> new BaseRecipe.RecType<>(SimpleGrillingRecipe.class, RT_BBQ));

	public static final RegistryEntry<BaseRecipe.RecType<SimpleSkeweringRecipe, SkeweringRecipe<?>, SimpleContainer>>
			RS_SKR = reg("skewering", () -> new BaseRecipe.RecType<>(SimpleSkeweringRecipe.class, RT_SKR));

	public static final RegistryEntry<AbstractShapelessRecipe.Serializer<CombineItemRecipe>>
			COMBINE = reg("combine", () -> new AbstractShapelessRecipe.Serializer<>(CombineItemRecipe::new));

	private static <A extends RecipeSerializer<?>> RegistryEntry<A> reg(String id, NonNullSupplier<A> sup) {
		return BarbequesDelight.REGISTRATE.simple(id, ForgeRegistries.Keys.RECIPE_SERIALIZERS, sup);
	}

	public static void register() {
	}

}
