package com.mao.barbequesdelight.content.item;

import com.mao.barbequesdelight.init.food.BBQSeasoning;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BBQSkewerItem extends FoodItem {

	public static final String KEY = "seasoning";

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

	public BBQSkewerItem(Properties prop) {
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
		applyEffects(stack, consumer);
		ItemStack remain = stack.getCraftingRemainingItem();
		ItemStack ans = super.finishUsingItem(stack, level, consumer);
		if (remain.isEmpty()) {
			return ans;
		}
		if (ans.isEmpty()) {
			return remain;
		}
		if (consumer instanceof Player player && !player.isCreative()) {
			player.getInventory().placeItemBackInInventory(remain);
		}
		return ans;
	}

	protected void applyEffects(ItemStack stack, LivingEntity consumer) {
		BBQSeasoning seasoning = getSeasoning(stack);
		if (seasoning != null && consumer instanceof Player player) {
			seasoning.onFinish(player);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		BBQSeasoning seasoning = getSeasoning(stack);
		if (seasoning != null)
			list.add(seasoning.getTitle());
		super.appendHoverText(stack, level, list, flag);
	}

}
