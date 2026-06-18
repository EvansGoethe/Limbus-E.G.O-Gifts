package me.yisang.limbusegogift;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GachaChestManager {

    private final LimbusEGOGift plugin;
    private final File dataFile;
    private YamlConfiguration config;
    private final Map<String, UUID> textDisplays = new HashMap<>();

    public GachaChestManager(LimbusEGOGift plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "gacha_chests.yml");
        load();
    }

    // ── 持久化 ────────────────────────────────────────────────────────────────

    private void load() {
        if (!dataFile.exists()) {
            plugin.getDataFolder().mkdirs();
            config = new YamlConfiguration();
            return;
        }
        config = YamlConfiguration.loadConfiguration(dataFile);
        // 重建懸浮文字（延遲到世界載入後）
        Bukkit.getScheduler().runTaskLater(plugin, this::respawnAllDisplays, 40L);
    }

    public void save() {
        try { config.save(dataFile); } catch (IOException e) { plugin.getLogger().severe("無法儲存 gacha_chests.yml: " + e.getMessage()); }
    }

    // ── 登記 / 移除 ──────────────────────────────────────────────────────────

    public boolean register(Location loc) {
        String key = locKey(loc);
        if (config.contains("chests." + key)) return false;
        config.set("chests." + key + ".world", loc.getWorld().getName());
        config.set("chests." + key + ".x", loc.getBlockX());
        config.set("chests." + key + ".y", loc.getBlockY());
        config.set("chests." + key + ".z", loc.getBlockZ());
        save();
        spawnDisplay(loc);
        return true;
    }

    public boolean unregister(Location loc) {
        String key = locKey(loc);
        if (!config.contains("chests." + key)) return false;
        config.set("chests." + key, null);
        save();
        removeDisplay(key);
        return true;
    }

    public boolean isGachaChest(Location loc) {
        return config.contains("chests." + locKey(loc));
    }

    public Set<Location> getAllLocations() {
        Set<Location> result = new HashSet<>();
        if (!config.contains("chests")) return result;
        for (String key : config.getConfigurationSection("chests").getKeys(false)) {
            Location loc = keyToLoc(key);
            if (loc != null) result.add(loc);
        }
        return result;
    }

    // ── TextDisplay 懸浮文字 ──────────────────────────────────────────────────

    private void spawnDisplay(Location loc) {
        String key = locKey(loc);
        removeDisplay(key);
        Location above = loc.clone().add(0.5, 1.4, 0.5);
        TextDisplay td = loc.getWorld().spawn(above, TextDisplay.class, e -> {
            e.setText(plugin.color("&#FFFFFF[&#FFD700飾品提取&#FFFFFF]"));
            e.setBillboard(org.bukkit.entity.Display.Billboard.CENTER);
            e.setTransformation(new Transformation(
                new Vector3f(0, 0, 0),
                new AxisAngle4f(0, 0, 0, 1),
                new Vector3f(1.2f, 1.2f, 1.2f),
                new AxisAngle4f(0, 0, 0, 1)
            ));
            e.setPersistent(false);
        });
        textDisplays.put(key, td.getUniqueId());
    }

    private void removeDisplay(String key) {
        UUID uid = textDisplays.remove(key);
        if (uid == null) return;
        Entity e = Bukkit.getEntity(uid);
        if (e != null) e.remove();
    }

    private void respawnAllDisplays() {
        for (Location loc : getAllLocations()) spawnDisplay(loc);
    }

    // ── 工具 ─────────────────────────────────────────────────────────────────

    private String locKey(Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    private Location keyToLoc(String key) {
        String[] p = key.split(",");
        if (p.length != 4) return null;
        World w = Bukkit.getWorld(p[0]);
        if (w == null) return null;
        try {
            return new Location(w, Integer.parseInt(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3]));
        } catch (NumberFormatException e) { return null; }
    }

    public void reload() {
        onDisable();
        textDisplays.clear();
        load();
    }

    public void onDisable() {
        textDisplays.forEach((k, uid) -> {
            Entity e = Bukkit.getEntity(uid);
            if (e != null) e.remove();
        });
    }
}
