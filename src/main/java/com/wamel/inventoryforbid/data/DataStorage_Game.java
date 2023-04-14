package com.wamel.inventoryforbid.data;

import org.bukkit.Material;

import java.util.HashMap;

public class DataStorage_Game {

    public static HashMap<Material, Integer> targetedBlockMap = new HashMap<>();

    public static HashMap<Material, Integer> brokenBlockMap = new HashMap<>();

    public static HashMap<Material, Integer> releasedBlockMap = new HashMap<>();

}
