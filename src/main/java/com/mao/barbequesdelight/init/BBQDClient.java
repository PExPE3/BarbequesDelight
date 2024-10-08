package com.mao.barbequesdelight.init;

import com.mao.barbequesdelight.content.block.BBQOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = BarbequesDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BBQDClient {

	@SubscribeEvent
	public static void onLayer(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "block_info", new BBQOverlay());
	}

}
