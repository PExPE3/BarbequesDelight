package com.mao.barbequesdelight.integration.rei.skewering;

import com.mao.barbequesdelight.common.recipe.SkeweringRecipe;
import com.mao.barbequesdelight.integration.rei.BBQDREIPlugin;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;

public class SkeweringDisplay extends BasicDisplay {

    private final int count;
    private final EntryIngredient tool;
    private final EntryIngredient sideDishes;

    public SkeweringDisplay(SkeweringRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput(null))));
        this.count = recipe.getIngredientCount();
        this.tool = EntryIngredients.of(recipe.getTool());
        this.sideDishes = EntryIngredients.of(recipe.getSideDishes());
    }

    public int getCount() {
        return count;
    }

    public EntryIngredient getSideDishes() {
        return sideDishes;
    }

    public EntryIngredient getTool() {
        return tool;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BBQDREIPlugin.SKEWERING_DISPLAY_CATEGORY;
    }
}
