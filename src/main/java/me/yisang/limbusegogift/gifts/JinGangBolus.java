package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class JinGangBolus extends BaseAccessory {
    public JinGangBolus(LimbusEGOGift plugin) {
        super(plugin, "jin_gang_bolus", "金剛丸", "&7被動：吸收 I，力量 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 30, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 30, 0, true, false));
    }
}