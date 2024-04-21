package com.mao.barbequesdelight.integration.rei.barbecuing;

import com.mao.barbequesdelight.common.recipe.BarbecuingRecipe;
import com.mao.barbequesdelight.integration.rei.BBQDREIPlugin;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;

public class GrillDisplay extends BasicDisplay {

    private final int barbecuingTime;

    public GrillDisplay(BarbecuingRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput(null))));
        this.barbecuingTime = recipe.getBarbecuingTime();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BBQDREIPlugin.GRILL_DISPLAY_CATEGORY;
    }

    public int getBarbecuingTime() {
        return barbecuingTime;
    }
}
