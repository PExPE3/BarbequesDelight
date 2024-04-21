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

public class SkeweringRecipe implements Recipe<Inventory> {

    protected final Identifier id;
    protected final Ingredient ingredient;
    protected final ItemStack tool;
    protected final ItemStack sideDishes;
    protected final ItemStack result;
    protected final int ingredientCount;

    public SkeweringRecipe(Identifier id, Ingredient ingredient, ItemStack tool, ItemStack result, ItemStack sideDishes, int ingredientCount) {
        this.id = id;
        this.ingredient = ingredient;
        this.sideDishes = sideDishes;

        if (!tool.isEmpty()) {
            this.tool = tool;
        } else if (result.getItem().getRecipeRemainder() != null) {
            this.tool = new ItemStack(result.getItem().getRecipeRemainder());
        } else {
            this.tool = ItemStack.EMPTY;
        }

        this.result = result;
        this.ingredientCount = ingredientCount;
    }

    public ItemStack getTool() {
        return tool;
    }

    public ItemStack getSideDishes() {
        return sideDishes;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(ingredient);
        return list;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return ingredient.test(inventory.getStack(0)) && inventory.getStack(0).getCount() >= getIngredientCount();
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(registryManager).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return result;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SkeweringRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SkeweringRecipe> {
        public static final SkeweringRecipe.Type INSTANCE = new SkeweringRecipe.Type();
    }
}
