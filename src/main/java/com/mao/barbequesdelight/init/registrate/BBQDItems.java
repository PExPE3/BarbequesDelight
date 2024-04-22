package com.mao.barbequesdelight.init.registrate;

import com.mao.barbequesdelight.content.item.BBQFoodItem;
import com.mao.barbequesdelight.init.BarbequesDelight;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class BBQDItems {

	public static final ItemEntry<BBQFoodItem> BURNT_FOOD = reg("burnt_skewer",
			p -> new BBQFoodItem(p.craftRemainder(Items.STICK).food(
					new FoodProperties.Builder().alwaysEat().nutrition(4).saturationMod(.2f)
							.effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 1), 1.0f)
							.effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 1), 1.0f)
							.build()))).register();

	public static final ItemEntry<BBQFoodItem> KEBAB_WRAP = reg("kebab_wrap",
			p -> new BBQFoodItem(p.food(new FoodProperties.Builder().nutrition(8).saturationMod(0.7f)
					.effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 1200, 0), 0.5f)
					.build()))).register();

	public static final ItemEntry<BBQFoodItem> KEBAB_SANDWICH = reg("kebab_sandwich",
			p -> new BBQFoodItem(p.food(new FoodProperties.Builder().nutrition(14).saturationMod(0.7f)
					.build()))).register();

	private static <T extends Item> ItemBuilder<T, ?> reg(String id, NonNullFunction<Item.Properties, T> item) {
		return BarbequesDelight.REGISTRATE.item(id, item);
	}

	public static void register() {
	}
}
