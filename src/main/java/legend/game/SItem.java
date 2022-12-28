package legend.game;

import legend.core.Config;
import legend.core.MathHelper;
import legend.core.Tuple;
import legend.core.gpu.GpuCommandPoly;
import legend.core.memory.Memory;
import legend.core.memory.Method;
import legend.core.memory.Value;
import legend.core.memory.types.ArrayRef;
import legend.core.memory.types.BoolRef;
import legend.core.memory.types.EnumRef;
import legend.core.memory.types.IntRef;
import legend.core.memory.types.Pointer;
import legend.core.memory.types.UnboundedArrayRef;
import legend.core.memory.types.UnsignedByteRef;
import legend.core.memory.types.UnsignedIntRef;
import legend.core.memory.types.UnsignedShortRef;
import legend.game.combat.Bttl_800c;
import legend.game.combat.types.BattleObject27c;
import legend.game.combat.types.BattleScriptDataBase;
import legend.game.combat.types.CombatantStruct1a8;
import legend.game.inventory.WhichMenu;
import legend.game.inventory.screens.DabasScreen;
import legend.game.inventory.screens.EquipmentScreen;
import legend.game.inventory.screens.MenuStack;
import legend.game.inventory.screens.SaveGameScreen;
import legend.game.inventory.screens.StatusScreen;
import legend.game.inventory.screens.UseItemScreen;
import legend.game.title.Ttle;
import legend.game.types.ActiveStatsa0;
import legend.game.types.CharacterData2c;
import legend.game.types.EquipmentStats1c;
import legend.game.types.InventoryMenuState;
import legend.game.types.LevelStuff08;
import legend.game.types.LodString;
import legend.game.types.MagicStuff08;
import legend.game.types.MenuAdditionInfo;
import legend.game.types.MenuGlyph06;
import legend.game.types.MenuItemStruct04;
import legend.game.types.MenuStruct08;
import legend.game.types.MessageBox20;
import legend.game.types.MessageBoxResult;
import legend.game.types.PartyPermutation08;
import legend.game.types.Renderable58;
import legend.game.types.SavedGameDisplayData;
import legend.game.types.ScriptState;
import legend.game.types.Translucency;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static legend.core.GameEngine.GPU;
import static legend.core.GameEngine.MEMORY;
import static legend.core.MathHelper.roundUp;
import static legend.core.MemoryHelper.getTriConsumerAddress;
import static legend.game.SMap.FUN_800e3fac;
import static legend.game.SMap.shops_800f4930;
import static legend.game.Scus94491BpeSegment.FUN_80018e84;
import static legend.game.Scus94491BpeSegment.FUN_800192d8;
import static legend.game.Scus94491BpeSegment.FUN_80019470;
import static legend.game.Scus94491BpeSegment._1f8003f4;
import static legend.game.Scus94491BpeSegment.allocateScriptState;
import static legend.game.Scus94491BpeSegment.decrementOverlayCount;
import static legend.game.Scus94491BpeSegment.deferReallocOrFree;
import static legend.game.Scus94491BpeSegment.displayWidth_1f8003e0;
import static legend.game.Scus94491BpeSegment.free;
import static legend.game.Scus94491BpeSegment.loadDrgnBinFile;
import static legend.game.Scus94491BpeSegment.loadDrgnDir;
import static legend.game.Scus94491BpeSegment.loadSupportOverlay;
import static legend.game.Scus94491BpeSegment.mallocTail;
import static legend.game.Scus94491BpeSegment.memcpy;
import static legend.game.Scus94491BpeSegment.scriptStartEffect;
import static legend.game.Scus94491BpeSegment.setScriptDestructor;
import static legend.game.Scus94491BpeSegment.setScriptTicker;
import static legend.game.Scus94491BpeSegment.setWidthAndFlags;
import static legend.game.Scus94491BpeSegment.simpleRand;
import static legend.game.Scus94491BpeSegment_8002.FUN_80022898;
import static legend.game.Scus94491BpeSegment_8002.FUN_80022a94;
import static legend.game.Scus94491BpeSegment_8002.FUN_80023544;
import static legend.game.Scus94491BpeSegment_8002.FUN_800239e0;
import static legend.game.Scus94491BpeSegment_8002.FUN_8002a86c;
import static legend.game.Scus94491BpeSegment_8002.FUN_8002a8f8;
import static legend.game.Scus94491BpeSegment_8002.FUN_8002bcc8;
import static legend.game.Scus94491BpeSegment_8002.FUN_8002bda4;
import static legend.game.Scus94491BpeSegment_8002.addGold;
import static legend.game.Scus94491BpeSegment_8002.allocateRenderable;
import static legend.game.Scus94491BpeSegment_8002.clearCharacterStats;
import static legend.game.Scus94491BpeSegment_8002.deallocateRenderables;
import static legend.game.Scus94491BpeSegment_8002.getItemIcon;
import static legend.game.Scus94491BpeSegment_8002.getJoypadInputByPriority;
import static legend.game.Scus94491BpeSegment_8002.getTimestampPart;
import static legend.game.Scus94491BpeSegment_8002.getUnlockedDragoonSpells;
import static legend.game.Scus94491BpeSegment_8002.giveItem;
import static legend.game.Scus94491BpeSegment_8002.intToStr;
import static legend.game.Scus94491BpeSegment_8002.playSound;
import static legend.game.Scus94491BpeSegment_8002.recalcInventory;
import static legend.game.Scus94491BpeSegment_8002.sortItems;
import static legend.game.Scus94491BpeSegment_8002.takeEquipment;
import static legend.game.Scus94491BpeSegment_8002.takeItem;
import static legend.game.Scus94491BpeSegment_8002.textWidth;
import static legend.game.Scus94491BpeSegment_8002.unloadRenderable;
import static legend.game.Scus94491BpeSegment_8002.uploadRenderables;
import static legend.game.Scus94491BpeSegment_8004.additionCounts_8004f5c0;
import static legend.game.Scus94491BpeSegment_8004.additionOffsets_8004f5ac;
import static legend.game.Scus94491BpeSegment_8004.loadingGameStateOverlay_8004dd08;
import static legend.game.Scus94491BpeSegment_8004.mainCallbackIndex_8004dd20;
import static legend.game.Scus94491BpeSegment_8004.setMono;
import static legend.game.Scus94491BpeSegment_8005.additionData_80052884;
import static legend.game.Scus94491BpeSegment_8005.combatants_8005e398;
import static legend.game.Scus94491BpeSegment_8005.spells_80052734;
import static legend.game.Scus94491BpeSegment_8005.standingInSavePoint_8005a368;
import static legend.game.Scus94491BpeSegment_8006._8006e398;
import static legend.game.Scus94491BpeSegment_8007.joypadPress_8007a398;
import static legend.game.Scus94491BpeSegment_8007.shopId_8007a3b4;
import static legend.game.Scus94491BpeSegment_800b._800bb168;
import static legend.game.Scus94491BpeSegment_800b._800bc910;
import static legend.game.Scus94491BpeSegment_800b._800bc960;
import static legend.game.Scus94491BpeSegment_800b._800bc968;
import static legend.game.Scus94491BpeSegment_800b._800bc97c;
import static legend.game.Scus94491BpeSegment_800b._800bdb9c;
import static legend.game.Scus94491BpeSegment_800b._800bdba0;
import static legend.game.Scus94491BpeSegment_800b._800bdc2c;
import static legend.game.Scus94491BpeSegment_800b._800bdc34;
import static legend.game.Scus94491BpeSegment_800b._800be5d0;
import static legend.game.Scus94491BpeSegment_800b.characterIndices_800bdbb8;
import static legend.game.Scus94491BpeSegment_800b.confirmDest_800bdc30;
import static legend.game.Scus94491BpeSegment_800b.continentIndex_800bf0b0;
import static legend.game.Scus94491BpeSegment_800b.drgn0_6666FilePtr_800bdc3c;
import static legend.game.Scus94491BpeSegment_800b.encounterId_800bb0f8;
import static legend.game.Scus94491BpeSegment_800b.equipmentStats_800be5d8;
import static legend.game.Scus94491BpeSegment_800b.gameOverMcq_800bdc3c;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.goldGainedFromCombat_800bc920;
import static legend.game.Scus94491BpeSegment_800b.highlightLeftHalf_800bdbe8;
import static legend.game.Scus94491BpeSegment_800b.highlightRightHalf_800bdbec;
import static legend.game.Scus94491BpeSegment_800b.inventoryJoypadInput_800bdc44;
import static legend.game.Scus94491BpeSegment_800b.inventoryMenuState_800bdc28;
import static legend.game.Scus94491BpeSegment_800b.itemsDroppedByEnemiesCount_800bc978;
import static legend.game.Scus94491BpeSegment_800b.itemsDroppedByEnemies_800bc928;
import static legend.game.Scus94491BpeSegment_800b.renderablePtr_800bdba4;
import static legend.game.Scus94491BpeSegment_800b.renderablePtr_800bdba8;
import static legend.game.Scus94491BpeSegment_800b.renderablePtr_800bdbf0;
import static legend.game.Scus94491BpeSegment_800b.renderablePtr_800bdc20;
import static legend.game.Scus94491BpeSegment_800b.renderablePtr_800bdc5c;
import static legend.game.Scus94491BpeSegment_800b.saveListDownArrow_800bdb98;
import static legend.game.Scus94491BpeSegment_800b.saveListUpArrow_800bdb94;
import static legend.game.Scus94491BpeSegment_800b.scriptStatePtrArr_800bc1c0;
import static legend.game.Scus94491BpeSegment_800b.secondaryCharIndices_800bdbf8;
import static legend.game.Scus94491BpeSegment_800b.selectedMenuOptionRenderablePtr_800bdbe0;
import static legend.game.Scus94491BpeSegment_800b.selectedMenuOptionRenderablePtr_800bdbe4;
import static legend.game.Scus94491BpeSegment_800b.spGained_800bc950;
import static legend.game.Scus94491BpeSegment_800b.stats_800be5f8;
import static legend.game.Scus94491BpeSegment_800b.submapIndex_800bd808;
import static legend.game.Scus94491BpeSegment_800b.textZ_800bdf00;
import static legend.game.Scus94491BpeSegment_800b.tickCount_800bb0fc;
import static legend.game.Scus94491BpeSegment_800b.totalXpFromCombat_800bc95c;
import static legend.game.Scus94491BpeSegment_800b.whichMenu_800bdc38;
import static legend.game.combat.Bttl_800c._800c66d0;
import static legend.game.combat.Bttl_800c.addCombatant;
import static legend.game.combat.Bttl_800c.charCount_800c677c;
import static legend.game.combat.Bttl_800c.combatantCount_800c66a0;
import static legend.game.combat.Bttl_800c.combatantTmdAndAnimLoadedCallback;
import static legend.game.combat.Bttl_800c.getCombatant;
import static legend.game.combat.Bttl_800c.loadCombatantTim;
import static legend.game.combat.Bttl_800c.loadCombatantTmdAndAnims;
import static legend.game.combat.Bttl_800f.FUN_800f863c;

public final class SItem {
  private SItem() { }

  public static final MenuStack menuStack = new MenuStack();

  public static final Value _800fba58 = MEMORY.ref(4, 0x800fba58L);

  public static final ArrayRef<UnsignedByteRef> additionXpPerLevel_800fba2c = MEMORY.ref(1, 0x800fba2cL, ArrayRef.of(UnsignedByteRef.class, 5, 1, UnsignedByteRef::new));

  public static final ArrayRef<MenuStruct08> _800fba7c = MEMORY.ref(4, 0x800fba7cL, ArrayRef.of(MenuStruct08.class, 8, 8, MenuStruct08::new));

  public static final Value _800fbabc = MEMORY.ref(4, 0x800fbabcL);

  public static final Value _800fbbf0 = MEMORY.ref(4, 0x800fbbf0L);

  public static final Value _800fbc88 = MEMORY.ref(2, 0x800fbc88L);

  public static final Value _800fbc9c = MEMORY.ref(1, 0x800fbc9cL);

  public static final Value _800fbca8 = MEMORY.ref(1, 0x800fbca8L);

  public static final ArrayRef<UnsignedIntRef> _800fbd08 = MEMORY.ref(4, 0x800fbd08L, ArrayRef.of(UnsignedIntRef.class, 10, 4, UnsignedIntRef::new));
  public static final ArrayRef<Pointer<ArrayRef<LevelStuff08>>> levelStuff_800fbd30 = MEMORY.ref(4, 0x800fbd30L, ArrayRef.of(Pointer.classFor(ArrayRef.classFor(LevelStuff08.class)), 9, 4, Pointer.deferred(4, ArrayRef.of(LevelStuff08.class, 61, 8, LevelStuff08::new))));
  public static final ArrayRef<Pointer<ArrayRef<MagicStuff08>>> magicStuff_800fbd54 = MEMORY.ref(4, 0x800fbd54L, ArrayRef.of(Pointer.classFor(ArrayRef.classFor(MagicStuff08.class)), 9, 4, Pointer.deferred(4, ArrayRef.of(MagicStuff08.class, 6, 8, MagicStuff08::new))));

  public static final ArrayRef<Pointer<ArrayRef<LevelStuff08>>> levelStuff_80111cfc = MEMORY.ref(4, 0x80111cfcL, ArrayRef.of(Pointer.classFor(ArrayRef.classFor(LevelStuff08.class)), 9, 4, Pointer.deferred(4, ArrayRef.of(LevelStuff08.class, 61, 8, LevelStuff08::new))));
  public static final ArrayRef<Pointer<ArrayRef<MagicStuff08>>> magicStuff_80111d20 = MEMORY.ref(4, 0x80111d20L, ArrayRef.of(Pointer.classFor(ArrayRef.classFor(MagicStuff08.class)), 9, 4, Pointer.deferred(4, ArrayRef.of(MagicStuff08.class, 6, 8, MagicStuff08::new))));

  public static final Value _80111d38 = MEMORY.ref(4, 0x80111d38L);

  /** Contains data for every combination of party members (like a DRGN0 file index that contains the textures and models of each char */
  public static final ArrayRef<ArrayRef<PartyPermutation08>> partyPermutations_80111d68 = MEMORY.ref(2, 0x80111d68L, ArrayRef.of(ArrayRef.classFor(PartyPermutation08.class), 9, 0x48, ArrayRef.of(PartyPermutation08.class, 9, 8, PartyPermutation08::new)));

  public static final ArrayRef<EquipmentStats1c> equipmentStats_80111ff0 = MEMORY.ref(1, 0x80111ff0L, ArrayRef.of(EquipmentStats1c.class, 0xc0, 0x1c, EquipmentStats1c::new));
  public static final ArrayRef<IntRef> kongolXpTable_801134f0 = MEMORY.ref(4, 0x801134f0L, ArrayRef.of(IntRef.class, 61, 4, IntRef::new));
  public static final ArrayRef<IntRef> dartXpTable_801135e4 = MEMORY.ref(4, 0x801135e4L, ArrayRef.of(IntRef.class, 61, 4, IntRef::new));
  public static final ArrayRef<IntRef> haschelXpTable_801136d8 = MEMORY.ref(4, 0x801136d8L, ArrayRef.of(IntRef.class, 61, 4, IntRef::new));
  public static final ArrayRef<IntRef> meruXpTable_801137cc = MEMORY.ref(4, 0x801137ccL, ArrayRef.of(IntRef.class, 61, 4, IntRef::new));
  public static final ArrayRef<IntRef> lavitzXpTable_801138c0 = MEMORY.ref(4, 0x801138c0L, ArrayRef.of(IntRef.class, 61, 4, IntRef::new));
  public static final ArrayRef<IntRef> roseXpTable_801139b4 = MEMORY.ref(4, 0x801139b4L, ArrayRef.of(IntRef.class, 61, 4, IntRef::new));
  public static final ArrayRef<IntRef> shanaXpTable_80113aa8 = MEMORY.ref(4, 0x80113aa8L, ArrayRef.of(IntRef.class, 61, 4, IntRef::new));

  public static final Value ptrTable_80114070 = MEMORY.ref(4, 0x80114070L);

