package com.mao.barbequesdelight.content.item;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BBQSandwichItem extends FoodItem {

	public static final String KEY = "Skewers";

	public static List<ItemStack> getSkewers(ItemStack stack) {
		var tag = stack.getTag();
		if (tag == null) return List.of();
		if (!tag.contains(KEY, Tag.TAG_LIST)) return List.of();
		List<ItemStack> ans = new ArrayList<>();
		ListTag list = tag.getList(KEY, Tag.TAG_COMPOUND);
		for (int i = 0; i < list.size(); i++) {
			ans.add(ItemStack.of(list.getCompound(i)));
		}
		return ans;
	}

	public BBQSandwichItem(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
		var skewers = getSkewers(stack);
		FoodProperties old = super.getFoodProperties(stack, entity);
		if (old == null || skewers.isEmpty()) return old;
		FoodProperties.Builder builder = new FoodProperties.Builder();
		builder.nutrition(old.getNutrition());
		builder.saturationMod(old.getSaturationModifier());
		if (old.isMeat()) builder.meat();
		if (old.isFastFood()) builder.fast();
		if (old.canAlwaysEat()) builder.alwaysEat();
		Set<MobEffect> set = new HashSet<>();
		var list = new ArrayList<ItemStack>();
		list.add(getDefaultInstance());
		list.addAll(skewers);
		for (var e : list) {
			FoodProperties x = e.getFoodProperties(entity);
			if (x == null) continue;
			for (var eff : x.getEffects()) {
				var ins = eff.getFirst();
				if (set.contains(ins.getEffect()))
					continue;
				set.add(eff.getFirst().getEffect());
				builder.effect(eff::getFirst, eff.getSecond());
			}
			if (x.isMeat()) builder.meat();
			if (x.isFastFood()) builder.fast();
			if (x.canAlwaysEat()) builder.alwaysEat();
		}
		return builder.build();
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		var skewers = getSkewers(stack);
		for (var e : skewers) {
			if (e.getItem() instanceof BBQSkewerItem item) {
				item.applyEffects(e, entity);
			}
		}
		return super.finishUsingItem(stack, level, entity);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		var skewers = getSkewers(stack);
		for (var e : skewers) {
			list.add(e.getHoverName());
		}
		super.appendHoverText(stack, level, list, flag);
	}

}
