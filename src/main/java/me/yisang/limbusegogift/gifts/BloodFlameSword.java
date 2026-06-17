package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class BloodFlameSword extends BaseAccessory {
    public BloodFlameSword(LimbusEGOGift plugin) {
        super(plugin, "bloodflame_sword", "血炎刀", "&7近戰攻擊點燃目標 3 秒");
    }
    @Override public void onAttack(EntityDamageByEntityEvent event, Player attacker) {
        double m = plugin.getUpgradeMultiplier(attacker, getId());
        if (event.getEntity() instanceof LivingEntity target)
            target.setFireTicks((int)(60 * m));
    }
}
