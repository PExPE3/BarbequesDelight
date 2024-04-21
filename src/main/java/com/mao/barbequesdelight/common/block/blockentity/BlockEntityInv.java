package com.mao.barbequesdelight.common.block.blockentity;

import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface BlockEntityInv extends Inventory {
    /**
     * Retrieves the item list of this inventory.
     * Must return the same instance every time it's called.
     */
    DefaultedList<ItemStack> getItems();

    /**
     * Creates an inventory from the item list.
     */
    static BlockEntityInv of(DefaultedList<ItemStack> items) {
        return () -> items;
    }

    /**
     * Creates a new inventory with the specified size.
     */
    static BlockEntityInv ofSize(int size) {
        return of(DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    /**
     * Returns the inventory size.
     */
    @Override
    default int size() {
        return getItems().size();
    }

    /**
     * Checks if the inventory is empty.
     * @return true if this inventory has only empty stacks, false otherwise.
     */
    @Override
    default boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the item in the slot.
     */
    @Override
    default ItemStack getStack(int slot) {
        return getItems().get(slot);
    }

    /**
     * Removes items from an inventory slot.
     * @param slot  The slot to remove from.
     * @param count How many items to remove. If there are fewer items in the slot than what are requested,
     *              takes all items in that slot.
     */
    @Override
    default ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    /**
     * Removes all items from an inventory slot.
     * @param slot The slot to remove from.
     */
    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getItems(), slot);
    }

    /**
     * Replaces the current stack in an inventory slot with the provided stack.
     * @param slot  The inventory slot of which to replace the itemstack.
     * @param stack The replacing itemstack. If the stack is too big for
     *              this inventory ({@link Inventory#getMaxCountPerStack()}),
     *              it gets resized to this inventory's maximum amount.
     */
    @Override
    default void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }
    }

    /**
     * Clears the inventory.
     */
    @Override
    default void clear() {
        getItems().clear();
    }

    /**
     * Marks the state as dirty.
     * Must be called after changes in the inventory, so that the game can properly save
     * the inventory contents and notify neighboring blocks of inventory changes.
     */
    @Override
    default void markDirty() {
        // Override if you want behavior.
    }

    /**
     * @return true if the player can use the inventory, false otherwise.
     */
    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    default int getSlotForHitting(BlockHitResult hit, World world){
        if (hit.getType() == HitResult.Type.BLOCK && hit.getSide() == Direction.UP) {
            Vec3d pos1 = hit.getPos();
            Direction facing = world.getBlockState(hit.getBlockPos()).get(HorizontalFacingBlock.FACING).getOpposite();
            BlockPos pos = hit.getBlockPos();
            boolean left = false;
            boolean right = false;
            switch (facing) {
                case NORTH -> {
                    left = pos1.x - (double) pos.getX() < 0.5D;
                    right = pos1.x - (double) pos.getX() > 0.5D;
                }
                case SOUTH -> {
                    left = pos1.x - (double) pos.getX() > 0.5D;
                    right = pos1.x - (double) pos.getX() < 0.5D;
                }
                case EAST -> {
                    left = pos1.z - (double) pos.getZ() < 0.5D;
                    right = pos1.z - (double) pos.getZ() > 0.5D;
                }
                case WEST -> {
                    left = pos1.z - (double) pos.getZ() > 0.5D;
                    right = pos1.z - (double) pos.getZ() < 0.5D;
                }
            }
            if (left) {
                return 0;
            } else if (right) {
                return 1;
            }
        }
        return size();
    }
}
