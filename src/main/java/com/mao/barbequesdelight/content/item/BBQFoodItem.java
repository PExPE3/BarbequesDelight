package com.mao.barbequesdelight.content.item;

import com.mao.barbequesdelight.init.data.BBQLangData;
import com.mao.barbequesdelight.init.food.BBQSeasoning;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.List;

public class BBQFoodItem extends Item {

	public static final String KEY = "seasoning";

	private static Component getTooltip(MobEffectInstance eff) {
		MutableComponent ans = Component.translatable(eff.getDescriptionId());
		MobEffect mobeffect = eff.getEffect();
		if (eff.getAmplifier() > 0) {
			ans = Component.translatable("potion.withAmplifier", ans,
					Component.translatable("potion.potency." + eff.getAmplifier()));
		}

		if (eff.getDuration() > 20) {
			ans = Component.translatable("potion.withDuration", ans,
					MobEffectUtil.formatDuration(eff, 1));
		}

		return ans.withStyle(mobeffect.getCategory().getTooltipFormatting());
	}

	public static void getFoodEffects(ItemStack stack, List<Component> list) {
		var food = stack.getFoodProperties(null);
		if (food == null) return;
		getFoodEffects(food, list);
	}

	public static void getFoodEffects(FoodProperties food, List<Component> list) {
		for (var eff : food.getEffects()) {
			int chance = Math.round(eff.getSecond() * 100);
			if (eff.getFirst() == null) continue; //I hate stupid modders
			Component ans = getTooltip(eff.getFirst());
			if (chance == 100) {
				list.add(ans);
			} else {
				list.add(BBQLangData.CHANCE_EFFECT.get(ans, chance));
			}
		}
	}

	@Nullable
	public static BBQSeasoning getSeasoning(ItemStack stack) {
		CompoundTag nbt = stack.getTag();
		if (nbt != null && !nbt.getString(KEY).isEmpty()) {
			String str = nbt.getString(KEY);
			try {
				return Enum.valueOf(BBQSeasoning.class, str);
			} catch (Exception ignored) {
			}
		}
		return null;
	}

	public BBQFoodItem(Properties prop) {
		super(prop);
	}

	@Override
	public Component getName(ItemStack stack) {
		BBQSeasoning seasoning = getSeasoning(stack);
		if (seasoning != null)
			return seasoning.getTitle().append(super.getName(stack).copy().withStyle(seasoning.color));
		return super.getName(stack);
	}

	@Override
	public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
		var ans = super.getFoodProperties(stack, entity);
		CompoundTag nbt = stack.getTag();
		if (ans == null || nbt == null) return ans;
		BBQSeasoning seasoning = getSeasoning(stack);
		if (seasoning == null) return ans;
		FoodProperties.Builder builder = new FoodProperties.Builder()
				.nutrition(ans.getNutrition())
				.saturationMod(ans.getSaturationModifier());
		for (var e : ans.getEffects()) {
			builder.effect(e::getFirst, e.getSecond());
		}
		if (ans.isFastFood()) builder.fast();
		if (ans.canAlwaysEat()) builder.alwaysEat();
		if (ans.isMeat()) builder.meat();
		seasoning.modify(builder);
		return builder.build();
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
		BBQSeasoning seasoning = getSeasoning(stack);
		if (seasoning != null && consumer instanceof Player player) {
			seasoning.onFinish(player);
		}
		return super.finishUsingItem(stack, level, consumer);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		BBQSeasoning seasoning = getSeasoning(stack);
		if (seasoning != null)
			list.add(seasoning.getTitle());
		if (Configuration.FOOD_EFFECT_TOOLTIP.get())
			getFoodEffects(stack, list);
	}

}
