package com.mao.barbequesdelight.init.data;

import com.mao.barbequesdelight.init.BarbequesDelight;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;
import java.util.Locale;

public enum BBQLangData {
	CHANCE_EFFECT("tooltip.chance", "%1$s with %2$s%% chance", 2, ChatFormatting.GRAY),
	CHILI("seasoning.chili", "Chili Flavored ", 0, ChatFormatting.YELLOW),
	CUMIN("seasoning.cumin", "Cumin Flavored ", 0, ChatFormatting.YELLOW),
	PEPPER("seasoning.pepper", "Pepper Flavored ", 0, ChatFormatting.YELLOW),
	CHILI_INFO("tooltip.chili", "It will make you always want to take one more skewer!", 0, ChatFormatting.GRAY),
	CUMIN_INFO("tooltip.cumin", "Skewers with it will make you feel better~", 0, ChatFormatting.GRAY),
	PEPPER_INFO("tooltip.pepper", "You can't wait to finish skewers with it!", 0, ChatFormatting.GRAY),
	JEI_BBQ("jei.grilling", "Grilling", 0, null),
	JEI_SKR("jei.skewering", "Skewering", 0, null),
	JEI_TIME("jei.time", "Grilling time: %s seconds each side", 1, ChatFormatting.GRAY),
	JEI_MAINHAND("jei.mainhand", "Right click basin with this item in main hand", 0, ChatFormatting.GRAY),
	JEI_OFFHAND("jei.offhand", "Hold this item in off hand", 0, ChatFormatting.GRAY),
	JEI_EMPTY("jei.offhand_empty", "Hold nothing in off hand", 0, ChatFormatting.GRAY),
	JEI_BASIN("jei.item_basin", "Click this item in basin", 0, ChatFormatting.GRAY),
	;

	private final String key, def;
	private final int arg;
	private final ChatFormatting format;


	BBQLangData(String key, String def, int arg, @Nullable ChatFormatting format) {
		this.key = BarbequesDelight.MODID + "." + key;
		this.def = def;
		this.arg = arg;
		this.format = format;
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public MutableComponent get(Object... args) {
		if (args.length != arg)
			throw new IllegalArgumentException("for " + name() + ": expect " + arg + " parameters, got " + args.length);
		MutableComponent ans = Component.translatable(key, args);
		if (format != null) {
			return ans.withStyle(format);
		}
		return ans;
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (BBQLangData lang : BBQLangData.values()) {
			pvd.add(lang.key, lang.def);
		}
	}

}
