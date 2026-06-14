package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class CQCManual extends BaseAccessory {
    public CQCManual(LimbusEGOGift plugin) {
        super(plugin, "cqc_manual", "近身格鬥手冊", "&7被動：力量 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 30, 0, true, false));
    }
}