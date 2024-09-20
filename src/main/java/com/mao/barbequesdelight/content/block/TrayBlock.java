package com.mao.barbequesdelight.content.block;

import dev.xkmc.l2modularblock.mult.CreateBlockStateBlockMethod;
import dev.xkmc.l2modularblock.mult.OnClickBlockMethod;
import dev.xkmc.l2modularblock.mult.PlacementBlockMethod;
import dev.xkmc.l2modularblock.mult.ShapeUpdateBlockMethod;
import dev.xkmc.l2modularblock.one.ShapeBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TrayBlock implements
		ShapeBlockMethod,
		CreateBlockStateBlockMethod, PlacementBlockMethod, ShapeUpdateBlockMethod,
		OnClickBlockMethod {

	public static final VoxelShape OUTER = Block.box(0, 0, 0, 16, 3, 16);

	private static final VoxelShape SHAPE = Shapes.join(OUTER,
			Block.box(1, 1, 1, 15, 3, 15),
			BooleanOp.ONLY_FIRST);

	public static final BooleanProperty SUPPORT = BooleanProperty.create("support");

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SUPPORT);
	}

	@Override
	public InteractionResult onClick(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level.getBlockEntity(pos) instanceof TrayBlockEntity be) {
			ItemStack stack = player.getItemInHand(hand);

		}
		return InteractionResult.PASS;
	}

	@Override
	public BlockState getStateForPlacement(BlockState state, BlockPlaceContext ctx) {
		return state.setValue(SUPPORT, !supported(ctx.getLevel(), ctx.getClickedPos()));
	}

	private Boolean supported(LevelAccessor level, BlockPos pos) {
		return Block.canSupportRigidBlock(level, pos.below());
	}

	@Override
	public BlockState updateShape(Block block, BlockState current, BlockState oldState, Direction facing, BlockState neiState, LevelAccessor level, BlockPos pos, BlockPos neiPos) {
		if (facing.getAxis().equals(Direction.Axis.Y)) {
			return current.setValue(SUPPORT, !supported(level, pos));
		}
		return current;
	}

	@Nullable
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return SHAPE;
	}

}
