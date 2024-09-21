package com.mao.barbequesdelight.content.block;

import dev.xkmc.l2core.base.tile.BaseBlockEntity;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

@SerialClass
public abstract class StorageTileBlockEntity extends BaseBlockEntity implements BlockContainer, StorageTile {

	@SerialField
	public final StorageTileContainer items = new StorageTileContainer(size());

	public StorageTileBlockEntity(BlockEntityType<? extends StorageTileBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		items.add(this);
	}

	@Override
	public void notifyTile() {
		setChanged();
		sync();
	}

	public ItemStack getStack(int i) {
		return items.getItem(i);
	}

	public void setStack(int i, ItemStack split) {
		items.setItem(i, split);
	}

	@Override
	public List<Container> getContainers() {
		return List.of(items);
	}

	//TODO cap

}
