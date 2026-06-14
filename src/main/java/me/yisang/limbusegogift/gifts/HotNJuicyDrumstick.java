package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class HotNJuicyDrumstick extends BaseAccessory {
    public HotNJuicyDrumstick(LimbusEGOGift plugin) {
        super(plugin, "hot_n_juicy_drumstick", "火熱多汁琵琶腿", "&7被動：不消耗飢餓值，力量 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 30, 1, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 30, 0, true, false));
    }
}