package com.mao.barbequesdelight.init.food;

import com.mao.barbequesdelight.content.item.BBQSkewerItem;
import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.data.BBQTagGen;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.Locale;

public enum BBQSkewers {
	COD(7, 1f, true),
	SALMON(7, 1f, true),
	CHICKEN(7, 0.7f, true),
	LAMB(12, 0.8f, true, new EffectEntry(() -> MobEffects.REGENERATION, 1800, 0, 0.5f)),
	RABBIT(10, 0.8f, true, new EffectEntry(() -> MobEffects.JUMP, 1800, 0, 1)),
	PORK_SAUSAGE(8, 0.7f, true, new EffectEntry(() -> MobEffects.DAMAGE_RESISTANCE, 1800, 0, 0.5f)),
	POTATO(6, 0.6f, true, new EffectEntry(ModEffects.NOURISHMENT, 1800, 0, 0.5f)),
	;

	public final ItemEntry<BBQSkewerItem> item;
	public final ItemEntry<BBQSkewerItem> skewer;

	BBQSkewers(int food, float sat, boolean meat, EffectEntry... entries) {
		String id = name().toLowerCase(Locale.ROOT);
		item = BarbequesDelight.REGISTRATE.item("raw_" + id + "_skewer",
						p -> new BBQSkewerItem(p.craftRemainder(Items.STICK)))
				.tag(BBQTagGen.RAW_SKEWERS)
				.model((ctx, pvd) -> pvd.handheld(ctx)).register();
		skewer = BarbequesDelight.REGISTRATE.item("grilled_" + id + "_skewer",
						p -> new BBQSkewerItem(p.craftRemainder(Items.STICK)
								.food(food(food, sat, meat, entries))))
				.tag(BBQTagGen.GRILLED_SKEWERS)
				.model((ctx, pvd) -> pvd.handheld(ctx)).register();
	}

	private static FoodProperties food(int food, float sat, boolean meat, EffectEntry... entries) {
		var ans = new FoodProperties.Builder()
				.nutrition(food).saturationMod(sat);
		if (meat) ans.meat();
		for (var e : entries) {
			ans.effect(e::getEffect, e.chance());
		}
		return ans.build();
	}

	public static void register() {
	}

}
