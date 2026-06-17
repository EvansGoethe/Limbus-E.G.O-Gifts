package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class CaskSpirits extends BaseAccessory {
    public CaskSpirits(LimbusEGOGift plugin) {
        super(plugin, "cask_spirits", "桶裝烈酒",
                "&#C89040", "我從未想過沒有酒的生活。",
                "觸發（受傷）：力量 II 持續 3 秒");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        victim.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 60, 1, true, true));
    }
}
