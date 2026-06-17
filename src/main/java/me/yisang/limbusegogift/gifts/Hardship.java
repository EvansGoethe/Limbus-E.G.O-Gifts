package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class Hardship extends BaseAccessory {
    public Hardship(LimbusEGOGift plugin) {
        super(plugin, "hardship", "苦難",
                "&#9E9E41", "在這不虔敬的空間裡，連這是否算祈禱都不得而知。",
                "被動：抗性提升 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 30, 0, true, false));
    }
}
