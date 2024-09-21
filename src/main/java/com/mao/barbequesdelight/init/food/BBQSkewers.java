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
	COD(7, 1f),
	SALMON(7, 1f),
	CHICKEN(7, 0.7f),
	MUSHROOM(6, 0.4f),
	BEEF(8, 0.7f, new EffectEntry(MobEffects.DAMAGE_BOOST, 1800, 0, 0.5f)),
	LAMB(12, 0.8f, new EffectEntry(MobEffects.REGENERATION, 1800, 0, 0.5f)),
	RABBIT(10, 0.8f, new EffectEntry(MobEffects.JUMP, 1800, 0, 1)),
	PORK_SAUSAGE(8, 0.7f, new EffectEntry(MobEffects.DAMAGE_RESISTANCE, 1800, 0, 0.5f)),
	POTATO(6, 0.6f, new EffectEntry(ModEffects.NOURISHMENT, 1800, 0, 0.5f)),
	VEGETABLE(5, 0.5f, new EffectEntry(MobEffects.REGENERATION, 1200, 0, 0.25f)),
	;

	public final ItemEntry<BBQSkewerItem> item;
	public final ItemEntry<BBQSkewerItem> skewer;

	BBQSkewers(int food, float sat, EffectEntry... entries) {
		String id = name().toLowerCase(Locale.ROOT);
		item = BarbequesDelight.REGISTRATE.item("raw_" + id + "_skewer",
						p -> new BBQSkewerItem(p.craftRemainder(Items.STICK)))
				.tag(BBQTagGen.RAW_SKEWERS)
				.model((ctx, pvd) -> pvd.handheld(ctx)).register();
		skewer = BarbequesDelight.REGISTRATE.item("grilled_" + id + "_skewer",
						p -> new BBQSkewerItem(p.craftRemainder(Items.STICK)
								.food(food(food, sat, entries))))
				.tag(BBQTagGen.GRILLED_SKEWERS)
				.model((ctx, pvd) -> pvd.handheld(ctx)).register();
	}

	private static FoodProperties food(int food, float sat, EffectEntry... entries) {
		var ans = new FoodProperties.Builder()
				.nutrition(food).saturationModifier(sat);
		for (var e : entries) {
			ans.effect(e::getEffect, e.chance());
		}
		return ans.build();
	}

	public static void register() {
	}

}
