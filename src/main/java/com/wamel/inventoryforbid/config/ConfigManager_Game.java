package com.wamel.inventoryforbid.config;

import com.wamel.inventoryforbid.InventoryForbid;
import com.wamel.inventoryforbid.data.DataStorage_Game;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class ConfigManager_Game {

    private static InventoryForbid plugin = InventoryForbid.getInstance();

    public static void loadTargetedBlockMap() {
        File file = new File(plugin.getDataFolder(), "game_data.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = yaml.getConfigurationSection("targetedBlockMap");

        HashMap<Material, Integer> map = new HashMap<>();

        if (section == null) {
            DataStorage_Game.targetedBlockMap = map;
            return;
        }

        for (String key : section.getKeys(true)) {
            map.put(Material.getMaterial(key), (Integer) section.get(key));
        }

        DataStorage_Game.targetedBlockMap = map;
    }

    public static void loadBrokenBlockMap() {
        File file = new File(plugin.getDataFolder(), "game_data.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = yaml.getConfigurationSection("brokenBlockMap");

        HashMap<Material, Integer> map = new HashMap<>();

        if (section == null) {
            DataStorage_Game.brokenBlockMap = map;
            return;
        }

        for (String key : section.getKeys(true)) {
            map.put(Material.getMaterial(key), (Integer) section.get(key));
        }

        DataStorage_Game.brokenBlockMap = map;
    }

    public static void loadReleasedBlockMap() {
        File file = new File(plugin.getDataFolder(), "game_data.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = yaml.getConfigurationSection("releasedBlockMap");

        HashMap<Material, Integer> map = new HashMap<>();

        if (section == null) {
            DataStorage_Game.releasedBlockMap = map;
            return;
        }

        for (String key : section.getKeys(true)) {
            map.put(Material.getMaterial(key), (Integer) section.get(key));
        }

        DataStorage_Game.releasedBlockMap = map;
    }

    public static void saveTargetedBlockMap() {
        File file = new File(plugin.getDataFolder(), "game_data.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = yaml.getConfigurationSection("targetedBlockMap");

        if (yaml.getConfigurationSection("targetedBlockMap") == null) {
            yaml.createSection("targetedBlockMap");
            section = yaml.getConfigurationSection("targetedBlockMap");
        } else {
            yaml.set("targetedBlockMap", null);
            yaml.createSection("targetedBlockMap");
            section = yaml.getConfigurationSection("targetedBlockMap");
        }

        for (Material material : DataStorage_Game.targetedBlockMap.keySet()) {
            section.set(String.valueOf(material), DataStorage_Game.targetedBlockMap.get(material));
        }

        try {
            yaml.save(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveBrokenBlockMap() {
        File file = new File(plugin.getDataFolder(), "game_data.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = yaml.getConfigurationSection("brokenBlockMap");

        if (yaml.getConfigurationSection("brokenBlockMap") == null) {
            yaml.createSection("brokenBlockMap");
            section = yaml.getConfigurationSection("brokenBlockMap");
        } else {
            yaml.set("brokenBlockMap", null);
            yaml.createSection("brokenBlockMap");
            section = yaml.getConfigurationSection("brokenBlockMap");
        }

        for (Material material : DataStorage_Game.brokenBlockMap.keySet()) {
            section.set(String.valueOf(material), DataStorage_Game.brokenBlockMap.get(material));
        }

        try {
            yaml.save(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveReleasedBlockMap() {
        File file = new File(plugin.getDataFolder(), "game_data.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = yaml.getConfigurationSection("releasedBlockMap");

        if (yaml.getConfigurationSection("releasedBlockMap") == null) {
            yaml.createSection("releasedBlockMap");
            section = yaml.getConfigurationSection("releasedBlockMap");
        } else {
            yaml.set("releasedBlockMap", null);
            yaml.createSection("releasedBlockMap");
            section = yaml.getConfigurationSection("releasedBlockMap");
        }

        for (Material material : DataStorage_Game.releasedBlockMap.keySet()) {
            section.set(String.valueOf(material), DataStorage_Game.releasedBlockMap.get(material));
        }

        try {
            yaml.save(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
