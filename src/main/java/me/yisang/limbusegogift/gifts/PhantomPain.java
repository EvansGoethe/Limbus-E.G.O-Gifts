package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class PhantomPain extends BaseAccessory {
    public PhantomPain(LimbusEGOGift plugin) {
        super(plugin, "phantom_pain", "幻痛",
                "&#656565", "他試圖抓住伸去的手，但沒能抓住。",
                "受傷時：25% 機率減少 70% 傷害");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        if (Math.random() < 0.25) {
            event.setDamage(event.getDamage() * 0.3);
        }
    }
}
