package com.mao.barbequesdelight.init;

import com.mao.barbequesdelight.content.block.BBQOverlay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, modid = BarbequesDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BBQDClient {

	@SubscribeEvent
	public static void onLayer(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.HOTBAR, BarbequesDelight.loc("block_info"), new BBQOverlay());
	}

}
