package com.mao.barbequesdelight.content.block;

import com.mao.barbequesdelight.content.recipe.SkeweringRecipe;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SerialClass
public class BasinBlockEntity extends StorageTileBlockEntity {

	public BasinBlockEntity(BlockEntityType<? extends BasinBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		items.add(this);
	}

	public int size() {
		return 2;
	}

	@Override
	public AABB getBox() {
		return BasinBlock.OUTER.bounds().move(getBlockPos()).deflate(0.01f);
	}

	public boolean specialClick(Player user, int slot, InteractionHand hand) {
		if (level == null) return false;
		ItemStack stack = user.getMainHandItem();
		ItemStack basin = items.getItem(slot);
		ItemStack garnishes = user.getOffhandItem();
		var cont = new SimpleContainer(stack, basin, garnishes);
		var optional = level.getRecipeManager().getRecipeFor(BBQDRecipes.RT_SKR.get(), cont, level);
		if (optional.isEmpty()) return false;
		if (level.isClientSide()) return true;
		SkeweringRecipe<?> recipe = optional.get();
		ItemStack ret = recipe.assemble(cont, level.registryAccess());
		if (user.getItemInHand(hand).isEmpty()) {
			user.setItemInHand(hand, ret);
		} else user.getInventory().placeItemBackInInventory(ret);
		notifyTile();
		return true;
	}

}
