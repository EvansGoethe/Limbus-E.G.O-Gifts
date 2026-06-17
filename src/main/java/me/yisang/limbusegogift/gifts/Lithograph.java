package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class Lithograph extends BaseAccessory {
    public Lithograph(LimbusEGOGift plugin) {
        super(plugin, "lithograph", "石板字符",
                "&#169876", "審判……定罪……懲戒……",
                "被動：所有傷害減少 10%");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        event.setDamage(event.getDamage() * 0.9);
    }
}
