package com.mao.barbequesdelight.common.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BarbecuingRecipe implements Recipe<Inventory> {

    protected final Ingredient ingredient;
    protected final ItemStack output;
    protected final Identifier id;
    protected final int barbecuingTime;

    public BarbecuingRecipe(Ingredient ingredient, ItemStack output, Identifier id, int barbecuingTime) {
        this.ingredient = ingredient;
        this.output = output;
        this.id = id;
        this.barbecuingTime = barbecuingTime;
    }

    public int getBarbecuingTime() {
        return barbecuingTime;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return ingredient.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.ingredient);
        return list;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BarbecuingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BarbecuingRecipe> {
        public static final Type INSTANCE = new Type();
    }
}
