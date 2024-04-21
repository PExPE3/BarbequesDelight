package com.mao.barbequesdelight.registry;

import com.mao.barbequesdelight.BarbequesDelight;
import com.mao.barbequesdelight.common.block.blockentity.GrillBlockEntity;
import com.mao.barbequesdelight.common.block.blockentity.IngredientsBasinBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BBQDEntityTypes {

    public static final BlockEntityType<GrillBlockEntity> GRILL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            BarbequesDelight.asID("grill_block_entity"),
            FabricBlockEntityTypeBuilder.create(GrillBlockEntity::new, BBQDBlocks.GRILL).build()
    );

    public static final BlockEntityType<IngredientsBasinBlockEntity> INGREDIENTS_BASIN_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            BarbequesDelight.asID("ingredients_basin_block_entity"),
            FabricBlockEntityTypeBuilder.create(IngredientsBasinBlockEntity::new, BBQDBlocks.INGREDIENTS_BASIN).build()
    );

    public static void registerBBQDEntityTypes(){
        BarbequesDelight.LOGGER.debug("Register BBQD EntityTypes For" + BarbequesDelight.MODID);
    }
}
