package com.mao.barbequesdelight.content.block;

import com.mao.barbequesdelight.init.BarbequesDelight;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import dev.xkmc.l2modularblock.DelegateBlock;
import dev.xkmc.l2modularblock.mult.CreateBlockStateBlockMethod;
import dev.xkmc.l2modularblock.mult.DefaultStateBlockMethod;
import dev.xkmc.l2modularblock.mult.OnClickBlockMethod;
import dev.xkmc.l2modularblock.mult.PlacementBlockMethod;
import dev.xkmc.l2modularblock.one.LightBlockMethod;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.CompositeModelBuilder;

public class GrillPlace implements CreateBlockStateBlockMethod, PlacementBlockMethod, DefaultStateBlockMethod,
		OnClickBlockMethod, LightBlockMethod {

	public static final BooleanProperty CAMPFIRE = BooleanProperty.create("has_campfire");

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CAMPFIRE);
	}

	@Override
	public int getLightValue(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
		return state.getValue(CAMPFIRE) ? 15 : 0;
	}

	@Override
	public BlockState getStateForPlacement(BlockState state, BlockPlaceContext ctx) {
		var camp = ctx.getLevel().getBlockState(ctx.getClickedPos());
		if (camp.is(Blocks.CAMPFIRE) &&
				camp.getValue(BlockStateProperties.LIT) &&
				!camp.getValue(BlockStateProperties.WATERLOGGED)) {
			return state.setValue(CAMPFIRE, true);
		}
		return state;
	}

	@Override
	public BlockState getDefaultState(BlockState state) {
		return state.setValue(CAMPFIRE, false);
	}

	@Override
	public InteractionResult onClick(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (!state.getValue(CAMPFIRE) && stack.is(Items.CAMPFIRE) && hit.getDirection().getAxis() != Direction.Axis.Y) {
			if (!level.isClientSide()) {
				level.setBlockAndUpdate(pos, state.setValue(CAMPFIRE, true));
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	public static void buildLoot(RegistrateBlockLootTables pvd, DelegateBlock block) {
		pvd.add(block, LootTable.lootTable()
				.withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)))
				.withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(Blocks.CAMPFIRE))
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(CAMPFIRE, true)))));
	}

	public static void buildModel(DataGenContext<Block, DelegateBlock> ctx, RegistrateBlockstateProvider pvd) {
		var grill = pvd.models().getBuilder("block/grill")
				.parent(new ModelFile.UncheckedModelFile(BarbequesDelight.loc("custom/grill")))
				.texture("side", pvd.modLoc("block/grill_side"))
				.texture("top", pvd.modLoc("block/grill_top"))
				.renderType("cutout");
		var camp = pvd.models().getExistingFile(new ResourceLocation("block/campfire"));
		var grillRef = new ModelFile.UncheckedModelFile(BarbequesDelight.loc("block/grill"));
		var composite = pvd.models().getBuilder("block/grill_with_campfire")
				.customLoader(CompositeModelBuilder::begin)
				.child("campfire", new BlockModelBuilder(null, pvd.models().existingFileHelper).parent(camp).renderType("cutout"))
				.child("grill", new BlockModelBuilder(null, pvd.models().existingFileHelper).parent(grillRef))
				.end().texture("particle", pvd.modLoc("block/grill_side")).ao(false);

		pvd.horizontalBlock(ctx.get(), state -> state.getValue(CAMPFIRE) ? composite : grill);
	}

}
