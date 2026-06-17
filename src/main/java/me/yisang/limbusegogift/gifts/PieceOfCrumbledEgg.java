package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
public class PieceOfCrumbledEgg extends BaseAccessory {
    public PieceOfCrumbledEgg(LimbusEGOGift plugin) {
        super(plugin, "piece_of_crumbled_egg", "破碎之卵的殘片",
                "&#33CF4F", "綻放的九人會。",
                "死亡時：對殺手落雷");
    }
    @Override public void onDeath(PlayerDeathEvent event, Player deceased) {
        Player killer = deceased.getKiller();
        if (killer != null) {
            deceased.getWorld().strikeLightning(killer.getLocation());
        }
    }
}
