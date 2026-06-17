package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class HandheldMirror extends BaseAccessory {
    public HandheldMirror(LimbusEGOGift plugin) {
        super(plugin, "handheld_mirror", "手鏡", "&7將 30% 近戰傷害反彈給攻擊者");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        if (!(event.getDamager() instanceof LivingEntity attacker)) return;
        double m = plugin.getUpgradeMultiplier(victim, getId());
        double reflected = event.getDamage() * 0.30 * m;
        attacker.damage(reflected);
    }
}
