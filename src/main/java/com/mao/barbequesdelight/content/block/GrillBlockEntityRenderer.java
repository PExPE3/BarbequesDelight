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

import java.util.Objects;

public class GrillBlockEntityRenderer implements BlockEntityRenderer<GrillBlockEntity> {

	public GrillBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(GrillBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		if (entity.getLevel() == null) return;
		for (int i = 0; i < entity.size(); ++i) {
			ItemStack itemStack = entity.getStack(i);
			if (!itemStack.isEmpty()) {
				Direction direction = entity.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getOpposite();
				matrices.pushPose();
				matrices.translate(0.5, 0.96, 0.5);
				matrices.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
				matrices.mulPose(Axis.XP.rotationDegrees(90.0F));
				var itemOffset = entity.getOffset(i);
				matrices.translate(itemOffset, 0, 0.0);
				matrices.scale(0.4f, 0.4f, 0.4f);
				matrices.mulPose(Axis.YP.rotationDegrees(entity.isFlipped(i) ? 180 : 0));
				int lightAbove = LevelRenderer.getLightColor(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos().above());
				Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED,
						lightAbove, overlay, matrices, vertexConsumers, entity.getLevel(), i);
				matrices.popPose();
			}
		}
	}
}
