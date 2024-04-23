package com.mao.barbequesdelight.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.*;

public interface BlockSlot {

	Vec2[] OFFSETS = {new Vec2(.2f, 0), new Vec2(-.2f, 0)};

	AABB getBox();

	default int getSlotForHitting(BlockHitResult hit, Level level) {
		if (hit.getType() != HitResult.Type.BLOCK)
			return 2;
		Vec3 pos1 = hit.getLocation();
		if (!getBox().contains(pos1))
			return 2;
		Direction facing = level.getBlockState(hit.getBlockPos())
				.getValue(HorizontalDirectionalBlock.FACING).getOpposite();
		BlockPos pos = hit.getBlockPos();
		boolean left = false;
		boolean right = false;
		switch (facing) {
			case NORTH -> {
				left = pos1.x - pos.getX() < 0.5D;
				right = pos1.x - pos.getX() > 0.5D;
			}
			case SOUTH -> {
				left = pos1.x - pos.getX() > 0.5D;
				right = pos1.x - pos.getX() < 0.5D;
			}
			case EAST -> {
				left = pos1.z - pos.getZ() < 0.5D;
				right = pos1.z - pos.getZ() > 0.5D;
			}
			case WEST -> {
				left = pos1.z - pos.getZ() > 0.5D;
				right = pos1.z - pos.getZ() < 0.5D;
			}
		}
		if (left) {
			return 0;
		} else if (right) {
			return 1;
		}
		return 2;
	}

}
