package com.mao.barbequesdelight.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import static net.minecraft.recipe.ShapedRecipe.outputFromJson;

public class SkeweringRecipeSerializer implements RecipeSerializer<SkeweringRecipe> {

    public static final SkeweringRecipeSerializer INSTANCE = new SkeweringRecipeSerializer();

    @Override
    public SkeweringRecipe read(Identifier id, JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));

        ItemStack output;
        JsonElement element = json.get("result");
        if (element.isJsonObject())
            output = outputFromJson((JsonObject) element);
        else {
            String string = element.getAsString();
            Item item = Registries.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
            output = new ItemStack(item);
        }

        ItemStack tool = ItemStack.EMPTY;
        if (JsonHelper.hasElement(json, "tool")) {
            JsonObject jsonContainer = JsonHelper.getObject(json, "tool");
            tool = new ItemStack(JsonHelper.getItem(jsonContainer, "item"), JsonHelper.getInt(jsonContainer, "count", 1));
        }

        ItemStack sideDishes;
        if (JsonHelper.hasElement(json, "sidedishes")) {
            JsonObject jsonContainer = JsonHelper.getObject(json, "sidedishes");
            sideDishes = new ItemStack(JsonHelper.getItem(jsonContainer, "item"), JsonHelper.getInt(jsonContainer, "count", 1));
        }else {
            sideDishes = ItemStack.EMPTY;
        }

        int ingredientCount = json.get("count").getAsInt();

        return new SkeweringRecipe(id, ingredient, tool, output, sideDishes, ingredientCount);
    }

    @Override
    public SkeweringRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient ingredient = Ingredient.fromPacket(buf);
        ItemStack output = buf.readItemStack();
        ItemStack tool = buf.readItemStack();
        ItemStack sideDishes = buf.readItemStack();
        int count =buf.readInt();

        return new SkeweringRecipe(id, ingredient, tool, output, sideDishes, count);
    }

    @Override
    public void write(PacketByteBuf buf, SkeweringRecipe recipe) {
        recipe.ingredient.write(buf);
        buf.writeItemStack(recipe.getOutput(null));
        buf.writeItemStack(recipe.getTool());
        buf.writeItemStack(recipe.getSideDishes());
        buf.writeInt(recipe.getIngredientCount());
    }
}
