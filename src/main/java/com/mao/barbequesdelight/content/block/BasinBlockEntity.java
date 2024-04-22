package com.mao.barbequesdelight.content.block;

import com.mao.barbequesdelight.content.recipe.SkeweringRecipe;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

@SerialClass
public class BasinBlockEntity extends BaseBlockEntity
		implements BlockContainer, BlockSlot {

	@SerialClass.SerialField(toClient = true)
	public final ItemStack[] items = {ItemStack.EMPTY, ItemStack.EMPTY};

	public BasinBlockEntity(BlockEntityType<? extends BasinBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public int size() {
		return 2;
	}

	public ItemStack getStack(int i) {
		return items[i];
	}

	public void setStack(int i, ItemStack split) {
		items[i] = split;
		inventoryChanged();
	}

	public boolean skewer(Player user, int slot) {
		if (level == null) return false;
		ItemStack stack = user.getMainHandItem();
		ItemStack basin = items[slot];
		ItemStack garnishes = user.getOffhandItem();
		var cont = new SimpleContainer(stack, basin, garnishes);
		var optional = level.getRecipeManager().getRecipeFor(BBQDRecipes.RT_SKR.get(), cont, level);
		if (optional.isEmpty()) return false;
		if (level.isClientSide()) return true;
		SkeweringRecipe<?> recipe = optional.get();
		user.getInventory().placeItemBackInInventory(recipe.assemble(cont, level.registryAccess()));
		inventoryChanged();
		return true;
	}

	@Override
	public List<Container> getContainers() {
		return List.of(new SimpleContainer(items));
	}

	public void inventoryChanged() {
		sync();
	}

}
