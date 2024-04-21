package com.mao.barbequesdelight.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class SkewerItem extends ConsumableItem {

    public SkewerItem(FoodComponent foodComponent, boolean hasFoodEffectTooltip) {
        super(new Settings().food(foodComponent).recipeRemainder(Items.STICK), hasFoodEffectTooltip);
    }


    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && !nbt.getString("seasoning").isEmpty()){
            switch (nbt.getString("seasoning")){
                case "chilli_powder" -> {
                  return super.getName(stack).copy().formatted(Formatting.RED);
                }
                case "pepper_powder" -> {
                    return super.getName(stack).copy().formatted(Formatting.GRAY);
                }
                case "cumin_powder" -> {
                    return super.getName(stack).copy().formatted(Formatting.YELLOW);
                }
            }
        }
        return super.getName(stack);
    }

    @Override
    public void affectConsumer(ItemStack stack, World world, LivingEntity user) {
        NbtCompound nbt = stack.getNbt();
        PlayerEntity player = (PlayerEntity) user;
        if (nbt != null && !nbt.getString("seasoning").isEmpty()){
            switch (nbt.getString("seasoning")){
                case "chilli_powder" -> {
                    user.damage(user.getDamageSources().hotFloor(), 2);
                    player.getHungerManager().add(2, 0.2f);
                }
                case "pepper_powder" -> player.getHungerManager().add(1, 0.5f);
                case "cumin_powder" -> player.setHealth(player.getHealth() + 1);
            }
        }
    }
}
