package com.wamel.inventoryforbid.config;

import com.wamel.inventoryforbid.InventoryForbid;
import com.wamel.inventoryforbid.data.DataStorage_Inventory;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigManager_Inventory {

    private static InventoryForbid plugin = InventoryForbid.getInstance();

    public static void load() {
        File file = new File(plugin.getDataFolder(), "inventory.yml");
        if (!(file.exists()))
            return;

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        DataStorage_Inventory.items.clear();
        ArrayList<org.bukkit.inventory.ItemStack> itemsList = (ArrayList<org.bukkit.inventory.ItemStack>) yaml.getList("items");

        for (int i=0; i< itemsList.size(); i++) {
            org.bukkit.inventory.ItemStack item = itemsList.get(i);
            DataStorage_Inventory.items.set(i, CraftItemStack.asNMSCopy(item));
        }

        ArrayList<org.bukkit.inventory.ItemStack> armorList = (ArrayList<org.bukkit.inventory.ItemStack>) yaml.getList("armor");

        for (int i=0; i< armorList.size(); i++) {
            org.bukkit.inventory.ItemStack item = armorList.get(i);
            DataStorage_Inventory.armor.set(i, CraftItemStack.asNMSCopy(item));
        }

        ArrayList<org.bukkit.inventory.ItemStack> extraSlotsList = (ArrayList<org.bukkit.inventory.ItemStack>) yaml.getList("extraSlots");

        for (int i=0; i< extraSlotsList.size(); i++) {
            org.bukkit.inventory.ItemStack item = extraSlotsList.get(i);
            DataStorage_Inventory.extraSlots.set(i, CraftItemStack.asNMSCopy(item));
        }
    }

    public static void save() {
        File file = new File(plugin.getDataFolder(), "inventory.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        LinkedList<org.bukkit.inventory.ItemStack> itemsList = new LinkedList<>();
        for (ItemStack item : DataStorage_Inventory.items) {
            org.bukkit.inventory.ItemStack stack = CraftItemStack.asCraftMirror(item);
            itemsList.add(stack);
        }
        yaml.set("items", itemsList);

        LinkedList<org.bukkit.inventory.ItemStack> armorList = new LinkedList<>();
        for (ItemStack item : DataStorage_Inventory.armor) {
            org.bukkit.inventory.ItemStack stack = CraftItemStack.asCraftMirror(item);
            armorList.add(stack);
        }
        yaml.set("armor", armorList);

        LinkedList<org.bukkit.inventory.ItemStack> extraSlotsList = new LinkedList<>();
        for (ItemStack item : DataStorage_Inventory.extraSlots) {
            org.bukkit.inventory.ItemStack stack = CraftItemStack.asCraftMirror(item);
            extraSlotsList.add(stack);
        }
        yaml.set("extraSlots", extraSlotsList);

        try {
            yaml.save(file);
        } catch(Exception e) {
            e.printStackTrace();
            plugin.getLogger().info("§c오류가 발생했습니다. 개발자에게 문의하세요.");
        }
    }
    
}
