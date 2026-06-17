package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class SpicebushBranch extends BaseAccessory {
    public SpicebushBranch(LimbusEGOGift plugin) {
        super(plugin, "spicebush_branch", "檀香梅枝",
                "&#C8A070", "我看不見花朵，可我聞到花香。花香盛放，我在那兒挖起墳墓。",
                "被動：中毒時轉化為回血效果");
    }
    @Override public void onPassiveTick(Player player) {
        if (player.hasPotionEffect(PotionEffectType.POISON)) {
            player.removePotionEffect(PotionEffectType.POISON);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 0, true, true));
        }
    }
}
