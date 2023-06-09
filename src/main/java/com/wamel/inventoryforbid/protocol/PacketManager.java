package com.wamel.inventoryforbid.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.wamel.inventoryforbid.InventoryForbid;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.HashSet;
import java.util.Set;

public class PacketManager {

    static Set<Player> breakingBlock = new HashSet<>();
    public static void register(InventoryForbid plugin) {
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();

        pm.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (breakingBlock.contains(event.getPlayer())) {
                    var slot = event.getPacket().getModifier().read(2);
                    if ((event.getPlayer().getInventory().getHeldItemSlot() + 36) == ((int) slot)) {
                        ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
                        if ((handItem.hasItemMeta() && handItem.getItemMeta() instanceof Damageable && ((Damageable) handItem.getItemMeta()).getDamage() > 0)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        });

        pm.addPacketListener(new PacketAdapter(plugin,
                ListenerPriority.NORMAL,
                PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                var Status = event.getPacket().getModifier().read(2);
                try {
                    if (Status.toString().equals("ABORT_DESTROY_BLOCK")) {
                        breakingBlock.remove(event.getPlayer());
                        event.getPlayer().updateInventory();
                    }
                    if (Status.toString().equals("START_DESTROY_BLOCK")) {
                        breakingBlock.add(event.getPlayer());
                    }
                    if (Status.toString().equals("STOP_DESTROY_BLOCK")) {
                        breakingBlock.remove(event.getPlayer());
                        event.getPlayer().updateInventory();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

    }
}
