package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class BlackSheetMusic extends BaseAccessory {
    public BlackSheetMusic(LimbusEGOGift plugin) {
        super(plugin, "black_sheet_music", "黑色樂譜", "&7被動：急迫 I");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 30, 0, true, false));
    }
}