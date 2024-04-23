package com.mao.barbequesdelight.compat.jei;

import com.mao.barbequesdelight.content.recipe.SimpleGrillingRecipe;
import com.mao.barbequesdelight.init.BarbequesDelight;
import com.mao.barbequesdelight.init.data.BBQLangData;
import com.mao.barbequesdelight.init.registrate.BBQDBlocks;
import dev.xkmc.l2library.serial.recipe.BaseRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GrillRecipeCategory extends BaseRecipeCategory<SimpleGrillingRecipe, GrillRecipeCategory> {

	protected static final ResourceLocation BG = BarbequesDelight.loc("textures/gui/grill.png");

	public GrillRecipeCategory() {
		super(BarbequesDelight.loc("grilling"), SimpleGrillingRecipe.class);
	}


	public GrillRecipeCategory init(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(BG, 0, 0, 118, 58);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, BBQDBlocks.GRILL.asStack());
		return this;
	}

	@Override
	public Component getTitle() {
		return BBQLangData.JEI_BBQ.get();
	}

	public void setRecipe(IRecipeLayoutBuilder builder, SimpleGrillingRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 16, 22).addIngredients(recipe.ingredient);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 22).addItemStack(recipe.output)
				.addTooltipCallback((view, list) -> list.add(BBQLangData.JEI_TIME.get("" + recipe.barbecuingTime / 20)));
	}

}
