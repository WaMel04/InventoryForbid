package com.wamel.inventoryforbid.data;

import com.wamel.inventoryforbid.config.ConfigManager_Config;

public class DataStorage_Config {

    public static Boolean ENABLE = (Boolean) ConfigManager_Config.read("enable");
    public static Integer START_SLOTS = (Integer) ConfigManager_Config.read("start_slots");
}
