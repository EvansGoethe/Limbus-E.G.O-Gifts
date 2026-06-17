package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class DistantStar extends BaseAccessory {
    public DistantStar(LimbusEGOGift plugin) {
        super(plugin, "distant_star", "彼方之星",
                "&#00DAFF", "追從著群星。",
                "被動：幸運 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 30, 0, true, false));
    }
}
