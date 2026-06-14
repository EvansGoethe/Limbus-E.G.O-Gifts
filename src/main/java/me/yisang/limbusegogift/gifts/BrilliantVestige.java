package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class BrilliantVestige extends BaseAccessory {
    public BrilliantVestige(LimbusEGOGift plugin) {
        super(plugin, "brilliant_vestige", "輝煌的殘影", "&7被動：夜視；附近生物顯現");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 30, 0, true, false));
        for (Entity e : player.getNearbyEntities(8, 8, 8)) {
            if (e instanceof LivingEntity living && !(e instanceof Player)) {
                living.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 30, 0, true, false));
            }
        }
    }
}