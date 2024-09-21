package com.mao.barbequesdelight.init;


import com.mao.barbequesdelight.content.block.StorageTileBlockEntity;
import com.mao.barbequesdelight.init.data.BBQLangData;
import com.mao.barbequesdelight.init.data.BBQRecipeGen;
import com.mao.barbequesdelight.init.data.BBQTagGen;
import com.mao.barbequesdelight.init.food.BBQSeasoning;
import com.mao.barbequesdelight.init.food.BBQSkewers;
import com.mao.barbequesdelight.init.registrate.BBQDBlocks;
import com.mao.barbequesdelight.init.registrate.BBQDItems;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2core.init.reg.simple.Reg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(BarbequesDelight.MODID)
@EventBusSubscriber(modid = BarbequesDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BarbequesDelight {

	public static final String MODID = "barbequesdelight";
	public static final Reg REG = new Reg(MODID);
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final Logger LOGGER = LoggerFactory.getLogger(BarbequesDelight.class);

	public static final SimpleEntry<CreativeModeTab> TAB = REGISTRATE.buildModCreativeTab("main", "Barbeque's Delight",
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

		});
	}

	@SubscribeEvent
	public static void onCapabilityRegister(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BBQDBlocks.TE_BASIN.get(), StorageTileBlockEntity::getItemHandler);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BBQDBlocks.TE_TRAY.get(), StorageTileBlockEntity::getItemHandler);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {
		REGISTRATE.addDataGenerator(ProviderType.DATA_MAP, ctx -> {
			var builder = ctx.builder(NeoForgeDataMaps.COMPOSTABLES);
			for (var e : BBQSkewers.values()) {
				builder.add(e.item, new Compostable(0.25f), false);
				builder.add(e.skewer, new Compostable(0.5f), false);
			}
			builder.add(BBQDItems.BURNT_FOOD, new Compostable(0.25f), false);
			builder.add(BBQDItems.KEBAB_WRAP, new Compostable(0.5f), false);
			builder.add(BBQDItems.KEBAB_SANDWICH, new Compostable(0.75f), false);
			builder.add(BBQDItems.BIBIMBAP, new Compostable(1f), false);
		});
	}

	@SubscribeEvent
	public static void communicate(InterModEnqueueEvent event) {
		event.enqueueWork(() -> {
			InterModComms.sendTo("carryon", "blacklistBlock", () -> MODID + ":grill");
		});
	}

	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}
}
