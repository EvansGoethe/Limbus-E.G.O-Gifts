package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class TranquilLotusBolus extends BaseAccessory {
    public TranquilLotusBolus(LimbusEGOGift plugin) {
        super(plugin, "tranquil_lotus_bolus", "靜蓮丸", "&7被動：抗性提升 I，緩慢 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 30, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 30, 0, true, false));
    }
}