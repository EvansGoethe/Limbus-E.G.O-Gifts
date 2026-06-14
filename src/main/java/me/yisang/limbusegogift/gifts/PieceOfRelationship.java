package me.yisang.limbusegogift.gifts;
import me.yisang.limbusegogift.BaseAccessory;
import me.yisang.limbusegogift.LimbusEGOGift;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class PieceOfRelationship extends BaseAccessory {
    public PieceOfRelationship(LimbusEGOGift plugin) {
        super(plugin, "piece_of_relationship", "緣分殘片", "&7被動：對 5 格內的玩家施加生命再生 I");
    }
    @Override public void onPassiveTick(Player player) {
        for (Player nearby : player.getLocation().getNearbyPlayers(5.0)) {
            if (!nearby.equals(player)) {
                nearby.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 0, true, false));
            }
        }
    }
}