  public static final UnboundedArrayRef<MenuGlyph06> glyphs_80114130 = MEMORY.ref(1, 0x80114130L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> glyphs_80114160 = MEMORY.ref(1, 0x80114160L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> equipmentGlyphs_80114180 = MEMORY.ref(1, 0x80114180L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> characterStatusGlyphs_801141a4 = MEMORY.ref(1, 0x801141a4L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> glyphs_801141c4 = MEMORY.ref(1, 0x801141c4L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> glyphs_801141e4 = MEMORY.ref(1, 0x801141e4L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> useItemGlyphs_801141fc = MEMORY.ref(1, 0x801141fcL, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> dabasMenuGlyphs_80114228 = MEMORY.ref(1, 0x80114228L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> glyphs_80114258 = MEMORY.ref(1, 0x80114258L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));

  public static final Value characterValidEquipment_80114284 = MEMORY.ref(1, 0x80114284L);

  public static final Value _80114290 = MEMORY.ref(1, 0x80114290L);

  public static final MenuGlyph06 glyph_801142d4 = MEMORY.ref(1, 0x801142d4L, MenuGlyph06::new);

  public static final ArrayRef<Pointer<LodString>> chapterNames_80114248 = MEMORY.ref(4, 0x80114248L, ArrayRef.of(Pointer.classFor(LodString.class), 4, 4, Pointer.deferred(4, LodString::new)));

  public static final ArrayRef<Pointer<LodString>> characterNames_801142dc = MEMORY.ref(4, 0x801142dcL, ArrayRef.of(Pointer.classFor(LodString.class), 9, 4, Pointer.deferred(4, LodString::new)));

  public static final ArrayRef<UnsignedShortRef> itemPrices_80114310 = MEMORY.ref(2, 0x80114310L, ArrayRef.of(UnsignedShortRef.class, 0x100, 2, UnsignedShortRef::new));
  public static final UnboundedArrayRef<MenuGlyph06> glyphs_80114510 = MEMORY.ref(1, 0x80114510L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));
  public static final UnboundedArrayRef<MenuGlyph06> glyphs_80114548 = MEMORY.ref(1, 0x80114548L, UnboundedArrayRef.of(0x6, MenuGlyph06::new));

  public static final ArrayRef<Pointer<LodString>> _80117a10 = MEMORY.ref(4, 0x80117a10L, ArrayRef.of(Pointer.classFor(LodString.class), 256, 4, Pointer.deferred(4, LodString::new)));

  public static final ArrayRef<Pointer<LodString>> equipment_8011972c = MEMORY.ref(4, 0x8011972cL, ArrayRef.of(Pointer.classFor(LodString.class), 256, 4, Pointer.deferred(4, LodString::new)));

  public static final ArrayRef<Pointer<LodString>> additions_8011a064 = MEMORY.ref(4, 0x8011a064L, ArrayRef.of(Pointer.classFor(LodString.class), 43, 4, Pointer.deferred(4, LodString::new)));

  public static final ArrayRef<Pointer<LodString>> _8011b75c = MEMORY.ref(4, 0x8011b75cL, ArrayRef.of(Pointer.classFor(LodString.class), 64, 4, Pointer.deferred(4, LodString::new)));

  public static final ArrayRef<Pointer<LodString>> _8011c008 = MEMORY.ref(4, 0x8011c008L, ArrayRef.of(Pointer.classFor(LodString.class), 64, 4, Pointer.deferred(4, LodString::new)));
  public static final ArrayRef<Pointer<LodString>> submapNames_8011c108 = MEMORY.ref(4, 0x8011c108L, ArrayRef.of(Pointer.classFor(LodString.class), 57, 4, Pointer.deferred(4, LodString::new)));
  public static final ArrayRef<Pointer<LodString>> worldMapNames_8011c1ec = MEMORY.ref(4, 0x8011c1ecL, ArrayRef.of(Pointer.classFor(LodString.class), 8, 4, Pointer.deferred(4, LodString::new)));

  public static final LodString _8011c254 = MEMORY.ref(4, 0x8011c254L, LodString::new);

  /** "Yes" */
  public static final LodString Yes_8011c20c = MEMORY.ref(2, 0x8011c20cL, LodString::new);
  /** "No" */
  public static final LodString No_8011c214 = MEMORY.ref(2, 0x8011c214L, LodString::new);
  public static final LodString Too_many_8011c21c = MEMORY.ref(2, 0x8011c21cL, LodString::new);
  public static final LodString items_8011c230 = MEMORY.ref(2, 0x8011c230L, LodString::new);
  public static final LodString Replace_8011c240 = MEMORY.ref(2, 0x8011c240L, LodString::new);
  /** Spelling mistake included */
  public static final LodString To_many_items_8011c268 = MEMORY.ref(2, 0x8011c268L, LodString::new);
  public static final LodString Discard_8011c288 = MEMORY.ref(2, 0x8011c288L, LodString::new);
  public static final LodString End_8011c29c = MEMORY.ref(2, 0x8011c29cL, LodString::new);
  public static final LodString This_item_cannot_be_thrown_away_8011c2a8 = MEMORY.ref(2, 0x8011c2a8L, LodString::new);
  public static final LodString Acquired_item_8011c2f8 = MEMORY.ref(2, 0x8011c2f8L, LodString::new);
  public static final LodString _8011c314 = MEMORY.ref(2, 0x8011c314L, LodString::new);
  public static final LodString _8011c32c = MEMORY.ref(2, 0x8011c32cL, LodString::new);
  public static final LodString _8011c340 = MEMORY.ref(2, 0x8011c340L, LodString::new);
  /** "Do you want to save now?" */
  public static final LodString Do_you_want_to_save_now_8011c370 = MEMORY.ref(2, 0x8011c370L, LodString::new);
  public static final LodString Are_you_sure_you_want_to_buy_8011c3ec = MEMORY.ref(2, 0x8011c3ecL, LodString::new);
  public static final LodString Cannot_carry_anymore_8011c43c = MEMORY.ref(2, 0x8011c43cL, LodString::new);
  public static final LodString Not_enough_money_8011c468 = MEMORY.ref(2, 0x8011c468L, LodString::new);
  /** "Conf." */
  public static final LodString Conf_8011c48c = MEMORY.ref(2, 0x8011c48cL, LodString::new);
  public static final LodString What_do_you_want_to_sell_8011c498 = MEMORY.ref(2, 0x8011c498L, LodString::new);
  public static final LodString Armed_8011c4cc = MEMORY.ref(2, 0x8011c4ccL, LodString::new);
  public static final LodString item_8011c4d8 = MEMORY.ref(2, 0x8011c4d8L, LodString::new);
  public static final LodString Which_item_do_you_want_to_sell_8011c4e4 = MEMORY.ref(2, 0x8011c4e4L, LodString::new);
  public static final LodString Which_weapon_do_you_want_to_sell_8011c524 = MEMORY.ref(2, 0x8011c524L, LodString::new);
  public static final LodString Are_you_sure_you_want_to_sell_8011c568 = MEMORY.ref(2, 0x8011c568L, LodString::new);
  /** "New Addition" */
  public static final LodString New_Addition_8011c5a8 = MEMORY.ref(2, 0x8011c5a8L, LodString::new);
  public static final LodString Spell_Unlocked_8011c5c4 = MEMORY.ref(2, 0x8011c5c4L, LodString::new);
  public static final LodString No_item_to_sell_8011c5dc = MEMORY.ref(2, 0x8011c5dcL, LodString::new);
  public static final LodString No_weapon_to_sell_8011c5fc = MEMORY.ref(2, 0x8011c5fcL, LodString::new);
  public static final LodString Do_you_want_to_be_armed_with_it_8011c620 = MEMORY.ref(2, 0x8011c620L, LodString::new);
  public static final LodString Is_armed_8011c670 = MEMORY.ref(2, 0x8011c670L, LodString::new);
  public static final LodString Put_in_the_bag_8011c684 = MEMORY.ref(2, 0x8011c684L, LodString::new);

  public static final LodString Buy_8011c6a4 = MEMORY.ref(2, 0x8011c6a4L, LodString::new);
  public static final LodString Sell_8011c6ac = MEMORY.ref(2, 0x8011c6acL, LodString::new);
  public static final LodString Carried_8011c6b8 = MEMORY.ref(2, 0x8011c6b8L, LodString::new);
  public static final LodString Leave_8011c6c8 = MEMORY.ref(2, 0x8011c6c8L, LodString::new);
  public static final LodString Cannot_be_armed_with_8011c6d4 = MEMORY.ref(2, 0x8011c6d4L, LodString::new);

  public static final LodString Number_kept_8011c7f4 = MEMORY.ref(2, 0x8011c7f4L, LodString::new);
  public static final LodString Note_8011c814 = MEMORY.ref(2, 0x8011c814L, LodString::new);
  public static final LodString Stay_8011c820 = MEMORY.ref(2, 0x8011c820L, LodString::new);
  public static final LodString Half_8011c82c = MEMORY.ref(2, 0x8011c82cL, LodString::new);
  public static final LodString Off_8011c838 = MEMORY.ref(2, 0x8011c838L, LodString::new);
  /**
   * "Really want"
   * "to throw"
   * "this away?"
   */
  public static final LodString Really_want_to_throw_this_away_8011c8d4 = MEMORY.ref(2, 0x8011c8d4L, LodString::new);
  /** "Save new game?" */
  public static final LodString Save_new_game_8011c9c8 = MEMORY.ref(2, 0x8011c9c8L, LodString::new);
  /** "Overwrite save?" */
  public static final LodString Overwrite_save_8011c9e8 = MEMORY.ref(2, 0x8011c9e8L, LodString::new);
  /** "Load this data?" */
  public static final LodString Load_this_data_8011ca08 = MEMORY.ref(2, 0x8011ca08L, LodString::new);
  /** "Saved" */
  public static final LodString Saved_8011cb2c = MEMORY.ref(2, 0x8011cb2cL, LodString::new);
  public static final LodString AcquiredGold_8011cdd4 = new LodString("Acquired Gold");
  /** "Status" */
  public static final LodString Status_8011ceb4 = MEMORY.ref(2, 0x8011ceb4L, LodString::new);
  /** "Item" */
  public static final LodString Item_8011cec4 = MEMORY.ref(2, 0x8011cec4L, LodString::new);
  /** "Armed" */
  public static final LodString Armed_8011ced0 = MEMORY.ref(2, 0x8011ced0L, LodString::new);
  /** "Addition" */
  public static final LodString Addition_8011cedc = MEMORY.ref(2, 0x8011cedcL, LodString::new);
  /** "Replace" */
  public static final LodString Replace_8011cef0 = MEMORY.ref(2, 0x8011cef0L, LodString::new);
  /** "Config" */
  public static final LodString Config_8011cf00 = MEMORY.ref(2, 0x8011cf00L, LodString::new);
  /** "Save" */
  public static final LodString Save_8011cf10 = MEMORY.ref(2, 0x8011cf10L, LodString::new);
  /** "Use it" */
  public static final LodString Use_it_8011cf1c = MEMORY.ref(2, 0x8011cf1cL, LodString::new);
  /** "Discard" */
  public static final LodString Discard_8011cf2c = MEMORY.ref(2, 0x8011cf2cL, LodString::new);
  /** "List" */
  public static final LodString List_8011cf3c = MEMORY.ref(2, 0x8011cf3cL, LodString::new);
  /** "Goods" */
  public static final LodString Goods_8011cf48 = MEMORY.ref(2, 0x8011cf48L, LodString::new);
  public static final LodString Vibrate_8011cf58 = MEMORY.ref(2, 0x8011cf58L, LodString::new);
  public static final LodString Off_8011cf6c = MEMORY.ref(2, 0x8011cf6cL, LodString::new);
  public static final LodString On_8011cf74 = MEMORY.ref(2, 0x8011cf74L, LodString::new);
  public static final LodString Sound_8011cf7c = MEMORY.ref(2, 0x8011cf7cL, LodString::new);
  public static final LodString Stereo_8011cf88 = MEMORY.ref(2, 0x8011cf88L, LodString::new);
  public static final LodString Mono_8011cf98 = MEMORY.ref(2, 0x8011cf98L, LodString::new);
  public static final LodString Morph_8011cfa4 = MEMORY.ref(2, 0x8011cfa4L, LodString::new);
  public static final LodString Normal_8011cfb0 = MEMORY.ref(2, 0x8011cfb0L, LodString::new);
  public static final LodString Short_8011cfc0 = MEMORY.ref(2, 0x8011cfc0L, LodString::new);
  public static final LodString HP_recovered_for_all_8011cfcc = MEMORY.ref(2, 0x8011cfccL, LodString::new);
  public static final LodString MP_recovered_for_all_8011cff8 = MEMORY.ref(2, 0x8011cff8L, LodString::new);
  public static final LodString Press_to_sort_8011d024 = MEMORY.ref(2, 0x8011d024L, LodString::new);
  public static final LodString DigDabas_8011d04c = new LodString("Diiig Dabas!");
  public static final LodString AcquiredItems_8011d050 = new LodString("Acquired Items");
  public static final LodString SpecialItem_8011d054 = new LodString("Special Item");
  public static final LodString Take_8011d058 = new LodString("Take");
  public static final LodString Discard_8011d05c = new LodString("Discard");
  public static final LodString NextDig_8011d064 = new LodString("Next Dig");
  public static final LodString Completely_recovered_8011d534 = MEMORY.ref(2, 0x8011d534L, LodString::new);
  public static final LodString Recovered_8011d560 = MEMORY.ref(2, 0x8011d560L, LodString::new);
  public static final LodString HP_8011d57c = MEMORY.ref(2, 0x8011d57cL, LodString::new);
  public static final LodString MP_8011d584 = MEMORY.ref(2, 0x8011d584L, LodString::new);
  public static final LodString SP_8011d58c = MEMORY.ref(2, 0x8011d58cL, LodString::new);
  public static final LodString Encounter_risk_reduced_8011d594 = MEMORY.ref(2, 0x8011d594L, LodString::new);
  public static final LodString Detoxified_8011d5c8 = MEMORY.ref(2, 0x8011d5c8L, LodString::new);
  public static final LodString Spirit_recovered_8011d5e0 = MEMORY.ref(2, 0x8011d5e0L, LodString::new);
  public static final LodString Fear_gone_8011d604 = MEMORY.ref(2, 0x8011d604L, LodString::new);
  public static final LodString Nothing_happened_8011d618 = MEMORY.ref(2, 0x8011d618L, LodString::new);

  public static final IntRef charSlot_8011d734 = MEMORY.ref(4, 0x8011d734L, IntRef::new);
  public static final IntRef selectedMenuOption_8011d738 = MEMORY.ref(4, 0x8011d738L, IntRef::new);
  public static final IntRef selectedItemSubmenuOption_8011d73c = MEMORY.ref(4, 0x8011d73cL, IntRef::new);
  public static final IntRef selectedSlot_8011d740 = MEMORY.ref(4, 0x8011d740L, IntRef::new);
  /** The first save game displayed on the menu, increments as you scroll down */
  public static final IntRef slotScroll_8011d744 = MEMORY.ref(4, 0x8011d744L, IntRef::new);
  public static final IntRef slotScroll_8011d748 = MEMORY.ref(4, 0x8011d748L, IntRef::new);
  public static final IntRef menuIndex_8011d74c = MEMORY.ref(4, 0x8011d74cL, IntRef::new);
  public static final IntRef count_8011d750 = MEMORY.ref(4, 0x8011d750L, IntRef::new);
  public static final Value _8011d754 = MEMORY.ref(4, 0x8011d754L);

  public static final UnsignedByteRef characterCount_8011d7c4 = MEMORY.ref(1, 0x8011d7c4L, UnsignedByteRef::new);

  public static final ArrayRef<MenuItemStruct04> menuItems_8011d7c8 = MEMORY.ref(1, 0x8011d7c8L, ArrayRef.of(MenuItemStruct04.class, 0x100, 0x4, MenuItemStruct04::new));

  public static final Value canSave_8011dc88 = MEMORY.ref(1, 0x8011dc88L);

  public static final Value _8011dc8c = MEMORY.ref(4, 0x8011dc8cL);
  public static final MessageBox20 messageBox_8011dc90 = new MessageBox20();

  public static final ArrayRef<Pointer<ArrayRef<MenuItemStruct04>>> _8011dcb8 = MEMORY.ref(4, 0x8011dcb8L, ArrayRef.of(Pointer.classFor(ArrayRef.classFor(MenuItemStruct04.class)), 2, 4, Pointer.deferred(4, ArrayRef.of(MenuItemStruct04.class, 0x130, 0x4, MenuItemStruct04::new))));

  public static final BoolRef _8011dcfc = MEMORY.ref(1, 0x8011dcfcL, BoolRef::new);

  public static final ArrayRef<MenuAdditionInfo> additions_8011e098 = MEMORY.ref(1, 0x8011e098L, ArrayRef.of(MenuAdditionInfo.class, 9, 0x2, MenuAdditionInfo::new));

  public static final IntRef menuIndex_8011e0d8 = MEMORY.ref(4, 0x8011e0d8L, IntRef::new);
  public static final IntRef menuIndex_8011e0dc = MEMORY.ref(4, 0x8011e0dcL, IntRef::new);
  public static final IntRef menuIndex_8011e0e0 = MEMORY.ref(4, 0x8011e0e0L, IntRef::new);
  public static final IntRef menuScroll_8011e0e4 = MEMORY.ref(4, 0x8011e0e4L, IntRef::new);
  public static final IntRef menuOption_8011e0e8 = MEMORY.ref(4, 0x8011e0e8L, IntRef::new);
  public static final IntRef menuOption_8011e0ec = MEMORY.ref(4, 0x8011e0ecL, IntRef::new);
  public static Renderable58 renderable_8011e0f0;
  public static Renderable58 renderable_8011e0f4;
  public static final UnboundedArrayRef<MenuItemStruct04> menuItems_8011e0f8 = MEMORY.ref(4, 0x8011e0f8L, UnboundedArrayRef.of(0x4, MenuItemStruct04::new));

  public static final UnsignedByteRef currentShopItemCount_8011e13c = MEMORY.ref(1, 0x8011e13cL, UnsignedByteRef::new);
  /**
   * <ul>
   *   <li>0x0 - Item Shop</li>
   *   <li>0x1 - Weapon Shop</li>
   * </ul>
  */
  public static final Value shopType_8011e13d = MEMORY.ref(1, 0x8011e13dL);
  public static final Value _8011e13e = MEMORY.ref(1, 0x8011e13eL);

  public static final Renderable58[] characterRenderables_8011e148 = new Renderable58[9];

  public static final Value _8011e170 = MEMORY.ref(1, 0x8011e170L);

  public static final UnsignedByteRef xpDivisor_8011e174 = MEMORY.ref(1, 0x8011e174L, UnsignedByteRef::new);

  public static final Value _8011e178 = MEMORY.ref(4, 0x8011e178L);
  public static final Value soundTick_8011e17c = MEMORY.ref(4, 0x8011e17cL);
  public static final ArrayRef<IntRef> pendingXp_8011e180 = MEMORY.ref(4, 0x8011e180L, ArrayRef.of(IntRef.class, 10, 4, IntRef::new));

  public static final ArrayRef<UnsignedByteRef> spellsUnlocked_8011e1a8 = MEMORY.ref(1, 0x8011e1a8L, ArrayRef.of(UnsignedByteRef.class, 10, 1, UnsignedByteRef::new));

  public static final ArrayRef<UnsignedByteRef> additionsUnlocked_8011e1b8 = MEMORY.ref(1, 0x8011e1b8L, ArrayRef.of(UnsignedByteRef.class, 10, 1, UnsignedByteRef::new));

  public static final Value _8011e1c8 = MEMORY.ref(1, 0x8011e1c8L);

  public static final Value _8011e1d8 = MEMORY.ref(1, 0x8011e1d8L);

  public static final EnumRef<MessageBoxResult> msgboxResult_8011e1e8 = MEMORY.ref(4, 0x8011e1e8L, EnumRef.of(MessageBoxResult.values()));

  public static final IntRef menuIndex_8011e1f0 = MEMORY.ref(4, 0x8011e1f0L, IntRef::new);
  public static final IntRef slotIndex_8011e1f4 = MEMORY.ref(4, 0x8011e1f4L, IntRef::new);
  public static final IntRef slotScroll_8011e1f8 = MEMORY.ref(4, 0x8011e1f8L, IntRef::new);
  public static final IntRef menuIndex_8011e1fc = MEMORY.ref(4, 0x8011e1fcL, IntRef::new);

  public static Renderable58 renderable_8011e200;
  public static Renderable58 renderable_8011e204;
  public static Renderable58 renderable_8011e208;

  public static final List<Tuple<String, SavedGameDisplayData>> saves = new ArrayList<>();

  @Method(0x800fbd78L)
  public static void allocatePlayerBattleObjects() {
    //LAB_800fbdb8
    for(charCount_800c677c.set(0); charCount_800c677c.get() < 3; charCount_800c677c.incr()) {
      if(gameState_800babc8.charIndex_88.get(charCount_800c677c.get()).get() < 0) {
        break;
      }
    }

    //LAB_800fbde8
    final long fp = _80111d38.offset(charCount_800c677c.get() * 0xcL).getAddress();
    final int[] charIndices = new int[charCount_800c677c.get()];

    //LAB_800fbe18
    for(int charSlot = 0; charSlot < charCount_800c677c.get(); charSlot++) {
      charIndices[charSlot] = addCombatant(0x200L + gameState_800babc8.charIndex_88.get(charSlot).get() * 0x2L, charSlot);
    }

    //LAB_800fbe4c
    //LAB_800fbe70
    for(int charSlot = 0; charSlot < charCount_800c677c.get(); charSlot++) {
      final int charIndex = gameState_800babc8.charIndex_88.get(charSlot).get();
      final int bobjIndex = allocateScriptState(charSlot + 6, 0x27c, false, null, 0, BattleObject27c::new);
      setScriptTicker(bobjIndex, getTriConsumerAddress(Bttl_800c.class, "bobjTicker", int.class, ScriptState.classFor(BattleObject27c.class), BattleObject27c.class));
      setScriptDestructor(bobjIndex, getTriConsumerAddress(Bttl_800c.class, "bobjDestructor", int.class, ScriptState.classFor(BattleObject27c.class), BattleObject27c.class));
      _8006e398.bobjIndices_e0c.get(_800c66d0.get()).set(bobjIndex);
      _8006e398.charBobjIndices_e40.get(charSlot).set(bobjIndex);
      final BattleObject27c bobj = scriptStatePtrArr_800bc1c0.get(bobjIndex).deref().innerStruct_00.derefAs(BattleObject27c.class);
      bobj.magic_00.set(BattleScriptDataBase.BOBJ);
      bobj.combatant_144.set(getCombatant((short)charIndices[charSlot]));
      bobj.charIndex_272.set((short)charIndex);
      bobj.charSlot_276.set((short)charSlot);
      bobj.combatantIndex_26c.set((short)charIndices[charSlot]);
      bobj._274.set((short)_800c66d0.get());
      bobj.model_148.coord2_14.coord.transfer.setX((int)MEMORY.ref(2, fp).offset(charSlot * 0x4L).offset(0x0L).getSigned());
      bobj.model_148.coord2_14.coord.transfer.setY(0);
      bobj.model_148.coord2_14.coord.transfer.setZ((int)MEMORY.ref(2, fp).offset(charSlot * 0x4L).offset(0x2L).getSigned());
      bobj.model_148.coord2Param_64.rotate.set((short)0, (short)0x400, (short)0);
      _800c66d0.incr();
    }

    //LAB_800fbf6c
    _8006e398.bobjIndices_e0c.get(_800c66d0.get()).set(-1);
    _8006e398.charBobjIndices_e40.get(charCount_800c677c.get()).set(-1);

    FUN_800f863c();
    decrementOverlayCount();
  }

  @Method(0x800fbfe0L)
  public static void loadEncounterAssets() {
    loadSupportOverlay(2, () -> SItem.loadEnemyTextures(2625 + encounterId_800bb0f8.get()));

    //LAB_800fc030
    for(int i = 0; i < combatantCount_800c66a0.get(); i++) {
      if(getCombatant(i).charSlot_19c.get() < 0) { // I think this means it's not a player
        loadCombatantTmdAndAnims(i);
      }

      //LAB_800fc050
    }

    //LAB_800fc064
    //LAB_800fc09c
    for(int i = 0; i < charCount_800c677c.get(); i++) {
      combatants_8005e398.get(scriptStatePtrArr_800bc1c0.get(_8006e398.charBobjIndices_e40.get(i).get()).deref().innerStruct_00.derefAs(BattleObject27c.class).combatantIndex_26c.get()).flags_19e.or(0x2a);
    }

    //LAB_800fc104
    final int charCount = charCount_800c677c.get();

    final int[] files = new int[charCount];
    final int[] slots = new int[charCount];

    charLoop:
    for(int charSlot = 0; charSlot < charCount; charSlot++) {
      for(int permGroup = 0; permGroup < 9; permGroup++) {
        for(int perm = 0; perm < 9; perm++) {
          for(int permSlot = 0; permSlot < 3; permSlot++) {
            final PartyPermutation08 permutation = partyPermutations_80111d68.get(permGroup).get(perm);

            if(permutation.charIndices_02.get(permSlot).get() == gameState_800babc8.charIndex_88.get(charSlot).get()) {
              files[charSlot] = permutation.drgn0File_00.get() - 3537;
              slots[charSlot] = permSlot;
              continue charLoop;
            }
          }
        }
      }
    }

    int permIndices = 0;
    for(int charSlot = 0; charSlot < charCount; charSlot++) {
      permIndices |= files[charSlot] << charSlot * 8;
      permIndices |= slots[charSlot] << 24 + charSlot * 2;
    }

    final int pi = permIndices;
    loadSupportOverlay(2, () -> SItem.deferLoadPartyPermutationTimMrg(pi));
    loadSupportOverlay(2, () -> SItem.deferLoadPartyPermutationTmdMrg(pi));
    _800bc960.oru(0x400L);
    decrementOverlayCount();
  }

  @Method(0x800fc210L)
  public static void loadPartyPermutationTmdMrg(final List<byte[]> files, final int charSlot) {
    //LAB_800fc260
    final BattleObject27c data = scriptStatePtrArr_800bc1c0.get(_8006e398.charBobjIndices_e40.get(charSlot).get()).deref().innerStruct_00.derefAs(BattleObject27c.class);
    final CombatantStruct1a8 combatant = data.combatant_144.deref();

    //LAB_800fc298
    int s0 = combatant.charSlot_19c.get() & 0x7f;
    s0 = s0 | (data.combatantIndex_26c.get() & 0x3f) << 9;
    s0 = s0 & 0xffff_feff;

    combatantTmdAndAnimLoadedCallback(files, s0);

    //LAB_800fc34c
    _800bc960.oru(0x4L);
    decrementOverlayCount();
  }

  @Method(0x800fc3c0L)
  public static void loadEnemyTextures(final int fileIndex) {
    // Example file: 2856
    loadDrgnDir(0, fileIndex, SItem::enemyTexturesLoadedCallback, 0);
  }

  @Method(0x800fc404L)
  public static void enemyTexturesLoadedCallback(final List<byte[]> files, final int param) {
    final long s2 = _1f8003f4.getPointer(); //TODO

    //LAB_800fc434
    for(int i = 0; i < combatantCount_800c66a0.get(); i++) {
      final CombatantStruct1a8 a0 = getCombatant(i);

      if(a0.charSlot_19c.get() < 0) {
        final long a2 = a0.charIndex_1a2.get() & 0x1ffL;

        //LAB_800fc464
        for(int enemySlot = 0; enemySlot < 3; enemySlot++) {
          if((MEMORY.ref(2, s2).offset(enemySlot * 0x2L).get() & 0x1ffL) == a2 && files.get(enemySlot).length != 0) {
            final long tim = mallocTail(files.get(enemySlot).length);
            MEMORY.setBytes(tim, files.get(enemySlot));
            loadCombatantTim(i, tim);
            free(tim);
            break;
          }
        }
      }
    }

    //LAB_800fc4cc
    decrementOverlayCount();
  }

  @Method(0x800fc504L)
  public static void deferLoadPartyPermutationTimMrg(final int permIndices) {
    for(int charSlot = 0; charSlot < charCount_800c677c.get(); charSlot++) {
      final int file = permIndices >> charSlot * 8 & 0xff;
      final int slot = permIndices >> 24 + charSlot * 2 & 0x3;

      loadDrgnDir(0, (3537 + file) + "/" + slot, SItem::loadPartyPermutationTimMrg, charSlot);
    }
  }

  @Method(0x800fc548L)
  public static void loadPartyPermutationTimMrg(final List<byte[]> files, final int charSlot) {
    final long tim = mallocTail(files.get(0).length);
    MEMORY.setBytes(tim, files.get(0));

    final BattleObject27c bobj = scriptStatePtrArr_800bc1c0.get(_8006e398.charBobjIndices_e40.get(charSlot).get()).deref().innerStruct_00.derefAs(BattleObject27c.class);
    loadCombatantTim(bobj.combatantIndex_26c.get(), tim);

    free(tim);
    decrementOverlayCount();
  }

  @Method(0x800fc654L)
  public static void deferLoadPartyPermutationTmdMrg(final int permIndices) {
    for(int charSlot = 0; charSlot < charCount_800c677c.get(); charSlot++) {
      final int file = permIndices >> charSlot * 8 & 0xff;
      final int slot = permIndices >> 24 + charSlot * 2 & 0x3;

      loadDrgnDir(0, (3537 + file + 1) + "/" + slot, SItem::loadPartyPermutationTmdMrg, charSlot);
    }
  }

  @Method(0x800fc698L)
  public static int getXpToNextLevel(final int charIndex) {
    if(charIndex == -1 || charIndex > 8) {
      //LAB_800fc6a4
      throw new RuntimeException("Character index " + charIndex + " out of bounds");
    }

    //LAB_800fc6ac
    final int level = gameState_800babc8.charData_32c.get(charIndex).level_12.get();

    if(level >= 60) {
      return 0; // Max level
    }

    final ArrayRef<IntRef> table = switch(charIndex) {
      case 0    -> dartXpTable_801135e4;
      case 1, 5 -> lavitzXpTable_801138c0;
      case 2, 8 -> shanaXpTable_80113aa8;
      case 3    -> roseXpTable_801139b4;
      case 4    -> haschelXpTable_801136d8;
      case 6    -> meruXpTable_801137cc;
      case 7    -> kongolXpTable_801134f0;
      default -> throw new RuntimeException("Impossible");
    };

    //LAB_800fc70c
    return table.get(level + 1).get();
  }

  @Method(0x800fc78cL)
  public static int getMenuOptionY(final int option) {
    return 65 + option * 13;
  }

  @Method(0x800fc7a4L)
  public static int getItemSubmenuOptionY(final int option) {
    return 80 + option * 13;
  }

  @Method(0x800fc7bcL)
  public static int FUN_800fc7bc(final int a0) {
    return 130 + a0 * 56;
  }

  @Method(0x800fc7d0L)
  public static int FUN_800fc7d0(final int a0) {
    return 130 + a0 * 46;
  }

  @Method(0x800fc7ecL)
  public static int menuOptionY(final int a0) {
    return 107 + a0 * 13;
  }

  @Method(0x800fc814L)
  public static int FUN_800fc814(final int a0) {
    return 9 + a0 * 17;
  }

  @Method(0x800fc824L)
  public static int FUN_800fc824(final int a0) {
    if(a0 == 0) {
      return 43;
    }

    return 221;
  }

  @Method(0x800fc838L)
  public static int getAdditionSlotY(final int a0) {
    return 113 + a0 * 14;
  }

  @Method(0x800fc84cL)
  public static int getSlotY(final int slot) {
    return 16 + slot * 72;
  }

  @Method(0x800fc860L)
  public static int FUN_800fc860(final int a0) {
    return 180 + a0 * 17;
  }

  @Method(0x800fc880L)
  public static int FUN_800fc880(int a0) {
    if(a0 >= 3) {
      a0 -= 3;
    }

    //LAB_800fc890
    return 198 + a0 * 57;
  }

  @Method(0x800fc8a8L)
  public static int FUN_800fc8a8(final int a0) {
    //LAB_800fc8b8
    return a0 >= 3 ? 122 : 16;
  }

  @Method(0x800fc8c0L)
  public static int getCharacterPortraitX(final int slot) {
    return 21 + slot * 50;
  }

  @Method(0x800fc8dcL)
  public static int getItemSlotY(final int slot) {
    return 18 + slot * 17;
  }

  @Method(0x800fc900L)
  public static Renderable58 FUN_800fc900(final int option) {
    final Renderable58 renderable = allocateUiElement(116, 116, 122, getItemSubmenuOptionY(option) - 2);
    FUN_80104b60(renderable);
    return renderable;
  }

  @Method(0x800fc944L)
  public static void menuAssetsLoaded(final long address, final int size, final int whichFile) {
    if(whichFile == 0) {
      //LAB_800fc98c
      FUN_80022a94(MEMORY.ref(4, address).offset(0x83e0L)); // Character textures
      FUN_80022a94(MEMORY.ref(4, address)); // Menu textures
      FUN_80022a94(MEMORY.ref(4, address).offset(0x6200L)); // Item textures
      FUN_80022a94(MEMORY.ref(4, address).offset(0x1_0460L));
      FUN_80022a94(MEMORY.ref(4, address).offset(0x1_0580L));
      deferReallocOrFree(address, 0, 1);
    } else if(whichFile == 1) {
      //LAB_800fc9e4
      drgn0_6666FilePtr_800bdc3c.setPointer(address);
    }

    //LAB_800fc9fc
  }

  public static void fadeOutArrows() {
    if(renderablePtr_800bdba4 != null) {
      fadeOutArrow(renderablePtr_800bdba4);
      renderablePtr_800bdba4 = null;
    }

    //LAB_800fca40
    if(renderablePtr_800bdba8 != null) {
      fadeOutArrow(renderablePtr_800bdba8);
      renderablePtr_800bdba8 = null;
    }

    //LAB_800fca60
    if(saveListUpArrow_800bdb94 != null) {
      fadeOutArrow(saveListUpArrow_800bdb94);
      saveListUpArrow_800bdb94 = null;
    }

    //LAB_800fca80
    if(saveListDownArrow_800bdb98 != null) {
      fadeOutArrow(saveListDownArrow_800bdb98);
      saveListDownArrow_800bdb98 = null;
    }
  }

  @Method(0x800fca0cL)
  public static void FUN_800fca0c(final InventoryMenuState nextMenuState, final long a1) {
    fadeOutArrows();

    //LAB_800fcaa4
    inventoryMenuState_800bdc28.set(InventoryMenuState._123);
    _800bdc2c.setu(a1);
    confirmDest_800bdc30.set(nextMenuState);
  }

  @Method(0x800fcad4L)
  public static void renderMenus() {
    final long v0;
    final long a0;
    final long s1;

    inventoryJoypadInput_800bdc44.setu(getJoypadInputByPriority());

    switch(inventoryMenuState_800bdc28.get()) {
      case INIT_0: // Initialize, loads some files (unknown contents)
        _800bdc34.setu(0);
        messageBox_8011dc90.state_0c = 0;
        loadCharacterStats(0);

        if(mainCallbackIndex_8004dd20.get() == 0x8L) {
          gameState_800babc8.isOnWorldMap_4e4.set(1);
          canSave_8011dc88.setu(0x1L);
        } else {
          //LAB_800fcbfc
          gameState_800babc8.isOnWorldMap_4e4.set(0);
          canSave_8011dc88.setu(standingInSavePoint_8005a368);
        }

        //LAB_800fcc10
        selectedMenuOption_8011d738.set(0);
        selectedItemSubmenuOption_8011d73c.set(0);
        inventoryMenuState_800bdc28.set(InventoryMenuState.AWAIT_INIT_1);
        break;

      case AWAIT_INIT_1:
        if(!drgn0_6666FilePtr_800bdc3c.isNull()) {
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
          _8011dcfc.set((gameState_800babc8.dragoonSpirits_19c.get(1).get() & 0x4L) > 0);
          gameState_800babc8.vibrationEnabled_4e1.and(1);
        }
        break;

      case _2:
        deallocateRenderables(0xffL);

        //LAB_800fccbc
        if(whichMenu_800bdc38 == WhichMenu.RENDER_CHAR_SWAP_MENU_24) { // Character swap screen
          //LAB_800fcce0
          recalcInventory();
          FUN_80103b10();
          inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
          break;
        }

        //LAB_800fcd00
        recalcInventory();
        FUN_80103b10();
        scriptStartEffect(0x2L, 0xaL);
        inventoryMenuState_800bdc28.set(InventoryMenuState.INIT_MAIN_MENU_3);
        break;

      case INIT_MAIN_MENU_3:
        renderGlyphs(glyphs_80114130, 0, 0);
        selectedMenuOptionRenderablePtr_800bdbe0 = allocateUiElement(115, 115, 29, getMenuOptionY(selectedMenuOption_8011d738.get()));
        FUN_80104b60(selectedMenuOptionRenderablePtr_800bdbe0);
        renderInventoryMenu(selectedMenuOption_8011d738.get(), 4, 0xffL);
        inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
        break;

      case MAIN_MENU_4: // Main inventory menu
        if(messageBox(messageBox_8011dc90) != MessageBoxResult.AWAITING_INPUT) {
          if((inventoryJoypadInput_800bdc44.get() & 0x40) != 0) { // Circle
            playSound(0x3L);
            FUN_800fca0c(InventoryMenuState._125, 0x1L);
          }

          //LAB_800fcdd4
          if(handleMenuUpDown(selectedMenuOption_8011d738, 8)) {
            selectedItemSubmenuOption_8011d73c.set(0);
            selectedMenuOptionRenderablePtr_800bdbe0.y_44 = getMenuOptionY(selectedMenuOption_8011d738.get());
          }

          //LAB_800fce08
          if((inventoryJoypadInput_800bdc44.get() & 0x2020) != 0) { // Right or cross
            final int menuOption;
            if((inventoryJoypadInput_800bdc44.get() & 0x2000) != 0) { // Right
              menuOption = selectedMenuOption_8011d738.get() + 10;
            } else {
              //LAB_800fce30
              menuOption = selectedMenuOption_8011d738.get();
            }

            //LAB_800fce34
            switch(menuOption) {
              case 0 -> {
                playSound(2);
                FUN_800fca0c(InventoryMenuState.STATUS_INIT_20, 0x1L);
              }

              case 1, 11 -> {
                playSound(4);
                selectedMenuOptionRenderablePtr_800bdbe4 = FUN_800fc900(selectedItemSubmenuOption_8011d73c.get());
                inventoryMenuState_800bdc28.set(InventoryMenuState._5);
              }

              case 2 -> {
                playSound(2);
                FUN_800fca0c(InventoryMenuState.EQUIPMENT_INIT_12, 0x1L);
              }

              case 3 -> {
                playSound(2);
                charSlot_8011d734.set(0);
                FUN_800fca0c(InventoryMenuState.ADDITIONS_INIT_23, 0x1L);
              }

              case 4 -> {
                playSound(2);
                FUN_800fca0c(InventoryMenuState.REPLACE_INIT_8, 0x1L);
              }

              case 5 -> {
                playSound(4);
                selectedItemSubmenuOption_8011d73c.set(0);
                selectedMenuOptionRenderablePtr_800bdbe4 = null;
                setMessageBoxText(messageBox_8011dc90, null, 0x1);
                inventoryMenuState_800bdc28.set(InventoryMenuState.CONFIG_6);
              }

              case 6 -> {
                if(canSave_8011dc88.get() != 0) {
                  playSound(2);
                  FUN_800fca0c(InventoryMenuState.INIT_LOAD_GAME_37, 0x1L);
                } else {
                  playSound(40);
                }
              }

              case 7 -> {
                playSound(0x2L);
                setMessageBoxText(messageBox_8011dc90, new LodString("Return to main menu?\nAlpha - do not use"), 2);
                inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4_QUIT_CONFIRM);
              }
            }
          }
        }

        //LAB_800fcf54
        //LAB_800fcf58
        if(selectedMenuOption_8011d738.get() == 1) {
          renderItemSubmenu(0xff, 6);
        }

        //LAB_800fcf70
        FUN_80102484(0);

        //LAB_800fd344
        renderInventoryMenu(selectedMenuOption_8011d738.get(), 4, 0);
        break;

      case MAIN_MENU_4_QUIT_CONFIRM:
        switch(messageBox(messageBox_8011dc90)) {
          case YES -> {
            FUN_800fca0c(InventoryMenuState.MAIN_MENU_4_QUIT, 0x1L);
          }

          case NO -> {
            inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
          }
        }

        FUN_80102484(0);
        renderInventoryMenu(selectedMenuOption_8011d738.get(), 4, 0);
        break;

      case MAIN_MENU_4_QUIT:
        deallocateRenderables(0xffL);
        free(drgn0_6666FilePtr_800bdc3c.getPointer());
        scriptStartEffect(2, 10);
        Ttle.test();
        break;

      case _5: // "Item" inventory submenu
        if((inventoryJoypadInput_800bdc44.get() & 0x8040L) != 0) { // Left or circle
          playSound(0x3L);
          inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
          unloadRenderable(selectedMenuOptionRenderablePtr_800bdbe4);
        }

        //LAB_800fcfc0
        if(handleMenuUpDown(selectedItemSubmenuOption_8011d73c, 5)) {
          selectedMenuOptionRenderablePtr_800bdbe4.y_44 = getItemSubmenuOptionY(selectedItemSubmenuOption_8011d73c.get()) - 2;
        }

        //LAB_800fcff0
        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0) { // Cross
          playSound(0x2L);
          final int menuIndex = selectedItemSubmenuOption_8011d73c.get();
          if(menuIndex == 0) {
            FUN_800fca0c(InventoryMenuState.USE_ITEM_MENU_INIT_26, 2);
          } else if(menuIndex == 1) {
            FUN_800fca0c(InventoryMenuState._31, 2);
          } else if(menuIndex == 2) {
            FUN_800fca0c(InventoryMenuState._16, 2);
          } else if(menuIndex == 3) {
            FUN_800fca0c(InventoryMenuState._35, 2);
          } else if(menuIndex == 4) {
            FUN_800fca0c(InventoryMenuState.DABAS_INIT_72, 2);
          }
        }

        //LAB_800fd060
        FUN_80102484(0x1L);
        renderItemSubmenu(selectedItemSubmenuOption_8011d73c.get(), 4);
        renderInventoryMenu(selectedMenuOption_8011d738.get(), 6, 0);
        break;

      case CONFIG_6:
        messageBox(messageBox_8011dc90);

        if(messageBox_8011dc90.ticks_10 >= 2) {
          if(selectedMenuOptionRenderablePtr_800bdbe4 == null) {
            selectedMenuOptionRenderablePtr_800bdbe4 = allocateUiElement(0x74, 0x74, FUN_800fc7bc(0) - 34, menuOptionY(0) - 2);
            FUN_80104b60(selectedMenuOptionRenderablePtr_800bdbe4);
            selectedMenuOptionRenderablePtr_800bdbe4.z_3c = 32;
          }

          //LAB_800fd100
          if(handleMenuUpDown(selectedItemSubmenuOption_8011d73c, 4)) {
            selectedMenuOptionRenderablePtr_800bdbe4.y_44 = menuOptionY(selectedItemSubmenuOption_8011d73c.get()) - 2;
          }

          //LAB_800fd130
          if((joypadPress_8007a398.get() & 0x8000L) != 0) {
            playSound(0x2L);
            a0 = selectedItemSubmenuOption_8011d73c.get();

            //LAB_800fd174
            if(a0 == 0) {
              //LAB_800fd18c
              gameState_800babc8.vibrationEnabled_4e1.set(0);
            } else if(a0 == 1) {
              //LAB_800fd1a0
              gameState_800babc8.mono_4e0.set(0);
              setMono(0);
            } else if(a0 == 2) {
              //LAB_800fd1b8
              gameState_800babc8.morphMode_4e2.set(0);
            } else if(a0 == 3) {
              //LAB_800fd1c4
              if(gameState_800babc8.indicatorMode_4e8.get() != 0) {
                gameState_800babc8.indicatorMode_4e8.decr();
              }
            }
          }

          //LAB_800fd1e0
          //LAB_800fd1e4
          if((joypadPress_8007a398.get() & 0x2000L) != 0) {
            playSound(0x2L);
            final int v1 = selectedItemSubmenuOption_8011d73c.get();

            //LAB_800fd22c
            if(v1 == 0) {
              //LAB_800fd244
              gameState_800babc8.vibrationEnabled_4e1.set(1);
              FUN_8002bcc8(0, 0x100L);
              FUN_8002bda4(0, 0, 0x3cL);
            } else if(v1 == 1) {
              //LAB_800fd278
              gameState_800babc8.mono_4e0.set(1);
              setMono(1);
            } else if(v1 == 2) {
              //LAB_800fd290
              gameState_800babc8.morphMode_4e2.set(1);
            } else if(v1 == 3) {
              //LAB_800fd29c
              if(gameState_800babc8.indicatorMode_4e8.get() < 2) {
                gameState_800babc8.indicatorMode_4e8.incr();
              }
            }
          }

          //LAB_800fd2bc
          //LAB_800fd2c0
          if((inventoryJoypadInput_800bdc44.get() & 0x40L) != 0) {
            playSound(0x2L);
            inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
            messageBox_8011dc90.state_0c++;
            unloadRenderable(selectedMenuOptionRenderablePtr_800bdbe4);
          }

          //LAB_800fd30c
          renderOptionsMenu(selectedItemSubmenuOption_8011d73c.get(), gameState_800babc8.vibrationEnabled_4e1.get(), gameState_800babc8.mono_4e0.get(), gameState_800babc8.morphMode_4e2.get(), gameState_800babc8.indicatorMode_4e8.get());
        }

        //LAB_800fd330
        FUN_80102484(0);

        //LAB_800fd344
        renderInventoryMenu(selectedMenuOption_8011d738.get(), 4, 0);
        break;

      case _7:
        deallocateRenderables(0xffL);
        renderGlyphs(glyphs_80114130, 0, 0);
        selectedMenuOptionRenderablePtr_800bdbe0 = allocateUiElement(0x73, 0x73, 29, getMenuOptionY(selectedMenuOption_8011d738.get()));
        selectedMenuOptionRenderablePtr_800bdbe4 = FUN_800fc900(selectedItemSubmenuOption_8011d73c.get());
        FUN_80104b60(selectedMenuOptionRenderablePtr_800bdbe0);
        renderInventoryMenu(selectedMenuOption_8011d738.get(), 4, 0xffL);
        scriptStartEffect(0x2L, 0xaL);
        FUN_80102484(0x1L);
        inventoryMenuState_800bdc28.set(InventoryMenuState._5);
        break;

      case REPLACE_INIT_8:
        scriptStartEffect(0x2L, 0xaL);
        selectedSlot_8011d740.set(0);
        slotScroll_8011d744.set(0);
        inventoryMenuState_800bdc28.set(InventoryMenuState._9);
        break;

      case _9:
        deallocateRenderables(0xffL);
        renderGlyphs(glyphs_80114160, 0, 0);
        highlightLeftHalf_800bdbe8 = allocateUiElement(0x7f, 0x7f, 16, getSlotY(selectedSlot_8011d740.get()));
        FUN_80104b60(highlightLeftHalf_800bdbe8);
        renderCharacterSwapScreen(0xffL);
        inventoryMenuState_800bdc28.set(InventoryMenuState._10);
        break;

      case _10:
        renderCharacterSwapScreen(0);

        if(_800bb168.get() != 0) {
          break;
        }

        if((inventoryJoypadInput_800bdc44.get() & 0x1000L) != 0 && selectedSlot_8011d740.get() > 0) { // Up
          selectedSlot_8011d740.decr();
          highlightLeftHalf_800bdbe8.y_44 = getSlotY(selectedSlot_8011d740.get());
          playSound(0x1L);
        }

        //LAB_800fd4e4
        //LAB_800fd4e8
        if((inventoryJoypadInput_800bdc44.get() & 0x4000L) != 0 && selectedSlot_8011d740.get() < 2) { // Down
          selectedSlot_8011d740.incr();
          highlightLeftHalf_800bdbe8.y_44 = getSlotY(selectedSlot_8011d740.get());
          playSound(0x1L);
        }

        //LAB_800fd52c
        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0) {
          final int charIndex = gameState_800babc8.charIndex_88.get(selectedSlot_8011d740.get()).get();
          if(!Config.unlockParty() && charIndex != -1 && (gameState_800babc8.charData_32c.get(charIndex).partyFlags_04.get() & 0x20L) != 0) {
            //LAB_800fd590
            playSound(0x28L);
          } else {
            //LAB_800fd5a0
            playSound(0x2L);
            highlightRightHalf_800bdbec = allocateUiElement(0x80, 0x80, FUN_800fc880(slotScroll_8011d744.get()), FUN_800fc8a8(slotScroll_8011d744.get()));
            FUN_80104b60(highlightRightHalf_800bdbec);
            inventoryMenuState_800bdc28.set(InventoryMenuState._11);
          }
        }

        //LAB_800fd5f4
        //LAB_800fd5f8
        if((inventoryJoypadInput_800bdc44.get() & 0x40L) != 0) {
          playSound(0x3L);

          //LAB_800fd62c
          FUN_800fca0c(whichMenu_800bdc38 != WhichMenu.RENDER_CHAR_SWAP_MENU_24 ? InventoryMenuState._2 : InventoryMenuState._125, 0x5L);
        }

        break;

      case _11:
        renderCharacterSwapScreen(0);

        if((inventoryJoypadInput_800bdc44.get() & 0x8000L) != 0 && slotScroll_8011d744.get() % 3 > 0) { // Left
          slotScroll_8011d744.decr();
          highlightRightHalf_800bdbec.x_40 = FUN_800fc880(slotScroll_8011d744.get());
          highlightRightHalf_800bdbec.y_44 = FUN_800fc8a8(slotScroll_8011d744.get());
          playSound(0x1L);
        }

        //LAB_800fd6b8
        if((inventoryJoypadInput_800bdc44.get() & 0x2000L) != 0 && slotScroll_8011d744.get() % 3 < 2) { // Right
          slotScroll_8011d744.incr();
          highlightRightHalf_800bdbec.x_40 = FUN_800fc880(slotScroll_8011d744.get());
          highlightRightHalf_800bdbec.y_44 = FUN_800fc8a8(slotScroll_8011d744.get());
          playSound(0x1L);
        }

        //LAB_800fd730
        if((inventoryJoypadInput_800bdc44.get() & 0x1000L) != 0 && slotScroll_8011d744.get() > 2) { // Up
          slotScroll_8011d744.sub(3);
          highlightRightHalf_800bdbec.x_40 = FUN_800fc880(slotScroll_8011d744.get());
          highlightRightHalf_800bdbec.y_44 = FUN_800fc8a8(slotScroll_8011d744.get());
          playSound(0x1L);
        }

        //LAB_800fd78c
        //LAB_800fd790
        if((inventoryJoypadInput_800bdc44.get() & 0x4000L) != 0 && slotScroll_8011d744.get() < 3) { // Down
          slotScroll_8011d744.add(3);
          highlightRightHalf_800bdbec.x_40 = FUN_800fc880(slotScroll_8011d744.get());
          highlightRightHalf_800bdbec.y_44 = FUN_800fc8a8(slotScroll_8011d744.get());
          playSound(0x1L);
        }

        //LAB_800fd7e4
        if((inventoryJoypadInput_800bdc44.get() & 0x40L) != 0) {
          playSound(0x3L);
          unloadRenderable(highlightRightHalf_800bdbec);
          inventoryMenuState_800bdc28.set(InventoryMenuState._10);
        }

        //LAB_800fd820
        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0) {
          int charCount = 0;
          for(int i = 0; i < 3; i++) {
            if(gameState_800babc8.charIndex_88.get(i).get() != -1) {
              charCount++;
            }
          }

          final int secondaryCharIndex = secondaryCharIndices_800bdbf8.get(slotScroll_8011d744.get()).get();
          if((!Config.unlockParty() || charCount < 2) && secondaryCharIndex == -1) {
            //LAB_800fd888
            playSound(0x28L);
            break;
          }

          if(secondaryCharIndex != -1 && (gameState_800babc8.charData_32c.get(secondaryCharIndex).partyFlags_04.get() & 0x2L) == 0) {
            //LAB_800fd888
            playSound(0x28L);
            break;
          }

          //LAB_800fd898
          playSound(0x2L);
          final int charIndex = gameState_800babc8.charIndex_88.get(selectedSlot_8011d740.get()).get();
          gameState_800babc8.charIndex_88.get(selectedSlot_8011d740.get()).set(secondaryCharIndex);
          secondaryCharIndices_800bdbf8.get(slotScroll_8011d744.get()).set(charIndex);
          inventoryMenuState_800bdc28.set(InventoryMenuState._9);
        }

        break;

      case EQUIPMENT_INIT_12:
        menuStack.pushScreen(new EquipmentScreen(() -> inventoryMenuState_800bdc28.set(InventoryMenuState._2)));
        inventoryMenuState_800bdc28.set(InventoryMenuState.EQUIPMENT_MENU_15);
        break;

      case EQUIPMENT_MENU_15:
        menuStack.render();
        break;

      case _16:
      case _31:
        scriptStartEffect(0x2L, 0xaL);
        deallocateRenderables(0xffL);
        renderGlyphs(glyphs_801141c4, 0, 0);
        _8011dcb8.get(0).setPointer(mallocTail(0x4c0L));
        _8011dcb8.get(1).setPointer(mallocTail(0x4c0L));
        recalcInventory();
        charSlot_8011d734.set(0);
        selectedSlot_8011d740.set(0);
        slotScroll_8011d744.set(0);
        slotScroll_8011d748.set(0);
        menuIndex_8011d74c.set(0);
        highlightLeftHalf_800bdbe8 = allocateUiElement(0x76, 0x76, FUN_800fc824(0), FUN_800fc814(selectedSlot_8011d740.get()) + 32);
        FUN_80104b60(highlightLeftHalf_800bdbe8);
        FUN_80102840(slotScroll_8011d744.get(), slotScroll_8011d748.get(), 0xff, 0xffL);

        if(inventoryMenuState_800bdc28.get() == InventoryMenuState._16) {
          inventoryMenuState_800bdc28.set(InventoryMenuState._17);
        } else {
          inventoryMenuState_800bdc28.set(InventoryMenuState._32);
        }

        break;

      case _17:
        _8011d754.setu(FUN_80104738(0));
        inventoryMenuState_800bdc28.set(InventoryMenuState._18);

        //LAB_800fe08c
        FUN_80102840(slotScroll_8011d744.get(), slotScroll_8011d748.get(), _8011dcb8.get(charSlot_8011d734.get()).deref().get(selectedSlot_8011d740.get() + slotScroll_8011d744.get()).itemId_00.get(), 0);
        break;

      case _32:
        _8011d754.setu(FUN_80104738(0x1L));
        inventoryMenuState_800bdc28.set(InventoryMenuState._33);

        //LAB_800fe08c
        FUN_80102840(slotScroll_8011d744.get(), slotScroll_8011d748.get(), _8011dcb8.get(charSlot_8011d734.get()).deref().get(selectedSlot_8011d740.get() + slotScroll_8011d748.get()).itemId_00.get(), 0);
        break;

      case _18:
      case _33: // Discard items menu
        //LAB_800fde10
        if(charSlot_8011d734.get() != 0) {
          count_8011d750.set(gameState_800babc8.itemCount_1e6.get());
        } else {
          //LAB_800fddf4
          count_8011d750.set(gameState_800babc8.equipmentCount_1e4.get() + (int)_8011d754.get());
        }

        if(charSlot_8011d734.get() != 0) {
          s1 = selectedSlot_8011d740.get() + slotScroll_8011d748.get();
        } else {
          //LAB_800fde38
          s1 = selectedSlot_8011d740.get() + slotScroll_8011d744.get();
        }

        //LAB_800fde50
        if((inventoryJoypadInput_800bdc44.get() & 0x40L) != 0) {
          playSound(0x3L);
          _800bdba0 = null;
          _800bdb9c = null;
          saveListDownArrow_800bdb98 = null;
          saveListUpArrow_800bdb94 = null;

          //LAB_800fdea8
          if(whichMenu_800bdc38 != WhichMenu.RENDER_SHOP_CARRIED_ITEMS_36) {
            FUN_800fca0c(InventoryMenuState._7, 0x8L);
          } else {
            FUN_800fca0c(InventoryMenuState._19, 0x8L);
          }
        }

        //LAB_800fdeb4
        if((inventoryJoypadInput_800bdc44.get() & 0x10L) != 0) { // Discard items menu - sort items
          playSound(0x2L);

          final ArrayRef<UnsignedByteRef> items;
          if(charSlot_8011d734.get() != 0) {
            items = gameState_800babc8.items_2e9;
          } else {
            //LAB_800fdef8
            items = gameState_800babc8.equipment_1e8;
          }

          //LAB_800fdf00
          sortItems(_8011dcb8.get(charSlot_8011d734.get()).deref(), items, count_8011d750.get());
        }

        //LAB_800fdf18
        //LAB_800fdf38
        //LAB_800fdf40
        if(scrollMenu(selectedSlot_8011d740, charSlot_8011d734.get() != 0 ? slotScroll_8011d748 : slotScroll_8011d744, 7, count_8011d750.get(), 1)) {
          highlightLeftHalf_800bdbe8.y_44 = FUN_800fc814(selectedSlot_8011d740.get()) + 32;
        }

        //LAB_800fdf7c
        if(handleMenuLeftRight(charSlot_8011d734, 2)) {
          highlightLeftHalf_800bdbe8.x_40 = FUN_800fc824(charSlot_8011d734.get());
        }

        //LAB_800fdfb4
        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0 && inventoryMenuState_800bdc28.get() == InventoryMenuState._33) {
          if((_8011dcb8.get(charSlot_8011d734.get()).deref().get((int)s1).price_02.get() & 0x2000) != 0) {
            //LAB_800fe064
            playSound(0x28L);
          } else if(_8011dcb8.get(charSlot_8011d734.get()).deref().get((int)s1).itemId_00.get() == 0xff) {
            //LAB_800fe064
            playSound(0x28L);
          } else {
            playSound(0x2L);
            menuIndex_8011d74c.set(0);
            renderablePtr_800bdc20 = allocateUiElement(0x7d, 0x7d, 314, FUN_800fc860(0));
            FUN_80104b60(renderablePtr_800bdc20);
            inventoryMenuState_800bdc28.set(InventoryMenuState.CONFIRM_ITEM_DISCARD_34);
          }
        }

        //LAB_800fe06c
        //LAB_800fe070
        //LAB_800fe08c
        FUN_80102840(slotScroll_8011d744.get(), slotScroll_8011d748.get(), _8011dcb8.get(charSlot_8011d734.get()).deref().get((int)s1).itemId_00.get(), 0);
        break;

      case CONFIRM_ITEM_DISCARD_34:
        if(charSlot_8011d734.get() != 0) {
          s1 = selectedSlot_8011d740.get() + slotScroll_8011d748.get();
        } else {
          //LAB_800fe0dc
          s1 =  selectedSlot_8011d740.get() + slotScroll_8011d744.get();
        }

        //LAB_800fe0f0
        renderText(Really_want_to_throw_this_away_8011c8d4, 192, 180, 4);
        renderCentredText(Yes_8011c20c, 328, FUN_800fc860(0) + 2, menuIndex_8011d74c.get() == 0 ? 5 : 6);
        renderCentredText(No_8011c214, 328, FUN_800fc860(1) + 2, menuIndex_8011d74c.get() == 0 ? 6 : 5);

        switch(handleYesNo(menuIndex_8011d74c)) {
          case SCROLLED ->
            //LAB_800fe1bc
            renderablePtr_800bdc20.y_44 = FUN_800fc860(menuIndex_8011d74c.get());

          case YES -> {
            //LAB_800fe1d8
            //LAB_800fe1fc
            for(int i = (int)s1; i < count_8011d750.get(); i++) {
              final MenuItemStruct04 a = _8011dcb8.get(charSlot_8011d734.get()).deref().get(i);
              final MenuItemStruct04 b = _8011dcb8.get(charSlot_8011d734.get()).deref().get(i + 1);
              a.itemId_00.set(b.itemId_00);
              a.itemSlot_01.set(b.itemSlot_01);
              a.price_02.set(b.price_02);
            }

            //LAB_800fe238
            count_8011d750.decr();

            final ArrayRef<UnsignedByteRef> items;
            if(charSlot_8011d734.get() != 0) {
              items = gameState_800babc8.items_2e9;
            } else {
              //LAB_800fe274
              items = gameState_800babc8.equipment_1e8;
            }

            //LAB_800fe27c
            FUN_800239e0(_8011dcb8.get(charSlot_8011d734.get()).deref(), items, count_8011d750.get());
            recalcInventory();

            //LAB_800fe29c
            unloadRenderable(renderablePtr_800bdc20);
            inventoryMenuState_800bdc28.set(InventoryMenuState._33);
          }

          case NO, CANCELLED -> {
            //LAB_800fe29c
            unloadRenderable(renderablePtr_800bdc20);
            inventoryMenuState_800bdc28.set(InventoryMenuState._33);
          }
        }

        //LAB_800fe2b4
        //LAB_800fe2bc
        FUN_80102840(slotScroll_8011d744.get(), slotScroll_8011d748.get(), 0xff, 0x1L);
        break;

      case _19:
        inventoryMenuState_800bdc28.set(InventoryMenuState.AWAIT_INIT_1);
        whichMenu_800bdc38 = WhichMenu.RENDER_SHOP_MENU_9;
        break;

      case STATUS_INIT_20:
        menuStack.pushScreen(new StatusScreen(() -> inventoryMenuState_800bdc28.set(InventoryMenuState._2)));
        inventoryMenuState_800bdc28.set(InventoryMenuState.STATUS_MENU_22);
        break;

      case STATUS_MENU_22:
        menuStack.render();
        break;

      case ADDITIONS_INIT_23:
        selectedSlot_8011d740.set(0);
        renderablePtr_800bdba8 = null;
        renderablePtr_800bdba4 = null;
        highlightRightHalf_800bdbec = null;
        highlightLeftHalf_800bdbe8 = null;
        scriptStartEffect(2, 10);
        deallocateRenderables(0xff);
        inventoryMenuState_800bdc28.set(InventoryMenuState.ADDITIONS_LOAD_24);
        break;

      case ADDITIONS_LOAD_24:
        deallocateRenderables(0);
        loadAdditions(characterIndices_800bdbb8.get(charSlot_8011d734.get()).get(), additions_8011e098);

        if(additions_8011e098.get(0).offset_00.get() != -1) {
          highlightLeftHalf_800bdbe8 = allocateUiElement(117, 117, 39, getAdditionSlotY(selectedSlot_8011d740.get()) - 4);
          FUN_80104b60(highlightLeftHalf_800bdbe8);
        }

        //LAB_800fe490
        allocateUiElement(69, 69,   0, 0);
        allocateUiElement(70, 70, 192, 0);
        renderAdditions(charSlot_8011d734.get(), selectedSlot_8011d740.get(), additions_8011e098, gameState_800babc8.charData_32c.get(characterIndices_800bdbb8.get(charSlot_8011d734.get()).get()).selectedAddition_19.get(), 0xffL);
        inventoryMenuState_800bdc28.set(InventoryMenuState.ADDITIONS_MENU_25);
        break;

      case ADDITIONS_MENU_25:
        FUN_801034cc(charSlot_8011d734.get(), characterCount_8011d7c4.get());
        renderAdditions(charSlot_8011d734.get(), selectedSlot_8011d740.get(), additions_8011e098, gameState_800babc8.charData_32c.get(characterIndices_800bdbb8.get(charSlot_8011d734.get()).get()).selectedAddition_19.get(), 0);

        if(_800bb168.get() != 0) {
          break;
        }

        if(handleMenuLeftRight(charSlot_8011d734, characterCount_8011d7c4.get())) {
          inventoryMenuState_800bdc28.set(InventoryMenuState.ADDITIONS_LOAD_24);
          unloadRenderable(highlightLeftHalf_800bdbe8);
        }

        //LAB_800fe5b8
        if((inventoryJoypadInput_800bdc44.get() & 0x40L) != 0) {
          playSound(0x3L);
          FUN_800fca0c(InventoryMenuState._2, 0x9L);
        }

        //LAB_800fe5e4
        if(additions_8011e098.get(0).offset_00.get() == -1) {
          break;
        }

        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0) {
          final int additionOffset = additions_8011e098.get(selectedSlot_8011d740.get()).offset_00.get();

          if(additionOffset != -1) {
            gameState_800babc8.charData_32c.get(characterIndices_800bdbb8.get(charSlot_8011d734.get()).get()).selectedAddition_19.set(additionOffset);
            playSound(0x2L);
            unloadRenderable(highlightLeftHalf_800bdbe8);
            inventoryMenuState_800bdc28.set(InventoryMenuState.ADDITIONS_LOAD_24);
          } else {
            //LAB_800fe680
            playSound(0x28L);
          }
        }

        //LAB_800fe68c
        if(handleMenuUpDown(selectedSlot_8011d740, 7)) {
          highlightLeftHalf_800bdbe8.y_44 = getAdditionSlotY(selectedSlot_8011d740.get()) - 4;
        }

        break;

      case USE_ITEM_MENU_INIT_26:
        menuStack.pushScreen(new UseItemScreen(() -> inventoryMenuState_800bdc28.set(InventoryMenuState._7)));
        inventoryMenuState_800bdc28.set(InventoryMenuState.USE_ITEM_MENU_29);
        break;

      case USE_ITEM_MENU_29: // Item list
        menuStack.render();
        break;

      case _35: // Goods menu
        scriptStartEffect(0x2L, 0xaL);
        deallocateRenderables(0xffL);
        renderGlyphs(glyphs_801141c4, 0, 0);
        count_8011d750.set(0);

        //LAB_800fec7c
        for(int i = 0; i < 70; i++) {
          menuItems_8011d7c8.get(i).itemId_00.set(0xff);

          if(i < 0x40) {
            if((gameState_800babc8.dragoonSpirits_19c.get(i >>> 5).get() & 0x1L << (i & 0x1fL)) != 0) {
              menuItems_8011d7c8.get(count_8011d750.get()).itemId_00.set(i);
              menuItems_8011d7c8.get(count_8011d750.get()).itemSlot_01.set(i);
              menuItems_8011d7c8.get(count_8011d750.get()).price_02.set(0);
              count_8011d750.incr();
            }
          }

          //LAB_800fecf0
        }

        slotScroll_8011d744.set(0);
        selectedSlot_8011d740.set(0);
        charSlot_8011d734.set(0);
        highlightLeftHalf_800bdbe8 = allocateUiElement(0x76, 0x76, FUN_800fc824(0), FUN_800fc814(selectedSlot_8011d740.get()) + 32);
        FUN_80104b60(highlightLeftHalf_800bdbe8);
        FUN_80102f74(charSlot_8011d734.get(), selectedSlot_8011d740.get(), slotScroll_8011d744.get(), 0xffL);
        inventoryMenuState_800bdc28.set(InventoryMenuState._36);
        break;

      case _36:
        if((inventoryJoypadInput_800bdc44.get() & 0x40) != 0) {
          playSound(2);
          _800bdba0 = null;
          _800bdb9c = null;
          saveListDownArrow_800bdb98 = null;
          saveListUpArrow_800bdb94 = null;
          menuItems_8011d7c8.get(count_8011d750.get()).itemSlot_01.set(0x30); // This is a bug - it's set to s0 but s0 is undefined. The value of s0 at this point is an address with the lower byte of 0x30. Unknown if this value needs to be set or not, and if it's just chance that 0x30 is a valid value.
          count_8011d750.incr();
          FUN_800fca0c(InventoryMenuState._7, 0xbL);
        }

        //LAB_800fede4
        if(scrollMenu(selectedSlot_8011d740, slotScroll_8011d744, 7, roundUp(count_8011d750.get(), 2), 2)) {
          highlightLeftHalf_800bdbe8.y_44 = FUN_800fc814(selectedSlot_8011d740.get()) + 32;
        }

        //LAB_800fee38
        if((inventoryJoypadInput_800bdc44.get() & 0x8000) != 0 && charSlot_8011d734.get() != 0) {
          playSound(1);
          charSlot_8011d734.set(0);
          highlightLeftHalf_800bdbe8.x_40 = FUN_800fc824(0);
        }

        //LAB_800fee80
        //LAB_800fee84
        if((inventoryJoypadInput_800bdc44.get() & 0x2000) != 0 && charSlot_8011d734.get() == 0) {
          playSound(1);
          charSlot_8011d734.set(1);
          highlightLeftHalf_800bdbe8.x_40 = FUN_800fc824(1);
        }

        //LAB_800feed0
        //LAB_800feed4
        FUN_80102f74(charSlot_8011d734.get(), selectedSlot_8011d740.get(), slotScroll_8011d744.get(), 0);
        break;

      case INIT_LOAD_GAME_37:
        menuStack.pushScreen(new SaveGameScreen(() -> {
          menuStack.popScreen();
          fadeOutArrows();
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }));

        inventoryMenuState_800bdc28.set(InventoryMenuState.LOAD_GAME_MENU_43);
        break;

      case LOAD_GAME_MENU_43:
        menuStack.render();
        break;

      case DABAS_INIT_72:
        menuStack.pushScreen(new DabasScreen(() -> {
          menuStack.popScreen();
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }));

        inventoryMenuState_800bdc28.set(InventoryMenuState.DABAS_MENU_79);
        break;

      case DABAS_MENU_79:
        menuStack.render();
        break;

      case _123: // Start fade out
        scriptStartEffect(0x1L, 0xaL);
        inventoryMenuState_800bdc28.set(InventoryMenuState._124);

      case _124:
        switch((int)_800bdc2c.get()) {
          case 0x1 -> {
            FUN_80102484(0);

            //LAB_801018f0
            renderInventoryMenu(selectedMenuOption_8011d738.get(), 4, 0xfeL);
          }

          case 0x2 -> {
            FUN_80102484(0x1L);
            renderItemSubmenu(selectedItemSubmenuOption_8011d73c.get(), 4);

            //LAB_801018f0
            renderInventoryMenu(selectedMenuOption_8011d738.get(), 6, 0xfeL);
          }

          case 0x5 -> renderCharacterSwapScreen(0xfeL);

          case 0x8 -> {
            if(charSlot_8011d734.get() != 0) {
              v0 = selectedSlot_8011d740.get() + slotScroll_8011d748.get();
            } else {
              //LAB_80101994
              v0 = selectedSlot_8011d740.get() + slotScroll_8011d744.get();
            }

            //LAB_801019a8
            FUN_80102840(slotScroll_8011d744.get(), slotScroll_8011d748.get(), _8011dcb8.get(charSlot_8011d734.get()).deref().get((int)v0).itemId_00.get(), 0);

            if((int)_800bb168.get() < 0xffL) {
              return;
            }

            free(_8011dcb8.get(0).getPointer());
            free(_8011dcb8.get(1).getPointer());
          }

          case 0x9 -> renderAdditions(charSlot_8011d734.get(), selectedSlot_8011d740.get(), additions_8011e098, gameState_800babc8.charData_32c.get(characterIndices_800bdbb8.get(charSlot_8011d734.get()).get()).selectedAddition_19.get(), 0xfeL);
          case 0xb -> FUN_80102f74(charSlot_8011d734.get(), selectedSlot_8011d740.get(), slotScroll_8011d744.get(), 0xfeL);
        }

        //LAB_80101afc
        //LAB_80101b00
        if((int)_800bb168.get() >= 0xffL) {
          //LAB_80101b14
          //LAB_80101b18
          inventoryMenuState_800bdc28.set(confirmDest_800bdc30.get());
        }

        break;

      case _125:
        deallocateRenderables(0xffL);
        free(drgn0_6666FilePtr_800bdc3c.getPointer());

        switch(whichMenu_800bdc38) {
          case RENDER_LOAD_GAME_MENU_14 -> {
            //LAB_80101b90
            scriptStartEffect(0x2L, 0xaL);
            whichMenu_800bdc38 = WhichMenu.UNLOAD_LOAD_GAME_MENU_15;
          }

          case RENDER_SAVE_GAME_MENU_19 ->
            //LAB_80101ba4
            whichMenu_800bdc38 = WhichMenu.UNLOAD_SAVE_GAME_MENU_20;

          case RENDER_CHAR_SWAP_MENU_24 -> {
            scriptStartEffect(0x2L, 0xaL);
            whichMenu_800bdc38 = WhichMenu.UNLOAD_CHAR_SWAP_MENU_25;
          }

          default -> {
            //LAB_80101b70
            //LAB_80101bb0
            scriptStartEffect(0x2L, 0xaL);
            whichMenu_800bdc38 = WhichMenu.UNLOAD_INVENTORY_MENU_5;
          }
        }

        //LAB_80101bc4
        if(mainCallbackIndex_8004dd20.get() == 0x5L && loadingGameStateOverlay_8004dd08.get() == 0) {
          FUN_800e3fac();
        }

        //LAB_80101bf8
        //LAB_80101bfc
        textZ_800bdf00.set(13);
        break;
    }
  }

