# Limbus E.G.O Gifts — Paper Plugin

將邊獄公司（Limbus Company）的 E.G.O 飾品帶進 Minecraft 的 Paper 插件。

- **版本**：2.0
- **Minecraft 版本**：1.21.4
- **平台**：Paper
- **飾品數量**：80 件（+ 4 種殘影升級材料）
- **資源包**：玩家加入時自動推送，拒絕將被踢出伺服器

---

## 飾品系統

每位玩家有 **5 個飾品欄位**，可同時裝備 5 件飾品，效果全部疊加生效。

飾品效果分為六種觸發時機：
- **被動（每 20 tick）**：持續性效果，如回血、速度提升
- **攻擊時**：攻擊敵人時觸發
- **受傷時**：被攻擊（近戰）時觸發
- **任何傷害時**：任意傷害來源（墜落、火焰等）時觸發
- **擊殺時**：擊殺生物時觸發
- **死亡時**：玩家死亡時觸發
- **互動時**：右鍵互動時觸發（部分飾品主動使用）

---

## 升級系統

使用對應等級的**殘影**可升級飾品，最多升 3 次：

| 等級 | 倍率 | 升級材料 |
|------|------|----------|
| Lv.0 | ×1.0 | — |
| Lv.1 | ×1.25 | 對應等級殘影 |
| Lv.2 | ×1.50 | 對應等級殘影 |
| Lv.3 | ×2.0  | 對應等級殘影 |

**操作方式**：手持殘影，開啟飾品欄（`/accessories`），點擊同等級飾品格即可升級。

| 殘影 | 對應等級 |
|------|----------|
| `dark_vestige` 黯淡的殘影 | I 級 |
| `faint_vestige` 微芒的殘影 | II 級 |
| `twinkling_vestige` 閃耀的殘影 | III 級 |
| `brilliant_vestige` 輝煌的殘影 | IV 級 |

---

## 抽取系統（v2.0 新增）

放置一個箱子，管理員使用 `/gachachest set` 設定為抽取箱，箱子上方會顯示 **[飾品提取]** 懸浮文字。

- **抽取費用**：32 狂氣（`lunacy`，以 Echo Shard 為基底的自訂物品）
- **抽取機率**：I / II / III 各 30%，IV 10%
- **去重機制**：已抽過（歷史紀錄）、背包持有、飾品欄裝備中的飾品不會重複出現
- **全齊獎勵**：某等級的所有飾品全數集齊後，改為給予對應等級的殘影
- **粒子效果**：白色粒子；若本次抽中 IV 級飾品則為黃色

---

## 指令

| 指令 | 說明 | 權限 |
|------|------|------|
| `/accessories` | 開啟飾品欄位 GUI | 所有人 |
| `/getgift <id>` | 取得指定飾品 | `limbus.admin` / OP |
| `/getgift menu` | 取得飾品欄位開啟物品（下界之星） | `limbus.admin` / OP |
| `/getgift admin` | 開啟管理員 GUI（翻頁瀏覽所有飾品） | `limbus.admin` / OP |
| `/getgift lunacy [數量]` | 取得狂氣（最多 64） | `limbus.admin` / OP |
| `/gachachest set` | 將所看之箱子設為抽取箱 | `limbus.admin` / OP |
| `/gachachest remove` | 移除箱子的抽取箱狀態 | `limbus.admin` / OP |

---

## 飾品一覽

### Tier I

| ID | 名稱 | 效果 |
|----|------|------|
| `broken_compass` | 破碎羅盤 | 攻擊時 15% 機率施加反胃 4 秒 |
| `dry_to_the_bone_breast` | 乾巴柴澀雞胸肉 | 被動：不消耗飢餓值 |
| `hardship` | 苦難 | 被動：抗性提升 I |
| `homeward` | 歸途 | 被動：血量高於 80% 時生命再生 II |
| `piece_of_a_torn_summer` | 破碎之夏的殘片 | 受到火焰傷害時獲得抗性提升 II 3 秒 |
| `sour_liquor_aroma` | 酸味的酒香 | 被動：速度 I；攻擊時 20% 機率施加緩慢 I |
| `nixie_divergence` | 輝光變動儀 | 攻擊時 15% 機率隨機獲得一種藥水效果 |
| `prejudice` | 偏見 | 對亡靈與蜘蛛類造成額外 50% 傷害 |
| `bloody_gadget` | 鮮血裝飾 | 攻擊額外 +2 傷害，但自損 0.5 HP |

