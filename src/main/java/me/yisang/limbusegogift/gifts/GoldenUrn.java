package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Objects;
public class GoldenUrn extends BaseAccessory {
    public GoldenUrn(LimbusEGOGift plugin) {
        super(plugin, "golden_urn", "金甕", "&7擊殺時：恢復 2 心並掉落金粒");
    }
    @Override public void onKill(EntityDeathEvent event, Player killer) {
        double max = Objects.requireNonNull(killer.getAttribute(Attribute.MAX_HEALTH)).getValue();
        killer.setHealth(Math.min(killer.getHealth() + 4.0, max));
        event.getEntity().getWorld().dropItemNaturally(
            event.getEntity().getLocation(), new ItemStack(Material.GOLD_NUGGET));
    }
}