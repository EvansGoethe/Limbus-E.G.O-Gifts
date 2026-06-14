package me.yisang.limbusegogift;

import me.yisang.limbusegogift.gifts.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LimbusEGOGift extends JavaPlugin implements Listener, TabCompleter {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    private static final String PACK_URL  = "https://github.com/EvansGoethe/Limbus_E.G.O_Gifts_plugin_ResourcePack/releases/download/releases/Limbus_E.G.O._Gifts_plugin_ResourcePack.v.10.zip";
    private static final String PACK_HASH = "f0c13d17d8681cca89521f2679da2a321592a189";
    private static final java.util.UUID PACK_UUID = java.util.UUID.nameUUIDFromBytes(
            PACK_URL.getBytes(java.nio.charset.StandardCharsets.UTF_8));

    private static byte[] hexToBytes(String hex) {
        byte[] data = new byte[hex.length() / 2];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ((Character.digit(hex.charAt(i * 2), 16) << 4)
                             + Character.digit(hex.charAt(i * 2 + 1), 16));
        }
        return data;
    }

    private NamespacedKey ITEM_ID_KEY;
    private NamespacedKey MENU_OPENER_KEY;
    private NamespacedKey[] SLOT_KEYS;

    private final Map<String, Accessory> accessories = new LinkedHashMap<>();
    private final Map<UUID, AccessoryMenu> openMenus = new HashMap<>();
    private final Map<UUID, GiftAdminGUI> openAdminMenus = new HashMap<>();

    // ── 初始化 ──────────────────────────────────────────────────────────────

    @Override
    public void onEnable() {
        ITEM_ID_KEY     = new NamespacedKey(this, "accessory_id");
        MENU_OPENER_KEY = new NamespacedKey(this, "menu_opener");
        SLOT_KEYS = new NamespacedKey[5];
        for (int i = 0; i < 5; i++) SLOT_KEYS[i] = new NamespacedKey(this, "slot_" + i);

        registerAccessory(new ArdentFlower(this));
        registerAccessory(new AshesToAshes(this));
        registerAccessory(new BlackSheetMusic(this));
        registerAccessory(new BlueZippoLighter(this));
        registerAccessory(new BrilliantVestige(this));
        registerAccessory(new BrokenCompass(this));
        registerAccessory(new CaskSpirits(this));
        registerAccessory(new ChiefButlersSecretArts(this));
        registerAccessory(new ClearMirrorCalmWater(this));
        registerAccessory(new ColdIllusion(this));
        registerAccessory(new CQCManual(this));
        registerAccessory(new CrystallizedBlood(this));
        registerAccessory(new DistantStar(this));
        registerAccessory(new DreamingElectricSheep(this));
        registerAccessory(new DryToTheBoneBreast(this));
        registerAccessory(new DuelingManualBook3(this));
        registerAccessory(new DustToDust(this));
        registerAccessory(new EbonyBrooch(this));
        registerAccessory(new EmeraldElytra(this));
        registerAccessory(new Finifugality(this));
        registerAccessory(new FlowerMound(this));
        registerAccessory(new FrozenCries(this));
        registerAccessory(new GoldenUrn(this));
        registerAccessory(new Hardship(this));
        registerAccessory(new Harestride(this));
        registerAccessory(new Homeward(this));
        registerAccessory(new HotNJuicyDrumstick(this));
        registerAccessory(new IllusoryHunt(this));
        registerAccessory(new JinGangBolus(this));
        registerAccessory(new Keenbranch(this));
        registerAccessory(new LaManchalandAllDayPass(this));
        registerAccessory(new LaManchalandStandardPass(this));
        registerAccessory(new Lithograph(this));
        registerAccessory(new MaskOfTheParade(this));
        registerAccessory(new MentalCorruptionBoostingGas(this));
        registerAccessory(new MoonInTheWater(this));
        registerAccessory(new Oracle(this));
        registerAccessory(new PainOfStifledRage(this));
        registerAccessory(new PhantomPain(this));
        registerAccessory(new PieceOfATornSummer(this));
        registerAccessory(new PieceOfCrumbledEgg(this));
        registerAccessory(new PieceOfRelationship(this));
        registerAccessory(new PlumeOfProof(this));
        registerAccessory(new Rags(this));
        registerAccessory(new Rest(this));
        registerAccessory(new Ruin(this));
        registerAccessory(new SmokingGunpowder(this));
        registerAccessory(new SourLiquorAroma(this));
        registerAccessory(new SpicebushBranch(this));
        registerAccessory(new StrangeGlyphTalisman(this));
        registerAccessory(new TangledBones(this));
        registerAccessory(new TenacityBolus(this));
        registerAccessory(new TheBookOfVengeance(this));
        registerAccessory(new TranquilLotusBolus(this));
        registerAccessory(new TraumaShield(this));
        registerAccessory(new TrialPlanGuide(this));
        registerAccessory(new TwinklingVestige(this));

        startPassiveTick();
        getServer().getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(getCommand("accessories")).setExecutor(
                (sender, cmd, label, args) -> { if (sender instanceof Player p) openMenu(p); return true; });

        PluginCommand getgift = getCommand("getgift");
        if (getgift != null) {
            getgift.setExecutor(this::onGetGift);
            getgift.setTabCompleter(this);
        }
    }

    public void registerAccessory(Accessory acc) {
        accessories.put(acc.getId(), acc);
    }

    // ── 公開工具方法 ────────────────────────────────────────────────────────

    public NamespacedKey getItemIdKey()      { return ITEM_ID_KEY; }
    public NamespacedKey getMenuOpenerKey()  { return MENU_OPENER_KEY; }
    public NamespacedKey getSlotKey(int i)   { return SLOT_KEYS[i]; }

    public String getAccessoryId(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta().getPersistentDataContainer()
                .get(ITEM_ID_KEY, PersistentDataType.STRING);
    }

    public boolean isAccessory(ItemStack item) {
        return getAccessoryId(item) != null;
    }

    public Accessory getAccessory(String id) {
        return accessories.get(id);
    }

    public java.util.Collection<Accessory> getAllAccessories() {
        return accessories.values();
    }

    public List<Accessory> getEquippedAccessories(Player player) {
        return AccessoryMenu.getEquipped(player, this);
    }

    // ── 開啟選單 ────────────────────────────────────────────────────────────

    public void openMenu(Player player) {
        AccessoryMenu menu = new AccessoryMenu(player, this);
        openMenus.put(player.getUniqueId(), menu);
        menu.open();
    }

    // ── 飾品選單開啟物品（NETHER_STAR + PDC menu_opener）────────────────────

    public ItemStack createMenuOpener() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color("&#FFD700✦ &f飾品欄位"));
        meta.setLore(List.of(color("&#AAAAAA右鍵開啟飾品欄位")));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(MENU_OPENER_KEY, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    private boolean isMenuOpener(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer()
                .has(MENU_OPENER_KEY, PersistentDataType.BYTE);
    }

    // ── 被動 Tick（每 20 tick 呼叫所有裝備飾品的 onPassiveTick）──────────────

    private void startPassiveTick() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Accessory acc : getEquippedAccessories(player)) {
                    acc.onPassiveTick(player);
                }
            }
        }, 0L, 20L);
    }

    // ── GUI 事件 ────────────────────────────────────────────────────────────

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        // Admin GUI click
        GiftAdminGUI adminGui = openAdminMenus.get(player.getUniqueId());
        if (adminGui != null && event.getInventory().equals(adminGui.getInventory())) {
            event.setCancelled(true);
            int slot = event.getSlot();
            if (slot < 0 || slot >= 54) return;
            if (slot == 45) { adminGui.changePage(-1); return; }
            if (slot == 53) { adminGui.changePage(+1); return; }
            if (adminGui.isNavSlot(slot)) return;
            ItemStack item = event.getCurrentItem();
            if (item != null && !item.getType().isAir()) {
                player.getInventory().addItem(item.clone());
                player.sendMessage(color("&#FFD700已給予物品。"));
            }
            return;
        }

        AccessoryMenu menu = openMenus.get(player.getUniqueId());
        if (menu == null || !event.getInventory().equals(menu.getInventory())) return;

        int raw = event.getRawSlot();
        int size = menu.getInventory().getSize();

        if (raw < size) {
            // 點擊 GUI 區域
            if (!menu.isAccessorySlot(raw)) {
                event.setCancelled(true);
                return;
            }
            // 飾品欄位：只允許放入飾品，或取出
            ItemStack cursor = event.getCursor();
            if (cursor != null && !cursor.getType().isAir() && !isAccessory(cursor)) {
                event.setCancelled(true);
            }
        } else {
            // 點擊玩家背包：阻止 Shift+Click 自動放入 GUI
            if (event.getClick().isShiftClick()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        AccessoryMenu menu = openMenus.get(player.getUniqueId());
        if (menu == null || !event.getInventory().equals(menu.getInventory())) return;

        for (int slot : event.getRawSlots()) {
            if (slot < menu.getInventory().getSize() && !menu.isAccessorySlot(slot)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        openAdminMenus.remove(player.getUniqueId());
        AccessoryMenu menu = openMenus.remove(player.getUniqueId());
        if (menu == null || !event.getInventory().equals(menu.getInventory())) return;
        menu.saveEquipped();
    }

    // ── 資源包推送 ───────────────────────────────────────────────────────────

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        getLogger().info("[ResourcePack] Sending pack to " + player.getName());
        try {
            player.addResourcePack(PACK_UUID, PACK_URL, hexToBytes(PACK_HASH),
                    "Receiving resource pack...", true);
            getLogger().info("[ResourcePack] Sent successfully to " + player.getName());
        } catch (Exception e) {
            getLogger().severe("[ResourcePack] Failed to send: " + e.getMessage());
        }
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        if (!PACK_UUID.equals(event.getID())) return;
        getLogger().info("[ResourcePack] " + event.getPlayer().getName()
                + " status: " + event.getStatus().name());
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            event.getPlayer().kickPlayer("§c請接受資源包以加入伺服器。");
        }
    }

    // ── 選單開啟物品右鍵 ────────────────────────────────────────────────────

    @EventHandler
    public void onMenuOpenerUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!isMenuOpener(event.getItem())) return;
        event.setCancelled(true);
        openMenu(event.getPlayer());
    }

    // ── 攻擊 / 受傷 / 擊殺事件，分發給裝備中的飾品 ─────────────────────────

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker) {
            for (Accessory acc : getEquippedAccessories(attacker)) {
                acc.onAttack(event, attacker);
            }
        }
        if (event.getEntity() instanceof Player victim) {
            for (Accessory acc : getEquippedAccessories(victim)) {
                acc.onDamaged(event, victim);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        for (Accessory acc : getEquippedAccessories(deceased)) {
            acc.onDeath(event, deceased);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        for (Accessory acc : getEquippedAccessories(killer)) {
            acc.onKill(event, killer);
        }
    }

    // ── 指令 ────────────────────────────────────────────────────────────────

    private boolean onGetGift(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length == 0) return false;

        String id = args[0].toLowerCase();
        if (id.equals("admin")) {
            if (!player.hasPermission("limbus.admin") && !player.isOp()) return true;
            GiftAdminGUI gui = new GiftAdminGUI(this, 0);
            openAdminMenus.put(player.getUniqueId(), gui);
            player.openInventory(gui.getInventory());
            return true;
        }
        if (id.equals("menu")) {
            player.getInventory().addItem(createMenuOpener());
        } else {
            Accessory acc = accessories.get(id);
            if (acc != null) acc.give(player);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> ids = new ArrayList<>(List.of("admin", "menu"));
            ids.addAll(accessories.keySet());
            return ids.stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        }
        return Collections.emptyList();
    }

    // ── 顏色代碼工具 ─────────────────────────────────────────────────────────

    public String color(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder r = new StringBuilder("§x");
            for (char c : hex.toCharArray()) r.append('§').append(c);
            matcher.appendReplacement(sb, r.toString());
        }
        matcher.appendTail(sb);
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }
}