  @Method(0x80101d10L)
  public static void renderInventoryMenu(final long selectedOption, final int a1, final long a2) {
    final int s5 = canSave_8011dc88.get() != 0 ? a1 : 6;

    //LAB_80101d54
    final boolean allocate = a2 == 0xff;
    if(allocate) {
      renderDragoonSpirits((int)gameState_800babc8.dragoonSpirits_19c.get(0).get(), 40, 197);
      renderEightDigitNumber(67, 184, gameState_800babc8.gold_94.get(), 0); // Gold
      renderCharacter(146, 184, 10);
      renderCharacter(164, 184, 10);
      renderTwoDigitNumber(166, 204, gameState_800babc8.stardust_9c.get()); // Stardust
    }

    //LAB_80101db8
    renderThreeDigitNumber(128, 184, getTimestampPart(gameState_800babc8.timestamp_a0.get(), 0), 0x3L);
    renderTwoDigitNumber(152, 184, getTimestampPart(gameState_800babc8.timestamp_a0.get(), 1), 0x3L);
    renderTwoDigitNumber(170, 184, getTimestampPart(gameState_800babc8.timestamp_a0.get(), 2), 0x3L);
    renderCharacterSlot(194,  16, gameState_800babc8.charIndex_88.get(0).get(), allocate, false);
    renderCharacterSlot(194,  88, gameState_800babc8.charIndex_88.get(1).get(), allocate, false);
    renderCharacterSlot(194, 160, gameState_800babc8.charIndex_88.get(2).get(), allocate, false);
    renderCentredText(chapterNames_80114248.get(gameState_800babc8.chapterIndex_98.get()).deref(), 94, 24, 4);

    final LodString v1;
    if(mainCallbackIndex_8004dd20.get() == 0x5L) {
      v1 = submapNames_8011c108.get(submapIndex_800bd808.get()).deref();
    } else {
      //LAB_80101ec0
      v1 = worldMapNames_8011c1ec.get(continentIndex_800bf0b0.get()).deref();
    }

    //LAB_80101ed4
    renderCentredText(v1, 90, 38, 4);

    //LAB_80101f0c
    renderCentredText(Status_8011ceb4,   62, getMenuOptionY(0) + 2, selectedOption == 0 ? 5 : a1);
    //LAB_80101f3c
    renderCentredText(Item_8011cec4,     62, getMenuOptionY(1) + 2, selectedOption == 1 ? 5 : a1);
    //LAB_80101f6c
    renderCentredText(Armed_8011ced0,    62, getMenuOptionY(2) + 2, selectedOption == 2 ? 5 : a1);
    //LAB_80101f9c
    renderCentredText(Addition_8011cedc, 62, getMenuOptionY(3) + 2, selectedOption == 3 ? 5 : a1);
    //LAB_80101fcc
    renderCentredText(Replace_8011cef0,  62, getMenuOptionY(4) + 2, selectedOption == 4 ? 5 : a1);
    //LAB_80101ff8
    renderCentredText(Config_8011cf00,   62, getMenuOptionY(5) + 2, selectedOption == 5 ? 5 : a1);
    //LAB_80102028
    renderCentredText(Save_8011cf10,     62, getMenuOptionY(6) + 2, selectedOption == 6 ? 5 : s5);
    renderCentredText(new LodString("Quit"),     62, getMenuOptionY(7) + 2, selectedOption == 7 ? 5 : a1);

    if(!allocate) {
      uploadRenderables();
    }

    //LAB_80102040
  }

  @Method(0x80102064L)
  public static void renderItemSubmenu(final int selectedIndex, final int a1) {
    FUN_801038d4(150, 20, 60);
    renderCentredText(Use_it_8011cf1c, 142, getItemSubmenuOptionY(0), selectedIndex == 0 ? 5 : a1);
    renderCentredText(Discard_8011cf2c, 142, getItemSubmenuOptionY(1), selectedIndex == 1 ? 5 : a1);
    renderCentredText(List_8011cf3c, 142, getItemSubmenuOptionY(2), selectedIndex == 2 ? 5 : a1);
    renderCentredText(Goods_8011cf48, 142, getItemSubmenuOptionY(3), selectedIndex == 3 ? 5 : a1);
    renderCentredText(new LodString("Diiig"), 142, getItemSubmenuOptionY(4), selectedIndex == 4 ? 5 : a1);
  }

  @Method(0x8010214cL)
  public static void renderOptionsMenu(final long optionIndex, final long vibrateMode, final long soundMode, final long morphMode, final long noteMode) {
    textZ_800bdf00.set(32);

    renderCentredText(Vibrate_8011cf58, FUN_800fc7bc(0) - 15, menuOptionY(0), optionIndex == 0 ? 5 : 4);
    renderCentredText(Off_8011cf6c, FUN_800fc7bc(1), menuOptionY(0), vibrateMode == 0 ? 5 : 4);
    renderCentredText(On_8011cf74, FUN_800fc7bc(2), menuOptionY(0), vibrateMode != 0 ? 5 : 4);
    renderCentredText(Sound_8011cf7c, FUN_800fc7bc(0) - 15, menuOptionY(1), optionIndex == 1 ? 5 : 4);
    renderCentredText(Stereo_8011cf88, FUN_800fc7bc(1), menuOptionY(1), soundMode == 0 ? 5 : 4);
    renderCentredText(Mono_8011cf98, FUN_800fc7bc(2), menuOptionY(1), soundMode != 0 ? 5 : 4);
    renderCentredText(Morph_8011cfa4, FUN_800fc7bc(0) - 15, menuOptionY(2), optionIndex == 2 ? 5 : 4);
    renderCentredText(Normal_8011cfb0, FUN_800fc7bc(1), menuOptionY(2), morphMode == 0 ? 5 : 4);
    renderCentredText(Short_8011cfc0, FUN_800fc7bc(2), menuOptionY(2), morphMode != 0 ? 5 : 4);
    renderCentredText(Note_8011c814, FUN_800fc7bc(0) - 15, menuOptionY(3), optionIndex != 3 ? 4 : 5);
    renderCentredText(Off_8011c838, FUN_800fc7d0(1), menuOptionY(3), noteMode == 0 ? 5 : 4);
    renderCentredText(Half_8011c82c, FUN_800fc7d0(2), menuOptionY(3), noteMode == 1 ? 5 : 4);
    renderCentredText(Stay_8011c820, FUN_800fc7d0(3), menuOptionY(3), noteMode == 2 ? 5 : 4);

    textZ_800bdf00.set(33);
  }

  @Method(0x80102484L)
  public static void FUN_80102484(final long a0) {
    //LAB_801024ac
    FUN_801038d4(a0 != 0 ? 23 : 24, 112, getMenuOptionY(1) + 3);
  }

  @Method(0x801024c4L)
  public static void renderCharacterSwapScreen(final long a0) {
    final boolean allocate = a0 == 0xff;

    FUN_801082a0(198,  16, secondaryCharIndices_800bdbf8.get(0).get(), allocate);
    FUN_801082a0(255,  16, secondaryCharIndices_800bdbf8.get(1).get(), allocate);
    FUN_801082a0(312,  16, secondaryCharIndices_800bdbf8.get(2).get(), allocate);
    FUN_801082a0(198, 122, secondaryCharIndices_800bdbf8.get(3).get(), allocate);
    FUN_801082a0(255, 122, secondaryCharIndices_800bdbf8.get(4).get(), allocate);
    FUN_801082a0(312, 122, secondaryCharIndices_800bdbf8.get(5).get(), allocate);

    if(gameState_800babc8.charIndex_88.get(0).get() != -1) {
      renderCharacterSlot(16, 16, gameState_800babc8.charIndex_88.get(0).get(), allocate, !Config.unlockParty() && (gameState_800babc8.charData_32c.get(gameState_800babc8.charIndex_88.get(0).get()).partyFlags_04.get() & 0x20) != 0);
    }

    //LAB_801025b4
    if(gameState_800babc8.charIndex_88.get(1).get() != -1) {
      renderCharacterSlot(16, 88, gameState_800babc8.charIndex_88.get(1).get(), allocate, !Config.unlockParty() && (gameState_800babc8.charData_32c.get(gameState_800babc8.charIndex_88.get(1).get()).partyFlags_04.get() & 0x20) != 0);
    }

    //LAB_801025f8
    if(gameState_800babc8.charIndex_88.get(2).get() != -1) {
      renderCharacterSlot(16, 160, gameState_800babc8.charIndex_88.get(2).get(), allocate, !Config.unlockParty() && (gameState_800babc8.charData_32c.get(gameState_800babc8.charIndex_88.get(2).get()).partyFlags_04.get() & 0x20) != 0);
    }

    //LAB_8010263c
    uploadRenderables();
  }

