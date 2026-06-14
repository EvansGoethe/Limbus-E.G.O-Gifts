package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Objects;
public class Finifugality extends BaseAccessory {
    public Finifugality(LimbusEGOGift plugin) {
        super(plugin, "finifugality", "留戀", "&7受傷時：血量低於 30% 觸發速度 II 急迫 I");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        double max = Objects.requireNonNull(victim.getAttribute(Attribute.MAX_HEALTH)).getValue();
        if (victim.getHealth() < max * 0.3) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, true, true));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 100, 0, true, true));
        }
    }
}