package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class MentalCorruptionBoostingGas extends BaseAccessory {
    public MentalCorruptionBoostingGas(LimbusEGOGift plugin) {
        super(plugin, "mental_corruption_boosting_gas", "精神汙染加速氣體", "&7攻擊時：30% 機率對目標與附近玩家施加反胃");
    }
    @Override public void onAttack(EntityDamageByEntityEvent event, Player attacker) {
        if (Math.random() < 0.30 && event.getEntity() instanceof LivingEntity target) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0, true, true));
            for (Player nearby : target.getLocation().getNearbyPlayers(5.0)) {
                if (!nearby.equals(attacker)) {
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 40, 0, true, true));
                }
            }
        }
    }
}