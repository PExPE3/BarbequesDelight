package com.mao.barbequesdelight.init.food;

import com.mao.barbequesdelight.content.item.SeasoningItem;
import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.data.BBQLangData;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Locale;
import java.util.function.Supplier;

public enum BBQSeasoning implements ItemLike {
	CUMIN(BBQLangData.CUMIN, BBQLangData.CUMIN_INFO, ChatFormatting.YELLOW, true),
	PEPPER(BBQLangData.PEPPER, BBQLangData.PEPPER_INFO, ChatFormatting.GRAY, true),
	CHILI(BBQLangData.CHILI, BBQLangData.CHILI_INFO, ChatFormatting.RED, true),
	HONEY_MUSTARD(BBQLangData.HONEY, BBQLangData.HONEY_INFO, ChatFormatting.YELLOW, false),
	BUFFALO(BBQLangData.BUFFALO, BBQLangData.BUFFALO_INFO, ChatFormatting.GOLD, false),
	BARBEQUE(BBQLangData.BBQ, BBQLangData.BBQ_INFO, ChatFormatting.GOLD, false),
	;

	private final BBQLangData lang, info;
	public final ChatFormatting color;
	private final String name;
	public final Supplier<? extends Item> item;

	BBQSeasoning(BBQLangData lang, BBQLangData info, ChatFormatting color, boolean powder) {
		this.lang = lang;
		this.info = info;
		this.color = color;
		name = name().toLowerCase(Locale.ROOT);
		item = BarbequesDelight.REGISTRATE.item(name + (powder ? "_powder" : "_sauce"),
						p -> new SeasoningItem(p.durability(powder ? 64 : 16), this))
				.register();
	}

	public String getName() {
		return name;
	}

	public boolean isCustomSeasoning() {
		return item instanceof ItemEntry<? extends Item>;
	}

	public Item asItem() {
		return item.get();
	}

	public MutableComponent getTitle() {
		return lang.get().withStyle(color);
	}

	public MutableComponent getInfo() {
		return info.get().withStyle(color);
	}

	public void onFinish(Player player) {
		if (this == CHILI)
			player.hurt(player.damageSources().inFire(), 2);
		if (this == BUFFALO)
			player.hurt(player.damageSources().inFire(), 2);
		if (this == CUMIN)
			player.heal(2);
	}

	public void modify(FoodProperties.Builder builder) {
		if (this == CHILI) builder.alwaysEdible();
		if (this == PEPPER) builder.fast();
	}

	public void appendEffect(FoodProperties.Builder builder, MobEffectInstance ins, float second) {
		int dur = ins.getDuration();
		int amp = ins.getAmplifier();
		if (this == HONEY_MUSTARD) {
			second *= 2;
			dur /= 2;
		} else if (this == BUFFALO) {
			dur /= 2;
			amp++;
		} else if (this == BARBEQUE) {
			dur *= 2;
		}
		int finalDur = dur;
		int finalAmp = amp;
		builder.effect(() -> new MobEffectInstance(ins.getEffect(), finalDur, finalAmp), second);
	}

	public static void register() {
	}

}