### Tier II

| ID | 名稱 | 效果 |
|----|------|------|
| `ardent_flower` | 火光花 | 被動：免疫火焰傷害 |
| `ashes_to_ashes` | 塵歸塵 | 擊殺時恢復 2 顆心 |
| `black_sheet_music` | 黑色樂譜 | 被動：急迫 I |
| `blue_zippo_lighter` | 藍色Zippo打火機 | 攻擊時 30% 機率點燃目標 3 秒 |
| `cask_spirits` | 桶裝烈酒 | 被動：生命再生 I；攻擊時偶有反效果 |
| `cold_illusion` | 冰冷的幻想 | 攻擊時施加緩慢 II 3 秒 |
| `cqc_manual` | 近身格鬥手冊 | 被動：力量 I |
| `crystallized_blood` | 結晶血液 | 受傷時獲得力量 I 3 秒 |
| `ebony_brooch` | 黑檀胸針 | 被動：夜視；夜晚獲得速度 I |
| `flower_mound` | 花塚 | 被動：生命再生 I |
| `frozen_cries` | 冰封的哀號 | 攻擊時施加緩慢 III 及虛弱 I 2 秒 |
| `harestride` | 卯足 | 被動：速度 II，跳躍提升 I |
| `pain_of_stifled_rage` | 鬱火 | 受傷時獲得力量 I 4 秒 |
| `plume_of_proof` | 證明的羽飾 | 擊殺時獲得速度 II 5 秒 |
| `rest` | 安息 | 被動：靜止時生命再生 II |
| `trial_plan_guide` | 試煉規劃指南 | 擊殺時額外獲得 50% 經驗值 |
| `carmilla` | 卡蜜拉 | 攻擊時偷取 3 HP；日光下每秒損 1 HP |
| `child_within_a_flask` | 瓶中嬰孩 | 受致命傷時有一次免死（冷卻 2 分鐘） |
| `green_spirit` | 綠光果實 | 右鍵：速度 II + 力量 I 5 秒，之後暈眩（冷卻 30 秒） |
| `sanguine_blossom_bolus` | 血花丸 | 非戰鬥時持續緩慢回血 |
| `late_bloomers_tattoo` | 刺青：大器晚成 | HP 越低攻防越強 |
| `e_type_dimensional_dagger` | E行次元短劍 | 攻擊時 25% 機率傳送到目標身後並造成額外傷害 |
| `bloodflame_sword` | 血炎刀 | 近戰攻擊點燃目標 3 秒 |

### Tier III

| ID | 名稱 | 效果 |
|----|------|------|
| `chief_butlers_secret_arts` | 首席管家的秘籍 | 被動：抗性提升 I，緩降 |
| `clear_mirror_calm_water` | 明鏡止水 | 受傷時 25% 機率減少 50% 傷害 |
| `dreaming_electric_sheep` | 夢中的電子羊 | 被動：緩降，緩慢 I |
| `dueling_manual_book_3` | 決鬥教材第3冊 | 受傷時 30% 機率格擋 50% 傷害 |
| `dust_to_dust` | 土歸土 | 擊殺時 30% 機率獲得吸收 I 5 秒 |
| `finifugality` | 留戀 | 受傷時血量低於 30% 觸發速度 II 急迫 I |
| `golden_urn` | 金甕 | 擊殺時恢復 2 心並掉落金粒 |
| `hot_n_juicy_drumstick` | 火熱多汁枇杷腿 | （主動效果） |
| `jin_gang_bolus` | 金剛丸 | 被動：吸收 I，力量 I |
| `keenbranch` | 磨尖的樹枝 | 攻擊時 20% 機率傷害提升 50% |
| `la_manchaland_all_day_pass` | 拉·曼查樂園自由通行券 | 被動：速度 I，跳躍提升 I，幸運 I |
| `la_manchaland_standard_pass` | 拉·曼查樂園常規通行券 | 被動：速度 I，幸運 I |
| `lithograph` | 石板字符 | 被動：所有傷害減少 10% |
| `mask_of_the_parade` | 遊行的面具 | 受傷時潛行狀態下獲得隱身 3 秒 |
| `mental_corruption_boosting_gas` | 精神汙染加速氣體 | 攻擊時 30% 機率對目標與附近玩家施加反胃 |
| `moon_in_the_water` | 水中月 | 被動：夜視，水下呼吸 |
| `phantom_pain` | 幻痛 | 受傷時 25% 機率減少 70% 傷害 |
| `smoking_gunpowder` | 有煙火藥 | 攻擊時 25% 機率在目標位置爆炸 |
| `strange_glyph_talisman` | 異文符咒 | （主動效果） |
| `nebulizer` | 霧化吸入器 | 被動：4 格內敵人持續中毒 |
| `strange_glyph_inscriptions` | 篆刻的異文 | 攻擊時 20% 機率觸發隨機正面或負面效果 |
| `rusty_commemorative_coin` | 生鏽的紀念幣 | 擊殺時 35% 機率掉落額外金粒 |
| `someones_device` | 某人的裝置 | 被動：ActionBar 顯示附近生物數量 |
| `special_contract` | 特殊合約 | 被動：力量 II 但緩慢 II |
| `flower_in_the_mirror` | 鏡中花 | 受致命傷時 40% 機率免死並獲得隱形+速度 |
| `sunshower` | 狐雨 | 雨天回血；晴天速度提升 |
| `thunderbranch` | 雷擊木 | 攻擊時 10% 機率召喚閃電 |