  @Method(0x80102840L)
  public static void FUN_80102840(final int slotScroll1, final int slotScroll2, final int itemId, final long a3) {
    renderMenuItems( 16, 33, _8011dcb8.get(0).deref(), slotScroll1, 7, saveListUpArrow_800bdb94, saveListDownArrow_800bdb98);
    renderMenuItems(194, 33, _8011dcb8.get(1).deref(), slotScroll2, 7, _800bdb9c, _800bdba0);
    renderThreeDigitNumber(136, 24, gameState_800babc8.equipmentCount_1e4.get(), 0x2L);
    renderTwoDigitNumber(326, 24, gameState_800babc8.itemCount_1e6.get(), 0x2L);

    final boolean allocate = a3 == 0xff;
    if(allocate) {
      allocateUiElement(0xb, 0xb, 154, 24);
      renderThreeDigitNumber(160, 24, 0xff);
      allocateUiElement(0xb, 0xb, 338, 24);
      renderTwoDigitNumber(344, 24, Config.inventorySize());
      allocateUiElement(0x55, 0x55, 16, 16);
      saveListUpArrow_800bdb94 = allocateUiElement(0x3d, 0x44, 180, FUN_800fc814(2));
      saveListDownArrow_800bdb98 = allocateUiElement(0x35, 0x3c, 180, FUN_800fc814(8));
      allocateUiElement(0x55, 0x55, 194, 16);
      _800bdb9c = allocateUiElement(0x3d, 0x44, 358, FUN_800fc814(2));
      _800bdba0 = allocateUiElement(0x35, 0x3c, 358, FUN_800fc814(8));
    }

    //LAB_80102a1c
    renderText(_8011c314,  32, 22, 4);
    renderText(_8011c32c, 210, 22, 4);

    if(a3 != 0x1L) {
      FUN_801038d4(0x89, 84, 178).clut_30 = 0x7ceb;
      renderText(Press_to_sort_8011d024, 37, 178, 4);
    }

    //LAB_80102a88
    renderString(0, 194, 178, itemId, allocate);
    uploadRenderables();
  }

  @Method(0x80102ad8L)
  public static void renderAdditions(final int charSlot, final int slotIndex, final ArrayRef<MenuAdditionInfo> additions, final int selectedAdditionOffset, final long a4) {
    final boolean allocate = a4 == 0xff;
    final int charIndex = characterIndices_800bdbb8.get(charSlot).get();

    if(additions.get(0).offset_00.get() == -1) {
      renderText(_8011c340, 106, 150, 4);
    } else {
      //LAB_80102b9c
      if(allocate) {
        renderGlyphs(glyphs_801141e4, 0, 0);
      }

      //LAB_80102bbc
      //LAB_80102bf0
      for(int i = 0; i < 8; i++) {
        final int y = getAdditionSlotY(i);

        if(allocate && i <  additionCounts_8004f5c0.get(charIndex).get()) { // Total number of additions
          renderCharacter(24, y, i + 1); // Addition number
        }

        //LAB_80102c30
        final int offset = additions.get(i).offset_00.get();
        final int index = additions.get(i).index_01.get();

        if(offset != -1) {
          //LAB_80102c58
          renderText(additions_8011a064.get(offset).deref(), 33, y - 2, offset != selectedAdditionOffset ? 4 : 5);

          if(allocate) {
            final int level = gameState_800babc8.charData_32c.get(charIndex).additionLevels_1a.get(index).get();
            renderThreeDigitNumber(197, y, level); // Addition level
            renderThreeDigitNumber(230, y, additionData_80052884.get(offset).attacks_01.get()); // Number of attacks
            renderThreeDigitNumber(263, y, additionData_80052884.get(offset).sp_02.get(level - 1).get()); // SP
            renderThreeDigitNumber(297, y, (int)(additionData_80052884.get(offset).damage_0c.get() * (ptrTable_80114070.offset(offset * 0x4L).deref(1).offset(level * 0x4L).offset(0x3L).get() + 100) / 100)); // Damage
            renderThreeDigitNumber(322, y, gameState_800babc8.charData_32c.get(charIndex).additionXp_22.get(index).get()); // Current XP

            if(level < 5) {
              renderThreeDigitNumber(342, y, additionXpPerLevel_800fba2c.get(level).get()); // Max XP
            } else {
              //LAB_80102d8c
              renderCharacter(354, y, 218); // Dash if at max XP
            }
          }
        }

        //LAB_80102d9c
        //LAB_80102da0
      }
    }

    //LAB_80102db0
    renderCharacterSlot(16, 21, charIndex, allocate, false);
    uploadRenderables();
  }

  @Method(0x80102f74L)
  public static void FUN_80102f74(final int charSlot, final int selectedSlot, final int slotScroll, final long a3) {
    final boolean allocate = a3 == 0xff;

    if(allocate) {
      allocateUiElement(0x55, 0x55,  16, 16);
      allocateUiElement(0x55, 0x55, 194, 16);
      _800bdb9c = allocateUiElement(0x3d, 0x44, 358, FUN_800fc814(2));
      _800bdba0 = allocateUiElement(0x35, 0x3c, 358, FUN_800fc814(8));
    }

    //LAB_8010301c
    renderText(Goods_8011cf48,  32, 22, 4);
    renderText(Goods_8011cf48, 210, 22, 4);
    FUN_8010965c(slotScroll, _800bdb9c, _800bdba0);
    renderString(1, 194, 178, menuItems_8011d7c8.get(charSlot + selectedSlot * 2 + slotScroll).itemId_00.get(), allocate);
    uploadRenderables();
  }

  @Method(0x801033ccL)
  public static void FUN_801033cc(final Renderable58 a0) {
    a0._28 = 0x1;
    a0._38 = 0;
    a0._34 = 0;
    a0.z_3c = 31;
  }

  @Method(0x801033e8L)
  public static void fadeOutArrow(final Renderable58 renderable) {
    unloadRenderable(renderable);

    final Renderable58 newRenderable = allocateUiElement(108, 111, renderable.x_40, renderable.y_44);
    newRenderable.flags_00 |= 0x10;
    FUN_801033cc(newRenderable);
  }

  @Method(0x80103444L)
  public static void FUN_80103444(@Nullable final Renderable58 a0, final int a1, final int a2, final int a3, final int a4) {
    if(a0 != null) {
      if(a0._18 == 0) {
        if((simpleRand() & 0x3000L) != 0) {
          a0._18 = a1;
          a0._1c = a2;
        } else {
          //LAB_801034a0
          a0._18 = a3;
          a0._1c = a4;
        }
      }
    }

    //LAB_801034b0
  }

  @Method(0x801034ccL)
  public static void FUN_801034cc(final int charSlot, final int charCount) {
    FUN_80103444(renderablePtr_800bdba4, 0x2d, 0x34, 0xaa, 0xb1);
    FUN_80103444(renderablePtr_800bdba8, 0x25, 0x2c, 0xa2, 0xa9);

    if(charSlot != 0) {
      if(renderablePtr_800bdba4 == null) {
        final Renderable58 renderable = allocateUiElement(0x6f, 0x6c, 18, 16);
        renderable._18 = 0x2d;
        renderable._1c = 0x34;
        renderablePtr_800bdba4 = renderable;
        FUN_801033cc(renderable);
      }
    } else {
      //LAB_80103578
      if(renderablePtr_800bdba4 != null) {
        fadeOutArrow(renderablePtr_800bdba4);
        renderablePtr_800bdba4 = null;
      }
    }

    //LAB_80103598
    if(charSlot < charCount - 1) {
      if(renderablePtr_800bdba8 == null) {
        final Renderable58 renderable = allocateUiElement(0x6f, 0x6c, 350, 16);
        renderable._18 = 0x25;
        renderable._1c = 0x2c;
        renderablePtr_800bdba8 = renderable;
        FUN_801033cc(renderable);
      }
      //LAB_801035e8
    } else if(renderablePtr_800bdba8 != null) {
      fadeOutArrow(renderablePtr_800bdba8);
      renderablePtr_800bdba8 = null;
    }

    //LAB_80103604
  }

  @Method(0x8010361cL)
  public static void renderSaveListArrows(final int scroll) {
    FUN_80103444(saveListUpArrow_800bdb94, 194, 201, 202, 209);
    FUN_80103444(saveListDownArrow_800bdb98, 178, 185, 186, 193);

    if(scroll != 0) {
      if(saveListUpArrow_800bdb94 == null) {
        // Allocate up arrow
        final Renderable58 renderable = allocateUiElement(111, 108, 182, 16);
        renderable._18 = 194;
        renderable._1c = 201;
        saveListUpArrow_800bdb94 = renderable;
        FUN_801033cc(renderable);
      }
      //LAB_801036c8
    } else if(saveListUpArrow_800bdb94 != null) {
      // Deallocate up arrow
      fadeOutArrow(saveListUpArrow_800bdb94);
      saveListUpArrow_800bdb94 = null;
    }

    //LAB_801036e8
    if(scroll < (whichMenu_800bdc38 == WhichMenu.RENDER_SAVE_GAME_MENU_19 ? saves.size() - 3 : saves.size() - 3) && (whichMenu_800bdc38 == WhichMenu.RENDER_SAVE_GAME_MENU_19 && saves.size() > 2 || saves.size() > 3)) {
      if(saveListDownArrow_800bdb98 == null) {
        // Allocate down arrow
        final Renderable58 renderable = allocateUiElement(111, 108, 182, 208);
        renderable._18 = 178;
        renderable._1c = 185;
        saveListDownArrow_800bdb98 = renderable;
        FUN_801033cc(renderable);
      }
      //LAB_80103738
    } else if(saveListDownArrow_800bdb98 != null) {
      // Deallocate down arrow
      fadeOutArrow(saveListDownArrow_800bdb98);
      saveListDownArrow_800bdb98 = null;
    }

    //LAB_80103754
  }

  @Method(0x8010376cL)
  public static void renderGlyphs(final UnboundedArrayRef<MenuGlyph06> glyphs, final int x, final int y) {
    //LAB_801037ac
    for(int i = 0; glyphs.get(i).glyph_00.get() != 0xff; i++) {
      final Renderable58 s0 = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);

      initGlyph(s0, glyphs.get(i));

      s0.x_40 += x;
      s0.y_44 += y;
    }

