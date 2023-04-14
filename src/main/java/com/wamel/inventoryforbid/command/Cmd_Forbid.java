package com.wamel.inventoryforbid.command;

import com.wamel.inventoryforbid.data.DataStorage_Config;
import com.wamel.inventoryforbid.data.DataStorage_Game;
import com.wamel.inventoryforbid.event.InventoryShareEvent;
import com.wamel.inventoryforbid.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Cmd_Forbid implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("");
            sender.sendMessage("   §b/blist §7- 파괴하여 슬롯을 해금한 블럭의 목록을 확인합니다.");
            sender.sendMessage("   §b/b §7- 바라보고 있는 블럭의 파괴 여부를 확인합니다.");
            sender.sendMessage("");
            if (sender.isOp()) {
                sender.sendMessage("   §9/forbid start §7- 게임을 시작합니다.");
                sender.sendMessage("   §9/forbid stop §7- 게임을 종료합니다.");
                sender.sendMessage("   §9/forbid list §7- 부숴야하는 블럭 목록을 확인합니다.");
                sender.sendMessage("");
            }
            return false;
        }
        if (args[0].equalsIgnoreCase("start")) {
            if (!sender.isOp()) {
                sender.sendMessage("§c권한이 부족합니다.");
                return false;
            }
            if (DataStorage_Config.ENABLE == true) {
                sender.sendMessage("§c이미 게임이 진행 중입니다.");
                return false;
            }
            if (Bukkit.getOnlinePlayers().size() == 0) {
                sender.sendMessage("§c현재 서버에 아무도 사람이 없어 게임을 시작할 수 없습니다.");
                return false;
            }

            DataStorage_Config.ENABLE = true;

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("     §9§l인벤토리 금지 by WaMel_");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("   §f목표: §c블럭들을 파괴하여 봉인된 인벤토리의 슬롯들을 모두 해방하세요!");
            Bukkit.broadcastMessage("   §7자세한 명령어는 '/forbid'를 참고하세요.");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("   §b/blist §7- 파괴하여 슬롯을 해금한 블럭의 목록을 확인합니다.");
            Bukkit.broadcastMessage("   §b/b §7- 바로보고 있는 블럭의 파괴 여부를 확인합니다.");
            Bukkit.broadcastMessage("");

            Random random = new Random();

            Player rPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[random.nextInt(Bukkit.getOnlinePlayers().size())];

            InventoryShareEvent.patch(rPlayer);

            for (int i=0; i<rPlayer.getInventory().getSize(); i++) {
                rPlayer.getInventory().setItem(i, new ItemStack(Material.BARRIER));
            }

            ArrayList liberatedSlots = new ArrayList();
            liberatedSlots.add(0);
            rPlayer.getInventory().setItem(0, new ItemStack(Material.AIR));

            for (int i=0; i<DataStorage_Config.START_SLOTS - 1; i++) {
                Integer slot = random.nextInt(rPlayer.getInventory().getSize());

                while (liberatedSlots.contains(slot)) {
                    slot = random.nextInt(rPlayer.getInventory().getSize());
                }

                liberatedSlots.add(slot);

                rPlayer.getInventory().setItem(slot, new ItemStack(Material.AIR));
            }

            DataStorage_Game.targetedBlockMap = new HashMap<>();

            ArrayList<Material> ignoredBlocks = new ArrayList() {{
                add(Material.CAVE_AIR);
                add(Material.VOID_AIR);
                add(Material.AIR);
                add(Material.COMMAND_BLOCK);
                add(Material.CHAIN_COMMAND_BLOCK);
                add(Material.REPEATING_COMMAND_BLOCK);
                add(Material.NETHER_PORTAL);
                add(Material.END_PORTAL);
                add(Material.END_PORTAL_FRAME);
                add(Material.LAVA);
                add(Material.WATER);
                add(Material.BARRIER);
                add(Material.STRUCTURE_BLOCK);
                add(Material.STRUCTURE_VOID);
                add(Material.BEDROCK);
            }};

            int lockedSlots = rPlayer.getInventory().getSize() - DataStorage_Config.START_SLOTS;
            for (int i=0; i<lockedSlots; i++) {
                Material block = Material.values()[random.nextInt(Material.values().length)];

                while (!block.isBlock() || ignoredBlocks.contains(block) || DataStorage_Game.targetedBlockMap.containsKey(block)) {
                    block = Material.values()[random.nextInt(Material.values().length)];

                    while (block.toString().toLowerCase().contains("cherry") || block.toString().toLowerCase().contains("hanging")) {
                        block = Material.values()[random.nextInt(Material.values().length)];
                    }
                }

                Integer slot = random.nextInt(rPlayer.getInventory().getSize());

                while (liberatedSlots.contains(slot) || DataStorage_Game.targetedBlockMap.values().contains(slot)) {
                    slot = random.nextInt(rPlayer.getInventory().getSize());
                }

                DataStorage_Game.targetedBlockMap.put(block, slot);
            }

            for(Player player : Bukkit.getOnlinePlayers()) {
                Util.sendTitleWithSound(player, "§c§l인벤토리 금지", "§f인벤토리에서 §c" + lockedSlots + "§f개의 슬롯이 봉인되었습니다...", 0, 60, 0, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 100, 1);
                InventoryShareEvent.patch(player);
            }
        }
        if (args[0].equalsIgnoreCase("stop")) {
            if (!sender.isOp()) {
                sender.sendMessage("§c권한이 부족합니다.");
                return false;
            }
            if (DataStorage_Config.ENABLE == false) {
                sender.sendMessage("§c게임이 진행 중이지 않습니다.");
                return false;
            }
            if (Bukkit.getOnlinePlayers().size() == 0) {
                sender.sendMessage("§c현재 서버에 아무도 사람이 없어 게임을 종료할 수 없습니다.");
                return false;
            }

            DataStorage_Config.ENABLE = false;

            Random random = new Random();

            Player rPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[random.nextInt(Bukkit.getOnlinePlayers().size())];

            for (int i=0; i<rPlayer.getInventory().getSize(); i++) {
                rPlayer.getInventory().setItem(i, new ItemStack(Material.AIR));
            }

            DataStorage_Game.targetedBlockMap.clear();
            DataStorage_Game.releasedBlockMap.clear();
            DataStorage_Game.brokenBlockMap.clear();

            for (Player player : Bukkit.getOnlinePlayers()) {
                try {
                    InventoryShareEvent.unPatch(player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("     §9§l인벤토리 금지 by WaMel_");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("   §f게임이 종료되었습니다.");
            Bukkit.broadcastMessage("");
        }
        if (args[0].equalsIgnoreCase("list")) {
            if (!sender.isOp()) {
                sender.sendMessage("§c권한이 부족합니다.");
                return false;
            }
            if (DataStorage_Config.ENABLE == false) {
                sender.sendMessage("§c게임이 진행 중이지 않습니다.");
                return false;
            }

            sender.sendMessage("§6--- 봉인된 슬롯과 그 블럭의 목록 §6---");

            ArrayList<Integer> slots = new ArrayList<>(DataStorage_Game.targetedBlockMap.values().stream().toList());
            Collections.sort(slots);

            for (Integer slot : slots) {
                for (Material block : DataStorage_Game.targetedBlockMap.keySet()) {
                    if (DataStorage_Game.targetedBlockMap.get(block) == slot) {
                        sender.sendMessage("§e[" + slot + "] " + block.toString() + "");
                        break;
                    }
                }
            }

        }
        return false;
    }

}
