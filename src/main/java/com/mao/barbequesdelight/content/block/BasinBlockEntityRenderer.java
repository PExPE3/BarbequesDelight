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

import java.util.Random;

public class BasinBlockEntityRenderer implements BlockEntityRenderer<BasinBlockEntity> {

	private final Random random = new Random(42);

	public BasinBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(BasinBlockEntity entity, float pTick, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
		if (entity.getLevel() == null) return;
		int lightAbove = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above());
		for (int i = 0; i < entity.size(); ++i) {
			ItemStack stack = entity.getStack(i);
			Direction direction = entity.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getOpposite();
			int seed = stack.isEmpty() ? 187 : stack.getItem().hashCode() + stack.getCount() + i * 64;
			this.random.setSeed(seed);
			var itemOffset = entity.getOffset(i);
			for (int j = 0; j < getModelCount(stack); ++j) {
				float r = (this.random.nextFloat() * 2.0F - 1.0F) * 0.03F;
				pose.pushPose();
				
				pose.translate(0.5, 0.2, 0.5);
				pose.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
				pose.translate(r, itemOffset, 0.35 - j * 0.05);
				pose.mulPose(Axis.XP.rotationDegrees(20 + j * 4));
				pose.scale(0.375f, 0.375f, 0.375f);

				Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
						lightAbove, overlay, pose, buffer, entity.getLevel(), i);

				pose.popPose();
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

	protected static void renderPos(int count, float r, PoseStack matrices) {
	}
}
