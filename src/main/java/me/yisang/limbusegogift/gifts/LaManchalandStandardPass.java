package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class LaManchalandStandardPass extends BaseAccessory {
    public LaManchalandStandardPass(LimbusEGOGift plugin) {
        super(plugin, "la_manchaland_standard_pass", "拉·曼查樂園常規通行券", "&7被動：速度 I，幸運 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 30, 0, true, false));
    }
}