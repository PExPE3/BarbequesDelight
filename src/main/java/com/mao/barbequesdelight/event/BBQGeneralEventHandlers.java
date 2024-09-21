package com.mao.barbequesdelight.event;

import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.food.BBQSeasoning;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;


@EventBusSubscriber(modid = BarbequesDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class BBQGeneralEventHandlers {

	@SubscribeEvent
	public static void registerBBQDTrades(VillagerTradesEvent event) {
		if (event.getType() == VillagerProfession.BUTCHER) {
			for (var e : BBQSeasoning.values())
				if (e.isCustomSeasoning())
					event.getTrades().get(1).add(new BasicItemListing(
							12, e.asItem().getDefaultInstance(), 4, 4, 0.05f
					));
		}
	}

}