    //LAB_801037f4
  }

  @Method(0x80103818L)
  public static Renderable58 allocateUiElement(final int startGlyph, final int endGlyph, final int x, final int y) {
    final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);

    if(endGlyph >= startGlyph) {
      renderable.glyph_04 = startGlyph;
      renderable.startGlyph_10 = startGlyph;
      renderable.endGlyph_14 = endGlyph;
    } else {
      //LAB_80103870
      renderable.glyph_04 = startGlyph;
      renderable.startGlyph_10 = endGlyph;
      renderable.endGlyph_14 = startGlyph;
      renderable.flags_00 |= 0x20;
    }

    //LAB_80103888
    if(startGlyph == endGlyph) {
      renderable.flags_00 |= 0x4;
    }

    //LAB_801038a4
    renderable.tpage_2c = 0x19;
    renderable.clut_30 = 0;
    renderable.x_40 = x;
    renderable.y_44 = y;

    return renderable;
  }

  @Method(0x80103910L)
  public static Renderable58 renderItemIcon(final int glyph, final int x, final int y, final long flags) {
    final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._c6a4, null);
    renderable.flags_00 |= flags | 0x4;
    renderable.glyph_04 = glyph;
    renderable.startGlyph_10 = glyph;
    renderable.endGlyph_14 = glyph;
    renderable.tpage_2c = 0x19;
    renderable.clut_30 = 0;
    renderable.x_40 = x;
    renderable.y_44 = y;
    return renderable;
  }

  @Method(0x801039a0L)
  public static boolean canEquip(final int equipmentId, final int charIndex) {
    return charIndex != -1 && equipmentId < 0xc0 && (characterValidEquipment_80114284.offset(charIndex).get() & equipmentStats_80111ff0.get(equipmentId).equips_03.get()) != 0;
  }

  @Method(0x801039f8L)
  public static int getEquipmentSlot(final int itemId) {
    if(itemId < 0xc0) {
      final int type = equipmentStats_80111ff0.get(itemId).type_01.get();

      //LAB_80103a2c
      for(int i = 0; i < 5; i++) {
        if((type & 0x80 >> i) != 0) {
          return i;
        }

        //LAB_80103a44
      }
    }

    //LAB_80103a54
    return -1;
  }

  /**
   * @return Item ID of previously-equipped item, 0xff if invalid, 0x100 if no item was equipped
   */
  @Method(0x80103a5cL)
  public static int equipItem(final int equipmentId, final int charIndex) {
    if(charIndex == -1) {
      return 0xff;
    }

    if((!canEquip(equipmentId, charIndex))) {
      return 0xff;
    }

    final int slot = getEquipmentSlot(equipmentId);
    if(slot == -1) {
      //LAB_80103ab8
      return 0xff;
    }

    //LAB_80103ac0
    final CharacterData2c charData = gameState_800babc8.charData_32c.get(charIndex);
    int previousId = charData.equipment_14.get(slot).get();
    charData.equipment_14.get(slot).set(equipmentId);

    if(previousId == 0xff) {
      previousId = 0x100;
    }

    //LAB_80103af4
    //LAB_80103af8
    return previousId;
  }

  @Method(0x80103b10L)
  public static void FUN_80103b10() {
    characterCount_8011d7c4.set(0);

    //LAB_80103b48
    int a2 = 0;
    for(int slot = 0; slot < 9; slot++) {
      secondaryCharIndices_800bdbf8.get(slot).set(-1);
      characterIndices_800bdbb8.get(slot).set(-1);

      if((gameState_800babc8.charData_32c.get(slot).partyFlags_04.get() & 0x1L) != 0) {
        characterIndices_800bdbb8.get(characterCount_8011d7c4.get()).set(slot);
        characterCount_8011d7c4.incr();

        if(gameState_800babc8.charIndex_88.get(0).get() != slot && gameState_800babc8.charIndex_88.get(1).get() != slot && gameState_800babc8.charIndex_88.get(2).get() != slot) {
          secondaryCharIndices_800bdbf8.get(a2).set(slot);
          a2++;
        }
      }

      //LAB_80103bb4
    }
  }

  @Method(0x80103bd4L)
  public static SavedGameDisplayData updateSaveGameDisplayData(final String filename, final int fileIndex) {
    final int char0 = gameState_800babc8.charIndex_88.get(0).get();
    final int char1 = gameState_800babc8.charIndex_88.get(1).get();
    final int char2 = gameState_800babc8.charIndex_88.get(2).get();
    final int level = gameState_800babc8.charData_32c.get(0).level_12.get();
    final int dlevel = stats_800be5f8.get(0).dlevel_0f.get();
    final int hp = gameState_800babc8.charData_32c.get(0).hp_08.get();
    final int maxHp = stats_800be5f8.get(0).maxHp_66.get();
    final int gold = gameState_800babc8.gold_94.get();
    final int timestamp = gameState_800babc8.timestamp_a0.get();
    final int dragoonSpirits = (int)(gameState_800babc8.dragoonSpirits_19c.get(0).get() & 0x1ff);
    final int stardust = gameState_800babc8.stardust_9c.get();

    final int placeIndex;
    final int placeType;
    if(mainCallbackIndex_8004dd20.get() == 8) {
      placeIndex = continentIndex_800bf0b0.get();
      placeType = 1;
      //LAB_80103c98
    } else if(whichMenu_800bdc38 == WhichMenu.RENDER_SAVE_GAME_MENU_19) {
      placeIndex = gameState_800babc8.chapterIndex_98.get();
      placeType = 3;
    } else {
      placeIndex = submapIndex_800bd808.get();
      placeType = 0;
    }

    return new SavedGameDisplayData(filename, fileIndex, char0, char1, char2, level, dlevel, hp, maxHp, gold, timestamp, dragoonSpirits, stardust, placeIndex, placeType);
  }

  @Method(0x80103cc4L)
  public static void renderText(final LodString text, final int x, final int y, final int a3) {
    final int s2;
    if(a3 == 2) {
      //LAB_80103d18
      s2 = 1;
    } else if(a3 == 6) {
      //LAB_80103d20
      s2 = 7;
    } else {
      s2 = 6;
    }

    //LAB_80103d24
    //LAB_80103d28
    Scus94491BpeSegment_8002.renderText(text, x    , y    , a3, 0);
    Scus94491BpeSegment_8002.renderText(text, x    , y + 1, s2, 0);
    Scus94491BpeSegment_8002.renderText(text, x + 1, y    , s2, 0);
    Scus94491BpeSegment_8002.renderText(text, x + 1, y + 1, s2, 0);
  }

  @Method(0x80103dd4L)
  public static int textLength(final LodString text) {
    //LAB_80103ddc
    int v1;
    for(v1 = 0; v1 < 0xff; v1++) {
      if(text.charAt(v1) == 0xa0ff) {
        break;
      }
    }

    //LAB_80103dfc
    return v1;
  }

  @Method(0x80103e90L)
  public static void renderCentredText(final LodString text, final int x, final int y, final int a3) {
    renderText(text, x - textWidth(text) / 2, y, a3);
  }

  /**
   * @param scrollAmount I'm pretty sure this is the amount the window scrolls when you reach the end of the elements that are currently on screen
   */
  @Method(0x80103f00L)
  public static boolean scrollMenu(final IntRef selectedSlot, @Nullable final IntRef scroll, int slotsDisplayed, int slotCount, final int scrollAmount) {
    slotsDisplayed = Math.min(slotsDisplayed, slotCount);

    if((inventoryJoypadInput_800bdc44.get() & 0x1000) != 0) { // Up
      if(selectedSlot.get() == 0) {
        if(scroll == null || scroll.get() == 0) { // Wrap around up
          selectedSlot.set(Math.max(0, slotsDisplayed - 1));

          if(scroll != null) {
            scroll.set(slotCount - slotsDisplayed);
          }
        } else {
          //LAB_80103f44
          if(scroll.get() < scrollAmount) {
            return true;
          }

          scroll.sub(scrollAmount);
        }
      } else {
        selectedSlot.decr();
      }
      //LAB_80103f64
    } else if((inventoryJoypadInput_800bdc44.get() & 0x4000) != 0) { // Down
      if(selectedSlot.get() < slotsDisplayed - 1) {
        selectedSlot.incr();
      } else {
        if(scroll == null || scroll.get() + slotsDisplayed == slotCount) {
          selectedSlot.set(0);

          if(scroll != null) {
            scroll.set(0);
          }
        } else {
          //LAB_80103f8c
          if(slotCount <= scroll.get() + slotsDisplayed * scrollAmount) {
            return true;
          }

          scroll.add(scrollAmount);
        }
      }
      //LAB_80103fb0
    } else if((inventoryJoypadInput_800bdc44.get() & 0x4) != 0) { // L1
      if(selectedSlot.get() != 0) {
        playSound(0x1L);
        selectedSlot.set(0);
      }

      return true;
      //LAB_80103fdc
    } else if((inventoryJoypadInput_800bdc44.get() & 0x1) != 0) { // L2
      if(selectedSlot.get() >= slotsDisplayed - 1) {
        return true;
      }

      playSound(0x1L);
      selectedSlot.set(slotsDisplayed - 1);
      return true;
      //LAB_80104008
    } else if((inventoryJoypadInput_800bdc44.get() & 0x8) == 0 || scroll.get() < scrollAmount) { // R1
      //LAB_8010404c
      if((inventoryJoypadInput_800bdc44.get() & 0x2) == 0) { // R2
        return false;
      }

      if(slotsDisplayed >= slotCount) {
        return false;
      }

      final int v1 = scroll.get() + slotsDisplayed * scrollAmount;
      slotCount -= slotsDisplayed * scrollAmount;
      if(v1 < slotCount) {
        scroll.set(v1);
        //LAB_8010408c
      } else if(scroll.get() < slotCount) {
        scroll.set(slotCount);
      } else {
        return false;
      }
    } else if(scroll.get() >= slotsDisplayed * scrollAmount) {
      scroll.sub(slotsDisplayed * scrollAmount);
    } else {
      //LAB_80104044
      scroll.set(0);
    }

    //LAB_80104098
    playSound(0x1L);

    //LAB_801040a0
    //LAB_801040ac
    return true;
  }

  @Method(0x801040c0L)
  public static boolean handleMenuUpDown(final IntRef menuIndex, final int menuOptionCount) {
    if((inventoryJoypadInput_800bdc44.get() & 0x1000) != 0) { // Up
      playSound(0x1L);

      if(menuIndex.get() != 0) {
        menuIndex.decr();
      } else {
        //LAB_80104108
        menuIndex.set(menuOptionCount - 1);
      }

      //LAB_8010410c
      //LAB_80104118
    } else if((inventoryJoypadInput_800bdc44.get() & 0x4000) != 0) { // Down
      playSound(0x1L);

      if(menuIndex.get() < menuOptionCount - 1) {
        menuIndex.incr();
      } else {
        menuIndex.set(0);
      }
    } else {
      return false;
    }

    //LAB_80104110
    //LAB_80104148
    return true;
  }

  @Method(0x8010415cL)
  public static boolean handleMenuLeftRight(final IntRef menuIndex, final int menuItemCount) {
    if((inventoryJoypadInput_800bdc44.get() & 0x8000) != 0 && menuIndex.get() != 0) { // Left
      menuIndex.decr();
      playSound(1);
      return true;
    }

    //LAB_80104184
    if((inventoryJoypadInput_800bdc44.get() & 0x2000) != 0 && menuIndex.get() < menuItemCount - 1) { // Right
      //LAB_801041b0
      menuIndex.incr();
      playSound(0x1L);

      //LAB_801041c8
      return true;
    }

    //LAB_801041c4
    return false;
  }

  @Method(0x801041d8L)
  public static YesNoResult handleYesNo(final IntRef menuOption) {
    if(handleMenuUpDown(menuOption, 2)) {
      return YesNoResult.SCROLLED;
    }

    if((inventoryJoypadInput_800bdc44.get() & 0x40) != 0) { // Circle/cancel
      playSound(0x3L);
      return YesNoResult.CANCELLED;
    }

    //LAB_80104220
    if((inventoryJoypadInput_800bdc44.get() & 0x20) != 0) { // Cross/accept
      playSound(0x2L);

      if(menuOption.get() == 0) {
        return YesNoResult.YES;
      }

      return YesNoResult.NO;
    }

    //LAB_80104244
    return YesNoResult.NONE;
  }

  @Method(0x801038d4L)
  public static Renderable58 FUN_801038d4(final int glyph, final int x, final int y) {
    final Renderable58 renderable = allocateUiElement(glyph, glyph, x, y);
    renderable.flags_00 |= 0x8;
    return renderable;
  }

  @Method(0x80104738L)
  public static long FUN_80104738(final long a0) {
    //LAB_8010476c
    for(int i = 0; i < 0x130; i++) {
      _8011dcb8.get(0).deref().get(i).itemId_00.set(0xff);
      _8011dcb8.get(1).deref().get(i).itemId_00.set(0xff);
    }

    //LAB_801047bc
    for(int i = 0; i < gameState_800babc8.itemCount_1e6.get(); i++) {
      _8011dcb8.get(1).deref().get(i).itemId_00.set(gameState_800babc8.items_2e9.get(i).get());
      _8011dcb8.get(1).deref().get(i).itemSlot_01.set(i);
      _8011dcb8.get(1).deref().get(i).price_02.set(0);
    }

    //LAB_8010480c
    //LAB_8010482c
    int s1;
    for(s1 = 0; s1 < gameState_800babc8.equipmentCount_1e4.get(); s1++) {
      _8011dcb8.get(0).deref().get(s1).itemId_00.set(gameState_800babc8.equipment_1e8.get(s1).get());
      _8011dcb8.get(0).deref().get(s1).itemSlot_01.set(s1);
      _8011dcb8.get(0).deref().get(s1).price_02.set(0);

      if(a0 != 0 && FUN_80022898(gameState_800babc8.equipment_1e8.get(s1).get()) != 0) {
        _8011dcb8.get(0).deref().get(s1).price_02.or(0x2000);
      }

      //LAB_80104898
    }

    //LAB_801048ac
    if(a0 != 0) {
      return 0;
    }

    int s2 = s1;
    int t0 = 0;

    //LAB_801048e0
    for(int i = 0; i < characterCount_8011d7c4.get(); i++) {
      //LAB_801048e8
      for(int a1 = 0; a1 < 5; a1++) {
        if(gameState_800babc8.charData_32c.get(characterIndices_800bdbb8.get(i).get()).equipment_14.get(a1).get() != 0xff) {
          _8011dcb8.get(0).deref().get(s2).itemId_00.set(gameState_800babc8.charData_32c.get(characterIndices_800bdbb8.get(i).get()).equipment_14.get(a1).get());
          _8011dcb8.get(0).deref().get(s2).itemSlot_01.set(s2);
          _8011dcb8.get(0).deref().get(s2).price_02.set(0x3000 | characterIndices_800bdbb8.get(i).get());

          t0++;
          s2++;
        }

        //LAB_80104968
      }
    }

    //LAB_8010498c
    //LAB_80104990
    return t0;
  }

  @Method(0x801049b4L)
  public static int loadAdditions(final int charIndex, final ArrayRef<MenuAdditionInfo> additions) {
    //LAB_801049c8
    for(int i = 0; i < 9; i++) {
      additions.get(i).offset_00.set(-1);
      additions.get(i).index_01.set(-1);
    }

    if(charIndex == -1) {
      return 0;
    }

    if(additionOffsets_8004f5ac.get(charIndex).get() == -1) { // No additions (Shiranda)
      //LAB_80104a08
      return 0;
    }

    //LAB_80104a10
    //LAB_80104a54
    int t5 = 0;
    int t0 = 0;
    for(int additionIndex = 0; additionIndex < additionCounts_8004f5c0.get(charIndex).get(); additionIndex++) {
      final long a0_0 = additionData_80052884.get(additionOffsets_8004f5ac.get(charIndex).get() + additionIndex)._00.get();

      if(a0_0 == -1 && (gameState_800babc8.charData_32c.get(charIndex).partyFlags_04.get() & 0x40L) != 0) {
        additions.get(t0).offset_00.set(additionOffsets_8004f5ac.get(charIndex).get() + additionIndex);
        additions.get(t0).index_01.set(additionIndex);
        t0++;
        //LAB_80104aa4
      } else if(a0_0 > 0 && a0_0 <= gameState_800babc8.charData_32c.get(charIndex).level_12.get()) {
        additions.get(t0).offset_00.set(additionOffsets_8004f5ac.get(charIndex).get() + additionIndex);
        additions.get(t0).index_01.set(additionIndex);

        if(gameState_800babc8.charData_32c.get(charIndex).additionLevels_1a.get(additionIndex).get() == 0) {
          gameState_800babc8.charData_32c.get(charIndex).additionLevels_1a.get(additionIndex).set(1);
        }

        //LAB_80104aec
        if(a0_0 == gameState_800babc8.charData_32c.get(charIndex).level_12.get()) {
          t5 = additionOffsets_8004f5ac.get(charIndex).get() + additionIndex + 1;
        }

        t0++;
      }

      //LAB_80104b00
    }

    //LAB_80104b14
    return t5;
  }

  @Method(0x80104b1cL)
  public static void initGlyph(final Renderable58 a0, final MenuGlyph06 glyph) {
    if(glyph.glyph_00.get() != 0xff) {
      a0.glyph_04 = glyph.glyph_00.get();
      a0.flags_00 |= 0x4;
    }

    //LAB_80104b40
    a0.tpage_2c = 0x19;
    a0.clut_30 = 0;
    a0.x_40 = glyph.x_02.get();
    a0.y_44 = glyph.y_04.get();
  }

  @Method(0x80104b60L)
  public static void FUN_80104b60(final Renderable58 a0) {
    a0._28 = 0x1;
    a0._34 = 0;
    a0._38 = 0;
    a0.z_3c = 0x23;
  }

  @Method(0x80104b7cL)
  public static boolean hasDragoon(final long dragoons, final int charIndex) {
    //LAB_80104b94
    if(charIndex == -1) {
      return false;
    }

    //LAB_80104be0
    if(charIndex == 0 && (dragoons & 0xff) >>> 7 != 0) { // Divine
      return true;
    }

    //LAB_80104c24
    //LAB_80104c28
    return (dragoons & 0x1L << (_800fba58.offset(charIndex * 0x4L).get() & 0x1fL)) > 0;
  }

  @Method(0x80104c30L)
  public static void renderTwoDigitNumber(final int x, final int y, final int value) {
    renderNumber(x, y, value, 0, 2);
  }

  @Method(0x80104dd4L)
  public static void renderThreeDigitNumber(final int x, final int y, final int value) {
    renderNumber(x, y, value, 0, 3);
  }

  @Method(0x80105048L)
  public static int renderThreeDigitNumberComparison(final int x, final int y, final int currentVal, int newVal) {
    long flags = 0;
    final int clut;
    if(currentVal < newVal) {
      clut = 0x7c6b;
      //LAB_80105090
    } else if(currentVal > newVal) {
      clut = 0x7c2b;
    } else {
      clut = 0;
    }

    //LAB_801050a0
    //LAB_801050a4
    if(newVal > 999) {
      newVal = 999;
    }

    //LAB_801050b0
    int s0 = newVal / 100 % 10;
    if(s0 != 0) {
      //LAB_80105108
      final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      //LAB_80105138
      //LAB_8010513c
      renderable.flags_00 |= 0xc;
      renderable.glyph_04 = s0;
      renderable.tpage_2c = 0x19;
      renderable.clut_30 = clut;
      renderable.z_3c = 0x21;
      renderable.x_40 = x;
      renderable.y_44 = y;
      flags |= 0x1L;
    }

    //LAB_80105190
    s0 = newVal / 10 % 10;
    if(s0 != 0 || (flags & 0x1L) != 0) {
      //LAB_801051ec
      final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      //LAB_8010521c
      //LAB_80105220
      renderable.flags_00 |= 0xc;
      renderable.glyph_04 = s0;
      renderable.tpage_2c = 0x19;
      renderable.clut_30 = clut;
      renderable.z_3c = 0x21;
      renderable.x_40 = x + 6;
      renderable.y_44 = y;
    }

    //LAB_80105274
    s0 = newVal % 10;
    final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
    //LAB_801052d8
    //LAB_801052dc
    renderable.flags_00 |= 0xc;
    renderable.glyph_04 = s0;
    renderable.tpage_2c = 0x19;
    renderable.clut_30 = clut;
    renderable.z_3c = 0x21;
    renderable.x_40 = x + 12;
    renderable.y_44 = y;
    return clut;
  }

  @Method(0x80105350L)
  public static void renderFourDigitNumber(final int x, final int y, final int value) {
    renderNumber(x, y, value, 0, 4);
  }

  /** Does something different with CLUT */
  @Method(0x8010568cL)
  public static void renderFourDigitNumber(final int x, final int y, int value, final int max) {
    int clut = 0;
    long flags = 0;

    if(value >= 9999) {
      value = 9999;
    }

    //LAB_801056d0
    if(max > 9999) {
      value = 9999;
    }

    //LAB_801056e0
    if(value < max / 2) {
      clut = 0x7cab;
    }

    //LAB_801056f0
    if(value < max / 10) {
      clut = 0x7c2b;
    }

    //LAB_80105714
    int s0 = value / 1_000 % 10;
    if(s0 != 0) {
      final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      renderable.glyph_04 = s0;
      //LAB_80105784
      //LAB_80105788
      renderable.flags_00 |= 0x4;
      renderable.tpage_2c = 0x19;
      renderable.x_40 = x;
      renderable.y_44 = y;
      renderable.clut_30 = clut;
      renderable.z_3c = 0x21;
      flags |= 0x1L;
    }

    //LAB_801057c0
    //LAB_801057d0
    s0 = value / 100 % 10;
    if(s0 != 0 || (flags & 0x1L) != 0) {
      //LAB_80105830
      final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      renderable.glyph_04 = s0;
      //LAB_80105860
      //LAB_80105864
      renderable.flags_00 |= 0x4;
      renderable.tpage_2c = 0x19;
      renderable.x_40 = x + 6;
      renderable.y_44 = y;
      renderable.clut_30 = clut;
      renderable.z_3c = 0x21;
      flags |= 0x1L;
    }

    //LAB_801058a0
    //LAB_801058ac
    s0 = value / 10 % 10;
    if(s0 != 0 || (flags & 0x1L) != 0) {
      //LAB_80105908
      final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      renderable.glyph_04 = s0;
      //LAB_80105938
      //LAB_8010593c
      renderable.flags_00 |= 0x4;
      renderable.tpage_2c = 0x19;
      renderable.x_40 = x + 12;
      renderable.y_44 = y;
      renderable.clut_30 = clut;
      renderable.z_3c = 0x21;
    }

    //LAB_80105978
    //LAB_80105984
    s0 = value % 10;
    final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
    renderable.glyph_04 = s0;
    //LAB_801059e8
    //LAB_801059ec
    renderable.flags_00 |= 0x4;
    renderable.tpage_2c = 0x19;
    renderable.x_40 = x + 18;
    renderable.y_44 = y;
    renderable.clut_30 = clut;
    renderable.z_3c = 0x21;
  }

  @Method(0x80105a50L)
  public static void renderSixDigitNumber(final int x, final int y, int value) {
    long flags = 0;

    if(value > 999999) {
      value = 999999;
    }

    //LAB_80105a98
    int s0 = value / 100_000 % 10;
    if(s0 != 0) {
      final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      struct.glyph_04 = s0;
      //LAB_80105b10
      //LAB_80105b14
      struct.flags_00 |= 0x4;
      struct.tpage_2c = 0x19;
      struct.clut_30 = 0;
      struct.z_3c = 0x21;
      struct.x_40 = x;
      struct.y_44 = y;
      flags |= 0x1L;
    }

    //LAB_80105b4c
    s0 = value / 10_000 % 10;
    if(s0 != 0 || (flags & 0x1L) != 0) {
      //LAB_80105ba8
      final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      struct.glyph_04 = s0;
      //LAB_80105bd8
      //LAB_80105bdc
      struct.flags_00 |= 0x4;
      struct.tpage_2c = 0x19;
      struct.clut_30 = 0;
      struct.z_3c = 0x21;
      struct.x_40 = x + 6;
      struct.y_44 = y;
      flags |= 0x1L;
    }

    //LAB_80105c18
    s0 = value / 1_000 % 10;
    if(s0 != 0 || (flags & 0x1L) != 0) {
      //LAB_80105c70
      final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      struct.glyph_04 = s0;
      //LAB_80105ca0
      //LAB_80105ca4
      struct.flags_00 |= 0x4;
      struct.tpage_2c = 0x19;
      struct.clut_30 = 0;
      struct.z_3c = 0x21;
      struct.x_40 = x + 12;
      struct.y_44 = y;
      flags |= 0x1L;
    }

    //LAB_80105ce0
    s0 = value / 100 % 10;
    if(s0 != 0 || (flags & 0x1L) != 0) {
      //LAB_80105d38
      final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      struct.glyph_04 = s0;
      //LAB_80105d68
      //LAB_80105d6c
      struct.flags_00 |= 0x4;
      struct.tpage_2c = 0x19;
      struct.clut_30 = 0;
      struct.z_3c = 0x21;
      struct.x_40 = x + 18;
      struct.y_44 = y;
      flags |= 0x1L;
    }

    //LAB_80105da4
    s0 = value / 10 % 10;
    if(s0 != 0 || (flags & 0x1L) != 0) {
      //LAB_80105dfc
      final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      struct.glyph_04 = s0;
      //LAB_80105e2c
      //LAB_80105e30
      struct.flags_00 |= 0x4;
      struct.tpage_2c = 0x19;
      struct.clut_30 = 0;
      struct.z_3c = 0x21;
      struct.x_40 = x + 24;
      struct.y_44 = y;
    }

    //LAB_80105e68
    s0 = value % 10;
    final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
    struct.glyph_04 = s0;
    //LAB_80105ecc
    //LAB_80105ed0
    struct.flags_00 |= 0x4;
    struct.tpage_2c = 0x19;
    struct.clut_30 = 0;
    struct.z_3c = 0x21;
    struct.x_40 = x + 30;
    struct.y_44 = y;
  }

  @Method(0x80105f2cL)
  public static void renderEightDigitNumber(final int x, final int y, final int value, final long flags) {
    renderNumber(x, y, value, flags, 8);
  }

  @Method(0x801065bcL)
  public static void renderFiveDigitNumber(final int x, final int y, final int value) {
    renderNumber(x, y, value, 0x2L, 5);
  }

  @Method(0x801069d0L)
  public static void FUN_801069d0(final int x, final int y, final int value) {
    // I didn't look at this method too closely, this may or may not be right
    renderNumber(x, y, value, 0x2L, 4);
  }

  /**
   * @param flags Bitset - 0x1: render leading zeros, 0x2: ?
   */
  public static void renderNumber(final int x, final int y, int value, long flags, final int digitCount) {
    if(value >= Math.pow(10, digitCount)) {
      value = (int)Math.pow(10, digitCount) - 1;
    }

    for(int i = 0; i < digitCount; i++) {
      final int digit = value / (int)Math.pow(10, digitCount - (i + 1)) % 10;

      if(digit != 0 || i == digitCount - 1 || (flags & 0x1L) != 0) {
        final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
        struct.flags_00 |= (flags & 0x2) != 0 ? 0xc : 0x4;
        struct.glyph_04 = digit;
        struct.tpage_2c = 0x19;
        struct.clut_30 = 0;
        struct.z_3c = 0x21;
        struct.x_40 = x + 6 * i;
        struct.y_44 = y;
        flags |= 0x1L;
      }
    }
  }

  @Method(0x80107764L)
  public static void renderThreeDigitNumber(final int x, final int y, final int value, final long flags) {
    renderNumber(x, y, value, flags, 3);
  }

  @Method(0x801079fcL)
  public static void renderTwoDigitNumber(final int x, final int y, final int value, final long flags) {
    renderNumber(x, y, value, flags, 2);
  }

  @Method(0x80107cb4L)
  public static void renderCharacter(final int x, final int y, final int character) {
    final Renderable58 v0 = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
    v0.flags_00 |= 0x4;
    v0.glyph_04 = character;
    v0.tpage_2c = 0x19;
    v0.clut_30 = 0x7ca9;
    v0.z_3c = 0x21;
    v0.x_40 = x;
    v0.y_44 = y;
  }

  @Method(0x80107d34L)
  public static void renderThreeDigitNumberComparisonWithPercent(final int x, final int y, final int currentVal, final int newVal) {
    final int clut = renderThreeDigitNumberComparison(x, y, currentVal, newVal);
    final Renderable58 v0 = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
    v0.flags_00 |= 0xc;
    v0.glyph_04 = 0xc;
    v0.tpage_2c = 0x19;
    v0.clut_30 = clut;
    v0.z_3c = 0x21;
    v0.x_40 = x + 20;
    v0.y_44 = y;
  }

  @Method(0x80107dd4L)
  public static void renderXp(final int x, final int y, final int xp) {
    if(xp != 0) {
      renderSixDigitNumber(x, y, xp);
    } else {
      //LAB_80107e08
      final Renderable58 v0 = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);
      v0.flags_00 |= 0x4;
      v0.glyph_04 = 218;
      v0.tpage_2c = 0x19;
      v0.clut_30 = 0x7ca9;
      v0.z_3c = 0x21;
      v0.x_40 = x + 30;
      v0.y_44 = y;
    }

    //LAB_80107e58
  }

  @Method(0x80107e70L)
  public static long FUN_80107e70(final int x, final int y, final int charIndex) {
    //LAB_80107e90
    final long a0_0 = gameState_800babc8.charData_32c.get(charIndex).status_10.get();

    if((tickCount_800bb0fc.get() & 0x10L) == 0) {
      return 0;
    }

    long v1 = a0_0 & 0x1L;

    if((a0_0 & 0x2L) != 0) {
      v1 = 0x2L;
    }

    //LAB_80107f00
    if((a0_0 & 0x4L) != 0) {
      v1 = 0x3L;
    }

    //LAB_80107f10
    if((a0_0 & 0x8L) != 0) {
      v1 = 0x4L;
    }

    //LAB_80107f1c
    if((a0_0 & 0x10L) != 0) {
      v1 = 0x5L;
    }

    //LAB_80107f28
    if((a0_0 & 0x20L) != 0) {
      v1 = 0x6L;
    }

    //LAB_80107f34
    if((a0_0 & 0x40L) != 0) {
      v1 = 0x7L;
    }

    //LAB_80107f40
    if((a0_0 & 0x80L) != 0) {
      v1 = 0x8L;
    }

    //LAB_80107f50
    if(v1 == 0) {
      //LAB_80107f88
      return 0;
    }

    final MenuStruct08 struct = _800fba7c.get((int)(v1 - 0x1L));
    renderCentredText(struct.text_00.deref(), x + 24, y, struct._04.get());

    //LAB_80107f8c
    return 0x1L;
  }

  @Method(0x80107f9cL)
  public static void renderCharacterSlot(final int x, final int y, final int charIndex, final boolean allocate, final boolean dontSelect) {
    if(charIndex != -1) {
      if(allocate) {
        allocateUiElement( 74,  74, x, y).z_3c = 33;
        allocateUiElement(153, 153, x, y);

        if(charIndex < 9) {
          final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._cfac, null);
          initGlyph(struct, glyph_801142d4);
          struct.glyph_04 = charIndex;
          struct.tpage_2c++;
          struct.z_3c = 33;
          struct.x_40 = x + 8;
          struct.y_44 = y + 8;
        }

        //LAB_80108098
        final ActiveStatsa0 stats = stats_800be5f8.get(charIndex);
        renderTwoDigitNumber(x + 154, y + 6, stats.level_0e.get());
        renderTwoDigitNumber(x + 112, y + 17, stats.dlevel_0f.get());
        renderThreeDigitNumber(x + 148, y + 17, stats.sp_08.get());
        renderFourDigitNumber(x + 100, y + 28, gameState_800babc8.charData_32c.get(charIndex).hp_08.get(), stats.maxHp_66.get());
        renderCharacter(x + 124, y + 28, 11);
        renderFourDigitNumber(x + 142, y + 28, stats.maxHp_66.get());
        renderThreeDigitNumber(x + 106, y + 39, stats.mp_06.get());
        renderCharacter(x + 124, y + 39, 11);
        renderThreeDigitNumber(x + 148, y + 39, stats.maxMp_6e.get());
        renderSixDigitNumber(x + 88, y + 50, gameState_800babc8.charData_32c.get(charIndex).xp_00.get());
        renderCharacter(x + 124, y + 50, 11);
        renderXp(x + 130, y + 50, getXpToNextLevel(charIndex));

        // Render "don't select" overlay
        if(dontSelect) {
          final Renderable58 struct = allocateUiElement(113, 113, x + 56, y + 24);
          struct.z_3c = 33;
        }
      }

      //LAB_80108218
      if(FUN_80107e70(x + 48, y + 3, charIndex) == 0) {
        renderText(characterNames_801142dc.get(charIndex).deref(), x + 48, y + 3, 4);
      }
    }

    //LAB_80108270
  }

  @Method(0x801082a0L)
  public static void FUN_801082a0(final int x, final int y, final int charIndex, final boolean allocate) {
    if(allocate && charIndex != -1) {
      if(charIndex < 9) {
        final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._cfac, null);
        initGlyph(renderable, glyph_801142d4);
        renderable.glyph_04 = charIndex;
        renderable.tpage_2c++;
        renderable.z_3c = 33;
        renderable.x_40 = x + 2;
        renderable.y_44 = y + 8;
      }

      //LAB_8010834c
      allocateUiElement(0x50, 0x50, x, y).z_3c = 33;
      allocateUiElement(0x9c, 0x9c, x, y);

      if((gameState_800babc8.charData_32c.get(charIndex).partyFlags_04.get() & 0x2L) == 0) {
        allocateUiElement(0x72, 0x72, x, y + 24).z_3c = 33;
      }

      //LAB_801083c4
      final ActiveStatsa0 stats = stats_800be5f8.get(charIndex);
      renderFourDigitNumber(x + 25, y + 57, stats.level_0e.get());
      renderFourDigitNumber(x + 25, y + 68, stats.dlevel_0f.get());
      renderFourDigitNumber(x + 25, y + 79, stats.hp_04.get(), stats.maxHp_66.get());
      renderFourDigitNumber(x + 25, y + 90, stats.mp_06.get());
    }

    //LAB_80108438
  }

  @Method(0x801085e0L)
  public static void renderCharacterStats(final int charIndex, final int equipmentId, final boolean allocate) {
    if(charIndex != -1) {
      final Memory.TemporaryReservation sp0x10tmp = MEMORY.temp(0xa0);
      final ActiveStatsa0 statsTmp = sp0x10tmp.get().cast(ActiveStatsa0::new);

      if(equipmentId != 0xff) {
        final Memory.TemporaryReservation sp0xb0tmp = MEMORY.temp(0x5);

        //LAB_80108638
        memcpy(sp0xb0tmp.address, gameState_800babc8.charData_32c.get(charIndex).equipment_14.getAddress(), 5);

        equipItem(equipmentId, charIndex);
        loadCharacterStats(0);

        //LAB_80108694
        memcpy(statsTmp.getAddress(), stats_800be5f8.get(charIndex).getAddress(), 0xa0);

        //LAB_801086e8
        memcpy(gameState_800babc8.charData_32c.get(charIndex).equipment_14.getAddress(), sp0xb0tmp.address, 5);

        sp0xb0tmp.release();

        loadCharacterStats(0);
      } else {
        //LAB_80108720
        //LAB_80108740
        memcpy(statsTmp.getAddress(), stats_800be5f8.get(charIndex).getAddress(), 0xa0);
      }

      //LAB_80108770
      final ActiveStatsa0 stats = stats_800be5f8.get(charIndex);
      renderThreeDigitNumberComparison( 58, 116, stats.bodyAttack_6a.get(), statsTmp.bodyAttack_6a.get());
      renderThreeDigitNumberComparison( 90, 116, stats.gearAttack_88.get(), statsTmp.gearAttack_88.get());
      renderThreeDigitNumberComparison(122, 116, stats.bodyAttack_6a.get() + stats.gearAttack_88.get(), statsTmp.bodyAttack_6a.get() + statsTmp.gearAttack_88.get());

      if(hasDragoon(gameState_800babc8.dragoonSpirits_19c.get(0).get(), charIndex)) {
        renderThreeDigitNumberComparisonWithPercent(159, 116, stats.dragoonAttack_72.get(), statsTmp.dragoonAttack_72.get());
      }

      //LAB_801087fc
      renderThreeDigitNumberComparison( 58, 128, stats.bodyDefence_6c.get(), statsTmp.bodyDefence_6c.get());
      renderThreeDigitNumberComparison( 90, 128, stats.gearDefence_8c.get(), statsTmp.gearDefence_8c.get());
      renderThreeDigitNumberComparison(122, 128, stats.bodyDefence_6c.get() + stats.gearDefence_8c.get(), statsTmp.bodyDefence_6c.get() + statsTmp.gearDefence_8c.get());

      if(hasDragoon(gameState_800babc8.dragoonSpirits_19c.get(0).get(), charIndex)) {
        renderThreeDigitNumberComparisonWithPercent(159, 128, stats.dragoonDefence_74.get(), statsTmp.dragoonDefence_74.get());
      }

      //LAB_8010886c
      renderThreeDigitNumberComparison( 58, 140, stats.bodyMagicAttack_6b.get(), statsTmp.bodyMagicAttack_6b.get());
      renderThreeDigitNumberComparison( 90, 140, stats.gearMagicAttack_8a.get(), statsTmp.gearMagicAttack_8a.get());
      renderThreeDigitNumberComparison(122, 140, stats.bodyMagicAttack_6b.get() + stats.gearMagicAttack_8a.get(), statsTmp.bodyMagicAttack_6b.get() + statsTmp.gearMagicAttack_8a.get());

      if(hasDragoon(gameState_800babc8.dragoonSpirits_19c.get(0).get(), charIndex)) {
        renderThreeDigitNumberComparisonWithPercent(159, 140, stats.dragoonMagicAttack_73.get(), statsTmp.dragoonMagicAttack_73.get());
      }

      //LAB_801088dc
      renderThreeDigitNumberComparison( 58, 152, stats.bodyMagicDefence_6d.get(), statsTmp.bodyMagicDefence_6d.get());
      renderThreeDigitNumberComparison( 90, 152, stats.gearMagicDefence_8e.get(), statsTmp.gearMagicDefence_8e.get());
      renderThreeDigitNumberComparison(122, 152, stats.bodyMagicDefence_6d.get() + stats.gearMagicDefence_8e.get(), statsTmp.bodyMagicDefence_6d.get() + statsTmp.gearMagicDefence_8e.get());

      if(hasDragoon(gameState_800babc8.dragoonSpirits_19c.get(0).get(), charIndex)) {
        renderThreeDigitNumberComparisonWithPercent(159, 152, stats.dragoonMagicDefence_75.get(), statsTmp.dragoonMagicDefence_75.get());
      }

      //LAB_8010894c
      renderThreeDigitNumberComparison( 58, 164, stats.bodySpeed_69.get(), statsTmp.bodySpeed_69.get());
      renderThreeDigitNumberComparison( 90, 164, stats.gearSpeed_86.get(), statsTmp.gearSpeed_86.get());
      renderThreeDigitNumberComparison(122, 164, stats.bodySpeed_69.get() + stats.gearSpeed_86.get(), statsTmp.bodySpeed_69.get() + statsTmp.gearSpeed_86.get());

      renderThreeDigitNumberComparisonWithPercent( 90, 176, stats.attackHit_90.get(), statsTmp.attackHit_90.get());
      renderThreeDigitNumberComparisonWithPercent(122, 176, stats.attackHit_90.get(), statsTmp.attackHit_90.get());
      renderThreeDigitNumberComparisonWithPercent( 90, 188, stats.magicHit_92.get(), statsTmp.magicHit_92.get());
      renderThreeDigitNumberComparisonWithPercent(122, 188, stats.magicHit_92.get(), statsTmp.magicHit_92.get());
      renderThreeDigitNumberComparisonWithPercent( 90, 200, stats.attackAvoid_94.get(), statsTmp.attackAvoid_94.get());
      renderThreeDigitNumberComparisonWithPercent(122, 200, stats.attackAvoid_94.get(), statsTmp.attackAvoid_94.get());
      renderThreeDigitNumberComparisonWithPercent( 90, 212, stats.magicAvoid_96.get(), statsTmp.magicAvoid_96.get());
      renderThreeDigitNumberComparisonWithPercent(122, 212, stats.magicAvoid_96.get(), statsTmp.magicAvoid_96.get());

      sp0x10tmp.release();

      if(allocate) {
        allocateUiElement(0x56, 0x56, 16, 94);
      }
    }

    //LAB_80108a50
  }

  @Method(0x80108a6cL)
  public static void renderSaveGameSlot(final int fileIndex, final int y, final long a3) {
    final SavedGameDisplayData saveData = saves.get(fileIndex).b();

    if((a3 & 0xff) != 0) {
      renderTwoDigitNumber(21, y, fileIndex + 1); // File number
    }

    //LAB_80108b3c
    final ArrayRef<Pointer<LodString>> locationNames;
    if(saveData.saveType == 1) {
      //LAB_80108b5c
      locationNames = worldMapNames_8011c1ec;
    } else if(saveData.saveType == 3) {
      //LAB_80108b78
      locationNames = chapterNames_80114248;
    } else {
      //LAB_80108b90
      locationNames = submapNames_8011c108;
    }

    //LAB_80108ba0
    renderCentredText(locationNames.get(saveData.locationIndex).deref(), 278, y + 47, 4); // Location text

    if((a3 & 0xff) != 0) {
      allocateUiElement(0x4c, 0x4c,  16, y).z_3c = 33; // Left half of border
      allocateUiElement(0x4d, 0x4d, 192, y).z_3c = 33; // Right half of border

      // Load char 0
      if(saveData.char0Index >= 0 && saveData.char0Index < 9) {
        final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._cfac, null);
        initGlyph(struct, glyph_801142d4);
        struct.glyph_04 = saveData.char0Index;
        struct.tpage_2c++;
        struct.z_3c = 33;
        struct.x_40 = 38;
        struct.y_44 = y + 8;
      }

      // Load char 1
      //LAB_80108c78
      if(saveData.char1Index >= 0 && saveData.char1Index < 9) {
        final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._cfac, null);
        initGlyph(struct, glyph_801142d4);
        struct.glyph_04 = saveData.char1Index;
        struct.tpage_2c++;
        struct.z_3c = 33;
        struct.x_40 = 90;
        struct.y_44 = y + 8;
      }

      // Load char 2
      //LAB_80108cd4
      if(saveData.char2Index >= 0 && saveData.char2Index < 9) {
        final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._cfac, null);
        initGlyph(struct, glyph_801142d4);
        struct.glyph_04 = saveData.char2Index;
        struct.tpage_2c++;
        struct.z_3c = 33;
        struct.x_40 = 142;
        struct.y_44 = y + 8;
      }

      //LAB_80108d30
      renderTwoDigitNumber(224, y + 6, saveData.level); // Level
      renderTwoDigitNumber(269, y + 6, saveData.dlevel); // Dragoon level
      renderFourDigitNumber(302, y + 6, saveData.currentHp); // Current HP
      renderFourDigitNumber(332, y + 6, saveData.maxHp); // Max HP
      renderEightDigitNumber(245, y + 17, saveData.gold, 0); // Gold
      renderThreeDigitNumber(306, y + 17, getTimestampPart(saveData.time, 0), 0x1L); // Time played hour
      renderCharacter(324, y + 17, 10); // Hour-minute colon
      renderTwoDigitNumber(330, y + 17, getTimestampPart(saveData.time, 1), 0x1L); // Time played minute
      renderCharacter(342, y + 17, 10); // Minute-second colon
      renderTwoDigitNumber(348, y + 17, getTimestampPart(saveData.time, 2), 0x1L); // Time played second
      renderTwoDigitNumber(344, y + 34, saveData.stardust); // Stardust
      renderDragoonSpirits(saveData.dragoonSpirits, 223, y + 27);
    }

    //LAB_80108e3c
  }

  @Method(0x80108e60L)
  public static void renderCharacterEquipment(final int charIndex, final boolean allocate) {
    if(charIndex == -1) {
      return;
    }

    final CharacterData2c charData = gameState_800babc8.charData_32c.get(charIndex);

    if(allocate) {
      allocateUiElement(0x59, 0x59, 194, 16);

      if(charData.equipment_14.get(0).get() != 0xff) {
        renderItemIcon(getItemIcon(charData.equipment_14.get(0).get()), 202, 17, 0);
      }

      //LAB_80108ee4
      if(charData.equipment_14.get(1).get() != 0xff) {
        renderItemIcon(getItemIcon(charData.equipment_14.get(1).get()), 202, 31, 0);
      }

      //LAB_80108f10
      if(charData.equipment_14.get(2).get() != 0xff) {
        renderItemIcon(getItemIcon(charData.equipment_14.get(2).get()), 202, 45, 0);
      }

      //LAB_80108f3c
      if(charData.equipment_14.get(3).get() != 0xff) {
        renderItemIcon(getItemIcon(charData.equipment_14.get(3).get()), 202, 59, 0);
      }

      //LAB_80108f68
      if(charData.equipment_14.get(4).get() != 0xff) {
        renderItemIcon(getItemIcon(charData.equipment_14.get(4).get()), 202, 73, 0);
      }
    }

    //LAB_80108f94
    //LAB_80108f98
    renderText(equipment_8011972c.get(charData.equipment_14.get(0).get()).deref(), 220, 19, 4);
    renderText(equipment_8011972c.get(charData.equipment_14.get(1).get()).deref(), 220, 33, 4);
    renderText(equipment_8011972c.get(charData.equipment_14.get(2).get()).deref(), 220, 47, 4);
    renderText(equipment_8011972c.get(charData.equipment_14.get(3).get()).deref(), 220, 61, 4);
    renderText(equipment_8011972c.get(charData.equipment_14.get(4).get()).deref(), 220, 75, 4);

    //LAB_8010905c
  }

  @Method(0x80109074L)
  public static void renderString(final int stringType, final int x, final int y, final int stringIndex, final boolean allocate) {
    if(allocate) {
      allocateUiElement(0x5b, 0x5b, x, y);
    }

    //LAB_801090e0
    LodString s0 = null;
    if(stringType == 0) {
      //LAB_80109118
      if(stringIndex == 0xff) {
        return;
      }

      s0 = _80117a10.get(stringIndex).deref();
    } else if(stringType == 0x1L) {
      //LAB_8010912c
      if(stringIndex >= 0xff) {
        //LAB_80109140
        s0 = _8011c254;
      } else {
        //LAB_80109154
        s0 = _8011b75c.get(stringIndex).deref();
      }
      //LAB_80109108
    } else if(stringType == 0x2L) {
      //LAB_8010914c
      s0 = switch(stringIndex) {
        case 0 -> new LodString("Send gold and items\nDabas has found to\nthe main game.");
        case 1 -> new LodString("Delete items from\nthe Pocket Station.");
        case 2 -> new LodString("Leave for the\nnext adventure.");
        default -> null;
      };
    }

    //LAB_80109160
    //LAB_80109168
    //LAB_80109188
    for(int i = 0; i < 4; i++) {
      int s4 = 0;
      final int len = Math.min(textLength(s0), 20);

      final Memory.TemporaryReservation sp0x10tmp = MEMORY.temp((len + 1) * 2);
      final LodString s3 = sp0x10tmp.get().cast(LodString::new);

      //LAB_801091bc
      //LAB_801091cc
      int a1;
      for(a1 = 0; a1 < len; a1++) {
        if(s0.charAt(a1) == 0xa1ffL) {
          //LAB_8010924c
          s4 = 1;
          break;
        }

        s3.charAt(a1, s0.charAt(a1));
      }

      //LAB_801091fc
      s3.charAt(a1, 0xa0ff);

      renderText(s3, x + 2, y + i * 14 + 4, 4);

      if(textLength(s3) > len) {
        //LAB_80109270
        break;
      }

      //LAB_80109254
      s0 = s0.slice(textLength(s3) + s4);

      sp0x10tmp.release();
    }

    //LAB_80109284
  }

  @Method(0x80109410L)
  public static void renderMenuItems(final int x, final int y, final ArrayRef<MenuItemStruct04> menuItems, final int slotScroll, final int itemCount, @Nullable final Renderable58 a5, @Nullable final Renderable58 a6) {
    int s3 = slotScroll;

    //LAB_8010947c
    int i;
    MenuItemStruct04 menuItem;
    for(i = 0, menuItem = menuItems.get(s3); i < itemCount && menuItem.itemId_00.get() != 0xff; i++, menuItem = menuItems.get(s3)) {
      //LAB_801094ac
      renderText(equipment_8011972c.get(menuItem.itemId_00.get()).deref(), x + 21, y + FUN_800fc814(i) + 2, (menuItem.price_02.get() & 0x6000) == 0 ? 4 : 6);
      renderItemIcon(getItemIcon(menuItem.itemId_00.get()), x + 4, y + FUN_800fc814(i), 0x8L);

      final int s0 = menuItem.price_02.get();
      if((s0 & 0x1000L) != 0) {
        renderItemIcon(48 | s0 & 0xf, x + 148, y + FUN_800fc814(i) - 1, 0x8L).clut_30 = (500 + (s0 & 0xf) & 0x1ff) << 6 | 0x2b;
        //LAB_80109574
      } else if((s0 & 0x2000L) != 0) {
        renderItemIcon(58, x + 148, y + FUN_800fc814(i) - 1, 0x8L).clut_30 = 0x7eaa;
      }

      //LAB_801095a4
      s3++;
    }

    //LAB_801095c0
    //LAB_801095d4
    //LAB_801095e0
    if(a5 != null) { // There was an NPE here when fading out item list
      if(slotScroll != 0) {
        a5.flags_00 &= 0xffff_ffbf;
      } else {
        a5.flags_00 |= 0x40;
      }
    }

    //LAB_80109614
    //LAB_80109628
    if(a6 != null) { // There was an NPE here when fading out item list
      if(menuItems.get(i + slotScroll).itemId_00.get() != 0xff) {
        a6.flags_00 &= 0xffff_ffbf;
      } else {
        a6.flags_00 |= 0x40;
      }
    }
  }

  @Method(0x8010965cL)
  public static void FUN_8010965c(final int slotScroll, @Nullable final Renderable58 a1, @Nullable final Renderable58 a2) {
    //LAB_801096c8
    int i;
    for(i = 0; i < 14 && menuItems_8011d7c8.get(slotScroll + i).itemId_00.get() != 0xff; i += 2) {
      renderText(_8011c008.get(menuItems_8011d7c8.get(slotScroll + i).itemId_00.get()).deref(), 37, FUN_800fc814(i / 2) + 34, 4);

      if(menuItems_8011d7c8.get(slotScroll + i + 1).itemId_00.get() != 0xff) {
        renderText(_8011c008.get(menuItems_8011d7c8.get(slotScroll + i + 1).itemId_00.get()).deref(), 214, FUN_800fc814(i / 2) + 34, 4);
      }
    }

    //LAB_8010977c
    //LAB_80109790
    //LAB_8010979c
    if(a1 != null) { // There was an NPE here
      if(slotScroll != 0) {
        a1.flags_00 &= 0xffff_ffbf;
      } else {
        a1.flags_00 |= 0x40;
      }
    }

    //LAB_801097d8
    //LAB_801097ec
    if(a2 != null) { // There was an NPE here
      if(menuItems_8011d7c8.get(slotScroll + i).itemId_00.get() != 0xff) {
        a2.flags_00 &= 0xffff_ffbf;
      } else {
        a2.flags_00 |= 0x40;
      }
    }
  }

  @Method(0x80109820L)
  public static Renderable58 FUN_80109820(final int x, final int y, final int glyph) {
    if(glyph >= 0x9L) {
      //LAB_801098a0
      return null;
    }

    final Renderable58 s0 = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._cfac, null);
    initGlyph(s0, glyph_801142d4);
    s0.tpage_2c++;
    s0.glyph_04 = glyph;
    s0.z_3c = 33;
    s0.x_40 = x;
    s0.y_44 = y;

    //LAB_801098a4
    return s0;
  }

  @Method(0x801098c0L)
  public static void renderDragoonSpirits(final int spirits, final int x, final int y) {
    final Memory.TemporaryReservation tmp = MEMORY.temp(0x28);
    final Value sp18 = tmp.get();

    sp18.offset(4, 0x00L).setu(_800fbabc.offset(0x00L));
    sp18.offset(4, 0x04L).setu(_800fbabc.offset(0x04L));
    sp18.offset(4, 0x08L).setu(_800fbabc.offset(0x08L));
    sp18.offset(4, 0x0cL).setu(_800fbabc.offset(0x0cL));
    sp18.offset(4, 0x10L).setu(_800fbabc.offset(0x10L));
    sp18.offset(4, 0x14L).setu(_800fbabc.offset(0x14L));
    sp18.offset(4, 0x18L).setu(_800fbabc.offset(0x18L));
    sp18.offset(4, 0x1cL).setu(_800fbabc.offset(0x1cL));

    //LAB_80109934
    for(int i = 0; i < 8; i++) {
      final long v0 = sp18.offset(i * 0x4L).get();
      if((spirits & 0x1L << (v0 & 0x1fL)) != 0) {
        final Renderable58 struct = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._0000, null);

        final Memory.TemporaryReservation sp0x10tmp = MEMORY.temp(6);
        final MenuGlyph06 glyph = sp0x10tmp.get().cast(MenuGlyph06::new);
        glyph.glyph_00.set(i + 0xd);
        glyph.x_02.set((short)(x + i * 12));
        glyph.y_04.set((short)y);
        initGlyph(struct, glyph);
        sp0x10tmp.release();

        struct.z_3c = 33;
      }

      //LAB_801099a0
    }

    tmp.release();
  }

  @Method(0x8010a0ecL)
  public static void loadSaveFile(final int saveSlot) {
    final byte[] data = SaveManager.loadGame(saves.get(saveSlot).a());

    final int offset = switch((int)MathHelper.get(data, 0, 4)) {
      case 0x01114353 -> 0x200;
      case 0x76615344 -> 0x34;
      default -> throw new RuntimeException("Invalid saved game file");
    };

    MEMORY.setBytes(gameState_800babc8.getAddress(), data, offset, 0x52c);
  }

  @Method(0x8010a344L)
  public static void saveGame(final int slot) {
    final SavedGameDisplayData displayData = updateSaveGameDisplayData(String.valueOf(slot), slot);

    final byte[] data = new byte[0x560];
    displayData.save(data, 0);
    MEMORY.getBytes(gameState_800babc8.getAddress(), data, 0x34, 0x52c);

    if(slot == -1) {
      SaveManager.newSave(data);
    } else {
      SaveManager.overwriteSave(saves.get(slot).a(), data);
    }
  }

  @Method(0x8010a7fcL)
  public static int getShopMenuYOffset(final int index) {
    return index * 16 + 58;
  }

  @Method(0x8010a808L)
  public static int FUN_8010a808(final int index) {
    return index * 17 + 18;
  }

  @Method(0x8010a818L)
  public static int FUN_8010a818(final int index) {
    return index * 50 + 17;
  }

  @Method(0x8010a834L)
  public static int FUN_8010a834(final int index) {
    return index * 17 + 126;
  }

  @Method(0x8010a844L)
  public static void FUN_8010a844(final InventoryMenuState nextMenuState, final long a1) {
    inventoryMenuState_800bdc28.set(InventoryMenuState._16);
    _800bdc2c.setu(a1);
    confirmDest_800bdc30.set(nextMenuState);
  }

  @Method(0x8010a864L)
  public static int FUN_8010a864(final int equipmentId) {
    int s3 = -1;

    //LAB_8010a8a4
    for(int i = 0; i < 7; i++) {
      if(characterIndices_800bdbb8.get(i).get() != -1) {
        characterRenderables_8011e148[i].y_44 = 174;

        if(equipmentId != 0xff) {
          if(!canEquip(equipmentId, characterIndices_800bdbb8.get(i).get())) {
            characterRenderables_8011e148[i].y_44 = 250;
            //LAB_8010a8f0
          } else if(s3 == -1) {
            s3 = i;
          }
        }
      }

      //LAB_8010a8fc
    }

    if(s3 == -1) {
      s3 = 0;
    }

    //LAB_8010a924
    return s3;
  }

  @Method(0x8010a948L)
  public static void renderShopMenu() {
    final long v0;
    final long v1;

    inventoryJoypadInput_800bdc44.setu(getJoypadInputByPriority());

    switch(inventoryMenuState_800bdc28.get()) {
      case INIT_0 -> {
        loadCharacterStats(0);
        menuIndex_8011e0dc.set(0);
        menuIndex_8011e0e0.set(0);
        menuScroll_8011e0e4.set(0);
        inventoryMenuState_800bdc28.set(InventoryMenuState.AWAIT_INIT_1);
      }

      case AWAIT_INIT_1 -> {
        if(!drgn0_6666FilePtr_800bdc3c.isNull()) {
          scriptStartEffect(2, 10);
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }
      }

      case _2 -> {
        deallocateRenderables(0xffL);
        renderGlyphs(glyphs_80114510, 0, 0);
        selectedMenuOptionRenderablePtr_800bdbe0 = allocateUiElement(0x7a, 0x7a, 49, getShopMenuYOffset(menuIndex_8011e0dc.get()));
        FUN_80104b60(selectedMenuOptionRenderablePtr_800bdbe0);
        currentShopItemCount_8011e13c.set(0);

        //LAB_8010ab00
        for(int i = 0; i < 16; i++) {
          final int menuItemIndex = currentShopItemCount_8011e13c.get();
          final int itemId = shops_800f4930.get(shopId_8007a3b4.get()).item_00.get(menuItemIndex).id_01.get();

          if(itemId != 0xff) {
            final MenuItemStruct04 menuItem = menuItems_8011e0f8.get(menuItemIndex);
            menuItem.itemId_00.set(itemId);
            menuItem.price_02.set((int)(itemPrices_80114310.get(itemId).get() * 0x2L));
            currentShopItemCount_8011e13c.incr();
          } else {
            //LAB_8010ab6c
            final MenuItemStruct04 menuItem = menuItems_8011e0f8.get(i);
            menuItem.itemId_00.set(0xff);
            menuItem.price_02.set(0);
          }
        }

        final MenuItemStruct04 menuItem = menuItems_8011e0f8.get(16);
        menuItem.itemId_00.set(0xff);
        menuItem.price_02.set(0);
        recalcInventory();
        FUN_80103b10();

        //LAB_8010abc4
        for(int charSlot = 0; charSlot < characterCount_8011d7c4.get(); charSlot++) {
          characterRenderables_8011e148[charSlot] = FUN_80109820(FUN_8010a818(charSlot), 174, characterIndices_800bdbb8.get(charSlot).get());
        }

        //LAB_8010ac00
        shopType_8011e13d.setu(shops_800f4930.get(shopId_8007a3b4.get()).shopType_00.get() & 0x1L);
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
        inventoryMenuState_800bdc28.set(InventoryMenuState.INIT_MAIN_MENU_3);
      }

      case INIT_MAIN_MENU_3 -> {
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());

        if(_800bb168.get() == 0) {
          if((inventoryJoypadInput_800bdc44.get() & 0x40) != 0) {
            playSound(3);
            FUN_8010a844(InventoryMenuState._19, 0x1L);
          }

          //LAB_8010acac
          if(handleMenuUpDown(menuIndex_8011e0dc, 4)) {
            menuScroll_8011e0e4.set(0);
            menuIndex_8011e0e0.set(0);
            selectedMenuOptionRenderablePtr_800bdbe0.y_44 = getShopMenuYOffset(menuIndex_8011e0dc.get());
          }

          //LAB_8010ace4
          if((inventoryJoypadInput_800bdc44.get() & 0x20) != 0) {
            playSound(2);

            shopType_8011e13d.setu(shops_800f4930.get(shopId_8007a3b4.get()).shopType_00.get() & 0x1L);

            v1 = menuIndex_8011e0dc.get();
            if(v1 == 0) {
              //LAB_8010ad64
              selectedMenuOptionRenderablePtr_800bdbe4 = allocateUiElement(0x7b, 0x7b, 170, FUN_8010a808(menuIndex_8011e0e0.get()));
              FUN_80104b60(selectedMenuOptionRenderablePtr_800bdbe4);

              if(shopType_8011e13d.get() == 0) {
                menuIndex_8011e0d8.set(FUN_8010a864(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get()));
                highlightLeftHalf_800bdbe8 = allocateUiElement(0x83, 0x83, FUN_8010a818(menuIndex_8011e0d8.get()), 174);
                FUN_80104b60(highlightLeftHalf_800bdbe8);
              }

              //LAB_8010ae00
              renderable_8011e0f0 = allocateUiElement(0x3d, 0x44, 358, FUN_8010a808(0));
              renderable_8011e0f4 = allocateUiElement(0x35, 0x3c, 358, FUN_8010a808(5));
              inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
            } else if(v1 == 0x1L) {
              //LAB_8010ae58
              menuOption_8011e0e8.set(0);
              renderable_8011e0f0 = allocateUiElement(0x3d, 0x44, 358, FUN_8010a808(0));
              renderable_8011e0f4 = allocateUiElement(0x35, 0x3c, 358, FUN_8010a808(5));
              inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
              //LAB_8010ad48
            } else if(v1 == 0x2L) {
              //LAB_8010aeb8
              FUN_8010a844(InventoryMenuState._18, 0x1L);
            } else if(v1 == 0x3L) {
              FUN_8010a844(InventoryMenuState._19, 0x1L);
            }
          }
        }
      }

      case MAIN_MENU_4 -> {
        if(shopType_8011e13d.get() == 0) {
          if(handleMenuLeftRight(menuIndex_8011e0d8, 7)) {
            highlightLeftHalf_800bdbe8.x_40 = FUN_8010a818(menuIndex_8011e0d8.get());
          }

          //LAB_8010af18
          renderEquipmentStatChange(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get(), characterIndices_800bdbb8.get(menuIndex_8011e0d8.get()).get());
        } else {
          //LAB_8010af64
          renderNumberOfItems(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get());
        }

        //LAB_8010af94
        renderString(0, 16, 122, menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get(), false);

        if((inventoryJoypadInput_800bdc44.get() & 0x40) != 0) {
          playSound(3);
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }

        //LAB_8010aff8
        if((inventoryJoypadInput_800bdc44.get() & 0x20) != 0) {
          if(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get() == 0xff) {
            playSound(0x28);
          } else {
            //LAB_8010b044
            playSound(2);
            menuOption_8011e0ec.set(0);
            renderablePtr_800bdbf0 = allocateUiElement(0x7d, 0x7d, 132, FUN_8010a834(0));
            FUN_80104b60(renderablePtr_800bdbf0);

            if(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get() < 0xc0) {
              v0 = gameState_800babc8.equipmentCount_1e4.get() < 255 ? 1 : 0;
            } else {
              //LAB_8010b0bc
              v0 = gameState_800babc8.itemCount_1e6.get() < Config.inventorySize() ? 1 : 0;
            }

            //LAB_8010b0cc
            inventoryMenuState_800bdc28.set(v0 != 0 ? InventoryMenuState._5 : InventoryMenuState.CONFIG_6);

            if(gameState_800babc8.gold_94.get() < menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).price_02.get()) {
              inventoryMenuState_800bdc28.set(InventoryMenuState._7);
            }
          }
        }

        //LAB_8010b124
        //LAB_8010b128
        if(scrollMenu(menuIndex_8011e0e0, menuScroll_8011e0e4, 6, currentShopItemCount_8011e13c.get(), 1)) {
          selectedMenuOptionRenderablePtr_800bdbe4.y_44 = FUN_8010a808(menuIndex_8011e0e0.get());

          if(shopType_8011e13d.get() == 0) {
            menuIndex_8011e0d8.set(FUN_8010a864(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get()));
            highlightLeftHalf_800bdbe8.x_40 = FUN_8010a818(menuIndex_8011e0d8.get());
          }
        }

        //LAB_8010b1c4
        //LAB_8010b1c8
        FUN_8010c458(menuItems_8011e0f8, menuScroll_8011e0e4.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
      }

      case _5 -> {
        renderText(Are_you_sure_you_want_to_buy_8011c3ec, 16, 128, 4);
        renderCentredText(Yes_8011c20c, 148, FUN_8010a834(0) + 2, menuOption_8011e0ec.get() == 0 ? 5 : 4);
        renderCentredText(No_8011c214, 148, FUN_8010a834(1) + 2, menuOption_8011e0ec.get() == 0 ? 4 : 5);

        switch(handleYesNo(menuOption_8011e0ec)) {
          case SCROLLED ->
            //LAB_8010b2bc
            renderablePtr_800bdbf0.y_44 = FUN_8010a834(menuOption_8011e0ec.get());

          case YES -> {
            //LAB_8010b2d8
            gameState_800babc8.gold_94.sub(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).price_02.get());
            unloadRenderable(renderablePtr_800bdbf0);

            if(shopType_8011e13d.get() != 0) {
              giveItem(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get());

              //LAB_8010b378
              inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
            } else {
              //LAB_8010b360
              inventoryMenuState_800bdc28.set(InventoryMenuState._13);
            }

            //LAB_8010b37c
            //LAB_8010b2a8
          }

          case NO, CANCELLED -> {
            //LAB_8010b368
            unloadRenderable(renderablePtr_800bdbf0);
            inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
          }
        }

        //LAB_8010b380
        //LAB_8010b384
        if(shopType_8011e13d.get() == 0) {
          if(handleMenuLeftRight(menuIndex_8011e0d8, 7)) {
            highlightLeftHalf_800bdbe8.x_40 = FUN_8010a818(menuIndex_8011e0d8.get());
          }

          //LAB_8010b3cc
          renderEquipmentStatChange(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get(), characterIndices_800bdbb8.get(menuIndex_8011e0d8.get()).get());
        } else {
          //LAB_8010b418
          renderNumberOfItems(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get());
        }

        //LAB_8010b448
        FUN_8010c458(menuItems_8011e0f8, menuScroll_8011e0e4.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
      }

      case CONFIG_6, _7 -> {
        //LAB_8010b490
        //LAB_8010b498
        renderText(inventoryMenuState_800bdc28.get() == InventoryMenuState.CONFIG_6 ? Cannot_carry_anymore_8011c43c : Not_enough_money_8011c468, 16, 128, 4);
        renderCentredText(Conf_8011c48c, 148, FUN_8010a834(0) + 2, 5);
        renderablePtr_800bdbf0.y_44 = FUN_8010a834(0);

        if((inventoryJoypadInput_800bdc44.get() & 0x60) != 0) {
          playSound(2);
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }

        //LAB_8010b508
        FUN_8010c458(menuItems_8011e0f8, menuScroll_8011e0e4.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
      }

      case REPLACE_INIT_8 -> {
        highlightRightHalf_800bdbec = allocateUiElement(0x7d, 0x7d, 132, FUN_8010a834(menuOption_8011e0e8.get()));
        FUN_80104b60(highlightRightHalf_800bdbec);
        renderable_8011e0f0.flags_00 |= 0x40;
        renderable_8011e0f4.flags_00 |= 0x40;
        FUN_8010a864(0xff);
        inventoryMenuState_800bdc28.set(InventoryMenuState._9);
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
      }

      case _9 -> {
        renderText(What_do_you_want_to_sell_8011c498, 16, 128, 4);
        renderCentredText(Armed_8011c4cc, 148, FUN_8010a834(0) + 2, menuOption_8011e0e8.get() == 0 ? 5 : 4);
        renderCentredText(item_8011c4d8, 148, FUN_8010a834(1) + 2, menuOption_8011e0e8.get() == 0 ? 4 : 5);

        switch(handleYesNo(menuOption_8011e0e8)) {
          case SCROLLED ->
            //LAB_8010b69c
            highlightRightHalf_800bdbec.y_44 = FUN_8010a834(menuOption_8011e0e8.get());

          case YES -> {
            //LAB_8010b6b8
            menuIndex_8011e0e0.set(0);
            menuScroll_8011e0e4.set(0);
            _8011e13e.setu(0);

            if(gameState_800babc8.equipmentCount_1e4.get() != 0) {
              inventoryMenuState_800bdc28.set(InventoryMenuState._10);
              selectedMenuOptionRenderablePtr_800bdbe4 = allocateUiElement(0x7b, 0x7b, 170, FUN_8010a808(0));
              FUN_80104b60(selectedMenuOptionRenderablePtr_800bdbe4);
              unloadRenderable(highlightRightHalf_800bdbec);
              FUN_8010a864(gameState_800babc8.equipment_1e8.get(0).get());
            } else {
              //LAB_8010b7b4
              menuOption_8011e0e8.set(0);
              inventoryMenuState_800bdc28.set(InventoryMenuState.EQUIPMENT_INIT_12);
            }
          }

          //LAB_8010b680
          case NO -> {
            //LAB_8010b73c
            _8011e13e.setu(0x1L);
            menuScroll_8011e0e4.set(0);
            menuIndex_8011e0e0.set(0);

            if(gameState_800babc8.itemCount_1e6.get() != 0) {
              inventoryMenuState_800bdc28.set(InventoryMenuState._10);
              selectedMenuOptionRenderablePtr_800bdbe4 = allocateUiElement(0x7b, 0x7b, 170, FUN_8010a808(0));
              FUN_80104b60(selectedMenuOptionRenderablePtr_800bdbe4);
              unloadRenderable(highlightRightHalf_800bdbec);
            } else {
              //LAB_8010b7b4
              menuOption_8011e0e8.set(0);
              inventoryMenuState_800bdc28.set(InventoryMenuState.EQUIPMENT_INIT_12);
            }
          }

          case CANCELLED ->
            //LAB_8010b7c8
            inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }

        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
      }

      case _10 -> {
        if(_8011e13e.get() == 0) {
          //LAB_8010b868
          renderText(Which_weapon_do_you_want_to_sell_8011c524, 16, 128, 4);
          renderString(0, 193, 122, gameState_800babc8.equipment_1e8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).get(), false);

          if(scrollMenu(menuIndex_8011e0e0, menuScroll_8011e0e4, 6, gameState_800babc8.equipmentCount_1e4.get(), 1)) {
            FUN_8010a864(gameState_800babc8.equipment_1e8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).get());
            selectedMenuOptionRenderablePtr_800bdbe4.y_44 = FUN_8010a808(menuIndex_8011e0e0.get());
          }
        } else {
          renderText(Which_item_do_you_want_to_sell_8011c4e4, 16, 128, 4);
          renderString(0, 193, 122, gameState_800babc8.items_2e9.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).get(), false);

          if(scrollMenu(menuIndex_8011e0e0, menuScroll_8011e0e4, 6, gameState_800babc8.itemCount_1e6.get(), 1)) {
            selectedMenuOptionRenderablePtr_800bdbe4.y_44 = FUN_8010a808(menuIndex_8011e0e0.get());
          }
        }

        //LAB_8010b918
        if((inventoryJoypadInput_800bdc44.get() & 0x40) != 0) {
          playSound(3);
          unloadRenderable(selectedMenuOptionRenderablePtr_800bdbe4);
          inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
        }

        //LAB_8010b954
        renderItemList(menuScroll_8011e0e4.get(), _8011e13e.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), _8011e13e.get());

        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0) {
          //LAB_8010b9e8
          v0 = menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get();
          //TODO not sure if this condition is right
          if(_8011e13e.get() != 0 && gameState_800babc8.items_2e9.get((int)v0).get() == 0xffL || _8011e13e.get() == 0 && (gameState_800babc8.equipment_1e8.get((int)v0).get() == 0xffL || FUN_80022898(gameState_800babc8.equipment_1e8.get((int)v0).get()) != 0)) {
            //LAB_8010ba28
            playSound(0x28);
          } else {
            //LAB_8010ba38
            playSound(2);
            menuOption_8011e0ec.set(0);
            renderablePtr_800bdbf0 = allocateUiElement(0x7d, 0x7d, 132, FUN_8010a834(0));
            FUN_80104b60(renderablePtr_800bdbf0);
            inventoryMenuState_800bdc28.set(InventoryMenuState._11);
          }
        }
      }

      case _11 -> {
        renderText(Are_you_sure_you_want_to_sell_8011c568, 16, 128, 4);
        renderCentredText(Yes_8011c20c, 148, FUN_8010a834(0) + 2, menuOption_8011e0ec.get() == 0 ? 5 : 4);
        renderCentredText(No_8011c214, 148, FUN_8010a834(1) + 2, menuOption_8011e0ec.get() == 0 ? 4 : 5);

        switch(handleYesNo(menuOption_8011e0ec)) {
          case SCROLLED ->
            //LAB_8010bb50
            renderablePtr_800bdbf0.y_44 = FUN_8010a834(menuOption_8011e0ec.get());

          case YES -> {
            //LAB_8010bb6c
            final int itemId;
            if(_8011e13e.get() != 0) {
              itemId = gameState_800babc8.items_2e9.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).get();
              v0 = takeItem(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get());
            } else {
              //LAB_8010bbc0
              itemId = gameState_800babc8.equipment_1e8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).get();
              v0 = takeEquipment(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get());
            }

            //LAB_8010bbfc
            if(v0 == 0) {
              addGold(itemPrices_80114310.get(itemId).get());
            }

            //LAB_8010bc1c
            unloadRenderable(selectedMenuOptionRenderablePtr_800bdbe4);
            unloadRenderable(renderablePtr_800bdbf0);
            inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
            //LAB_8010bb3c
          }

          case NO, CANCELLED -> {
            //LAB_8010bc48
            unloadRenderable(renderablePtr_800bdbf0);
            inventoryMenuState_800bdc28.set(InventoryMenuState._10);
          }
        }

        //LAB_8010bcf8
        //LAB_8010bcfc
        //LAB_8010bd00
        renderItemList(menuScroll_8011e0e4.get(), _8011e13e.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), _8011e13e.get());
      }

      case EQUIPMENT_INIT_12 -> {
        renderCentredText(Conf_8011c48c, 148, FUN_8010a834(0) + 2, 5);
        highlightRightHalf_800bdbec.y_44 = FUN_8010a834(0);

        //LAB_8010bcb4
        //LAB_8010bcbc
        renderText(_8011e13e.get() != 0 ? No_item_to_sell_8011c5dc : No_weapon_to_sell_8011c5fc, 16, 128, 4);

        if((inventoryJoypadInput_800bdc44.get() & 0x60) != 0) {
          playSound(2);

          //LAB_8010bcf4
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }

        //LAB_8010bcf8
        //LAB_8010bcfc
        //LAB_8010bd00
        renderItemList(menuScroll_8011e0e4.get(), _8011e13e.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), _8011e13e.get());
      }

      case _13, _14 -> {
        if(inventoryMenuState_800bdc28.get() == InventoryMenuState._13) {
          menuOption_8011e0ec.set(0);
          renderablePtr_800bdbf0 = allocateUiElement(0x7d, 0x7d, 132, FUN_8010a834(0));
          FUN_80104b60(renderablePtr_800bdbf0);
          inventoryMenuState_800bdc28.set(InventoryMenuState._14);
        }

        renderEquipmentStatChange(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get(), characterIndices_800bdbb8.get(menuIndex_8011e0d8.get()).get());

        if(handleMenuLeftRight(menuIndex_8011e0d8, 7)) {
          highlightLeftHalf_800bdbe8.x_40 = FUN_8010a818(menuIndex_8011e0d8.get());
        }

        //LAB_8010be00
        renderText(Do_you_want_to_be_armed_with_it_8011c620, 16, 128, 4);
        renderCentredText(Yes_8011c20c, 148, FUN_8010a834(0) + 2, menuOption_8011e0ec.get() == 0 ? 5 : 4);
        renderCentredText(No_8011c214, 148, FUN_8010a834(1) + 2, menuOption_8011e0ec.get() == 0 ? 4 : 5);

        switch(handleYesNo(menuOption_8011e0ec)) {
          case SCROLLED ->
            //LAB_8010becc
            renderablePtr_800bdbf0.y_44 = FUN_8010a834(menuOption_8011e0ec.get());

          case YES ->
            //LAB_8010bee8
            inventoryMenuState_800bdc28.set(InventoryMenuState.EQUIPMENT_MENU_15);
            //LAB_8010beb8

          case NO, CANCELLED -> {
            //LAB_8010bef4
            unloadRenderable(renderablePtr_800bdbf0);
            giveItem(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get());

            //LAB_8010bf38
            inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
          }
        }

        //LAB_8010bf3c
        //LAB_8010bf40
        FUN_8010c458(menuItems_8011e0f8, menuScroll_8011e0e4.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
      }

      case EQUIPMENT_MENU_15 -> {
        renderEquipmentStatChange(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get(), characterIndices_800bdbb8.get(menuIndex_8011e0d8.get()).get());

        if(handleMenuLeftRight(menuIndex_8011e0d8, 7)) {
          highlightLeftHalf_800bdbe8.x_40 = FUN_8010a818(menuIndex_8011e0d8.get());
        }

        //LAB_8010bfe4
        renderCentredText(Conf_8011c48c, 148, FUN_8010a834(0) + 2, 5);
        renderablePtr_800bdbf0.y_44 = FUN_8010a834(0);

        if(!canEquip(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get(), characterIndices_800bdbb8.get(menuIndex_8011e0d8.get()).get())) {
          //LAB_8010c0fc
          renderText(Put_in_the_bag_8011c684, 16, 128, 4);

          if((inventoryJoypadInput_800bdc44.get() & 0x60) != 0) {
            playSound(2);

            //LAB_8010c150
            giveItem(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get());
            unloadRenderable(renderablePtr_800bdbf0);
            inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
          }
        } else {
          renderText(characterNames_801142dc.get(characterIndices_800bdbb8.get(menuIndex_8011e0d8.get()).get()).deref(), 24, 128, 4);
          renderText(Is_armed_8011c670, 16, 142, 4);

          if((inventoryJoypadInput_800bdc44.get() & 0x60) != 0) {
            playSound(2);
            giveItem(equipItem(menuItems_8011e0f8.get(menuScroll_8011e0e4.get() + menuIndex_8011e0e0.get()).itemId_00.get(), characterIndices_800bdbb8.get(menuIndex_8011e0d8.get()).get()));
            unloadRenderable(renderablePtr_800bdbf0);
            inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
          }
        }

        //LAB_8010c174
        FUN_8010c458(menuItems_8011e0f8, menuScroll_8011e0e4.get(), renderable_8011e0f0, renderable_8011e0f4);
        renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
      }

      case _16, _17 -> {
        if(inventoryMenuState_800bdc28.get() == InventoryMenuState._16) {
          scriptStartEffect(1, 10);
          inventoryMenuState_800bdc28.set(InventoryMenuState._17);
        }

        if(_800bb168.get() >= 0xff) {
          inventoryMenuState_800bdc28.set(confirmDest_800bdc30.get());
        }

        //LAB_8010c1e0
        if(_800bdc2c.get() == 0x1L) {
          //LAB_8010c1f0
          //LAB_8010c1f4
          renderShopMenu(menuIndex_8011e0dc.get(), shopType_8011e13d.get());
        }
      }

      case _18 -> {
        inventoryMenuState_800bdc28.set(InventoryMenuState._16);
        whichMenu_800bdc38 = WhichMenu.RENDER_SHOP_CARRIED_ITEMS_36;
      }

      case _19 -> {
        scriptStartEffect(2, 10);
        deallocateRenderables(0xff);
        free(gameOverMcq_800bdc3c.getPointer());
        if(mainCallbackIndex_8004dd20.get() == 0x5L && loadingGameStateOverlay_8004dd08.get() == 0) {
          FUN_800e3fac();
        }

        //LAB_8010c290
        //LAB_8010c294
        whichMenu_800bdc38 = WhichMenu.UNLOAD_SHOP_MENU_10;
        textZ_800bdf00.set(13);
      }
    }

    //LAB_8010c2a4
  }

  @Method(0x8010c2c8L)
  public static void renderShopMenu(final int selectedMenuItem, final long a2) {
    renderCentredText(Buy_8011c6a4, 72, getShopMenuYOffset(0) + 2, selectedMenuItem != 0 ? 4 : 5);
    renderCentredText(Sell_8011c6ac, 72, getShopMenuYOffset(1) + 2, selectedMenuItem != 1 ? 4 : 5);
    renderCentredText(Carried_8011c6b8, 72, getShopMenuYOffset(2) + 2, selectedMenuItem != 2 ? 4 : 5);
    renderCentredText(Leave_8011c6c8, 72, getShopMenuYOffset(3) + 2, selectedMenuItem != 3 ? 4 : 5);

    if((a2 & 0xffL) != 0) {
      renderTwoDigitNumber(105, 36, gameState_800babc8.itemCount_1e6.get(), 0x2L);
      FUN_801038d4(94, 16, 16);
      renderTwoDigitNumber(123, 36, Config.inventorySize(), 0x2L);
    } else {
      //LAB_8010c3e8
      renderThreeDigitNumber(93, 36, gameState_800babc8.equipmentCount_1e4.get(), 0x2L);
      FUN_801038d4(95, 16, 16);
      renderThreeDigitNumber(117, 36, 255, 0x2L);
    }

    //LAB_8010c428
    renderEightDigitNumber(87, 24, gameState_800babc8.gold_94.get(), 0x2L);
    uploadRenderables();
  }

  @Method(0x8010c458L)
  public static void FUN_8010c458(final UnboundedArrayRef<MenuItemStruct04> items, final int startItemIndex, final Renderable58 a2, final Renderable58 a3) {
    //LAB_8010c4b4
    int i;
    for(i = 0; items.get(startItemIndex + i).itemId_00.get() != 0xff; i++) {
      if(i >= 6) {
        break;
      }

      final MenuItemStruct04 item = items.get(startItemIndex + i);
      renderText(equipment_8011972c.get(item.itemId_00.get()).deref(), 168, FUN_8010a808(i) + 2, 4);
      renderFiveDigitNumber(324, FUN_8010a808(i) + 4, item.price_02.get());
      renderItemIcon(getItemIcon(item.itemId_00.get()), 151, FUN_8010a808(i), 0x8L);
    }

    //LAB_8010c558
    //LAB_8010c578
    if(startItemIndex != 0) {
      a2.flags_00 &= 0xffff_ffbf;
    } else {
      //LAB_8010c56c
      a2.flags_00 |= 0x40;
    }

    //LAB_8010c5b0
    if(items.get(i + startItemIndex).itemId_00.get() != 0xff) {
      a3.flags_00 &= 0xffff_ffbf;
    } else {
      //LAB_8010c5a4
      a3.flags_00 |= 0x40;
    }
  }

  @Method(0x8010c5e0L)
  public static void renderItemList(final int firstItem, final long a1, final Renderable58 upArrow, final Renderable58 downArrow) {
    if((a1 & 0xff) != 0) {
      //LAB_8010c654
      int i;
      for(i = 0; gameState_800babc8.items_2e9.get(firstItem + i).get() != 0xff && i < 6; i++) {
        final int itemId = gameState_800babc8.items_2e9.get(firstItem + i).get();
        renderItemIcon(getItemIcon(itemId), 151, FUN_8010a808(i), 0x8L);

        //LAB_8010c6b0
        renderText(equipment_8011972c.get(itemId).deref(), 168, FUN_8010a808(i) + 2, FUN_80022898(itemId) == 0 ? 4 : 6);
        FUN_801069d0(324, FUN_8010a808(i) + 4, itemPrices_80114310.get(itemId).get());
      }

      //LAB_8010c708
      if(gameState_800babc8.items_2e9.get(firstItem + i).get() == 0xff) {
        downArrow.flags_00 |= 0x40;
      } else {
        downArrow.flags_00 &= 0xffff_ffbf;
      }
    } else {
      //LAB_8010c734
      //LAB_8010c764
      int i;
      for(i = 0; gameState_800babc8.equipment_1e8.get(firstItem + i).get() != 0xff && i < 6; i++) {
        final int itemId = gameState_800babc8.equipment_1e8.get(firstItem + i).get();
        renderItemIcon(getItemIcon(itemId), 151, FUN_8010a808(i), 0x8L);

        //LAB_8010c7c0
        renderText(equipment_8011972c.get(itemId).deref(), 168, FUN_8010a808(i) + 2, FUN_80022898(itemId) == 0 ? 4 : 6);

        if(FUN_80022898(itemId) != 0) {
          renderItemIcon(58, 330, FUN_8010a808(i), 0x8L).clut_30 = 0x7eaa;
        } else {
          //LAB_8010c814
          renderFiveDigitNumber(322, FUN_8010a808(i) + 4, itemPrices_80114310.get(itemId).get());
        }
      }

      //LAB_8010c854
      if(gameState_800babc8.equipment_1e8.get(firstItem + i).get() == 0xff) {
        //LAB_8010c880
        downArrow.flags_00 |= 0x40;
      } else {
        downArrow.flags_00 &= 0xffff_ffbf;
      }
    }

    //LAB_8010c88c
    if(firstItem == 0) {
      //LAB_8010c8a4
      upArrow.flags_00 |= 0x40;
    } else {
      upArrow.flags_00 &= 0xffff_ffbf;
    }
  }

  @Method(0x8010c8e4L)
  public static void renderEquipmentStatChange(final int equipmentId, final int charIndex) {
    if(charIndex != -1) {
      final Memory.TemporaryReservation tmp = MEMORY.temp(0xa0);
      final ActiveStatsa0 oldStats = new ActiveStatsa0(tmp.get());

      //LAB_8010c920
      memcpy(oldStats.getAddress(), stats_800be5f8.get(charIndex).getAddress(), 0xa0);

      //LAB_8010c974
      final int[] oldEquipment = new int[5];
      for(int equipmentSlot = 0; equipmentSlot < 5; equipmentSlot++) {
        oldEquipment[equipmentSlot] = gameState_800babc8.charData_32c.get(charIndex).equipment_14.get(equipmentSlot).get();
      }

      if(equipItem(equipmentId, charIndex) != 0xff) {
        FUN_801038d4(0x67, 210, 127);
        FUN_801038d4(0x68, 210, 137);
        FUN_801038d4(0x69, 210, 147);
        FUN_801038d4(0x6a, 210, 157);
        final ActiveStatsa0 newStats = stats_800be5f8.get(charIndex);
        renderThreeDigitNumber(246, 127, newStats.gearAttack_88.get(), 0x2L);
        renderThreeDigitNumber(246, 137, newStats.gearDefence_8c.get(), 0x2L);
        renderThreeDigitNumber(246, 147, newStats.gearMagicAttack_8a.get(), 0x2L);
        renderThreeDigitNumber(246, 157, newStats.gearMagicDefence_8e.get(), 0x2L);
        FUN_801038d4(0x6b, 274, 127);
        FUN_801038d4(0x6b, 274, 137);
        FUN_801038d4(0x6b, 274, 147);
        FUN_801038d4(0x6b, 274, 157);
        loadCharacterStats(0);
        renderThreeDigitNumberComparison(284, 127, oldStats.gearAttack_88.get(), newStats.gearAttack_88.get());
        renderThreeDigitNumberComparison(284, 137, oldStats.gearDefence_8c.get(), newStats.gearDefence_8c.get());
        renderThreeDigitNumberComparison(284, 147, oldStats.gearMagicAttack_8a.get(), newStats.gearMagicAttack_8a.get());
        renderThreeDigitNumberComparison(284, 157, oldStats.gearMagicDefence_8e.get(), newStats.gearMagicDefence_8e.get());
      } else {
        //LAB_8010cafc
        renderText(Cannot_be_armed_with_8011c6d4, 228, 137, 4);
      }

      //LAB_8010cb18
      //LAB_8010cb3c
      for(int equipmentSlot = 0; equipmentSlot < 5; equipmentSlot++) {
        gameState_800babc8.charData_32c.get(charIndex).equipment_14.get(equipmentSlot).set(oldEquipment[equipmentSlot]);
      }

      loadCharacterStats(0);

      tmp.release();
    }

    //LAB_8010cb6c
  }

  @Method(0x8010cb80L)
  public static void renderNumberOfItems(final int itemId) {
    if(itemId != 0xff) {
      //LAB_8010cbb8
      int count = 0;
      for(int i = 0; i < gameState_800babc8.itemCount_1e6.get(); i++) {
        if(gameState_800babc8.items_2e9.get(i).get() == itemId) {
          count++;
        }

        //LAB_8010cbcc
      }

      //LAB_8010cbdc
      final LodString num = new LodString(11);
      intToStr(count, num);
      renderText(Number_kept_8011c7f4, 228, 137, 4);
      renderText(num, 274, 137, 4);
    }

    //LAB_8010cc14
  }

  /**
   * @return True if there is remaining XP to give
   */
  @Method(0x8010cc24L)
  public static boolean givePendingXp(final int charIndex, final int charSlot) {
    if(charIndex == -1) {
      return false;
    }

    final int pendingXp = pendingXp_8011e180.get(charIndex).get();

    if(pendingXp == 0) {
      //LAB_8010cc68
      return false;
    }

    //LAB_8010cc70
    final int cappedPendingXp;
    if((joypadPress_8007a398.get() & 0x20L) != 0 || pendingXp < 10) {
      cappedPendingXp = pendingXp;
    } else {
      cappedPendingXp = 10;
    }

    //LAB_8010cc94
    //LAB_8010cc98
    int xp = gameState_800babc8.charData_32c.get(charIndex).xp_00.get();
    if(xp <= 999999) {
      xp = xp + cappedPendingXp;
    } else {
      xp = 999999;
    }

    //LAB_8010ccd4
    gameState_800babc8.charData_32c.get(charIndex).xp_00.set(xp);
    pendingXp_8011e180.get(charIndex).sub(cappedPendingXp);

    //LAB_8010cd30
    while(gameState_800babc8.charData_32c.get(charIndex).xp_00.get() >= getXpToNextLevel(charIndex) && gameState_800babc8.charData_32c.get(charIndex).level_12.get() < 60) {
      gameState_800babc8.charData_32c.get(charIndex).level_12.incr();

      _8011e1c8.offset(charSlot).addu(0x1L);
      if(additionsUnlocked_8011e1b8.get(charSlot).get() == 0) {
        additionsUnlocked_8011e1b8.get(charSlot).set(loadAdditions(charIndex, additions_8011e098));
      }

      //LAB_8010cd9c
    }

    //LAB_8010cdb0
    //LAB_8010cdcc
    return pendingXp_8011e180.get(charIndex).get() > 0;
  }

  @Method(0x8010cde8L)
  public static void levelUpDragoon(final int charIndex, final int charSlot) {
    if(charIndex != -1) {
      gameState_800babc8.charData_32c.get(charIndex).dlevelXp_0e.add(spGained_800bc950.get(charSlot).get());

      if(gameState_800babc8.charData_32c.get(charIndex).dlevelXp_0e.get() > 32000) {
        gameState_800babc8.charData_32c.get(charIndex).dlevelXp_0e.set(32000);
      }

      //LAB_8010ceb0
      //LAB_8010cecc
      while(gameState_800babc8.charData_32c.get(charIndex).dlevelXp_0e.get() >= _800fbbf0.offset(charIndex * 0x4L).deref(2).offset(gameState_800babc8.charData_32c.get(charIndex).dlevel_13.get() * 0x2L).offset(0x2L).get() && gameState_800babc8.charData_32c.get(charIndex).dlevel_13.get() < 5) {
        loadCharacterStats(0);
        final byte[] spellIndices = new byte[8];
        final int spellCount = getUnlockedDragoonSpells(spellIndices, charIndex);

        gameState_800babc8.charData_32c.get(charIndex).dlevel_13.incr();
        _8011e1d8.offset(charSlot).addu(0x1L);

        loadCharacterStats(0);
        if(spellCount != getUnlockedDragoonSpells(spellIndices, charIndex)) {
          spellsUnlocked_8011e1a8.get(charSlot).set(spellIndices[spellCount] + 1);
        }

        //LAB_8010cf70
      }
    }

    //LAB_8010cf84
  }

  @Method(0x8010cfa0L)
  public static Renderable58 FUN_8010cfa0(final int startGlyph, final int endGlyph, final int x, final int y, final int u, final int v) {
    final Renderable58 renderable = allocateRenderable(drgn0_6666FilePtr_800bdc3c.deref()._d2d8, null);
    renderable.glyph_04 = startGlyph;
    renderable.startGlyph_10 = startGlyph;

    if(startGlyph != endGlyph) {
      renderable.endGlyph_14 = endGlyph;
    } else {
      renderable.endGlyph_14 = endGlyph;
      renderable.flags_00 |= 0x4;
    }

    //LAB_8010d004
    renderable.x_40 = x;
    renderable.y_44 = y;
    renderable.clut_30 = v << 6 | (u & 0x3f0) >> 4;
    renderable.tpage_2c = 0x1b;
    return renderable;
  }

  @Method(0x8010d050L)
  public static void FUN_8010d050(final InventoryMenuState nextMenuState, final long a1) {
    inventoryMenuState_800bdc28.set(InventoryMenuState._16);
    _800bdc2c.setu(a1);
    confirmDest_800bdc30.set(nextMenuState);
  }

  @Method(0x8010d078L)
  public static void FUN_8010d078(int x, int y, final int w, final int h, final int type) {
    x -= 8 + displayWidth_1f8003e0.get() / 2;
    y -= 120;

    final GpuCommandPoly cmd = new GpuCommandPoly(4)
      .pos(0, x, y)
      .pos(1, x + w, y)
      .pos(2, x, y + h)
      .pos(3, x + w, y + h);

    final int z;
    switch(type) {
      case 0 -> {
        z = 36;

        cmd
          .rgb(0, 0, 0, 1)
          .rgb(1, 0, 0, 1)
          .rgb(2, 0, 0, 1)
          .rgb(3, 0, 0, 1);
      }

      case 1 -> {
        z = 36;

        cmd
          .translucent(Translucency.HALF_B_PLUS_HALF_F)
          .rgb(0, 0x80, 0x80, 0x80)
          .rgb(1,    0, 0x14, 0x50)
          .rgb(2,    0, 0x14, 0x50)
          .rgb(3,    0,    0,    0);
      }

      case 2 -> {
        z = 36;

        cmd
          .monochrome(0, 0x7f)
          .monochrome(1, 0x7f)
          .monochrome(2, 0)
          .monochrome(3, 0);
      }

      case 3 -> {
        z = 34;

        cmd
          .rgb(0, 0xff, 0x7a, 0)
          .rgb(1, 0xff, 0x7a, 0)
          .rgb(2, 0x49, 0x23, 0)
          .rgb(3, 0x49, 0x23, 0);
      }

      case 4 -> {
        z = 35;

        cmd
          .rgb(0, 0xff, 0x7a, 0)
          .rgb(1, 0xff, 0x7a, 0)
          .rgb(2, 0xff, 0x7a, 0)
          .rgb(3, 0xff, 0x7a, 0);
      }

      case 5 -> {
        z = 34;

        cmd
          .rgb(0, 0, 0x84, 0xfe)
          .rgb(1, 0, 0x84, 0xfe)
          .rgb(2, 0, 0x26, 0x48)
          .rgb(3, 0, 0x26, 0x48);
      }

      case 6 -> {
        z = 35;

        cmd
          .monochrome(0, 0x7f)
          .monochrome(1, 0x7f)
          .monochrome(2, 0)
          .monochrome(3, 0);
      }

      default -> z = 0;
    }

    //LAB_8010d2c4
    GPU.queueCommand(z, cmd);

    //LAB_8010d318
  }

  @Method(0x8010d32cL)
  public static boolean characterIsAlive(final int charSlot) {
    final int charIndex = gameState_800babc8.charIndex_88.get(charSlot).get();

    if(charIndex != -1) {
      //LAB_8010d36c
      for(int i = 0; i < _800bc97c.get(); i++) {
        if(_800bc968.offset(i * 0x4L).get() == charIndex) {
          return true;
        }

        //LAB_8010d384
      }
    }

    //LAB_8010d390
    return false;
  }

  @Method(0x8010d398L)
  public static void renderAdditionUnlocked(final int x, final int y, final int additionIndex, final int height) {
    FUN_8010d078(x, y + 20 - height, 134, (height + 1) * 2, 4);
    FUN_8010d078(x + 1, y + 20 - height + 1, 132, height * 2, 3);

    if(height >= 20) {
      Scus94491BpeSegment_8002.renderText(additions_8011a064.get(additionIndex).deref(), x - 4, y + 6, 0, 0);
      Scus94491BpeSegment_8002.renderText(New_Addition_8011c5a8, x - 4, y + 20, 0, 0);
    }

    //LAB_8010d470
  }

  @Method(0x8010d498L)
  public static void renderSpellUnlocked(final int x, final int y, final int spellIndex, final int height) {
    FUN_8010d078(x, y + 20 - height, 134, (height + 1) * 2, 6); // New spell border
    FUN_8010d078(x + 1, y + 20 - height + 1, 132, height * 2, 5); // New spell background

    if(height >= 20) {
      Scus94491BpeSegment_8002.renderText(spells_80052734.get(spellIndex).deref(), x - 4, y + 6, 0, 0);
      Scus94491BpeSegment_8002.renderText(Spell_Unlocked_8011c5c4, x - 4, y + 20, 0, 0);
    }

    //LAB_8010d470
  }

  @Method(0x8010d598L)
  public static int FUN_8010d598(final int charSlot) {
    final int charIndex = gameState_800babc8.charIndex_88.get(charSlot).get();

    if(charIndex == -1) {
      return 0;
    }

    if(_800bc910.offset(charSlot * 0x4L).get() == 0) {
      //LAB_8010d5d0
      return 0;
    }

    //LAB_8010d5d8
    final int a0 = additionOffsets_8004f5ac.get(charIndex).get() + additionCounts_8004f5c0.get(charIndex).get();
    if(a0 == -1) {
      return 0;
    }

    //LAB_8010d60c
    return a0;
  }

  @Method(0x8010d614L)
  public static void renderPostCombatReport() {
    inventoryJoypadInput_800bdc44.setu(getJoypadInputByPriority());

    switch(inventoryMenuState_800bdc28.get()) {
      case INIT_0:
        renderablePtr_800bdc5c = null;
        drgn0_6666FilePtr_800bdc3c.clear();
        setWidthAndFlags(320);
        loadDrgnBinFile(0, 6665, 0, SItem::menuAssetsLoaded, 0, 0x5L);
        loadDrgnBinFile(0, 6666, 0, SItem::menuAssetsLoaded, 1, 0x3L);
        textZ_800bdf00.set(33);
        inventoryMenuState_800bdc28.set(InventoryMenuState.AWAIT_INIT_1);
        break;

      case AWAIT_INIT_1:
        if(!drgn0_6666FilePtr_800bdc3c.isNull()) {
          scriptStartEffect(0x2L, 0xaL);
          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }
        break;

      case _2:
        if(_800bb168.get() == 0) {
          deallocateRenderables(0xffL);
          Renderable58 glyph = FUN_8010cfa0(0, 0, 165, 21, 720, 497);
          glyph._34 = 0;
          glyph._38 = 0;
          glyph = FUN_8010cfa0(2, 2, 13, 21, 720, 497);
          glyph._34 = 0;
          glyph._38 = 0;
          glyph = FUN_8010cfa0(1, 1, 13, 149, 720, 497);
          glyph._34 = 0;
          glyph._38 = 0;

          FUN_8010cfa0(0x3e, 0x3e, 24, 28, 736, 497);
          FUN_8010cfa0(0x3d, 0x3d, 24, 40, 736, 497);
          FUN_8010cfa0(0x40, 0x40, 24, 52, 736, 497);

          //LAB_8010d81c
          for(int i = 0; i < 6; i++) {
            if(i >= itemsDroppedByEnemiesCount_800bc978.get()) {
              itemsDroppedByEnemies_800bc928.get(i).set(0xff);
            }

            //LAB_8010d830
          }

          FUN_80103b10();
          recalcInventory();

          //LAB_8010d87c
          for(int i = 0; i < 10; i++) {
            spellsUnlocked_8011e1a8.get(i).set(0);
            additionsUnlocked_8011e1b8.get(i).set(0);
            _8011e1c8.offset(i).setu(0);
            _8011e1d8.offset(i).setu(0);
            pendingXp_8011e180.get(i).set(0);
          }

          additionsUnlocked_8011e1b8.get(0).set(FUN_8010d598(0));
          additionsUnlocked_8011e1b8.get(1).set(FUN_8010d598(1));
          additionsUnlocked_8011e1b8.get(2).set(FUN_8010d598(2));

          xpDivisor_8011e174.set(0);
          for(int charSlot = 0; charSlot < 3; charSlot++) {
            if(characterIsAlive(charSlot)) {
              xpDivisor_8011e174.incr();
            }
          }

          for(int charSlot = 0; charSlot < 3; charSlot++) {
            if(characterIsAlive(charSlot)) {
              pendingXp_8011e180.get(gameState_800babc8.charIndex_88.get(charSlot).get()).set(totalXpFromCombat_800bc95c.get() / xpDivisor_8011e174.get());
            }
          }

          //LAB_8010d9d4
          //LAB_8010d9f8
          for(int secondaryCharSlot = 0; secondaryCharSlot < 6; secondaryCharSlot++) {
            final int secondaryCharIndex = secondaryCharIndices_800bdbf8.get(secondaryCharSlot).get();

            if(secondaryCharIndex != -1) {
              pendingXp_8011e180.get(secondaryCharIndex).set(totalXpFromCombat_800bc95c.get() / xpDivisor_8011e174.get() / 2);
            }

            //LAB_8010da24
          }

          inventoryMenuState_800bdc28.set(InventoryMenuState.INIT_MAIN_MENU_3);
          FUN_8010e9a8(0x1L, xpDivisor_8011e174.get());
        }

        break;

      case INIT_MAIN_MENU_3:
        if((joypadPress_8007a398.get() & 0x20L) != 0) {
          //LAB_8010da84
          if(goldGainedFromCombat_800bc920.get() == 0) {
            inventoryMenuState_800bdc28.set(InventoryMenuState._5);
          } else {
            inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
          }
        }

        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case MAIN_MENU_4:
        final int goldTick;
        if((joypadPress_8007a398.get() & 0x20L) != 0) {
          goldTick = goldGainedFromCombat_800bc920.get();
        } else {
          //LAB_8010dab4
          goldTick = 10;
        }

        //LAB_8010dabc
        final int goldGained = goldGainedFromCombat_800bc920.get();

        if(goldTick >= goldGained) {
          soundTick_8011e17c.setu(0);
          goldGainedFromCombat_800bc920.set(0);
          inventoryMenuState_800bdc28.set(InventoryMenuState._5);
          gameState_800babc8.gold_94.add(goldGained);
        } else {
          //LAB_8010db00
          goldGainedFromCombat_800bc920.sub(goldTick);
          gameState_800babc8.gold_94.add(goldTick);
        }

        //LAB_8010db18
        if(gameState_800babc8.gold_94.get() > 99999999) {
          gameState_800babc8.gold_94.set(99999999);
        }

        //LAB_8010db3c
        //LAB_8010db40
        soundTick_8011e17c.addu(0x1L);

        if((soundTick_8011e17c.get() & 0x1L) != 0) {
          playSound(0x1L);
        }

        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _5:
        final boolean moreXpToGive =
          givePendingXp(gameState_800babc8.charIndex_88.get(0).get(), 0) ||
          givePendingXp(gameState_800babc8.charIndex_88.get(1).get(), 1) ||
          givePendingXp(gameState_800babc8.charIndex_88.get(2).get(), 2) ||
          givePendingXp(secondaryCharIndices_800bdbf8.get(0).get(), 3) ||
          givePendingXp(secondaryCharIndices_800bdbf8.get(1).get(), 4) ||
          givePendingXp(secondaryCharIndices_800bdbf8.get(2).get(), 5) ||
          givePendingXp(secondaryCharIndices_800bdbf8.get(3).get(), 6) ||
          givePendingXp(secondaryCharIndices_800bdbf8.get(4).get(), 7) ||
          givePendingXp(secondaryCharIndices_800bdbf8.get(5).get(), 8);

        if(moreXpToGive) {
          soundTick_8011e17c.addu(0x1L);

          if((soundTick_8011e17c.get() & 0x1L) != 0) {
            playSound(0x1L);
          }
        } else {
          _8011e170.setu(0x3L);
          totalXpFromCombat_800bc95c.set(0);

          if(additionsUnlocked_8011e1b8.get(0).get() + additionsUnlocked_8011e1b8.get(1).get() + additionsUnlocked_8011e1b8.get(2).get() == 0) {
            //LAB_8010dc9c
            inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
          } else if((joypadPress_8007a398.get() & 0x20L) != 0) {
            playSound(0x2L);
            _8011e178.setu(0);
            inventoryMenuState_800bdc28.set(InventoryMenuState.CONFIG_6);
          }
        }

        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case CONFIG_6:
        if((int)_8011e178.get() < 0x14L) {
          _8011e178.addu(0x2L);
        } else {
          //LAB_8010dcc8
          if((joypadPress_8007a398.get() & 0x20L) != 0) {
            playSound(0x2L);

            //LAB_8010dcf0
            inventoryMenuState_800bdc28.set(InventoryMenuState._7);
          }
        }

        //LAB_8010dcf4
        //LAB_8010dcf8
        renderAdditionsUnlocked((int)_8011e178.get());
        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _7:
        if((int)_8011e178.get() > 0) {
          _8011e178.subu(0x2L);
        } else {
          //LAB_8010dd28
          inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
        }

        renderAdditionsUnlocked((int)_8011e178.get());
        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case REPLACE_INIT_8:
        if(_8011e170.get() >= 0x9L) {
          //LAB_8010dd90
          inventoryMenuState_800bdc28.set(InventoryMenuState._10);
        } else if(_8011e1c8.offset(_8011e170.get()).get() != 0) {
          FUN_800192d8(-0x50L, 0x2cL);
          playSound(0x9L);
          inventoryMenuState_800bdc28.set(InventoryMenuState._9);
        } else {
          //LAB_8010dd88
          _8011e170.addu(0x1L);
        }

        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _9:
        FUN_8010e708(24, 152, secondaryCharIndices_800bdbf8.get((int)(_8011e170.get() - 3)).get());

        if((joypadPress_8007a398.get() & 0x60L) != 0) {
          playSound(0x2L);
          _8011e1c8.offset(_8011e170.get()).setu(0);
          inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
          _8011e170.addu(0x1L);
        }

        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _10:
        for(int charSlot = 0; charSlot < 3; charSlot++) {
          if(characterIsAlive(charSlot)) {
            levelUpDragoon(gameState_800babc8.charIndex_88.get(charSlot).get(), charSlot);
          }
        }

        //LAB_8010de6c
        if(spellsUnlocked_8011e1a8.get(0).get() != 0 || spellsUnlocked_8011e1a8.get(1).get() != 0 || spellsUnlocked_8011e1a8.get(2).get() != 0) {
          inventoryMenuState_800bdc28.set(InventoryMenuState._11);
        } else {
          //LAB_8010de98
          inventoryMenuState_800bdc28.set(InventoryMenuState._14);
        }

        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _11:
        if((joypadPress_8007a398.get() & 0x20L) != 0) {
          _8011e178.setu(0);
          playSound(0x2L);

          //LAB_8010decc
          inventoryMenuState_800bdc28.set(InventoryMenuState.EQUIPMENT_INIT_12);
        }

        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case EQUIPMENT_INIT_12:
        if(_8011e178.get() < 0x14L) {
          _8011e178.addu(0x2L);
        } else {
          //LAB_8010def4
          if((joypadPress_8007a398.get() & 0x20L) != 0) {
            playSound(0x2L);

            //LAB_8010df1c
            inventoryMenuState_800bdc28.set(InventoryMenuState._13);
          }
        }

        //LAB_8010df20
        //LAB_8010df24
        renderSpellsUnlocked((int)_8011e178.get());
        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _13:
        if((int)_8011e178.get() > 0) {
          _8011e178.subu(0x2L);
        } else {
          //LAB_8010df54
          //LAB_8010df1c
          inventoryMenuState_800bdc28.set(InventoryMenuState._14);
        }

        //LAB_8010df20
        //LAB_8010df24
        renderSpellsUnlocked((int)_8011e178.get());
        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _14:
        if((joypadPress_8007a398.get() & 0x60L) != 0) {
          playSound(0x3L);

          final InventoryMenuState nextMenuState;
          if(itemsDroppedByEnemiesCount_800bc978.get() == 0 || (FUN_80023544(itemsDroppedByEnemies_800bc928, itemsDroppedByEnemiesCount_800bc978) & 0xff) == 0) {
            //LAB_8010dfac
            nextMenuState = InventoryMenuState._18; // No items remaining
          } else {
            nextMenuState = InventoryMenuState._19; // Some items remaining
          }

          //LAB_8010dfb0
          FUN_8010d050(nextMenuState, 0x1L);
        }

        //LAB_8010dfb8
        //LAB_8010dfbc
        FUN_8010e9a8(0, xpDivisor_8011e174.get());
        break;

      case _16:
        scriptStartEffect(0x1L, 0xaL);
        inventoryMenuState_800bdc28.set(InventoryMenuState._17);

      case _17:
        FUN_8010e9a8(0, xpDivisor_8011e174.get());

        if((int)_800bb168.get() >= 0xffL) {
          inventoryMenuState_800bdc28.set(confirmDest_800bdc30.get());
          FUN_80019470();
        }

        break;

      case _18:
        scriptStartEffect(0x2L, 0xaL);
        deallocateRenderables(0xffL);
        free(drgn0_6666FilePtr_800bdc3c.getPointer());
        whichMenu_800bdc38 = WhichMenu.UNLOAD_POST_COMBAT_REPORT_30;
        textZ_800bdf00.set(13);
        break;

      case _19:
        setWidthAndFlags(384);
        deallocateRenderables(0xffL);
        inventoryMenuState_800bdc28.set(InventoryMenuState.AWAIT_INIT_1);
        whichMenu_800bdc38 = WhichMenu.RENDER_TOO_MANY_ITEMS_MENU_34;
        break;
    }

    //LAB_8010e09c
    //LAB_8010e0a0
    FUN_8010d078(166,  22, 136, 192, 1);
    FUN_8010d078( 14,  22, 144, 120, 1);
    FUN_8010d078( 14, 150, 144,  64, 1);
    FUN_8010d078( 0,    0, 240, 240, 0);
  }

  @Method(0x8010e114L)
  public static Renderable58 FUN_8010e114(final int x, final int y, final int charSlot) {
    if(charSlot >= 9) {
      //LAB_8010e1ec
      throw new IllegalArgumentException("Invalid character index");
    }

    final int glyph = (int)_800fbc9c.offset(charSlot).getSigned();
    final Renderable58 renderable = FUN_8010cfa0(glyph, glyph, x, y, 704, (int)_800fbc88.offset(charSlot * 0x2L).getSigned());
    renderable.z_3c = 35;

    //LAB_8010e1f0
    return renderable;
  }

  @Method(0x8010e200L)
  public static void FUN_8010e200(final int x, final int y, int val, final UnsignedIntRef a3) {
    val = val % 10;
    if(val != 0 || a3.get() != 0) {
      //LAB_8010e254
      final Renderable58 renderable = FUN_8010cfa0(val + 3, val + 3, x, y, 736, 497);
      renderable.flags_00 |= 0x8;
      a3.set(1);
    }

    //LAB_8010e290
  }

  @Method(0x8010e2a0L)
  public static void FUN_8010e2a0(final int x, final int y, final int dlevel) {
    final int s2 = Math.min(99, dlevel);
    final UnsignedIntRef sp0x10 = new UnsignedIntRef();
    FUN_8010e200(x, y, s2 / 10, sp0x10.set(0));
    FUN_8010e200(x + 6, y, s2, sp0x10.incr());
  }

  @Method(0x8010e340L)
  public static void FUN_8010e340(final int x, final int y, final int val) {
    final int s2 = Math.min(999_999, val);
    final UnsignedIntRef sp0x10 = new UnsignedIntRef();
    FUN_8010e200(x, y, s2 / 100_000, sp0x10);
    FUN_8010e200(x +  6, y, s2 / 10_000, sp0x10);
    FUN_8010e200(x + 12, y, s2 /  1_000, sp0x10);
    FUN_8010e200(x + 18, y, s2 /    100, sp0x10);
    FUN_8010e200(x + 24, y, s2 /     10, sp0x10);
    FUN_8010e200(x + 30, y, s2, sp0x10.incr());
  }

  @Method(0x8010e490L)
  public static void FUN_8010e490(final int x, final int y, final int val) {
    final int s2 = Math.min(99_999_999, val);
    final UnsignedIntRef sp0x10 = new UnsignedIntRef();
    FUN_8010e200(x, y, s2 / 10_000_000, sp0x10);
    FUN_8010e200(x +  6, y, s2 / 1_000_000, sp0x10);
    FUN_8010e200(x + 12, y, s2 /   100_000, sp0x10);
    FUN_8010e200(x + 18, y, s2 /    10_000, sp0x10);
    FUN_8010e200(x + 24, y, s2 /     1_000, sp0x10);
    FUN_8010e200(x + 30, y, s2 /       100, sp0x10);
    FUN_8010e200(x + 36, y, s2 /        10, sp0x10);
    FUN_8010e200(x + 42, y, s2, sp0x10.incr());
  }

  @Method(0x8010e630L)
  public static void FUN_8010e630(final int x, final int y, final int val) {
    if(val != 0) {
      FUN_8010e340(x, y, val);
    } else {
      //LAB_8010e660
      final Renderable58 renderable = FUN_8010cfa0(0x47, 0x47, x + 30, y, 736, 497);
      renderable.flags_00 |= 0x8;
    }

    //LAB_8010e698
  }

  @Method(0x8010e6a8L)
  public static int getXpWidth(final int xp) {
    if(xp > 99999) {
      return 36;
    }

    //LAB_8010e6c4
    if(xp > 9999) {
      return 30;
    }

    //LAB_8010e6d4
    if(xp > 999) {
      return 24;
    }

    //LAB_8010e6e4
    if(xp > 99) {
      //LAB_8010e6fc
      return 18;
    }

    if(xp > 9) {
      //LAB_8010e700
      return 12;
    }

    return 6;
  }

  @Method(0x8010e708L)
  public static void FUN_8010e708(final int x, final int y, final int charIndex) {
    if(charIndex != -1) {
      FUN_8010d078(x + 1, y + 5, 24, 32, 2);
      final Renderable58 renderable = FUN_8010e114(x - 1, y + 4, charIndex);
      renderable.flags_00 |= 0x8;
      FUN_8010cfa0((int)_800fbca8.offset(charIndex).get(), (int)_800fbca8.offset(charIndex).get(), x + 32, y + 4, 736, 497).flags_00 |= 0x8;
      FUN_8010cfa0(0x3b, 0x3b, x + 30, y + 16, 736, 497).flags_00 |= 0x8;
      FUN_8010cfa0(0x3c, 0x3c, x + 30, y + 28, 736, 497).flags_00 |= 0x8;
      FUN_8010cfa0(0x3d, 0x3d, x, y + 40, 736, 497).flags_00 |= 0x8;
      FUN_8010cfa0(0x3c, 0x3c, x, y + 52, 736, 497).flags_00 |= 0x8;
      FUN_8010cfa0(0x3d, 0x3d, x + 10, y + 52, 736, 497).flags_00 |= 0x8;

      FUN_8010e2a0(x + 108, y + 16, gameState_800babc8.charData_32c.get(charIndex).level_12.get());

      final int dlevel;
      if(!hasDragoon(gameState_800babc8.dragoonSpirits_19c.get(0).get(), charIndex)) {
        dlevel = 0;
      } else {
        dlevel = gameState_800babc8.charData_32c.get(charIndex).dlevel_13.get();
      }

      //LAB_8010e8e0
      FUN_8010e2a0(x + 108, y + 28, dlevel);
      final int xp = getXpToNextLevel(charIndex);
      FUN_8010e340(x + 76 - getXpWidth(xp), y + 40, gameState_800babc8.charData_32c.get(charIndex).xp_00.get());
      FUN_8010cfa0(0x22, 0x22, x - (getXpWidth(xp) - 114), y + 40, 736, 497).flags_00 |= 0x8;
      FUN_8010e630(x + 84, y + 40, xp);


      final int dxp = (int) _800fbbf0.offset(charIndex * 0x4L).deref(2).offset(gameState_800babc8.charData_32c.get(charIndex).dlevel_13.get() * 0x2L).offset(0x2L).get();
      FUN_8010e340(x + 76 - getXpWidth(dxp), y + 52, gameState_800babc8.charData_32c.get(charIndex).dlevelXp_0e.get());
      FUN_8010cfa0(0x22, 0x22, x - (getXpWidth(dxp) - 114), y + 52, 736, 497).flags_00 |= 0x8;
      FUN_8010e630(x + 84, y + 52, dxp);
    }

    //LAB_8010e978
  }

  @Method(0x8010e9a8L)
  public static void FUN_8010e9a8(final long a0, final long a1) {
    int y1 = 24;
    int y2 = -82;
    int y3 = -70;

    //LAB_8010e9fc
    for(int i = 0; i < 3; i++) {
      if(gameState_800babc8.charIndex_88.get(i).get() != -1) {
        FUN_8010e708(176, y1, gameState_800babc8.charIndex_88.get(i).get());

        if(_8011e1c8.offset(i).get() != 0) {
          _8011e1c8.offset(i).setu(0);
          FUN_800192d8(72, y2);
          playSound(9);
        }

        //LAB_8010ea44
        if(_8011e1d8.offset(i).get() != 0) {
          _8011e1d8.offset(i).setu(0);
          FUN_800192d8(72, y3);
          playSound(9);
        }
      }

      //LAB_8010ea70
      y1 += 64;
      y2 += 64;
      y3 += 64;
    }

    FUN_8010e490( 96, 28, goldGainedFromCombat_800bc920.get());
    FUN_8010e340(108, 40, totalXpFromCombat_800bc95c.get());

    y1 = 63;
    y2 = 64;

    //LAB_8010eae0
    for(int i = 0; i < itemsDroppedByEnemiesCount_800bc978.get(); i++) {
      if(itemsDroppedByEnemies_800bc928.get(i).get() != 0xff) {
        renderItemIcon(getItemIcon(itemsDroppedByEnemies_800bc928.get(i).get()), 18, y1, 0x8L);
        renderText(equipment_8011972c.get(itemsDroppedByEnemies_800bc928.get(i).get()).deref(), 28, y2, 0);
      }

      //LAB_8010eb38
      y2 += 16;
      y1 += 16;
    }

    //LAB_8010eb58
    FUN_8010e490(96, 156, gameState_800babc8.gold_94.get());

    if(a0 != 0) {
      FUN_8010cfa0(0x3f, 0x3f, 144,  28, 736, 497);
      FUN_8010cfa0(0x3f, 0x3f, 144, 156, 736, 497);
    }

    //LAB_8010ebb0
    uploadRenderables();
    FUN_80018e84();
  }

  @Method(0x8010ebecL)
  public static void renderAdditionsUnlocked(final int height) {
    for(int i = 0; i < 3; i++) {
      if(additionsUnlocked_8011e1b8.get(i).get() != 0) {
        renderAdditionUnlocked(168, 40 + i * 64, additionsUnlocked_8011e1b8.get(i).get() - 1, height);
      }
    }
  }

  @Method(0x8010ec6cL)
  public static void renderSpellsUnlocked(final int height) {
    //LAB_8010ec98
    for(int i = 0; i < 3; i++) {
      if(spellsUnlocked_8011e1a8.get(i).get() != 0) {
        renderSpellUnlocked(168, 40 + i * 64, spellsUnlocked_8011e1a8.get(i).get() - 1, height);
      }

      //LAB_8010ecc0
    }
  }

  @Method(0x8010ececL)
  public static MessageBoxResult messageBox(final MessageBox20 messageBox) {
    final Renderable58 renderable;

    switch(messageBox.state_0c) {
      case 0:
        return MessageBoxResult.YES;

      case 1: // Allocate
        messageBox.state_0c = 2;
        messageBox.renderable_04 = null;
        messageBox.renderable_08 = allocateUiElement(149, 142, messageBox.x_1c - 50, messageBox.y_1e - 10);
        messageBox.renderable_08.z_3c = 32;
        messageBox.renderable_08._18 = 142;
        msgboxResult_8011e1e8.set(MessageBoxResult.AWAITING_INPUT);

      case 2:
        if(messageBox.renderable_08._0c != 0) {
          messageBox.state_0c = 3;
        }

        break;

      case 3:
        textZ_800bdf00.set(31);
        final int x = messageBox.x_1c + 60;
        int y = messageBox.y_1e + 7;

        messageBox.ticks_10++;

        if(messageBox.text_00 != null) {
          for(final LodString line : messageBox.text_00) {
            renderCentredText(line, x, y, 4);
            y += 14;
          }
        }

        //LAB_8010eeac
        textZ_800bdf00.set(33);

        if(messageBox.type_15 == 0) {
          //LAB_8010eed8
          if((inventoryJoypadInput_800bdc44.get() & 0x60) != 0) {
            playSound(2);
            messageBox.state_0c = 4;
            msgboxResult_8011e1e8.set(MessageBoxResult.YES);
          }

          break;
        }

        if(messageBox.type_15 == 2) {
          //LAB_8010ef10
          if(messageBox.renderable_04 == null) {
            renderable = allocateUiElement(125, 125, messageBox.x_1c + 45, messageBox.menuIndex_18 * 14 + y + 5);
            messageBox.renderable_04 = renderable;
            renderable._38 = 0;
            renderable._34 = 0;
            messageBox.renderable_04.z_3c = 32;
          }

          //LAB_8010ef64
          textZ_800bdf00.set(31);

          renderCentredText(Yes_8011c20c, messageBox.x_1c + 60, y + 7, messageBox.menuIndex_18 == 0 ? 5 : 4);
          renderCentredText(No_8011c214, messageBox.x_1c + 60, y + 21, messageBox.menuIndex_18 == 0 ? 4 : 5);

          textZ_800bdf00.set(33);

          final IntRef index = new IntRef().set(messageBox.menuIndex_18);
          final YesNoResult msgboxYesNo = handleYesNo(index);
          messageBox.menuIndex_18 = index.get();

          if(msgboxYesNo == YesNoResult.SCROLLED) {
            //LAB_8010f014
            messageBox.renderable_04.y_44 = messageBox.menuIndex_18 * 14 + y + 5;
          } else if(msgboxYesNo == YesNoResult.YES) {
            //LAB_8010f040
            messageBox.state_0c = 4;
            msgboxResult_8011e1e8.set(MessageBoxResult.YES);
          } else if(msgboxYesNo == YesNoResult.NO || msgboxYesNo == YesNoResult.CANCELLED) {
            //LAB_8010f000
            //LAB_8010f05c
            messageBox.state_0c = 4;
            msgboxResult_8011e1e8.set(MessageBoxResult.NO);
          }
        }

        break;

      case 4:
        messageBox.state_0c = 5;

        if(messageBox.renderable_04 != null) {
          unloadRenderable(messageBox.renderable_04);
        }

        //LAB_8010f084
        unloadRenderable(messageBox.renderable_08);
        renderable = allocateUiElement(0x8e, 0x95, messageBox.x_1c - 50, messageBox.y_1e - 10);
        messageBox.renderable_08 = renderable;
        renderable.z_3c = 32;
        messageBox.renderable_08.flags_00 |= 0x10;
        break;

      case 5:
        if(messageBox.renderable_08._0c != 0) {
          messageBox.state_0c = 6;
        }

        break;

      case 6:
        messageBox.state_0c = 0;
        return msgboxResult_8011e1e8.get();
    }

    //LAB_8010f108
    //LAB_8010f10c
    return MessageBoxResult.AWAITING_INPUT;
  }

  @Method(0x8010f130L)
  public static void setMessageBoxText(final MessageBox20 messageBox, @Nullable final LodString text, final int type) {
    if(text != null) {
      final List<LodString> lines = new ArrayList<>();
      final int length = textLength(text);

      int lineStart = 0;
      for(int charIndex = 0; charIndex < length; charIndex++) {
        if(text.charAt(charIndex) == 0xa1ff) {
          final LodString slice = text.slice(lineStart, charIndex - lineStart + 1);
          slice.charAt(charIndex - lineStart, 0xa0ff);
          lines.add(slice);
          lineStart = charIndex + 1;
        }
      }

      lines.add(text.slice(lineStart));

      messageBox.text_00 = lines.toArray(LodString[]::new);
    } else {
      messageBox.text_00 = null;
    }

    messageBox.x_1c = 120;
    messageBox.y_1e = 100;
    messageBox.type_15 = type;
    messageBox.menuIndex_18 = 0;
    messageBox.ticks_10 = 0;
    messageBox.state_0c = 1;
  }

  @Method(0x8010f178L)
  public static int FUN_8010f178(final int a0) {
    return a0 * 17 + 42;
  }

  @Method(0x8010f188L)
  public static int FUN_8010f188(final int a0) {
    return 160 + a0 * 17;
  }

  @Method(0x8010f198L)
  public static void renderTooManyItemsMenu() {
    inventoryJoypadInput_800bdc44.setu(getJoypadInputByPriority());

    //TODO this menu should use its own state var - the enum is for the giant main state method above
    switch(inventoryMenuState_800bdc28.get()) {
      case INIT_0:
        inventoryMenuState_800bdc28.set(InventoryMenuState.AWAIT_INIT_1);
        break;

      case AWAIT_INIT_1:
        if(!drgn0_6666FilePtr_800bdc3c.isNull()) {
          _8011dcb8.get(0).setPointer(mallocTail(0x4c0L));
          _8011dcb8.get(1).setPointer(mallocTail(0x4c0L));
          recalcInventory();
          FUN_80104738(0x1L);
          messageBox_8011dc90.state_0c = 0;

          //LAB_8010f2e8
          for(int itemIndex = 0; itemIndex < 10; itemIndex++) {
            final MenuItemStruct04 item = menuItems_8011d7c8.get(itemIndex);

            final int itemId;
            if(itemIndex >= itemsDroppedByEnemiesCount_800bc978.get()) {
              itemId = 0xff;
            } else {
              itemId = itemsDroppedByEnemies_800bc928.get(itemIndex).get();
            }

            //LAB_8010f300
            item.itemId_00.set(itemId);
            item.itemSlot_01.set(0);
            item.price_02.set(0);
          }

          inventoryMenuState_800bdc28.set(InventoryMenuState._2);
        }
        break;

      case _2:
        deallocateRenderables(0xffL);
        slotScroll_8011e1f8.set(0);
        slotIndex_8011e1f4.set(0);
        menuIndex_8011e1f0.set(0);
        inventoryMenuState_800bdc28.set(InventoryMenuState.INIT_MAIN_MENU_3);
        break;

      case INIT_MAIN_MENU_3:
        deallocateRenderables(0);
        FUN_8010fd80(true, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0);
        menuIndex_8011e1fc.set(0);
        final Renderable58 renderable = allocateUiElement(125, 125, 136, FUN_8010f188(0) - 2);
        renderable_8011e208 = renderable;
        FUN_80104b60(renderable);
        scriptStartEffect(0x2L, 0xaL);
        inventoryMenuState_800bdc28.set(InventoryMenuState.MAIN_MENU_4);
        break;

      case MAIN_MENU_4:
        FUN_8010fd80(false, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0);

        if(_800bb168.get() == 0) {
          renderText(Too_many_8011c21c, 16, 151, 4);
          renderText(items_8011c230, 16, 168, 4);
          renderText(Replace_8011c240, 16, 185, 4);
          renderCentredText(Yes_8011c20c, 150, FUN_8010f188(0), menuIndex_8011e1fc.get() == 0 ? 5 : 6);
          renderCentredText(No_8011c214, 150, FUN_8010f188(1), menuIndex_8011e1fc.get() != 0 ? 5 : 6);

          switch(handleYesNo(menuIndex_8011e1fc)) {
            case SCROLLED ->
              renderable_8011e208.y_44 = FUN_8010f188(menuIndex_8011e1fc.get()) - 2;
            case YES -> {
              unloadRenderable(renderable_8011e208);
              inventoryMenuState_800bdc28.set(InventoryMenuState.CONFIG_6);
            }
            case NO, CANCELLED -> {
              unloadRenderable(renderable_8011e208);
              inventoryMenuState_800bdc28.set(InventoryMenuState._10);
            }
          }
        }
        break;

      case CONFIG_6:
        menuIndex_8011e1f0.set(0);
        final Renderable58 renderable2 = allocateUiElement(124, 124, 42, FUN_8010f178(0));
        renderable_8011e200 = renderable2;
        FUN_80104b60(renderable2);

      case _7:
        deallocateRenderables(0);
        FUN_8010fd80(true, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0x1L);
        inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
        break;

      case REPLACE_INIT_8:
        if(scrollMenu(menuIndex_8011e1f0, null, 5, itemsDroppedByEnemiesCount_800bc978.get(), 1)) {
          renderable_8011e200.y_44 = FUN_8010f178(menuIndex_8011e1f0.get());
        }

        //LAB_8010f608
        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0) {
          if(menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get() != 0xff) {
            slotScroll_8011e1f8.set(0);
            slotIndex_8011e1f4.set(0);
            final Renderable58 renderable3 = allocateUiElement(118, 118, 220, FUN_8010f178(0));
            renderable_8011e204 = renderable3;
            FUN_80104b60(renderable3);
            playSound(0x2L);
            inventoryMenuState_800bdc28.set(InventoryMenuState._9);
          } else {
            //LAB_8010f68c
            playSound(0x28L);
          }
        }

        //LAB_8010f694
        if((inventoryJoypadInput_800bdc44.get() & 0x40L) != 0) {
          playSound(0x3L);
          unloadRenderable(renderable_8011e200);
          inventoryMenuState_800bdc28.set(InventoryMenuState._10);
        }

        //LAB_8010f6d4
        FUN_8010fd80(false, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0x1L);
        break;

      case _9:
        final int slotCount;
        if(menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get() < 0xc0) {
          slotCount = gameState_800babc8.equipmentCount_1e4.get();
        } else {
          //LAB_8010f754
          slotCount = gameState_800babc8.itemCount_1e6.get();
        }

        //LAB_8010f76c
        if(scrollMenu(slotIndex_8011e1f4, slotScroll_8011e1f8, 7, slotCount, 1)) {
          renderable_8011e204.y_44 = FUN_8010f178(slotIndex_8011e1f4.get());
        }

        //LAB_8010f79c
        if((inventoryJoypadInput_800bdc44.get() & 0x40L) != 0) {
          playSound(0x3L);
          unloadRenderable(renderable_8011e204);
          inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);
        }

        //LAB_8010f7d8
        if((inventoryJoypadInput_800bdc44.get() & 0x10L) != 0) {
          playSound(0x2L);

          if(menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get() < 0xc0) {
            sortItems(_8011dcb8.get(0).deref(), gameState_800babc8.equipment_1e8, gameState_800babc8.equipmentCount_1e4.get());
          } else {
            //LAB_8010f838
            sortItems(_8011dcb8.get(1).deref(), gameState_800babc8.items_2e9, gameState_800babc8.itemCount_1e6.get());
          }
        }

        //LAB_8010f858
        if((inventoryJoypadInput_800bdc44.get() & 0x20L) != 0) {
          final MenuItemStruct04 newItem = menuItems_8011d7c8.get(menuIndex_8011e1f0.get());
          final int isItem = menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get() >= 0xc0 ? 1 : 0;
          final MenuItemStruct04 existingItem = _8011dcb8.get(isItem).deref().get(slotIndex_8011e1f4.get() + slotScroll_8011e1f8.get());

          if((existingItem.price_02.get() & 0x6000) != 0) {
            playSound(0x28L);
          } else {
            //LAB_8010f8f4
            final int itemId = existingItem.itemId_00.get();
            final int itemSlot = existingItem.itemSlot_01.get();
            final int flags = existingItem.price_02.get();

            existingItem.itemId_00.set(newItem.itemId_00.get());
            existingItem.itemSlot_01.set(newItem.itemSlot_01.get());
            existingItem.price_02.set(newItem.price_02.get());

            newItem.itemId_00.set(itemId);
            newItem.itemSlot_01.set(itemSlot);
            newItem.price_02.set(flags);

            playSound(2);
            unloadRenderable(renderable_8011e204);
            inventoryMenuState_800bdc28.set(InventoryMenuState.REPLACE_INIT_8);

            //LAB_8010f99c
            if(isItem != 0) {
              FUN_800239e0(_8011dcb8.get(1).deref(), gameState_800babc8.items_2e9, gameState_800babc8.itemCount_1e6.get());
            } else {
              //LAB_8010f98c
              FUN_800239e0(_8011dcb8.get(0).deref(), gameState_800babc8.equipment_1e8, gameState_800babc8.equipmentCount_1e4.get());
            }
          }
        }

        //LAB_8010f9a4
        //LAB_8010f9a8
        FUN_8010fd80(false, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0x3L);
        break;

      case _10:
        menuIndex_8011e1fc.set(0);
        final Renderable58 renderable4 = allocateUiElement(125, 125, 136, FUN_8010f188(0) - 2);
        renderable_8011e208 = renderable4;
        FUN_80104b60(renderable4);
        inventoryMenuState_800bdc28.set(InventoryMenuState._11);

      case _11:
        renderText(To_many_items_8011c268, 16, 151, 4);
        renderText(Discard_8011c288, 16, 168, 4);
        renderText(End_8011c29c, 16, 185, 4);
        renderCentredText(Yes_8011c20c, 150, FUN_8010f188(0), menuIndex_8011e1fc.get() == 0 ? 5 : 6);
        renderCentredText(No_8011c214, 150, FUN_8010f188(1), menuIndex_8011e1fc.get() != 0 ? 5 : 6);
        FUN_8010fd80(false, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0);

        switch(handleYesNo(menuIndex_8011e1fc)) {
          //LAB_8010fb28
          case SCROLLED ->
            //LAB_8010fb4c
            renderable_8011e208.y_44 = FUN_8010f188(menuIndex_8011e1fc.get()) - 2;

          case YES -> {
            //LAB_8010fb6c
            unloadRenderable(renderable_8011e208);

            //LAB_8010fb94
            long s2 = 0;
            for(int i = 0; i < itemsDroppedByEnemiesCount_800bc978.get(); i++) {
              if(FUN_80022898(menuItems_8011d7c8.get(i).itemId_00.get()) != 0) {
                s2 = s2 + 0x1L;
              }

              //LAB_8010fbb0
            }

            //LAB_8010fbc4
            if(s2 != 0) {
              setMessageBoxText(messageBox_8011dc90, This_item_cannot_be_thrown_away_8011c2a8, 0);
              inventoryMenuState_800bdc28.set(InventoryMenuState._13);
            } else {
              //LAB_8010fbe8
              scriptStartEffect(0x1L, 0xaL);
              inventoryMenuState_800bdc28.set(InventoryMenuState.EQUIPMENT_INIT_12);
            }
          }

          case NO, CANCELLED -> {
            //LAB_8010fc04
            //LAB_8010fc08
            unloadRenderable(renderable_8011e208);
            inventoryMenuState_800bdc28.set(InventoryMenuState.CONFIG_6);
          }
        }
        break;

      case EQUIPMENT_INIT_12:
        FUN_8010fd80(false, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0);

        if(_800bb168.get() >= 0xff) {
          scriptStartEffect(0x2L, 0xaL);
          free(_8011dcb8.get(0).getPointer());
          free(_8011dcb8.get(1).getPointer());
          deallocateRenderables(0xffL);
          free(drgn0_6666FilePtr_800bdc3c.getPointer());
          whichMenu_800bdc38 = WhichMenu.UNLOAD_TOO_MANY_ITEMS_MENU_35;

          if(mainCallbackIndex_8004dd20.get() == 0x5L && loadingGameStateOverlay_8004dd08.get() == 0) {
            FUN_800e3fac();
          }

          //LAB_8010fd00
          //LAB_8010fd04
          textZ_800bdf00.set(13);
        }
        break;

      case _13:
        if(messageBox(messageBox_8011dc90) != MessageBoxResult.AWAITING_INPUT) {
          inventoryMenuState_800bdc28.set(InventoryMenuState.CONFIG_6);
        }

        //LAB_8010fd28
        //LAB_8010fd5c
        FUN_8010fd80(false, menuItems_8011d7c8.get(menuIndex_8011e1f0.get()).itemId_00.get(), slotIndex_8011e1f4.get(), slotScroll_8011e1f8.get(), 0);
        break;
    }

    //LAB_8010fd64
  }

  @Method(0x8010fd80L)
  public static void FUN_8010fd80(final boolean allocate, final int itemId, final int slotIndex, final int slotScroll, final long a4) {
    if(allocate) {
      renderGlyphs(glyphs_80114548, 0, 0);
      saveListUpArrow_800bdb94 = allocateUiElement(61, 68, 358, FUN_8010f178(0));
      saveListDownArrow_800bdb98 = allocateUiElement(53, 60, 358, FUN_8010f178(6));
    }

    //LAB_8010fe18
    //LAB_8010fe38
    renderMenuItems(16, 33, menuItems_8011d7c8, 0, Math.min(5, itemsDroppedByEnemiesCount_800bc978.get()), saveListUpArrow_800bdb94, saveListDownArrow_800bdb98);

    if((a4 & 0x1L) != 0 && !allocate) {
      renderString(0, 16, 164, itemId, false);
    }

    //LAB_8010fe90
    //LAB_8010fe94
    renderText(Acquired_item_8011c2f8, 32, 22, 4);

    if(itemId >= 0xc0) {
      //LAB_8010ff30
      if(itemId >= 0xff && (a4 & 0x2L) != 0) {
        final Renderable58 renderable = FUN_801038d4(137, 84, 140);
        renderable.clut_30 = 0x7ceb;
        renderText(Press_to_sort_8011d024, 37, 140, 4);
      }

      renderText(_8011c32c, 210, 22, 4);

      if((a4 & 0x1L) != 0) {
        renderMenuItems(194, 33, _8011dcb8.get(1).deref(), slotScroll, 7, saveListUpArrow_800bdb94, saveListDownArrow_800bdb98);
      }

      //LAB_8010ff90
      if((a4 & 0x2L) != 0) {
        //LAB_8010ffb4
        renderString(0, 194, 164, _8011dcb8.get(1).deref().get(slotScroll + slotIndex).itemId_00.get(), allocate);

        //LAB_8010ffcc
        if((a4 & 0x2L) != 0) {
          final Renderable58 renderable = FUN_801038d4(137, 84, 140);
          renderable.clut_30 = 0x7ceb;
          renderText(Press_to_sort_8011d024, 37, 140, 4);
        }
      }
    } else {
      renderText(_8011c314, 210, 22, 4);

      if((a4 & 0x1L) != 0) {
        renderMenuItems(194, 33, _8011dcb8.get(0).deref(), slotScroll, 7, saveListUpArrow_800bdb94, saveListDownArrow_800bdb98);
      }

      //LAB_8010ff08
      if((a4 & 0x2L) != 0) {
        renderString(0, 194, 164, _8011dcb8.get(0).deref().get(slotScroll + slotIndex).itemId_00.get(), allocate);

        if((a4 & 0x2L) != 0) {
          final Renderable58 renderable = FUN_801038d4(137, 84, 140);
          renderable.clut_30 = 0x7ceb;
          renderText(Press_to_sort_8011d024, 37, 140, 4);
        }
      }
    }

    //LAB_80110004
    uploadRenderables();
  }

  @Method(0x80110030L)
  public static void loadCharacterStats(long a0) {
    final long spc0 = a0;

    clearCharacterStats();

    //LAB_80110174
    for(int charIndex = 0; charIndex < 9; charIndex++) {
      final ActiveStatsa0 stats = stats_800be5f8.get(charIndex);

      final CharacterData2c charData = gameState_800babc8.charData_32c.get(charIndex);

      stats.xp_00.set(charData.xp_00.get());
      stats.hp_04.set(charData.hp_08.get());
      stats.mp_06.set(charData.mp_0a.get());
      stats.sp_08.set(charData.sp_0c.get());
      stats._0a.set(charData.dlevelXp_0e.get());
      stats.dragoonFlag_0c.set(charData.status_10.get());
      stats.level_0e.set(charData.level_12.get());
      stats.dlevel_0f.set(charData.dlevel_13.get());

      //LAB_801101e4
      for(int i = 0; i < 5; i++) {
        stats.equipment_30.get(i).set(charData.equipment_14.get(i).get());
      }

      stats.selectedAddition_35.set(charData.selectedAddition_19.get());

      //LAB_80110220
      for(int i = 0; i < 8; i++) {
        stats.additionLevels_36.get(i).set(charData.additionLevels_1a.get(i).get());
        stats.additionXp_3e.get(i).set(charData.additionXp_22.get(i).get());
      }

      final LevelStuff08 levelStuff = levelStuff_800fbd30.get(charIndex).deref().get(stats.level_0e.get());
      stats.maxHp_66.set(levelStuff.hp_00.get());
      stats.addition_68.set(levelStuff.addition_02.get());
      stats.bodySpeed_69.set(levelStuff.bodySpeed_03.get());
      stats.bodyAttack_6a.set(levelStuff.bodyAttack_04.get());
      stats.bodyMagicAttack_6b.set(levelStuff.bodyMagicAttack_05.get());
      stats.bodyDefence_6c.set(levelStuff.bodyDefence_06.get());
      stats.bodyMagicDefence_6d.set(levelStuff.bodyMagicDefence_07.get());

      final MagicStuff08 magicStuff = magicStuff_800fbd54.get(charIndex).deref().get(stats.dlevel_0f.get());
      stats.maxMp_6e.set(magicStuff.mp_00.get());
      stats.spellIndex_70.set(magicStuff.spellIndex_02.get());
      stats._71.set(magicStuff._03.get());
      stats.dragoonAttack_72.set(magicStuff.dragoonAttack_04.get());
      stats.dragoonMagicAttack_73.set(magicStuff.dragoonMagicAttack_05.get());
      stats.dragoonDefence_74.set(magicStuff.dragoonDefence_06.get());
      stats.dragoonMagicDefence_75.set(magicStuff.dragoonMagicDefence_07.get());

      final int a2 = stats.selectedAddition_35.get();
      if(a2 != -1) {
        //TODO straighten this out
        a0 = ptrTable_80114070.offset(a2 * 0x4L).deref(4).offset(MEMORY.ref(1, stats.additionLevels_36.getAddress()).offset(a2 - additionOffsets_8004f5ac.get(charIndex).get()).get() * 0x4L).getAddress();

        stats._9c.set((int)MEMORY.ref(2, a0).offset(0x0L).get());
        stats.additionSpMultiplier_9e.set((int)MEMORY.ref(1, a0).offset(0x2L).get());
        stats.additionDamageMultiplier_9f.set((int)MEMORY.ref(1, a0).offset(0x3L).get());
      }

      //LAB_8011042c
      FUN_8011085c(charIndex);

      long v0 = _800fbd08.get(charIndex).get();
      a0 = v0 & 0x1fL;
      v0 = v0 >>> 5;
      if((gameState_800babc8.dragoonSpirits_19c.get((int)v0).get() & 0x1L << a0) != 0) {
        stats.dragoonFlag_0c.or(0x2000);
        a0 = _800fbd08.get(charIndex).get();

        if((gameState_800babc8._4e6.get() >> a0 & 1) == 0) {
          gameState_800babc8._4e6.or(1 << a0);

          stats.mp_06.set(magicStuff.mp_00.get());
          stats.maxMp_6e.set(magicStuff.mp_00.get());
        }
      } else {
        //LAB_801104ec
        stats.mp_06.set(0);
        stats.maxMp_6e.set(0);
        stats.dlevel_0f.set(0);
      }

      //LAB_801104f8
      if(charIndex == 0) {
        v0 = _800fbd08.get(9).get();

        a0 = v0 & 0x1fL;
        v0 = v0 >>> 5;
        if((gameState_800babc8.dragoonSpirits_19c.get((int)v0).get() & 0x1L << a0) != 0) {
          stats.dragoonFlag_0c.or(0x6000);

          final long a1 = _800fbd08.get(0).get();

          if((gameState_800babc8._4e6.get() >> a1 & 1) == 0) {
            gameState_800babc8._4e6.or(1 << a1);
            stats.mp_06.set(magicStuff.mp_00.get());
            stats.maxMp_6e.set(magicStuff.mp_00.get());
          } else {
            //LAB_80110590
            stats.mp_06.set(charData.mp_0a.get());
            stats.maxMp_6e.set(magicStuff.mp_00.get());
          }
        }
      }

      //LAB_801105b0
      int maxHp = stats.maxHp_66.get() * (stats.hpMulti_62.get() / 100 + 1);

      //TODO remove HP cap
      if(maxHp >= 9999) {
        maxHp = 9999;
      }

      //LAB_801105f0
      stats.maxHp_66.set(maxHp);

      if(stats.hp_04.get() > maxHp) {
        stats.hp_04.set(maxHp);
      }

      //LAB_80110608
      final int maxMp = stats.maxMp_6e.get() * (stats.mpMulti_64.get() / 100 + 1);

      stats.maxMp_6e.set(maxMp);

      if(stats.mp_06.get() > maxMp) {
        stats.mp_06.set(maxMp);
      }

      //LAB_80110654
    }

    if(spc0 == 0x1L) {
      decrementOverlayCount();
      _800be5d0.setu(1);
    }

    //LAB_8011069c
  }

  @Method(0x801106ccL)
  public static void FUN_801106cc(final int equipmentId) {
    FUN_8002a8f8();

    memcpy(equipmentStats_800be5d8.getAddress(), equipmentStats_80111ff0.get(equipmentId).getAddress(), 0x1c);
  }

  @Method(0x8011085cL)
  public static void FUN_8011085c(final int charIndex) {
    FUN_8002a86c(charIndex);
    final ActiveStatsa0 characterStats = stats_800be5f8.get(charIndex);
    final EquipmentStats1c equipmentStats = equipmentStats_800be5d8;

    //LAB_801108b0
    for(int equipmentSlot = 0; equipmentSlot < 5; equipmentSlot++) {
      final int equipmentId = stats_800be5f8.get(charIndex).equipment_30.get(equipmentSlot).get();

      if(equipmentId != 0xff) {
        FUN_801106cc(equipmentId);

        characterStats.specialEffectFlag_76.or(equipmentStats._00.get());
        characterStats._77.or(equipmentStats.type_01.get());
        characterStats._78.or(equipmentStats._02.get());
        characterStats._79.or(equipmentStats.equips_03.get());
        characterStats.elementFlag_7a.or(equipmentStats.element_04.get());
        characterStats._7b.or(equipmentStats._05.get());
        characterStats.elementalResistanceFlag_7c.or(equipmentStats.eHalf_06.get());
        characterStats.elementalImmunityFlag_7d.or(equipmentStats.eImmune_07.get());
        characterStats.statusResistFlag_7e.or(equipmentStats.statRes_08.get());
        characterStats._7f.or(equipmentStats._09.get());
        characterStats._84.add(equipmentStats.icon_0e.get());
        characterStats.gearSpeed_86.add(equipmentStats.spd_0f.get());
        characterStats.gearAttack_88.add(equipmentStats.atkHi_10.get());
        characterStats.gearMagicAttack_8a.add(equipmentStats.matk_11.get());
        characterStats.gearDefence_8c.add(equipmentStats.def_12.get());
        characterStats.gearMagicDefence_8e.add(equipmentStats.mdef_13.get());
        characterStats.attackHit_90.add(equipmentStats.aHit_14.get());
        characterStats.magicHit_92.add(equipmentStats.mHit_15.get());
        characterStats.attackAvoid_94.add(equipmentStats.aAv_16.get());
        characterStats.magicAvoid_96.add(equipmentStats.mAv_17.get());
        characterStats.onHitStatusChance_98.add(equipmentStats.onStatusChance_18.get());
        characterStats._99.add(equipmentStats._19.get());
        characterStats._9a.add(equipmentStats._1a.get());
        characterStats.onHitStatus_9b.or(equipmentStats.onHitStatus_1b.get());
        characterStats._80.add(equipmentStats.atk_0a.get());
        characterStats.gearAttack_88.add((short)equipmentStats.atk_0a.get());
        characterStats._81.or(equipmentStats.special1_0b.get());

        //LAB_80110b10
        long a0 = 0x1L;
        long a1;
        for(a1 = 0; a1 < 8; a1++) {
          if((equipmentStats.special1_0b.get() & a0) != 0) {
            if(a0 == 0x1L) {
              //LAB_80110c14
              characterStats.mpPerMagicalHit_54.add((short)equipmentStats.specialAmount_0d.get());
            } else if(a0 == 0x2L) {
              //LAB_80110bfc
              characterStats.spPerMagicalHit_52.add((short)equipmentStats.specialAmount_0d.get());
              //LAB_80110b54
            } else if(a0 == 0x4L) {
              //LAB_80110be4
              characterStats.mpPerPhysicalHit_50.add((short)equipmentStats.specialAmount_0d.get());
            } else if(a0 == 0x8L) {
              //LAB_80110bcc
              characterStats.spPerPhysicalHit_4e.add((short)equipmentStats.specialAmount_0d.get());
            } else if(a0 == 0x10L) {
              //LAB_80110bb4
              characterStats.spMultiplier_4c.add((short)equipmentStats.specialAmount_0d.get());
              //LAB_80110b64
            } else if(a0 == 0x20L) {
              //LAB_80110bac
              characterStats.physicalResistance_4a.set(1);
              //LAB_80110b88
            } else if(a0 == 0x40L) {
              //LAB_80110ba4
              characterStats.magicalImmunity_48.set(1);
            } else if(a0 == 0x80L) {
              characterStats.physicalImmunity_46.set(1);
            }
          }

          //LAB_80110c28
          a0 = a0 << 1;
        }

        characterStats._82.or(equipmentStats.special2_0c.get());

        //LAB_80110c54
        a0 = 0x1L;
        for(a1 = 0; a1 < 8; a1++) {
          if((equipmentStats.special2_0c.get() & a0) != 0) {
            if(a0 == 0x1L) {
              //LAB_80110d78
              characterStats.mpMulti_64.add((short)equipmentStats.specialAmount_0d.get());
            } else if(a0 == 0x2L) {
              //LAB_80110d60
              characterStats.hpMulti_62.add((short)equipmentStats.specialAmount_0d.get());
              //LAB_80110c98
            } else if(a0 == 0x4L) {
              //LAB_80110d58
              characterStats.magicalResistance_60.set(1);
            } else if(a0 == 0x8L) {
              //LAB_80110d40
              characterStats.revive_5e.add((short)equipmentStats.specialAmount_0d.get());
            } else if(a0 == 0x10L) {
              //LAB_80110d28
              characterStats.spRegen_5c.add((short)equipmentStats.specialAmount_0d.get());
              //LAB_80110ca8
            } else if(a0 == 0x20L) {
              //LAB_80110d10
              characterStats.mpRegen_5a.add((short)equipmentStats.specialAmount_0d.get());
              //LAB_80110ccc
            } else if(a0 == 0x40L) {
              //LAB_80110cf8
              characterStats.hpRegen_58.add((short)equipmentStats.specialAmount_0d.get());
            } else if(a0 == 0x80L) {
              characterStats._56.add((short)equipmentStats.specialAmount_0d.get());
            }
          }

          //LAB_80110d8c
          a0 = a0 << 1;
        }
      }
    }
  }
}
