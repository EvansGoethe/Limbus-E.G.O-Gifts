package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class MaskOfTheParade extends BaseAccessory {
    public MaskOfTheParade(LimbusEGOGift plugin) {
        super(plugin, "mask_of_the_parade", "遊行的面具", "&7受傷時：潛行狀態下獲得隱身 3 秒");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        if (victim.isSneaking()) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 0, true, true));
        }
    }
}