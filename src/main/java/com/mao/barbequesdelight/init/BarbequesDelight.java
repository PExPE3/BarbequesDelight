package com.mao.barbequesdelight.init;


import com.mao.barbequesdelight.init.data.BBQLangData;
import com.mao.barbequesdelight.init.data.BBQRecipeGen;
import com.mao.barbequesdelight.init.data.BBQTagGen;
import com.mao.barbequesdelight.init.food.BBQSeasoning;
import com.mao.barbequesdelight.init.food.BBQSkewers;
import com.mao.barbequesdelight.init.registrate.BBQDBlocks;
import com.mao.barbequesdelight.init.registrate.BBQDItems;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(BarbequesDelight.MODID)
@Mod.EventBusSubscriber(modid = BarbequesDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BarbequesDelight {

	public static final String MODID = "barbequesdelight";
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final Logger LOGGER = LoggerFactory.getLogger(BarbequesDelight.class);

	public static final RegistryEntry<CreativeModeTab> TAB = REGISTRATE.buildModCreativeTab("main", "Barbeque's Delight",
			e -> e.icon(BBQDBlocks.GRILL::asStack));

	public BarbequesDelight() {
		BBQDBlocks.register();
		BBQSeasoning.register();
		BBQSkewers.register();
		BBQDItems.register();
		BBQDRecipes.register();

		REGISTRATE.addDataGenerator(ProviderType.LANG, BBQLangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, BBQRecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, BBQTagGen::onBlockTagGen);
	}

	@SubscribeEvent
	public static void commonInit(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			for (var e : BBQSkewers.values()) {
				ComposterBlock.COMPOSTABLES.put(e.item.get(), 0.25f);
				ComposterBlock.COMPOSTABLES.put(e.skewer.get(), 0.5f);
			}
			ComposterBlock.COMPOSTABLES.put(BBQDItems.BURNT_FOOD.get(), 0.25f);
		});
	}

	@SubscribeEvent
	public static void communicate(InterModEnqueueEvent event) {
		event.enqueueWork(() -> {
			InterModComms.sendTo("carryon", "blacklistBlock", () -> MODID + ":grill");
		});
	}

	public static ResourceLocation loc(String id) {
		return new ResourceLocation(MODID, id);
	}
}
