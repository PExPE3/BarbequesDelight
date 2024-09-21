package com.mao.barbequesdelight.init.registrate;

import com.mao.barbequesdelight.content.recipe.*;
import com.mao.barbequesdelight.init.BarbequesDelight;
import dev.xkmc.l2core.init.reg.simple.SR;
import dev.xkmc.l2core.init.reg.simple.Val;
import dev.xkmc.l2core.serial.recipe.AbstractShapelessRecipe;
import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class BBQDRecipes {


	private static final SR<RecipeSerializer<?>> RS = SR.of(BarbequesDelight.REG, BuiltInRegistries.RECIPE_SERIALIZER);
	private static final SR<RecipeType<?>> RT = SR.of(BarbequesDelight.REG, BuiltInRegistries.RECIPE_TYPE);

	public static final Val<RecipeType<GrillingRecipe<?>>> RT_BBQ = RT.reg("grilling", RecipeType::simple);
	public static final Val<RecipeType<SkeweringRecipe<?>>> RT_SKR = RT.reg("skewering", RecipeType::simple);

	public static final Val<BaseRecipe.RecType<SimpleGrillingRecipe, GrillingRecipe<?>, SingleRecipeInput>>
			RS_BBQ = RS.reg("grilling", () -> new BaseRecipe.RecType<>(SimpleGrillingRecipe.class, RT_BBQ));

	public static final Val<BaseRecipe.RecType<SimpleSkeweringRecipe, SkeweringRecipe<?>, SkeweringInput>>
			RS_SKR = RS.reg("skewering", () -> new BaseRecipe.RecType<>(SimpleSkeweringRecipe.class, RT_SKR));

	public static final Val<AbstractShapelessRecipe.Serializer<CombineItemRecipe>>
			COMBINE = RS.reg("combine", () -> new AbstractShapelessRecipe.Serializer<>(CombineItemRecipe::new));

	public static void register() {
	}

}
