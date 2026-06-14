package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class SmokingGunpowder extends BaseAccessory {
    public SmokingGunpowder(LimbusEGOGift plugin) {
        super(plugin, "smoking_gunpowder", "有煙火藥", "&7攻擊時：25% 機率在目標位置爆炸");
    }
    @Override public void onAttack(EntityDamageByEntityEvent event, Player attacker) {
        if (Math.random() < 0.25) {
            event.getEntity().getWorld().createExplosion(
                event.getEntity().getLocation(), 1.5f, false, false);
        }
    }
}