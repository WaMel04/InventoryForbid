package com.wamel.inventoryforbid.util;

import com.wamel.inventoryforbid.InventoryForbid;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class Util {

    private static InventoryForbid plugin = InventoryForbid.getInstance();

    public static void sendTitleWithSound(Player player, String title, String subtitle, int fadeIn, int tick, int fadeOut, Sound sound, int volume, float pitch) {
        player.sendTitle(title, subtitle, fadeIn, tick, fadeOut);
        player.playSound(player, sound, volume, pitch);
    }

    public static void spawnFirework(Location loc) {
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwMeta = fw.getFireworkMeta();

        fwMeta.setPower(1);
        fwMeta.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).flicker(true).with(FireworkEffect.Type.BALL).build());
        fwMeta.addEffect(FireworkEffect.builder().withColor(Color.WHITE).flicker(true).with(FireworkEffect.Type.BALL).build());

        fw.setFireworkMeta(fwMeta);
        fw.setMetadata("noDamage", new FixedMetadataValue(plugin, true));
    }

}
