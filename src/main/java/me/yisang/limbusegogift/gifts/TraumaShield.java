package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class TraumaShield extends BaseAccessory {
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    public TraumaShield(LimbusEGOGift plugin) {
        super(plugin, "trauma_shield", "精神遮蔽力場", "&7受傷時：每 60 秒吸收一次傷害");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        long now = System.currentTimeMillis();
        if (now - cooldowns.getOrDefault(victim.getUniqueId(), 0L) >= 60_000L) {
            cooldowns.put(victim.getUniqueId(), now);
            event.setDamage(0);
        }
    }
}