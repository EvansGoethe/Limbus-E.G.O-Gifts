package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class TheBookOfVengeance extends BaseAccessory {
    public TheBookOfVengeance(LimbusEGOGift plugin) {
        super(plugin, "the_book_of_vengeance", "復仇帳簿", "&7受傷時：獲得力量 II 3 秒");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        victim.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 60, 1, true, true));
    }
}