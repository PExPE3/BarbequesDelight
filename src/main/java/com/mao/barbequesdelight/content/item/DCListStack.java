package com.mao.barbequesdelight.content.item;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class DCListStack {

	public static final DCListStack EMPTY = new DCListStack(List.of());

	private final List<ItemStack> stack;
	private final int hashCode;

	public DCListStack(List<ItemStack> stack) {
		this.stack = stack;
		hashCode = ItemStack.hashStackList(stack);
	}

	public List<ItemStack> stack() {
		return stack;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof DCListStack s && hashCode == s.hashCode && stack.equals(s.stack);
	}

}
