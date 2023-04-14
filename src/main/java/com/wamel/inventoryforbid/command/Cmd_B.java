package com.wamel.inventoryforbid.command;

import com.wamel.inventoryforbid.data.DataStorage_Config;
import com.wamel.inventoryforbid.data.DataStorage_Game;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Cmd_B implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (DataStorage_Config.ENABLE == false) {
            sender.sendMessage("§c게임이 진행 중이지 않습니다.");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c콘솔에선 실행할 수 없는 명령어입니다.");
            return false;
        }

        Player player = (Player) sender;

        Set set = new HashSet() {{
            add(Material.AIR);
            add(Material.WATER);
            add(Material.LAVA);
            add(Material.CAVE_AIR);
            add(Material.VOID_AIR);
        }};

        if (player.getTargetBlock(set, 10) == null) {
            sender.sendMessage("§c타겟이 없습니다!");
            return false;
        }

        Material block = player.getTargetBlock(set, 10).getType();

        if (DataStorage_Game.brokenBlockMap.containsKey(block)) {
            sender.sendMessage("§c" + block.toString() + "§6(은)는 파괴한 적 있는 블럭입니다. §7(" + DataStorage_Game.brokenBlockMap.get(block) + "번 파괴함)");
        } else {
            sender.sendMessage("§c" + block.toString() + "§6(은)는 파괴한 적 없는 블럭입니다!");
        }
        return false;
    }

}
