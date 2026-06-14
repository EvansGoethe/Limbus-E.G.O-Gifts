package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class StrangeGlyphTalisman extends BaseAccessory {
    public StrangeGlyphTalisman(LimbusEGOGift plugin) {
        super(plugin, "strange_glyph_talisman", "異文符咒", "&7被動：所有傷害減少 15%");
    }
    @Override public void onDamaged(EntityDamageByEntityEvent event, Player victim) {
        event.setDamage(event.getDamage() * 0.85);
    }
}