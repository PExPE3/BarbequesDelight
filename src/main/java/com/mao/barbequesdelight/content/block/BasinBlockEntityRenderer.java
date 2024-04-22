package com.mao.barbequesdelight.content.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec2;

import java.util.Random;

public class BasinBlockEntityRenderer implements BlockEntityRenderer<BasinBlockEntity> {

	private final Random random = new Random(42);

	public BasinBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(BasinBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		if (entity.getLevel() == null) return;
		for (int i = 0; i < entity.size(); ++i) {
			ItemStack stack = entity.getStack(i);
			Direction direction = entity.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getOpposite();
			int seed = stack.isEmpty() ? 187 : stack.getItem().hashCode() + stack.getCount();
			this.random.setSeed(seed);
			for (int j = 0; j < getModelCount(stack); ++j) {
				matrices.pushPose();
				float xOffset = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.2F;
				float zOffset = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.2F;
				renderPos(direction, j, xOffset, zOffset, matrices);
				matrices.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));
				Vec2 itemOffset = entity.OFFSETS[i];
				matrices.translate(itemOffset.x, itemOffset.y, 0.0);
				matrices.scale(0.375f, 0.375f, 0.375f);
				int lightAbove = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above());
				Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
						lightAbove, overlay, matrices, vertexConsumers, entity.getLevel(), i);
				matrices.popPose();
			}
		}
	}

	protected int getModelCount(ItemStack stack) {
		if (stack.getCount() > 48) {
			return 12;
		} else if (stack.getCount() > 32) {
			return 10;
		} else if (stack.getCount() > 16) {
			return 8;
		} else if (stack.getCount() > 8) {
			return 6;
		} else {
			return stack.getCount() > 1 ? 2 : 1;
		}
	}

	protected static void renderPos(Direction direction, int count, float xOffset, float zOffset, PoseStack matrices) {
		switch (direction.getOpposite()) {
			case SOUTH -> {
				matrices.translate(0.5 + xOffset, 0.2, 0.15 + (double) count / 20);
				matrices.mulPose(Axis.XP.rotationDegrees((float) -(20 + count * 4)));
			}
			case NORTH -> {
				matrices.translate(0.5 + xOffset, 0.2, 0.85 - (double) count / 20);
				matrices.mulPose(Axis.XN.rotationDegrees((float) -(20 + count * 4)));
			}
			case EAST -> {
				matrices.translate(0.15 + (double) count / 20, 0.2, 0.5 + zOffset);
				matrices.mulPose(Axis.ZP.rotationDegrees((float) (20 + count * 4)));
			}
			case WEST -> {
				matrices.translate(0.85 - (double) count / 20, 0.2, 0.5 + zOffset);
				matrices.mulPose(Axis.ZN.rotationDegrees((float) -(20 + count * 4)));
			}
		}
	}
}
