package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class Sunshower extends BaseAccessory {
    public Sunshower(LimbusEGOGift plugin) {
        super(plugin, "sunshower", "狐雨", "&7雨天回血；晴天速度提升");
    }
    @Override public void onPassiveTick(Player player) {
        double m = plugin.getUpgradeMultiplier(player, getId());
        boolean raining = player.getWorld().hasStorm() || player.getWorld().isThundering();
        if (raining)
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, (int)(m - 1), true, false));
        else
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, (int)(m - 1), true, false));
    }
}
