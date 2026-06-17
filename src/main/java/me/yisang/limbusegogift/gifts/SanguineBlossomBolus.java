package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class SanguineBlossomBolus extends BaseAccessory {
    private final Map<UUID, Long> lastDamaged = new HashMap<>();
    public SanguineBlossomBolus(LimbusEGOGift plugin) {
        super(plugin, "sanguine_blossom_bolus", "血花丸", "&7非戰鬥時持續緩慢回血");
    }
    @Override public void onAnyDamage(EntityDamageEvent event, Player victim) {
        lastDamaged.put(victim.getUniqueId(), System.currentTimeMillis());
    }
    @Override public void onPassiveTick(Player player) {
        long last = lastDamaged.getOrDefault(player.getUniqueId(), 0L);
        if (System.currentTimeMillis() - last < 5000) return;
        double m = plugin.getUpgradeMultiplier(player, getId());
        int level = m >= 2.0 ? 1 : 0;
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, level, true, false));
    }
}
