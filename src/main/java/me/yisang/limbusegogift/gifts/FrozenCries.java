package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class FrozenCries extends BaseAccessory {
    public FrozenCries(LimbusEGOGift plugin) {
        super(plugin, "frozen_cries", "冰封的哀號",
                "&#4498DB", "不甘平凡。",
                "攻擊時：施加緩慢 III 2 秒");
    }
    @Override public void onAttack(EntityDamageByEntityEvent event, Player attacker) {
        if (event.getEntity() instanceof LivingEntity target) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 2, true, true));
        }
    }
}
