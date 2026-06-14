package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class ChiefButlersSecretArts extends BaseAccessory {
    public ChiefButlersSecretArts(LimbusEGOGift plugin) {
        super(plugin, "chief_butlers_secret_arts", "首席管家的秘籍", "&7被動：抗性提升 I，緩降");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 30, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 30, 0, true, false));
    }
}