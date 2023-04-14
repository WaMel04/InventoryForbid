package com.wamel.inventoryforbid.config;

import com.wamel.inventoryforbid.InventoryForbid;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager_Config {

    private static InventoryForbid plugin = InventoryForbid.getInstance();

    public static Object read(String key) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        return yaml.get(key);
    }

    public static void write(String key, Object value) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set(key, value);

        try {
            yaml.save(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
