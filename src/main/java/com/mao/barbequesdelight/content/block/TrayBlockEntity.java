package com.mao.barbequesdelight.content.block;

import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2library.base.tile.BaseContainerListener;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
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
public class TrayBlockEntity extends BaseBlockEntity
		implements BlockContainer, BaseContainerListener, BlockSlot {

	@SerialClass.SerialField(toClient = true)
	public final BBQContainer items = new BBQContainer(3);

	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> new InvWrapper(items));

	public TrayBlockEntity(BlockEntityType<? extends TrayBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		items.add(this);
	}

	@Override
	public void notifyTile() {
		inventoryChanged();
	}

	public int size() {
		return 3;
	}

	@Override
	public AABB getBox() {
		return TrayBlock.OUTER.bounds().move(getBlockPos()).deflate(0.01f);
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

	public void inventoryChanged() {
		setChanged();
		sync();
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return handler.cast();
		}
		return super.getCapability(cap, side);
	}

}
