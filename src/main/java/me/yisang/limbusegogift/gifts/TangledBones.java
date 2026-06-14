package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class TangledBones extends BaseAccessory {
    public TangledBones(LimbusEGOGift plugin) {
        super(plugin, "tangled_bones", "碎裂的骨片", "&7攻擊時：對不死生物傷害翻倍；25% 點燃");
    }
    @Override public void onAttack(EntityDamageByEntityEvent event, Player attacker) {
        Entity target = event.getEntity();
        if (isUndead(target)) {
            event.setDamage(event.getDamage() * 2.0);
            if (Math.random() < 0.25 && target instanceof LivingEntity le) {
                le.setFireTicks(60);
            }
        }
    }
    private boolean isUndead(Entity e) {
        return e instanceof Zombie || e instanceof Skeleton || e instanceof WitherSkeleton
            || e instanceof Phantom || e instanceof Drowned || e instanceof Stray
            || e instanceof Husk || e instanceof ZombieVillager || e instanceof Wither
            || e instanceof Zoglin;
    }
}