package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class DryToTheBoneBreast extends BaseAccessory {
    public DryToTheBoneBreast(LimbusEGOGift plugin) {
        super(plugin, "dry_to_the_bone_breast", "乾巴柴澀雞胸肉", "&7被動：不消耗飢餓值");
    }
    @Override public void onPassiveTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 30, 1, true, false));
    }
}