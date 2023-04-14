package com.wamel.inventoryforbid.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DataStorage_Inventory {

    public static NonNullList<ItemStack> items = NonNullList.a(36, ItemStack.b);
    public static NonNullList<ItemStack> armor = NonNullList.a(4, ItemStack.b);
    public static NonNullList<ItemStack> extraSlots = NonNullList.a(1, ItemStack.b);
    public static List<NonNullList<ItemStack>> contents = ImmutableList.of(items, armor, extraSlots);

}
