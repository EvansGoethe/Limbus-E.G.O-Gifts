package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class EbonyBrooch extends BaseAccessory {
    public EbonyBrooch(LimbusEGOGift plugin) {
        super(plugin, "ebony_brooch", "黑檀胸針", "&7被動：夜視；夜晚獲得速度 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 30, 0, true, false));
        long time = player.getWorld().getTime();
        if (time >= 13000 && time <= 23000) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 0, true, false));
        }
    }
}