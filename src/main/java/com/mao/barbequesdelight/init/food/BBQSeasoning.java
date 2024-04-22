package com.mao.barbequesdelight.init.food;

import com.mao.barbequesdelight.content.item.SeasoningItem;
import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.data.BBQLangData;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;

import java.util.Locale;

public enum BBQSeasoning {
	CUMIN(BBQLangData.CUMIN, BBQLangData.CUMIN_INFO, ChatFormatting.YELLOW),
	PEPPER(BBQLangData.PEPPER, BBQLangData.PEPPER_INFO, ChatFormatting.GRAY),
	CHILI(BBQLangData.CHILI, BBQLangData.CHILI_INFO, ChatFormatting.RED);

	private final BBQLangData lang, info;
	public final ChatFormatting color;
	private final String name;
	public final ItemEntry<SeasoningItem> item;

	BBQSeasoning(BBQLangData lang, BBQLangData info, ChatFormatting color) {
		this.lang = lang;
		this.info = info;
		this.color = color;
		name = name().toLowerCase(Locale.ROOT);
		item = BarbequesDelight.REGISTRATE.item(name + "_powder", p -> new SeasoningItem(p, this))
				.register();
	}

	public String getName() {
		return name;
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
		if (this == CUMIN)
			player.heal(2);
	}

	public void modify(FoodProperties.Builder builder) {
		if (this == CHILI) builder.alwaysEat();
		if (this == PEPPER) builder.fast();
	}

	public static void register() {
	}

}
