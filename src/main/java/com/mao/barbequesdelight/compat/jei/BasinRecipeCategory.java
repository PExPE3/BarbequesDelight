package com.mao.barbequesdelight.compat.jei;

import com.mao.barbequesdelight.content.recipe.SimpleSkeweringRecipe;
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

import java.util.stream.Stream;

public class BasinRecipeCategory extends BaseRecipeCategory<SimpleSkeweringRecipe, BasinRecipeCategory> {

	protected static final ResourceLocation BG = BarbequesDelight.loc("textures/gui/skewering.png");

	public BasinRecipeCategory() {
		super(BarbequesDelight.loc("skewering"), SimpleSkeweringRecipe.class);
	}


	public BasinRecipeCategory init(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(BG, 0, 0, 118, 58);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, BBQDBlocks.BASIN.asStack());
		return this;
	}

	@Override
	public Component getTitle() {
		return BBQLangData.JEI_BBQ.get();
	}

	public void setRecipe(IRecipeLayoutBuilder builder, SimpleSkeweringRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 13, 13).addIngredients(recipe.tool)
				.addTooltipCallback((view, list) -> list.add(BBQLangData.JEI_MAINHAND.get()));
		builder.addSlot(RecipeIngredientRole.INPUT, 13, 32).addIngredients(VanillaTypes.ITEM_STACK,
						Stream.of(recipe.side.getItems()).map(e -> e.copyWithCount(recipe.sideCount)).toList())
				.addTooltipCallback((view, list) -> list.add(recipe.sideCount == 0 ?
						BBQLangData.JEI_EMPTY.get() : BBQLangData.JEI_OFFHAND.get()));
		builder.addSlot(RecipeIngredientRole.INPUT, 33, 22).addIngredients(VanillaTypes.ITEM_STACK,
						Stream.of(recipe.ingredient.getItems()).map(e -> e.copyWithCount(recipe.ingredientCount)).toList())
				.addTooltipCallback((view, list) -> list.add(BBQLangData.JEI_BASIN.get()));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 83, 22).addItemStack(recipe.output);
	}

}