### Tier IV

| ID | 名稱 | 效果 |
|----|------|------|
| `distant_star` | 彼方之星 | 被動：幸運 I |
| `emerald_elytra` | 綠色鞘翅 | 被動：緩降，跳躍提升 I |
| `illusory_hunt` | 異想狩獵 | 攻擊時 25% 機率施加失明 3 秒 |
| `oracle` | 神諭 | 被動：血量低於 40% 時使附近生物顯現 |
| `piece_of_crumbled_egg` | 破碎之卵的殘片 | 死亡時對殺手落雷 |
| `piece_of_relationship` | 緣分殘片 | 被動：對 5 格內的玩家施加生命再生 I |
| `rags` | 破布 | 受傷時將 30% 傷害反彈給攻擊者 |
| `ruin` | 破滅 | 攻擊時額外 4 傷害，但自身損失 1 生命值 |
| `spicebush_branch` | 檀香梅枝 | 被動：中毒時轉化為回血效果 |
| `tangled_bones` | 碎裂的骨片 | 攻擊時對不死生物傷害翻倍；25% 點燃 |
| `tenacity_bolus` | 強韌丸 | 被動：抗性提升 I，生命提升 I |
| `the_book_of_vengeance` | 復仇帳簿 | 受傷時獲得力量 II 3 秒 |
| `tranquil_lotus_bolus` | 靜蓮丸 | 被動：抗性提升 I，緩慢 I |
| `trauma_shield` | 精神遮蔽力場 | 受傷時每 60 秒吸收一次傷害 |
| `endless_hunger` | 無盡的飢餓 | 飢餓不虛弱；攻擊力隨飽食度成正比 |
| `royal_jelly_perfume` | 蜂王漿香水 | 被動：生命恢復 I；附近蜜蜂不攻擊 |
| `millarca` | 蜜拉卡 | 攻擊時偷取 3 HP（強力吸血） |
| `artistic_sense` | 美感 | 附近有玩家或命名生物時發出感知提示 |
| `handheld_mirror` | 手鏡 | 將 30% 近戰傷害反彈給攻擊者 |
| `glimpse_of_flames` | 炎鱗 | 被攻擊時點燃攻擊者 3 秒並噴射火焰粒子 |
| `sownpour` | 暴雨 | 攻擊時 30% 機率連鎖打擊附近敵人（50% 傷害） |

---

## 資源包

插件使用獨立資源包提供飾品外觀，玩家加入時會自動收到推送。  
資源包來源：[Limbus_E.G.O_Gifts_plugin_ResourcePack](https://github.com/EvansGoethe/Limbus_E.G.O_Gifts_plugin_ResourcePack)

---

## 安裝方式

1. 將編譯好的 `.jar` 放入伺服器的 `plugins/` 資料夾
2. 啟動伺服器，資源包會自動推送給玩家
3. 輸入 `/getgift menu` 取得飾品欄位開啟物品，右鍵即可開啟飾品 GUI
4. 管理員使用 `/getgift lunacy <數量>` 發放狂氣，放置箱子後 `/gachachest set` 設定抽取箱
