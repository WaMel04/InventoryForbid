package com.wamel.inventoryforbid.event;

import com.google.common.collect.ImmutableList;
import com.wamel.inventoryforbid.data.DataStorage_Config;
import com.wamel.inventoryforbid.data.DataStorage_Inventory;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;
import java.util.List;

public class InventoryShareEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (DataStorage_Config.ENABLE) {
            patch(event.getPlayer());
        } else {
            try {
                unPatch(event.getPlayer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        try {
            unPatch(event.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void patch(Player player) {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        PlayerInventory inv = nmsPlayer.fJ();

        try {
            setField(inv, "i", DataStorage_Inventory.items);
            setField(inv, "j", DataStorage_Inventory.armor);
            setField(inv, "k", DataStorage_Inventory.extraSlots);
            setField(inv, "o", DataStorage_Inventory.contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unPatch(Player player) throws NoSuchFieldException, IllegalAccessException {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        PlayerInventory inv = nmsPlayer.fJ();

        NonNullList<net.minecraft.world.item.ItemStack> personalItems = NonNullList.a(36, net.minecraft.world.item.ItemStack.b);
        NonNullList<net.minecraft.world.item.ItemStack> personalArmor = NonNullList.a(4, net.minecraft.world.item.ItemStack.b);
        NonNullList<net.minecraft.world.item.ItemStack> personalExtraSlots = NonNullList.a(1, net.minecraft.world.item.ItemStack.b);

        NonNullList<net.minecraft.world.item.ItemStack> tempItemsList = getField(inv, "i");
        for (int i=0; i< tempItemsList.size(); i++) {
            net.minecraft.world.item.ItemStack item = tempItemsList.get(i);
            personalItems.set(i, item);
        }

        NonNullList<net.minecraft.world.item.ItemStack> tempArmorList = getField(inv, "j");
        for (int i=0; i< tempArmorList.size(); i++) {
            net.minecraft.world.item.ItemStack item = tempArmorList.get(i);
            personalArmor.set(i, item);
        }

        NonNullList<net.minecraft.world.item.ItemStack> tempExtraSlotsList = getField(inv, "k");
        for (int i=0; i< tempExtraSlotsList.size(); i++) {
            net.minecraft.world.item.ItemStack item = tempExtraSlotsList.get(i);
            personalExtraSlots.set(i, item);
        }

        List<NonNullList<ItemStack>> personalContents = ImmutableList.of(personalItems, personalArmor, personalExtraSlots);

        try {
            setField(inv, "i", personalItems);
            setField(inv, "j", personalArmor);
            setField(inv, "k", personalExtraSlots);
            setField(inv, "o", personalContents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setField(Object obj, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private static NonNullList<ItemStack> getField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (NonNullList<ItemStack>) field.get(obj);
    }
}
