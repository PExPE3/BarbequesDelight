package com.mao.barbequesdelight.content.block;

import com.mao.barbequesdelight.init.data.BBQLangData;
import com.mao.barbequesdelight.init.registrate.BBQDItems;
import com.mao.barbequesdelight.init.registrate.BBQDRecipes;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2modularblock.tile_api.TickableBlockEntity;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.registry.ModSounds;

import java.util.List;

@SerialClass
public class GrillBlockEntity extends BaseBlockEntity
		implements HeatableBlockEntity, TickableBlockEntity, BlockContainer, BlockSlot {

	@SerialClass
	public static class ItemEntry {

		@SerialClass.SerialField(toClient = true)
		public int time, duration;
		@SerialClass.SerialField(toClient = true)
		public boolean flipped, burnt;

		@SerialClass.SerialField(toClient = true)
		public ItemStack stack = ItemStack.EMPTY;

		public void tick(GrillBlockEntity be, boolean heated) {
			if (be.level == null) return;
			if (stack.isEmpty()) return;
			if (!heated) {
				if (time > 0) {
					time -= 2;
					if (time <= 0) time = 0;
				}
				return;
			}
			if (stack.isEmpty()) return;
			time++;
			if (time < duration) return;
			if (time >= duration * 2) {
				burnt = true;
				stack = BBQDItems.BURNT_FOOD.asStack();
				be.inventoryChanged();
			}
			if (!flipped) return;
			if (time == duration && !be.level.isClientSide()) {
				var cont = new SimpleContainer(stack);
				var opt = be.level.getRecipeManager().getRecipeFor(BBQDRecipes.RT_BBQ.get(), cont, be.level);
				if (opt.isPresent()) {
					CompoundTag tag = stack.getTag();
					stack = opt.get().assemble(cont, be.level.registryAccess());
					stack.setTag(tag);
					be.inventoryChanged();
				}
			}
		}

		public boolean canFlip() {
			return !flipped && !burnt && time >= duration / 2;
		}

		public boolean smoking() {
			return burnt || canFlip() || flipped && time >= duration;
		}

		public boolean flip(GrillBlockEntity be) {
			if (be.level == null) return false;
			if (!canFlip()) return false;
			flipped = true;
			time = duration / 2;
			if (!be.level.isClientSide())
				be.inventoryChanged();
			be.level.playSound(null, be.getBlockPos(),
					ModSounds.BLOCK_SKILLET_ADD_FOOD.get(),
					SoundSource.BLOCKS, 0.8F, 1.0F);
			return true;
		}

		public boolean addItem(GrillBlockEntity be, ItemStack stack) {
			if (be.level == null) return false;
			var opt = be.level.getRecipeManager()
					.getRecipeFor(BBQDRecipes.RT_BBQ.get(), new SimpleContainer(stack), be.level);
			if (opt.isEmpty()) return false;
			if (!be.level.isClientSide) {
				duration = opt.get().getBarbecuingTime();
				time = 0;
				flipped = burnt = false;
				this.stack = stack.copyWithCount(1);
				stack.shrink(1);
				be.inventoryChanged();
			}
			be.level.playSound(null, be.getBlockPos(), SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
			return true;
		}

		public Component getTooltip() {
			if (burnt) {
				return BBQLangData.JADE_BURNT.get();
			}
			if (!flipped) {
				if (time < duration / 2) {
					return BBQLangData.JADE_COOK.get((duration / 2 - time + 19) / 20);
				} else {
					return BBQLangData.JADE_FLIP.get();
				}
			} else {
				if (time < duration) {
					return BBQLangData.JADE_COOK.get((duration - time + 19) / 20);
				} else {
					return BBQLangData.JADE_COOKED.get();
				}
			}
		}
	}

	@SerialClass.SerialField(toClient = true)
	public final ItemEntry[] entries = {new ItemEntry(), new ItemEntry()};

	public GrillBlockEntity(BlockEntityType<? extends GrillBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public int size() {
		return entries.length;
	}

	public ItemStack getStack(int i) {
		if (i < 0 || i >= size()) return ItemStack.EMPTY;
		return entries[i].stack;
	}

	public boolean addItem(int i, ItemStack stack) {
		if (i < 0 || i >= size()) return false;
		return entries[i].addItem(this, stack);
	}

	@Override
	public AABB getBox() {
		return GrillBlock.OUTER.bounds().move(getBlockPos())
				.move(0, 1 / 16f, 0)
				.deflate(0.01f);
	}

	@Override
	public void tick() {
		if (level == null) return;
		boolean heat = isHeated();
		for (var e : entries) {
			e.tick(this, heat);
		}
		if (level.isClientSide() && heat) {
			addParticles();
		}
	}

	public boolean flip(int i) {
		if (i < 0 || i >= size()) return false;
		return entries[i].flip(this);
	}

	public boolean isFlipped(int i) {
		if (i < 0 || i >= size()) return false;
		return entries[i].flipped;
	}

	public boolean isHeated() {
		return level != null && this.isHeated(level, getBlockPos());
	}

	public boolean isBarbecuing() {
		if (level == null) return false;
		if (!isHeated()) return false;
		for (var e : entries) {
			if (!e.stack.isEmpty())
				return true;
		}
		return false;
	}

	private void addParticles() {
		Level level = getLevel();
		if (level == null) return;
		BlockPos pos = getBlockPos();
		var r = level.random;
		if (r.nextFloat() < 0.2F && isBarbecuing()) {
			double x = pos.getX() + 0.5D + (r.nextDouble() * 0.4D - 0.2D);
			double y = pos.getY() + 1.0D;
			double z = pos.getZ() + 0.5D + (r.nextDouble() * 0.4D - 0.2D);
			double motionY = r.nextBoolean() ? 0.015D : 0.005D;
			level.addParticle(ModParticleTypes.STEAM.get(), x, y, z, 0.0D, motionY, 0.0D);
		}
		for (int i = 0; i < entries.length; ++i) {
			if (!entries[i].stack.isEmpty()) {
				double x0 = pos.getX() + .5d;
				double y = pos.getY() + 1.d;
				double z0 = pos.getZ() + .5d;
				var v1 = getOffset(i);
				Direction dir = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
				int index = dir.get2DDataValue();
				Vec2 offset = index % 2 == 0 ? new Vec2(v1, 0) : new Vec2(0, v1);
				double x = x0 - (dir.getStepX() * offset.x) + (dir.getClockWise().getStepX() * offset.x);
				double z = z0 - (dir.getStepZ() * offset.y) + (dir.getClockWise().getStepZ() * offset.y);
				for (int j = 0; j < (entries[i].smoking() ? 8 : 1); ++j) {
					if (r.nextFloat() < 0.2f) {
						double dx0 = (r.nextDouble() + r.nextDouble() - 1) * 0.1f;
						double dz0 = (r.nextDouble() + r.nextDouble() - 1) * 0.1f;
						level.addParticle(ParticleTypes.SMOKE, x + dx0, y, z + dz0, .0d, 5.e-4d, .0d);
					}
				}
			}
		}
	}

	@Override
	public List<Container> getContainers() {
		ItemStack[] ans = new ItemStack[size()];
		for (int i = 0; i < size(); i++)
			ans[i] = entries[i].stack;
		return List.of(new SimpleContainer(ans));
	}

	public void inventoryChanged() {
		setChanged();
		sync();
	}

}
