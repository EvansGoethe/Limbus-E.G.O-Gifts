package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class PieceOfATornSummer extends BaseAccessory {
    public PieceOfATornSummer(LimbusEGOGift plugin) {
        super(plugin, "piece_of_a_torn_summer", "破碎之夏的殘片", "&7受到火焰傷害時：獲得抗性提升 II 3 秒");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        DamageCause cause = event.getCause();
        if (cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK
                || cause == DamageCause.HOT_FLOOR || cause == DamageCause.LAVA) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 1, true, true));
        }
    }
}