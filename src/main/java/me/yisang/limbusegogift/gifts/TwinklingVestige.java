package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class TwinklingVestige extends BaseAccessory {
    public TwinklingVestige(LimbusEGOGift plugin) {
        super(plugin, "twinkling_vestige", "閃耀的殘影", "&7被動：生命再生 II");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 1, true, false));
    }
}