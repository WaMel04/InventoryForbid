package com.wamel.inventoryforbid.event;

import com.wamel.inventoryforbid.data.DataStorage_Config;
import com.wamel.inventoryforbid.data.DataStorage_Game;
import com.wamel.inventoryforbid.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MinecraftEvent implements Listener {

    // 폭죽 피해 제거
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager().hasMetadata("noDamage")) {
            event.setCancelled(true);
            event.setDamage(0);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!DataStorage_Config.ENABLE)
            return;
        if (event.getPlayer() == null)
            return;
        if (event.getBlock().getType().equals(Material.BARRIER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        event.getPlayer().updateInventory();
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!DataStorage_Config.ENABLE)
            return;
        if (event.getPlayer() == null)
            return;
        if (event.getItemDrop().getItemStack().getType().equals(Material.BARRIER))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!DataStorage_Config.ENABLE)
            return;

        Player player = event.getEntity();
        event.setKeepInventory(true);
        event.getDrops().clear();

        for (int i=0; i<player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null) {
                if (!player.getInventory().getItem(i).getType().equals(Material.BARRIER)) {
                    ItemStack item = player.getInventory().getItem(i);

                    player.getInventory().setItem(i, null);

                    event.getDrops().add(item.clone());
                }
            }
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        if (!DataStorage_Config.ENABLE)
            return;

        if (event.getMainHandItem().getType().equals(Material.BARRIER) || event.getOffHandItem().getType().equals(Material.BARRIER))
            event.setCancelled(true);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!DataStorage_Config.ENABLE)
            return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.updateInventory();
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!DataStorage_Config.ENABLE)
            return;

        if (event.getCursor() == null)
            return;
        if (event.getCursor().getType().equals(Material.BARRIER))
            event.setCancelled(true);

        if (event.getCurrentItem() == null)
            return;
        if (event.getCurrentItem().getType().equals(Material.BARRIER))
            event.setCancelled(true);

        if (event.getHotbarButton() == -1)
            return;
        if (event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) == null)
            return;
        if (event.getWhoClicked().getInventory().getItem(event.getHotbarButton()).getType().equals(Material.BARRIER))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!DataStorage_Config.ENABLE)
            return;
        if (event.getPlayer() == null)
            return;

        Player player = event.getPlayer();
        Material block = event.getBlock().getType();

        if (DataStorage_Game.targetedBlockMap.containsKey(block)) {
            Integer slot = DataStorage_Game.targetedBlockMap.get(block);

            DataStorage_Game.targetedBlockMap.remove(block);
            DataStorage_Game.releasedBlockMap.put(block, slot);

            ItemStack apple = new ItemStack(Material.GOLDEN_APPLE,1);
            ItemMeta meta = apple.getItemMeta();

            meta.setDisplayName("§6새로운 슬롯 해제!");
            apple.setItemMeta(meta);

            player.getInventory().setItem(slot, apple);
            Util.spawnFirework(event.getBlock().getLocation());
            Bukkit.broadcastMessage("§c" + player.getName() + "§6님이 §a" + block.toString() + "§6(을)를 파괴하여 §b" + slot + "번 슬롯§6의 봉인을 해제했습니다!");

            if (DataStorage_Game.targetedBlockMap.size() == 0) {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    Util.sendTitleWithSound(player1, "§a§lClear", "§f모든 슬롯의 봉인을 해제했습니다.", 20, 20*5, 20, Sound.UI_TOAST_CHALLENGE_COMPLETE, 100, 1);
                }
            }
        }
        if (DataStorage_Game.brokenBlockMap.containsKey(block)) {
            DataStorage_Game.brokenBlockMap.put(block, DataStorage_Game.brokenBlockMap.get(block) + 1);
        } else {
            DataStorage_Game.brokenBlockMap.put(block, 1);
        }
    }



}
