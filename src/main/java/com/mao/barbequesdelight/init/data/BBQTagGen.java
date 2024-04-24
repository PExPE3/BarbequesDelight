package com.mao.barbequesdelight.init.data;

import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.registrate.BBQDBlocks;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BBQTagGen {

	public static final TagKey<Item> GRILLED_SKEWERS = ItemTags.create(BarbequesDelight.loc("grilled_skewers"));
	public static final TagKey<Item> RAW_SKEWERS = ItemTags.create(BarbequesDelight.loc("raw_skewers"));

	public static void onBlockTagGen(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
		pvd.addTag(BlockTags.create(new ResourceLocation("carryon", "block_blacklist")))
				.add(BBQDBlocks.GRILL.get(), BBQDBlocks.BASIN.get());
	}

}
