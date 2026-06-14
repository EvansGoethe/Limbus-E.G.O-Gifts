package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class ClearMirrorCalmWater extends BaseAccessory {
    public ClearMirrorCalmWater(LimbusEGOGift plugin) {
        super(plugin, "clear_mirror_calm_water", "明鏡止水", "&7受傷時：25% 機率減少 50% 傷害");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        if (Math.random() < 0.25) {
            event.setDamage(event.getDamage() * 0.5);
        }
    }
}