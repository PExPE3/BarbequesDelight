package com.mao.barbequesdelight.content.block;

import com.mao.barbequesdelight.init.registrate.BBQDBlocks;
import dev.xkmc.l2modularblock.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2modularblock.mult.OnClickBlockMethod;
import dev.xkmc.l2modularblock.one.ShapeBlockMethod;
import dev.xkmc.l2modularblock.type.BlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BasinBlock implements OnClickBlockMethod, ShapeBlockMethod {

	public static final BlockMethod TE = new BlockEntityBlockMethodImpl<>(BBQDBlocks.TE_BASIN, BasinBlockEntity.class);

	private static final VoxelShape SHAPE = Shapes.or(
			Block.box(1, 0, 1, 15, 1, 15),
			Block.box(14, 1, 2, 15, 4, 14),
			Block.box(1, 1, 14, 15, 4, 15),
			Block.box(1, 1, 1, 15, 4, 2),
			Block.box(1, 1, 2, 2, 4, 14)
	);

	@Override
	public InteractionResult onClick(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!(level.getBlockEntity(pos) instanceof BasinBlockEntity basin))
			return InteractionResult.PASS;
		int i = basin.getSlotForHitting(hit, level);
		if (i < 0 || i >= basin.size())
			return InteractionResult.PASS;
		ItemStack basinStack = basin.getStack(i);
		ItemStack handStack = player.getItemInHand(hand);
		if (basinStack.isEmpty() && !handStack.isEmpty()) {
			if (!level.isClientSide())
				basin.setStack(i, handStack.split(handStack.getCount()));
			level.playSound(null, pos, SoundEvents.WOOD_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
			return InteractionResult.SUCCESS;
		}
		if (basin.skewer(player, i)) {
			level.playSound(player, pos, SoundEvents.BUNDLE_INSERT, SoundSource.PLAYERS, 1.0f, 1.0f);
			return InteractionResult.SUCCESS;
		} else if (hand == InteractionHand.MAIN_HAND && handStack.isEmpty()) {
			level.playSound(null, pos, SoundEvents.WOOD_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
			if (!level.isClientSide()) {
				player.getInventory().placeItemBackInInventory(basinStack.split(basinStack.getCount()));
				basin.inventoryChanged();
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Nullable
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return SHAPE;
	}

}
