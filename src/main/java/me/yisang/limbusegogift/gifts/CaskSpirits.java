package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class CaskSpirits extends BaseAccessory {
    public CaskSpirits(LimbusEGOGift plugin) {
        super(plugin, "cask_spirits", "桶裝烈酒", "&7被動：生命再生 I；攻擊時偶有反效果");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 0, true, false));
    }
    @Override public void onAttack(EntityDamageByEntityEvent event, Player attacker) {
        if (Math.random() < 0.10) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 0, true, true));
        }
    }
}