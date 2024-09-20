package com.mao.barbequesdelight.event;

import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.food.BBQSeasoning;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = BarbequesDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
