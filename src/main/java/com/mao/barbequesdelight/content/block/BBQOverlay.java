package com.mao.barbequesdelight.content.block;

import com.mao.barbequesdelight.init.data.BBQLangData;
import dev.xkmc.l2core.util.Proxy;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BBQOverlay implements LayeredDraw.Layer {

	public void render(GuiGraphics g, DeltaTracker delta) {
		LocalPlayer player = Proxy.getClientPlayer();
		HitResult ray = Minecraft.getInstance().hitResult;
		if (ray instanceof BlockHitResult bray) {
			BlockPos pos = bray.getBlockPos();
			BlockEntity entity = player.level().getBlockEntity(pos);
			if (entity instanceof GrillBlockEntity grill) {
				int id = grill.getSlotForHitting(bray, player.level());
				Font font = Minecraft.getInstance().font;
				if (grill.canFlip(id)) {
					Component text = BBQLangData.INFO_FLIP.get();
					renderText(font, g, g.guiWidth() / 2, g.guiHeight() / 2 + 16, text);
				}
			}
		}
	}

	private static void renderText(Font font, GuiGraphics g, int x, int y, Component text) {
		x -= font.width(text) / 2;
		g.drawString(font, text, x, y, -1);
	}

}
