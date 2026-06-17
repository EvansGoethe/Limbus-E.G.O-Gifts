package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class PainOfStifledRage extends BaseAccessory {
    public PainOfStifledRage(LimbusEGOGift plugin) {
        super(plugin, "pain_of_stifled_rage", "鬱火",
                "&#DA3D24", "公牛絕望地咆哮著……",
                "受傷時：獲得力量 I 5 秒");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        victim.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 100, 0, true, true));
    }
}
