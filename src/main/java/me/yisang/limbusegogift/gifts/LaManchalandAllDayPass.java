package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class LaManchalandAllDayPass extends BaseAccessory {
    public LaManchalandAllDayPass(LimbusEGOGift plugin) {
        super(plugin, "la_manchaland_all_day_pass", "拉．曼查樂園自由通行證",
                "&#FCD05C", "代替我，與那孩子一同追尋夢想吧。",
                "被動：速度 I，跳躍提升 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 30, 0, true, false));
    }
}
