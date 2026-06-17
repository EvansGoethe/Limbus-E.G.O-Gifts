package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class MoonInTheWater extends BaseAccessory {
    public MoonInTheWater(LimbusEGOGift plugin) {
        super(plugin, "moon_in_the_water", "水中月",
                "&#8EC5F0", "枉凝眉。",
                "被動：夜視");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 30, 0, true, false));
    }
}
