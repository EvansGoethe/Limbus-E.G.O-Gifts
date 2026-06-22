package me.yisang.limbusegogift;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class GachaListener implements Listener {

    private static final int LUNACY_COST = 32;
    private static final double TIER4_CHANCE = 0.10;

    private final LimbusEGOGift plugin;
    private final GachaChestManager chestMgr;
    private final SpindleChestManager spindleMgr;
    private final NamespacedKey LUNACY_KEY;
    private final NamespacedKey SPINDLE_KEY;
    private final Random rng = new Random();

    public GachaListener(LimbusEGOGift plugin, GachaChestManager chestMgr, SpindleChestManager spindleMgr) {
        this.plugin = plugin;
        this.chestMgr = chestMgr;
        this.spindleMgr = spindleMgr;
        this.LUNACY_KEY = new NamespacedKey(plugin, "lunacy");
        this.SPINDLE_KEY = new NamespacedKey(plugin, "spindle");
    }

    // ── 紡錘代幣 ──────────────────────────────────────────────────────────────

    public ItemStack createSpindle(int amount) {
        ItemStack item = new ItemStack(Material.ECHO_SHARD, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.color("&#FFE5A0紡錘"));
        meta.setLore(List.of(plugin.color("&#FFE5A0將所有可能性如絲線般紡織在一起的物品。")));
        meta.setItemModel(new NamespacedKey("gifts", "spindle"));
        meta.getPersistentDataContainer().set(SPINDLE_KEY, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isSpindle(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(SPINDLE_KEY, PersistentDataType.BYTE);
    }

    private int countSpindle(Player player) {
        int total = 0;
        for (ItemStack i : player.getInventory().getContents()) {
            if (isSpindle(i)) total += i.getAmount();
        }
        return total;
    }

    private void removeSpindle(Player player, int amount) {
        int remaining = amount;
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length && remaining > 0; i++) {
            ItemStack item = contents[i];
            if (!isSpindle(item)) continue;
            if (item.getAmount() <= remaining) {
                remaining -= item.getAmount();
                contents[i] = null;
            } else {
                item.setAmount(item.getAmount() - remaining);
                remaining = 0;
            }
        }
        player.getInventory().setContents(contents);
    }

    // ── 狂氣物品 ──────────────────────────────────────────────────────────────

    public ItemStack createLunacy(int amount) {
        ItemStack item = new ItemStack(Material.WITHER_ROSE, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(plugin.color("&#FF0000狂氣"));
        meta.setLore(List.of(plugin.color("&#FF0000人因心懷狂氣，才有前進的動力")));
        meta.setItemModel(new NamespacedKey("gifts", "lunacy"));
        meta.getPersistentDataContainer().set(LUNACY_KEY, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isLunacy(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(LUNACY_KEY, PersistentDataType.BYTE);
    }

    // ── 阻止狂氣種花 ──────────────────────────────────────────────────────────

    @EventHandler
    public void onLunacyPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!isLunacy(event.getItem())) return;
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) return;
        event.setCancelled(true);
    }

    // ── 右鍵箱子觸發 ─────────────────────────────────────────────────────────

    @EventHandler
    public void onChestClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        if (!chestMgr.isGachaChest(event.getClickedBlock().getLocation())) return;
        event.setCancelled(true);

        Player player = event.getPlayer();
        int lunacyCount = countLunacy(player);

        if (lunacyCount < LUNACY_COST) {
            player.sendMessage(plugin.color("&#FF5555狂氣不足！需要 " + LUNACY_COST + " 個，你只有 " + lunacyCount + " 個。"));
            return;
        }

        removeLunacy(player, LUNACY_COST);
        Accessory result = draw(player);

        if (result == null) {
            player.sendMessage(plugin.color("&#FF5555抽取失敗：飾品池為空。"));
            return;
        }

        boolean isTierIV = plugin.getTier(result.getId()) == 4;
        playDrawEffect(event.getClickedBlock().getLocation(), isTierIV);

        result.give(player);

        String tierColor = isTierIV ? "&#FFD700" : "&#FFFFFF";
        player.sendMessage(plugin.color("&#9928BB[飾品提取] &#AAAAAA獲得：" + tierColor + result.createItem().getItemMeta().getDisplayName()));
    }

    // ── 紡錘抽獎箱 ────────────────────────────────────────────────────────────

    @EventHandler
    public void onSpindleChestClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        Location chestLoc = event.getClickedBlock().getLocation();
        if (!spindleMgr.isSpindleChest(chestLoc)) return;

        Player player = event.getPlayer();
        boolean isAdmin = player.hasPermission("limbus.admin") || player.isOp();

        // 管理員潛行右鍵：照常開箱以編輯獎池
        if (isAdmin && player.isSneaking()) return;

        // 其餘一律不可開箱，改為抽取
        event.setCancelled(true);

        int cost = spindleMgr.getCost(chestLoc);
        int have = countSpindle(player);
        if (have < cost) {
            player.sendMessage(plugin.color("&#FF5555紡錘不足！需要 " + cost + " 個，你只有 " + have + " 個。"));
            return;
        }

        ItemStack prize = drawFromChest(event.getClickedBlock());
        if (prize == null) {
            player.sendMessage(plugin.color("&#FF5555此抽獎箱目前是空的。"));
            return;
        }

        removeSpindle(player, cost);
        playSpindleEffect(chestLoc);

        // 給獎（背包滿則掉落腳邊）
        Map<Integer, ItemStack> overflow = player.getInventory().addItem(prize);
        for (ItemStack left : overflow.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), left);
        }

        String boxName = spindleMgr.getName(chestLoc);
        String prizeName = (prize.hasItemMeta() && prize.getItemMeta().hasDisplayName())
                ? prize.getItemMeta().getDisplayName()
                : prize.getType().name();
        player.sendMessage(plugin.color("&#FFE5A0[" + boxName + "] &#AAAAAA獲得：&#FFFFFF" + prizeName));
    }

    /** 從箱子 inventory 依疊總數加權抽一件，回傳數量 1 的複本（箱內不減）。 */
    private ItemStack drawFromChest(org.bukkit.block.Block block) {
        if (!(block.getState() instanceof org.bukkit.block.Container container)) return null;
        List<ItemStack> slots = new ArrayList<>();
        int totalWeight = 0;
        for (ItemStack it : container.getInventory().getContents()) {
            if (it != null && !it.getType().isAir()) {
                slots.add(it);
                totalWeight += it.getAmount();
            }
        }
        if (slots.isEmpty() || totalWeight <= 0) return null;

        int roll = rng.nextInt(totalWeight);
        for (ItemStack it : slots) {
            roll -= it.getAmount();
            if (roll < 0) {
                ItemStack prize = it.clone();
                prize.setAmount(1);
                return prize;
            }
        }
        return null; // 理論上不會到這
    }

    private void playSpindleEffect(Location loc) {
        Location center = loc.clone().add(0.5, 1.0, 0.5);
        loc.getWorld().spawnParticle(Particle.END_ROD, center, 70, 0.4, 0.5, 0.4, 0.04);
        loc.getWorld().spawnParticle(Particle.GLOW, center, 30, 0.35, 0.45, 0.35, 0.02);
        loc.getWorld().spawnParticle(Particle.FLASH, center, 1);
        loc.getWorld().playSound(center, Sound.BLOCK_BEACON_ACTIVATE, 0.7f, 1.6f);
        loc.getWorld().playSound(center, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.6f, 1.4f);
    }

    // ── 抽取邏輯 ──────────────────────────────────────────────────────────────

    private Accessory draw(Player player) {
        int tier = pickTier();
        List<Accessory> pool = plugin.getAllAccessories().stream()
            .filter(a -> plugin.getTier(a.getId()) == tier && !plugin.isVestige(a.getId()))
            .collect(Collectors.toList());

        if (pool.isEmpty()) return null;

        Accessory result = pool.get(new Random().nextInt(pool.size()));

        // 抽到重複（背包或飾品欄已持有）→ 改給對應等級殘影
        if (playerHasInInventory(player, result.getId()) || playerHasEquipped(player, result.getId())) {
            return getVestigeForTier(tier);
        }

        return result;
    }

    private int pickTier() {
        double roll = Math.random();
        if (roll < TIER4_CHANCE) return 4;
        roll -= TIER4_CHANCE;
        double each = (1.0 - TIER4_CHANCE) / 3.0;
        if (roll < each) return 1;
        if (roll < each * 2) return 2;
        return 3;
    }

    private Accessory getVestigeForTier(int tier) {
        String id = switch (tier) {
            case 1 -> "dark_vestige";
            case 2 -> "faint_vestige";
            case 3 -> "twinkling_vestige";
            default -> "brilliant_vestige";
        };
        return plugin.getAccessory(id);
    }

    // ── 背包與飾品欄掃描 ──────────────────────────────────────────────────────

    private boolean playerHasInInventory(Player player, String id) {
        for (ItemStack i : player.getInventory().getContents()) {
            if (id.equals(plugin.getAccessoryId(i))) return true;
        }
        return false;
    }

    private boolean playerHasEquipped(Player player, String id) {
        return plugin.getEquippedAccessories(player).stream().anyMatch(a -> a.getId().equals(id));
    }

    // ── 狂氣計算 ─────────────────────────────────────────────────────────────

    private int countLunacy(Player player) {
        int total = 0;
        for (ItemStack i : player.getInventory().getContents()) {
            if (isLunacy(i)) total += i.getAmount();
        }
        return total;
    }

    private void removeLunacy(Player player, int amount) {
        int remaining = amount;
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length && remaining > 0; i++) {
            ItemStack item = contents[i];
            if (!isLunacy(item)) continue;
            if (item.getAmount() <= remaining) {
                remaining -= item.getAmount();
                contents[i] = null;
            } else {
                item.setAmount(item.getAmount() - remaining);
                remaining = 0;
            }
        }
        player.getInventory().setContents(contents);
    }

    // ── 粒子效果 ─────────────────────────────────────────────────────────────

    private void playDrawEffect(Location loc, boolean tierIV) {
        Location center = loc.clone().add(0.5, 1.0, 0.5);
        Particle particle = tierIV ? Particle.END_ROD : Particle.SNOWFLAKE;
        loc.getWorld().spawnParticle(particle, center, 40, 0.4, 0.4, 0.4, 0.05);
        loc.getWorld().playSound(center, tierIV ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
    }
}
