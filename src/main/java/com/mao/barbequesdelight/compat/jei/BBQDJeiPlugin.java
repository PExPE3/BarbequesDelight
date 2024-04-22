package com.mao.barbequesdelight.compat.jei;

import com.mao.barbequesdelight.content.recipe.SimpleGrillingRecipe;
import com.mao.barbequesdelight.content.recipe.SimpleSkeweringRecipe;
import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.registrate.BBQDBlocks;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2library.util.Proxy;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

@JeiPlugin
public class BBQDJeiPlugin implements IModPlugin {

	public static final ResourceLocation ID = BarbequesDelight.loc("main");

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	public final GrillRecipeCategory GRILL = new GrillRecipeCategory();
	public final BasinRecipeCategory BASIN = new BasinRecipeCategory();
	public IGuiHelper GUI_HELPER;

	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(GRILL.init(helper));
		registration.addRecipeCategories(BASIN.init(helper));
		this.GUI_HELPER = helper;
	}

	public void registerRecipes(IRecipeRegistration registration) {
		ClientLevel level = Proxy.getClientWorld();
		assert level != null;
		registration.addRecipes(GRILL.getRecipeType(), level.getRecipeManager().getAllRecipesFor(BBQDRecipes.RT_BBQ.get())
				.stream().map(e -> e instanceof SimpleGrillingRecipe b ? b : null).filter(Objects::nonNull).toList());
		registration.addRecipes(BASIN.getRecipeType(), level.getRecipeManager().getAllRecipesFor(BBQDRecipes.RT_SKR.get())
				.stream().map(e -> e instanceof SimpleSkeweringRecipe b ? b : null).filter(Objects::nonNull).toList());
	}

	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(BBQDBlocks.GRILL.asStack(), GRILL.getRecipeType());
		registration.addRecipeCatalyst(BBQDBlocks.BASIN.asStack(), BASIN.getRecipeType());
	}


}
