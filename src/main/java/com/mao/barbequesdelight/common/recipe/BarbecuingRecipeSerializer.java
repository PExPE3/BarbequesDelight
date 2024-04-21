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

public class BarbecuingRecipeSerializer implements RecipeSerializer<BarbecuingRecipe> {

    public static final BarbecuingRecipeSerializer INSTANCE = new BarbecuingRecipeSerializer();

    @Override
    public BarbecuingRecipe read(Identifier id, JsonObject json) {
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

        int barbecuingtime = json.get("barbecuingtime").getAsInt();

        return new BarbecuingRecipe(ingredient, output, id, barbecuingtime);
    }

    @Override
    public BarbecuingRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient ingredient = Ingredient.fromPacket(buf);
        ItemStack output = buf.readItemStack();
        int barbecuingtime = buf.readInt();

        return new BarbecuingRecipe(ingredient, output, id, barbecuingtime);
    }

    @Override
    public void write(PacketByteBuf buf, BarbecuingRecipe recipe) {
        recipe.ingredient.write(buf);
        buf.writeItemStack(recipe.getOutput(null));
        buf.writeInt(recipe.getBarbecuingTime());
    }
}
