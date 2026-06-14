package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Objects;
public class Oracle extends BaseAccessory {
    public Oracle(LimbusEGOGift plugin) {
        super(plugin, "oracle", "神諭", "&7被動：血量低於 40% 時使附近生物顯現");
    }
    @Override public void onPassiveTick(Player player) {
        double max = Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue();
        if (player.getHealth() < max * 0.4) {
            for (Entity e : player.getNearbyEntities(8, 8, 8)) {
                if (e instanceof LivingEntity living && !(e instanceof Player)) {
                    living.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 0, true, false));
                }
            }
        }
    }
}