package com.mao.barbequesdelight.registry;

import com.mao.barbequesdelight.BarbequesDelight;
import com.mao.barbequesdelight.common.block.GrillBlock;
import com.mao.barbequesdelight.common.block.IngredientsBasinBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BBQDBlocks {

    public static final Block GRILL = register("grill", new GrillBlock());
    public static final Block INGREDIENTS_BASIN = register("ingredients_basin", new IngredientsBasinBlock());

    private static Block register(String id, Block block){
        return Registry.register(Registries.BLOCK, BarbequesDelight.asID(id), block);
    }

    public static void registerBBQDBlocks(){
        BarbequesDelight.LOGGER.debug("Register BBQD Blocks For" + BarbequesDelight.MODID);
    }
}
