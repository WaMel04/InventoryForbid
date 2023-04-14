package com.wamel.inventoryforbid.command;

import com.wamel.inventoryforbid.data.DataStorage_Config;
import com.wamel.inventoryforbid.data.DataStorage_Game;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Array;
import java.util.*;

public class Cmd_Blist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (DataStorage_Config.ENABLE == false) {
            sender.sendMessage("§c게임이 진행 중이지 않습니다.");
            return false;
        }

        sender.sendMessage("§6--- 파괴하여 슬롯의 봉인을 해제한 블럭의 목록 §6---");

        ArrayList<Integer> slots = new ArrayList<>(DataStorage_Game.releasedBlockMap.values().stream().toList());
        Collections.sort(slots);

        for (Integer slot : slots) {
            for (Material block : DataStorage_Game.releasedBlockMap.keySet()) {
                if (DataStorage_Game.releasedBlockMap.get(block) == slot) {
                    sender.sendMessage("§e[" + slot + "] " + block.toString() + " §a(완료)");
                    break;
                }
            }
        }

        return false;
    }

}
