package com.wamel.inventoryforbid;

import com.wamel.inventoryforbid.command.Cmd_B;
import com.wamel.inventoryforbid.command.Cmd_Blist;
import com.wamel.inventoryforbid.command.Cmd_Forbid;
import com.wamel.inventoryforbid.config.ConfigManager_Config;
import com.wamel.inventoryforbid.config.ConfigManager_Game;
import com.wamel.inventoryforbid.config.ConfigManager_Inventory;
import com.wamel.inventoryforbid.data.DataStorage_Config;
import com.wamel.inventoryforbid.data.DataStorage_Game;
import com.wamel.inventoryforbid.event.InventoryShareEvent;
import com.wamel.inventoryforbid.event.MinecraftEvent;
import com.wamel.inventoryforbid.protocol.PacketManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class InventoryForbid extends JavaPlugin {

    private static InventoryForbid instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        ConfigManager_Game.loadBrokenBlockMap();
        ConfigManager_Game.loadTargetedBlockMap();
        ConfigManager_Game.loadReleasedBlockMap();

        ConfigManager_Inventory.load();

        getServer().getPluginManager().registerEvents(new InventoryShareEvent(), this);
        getServer().getPluginManager().registerEvents(new MinecraftEvent(), this);

        getCommand("forbid").setExecutor(new Cmd_Forbid());
        getCommand("b").setExecutor(new Cmd_B());
        getCommand("blist").setExecutor(new Cmd_Blist());

        PacketManager.register(this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (DataStorage_Config.ENABLE == true)
                InventoryShareEvent.patch(player);
        }
    }

    @Override
    public void onDisable() {
        ConfigManager_Game.saveBrokenBlockMap();
        ConfigManager_Game.saveTargetedBlockMap();
        ConfigManager_Game.saveReleasedBlockMap();

        ConfigManager_Config.write("enable", DataStorage_Config.ENABLE);
        ConfigManager_Inventory.save();

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                InventoryShareEvent.unPatch(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static InventoryForbid getInstance() {
        return instance;
    }
}
