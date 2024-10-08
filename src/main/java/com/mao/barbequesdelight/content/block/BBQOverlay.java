package com.mao.barbequesdelight.content.block;

import com.mao.barbequesdelight.init.data.BBQLangData;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class BBQOverlay implements IGuiOverlay {

	@Override
	public void render(ForgeGui gui, GuiGraphics g, float partialTick, int screenWidth, int screenHeight) {
		LocalPlayer player = Proxy.getClientPlayer();
		if (player == null) return;
		var ray = RayTraceUtil.rayTraceBlock(player.level(), player, player.getBlockReach());
		if (ray.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = ray.getBlockPos();
			BlockEntity entity = player.level().getBlockEntity(pos);
			if (entity instanceof GrillBlockEntity grill) {
				int id = grill.getSlotForHitting(ray, player.level());
				if (grill.canFlip(id)) {
					gui.setupOverlayRenderState(true, false);
					Component text = BBQLangData.INFO_FLIP.get();
					renderText(gui, g, screenWidth / 2, screenHeight / 2 + 16, text);
				}
			}
		}
	}

	private static void renderText(ForgeGui gui, GuiGraphics g, int x, int y, Component text) {
		Font font = gui.getFont();
		x -= font.width(text) / 2;
		g.drawString(font, text, x, y, 0xFFFFFFFF);
	}

}
