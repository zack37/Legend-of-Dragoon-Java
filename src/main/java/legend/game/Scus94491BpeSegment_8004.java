package legend.game;

import legend.core.gte.COLOUR;
import legend.core.gte.MATRIX;
import legend.core.gte.SVECTOR;
import legend.core.gte.VECTOR;
import legend.core.memory.Method;
import legend.core.memory.Value;
import legend.core.memory.types.ArrayRef;
import legend.core.memory.types.BoolRef;
import legend.core.memory.types.FunctionRef;
import legend.core.memory.types.IntRef;
import legend.core.memory.types.Pointer;
import legend.core.memory.types.RunnableRef;
import legend.core.memory.types.ShortRef;
import legend.core.memory.types.UnboundedArrayRef;
import legend.core.memory.types.UnsignedShortRef;
import legend.core.spu.Spu;
import legend.core.spu.Voice;
import legend.game.combat.Bttl_800c;
import legend.game.combat.Bttl_800e;
import legend.game.title.Ttle;
import legend.game.types.CallbackStruct;
import legend.game.types.FileEntry08;
import legend.game.types.ItemStats0c;
import legend.game.types.MoonMusic08;
import legend.game.types.PlayableSoundStruct;
import legend.game.types.RunningScript;
import legend.game.types.ScriptFile;
import legend.game.types.SpuStruct124;
import legend.game.types.SpuStruct44;
import legend.game.types.SpuStruct66;
import legend.game.types.SshdFile;
import legend.game.types.SssqFile;
import legend.game.types.SubmapMusic08;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

import static legend.core.GameEngine.CPU;
import static legend.core.GameEngine.MEMORY;
import static legend.core.GameEngine.SPU;
import static legend.game.Scus94491BpeSegment._80011db0;
import static legend.game.Scus94491BpeSegment_8003.bzero;
import static legend.game.Scus94491BpeSegment_8005._8005967c;
import static legend.game.Scus94491BpeSegment_8005._80059b3c;
import static legend.game.Scus94491BpeSegment_8005._80059f3c;
import static legend.game.Scus94491BpeSegment_8005._80059f7c;
import static legend.game.Scus94491BpeSegment_8005.atanTable_80058d0c;
import static legend.game.Scus94491BpeSegment_8005.sin_cos_80054d0c;
import static legend.game.Scus94491BpeSegment_8005.sssqFadeCurrent_8005a1ce;
import static legend.game.Scus94491BpeSegment_8005.sssqStatus_8005a1d0;
import static legend.game.Scus94491BpeSegment_800c._800c3a40;
import static legend.game.Scus94491BpeSegment_800c._800c4aa8;
import static legend.game.Scus94491BpeSegment_800c._800c4aac;
import static legend.game.Scus94491BpeSegment_800c._800c4ab0;
import static legend.game.Scus94491BpeSegment_800c._800c4ab4;
import static legend.game.Scus94491BpeSegment_800c._800c4ab8;
import static legend.game.Scus94491BpeSegment_800c._800c4abc;
import static legend.game.Scus94491BpeSegment_800c._800c4ac8;
import static legend.game.Scus94491BpeSegment_800c._800c6630;
import static legend.game.Scus94491BpeSegment_800c._800c6674;
import static legend.game.Scus94491BpeSegment_800c.playableSoundPtrArr_800c43d0;
import static legend.game.Scus94491BpeSegment_800c.spuDmaCompleteCallback_800c6628;
import static legend.game.Scus94491BpeSegment_800c.sshd10Ptr_800c6678;
import static legend.game.Scus94491BpeSegment_800c.sshdPtr_800c4ac0;
import static legend.game.Scus94491BpeSegment_800c.sssqDataPointer_800c6680;
import static legend.game.Scus94491BpeSegment_800c.sssqPtr_800c4aa4;
import static legend.game.Scus94491BpeSegment_800c.sssqPtr_800c667c;
import static legend.game.Scus94491BpeSegment_800c.voicePtr_800c4ac4;

public final class Scus94491BpeSegment_8004 {
  private Scus94491BpeSegment_8004() { }

  private static final Logger LOGGER = LogManager.getFormatterLogger(Scus94491BpeSegment_8004.class);

  public static final UnboundedArrayRef<FileEntry08> overlays_8004db88 = MEMORY.ref(2, 0x8004db88L, UnboundedArrayRef.of(0x8, FileEntry08::new));

  /**
   * <ol start="0">
   *   <li>{@link Scus94491BpeSegment_800e#preload()}</li>
   *   <li>{@link Scus94491BpeSegment_800e#finalizePregameLoading()}</li>
   *   <li>{@link Ttle#executeTtleLoadingStage()}</li>
   *   <li>{@link Ttle#executeTtleUnloadingStage()}</li>
   *   <li>0x800eaa88 (TODO)</li>
   *   <li>{@link SMap#executeSmapLoadingStage()} Sets up rendering and loads scene</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80018658()}</li>
   *   <li>{@link Ttle#FUN_800c75fc()}</li>
   *   <li>{@link WMap#FUN_800cc738()}</li>
   *   <li>{@link SMap#startFmvLoadingStage()}</li>
   *   <li>{@link SMap#swapDiskLoadingStage()}</li>
   *   <li>{@link SMap#FUN_800d9e08()}</li>
   *   <li>0x800c6eb8 (TODO)</li>
   *   <li>0x800cab8c (TODO)</li>
   *   <li>null</li>
   *   <li>0x800c6978 (TODO)</li>
   *   <li>null</li>
   *   <li>0x800cdcdc (TODO)</li>
   *   <li>0x800cabd4 (TODO)</li>
   *   <li>null</li>
   * </ol>
   */
  public static final ArrayRef<CallbackStruct> gameStateCallbacks_8004dbc0 = MEMORY.ref(4, 0x8004dbc0L, ArrayRef.of(CallbackStruct.class, 20, 0x10, CallbackStruct::new));

  public static final IntRef _8004dd00 = MEMORY.ref(4, 0x8004dd00L, IntRef::new);
  public static final Pointer<FileEntry08> currentlyLoadingFileEntry_8004dd04 = MEMORY.ref(4, 0x8004dd04L, Pointer.deferred(4, FileEntry08::new));
  public static final Value loadingGameStateOverlay_8004dd08 = MEMORY.ref(4, 0x8004dd08L);
  public static final Value _8004dd0c = MEMORY.ref(4, 0x8004dd0cL);
  public static int loadedOverlayIndex_8004dd10;
  public static int overlaysLoadedCount_8004dd1c;
  public static boolean loadingOverlay_8004dd1e;

  /**
   * <ol>
   *   <li value="5">SMAP</li>
   *   <li value="6">Combat</li>
   *   <li value="8">WMAP</li>
   * </ol>
   */
  public static final Value mainCallbackIndex_8004dd20 = MEMORY.ref(4, 0x8004dd20L);
  /** When the overlay finishes loading, switch to this */
  public static final Value mainCallbackIndexOnceLoaded_8004dd24 = MEMORY.ref(4, 0x8004dd24L);
  /** The previous index before the file finished loading */
  public static final Value previousMainCallbackIndex_8004dd28 = MEMORY.ref(4, 0x8004dd28L);

  public static final Value _8004dd30 = MEMORY.ref(4, 0x8004dd30L);
  public static final Value width_8004dd34 = MEMORY.ref(2, 0x8004dd34L);

  public static final UnsignedShortRef reinitOrderingTableBits_8004dd38 = MEMORY.ref(2, 0x8004dd38L, UnsignedShortRef::new);

  public static final Pointer<RunnableRef> syncFrame_8004dd3c = MEMORY.ref(4, 0x8004dd3cL, Pointer.of(4, RunnableRef::new));
  public static final Pointer<RunnableRef> swapDisplayBuffer_8004dd40 = MEMORY.ref(4, 0x8004dd40L, Pointer.of(4, RunnableRef::new));
  public static final Value simpleRandSeed_8004dd44 = MEMORY.ref(4, 0x8004dd44L);
  public static final Value _8004dd48 = MEMORY.ref(2, 0x8004dd48L);

  /** The current disk number, 1-indexed */
  public static final IntRef diskNum_8004ddc0 = MEMORY.ref(4, 0x8004ddc0L, IntRef::new);

  public static final BoolRef preloadingAudioAssets_8004ddcc = MEMORY.ref(1, 0x8004ddccL, BoolRef::new);

  public static final IntRef scriptStateUpperBound_8004de4c = MEMORY.ref(4, 0x8004de4cL, IntRef::new);

  /**
   * Table of pointers to variables accessible by scripting engine
   *
   * <ol start="0">
   *   <li>{@link legend.game.Scus94491BpeSegment_8004#mainCallbackIndex_8004dd20}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#pregameLoadingStage_800bb10c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#tickCount_800bb0fc}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bee90}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bee94}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#gold_94}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_08}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8007#_8007a3a8}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bb104}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800babc0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bb168}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#scriptEffect_800bb140#red0_20}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#scriptEffect_800bb140#green0_1c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#scriptEffect_800bb140#blue0_14}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#scriptEffect_800bb140#red1_18}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#scriptEffect_800bb140#green1_10}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#scriptEffect_800bb140#blue1_0c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charIndex_88}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#chapterIndex_98}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#stardust_9c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#timestamp_a0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#submapScene_a4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#submapCut_a8}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#submapScene_a4} (duplicate, not 0xac)</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_b0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8007#vsyncMode_8007a3b8}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bee98}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800beebc}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bee9c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800beea4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800beeac}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800beeb4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#bobjIndices_e0c}</li>
   *   <li>{@link legend.game.combat.Bttl_800c#_800c66d0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#bobjIndices_e40}</li>
   *   <li>{@link Bttl_800c#charCount_800c677c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#bobjIndices_e50}</li>
   *   <li>{@link Bttl_800c#monsterCount_800c6768}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#morphMode_ee4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#partyPermutation_eec}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#itemsDroppedByEnemiesCount_800bc978}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#itemsDroppedByEnemies_800bc928}</li>
   *   <li>{@link Bttl_800c#_800c66bc}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#encounterId_800bb0f8}</li>
   *   <li>{@link Bttl_800c#_800c6748}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#_180}</li>
   *   <li>{@link Bttl_800c#_800c6718}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#combatStage_800bb0f4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#bobjIndices_e78}</li>
   *   <li>{@link Bttl_800c#_800c669c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#bobjIndices_eac}</li>
   *   <li>{@link Bttl_800c#_800c6760}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#bobjIndices_ebc}</li>
   *   <li>{@link Bttl_800c#enemyCount_800c6758}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#_ef0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_b4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_b8}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bc974}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bc960}</li>
   *   <li>{@link legend.game.combat.Bttl_800c#_800c66c8}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#goldGainedFromCombat_800bc920}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#totalXpFromCombat_800bc95c}</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>{@link legend.game.SMap#wobjIndices_800c6880}</li>
   *   <li>{@link legend.game.SMap#submapScriptIndex_800c6740}</li>
   *   <li>{@link legend.game.SMap#wobjCount_800c6730}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bd7b0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#_800bda08}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8005#submapCut_80052c30}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8005#submapScene_80052c34}</li>
   *   <li>{@link legend.game.SMap#_800cb44c}</li>
   *   <li>{@link legend.game.SMap#encounterAccumulator_800c6ae8}</li>
   *   <li>{@link legend.game.SMap#_800c6970}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8004#_8004de54}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8004#_8004de50}</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>{@link Bttl_800c#_800c6914}</li>
   *   <li>{@link Bttl_800c#_800c6918}</li>
   *   <li>{@link Bttl_800c#_800c67c8}</li>
   *   <li>{@link Bttl_800c#_800c67cc}</li>
   *   <li>{@link Bttl_800c#_800c67d0}</li>
   *   <li>{@link Bttl_800c#_800c6710}</li>
   *   <li>{@link Bttl_800c#_800c6780}</li>
   *   <li>{@link Bttl_800c#_800c66a8}</li>
   *   <li>{@link Bttl_800c#_800c6700}</li>
   *   <li>{@link Bttl_800c#_800c6704}</li>
   *   <li>{@link Bttl_800c#_800c66b0}</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>{@link Bttl_800c#_800c6754}</li>
   *   <li>{@link Bttl_800c#currentStage_800c66a4}</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>{@link Bttl_800c#_800c6764}</li>
   *   <li>{@link Bttl_800c#_800c6774}</li>
   *   <li>{@link Bttl_800c#_800c6778}</li>
   *   <li>{@link Bttl_800c#_800c676c}</li>
   *   <li>{@link Bttl_800c#_800c6770}</li>
   *   <li>{@link Bttl_800c#mcqColour_800fa6dc}</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_15c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_17c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#dragoonSpirits_19c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(0)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(1)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(2)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(3)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(4)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(5)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(6)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(7)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#charData_32c(8)#partyFlags_04}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8005#standingInSavePoint_8005a368}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8007#shopId_8007a3b4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_1a4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_800b#gameState_800babc8#_1c4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(0)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(1)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(2)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(3)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(4)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(5)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(6)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(7)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(8)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment_8006#_8006e398#specialEffect_20(9)}</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   *   <li>null</li>
   * </ol>
   */
  public static final ArrayRef<Pointer<IntRef>> scriptPtrs_8004de58 = MEMORY.ref(4, 0x8004de58L, ArrayRef.of(Pointer.classFor(IntRef.class), 0x90, 4, Pointer.deferred(4, IntRef::new)));

  /**
   * This is the world's largest jump table
   *
   * <ol start="0">
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptPause}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptRewindAndPause}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptWait}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptCompare}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptCompare0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptMove}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80016790}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptMemCopy}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptSetZero}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptAnd}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptOr(RunningScript)}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptXor}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptAndOr}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNot}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptShiftLeft}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptShiftRightArithmetic}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptAdd}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptSubtract}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptSubtract2}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptIncrementBy1}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptDecrementBy1}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNegate}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptAbs}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptMultiply}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptDivide}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptDivide2}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptMod}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptMod2}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80016b2c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80016b5c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80016b8c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptMod}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptMod2}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptSquareRoot}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80016c00}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptSin}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptCos}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptRatan2}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptExecuteSubFunc}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptJump}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptConditionalJump}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptConditionalJump0}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80016dec}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80016e1c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptJumpAndLink}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptJumpReturn}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptJumpAndLinkTable}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptDeallocateSelf}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptDeallocateChildren}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptDeallocateOther}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptForkAndJump}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptForkAndReenter}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptConsumeChild}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_800172f4}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_800172fc}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_80017304}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#FUN_8001730c}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   *   <li>{@link legend.game.Scus94491BpeSegment#scriptNotImplemented}</li>
   * </ol>
   */
  public static final ArrayRef<Pointer<FunctionRef<RunningScript, Long>>> scriptFunctions_8004e098 = MEMORY.ref(4, 0x8004e098L, ArrayRef.of(Pointer.classFor(FunctionRef.classFor(RunningScript.class, Long.class)), 0x81, 4, Pointer.of(4, FunctionRef::new)));

  /**
   * <p>Actually this is</p>
   *
   * <p>All methods that are skipped are {@link Scus94491BpeSegment#scriptRewindAndPause2}</p>
   *
   * <p>Many methods are copied into this table at runtime.</p>
   *
   * <ol start="0">
   *   <li>{@link Scus94491BpeSegment#FUN_80017354}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80017374}</li>
   *   <li>{@link Scus94491BpeSegment#scriptSetGlobalFlag1}</li>
   *   <li>{@link Scus94491BpeSegment#scriptReadGlobalFlag1}</li>
   *   <li>{@link Scus94491BpeSegment#scriptSetGlobalFlag2}</li>
   *   <li>{@link Scus94491BpeSegment#scriptReadGlobalFlag2}</li>
   *   <li>{@link Scus94491BpeSegment#scriptStartEffect}</li>
   *   <li>{@link Scus94491BpeSegment#scriptWaitForFilesToLoad}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80017584}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_800175b4}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80017648}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80017688}</li>
   *   <li>{@link SMap#FUN_800d9bc0}</li>
   *   <li>{@link SMap#FUN_800d9bf4}</li>
   *   <li>{@link SMap#FUN_800d9c1c}</li>
   *   <li>{@link SMap#scriptSetCharAddition}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_800176c0}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_800176ec}</li>
   *   <li>{@link SMap#scriptGetCharAddition}</li>
   *   <li>{@link SMap#FUN_800d9d60}</li>
   * </ol>
   * ...
   * <ol start="128">
   *   <li>{@link Bttl_800c#scriptSetBobjPos}</li>
   *   <li>{@link Bttl_800c#scriptGetBobjPos}</li>
   *   <li>{@link Bttl_800c#scriptSetBobjRotation}</li>
   *   <li>{@link Bttl_800c#FUN_800cc7d8}</li>
   *   <li>{@link Bttl_800c#scriptSetBobjRotationY}</li>
   *   <li>{@link Bttl_800c#FUN_800cc948}</li>
   *   <li>{@link Bttl_800c#scriptGetBobjRotation}</li>
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   *   <li>{@link Bttl_800c#scriptGetMonsterStatusResistFlags}</li>
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   *   <li>{@link Bttl_800c#FUN_800cb618}</li>
   *   <li>{@link Bttl_800c#FUN_800cb674}</li>
   *   <li>{@link Bttl_800c#FUN_800cb6bc}</li>
   *   <li>{@link Bttl_800c#FUN_800cb764}</li>
   *   <li>{@link Bttl_800c#FUN_800cb76c}</li>
   *   <li>{@link Bttl_800c#FUN_800cb9b0}</li>
   *   <li>{@link Bttl_800c#FUN_800cb9f0}</li>
   *   <li>{@link Bttl_800c#FUN_800cba28}</li>
   *   <li>{@link Bttl_800c#FUN_800cba60}</li>
   *   <li>{@link Bttl_800c#FUN_800cbabc}</li>
   *   <li>{@link Bttl_800c#FUN_800cbb00}</li>
   *   <li>{@link Bttl_800c#FUN_800cbc14}</li>
   *   <li>{@link Bttl_800c#FUN_800cbde0}</li>
   *   <li>{@link Bttl_800c#FUN_800cbef8}</li>
   *   <li>{@link Bttl_800c#FUN_800cc0c8}</li>
   *   <li>{@link Bttl_800c#FUN_800cc1cc}</li>
   *   <li>{@link Bttl_800c#FUN_800cc364}</li>
   *   <li>{@link Bttl_800c#FUN_800cc46c}</li>
   *   <li>{@link Bttl_800c#FUN_800cc608}</li>
   *   <li>{@link Bttl_800c#FUN_800cc698}</li>
   *   <li>{@link Bttl_800c#FUN_800cc784}</li>
   *   <li>{@link Bttl_800c#FUN_800cc8f4}</li>
   *   <li>{@link Bttl_800c#FUN_800cca34}</li>
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   *   <li>{@link Bttl_800c#scriptRenderDamage}</li>
   *   <li>{@link Bttl_800c#FUN_800ccb70}</li>
   *   <li>{@link Bttl_800c#FUN_800ccba4}</li>
   *   <li>{@link Bttl_800c#FUN_800cccf4}</li>
   *   <li>{@link Bttl_800c#FUN_800ccd34}</li>
   *   <li>{@link Bttl_800c#scriptGetStat}</li>
   *   <li>{@link Bttl_800c#FUN_800ccf0c}</li>
   *   <li>{@link Bttl_800c#FUN_800ccec8}</li>
   *   <li>{@link Bttl_800c#FUN_800ccef8}</li>
   *   <li>{@link Bttl_800c#FUN_800ccf2c}</li>
   *   <li>{@link Bttl_800c#FUN_800cd0ec}</li>
   *   <li>{@link Bttl_800c#FUN_800cd078}</li>
   *   <li>{@link Bttl_800c#levelUpAddition}</li>
   *   <li>{@link Bttl_800c#FUN_800cce70}</li>
   *   <li>{@link Bttl_800c#scriptSetStat}</li>
   * </ol>
   * ...
   * <ol start="320">
   *   <li>{@link Bttl_800c#FUN_800cc9d8}</li>
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   *   <li>{@link Bttl_800c#FUN_800cb84c}</li>
   *   <li>{@link Bttl_800c#FUN_800cb95c}</li>
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   *   <li value="352">{@link Bttl_800c#FUN_800cd3b4}</li>
   *   <li>{@link Bttl_800e#scriptCopyVram}</li>
   *   <li>{@link Bttl_800c#FUN_800cd468}</li>
   *   <li>{@link Bttl_800c#FUN_800cd4b0}</li>
   *   <li>{@link Bttl_800c#FUN_800cd4f0}</li>
   *   <li>{@link Bttl_800c#scriptAddCombatant}</li>
   *   <li>{@link Bttl_800c#scriptDeallocateAndClearCombatant}</li>
   *   <li>{@link Bttl_800c#FUN_800cda78}</li>
   *   <li>{@link Bttl_800c#FUN_800cd5b4}</li>
   *   <li>{@link Bttl_800c#FUN_800cd740}</li>
   *   <li>{@link Bttl_800c#FUN_800cd7a8}</li>
   *   <li>{@link Bttl_800c#FUN_800cd810}</li>
   *   <li>{@link Bttl_800c#FUN_800cd8a4}</li>
   *   <li>{@link Bttl_800c#scriptGetBobjNobj}</li>
   *   <li>{@link Bttl_800c#scriptDeallocateCombatant}</li>
   *   <li>{@link Bttl_800c#FUN_800cdb18}</li>
   *   <li>{@link Bttl_800c#scriptLoadStage}</li>
   *   <li>{@link Bttl_800c#FUN_800cd910}</li>
   *   <li>{@link Bttl_800c#scriptGetCombatantIndex}</li>
   *   <li>{@link Bttl_800c#FUN_800cd998}</li>
   *   <li>{@link Bttl_800c#FUN_800cdb74}</li>
   * </ol>
   * ...
   * <ol start="1023">
   *   <li>{@link Scus94491BpeSegment#scriptRewindAndPause2}</li>
   * </ol>
   */
  public static final ArrayRef<Pointer<FunctionRef<RunningScript, Long>>> scriptSubFunctions_8004e29c = MEMORY.ref(4, 0x8004e29cL, ArrayRef.of(Pointer.classFor(FunctionRef.classFor(RunningScript.class, Long.class)), 0x3ff, 4, Pointer.of(4, FunctionRef::new)));
  // 8004f29c end of jump table

  public static final Value _8004f2a8 = MEMORY.ref(4, 0x8004f2a8L);
  public static final ArrayRef<ItemStats0c> itemStats_8004f2ac = MEMORY.ref(1, 0x8004f2acL, ArrayRef.of(ItemStats0c.class, 0x40, 0xc, ItemStats0c::new));
  public static final ArrayRef<ShortRef> additionOffsets_8004f5ac = MEMORY.ref(2, 0x8004f5acL, ArrayRef.of(ShortRef.class, 10, 0x2, ShortRef::new));
  public static final ArrayRef<ShortRef> additionCounts_8004f5c0 = MEMORY.ref(2, 0x8004f5c0L, ArrayRef.of(ShortRef.class, 10, 0x2, ShortRef::new));
  /**
   * <ol start="0">
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#FUN_800c7524}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#FUN_800c7648}</li>
   *   <li>{@link Bttl_800c#FUN_800c76a0}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80018998}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80018998}</li>
   *   <li>{@link Bttl_800c#FUN_800c772c}</li>
   *   <li>{@link Bttl_800c#deferAllocateEnemyBattleObjects()}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#deferAllocatePlayerBattleObjects()}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#deferLoadEncounterAssets}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#FUN_800c7964}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#FUN_800c79f0}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#deferDoNothing}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#FUN_800c7a80}</li>
   *   <li>{@link Bttl_800c#FUN_800c7bb8}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Bttl_800c#FUN_800c8068}</li>
   *   <li>{@link Bttl_800c#deallocateCombat}</li>
   *   <li>{@link Scus94491BpeSegment#waitForFilesToLoad}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80018998}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_80018508}</li>
   *   <li>{@link Scus94491BpeSegment#FUN_800189b0}</li>
   * </ol>
   */
  public static final ArrayRef<Pointer<RunnableRef>> _8004f5d4 = MEMORY.ref(4, 0x8004f5d4L, ArrayRef.of(Pointer.classFor(RunnableRef.class), 31, 4, Pointer.deferred(4, RunnableRef::new)));

  public static final ScriptFile doNothingScript_8004f650 = MEMORY.ref(4, 0x8004f650L, ScriptFile::new);
  public static final Value _8004f658 = MEMORY.ref(4, 0x8004f658L);

  public static final Value _8004f6a4 = MEMORY.ref(4, 0x8004f6a4L);

  public static final Value _8004f6e4 = MEMORY.ref(4, 0x8004f6e4L);
  public static final Value _8004f6e8 = MEMORY.ref(4, 0x8004f6e8L);
  public static final Value _8004f6ec = MEMORY.ref(4, 0x8004f6ecL);

  public static final ArrayRef<SubmapMusic08> _8004fa98 = MEMORY.ref(1, 0x8004fa98L, ArrayRef.of(SubmapMusic08.class, 13, 8, SubmapMusic08::new));
  public static final ArrayRef<SubmapMusic08> _8004fb00 = MEMORY.ref(1, 0x8004fb00L, ArrayRef.of(SubmapMusic08.class, 130, 8, SubmapMusic08::new));
  public static final ArrayRef<MoonMusic08> moonMusic_8004ff10 = MEMORY.ref(4, 0x8004ff10L, ArrayRef.of(MoonMusic08.class, 43, 8, MoonMusic08::new));

  /** TODO This is probably one of the RotMatrix* methods */
  @Method(0x80040010L)
  public static MATRIX RotMatrix_80040010(final SVECTOR r, final MATRIX matrix) {
    long t8;
    long t9;

    final int x = r.getX();
    final short sinX;
    if(x < 0) {
      //LAB_8004002c
      t9 = sin_cos_80054d0c.offset((-x & 0xfff) * 0x4L).get();
      sinX = (short)-(short)t9;
    } else {
      //LAB_80040054
      t9 = sin_cos_80054d0c.offset((x & 0xfff) * 0x4L).get();
      sinX = (short)t9;
    }

    final short cosX = (short)((int)t9 >> 16);

    //LAB_80040074
    final int y = r.getY();
    final short sinYP;
    final short sinYN;
    if(y < 0) {
      //LAB_80040090
      t9 = sin_cos_80054d0c.offset((-y & 0xfff) * 0x4L).get();
      sinYP = (short)-(short)t9;
      sinYN = (short)t9;
    } else {
      //LAB_800400b8
      t9 = sin_cos_80054d0c.offset((y & 0xfff) * 0x4L).get();
      sinYP = (short)t9;
      sinYN = (short)-(short)t9;
    }

    final short cosY = (short)((int)t9 >> 16);

    //LAB_800400dc
    matrix.set(2, 0, sinYN);
    matrix.set(2, 1, (short)(sinX * cosY >> 12));
    matrix.set(2, 2, (short)(cosX * cosY >> 12));

    final int z = r.getZ();
    final short sinZ;
    if(z < 0) {
      //LAB_8004011c
      t9 = sin_cos_80054d0c.offset((-z & 0xfff) * 0x4L).get();
      sinZ = (short)-(short)t9;
    } else {
      //LAB_80040144
      t9 = sin_cos_80054d0c.offset((z & 0xfff) * 0x4L).get();
      sinZ = (short)t9;
    }

    final short cosZ = (short)((int)t9 >> 16);

    //LAB_80040170
    matrix.set(0, 0, (short)(cosY * cosZ >> 12));
    matrix.set(1, 0, (short)(sinZ * cosY >> 12));
    t8 = sinX * sinYP >> 12;
    matrix.set(0, 1, (short)((t8 * cosZ >> 12) - (sinZ * cosX >> 12)));
    matrix.set(1, 1, (short)((t8 * sinZ >> 12) + (cosX * cosZ >> 12)));
    t8 = sinYP * cosX >> 12;
    matrix.set(0, 2, (short)((t8 * cosZ >> 12) + (sinX * sinZ >> 12)));
    matrix.set(1, 2, (short)((t8 * sinZ >> 12) - (sinX * cosZ >> 12)));

    return matrix;
  }

  @Method(0x800402a0L)
  public static void RotMatrixX(final long r, final MATRIX matrix) {
    final int sinCos;
    final short sin;

    if(r < 0) {
      //LAB_800402bc
      sinCos = (int)sin_cos_80054d0c.offset((-r & 0xfffL) * 4).get();
      sin = (short)-(short)sinCos;
    } else {
      //LAB_800402e4
      sinCos = (int)sin_cos_80054d0c.offset((r & 0xfffL) * 4).get();
      sin = (short)sinCos;
    }

    final short cos = (short)(sinCos >> 16);

    //LAB_80040304
    final long m10 = matrix.get(1, 0);
    final long m11 = matrix.get(1, 1);
    final long m12 = matrix.get(1, 2);
    final long m20 = matrix.get(2, 0);
    final long m21 = matrix.get(2, 1);
    final long m22 = matrix.get(2, 2);

    matrix.set(1, 0, (short)(cos * m10 - sin * m20 >> 12));
    matrix.set(1, 1, (short)(cos * m11 - sin * m21 >> 12));
    matrix.set(1, 2, (short)(cos * m12 - sin * m22 >> 12));
    matrix.set(2, 0, (short)(sin * m10 + cos * m20 >> 12));
    matrix.set(2, 1, (short)(sin * m11 + cos * m21 >> 12));
    matrix.set(2, 2, (short)(sin * m12 + cos * m22 >> 12));
  }

  @Method(0x80040440L)
  public static void RotMatrixY(final long r, final MATRIX matrix) {
    final int sinCos;
    final short sin;

    if(r < 0) {
      //LAB_8004045c
      sinCos = (int)sin_cos_80054d0c.offset((-r & 0xfff) * 4).get();
      sin = (short)sinCos;
    } else {
      //LAB_80040480
      sinCos = (int)sin_cos_80054d0c.offset((r & 0xfffL) * 4).get();
      sin = (short)-(short)sinCos;
    }

    final short cos = (short)(sinCos >> 16);

    //LAB_800404a4
    final short m0 = matrix.get(0);
    final short m1 = matrix.get(1);
    final short m2 = matrix.get(2);
    final short m6 = matrix.get(6);
    final short m7 = matrix.get(7);
    final short m8 = matrix.get(8);
    matrix.set(0, (short)(cos * m0 - sin * m6 >> 12));
    matrix.set(1, (short)(cos * m1 - sin * m7 >> 12));
    matrix.set(2, (short)(cos * m2 - sin * m8 >> 12));
    matrix.set(6, (short)(sin * m0 + cos * m6 >> 12));
    matrix.set(7, (short)(sin * m1 + cos * m7 >> 12));
    matrix.set(8, (short)(sin * m2 + cos * m8 >> 12));
  }

  @Method(0x800405e0L)
  public static void RotMatrixZ(final long r, final MATRIX matrix) {
    final int sinCos;
    final short sin;

    if(r < 0) {
      //LAB_800405fc
      sinCos = (int)sin_cos_80054d0c.offset((-r & 0xfff) * 4).get();
      sin = (short)-(short)sinCos;
    } else {
      //LAB_80040624
      sinCos = (int)sin_cos_80054d0c.offset((r & 0xfff) * 4).get();
      sin = (short)sinCos;
    }

    final short cos = (short)(sinCos >> 16);

    //LAB_80040644
    final long m00 = matrix.get(0, 0);
    final long m01 = matrix.get(0, 1);
    final long m02 = matrix.get(0, 2);
    final long m10 = matrix.get(1, 0);
    final long m11 = matrix.get(1, 1);
    final long m12 = matrix.get(1, 2);

    matrix.set(0, 0, (short)(cos * m00 - sin * m10 >> 12));
    matrix.set(0, 1, (short)(cos * m01 - sin * m11 >> 12));
    matrix.set(0, 2, (short)(cos * m02 - sin * m12 >> 12));
    matrix.set(1, 0, (short)(sin * m00 + cos * m10 >> 12));
    matrix.set(1, 1, (short)(sin * m01 + cos * m11 >> 12));
    matrix.set(1, 2, (short)(sin * m02 + cos * m12 >> 12));
  }

  /** TODO RotMatrix_gte? */
  @Method(0x80040780L)
  public static void RotMatrix_80040780(final SVECTOR vector, final MATRIX matrix) {
    final int x = vector.getX();
    final int y = vector.getY();
    final int z = vector.getZ();

    final int signX = x >> 31;
    final int signY = y >> 31;
    final int signZ = z >> 31;

    final int sinCosX = (int)sin_cos_80054d0c.offset((x + signX ^ signX) * 0x4L & 0x3ffcL).get();
    final int sinCosY = (int)sin_cos_80054d0c.offset((y + signY ^ signY) * 0x4L & 0x3ffcL).get();
    final int sinCosZ = (int)sin_cos_80054d0c.offset((z + signZ ^ signZ) * 0x4L & 0x3ffcL).get();

    final short sinX = (short)(((sinCosX << 16) + signX ^ signX) >>> 16);
    final short sinY = (short)(((sinCosY << 16) + signY ^ signY) >>> 16);
    final short sinZ = (short)(((sinCosZ << 16) + signZ ^ signZ) >>> 16);

    final short cosX = (short)(sinCosX >> 16);
    final short cosY = (short)(sinCosY >> 16);
    final short cosZ = (short)(sinCosZ >> 16);

    CPU.MTC2(cosX,  8);
    CPU.MTC2(sinY,  9);
    CPU.MTC2(sinZ, 10);
    CPU.MTC2(cosZ, 11);
    CPU.COP2(0x198003dL);
    final long t0 = CPU.MFC2( 9);
    final long t1 = CPU.MFC2(10);
    final long t2 = CPU.MFC2(11);
    CPU.MTC2(sinX,  8);
    CPU.MTC2(sinY,  9);
    CPU.MTC2(sinZ, 10);
    CPU.MTC2(cosZ, 11);
    CPU.COP2(0x198003dL);
    final long t3 = CPU.MFC2( 9);
    final long t4 = CPU.MFC2(10);
    final long t5 = CPU.MFC2(11);
    CPU.MTC2(cosZ, 8);
    CPU.MTC2(cosY, 9);
    CPU.MTC2(t3, 10);
    CPU.MTC2(t0, 11);
    CPU.COP2(0x198003dL);
    final long a0 = CPU.MFC2( 9);
    final long a1 = CPU.MFC2(10);
    final long a2 = CPU.MFC2(11);
    CPU.MTC2(cosY, 9);
    CPU.MTC2(sinZ, 8);
    CPU.MTC2(t3, 10);
    CPU.MTC2(t0, 11);
    CPU.COP2(0x198003dL);
    matrix.set(0, (short)a0);
    matrix.set(1, (short)(a1 - t1));
    matrix.set(2, (short)(a2 + t4));
    matrix.set(3, (short)CPU.MFC2( 9));
    matrix.set(4, (short)(CPU.MFC2(10) + t2));
    matrix.set(5, (short)(CPU.MFC2(11) - t5));
    matrix.set(6, (short)-sinY);
    matrix.set(7, (short)(cosY * sinX >> 12));
    matrix.set(8, (short)(cosY * cosX >> 12));
  }

  @Method(0x80040980L)
  public static void FUN_80040980(final SVECTOR a0, final MATRIX v0) {
    long at;
    long v1;
    long a1;
    long a2;
    long a3;
    long t0;
    long t1;
    long t2;
    long t3;
    long t4;
    long t5;
    long t6;
    final long t7;
    long lo;
    t0 = a0.getZ();
    v1 = 0x8005_0000L;
    v1 = v1 + 0x4d0cL;
    t4 = a0.getXY();
    t3 = (int)t0 >> 31;
    t0 = t0 + t3;
    t0 = t0 ^ t3;
    t0 = t0 << 2;
    t0 = t0 & 0x3ffcL;
    t0 = t0 + v1;
    a2 = MEMORY.ref(4, t0).offset(0x0L).get();
    t0 = (int)t4 >> 16;
    t2 = (int)t0 >> 31;
    t0 = t0 + t2;
    t0 = t0 ^ t2;
    t0 = t0 << 2;
    t0 = t0 & 0x3ffcL;
    t0 = t0 + v1;
    a1 = MEMORY.ref(4, t0).offset(0x0L).get();
    t0 = t4 << 16;
    t0 = (int)t0 >> 16;
    t1 = (int)t0 >> 31;
    t0 = t0 + t1;
    t0 = t0 ^ t1;
    t0 = t0 << 2;
    t0 = t0 & 0x3ffcL;
    t0 = t0 + v1;
    long a0_0 = MEMORY.ref(4, t0).offset(0x0L).get();
    at = a2 << 16;
    a2 = (int)a2 >> 16;
    a2 = a2 << 16;
    at = at + t3;
    at = at ^ t3;
    at = at >>> 16;
    a2 = a2 | at;
    at = a1 << 16;
    a1 = (int)a1 >> 16;
    a1 = a1 << 16;
    at = at + t2;
    at = at ^ t2;
    at = at >>> 16;
    a1 = a1 | at;
    at = a0_0 << 16;
    a0_0 = (int)a0_0 >> 16;
    a0_0 = a0_0 << 16;
    at = at + t1;
    at = at ^ t1;
    at = at >>> 16;
    a0_0 = a0_0 | at;
    t0 = (int)a0_0 >> 16;
    CPU.MTC2(t0, 8);
    a3 = a1 << 16;
    a3 = (int)a3 >> 16;
    CPU.MTC2(a3, 9);
    v1 = a2 << 16;
    v1 = (int)v1 >> 16;
    CPU.MTC2(v1, 10);
    at = (int)a2 >> 16;
    CPU.MTC2(at, 11);
    CPU.COP2(0x198003dL);
    at = (int)a1 >> 16;
    lo = (long)(int)at * (int)t0 & 0xffff_ffffL;
    t0 = CPU.MFC2(9);
    t1 = CPU.MFC2(10);
    t6 = a0_0 << 16;
    t2 = CPU.MFC2(11);
    t6 = (int)t6 >> 16;
    CPU.MTC2(t6, 8);
    CPU.MTC2(a3, 9);
    CPU.MTC2(v1, 10);
    at = (int)a2 >> 16;
    CPU.MTC2(at, 11);
    CPU.COP2(0x198003dL);
    at = lo;
    at = (int)at >> 12;
    v0.set(8, (short)at);
    t3 = CPU.MFC2(9);
    t4 = CPU.MFC2(10);
    t5 = CPU.MFC2(11);
    at = (int)a2 >> 16;
    CPU.MTC2(at, 8);
    at = (int)a1 >> 16;
    CPU.MTC2(at, 9);
    lo = (long)(int)at * (int)t6 & 0xffff_ffffL;
    CPU.MTC2(t3, 10);
    CPU.MTC2(t0, 11);
    CPU.COP2(0x198003dL);
    a0_0 = CPU.MFC2(9);
    a1 = CPU.MFC2(10);
    a2 = CPU.MFC2(11);
    CPU.MTC2(v1, 8);
    CPU.MTC2(at, 9);
    CPU.MTC2(t3, 10);
    CPU.MTC2(t0, 11);
    CPU.COP2(0x198003dL);
    t1 = a1 + t1;
    t1 = t1 << 16;
    a3 = a3 & 0xffffL;
    t1 = t1 | a3;
    v0.setPacked(2, t1);
    at = CPU.MFC2(9);
    a0_0 = a0_0 & 0xffffL;
    at = -at;
    at = at << 16;
    at = at | a0_0;
    v0.setPacked(0, at);
    t6 = CPU.MFC2(10);
    at = lo;
    t7 = CPU.MFC2(11);
    t2 = t2 - t6;
    at = (int)at >> 12;
    at = -at;
    t2 = t2 & 0xffffL;
    at = at << 16;
    at = at | t2;
    v0.setPacked(4, at);
    t4 = t4 - a2;
    t4 = t4 & 0xffffL;
    t5 = t5 + t7;
    t5 = t5 << 16;
    t4 = t4 | t5;
    v0.setPacked(6, t4);
  }

  /**
   * Uses PlayStation format (4096 = 360 degrees = 2pi) to finish the x/y arctan function (-180 degrees and +180 degrees, -pi...pi).
   *
   *  1-bit sign
   * 19-bit whole
   * 12-bit decimal
   */
  @Method(0x80040b90L)
  public static int ratan2(int x, int y) {
    boolean negativeY = false;
    boolean negativeX = false;

    if(y < 0) {
      negativeY = true;
      y = -y;
    }

    //LAB_80040ba4
    if(x < 0) {
      negativeX = true;
      x = -x;
    }

    //LAB_80040bb4
    if(y == 0 && x == 0) {
      return 0;
    }

    //LAB_80040bc8
    int atan;
    if(x < y) {
      if((x & 0x7fe0_0000) == 0) {
        //LAB_80040c10
        //LAB_80040c3c
        x = Math.floorDiv(x << 10, y);
      } else {
        x = Math.floorDiv(x, y >> 10);
      }

      //LAB_80040c44
      atan = (int)atanTable_80058d0c.offset(x * 0x2L).getSigned();
    } else {
      //LAB_80040c58
      if((y & 0x7fe0_0000) == 0) {
        //LAB_80040c98
        //LAB_80040cc4
        x = Math.floorDiv(y << 10, x);
      } else {
        //LAB_80040c8c
        x = Math.floorDiv(y, x >> 10);
      }

      //LAB_80040ccc
      atan = 0x400 - (int)atanTable_80058d0c.offset(x * 0x2L).getSigned();
    }

    //LAB_80040ce0
    if(negativeY) {
      atan = 0x800 - atan;
    }

    //LAB_80040cec
    if(negativeX) {
      return -atan;
    }

    //LAB_80040cfc
    return atan;
  }

  @Method(0x80040df0L)
  public static void FUN_80040df0(final VECTOR a0, final COLOUR in, final COLOUR out) {
    CPU.MTC2(a0.getX(), 0); // VXY0
    CPU.MTC2(a0.getY(), 1); // VZ0
    CPU.MTC2(in.pack(), 6); // RGBC
    CPU.COP2(0x108_041bL);
    out.unpack(CPU.MFC2(22)); // RGB FIFO
  }

  @Method(0x80040e10L)
  public static VECTOR FUN_80040e10(final VECTOR a0, final VECTOR a1) {
    CPU.MTC2(a0.getX(),  9);
    CPU.MTC2(a0.getY(), 10);
    CPU.MTC2(a0.getZ(), 11);
    CPU.COP2(0xa00428L);
    a1.setX((int)CPU.MFC2(25));
    a1.setY((int)CPU.MFC2(26));
    a1.setZ((int)CPU.MFC2(27));
    return a1;
  }

  @Method(0x80040e40L)
  public static void FUN_80040e40(final VECTOR a0, final VECTOR a1, final VECTOR out) {
    final long t5 = CPU.CFC2(0);
    final long t6 = CPU.CFC2(2);
    final long t7 = CPU.CFC2(4);
    CPU.CTC2(a0.getX(),  0);
    CPU.CTC2(a0.getY(),  2);
    CPU.CTC2(a0.getZ(),  4);
    CPU.MTC2(a1.getX(),  9);
    CPU.MTC2(a1.getY(), 10);
    CPU.MTC2(a1.getZ(), 11);
    CPU.COP2(0x178000cL);
    out.setX((int)CPU.MFC2(25));
    out.setY((int)CPU.MFC2(26));
    out.setZ((int)CPU.MFC2(27));
    CPU.CTC2(t5, 0);
    CPU.CTC2(t6, 2);
    CPU.CTC2(t7, 4);
  }

  @Method(0x80040ea0L)
  public static int Lzc(final int val) {
    CPU.MTC2(val, 30);
    return (int)CPU.MFC2(31);
  }

  /**
   * Transform vector a1 and store in vector a2. Matrix a0 is uploaded to GTE in transposed order.
   */
  @Method(0x80040ec0L)
  public static VECTOR ApplyTransposeMatrixLV(final MATRIX a0, final VECTOR a1, final VECTOR a2) {
    CPU.CTC2((a0.get(3) & 0xffffL) << 16 | a0.get(0) & 0xffffL, 0);
    CPU.CTC2((a0.get(1) & 0xffffL) << 16 | a0.get(6) & 0xffffL, 1);
    CPU.CTC2((a0.get(7) & 0xffffL) << 16 | a0.get(4) & 0xffffL, 2);
    CPU.CTC2((a0.get(5) & 0xffffL) << 16 | a0.get(2) & 0xffffL, 3);
    CPU.CTC2(a0.get(8), 4);

    long t0;
    long t3;
    if(a1.getX() < 0) {
      t0 = -a1.getX();
      t3 = (int)t0 >> 15;
      t3 = -t3;
      t0 = t0 & 0x7fffL;
      t0 = -t0;
    } else {
      //LAB_80040f54
      t3 = a1.getX() >> 15;
      t0 = a1.getX() & 0x7fffL;
    }

    //LAB_80040f5c
    long t1;
    long t4;
    if(a1.getY() < 0) {
      t1 = -a1.getY();
      t4 = (int)t1 >> 15;
      t4 = -t4;
      t1 = t1 & 0x7fffL;
      t1 = -t1;
    } else {
      //LAB_80040f7c
      t4 = a1.getY() >> 15;
      t1 = a1.getY() & 0x7fffL;
    }

    //LAB_80040f84
    long t2;
    long t5;
    if(a1.getZ() < 0) {
      t2 = -a1.getZ();
      t5 = (int)t2 >> 15;
      t5 = -t5;
      t2 = t2 & 0x7fffL;
      t2 = -t2;
    } else {
      //LAB_80040fa4
      t5 = a1.getZ() >> 15;
      t2 = a1.getZ() & 0x7fffL;
    }

    //LAB_80040fac
    CPU.MTC2((t4 & 0xffffL) << 16 | t3 & 0xffffL, 0);
    CPU.MTC2(t5, 1);

    CPU.COP2(0x406012L);
    t3 = CPU.MFC2(25);
    t4 = CPU.MFC2(26);
    t5 = CPU.MFC2(27);

    CPU.MTC2((t1 & 0xffffL) << 16 | t0 & 0xffffL, 0);
    CPU.MTC2(t2, 1);
    CPU.COP2(0x486012L);

    //LAB_80041008
    //LAB_8004100c
    //LAB_80041024
    //LAB_80041028
    //LAB_80041040
    //LAB_80041044
    a2.setX((int)(CPU.MFC2(25) + t3 * 0x8L));
    a2.setY((int)(CPU.MFC2(26) + t4 * 0x8L));
    a2.setZ((int)(CPU.MFC2(27) + t5 * 0x8L));
    return a2;
  }

  // Start of SPU code

  @Method(0x80045cb8L)
  public static void sssqTick() {
    sssqStatus(0x1L);
    FUN_8004a8b8();

    final SpuStruct44 spu44 = _800c6630;

    //LAB_80045d04
    for(spu44.channelIndex_01.set(0); spu44.channelIndex_01.get() < 24; spu44.channelIndex_01.incr()) {
      final SpuStruct124 spu124 = _800c4ac8.get(spu44.channelIndex_01.get());

      if(spu124._028.get() == 1 || spu124._02a.get() == 1) {
        //LAB_80045d24
        FUN_80047b38(spu44.channelIndex_01.get());

        LAB_80045d40:
        while(spu124._118.get() == 0) {
          sssqReadEvent(spu44.channelIndex_01.get());

          if(FUN_80047bd0(spu44.channelIndex_01.get()) == 0) {
            spu124.sssqOffset_00c.add(0x3L);
            spu44._04.set(0);
          } else {
            //LAB_80045d7c
            spu44._04.set(1);

            final int command = spu124.command_000.get() & 0xf0;
            if(command == 0x80) { // Key off event
              //LAB_80045fdc
              sssqHandleKeyOff(spu44.channelIndex_01.get());
              //LAB_80045dc0
            } else if(command == 0x90) { // Key on event
              //LAB_80046004
              sssqHandleKeyOn(spu44.channelIndex_01.get());
            } else if(command == 0xa0) { // Polyphonic key pressure (aftertouch)
              //LAB_80045ff0
              FUN_80046224(spu44.channelIndex_01.get());
              //LAB_80045dd4
            } else if(command == 0xb0) { // Control change
              //LAB_80045e60
              switch(spu124._002.get()) { // Controller number
                case 0x1 -> sssqHandleModulationWheel(spu44.channelIndex_01.get()); // Modulation wheel
                case 0x2 -> sssqHandleBreathControl(spu44.channelIndex_01.get()); // Breath control
                case 0x6 -> sssqHandleDataEntry(spu44.channelIndex_01.get()); // Data entry
                case 0x7 -> sssqHandleVolume(spu44.channelIndex_01.get()); // Volume

                case 0xa -> { // Pan
                  if(spu44.mono_36.get() == 0) {
                    //LAB_80045f44
                    sssqHandlePan(spu44.channelIndex_01.get());
                  } else if(spu124._028.get() == 0) {
                    //LAB_80045f30
                    spu124.sssqOffset_00c.add(0x6L);
                  } else {
                    sssqDataPointer_800c6680.deref(1).offset(0x4L).setu(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get() + 0x2L).get());
                    spu124.sssqOffset_00c.add(0x3L);
                  }
                }

                case 0x40 -> sssqHandleSustain(spu44.channelIndex_01.get()); // Damper pedal (sustain)
                case 0x41 -> sssqHandlePortamento(spu44.channelIndex_01.get()); // Portamento

                case 0x60 -> { // Data increment (???)
                  FUN_80049e2c(spu44.channelIndex_01.get());
                  FUN_8004a5e0(spu44.channelIndex_01.get());
                  break LAB_80045d40;
                }

                case 0x62 -> FUN_8004a2c0(spu44.channelIndex_01.get()); // Non-registered parameter number LSB (???)
                case 0x63 -> FUN_8004a34c(spu44.channelIndex_01.get()); // Non-registered parameter number MSB (???)
              }
            } else if(command == 0xc0) { // Program change
              //LAB_80045e4c
              sssqHandleProgramChange(spu44.channelIndex_01.get());
            } else if(command == 0xe0) { // Pitch bend
              //LAB_80045fc8
              sssqHandlePitchBend(spu44.channelIndex_01.get());
            } else if(command == 0xf0) { // Meta event
              //LAB_80045df8
              if(spu124._002.get() == 0x2f) { // End of track
                //LAB_80045e24
                sssqHandleEndOfTrack(spu44.channelIndex_01.get());
                break;
              }

              if(spu124._002.get() == 0x51) { // Tempo
                //LAB_80045e38
                sssqHandleTempo(spu44.channelIndex_01.get());
              }
            }
          }

          //LAB_80046010
          FUN_8004a5e0(spu44.channelIndex_01.get());
        }

        //LAB_8004602c
        if(spu44._04.get() != 0) {
          spu44.keyOnLo_3a.or(spu124.keyOnLo_0de.get());
          spu44.keyOnHi_3c.or(spu124.keyOnHi_0e0.get());
          spu44.keyOffLo_3e.or(spu124.keyOffLo_0e2.get());
          spu44.keyOffHi_40.or(spu124.keyOffHi_0e4.get());

          spu124.keyOnLo_0de.set(0);
          spu124.keyOnHi_0e0.set(0);
          spu124.keyOffLo_0e2.set(0);
          spu124.keyOffHi_0e4.set(0);

          spu44._04.set(0);
        }

        //LAB_800460a0
        if(spu124.tempo_108.get() != 0 || spu124._02a.get() == 1) {
          //LAB_800460c0
          if(spu124._118.get() != 0) {
            spu124._118.decr();
          }
        }

        //LAB_800460d4
        if(spu124._037.get() != 0) {
          spu124.command_000.set(spu124._039);
          spu124._001.set(spu124._039);
          spu124.sssqOffset_00c.set(spu124._02c);
          spu124._037.set(0);

          if(spu124._0e6.get() == 0) {
            //LAB_80046118
            spu124._028.set(1);
            spu124._118.set(0);
          } else {
            spu124._02a.set(1);
            spu124._0e6.set(0);
          }
        }
      }

      //LAB_80046120
      FUN_8004af98(spu44.channelIndex_01.get());
    }

    SPU.VOICE_CHN_NOISE_MODE.set(spu44.noiseModeHi_18.get() << 16 | spu44.noiseModeLo_16.get());
    SPU.VOICE_CHN_REVERB_MODE.set(spu44.reverbModeHi_14.get() << 16 | spu44.reverbModeLo_12.get());

    if(spu44.keyOffLo_3e.get() != 0 || spu44.keyOffHi_40.get() != 0) {
      SPU.VOICE_KEY_OFF.set(spu44.keyOffHi_40.get() << 16 | spu44.keyOffLo_3e.get());
    }

    //LAB_800461b0
    if(spu44.keyOnLo_3a.get() != 0 || spu44.keyOnHi_3c.get() != 0) {
      SPU.VOICE_KEY_ON.set(spu44.keyOnHi_3c.get() << 16 | spu44.keyOnLo_3a.get());
    }

    //LAB_800461e0
    spu44.keyOnLo_3a.set(0);
    spu44.keyOnHi_3c.set(0);
    spu44.keyOffLo_3e.set(0);
    spu44.keyOffHi_40.set(0);

    FUN_800470fc();
    FUN_8004b2c4();
    sssqStatus(0);
  }

  @Method(0x80046224L)
  public static void FUN_80046224(final int channelIndex) {
    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
    if(spu124._003.get() == 0) {
      FUN_80048514(channelIndex);
      return;
    }

    //LAB_8004629c
    final long sp18 = spu124._002.get() - _800c6674.deref(1).offset(0x6L).get();
    if((int)sp18 < 0) {
      return;
    }

    sshd10Ptr_800c6678.add(sp18 * 0x10L);
    sssqDataPointer_800c6680.deref(1).offset(0xaL).set(0x40L);

    final short voiceIndex = (short)FUN_80048000(sshd10Ptr_800c6678.deref()._00.get(), sshd10Ptr_800c6678.deref()._01.get(), spu124.playableSoundIndex_020.get());
    if(voiceIndex == -1) {
      spu124.sssqOffset_00c.add(0x4L);
      _800c6630._04.set(0);
      return;
    }

    //LAB_8004632c
    final SpuStruct66 struct66 = _800c3a40.get(voiceIndex);
    if((sshd10Ptr_800c6678.deref()._0f.get() & 0x1) != 0) {
      struct66._08.set(0);
      struct66._0c.set(1);
    } else {
      //LAB_80046374
      struct66._08.set(1);
      struct66._0c.set(0);
    }

    //LAB_8004639c
    struct66.used_00.set(true);
    struct66._02.set(spu124._002.get());
    struct66.channel_04.set(spu124.command_000.get() & 0xf);
    struct66.channelIndex_06.set((short)channelIndex);
    struct66._0a.set(_800c6630.voiceIndex_00.get());
    struct66._0e.set((int)sp18);
    struct66._12.set(0);
    struct66._18.set(0);
    struct66._1a.set(1);
    struct66._1c.set(4);
    struct66._1e.set(sshd10Ptr_800c6678.deref()._01.get());
    struct66._20.set(sshd10Ptr_800c6678.deref()._00.get());
    struct66.playableSoundIndex_22.set(spu124.playableSoundIndex_020);
    struct66._24.set(spu124._024);
    struct66._26.set(spu124._022);
    struct66._28.set((int)sssqDataPointer_800c6680.deref(1).offset(0xeL).get());
    struct66._2a.set((int)_800c6674.deref(1).offset(0x1L).get());
    struct66._2c.set((int)_800c4ab0.deref(1).offset(spu124._003.get()).offset(0x2L).get());
    struct66._2e.set(sshd10Ptr_800c6678.deref()._0b.get());
    struct66._30.set((int)_80059f3c.offset(FUN_80048b90(4, 0) / 0x2L & 0x7ffeL).offset(0x0L).get());
    struct66._32.set((int)_80059f3c.offset(FUN_80048b90(4, 0) / 0x2L & 0x7ffeL).offset(0x1L).get());
    struct66._34.set((int)sssqDataPointer_800c6680.deref(1).offset(0x3L).get());

    int v1 = sshd10Ptr_800c6678.deref()._03.get();
    if((v1 & 0x80) != 0) {
      v1 = 0xff00 | v1;
    }

    //LAB_80046500
    struct66._36.set((short)v1);
    struct66._38.set((int)sssqDataPointer_800c6680.deref(1).offset(0xaL).get());
    struct66._3a.set(sshd10Ptr_800c6678.deref()._0d.get());
    struct66._3c.set((int)sssqDataPointer_800c6680.deref(1).offset(0xcL).get());
    struct66._3e.set(spu124._005.get());
    struct66.sssqEntry_44.set(0);
    struct66._40.set(sshd10Ptr_800c6678.deref()._02.get());
    struct66._4a.set(sshd10Ptr_800c6678.deref()._0a.get());
    struct66._4c.set(sshd10Ptr_800c6678.deref()._0c.get());
    struct66._4e.set(120);
    struct66._62.set(0);

    if(_800c6630.voiceIndex_00.get() < 24) {
      _800c6630.voiceIndex_00.incr();
    }

    v1 = sshd10Ptr_800c6678.deref()._0f.get();
    if((v1 & 0x20) != 0) {
      if((v1 & 0x40) != 0) {
        struct66._10.set((int)_800c6674.deref(1).offset(0x5L).get());
      } else {
        //LAB_800465a4
        struct66._10.set(sshd10Ptr_800c6678.deref()._0e.get());
      }

      //LAB_800465b0
      struct66._14.set(1);
      struct66._16.set(127);
    } else {
      //LAB_800465ec
      struct66._14.set(0);
    }

    //LAB_800465f0
    int l = FUN_80048ab8(channelIndex, FUN_80048b90(4, 0), 0);
    int r = FUN_80048ab8(channelIndex, FUN_80048b90(4, 0), 1);

    final long t0;
    if((sshd10Ptr_800c6678.deref()._0f.get() & 0x10L) != 0) {
      t0 = _800c6674.deref(1).offset(0x4L).get();
    } else {
      //LAB_80046668
      t0 = sshd10Ptr_800c6678.deref()._0d.get();
    }

    //LAB_8004666c
    if(spu124.pitchShifted_0e9.get() != 0) {
      //LAB_8004669c
      voicePtr_800c4ac4.deref().voices[voiceIndex].ADPCM_SAMPLE_RATE.set(calculateSampleRate(sshd10Ptr_800c6678.deref()._02.get(), spu124._002.get(), sshd10Ptr_800c6678.deref()._03.get(), sssqDataPointer_800c6680.deref(1).offset(0xaL).get(), t0) * spu124.pitch_0ec.get() / 0x1000);
      l = scaleValue12((short)l, spu124.pitchShiftVolLeft_0ee.get());
      r = scaleValue12((short)r, spu124.pitchShiftVolRight_0f0.get());
      struct66._42.set(1);
    } else {
      //LAB_80046730
      //LAB_80046750
      voicePtr_800c4ac4.deref().voices[voiceIndex].ADPCM_SAMPLE_RATE.set(calculateSampleRate(sshd10Ptr_800c6678.deref()._02.get(), spu124._002.get(), sshd10Ptr_800c6678.deref()._03.get(), sssqDataPointer_800c6680.deref(1).offset(0xaL).get(), t0));
      l = scaleValue12((short)l, (short)0x1000);
      r = scaleValue12((short)r, (short)0x1000);
      struct66._42.set(0);
    }

    //LAB_800467c8
    if(_800c6630.mono_36.get() != 0) {
      l = maxShort(l, r);
      r = l;
    }

    //LAB_800467f0
    final Voice voice = voicePtr_800c4ac4.deref().voices[voiceIndex];
    voice.LEFT.set(l);
    voice.RIGHT.set(r);
    voice.ADPCM_START_ADDR.set(playableSoundPtrArr_800c43d0.get(spu124.playableSoundIndex_020.get()).soundBufferPtr_08.get() + sshd10Ptr_800c6678.deref()._04.get());
    voice.ADSR_LO.set(sshd10Ptr_800c6678.deref().adsrLo_06.get());
    voice.ADSR_HI.set(sshd10Ptr_800c6678.deref().adsrHi_08.get());
    setKeyOn(channelIndex, voiceIndex);

    if(spu124.reverbEnabled_0ea.get() != 0 || (sshd10Ptr_800c6678.deref()._0f.get() & 0x80) != 0) {
      if(voiceIndex < 16) {
        _800c6630.reverbModeLo_12.or(1 << voiceIndex);
      } else {
        _800c6630.reverbModeHi_14.or(1 << voiceIndex - 16);
      }
      //LAB_80046884
      //LAB_800468d8
    } else if((sshd10Ptr_800c6678.deref()._0f.get() & 0x80) == 0) {
      if(voiceIndex < 16) {
        _800c6630.reverbModeLo_12.and(~(1 << voiceIndex));
      } else {
        //LAB_800468f8
        _800c6630.reverbModeHi_14.and(~(1 << voiceIndex - 16));
      }
    }

    //LAB_80046914
    if((sshd10Ptr_800c6678.deref()._0f.get() & 0x2) == 0) {
      if(voiceIndex < 16) {
        //LAB_80046964
        _800c6630.noiseModeLo_16.and(~(1 << voiceIndex));
      } else {
        //LAB_80046990
        _800c6630.noiseModeHi_18.and(~(1 << voiceIndex - 16));
      }
    } else {
      setNoiseMode(channelIndex, voiceIndex);
      voicePtr_800c4ac4.deref().SPUCNT
        .and(0xc0ff) // Mask off noise freq step/shift
        .or(sshd10Ptr_800c6678.deref()._02.get() << 8);
    }

    //LAB_800469ac
    sshd10Ptr_800c6678.sub(sp18 * 0x10L);
    spu124.sssqOffset_00c.add(0x4L);

    //LAB_800469d4
  }

  @Method(0x80046a04L)
  public static void sssqHandleKeyOn(final int channelIndex) {
    final SpuStruct124 s2 = _800c4ac8.get(channelIndex);
    if(s2._003.get() == 0) {
      sssqHandleKeyOff(channelIndex);
      return;
    }

    //LAB_80046a7c
    if(sssqDataPointer_800c6680.deref(1).offset(0x3L).get() != 0) {
      final long v1 = _800c6674.deref(1).get();
      if(v1 == 0xffL) {
        final long v0 = s2._002.get() - _800c6674.deref(1).offset(0x6L).get();
        s2._026.set((int)v0);
        s2._01e.set((int)v0);
        //LAB_80046acc
      } else if((v1 & 0x80L) != 0) {
        s2._026.set((int)(v1 + 0x80L) & 0xff); // This can actually overflow
        s2._01e.set(0);
        _800c6630._0c.set(0);
      } else {
        //LAB_80046ae8
        s2._026.set((int)v1);
        s2._01e.set(0);
        _800c6630._0c.set(1);
      }

      //LAB_80046af8
      //LAB_80046b24
      for(int s7 = s2._01e.get(); s7 < s2._026.get() + 1; s7++) {
        if(FUN_80048938(_800c6674.deref(1).get(), s7, s2._002.get())) {
          final short voiceIndex = FUN_80047e1c();
          if(voiceIndex == -1) {
            break;
          }

          final SpuStruct66 s1 = _800c3a40.get(voiceIndex);

          sshd10Ptr_800c6678.add(s7 * 0x10L);
          if((sshd10Ptr_800c6678.deref()._0f.get() & 0x1) != 0) {
            s1._08.set(0);
            s1._0c.set(1);
          } else {
            //LAB_80046bb4
            s1._08.set(1);
            s1._0c.set(0);
          }

          //LAB_80046bdc
          s1.used_00.set(true);
          s1._02.set(s2._002.get());
          s1.channelIndex_06.set((short)channelIndex);
          s1.channel_04.set(s2.command_000.get() & 0xf);
          s1._0a.set(_800c6630.voiceIndex_00.get());
          s1._0e.set(s7);
          s1._12.set(0);
          s1._1a.set(0);
          s1._1c.set(0);
          s1._1e.set(0);
          s1._20.set(0);
          s1.playableSoundIndex_22.set(s2.playableSoundIndex_020.get());
          s1._28.set((int)sssqDataPointer_800c6680.deref(1).offset(0xeL).get());
          s1._2a.set((int)_800c6674.deref(1).offset(0x1L).get());
          s1._2c.set((int)_800c4ab0.deref(1).offset(s2._003.get()).offset(0x2L).get());
          s1._2e.set(sshd10Ptr_800c6678.deref()._0b.get());
          s1._30.set((int)_80059f3c.offset(FUN_80048b90(0, 0) / 0x2L & 0x7ffeL).offset(0x0L).get());
          s1._32.set((int)_80059f3c.offset(FUN_80048b90(0, 0) / 0x2L & 0x7ffeL).offset(0x1L).get());
          s1._34.set((int)sssqDataPointer_800c6680.deref(1).offset(0x3L).get());

          int n = sshd10Ptr_800c6678.deref()._03.get();
          if((n & 0x80) != 0) {
            n |= 0xff00;
          }

          //LAB_80046d08
          s1._36.set((short)n);
          s1._38.set((int)sssqDataPointer_800c6680.deref(1).offset(0xaL).get());
          s1._3a.set(sshd10Ptr_800c6678.deref()._0d.get());
          s1._3c.set((int)sssqDataPointer_800c6680.deref(1).offset(0xcL).get());
          s1._3e.set(s2._005.get());
          s1._40.set(sshd10Ptr_800c6678.deref()._02.get());
          s1._42.set(0);
          s1.sssqEntry_44.set(0);
          s1._4a.set(sshd10Ptr_800c6678.deref()._0a.get());
          s1._4c.set((int)sssqDataPointer_800c6680.deref(1).offset(0x4L).get());
          s1._4e.set(120);

          if(sssqDataPointer_800c6680.deref(1).offset(0xbL).get() == 0x7f) {
            s1._18.set(1);
          }

          //LAB_80046d80
          if((sshd10Ptr_800c6678.deref()._0f.get() & 0x20) == 0 || sssqDataPointer_800c6680.deref(1).offset(0x9L).get() == 0) {
            //LAB_80046e1c
            //LAB_80046e20
            s1._14.set(0);
            s1._16.set(0);
          } else {
            if((sshd10Ptr_800c6678.deref()._0f.get() & 0x40) != 0) {
              s1._10.set((int)_800c6674.deref(1).offset(0x5L).get());
            } else {
              //LAB_80046dd0
              s1._10.set(sshd10Ptr_800c6678.deref()._0e.get());
            }

            //LAB_80046ddc
            s1._14.set(1);
            s1._16.set((int)sssqDataPointer_800c6680.deref(1).offset(0x9L).get());
          }

          //LAB_80046e4c
          final long t0;
          if((sshd10Ptr_800c6678.deref()._0f.get() & 0x10) != 0) {
            t0 = _800c6674.deref(1).offset(0x4L).get();
          } else {
            //LAB_80046e7c
            t0 = sshd10Ptr_800c6678.deref()._0d.get();
          }

          //LAB_80046e80
          //LAB_80046ea0
          voicePtr_800c4ac4.deref().voices[voiceIndex].ADPCM_SAMPLE_RATE.set(calculateSampleRate(sshd10Ptr_800c6678.deref()._02.get(), s2._002.get(), sshd10Ptr_800c6678.deref()._03.get(), sssqDataPointer_800c6680.deref(1).offset(0xaL).get(), t0));
          int l = FUN_80048ab8(channelIndex, FUN_80048b90(0, 0), 0);
          int r = FUN_80048ab8(channelIndex, FUN_80048b90(0, 0), 1);

          if(_800c6630.mono_36.get() != 0) {
            l = maxShort(l, r);
            r = l;
          }

          //LAB_80046f30
          final Voice voice = voicePtr_800c4ac4.deref().voices[voiceIndex];
          voice.LEFT.set(l);
          voice.RIGHT.set(r);
          voice.ADPCM_START_ADDR.set(sshd10Ptr_800c6678.deref()._04.get() + playableSoundPtrArr_800c43d0.get(s2.playableSoundIndex_020.get()).soundBufferPtr_08.get());
          voice.ADSR_LO.set(sshd10Ptr_800c6678.deref().adsrLo_06.get());
          voice.ADSR_HI.set(sshd10Ptr_800c6678.deref().adsrHi_08.get());
          setKeyOn(channelIndex, voiceIndex);

          if((sshd10Ptr_800c6678.deref()._0f.get() & 0x80) != 0) {
            if(voiceIndex < 16) {
              _800c6630.reverbModeLo_12.or(1 << voiceIndex);
            } else {
              //LAB_80046fcc
              _800c6630.reverbModeHi_14.or(1 << voiceIndex - 16);
            }
          } else {
            //LAB_80046fe8
            if(voiceIndex < 16) {
              _800c6630.reverbModeLo_12.and(~(1 << voiceIndex));
            } else {
              //LAB_80047008
              _800c6630.reverbModeHi_14.and(~(1 << voiceIndex - 16));
            }
          }

          //LAB_80047024
          if(voiceIndex < 16) {
            _800c6630.noiseModeLo_16.and(~(1 << voiceIndex));
          } else {
            //LAB_80047050
            _800c6630.noiseModeHi_18.and(~(1 << voiceIndex - 16));
          }

          //LAB_8004706c
          if(_800c6630.voiceIndex_00.get() < 24) {
            _800c6630.voiceIndex_00.incr();
          }

          sshd10Ptr_800c6678.sub(s7 * 0x10L);

          if(_800c6630._0c.get() != 0) {
            //LAB_80046ae0
            _800c6630._0c.set(0);
            break;
          }
        }

        //LAB_8004709c
      }
    }

    //LAB_800470bc
    s2.sssqOffset_00c.add(0x3L);

    //LAB_800470cc
  }

  @Method(0x800470fcL)
  public static void FUN_800470fc() {
    final SpuStruct44 struct44 = _800c6630;

    //LAB_80047144
    for(short voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final Voice voice = voicePtr_800c4ac4.deref().voices[voiceIndex];
      final SpuStruct66 struct66 = _800c3a40.get(voiceIndex);

      if(struct66.used_00.get()) {
        if(struct66.channelIndex_06.get() >= 0 && struct66.channelIndex_06.get() < 24) {
          final SpuStruct124 struct124 = _800c4ac8.get(struct66.channelIndex_06.get());

          long v1;
          if(struct66._14.get() == 1 || struct66.sssqEntry_44.get() == 1 || struct124._104.get() == 1) {
            //LAB_800471d0
            //LAB_800471d4
            long a2 = struct66._36.get();
            long a1 = struct66._02.get();
            long t2 = struct66._40.get();
            long a3 = struct66._38.get();
            long t3 = struct66._3a.get();
            if(struct66._14.get() == 1 || struct66.sssqEntry_44.get() == 1) {
              //LAB_80047220
              if((a1 & 0xffff) >= (t2 & 0xffff)) {
                t2 = 120 - (a1 - t2);
              } else {
                //LAB_80047244
                t2 = 120 + t2 - a1;
              }

              //LAB_80047248
              long a0;
              if(struct66._14.get() != 0) {
                if(struct44._42.get() != 60 || (struct66._3c.get() & 0xfff) != 120) {
                  //LAB_800472cc
                  //LAB_800472d0
                  struct66._12.add(struct66._3c.get() & 0xfff);
                } else {
                  final int v0_0 = struct66._3c.get() & 0xf000;
                  if(v0_0 != 0) {
                    struct66._3c.and(0xfff).or((v0_0 - 0x1000));
                    struct66._12.add(struct66._3c.get() & 0xfff);
                  } else {
                    //LAB_800472c0
                    struct66._3c.or(0x6000);
                  }
                }

                //LAB_80047300
                a0 = playableSoundPtrArr_800c43d0.get(struct124.playableSoundIndex_020.get()).sshdPtr_04.getPointer();

                a3 = 0x80L;
                if((a0 & 0x3) == 0) {
                  sshdPtr_800c4ac0.setPointer(a0);

                  a1 = a0 + MEMORY.ref(4, a0).offset(0x18L).get();
                  if(a1 != -1 && (a1 & 0x1) == 0) {
                    _800c4ab4.setu(a1);
                    _800c4ab8.setu(a1);

                    if(struct66._12.get() >= 0xf0) {
                      struct66._12.set((struct66._3c.get() & 0xfff) >>> 1);
                    }

                    //LAB_800473a0
                    v1 = _800c4ab8.get() + _800c4ab4.deref(2).offset(struct66._10.get() * 0x2L).offset(0x2L).get() + (struct66._12.get() >>> 2);
                    a3 = MEMORY.ref(1, v1).offset(0x0L).get();
                  }
                }

                //LAB_800473d4
                //LAB_800473d8
                a1 = struct66._4e.get();
                if(struct66._1c.get() == 0) {
                  long v0;
                  if(struct66._38.get() >= 64) {
                    v0 = (struct66._38.get() - 64) * struct66._3a.get();
                    v1 = (int)v0 / 64;
                    a1 = a1 + v1;
                    v0 = (int)v0 / 4;
                    v1 = v1 * 16;
                  } else {
                    //LAB_80047454
                    v0 = (64 - struct66._38.get()) * struct66._3a.get();
                    a0 = (int)v0 / 64;
                    a1 = a1 - a0;
                    v1 = (int)v0 / 4;
                    v0 = a0 * 16;
                  }

                  //LAB_8004748c
                  v0 = v0 - v1;
                  a2 = a2 + v0;
                  t3 = 1;
                }

                //LAB_80047498
                a3 = a3 * struct66._16.get() / 255 - ((struct66._16.get() + 1) / 2 - 64);
              }

              //LAB_800474f0
              if(struct66.sssqEntry_44.get() != 0) {
                if(struct66._62.get() != 0) {
                  struct66._62.decr();

                  if((struct66._60.get() & 0x80) != 0) {
                    a0 = struct66._64.get() - struct66._62.get();
                    a1 = struct66._4e.get() - a0 * (0x100 - struct66._60.get()) / 10 / struct66._64.get();
                    a2 = a2 - a0 * (-struct66._60.get() & 0xff) * 192 / (struct66._64.get() * 120) % 16;
                  } else {
                    //LAB_8004762c
                    a0 = (struct66._64.get() - struct66._62.get()) * struct66._60.get();
                    a1 = struct66._4e.get() + a0 / 10 / struct66._64.get();
                    a2 = a2 + a0 * 192 / (struct66._64.get() * 120) % 16;
                  }

                  //LAB_800476f4
                  if((a1 & 0xffff) <= 0xc) {
                    a1 = 0xc;
                  }

                  //LAB_8004770c
                  if((a1 & 0xffff) >= 0xf3) {
                    a1 = 0xf3;
                  }

                  //LAB_8004771c
                  struct124._11c.set((int)a1);

                  if(struct66._62.get() == 0) {
                    struct66._4e.set((int)a1);
                    struct66.sssqEntry_44.set(0);
                  }
                }
              }
            }

            //LAB_80047754
            //LAB_80047758
            final int pitch;
            if(struct66._42.get() == 1 || struct124._104.get() == 1) {
              //LAB_80047794
              pitch = struct124.pitch_0ec.get();
            } else {
              pitch = 0x1000;
            }

            //LAB_800477a0
            //LAB_800477a4
            voice.ADPCM_SAMPLE_RATE.set(pitch * (calculateSampleRate((int)t2, (int)a1, (short)a2, a3 & 0xffff, t3) & 0xffff) >> 12);
          }

          //LAB_800477ec
          if(struct66._1a.get() == 1) {
            if(struct66._46.get() == 1 || struct66._48.get() == 1 || struct124._105.get() == 1) {
              //LAB_80047844
              //LAB_80047848
              if(struct66._46.get() != 0) {
                v1 = struct66._50.get();

                if(v1 == struct66._52.get()) {
                  struct66._46.set(0);
                } else {
                  if(struct66._54.get() != 0) {
                    struct66._2c.set(FUN_8004af3c(struct66._50.get() & 0xff, struct66._52.get() & 0xff, struct66._56.get() & 0xff, struct66._54.get() & 0xff) & 0xff);
                    struct66._54.decr();
                  } else {
                    //LAB_800478c8
                    struct66._2c.set((int)v1);

                    //LAB_800478cc
                    struct66._46.set(0);
                  }
                }
              }

              //LAB_800478d0
              //LAB_800478d4
              if(struct66._48.get() != 0) {
                v1 = struct66._58.get();

                if(v1 == struct66._5a.get()) {
                  struct66._48.set(0);
                } else {
                  //LAB_8004791c
                  if(struct66._5c.get() != 0) {
                    struct66._4c.set(FUN_8004af3c(struct66._58.get() & 0xff, struct66._5a.get() & 0xff, struct66._5e.get() & 0xff, struct66._5c.get() & 0xff) & 0xff);
                    struct66._5c.decr();
                  } else {
                    //LAB_8004795c
                    struct66._4c.set((int)v1);
                    struct66._48.set(0);
                  }

                  //LAB_80047964
                  struct66._30.set((int)_80059f3c.offset((struct66._4c.get() >>> 2) * 0x2L).offset(0x0L).get());
                  struct66._32.set((int)_80059f3c.offset((struct66._4c.get() >>> 2) * 0x2L).offset(0x1L).get());
                }
              }

              //LAB_800479c4
              int l = FUN_8004ae94(voiceIndex & 0xffff, 0);
              int r = FUN_8004ae94(voiceIndex & 0xffff, 1);
              if(struct66._42.get() == 1 || struct124._105.get() == 1) {
                //LAB_80047a24
                l = scaleValue12((short)l, struct124.pitchShiftVolLeft_0ee.get());
                r = scaleValue12((short)r, struct124.pitchShiftVolRight_0f0.get());
              }

              //LAB_80047a44
              if(struct44.mono_36.get() != 0) {
                r = maxShort((short)l, (short)r);
                l = r;
              }

              //LAB_80047a6c
              voice.LEFT.set(l);
              voice.RIGHT.set(r);
            }
          }

          //LAB_80047a88
          //LAB_80047a90
        }
      }

      //LAB_80047acc
    }
  }

  @Method(0x80047b38L)
  public static void FUN_80047b38(final int channelIndex) {
    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
    final SshdFile sshd = playableSoundPtrArr_800c43d0.get(spu124.playableSoundIndex_020.get()).sshdPtr_04.deref();

    _800c6630.sshdPtr_08.set(sshd);
    sshdPtr_800c4ac0.set(sshd);
    sssqPtr_800c4aa4.setu(spu124.sssqPtr_010.getPointer());
    _800c4ab0.setu(sshd.getAddress() + sshd.ptr_14.get());

    final long v1 = sshd.getAddress() + MEMORY.ref(4, sshd._10.getAddress()).offset(spu124._02a.get() * 0x10L).get(); //TODO

    _800c4aa8.setu(v1);
    _800c4aac.setu(v1);
  }

  @Method(0x80047bd0L)
  public static long FUN_80047bd0(final int channelIndex) {
    if((_800c4ac8.get(channelIndex)._028_4b.get() & 0xff_00ff) == 0x1_0000) {
      if(sshdPtr_800c4ac0.deref().ptr_20.get() != -1) {
        _800c6674.setu(_800c6630.sshdPtr_08.getPointer() + sshdPtr_800c4ac0.deref().ptr_20.get() + _800c4aa8.deref(2).offset(sssqPtr_800c4aa4.deref(1).offset(_800c4ac8.get(channelIndex).sssqOffset_00c.get()).offset(0x3L).get() * 0x2L).offset(0x192L).get() + 0x190L);
        sshd10Ptr_800c6678.setPointer(_800c6630.sshdPtr_08.getPointer() + sshdPtr_800c4ac0.deref().ptr_20.get() + _800c4aa8.deref(2).offset(sssqPtr_800c4aa4.deref(1).offset(_800c4ac8.get(channelIndex).sssqOffset_00c.get()).offset(0x3L).get() * 0x2L).offset(0x192L).get() + 0x198L);
        sssqPtr_800c667c.setu(_800c6630.sshdPtr_08.getPointer() + sshdPtr_800c4ac0.deref().ptr_20.get());
        sssqDataPointer_800c6680.setu(_800c6630.sshdPtr_08.getPointer() + sshdPtr_800c4ac0.deref().ptr_20.get() + (channelIndex + 1) * 0x10L);
        return 0x1L;
      }
    }

    //LAB_80047cd0
    if(_800c4ac8.get(channelIndex)._028.get() == 0) {
      return 0;
    }

    if(_800c4ac8.get(channelIndex)._02a.get() == 1) {
      //LAB_80047cf0
      return 0;
    }

    //LAB_80047cf8
    final int command = _800c4ac8.get(channelIndex).command_000.get();
    final int channel = command & 0xf;
    if(command < 0xa0) {
      if(_800c4aa8.deref(2).offset(sssqPtr_800c4aa4.deref(1).offset(channel * 0x10L).offset(0x12L).get() * 0x2L).offset(0x2L).get() == 0xffff) {
        return 0;
      }

      if(_800c4aa8.deref(2).get() < sssqPtr_800c4aa4.deref(1).offset(channel * 0x10L).offset(0x12L).get()) {
        return 0;
      }

      if(sshdPtr_800c4ac0.deref()._10.get() == -1) {
        return 0;
      }
    }

    //LAB_80047d7c
    //LAB_80047d80
    _800c6674.setu(_800c6630.sshdPtr_08.getPointer() + sshdPtr_800c4ac0.deref()._10.get() + _800c4aa8.deref(2).offset(sssqPtr_800c4aa4.deref(1).offset(channel * 0x10L).offset(0x12L).get() * 0x2L).offset(0x2L).get());
    sshd10Ptr_800c6678.setPointer(_800c6630.sshdPtr_08.getPointer() + sshdPtr_800c4ac0.deref()._10.get() + _800c4aa8.deref(2).offset(sssqPtr_800c4aa4.deref(1).offset(channel * 0x10L).offset(0x12L).get() * 0x2L).offset(0x2L).get() + 0x8L);
    sssqPtr_800c667c.setu(_800c4ac8.get(channelIndex).sssqPtr_010.getPointer());
    sssqDataPointer_800c6680.setu(_800c4ac8.get(channelIndex).sssqPtr_010.getPointer() + (channel + 1) * 0x10L);

    //LAB_80047e14
    return 0x1L;
  }

  @Method(0x80047e1cL)
  public static short FUN_80047e1c() {
    //LAB_80047e34
    for(int i = 0; i < 24; i++) {
      //LAB_80047e4c
      if(_800c6630.voiceIndex_10.get() < 23) {
        _800c6630.voiceIndex_10.incr();
      } else {
        _800c6630.voiceIndex_10.set((short)0);
      }

      if(_800c3a40.get(_800c6630.voiceIndex_10.get())._10.get() == 0) {
        return _800c6630.voiceIndex_10.get();
      }
    }

    //LAB_80047ea0
    int a1 = 24;
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      if(_800c3a40.get(voiceIndex)._1a.get() == 0 && _800c3a40.get(voiceIndex)._08.get() == 1) {
        final int v1 = _800c3a40.get(voiceIndex)._0a.get();

        if(a1 > v1) {
          a1 = v1;
          _800c6630.voiceIndex_10.set((short)voiceIndex);
        }
      }

      //LAB_80047ef4
    }

    if(a1 == 24) {
      //LAB_80047f28
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        if(_800c3a40.get(voiceIndex)._1a.get() == 0) {
          final int v1 = _800c3a40.get(voiceIndex)._0a.get();

          if(a1 > v1) {
            //LAB_80047f84
            a1 = v1;
            _800c6630.voiceIndex_10.set((short)voiceIndex);
            break;
          }
        }

        //LAB_80047f64
      }
    }

    if(a1 == 24) {
      _800c6630.voiceIndex_10.set((short)-1);
      return -1;
    }

    //LAB_80047f90
    //LAB_80047fa0
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      if((a1 & 0xffff) < _800c3a40.get(voiceIndex)._0a.get()) {
        _800c3a40.get(voiceIndex)._0a.decr();
      }

      //LAB_80047fd0
    }

    _800c6630.voiceIndex_00.decr();

    //LAB_80047ff4
    return _800c6630.voiceIndex_10.get();
  }

  @Method(0x80048000L)
  public static long FUN_80048000(final long a0, final long a1, final long playableSoundIndex) {
    if((a0 & 0xffffL) != 0) {
      //LAB_8004802c
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        if(_800c3a40.get(voiceIndex)._1a.get() == 1 && _800c3a40.get(voiceIndex)._20.get() == (a0 & 0xffffL) && _800c3a40.get(voiceIndex).playableSoundIndex_22.get() == (playableSoundIndex & 0xffffL)) {
          //LAB_80048080
          for(int voiceIndex2 = 0; voiceIndex2 < 24; voiceIndex2++) {
            final int v1 = _800c3a40.get(voiceIndex)._0a.get();

            if(v1 < _800c3a40.get(voiceIndex2)._0a.get() && v1 != 64) {
              _800c3a40.get(voiceIndex2)._0a.decr();
            }

            //LAB_800480cc
          }

          //LAB_80048260
          _800c6630.voiceIndex_00.decr();
          return (short)voiceIndex;
        }

        //LAB_800480f0
      }
    }

    int t2 = 0;
    int t1 = 24;

    //LAB_80048108
    if(_800c6630._0d.get() >= _800c6630._03.get()) {
      //LAB_80048134
      for(int i = 0; i < 24; i++) {
        //LAB_80048144
        for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
          if(_800c3a40.get(voiceIndex)._1a.get() == 1) {
            final int v1 = _800c3a40.get(voiceIndex)._0a.get();

            if(v1 >= i && v1 < (short)t1) {
              t1 = v1;
              t2 = voiceIndex;
            }
          }

          //LAB_800481a0
        }

        if(_800c3a40.get(t2)._1e.get() <= (a1 & 0xffffL)) {
          //LAB_800481fc
          for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
            final int v1 = _800c3a40.get(voiceIndex)._0a.get();

            if(v1 > (short)t1 && v1 != 0xffffL) {
              _800c3a40.get(voiceIndex)._0a.decr();
            }

            //LAB_80048240
          }

          //LAB_80048260
          _800c6630.voiceIndex_00.decr();
          return (short)t2;
        }

        //LAB_8004826c
        t1 = 24;
      }

      return -1;
    }

    //LAB_800482a0
    //LAB_800482a8
    for(int i = 0; i < 24; i++) {
      if(_800c6630.voiceIndex_10.get() < 23) {
        _800c6630.voiceIndex_10.incr();
      } else {
        _800c6630.voiceIndex_10.set((short)0);
      }

      //LAB_800482c0
      if(!_800c3a40.get(_800c6630.voiceIndex_10.get()).used_00.get()) {
        //LAB_8004828c
        _800c6630._0d.incr();
        return _800c6630.voiceIndex_10.get();
      }
    }

    int t3 = -1;

    //LAB_80048320
    jmp_80048478:
    {
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        if(_800c3a40.get(voiceIndex)._08.get() == 1 && _800c3a40.get(voiceIndex)._1a.get() != 1) {
          //LAB_8004836c
          for(int voiceIndex2 = voiceIndex; voiceIndex2 < 24; voiceIndex2++) {
            if(_800c3a40.get(voiceIndex2)._08.get() == 1 && _800c3a40.get(voiceIndex2)._1a.get() != 1) {
              final int v1 = _800c3a40.get(voiceIndex2)._0a.get();
              if(v1 < (short)t1) {
                t1 = v1;
                t3 = voiceIndex2;
              }
            }

            //LAB_800483c8
          }

          break jmp_80048478;
        }

        //LAB_800483e8
      }

      //LAB_80048414
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        if(_800c3a40.get(voiceIndex)._1a.get() != 1) {
          final int v1 = _800c3a40.get(voiceIndex)._0a.get();
          if(v1 < (short)t1) {
            t1 = v1;
            t3 = voiceIndex;
          }
        }

        //LAB_80048460
      }
    }

    //LAB_80048478
    //LAB_8004847c
    //LAB_80048494
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final int v1 = _800c3a40.get(voiceIndex)._0a.get();

      if(v1 > (short)t1 && v1 != 0xffff) {
        _800c3a40.get(voiceIndex)._0a.decr();
      }

      //LAB_800484d8
    }

    _800c6630._0d.incr();
    _800c6630.voiceIndex_00.decr();

    //LAB_80048508
    return (short)t3;
  }

  @Method(0x80048514L)
  public static void FUN_80048514(final int channelIndex) {
    long s3 = 0;
    long a3 = 0;

    //LAB_8004857c
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      if(_800c3a40.get(voiceIndex).used_00.get()) {
        if(_800c3a40.get(voiceIndex)._1a.get() == 1) {
          if(_800c3a40.get(voiceIndex).playableSoundIndex_22.get() == _800c4ac8.get(channelIndex).playableSoundIndex_020.get()) {
            if(_800c3a40.get(voiceIndex)._3e.get() == _800c4ac8.get(channelIndex)._005.get()) {
              if(_800c3a40.get(voiceIndex)._02.get() == _800c4ac8.get(channelIndex)._002.get()) {
                if(_800c3a40.get(voiceIndex)._0c.get() == 1) {
                  if(_800c3a40.get(voiceIndex).channelIndex_06.get() == channelIndex) {
                    s3 |= 0x1L << voiceIndex;
                  } else {
                    //LAB_8004861c
                    a3 |= 0x1L << voiceIndex;
                  }
                }
              }
            }
          }
        }
      }

      //LAB_80048620
    }

    if(s3 == 0) {
      s3 = a3;
    }

    //LAB_80048640
    //LAB_80048650
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      if((s3 & 0x1L << voiceIndex) != 0) {
        setKeyOff(channelIndex, voiceIndex);
        _800c3a40.get(voiceIndex)._08.incr();
      }

      //LAB_80048684
    }

    _800c4ac8.get(channelIndex).sssqOffset_00c.add(0x4L);
  }

  @Method(0x800486d4L)
  public static long sssqHandleKeyOff(final int channelIndex) {
    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    //LAB_80048724
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);
      if(spu66.used_00.get()) {
        if(spu66._1a.get() == 0) {
          if(spu66.channelIndex_06.get() == channelIndex) {
            if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
              if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
                if(spu66._02.get() == spu124._002.get()) {
                  if(spu66._0c.get() == 0) {
                    //LAB_800487d0
                    spu66._08.set(1);
                    //LAB_800487d4
                    spu66._18.set(0);
                  } else if(spu66._18.get() == 0) {
                    spu66._08.set(1);
                  } else {
                    //LAB_800487d4
                    spu66._18.set(0);
                  }

                  //LAB_800487d8
                  setKeyOff(channelIndex, voiceIndex);
                }
              }
            }
          }
        }
      }

      //LAB_800487e4
    }

    spu124.sssqOffset_00c.add(0x3L);
    return channelIndex;
  }

  @Method(0x80048828L)
  public static void setKeyOn(final int channelIndex, final long voiceIndex) {
    if(voiceIndex < 16) {
      _800c4ac8.get(channelIndex).keyOnLo_0de.or(1 << voiceIndex);
    } else {
      //LAB_80048874
      _800c4ac8.get(channelIndex).keyOnHi_0e0.or(1 << voiceIndex - 16);
    }
  }

  @Method(0x8004888cL)
  public static void setNoiseMode(final int channelIndex, final int voiceIndex) {
    final SpuStruct44 spu44 = _800c6630;

    if(voiceIndex < 16) {
      spu44.noiseModeLo_16.or(1 << voiceIndex);
    } else {
      spu44.noiseModeHi_18.or(1 << voiceIndex - 16);
    }
  }

  @Method(0x800488d4L)
  public static void setKeyOff(final int channelIndex, final int voiceIndex) {
    final SpuStruct124 a2 = _800c4ac8.get(channelIndex);

    if(voiceIndex < 16) {
      a2.keyOffLo_0e2.or(1 << voiceIndex);
    } else {
      //LAB_80048920
      a2.keyOffHi_0e4.or(1 << voiceIndex - 16);
    }
  }

  @Method(0x80048938L)
  public static boolean FUN_80048938(final long a0, final int a1, final long a2) {
    if(a0 == 0xffL) {
      return true;
    }

    //LAB_80048950
    sshd10Ptr_800c6678.add(a1 * 0x10L);

    final boolean ret;
    if(a2 < sshd10Ptr_800c6678.deref()._00.get()) {
      ret = false;
    } else {
      ret = sshd10Ptr_800c6678.deref()._01.get() >= a2;
    }

    //LAB_80048988
    sshd10Ptr_800c6678.sub(a1 * 0x10L);

    //LAB_80048990
    return ret;
  }

  /**
   * @param note 0-127, numeric representation of musical note, e.g. 60 = middle C
   */
  @Method(0x80048998L)
  public static int calculateSampleRate(final int a0, final int note, final long a2, final long a3, final long a4) {
    // There are 12 notes per octave, %12 is likely getting the note, and /12 the octave

    if(note < a0) {
      return (int)(_8005967c.offset(((12 - (a0 - note) % 12) * 16 + (int)a4 * (int)(a3 - 64) / 4 + 0xd0L + (short)a2) * 0x2L).get() >> ((a0 - note) / 12 + 1));
    }

    //LAB_80048a38
    return (int)(_8005967c.offset(((note - a0) % 12 * 16 + (int)a4 * (a3 - 64) / 4 + 0xd0L + (short)a2) * 0x2L).get() << (note - a0) / 12);
  }

  @Method(0x80048ab8L) //TODO this appears to be calculating volume
  public static int FUN_80048ab8(final int channelIndex, final int a1, final int a2) {
    final int t2 = (int)(sssqDataPointer_800c6680.deref(1).offset(0xeL).get()
      * _800c6674.deref(1).offset(0x1L).get()
      * _800c4ab0.deref(1).offset(_800c4ac8.get(channelIndex)._003.get()).offset(0x2L).get()
      * sshd10Ptr_800c6678.deref()._0b.get()
      / 0x4000
      * _80059f3c.offset(a2).offset(a1 / 2 & 0x7ffeL).get());

    final int v1;
    if(sshd10Ptr_800c6678.deref()._0a.get() == 0) {
      v1 = t2 / 0x80;
    } else {
      v1 = sshd10Ptr_800c6678.deref()._0a.get() << 8 | (short)(t2 / 0x80) / 0x80;
    }

    //LAB_80048b88
    return v1 & 0xffff;
  }

  @Method(0x80048b90L)
  public static int FUN_80048b90(final int a0, final int a1) {
    sshd10Ptr_800c6678.add(a1 * 0x10L);

    final int a1_0;
    if(a0 == 4) {
      a1_0 = sshd10Ptr_800c6678.deref()._0c.get();
    } else {
      //LAB_80048bc4
      a1_0 = (int)_80059b3c.offset(_80059b3c.offset(sshd10Ptr_800c6678.deref()._0c.get() / 4 + _800c6674.deref(1).offset(0x2L).get() / 4 * 0x20L).get() / 4 + sssqDataPointer_800c6680.deref(1).offset(0x4L).get() / 4 * 0x20L).get();
    }

    //LAB_80048c1c
    sshd10Ptr_800c6678.sub(a1 * 0x10L);
    return a1_0 & 0xffff;
  }

  @Method(0x80048c38L) //TODO return SssqFile
  public static long FUN_80048c38(final int playableSoundIndex, long a1, long a2) {
    assert playableSoundIndex >= 0;
    assert a1 >= 0;
    assert a2 >= 0;

    final PlayableSoundStruct sound = playableSoundPtrArr_800c43d0.get(playableSoundIndex);
    final SpuStruct44 a3 = _800c6630;
    final SshdFile sshd = sound.sshdPtr_04.deref();
    a3.sshdPtr_08.set(sshd);
    sshdPtr_800c4ac0.set(sshd);
    final long t1 = sshd.getAddress() + sshd.ptr_1c.get();

    if((sshd.ptr_1c.get() & 0x1) != 0) {
      LOGGER.error("PTR_1C HAS INVALID VALUE %08x".formatted(sshd.ptr_1c.get()), new Throwable());
    }

    _800c4abc.setu(t1);

    if(sshd.ptr_20.get() != -1) {
      if((playableSoundIndex & 0x80) == 0) {
        if(a3._03.get() != 0) {
          if(sound.used_00.get()) {
            a1 = a1 & 0xffff;

            if(MEMORY.ref(2, t1).offset(0x0L).get() >= a1) {
              a1 = t1 + a1 * 0x2L;
              final long v1 = MEMORY.ref(2, a1).offset(0x2L).get();
              if(v1 != 0xffff) {
                final long v0 = MEMORY.ref(2, t1).offset(v1 & 0xfffe).get();
                a2 = a2 & 0xffff;
                if(v0 >= a2) {
                  _800c4aa8.setu(sshd.getAddress() + sshd.ptr_20.get());
                  _800c4ab0.setu(sshd.getAddress() + sshd.ptr_14.get());
                  return sshd.getAddress() + sshd.ptr_1c.get() + MEMORY.ref(2, t1).offset((a2 + (MEMORY.ref(2, a1).offset(0x2L).get() / 2)) * 2).offset(0x2L).get();
                }
              }
            }
          }
        }
      }
    }

    //LAB_80048d3c
    return 0;
  }

  @Method(0x80048d44L)
  public static long FUN_80048d44(final int playableSoundIndex, final long a1, final long a2) {
    final long v0 = FUN_80048c38(playableSoundIndex, a1, a2);
    if(v0 == 0) {
      assert false : "Error";
      return -0x1L;
    }

    final SpuStruct44 spu44 = _800c6630;

    //LAB_80048dac
    for(int channelIndex = 0; channelIndex < 24; channelIndex++) {
      final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
      if(spu124._027.get() == 0 && spu124._029.get() == 0) {
        spu124._029.set(1);
        spu124._02a.set(1);
        spu124._028.set(0);
        spu124._027.set(0);
        spu124._118.set(0);
        spu124._0e7.set(0);
        spu124.sssqPtr_010.setPointer(v0);
        spu124.sssqOffset_00c.set(0);
        spu124.playableSoundIndex_020.set(playableSoundIndex);
        spu124._024.set((int)a1);
        spu124._022.set((int)a2);

        if(spu44._23.get() != 0) {
          spu124.reverbEnabled_0ea.set(1);
          spu44._23.set(0);
        }

        //LAB_80048e10
        spu124._035.set(0);
        spu124._037.set(0);
        spu124._0e6.set(0);
        spu124.pitchShiftVolLeft_0ee.set((short)0);
        spu124.pitchShifted_0e9.set(0);
        spu124.pitchShiftVolRight_0f0.set((short)0);

        if(spu44.pitchShifted_22.get() != 0) {
          spu124.pitchShifted_0e9.set(1);
          spu124.pitch_0ec.set(spu44.pitch_24);
          spu124.pitchShiftVolLeft_0ee.set(spu44.pitchShiftVolLeft_26);
          spu124.pitchShiftVolRight_0f0.set(spu44.pitchShiftVolRight_28);
          spu44.pitchShifted_22.set(0);
          spu44.pitch_24.set(0);
          spu44.pitchShiftVolLeft_26.set((short)0);
          spu44.pitchShiftVolRight_28.set((short)0);
        }

        return channelIndex;
      }

      //LAB_80048e74
    }

    //LAB_80048e8c
    //LAB_80048e90
//TODO    assert false : "Error";
    return -0x1L;
  }

  @Method(0x80048eb8L)
  public static void sssqHandleEndOfTrack(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    if(spu124._02a.get() != 0) {
      spu124._0e7.set(1);
      spu124._118.set(0);
      spu124._02a.set(0);
      spu124._104.set(0);
      spu124._105.set(0);
      spu124.pitchShifted_0e9.set(0);
      spu124.reverbEnabled_0ea.set(0);
    } else {
      //LAB_80048f0c
      spu124.sssqOffset_00c.set(0x110L);
      spu124._028.set(0);
    }

    //LAB_80048f18
    //LAB_80048f30
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

      if(spu66.channelIndex_06.get() == channelIndex) {
        if(spu66._1a.get() == 0) {
          spu66._14.set(0);
          spu66._38.set(64);
        }
      }

      //LAB_80048f74
    }

    spu124._001.set(spu124.command_000);
  }

  @Method(0x80048f98L)
  public static void sssqHandleTempo(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
    final long v1 = spu124.sssqPtr_010.getPointer() + spu124.sssqOffset_00c.get();
    spu124.tempo_108.set((int)(MEMORY.ref(1, v1).offset(0x3L).get() << 8 | MEMORY.ref(1, v1).offset(0x2L).get()));
    spu124.sssqOffset_00c.add(0x4L);
  }

  @Method(0x80048fecL)
  public static void sssqHandleProgramChange(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    if(spu124._02a.get() == 0) {
      sssqDataPointer_800c6680.deref(1).offset(0x2L).setu(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x1L)); // Read patch number
      sssqDataPointer_800c6680.deref(1).offset(0xaL).setu(0x40L);
      sssqDataPointer_800c6680.deref(1).offset(0xbL).setu(0x40L);
    }

    //LAB_80049058
    spu124.sssqOffset_00c.add(0x2L);
  }

  @Method(0x8004906cL)
  public static void sssqHandleModulationWheel(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    if(spu124._02a.get() != 0) {
      //LAB_800490b8
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

        if(spu66._1a.get() == 1) {
          if(spu66._3e.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x3L).get()) {
            if(spu66._02.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x4L).get()) {
              if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
                if(spu66.channelIndex_06.get() == channelIndex) {
                  if(spu66.used_00.get()) {
                    spu66._14.set(1);
                    spu66._16.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());
                  }
                }
              }
            }
          }
        }

        //LAB_80049150
      }

      spu124.sssqOffset_00c.add(0x5L);
      return;
    }

    //LAB_80049178
    sssqDataPointer_800c6680.deref(1).offset(0x9L).setu(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());

    //LAB_800491b0
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);
      if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
        if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
          if(spu66.channelIndex_06.get() == channelIndex) {
            if(spu66.used_00.get()) {
              spu66._14.set(1);
              spu66._16.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());
            }
          }
        }
      }

      //LAB_80049228
    }

    spu124.sssqOffset_00c.add(0x3L);
  }

  @Method(0x80049250L)
  public static void sssqHandleBreathControl(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    final int t0 = 240 / (60 - (int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get() * 58 / 127);

    //LAB_800492dc
    //LAB_800492f4

    if(spu124._02a.get() != 0) {
      //LAB_80049318
      for(int i = 0; i < 24; i++) {
        final SpuStruct66 spu66 = _800c3a40.get(i);

        if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
          if(spu66._3e.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x3L).get()) {
            if(spu66._02.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x4L).get()) {
              if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
                if(spu66.channelIndex_06.get() == channelIndex) {
                  if(spu66.used_00.get()) {
                    spu66._3c.set(t0);
                  }
                }
              }
            }
          }
        }

        //LAB_800493ac
      }

      spu124.sssqOffset_00c.add(0x5L);
      return;
    }

    //LAB_800493d4
    sssqDataPointer_800c6680.deref(1).offset(0xcL).setu(t0);

    //LAB_800493f4
    for(int i = 0; i < 24; i++) {
      final SpuStruct66 spu66 = _800c3a40.get(i);

      if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
        if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
          if(spu66.channelIndex_06.get() == channelIndex) {
            if(spu66.used_00.get()) {
              spu66._3c.set(t0);
            }
          }
        }
      }

      //LAB_80049458
    }

    spu124.sssqOffset_00c.add(0x3L);
  }

  @Method(0x80049480L)
  public static void sssqHandlePortamento(final int channelIndex) {
    final SpuStruct44 struct44 = _800c6630;
    final SpuStruct124 struct124 = _800c4ac8.get(channelIndex);
    final long sssq = sssqPtr_800c4aa4.get();

    //LAB_800494d0
    for(int t2 = 0; t2 < 24; t2++) {
      final SpuStruct66 struct66 = _800c3a40.get(t2);

      if(struct66.used_00.get()) {
        if(struct66._1a.get() == 1) {
          if(struct66.playableSoundIndex_22.get() == struct124.playableSoundIndex_020.get()) {
            final long a2 = sssq + struct124.sssqOffset_00c.get();

            if(struct66._3e.get() == MEMORY.ref(1, a2).offset(0x4L).get()) {
              if(struct66._02.get() == MEMORY.ref(1, a2).offset(0x5L).get()) {
                if(struct66.channelIndex_06.get() == channelIndex) {
                  if(struct66._62.get() != 0) {
                    struct66._4e.set(struct124._11c.get());
                  }

                  //LAB_80049578
                  struct66.sssqEntry_44.set(1);
                  struct66._60.set((int)MEMORY.ref(1, a2).offset(0x3L).get());
                  struct66._62.set((int)(MEMORY.ref(1, a2).offset(0x2L).get() * 4 * struct44._42.get() / 60));
                  struct66._64.set((int)(MEMORY.ref(1, a2).offset(0x2L).get() * 4 * struct44._42.get() / 60));
                }
              }
            }
          }
        }
      }
    }

    struct124.sssqOffset_00c.add(0x6L);
  }

  @Method(0x80049638L)
  public static void sssqHandleVolume(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
    final SpuStruct44 spu44 = _800c6630;

    if(spu124._02a.get() != 0) {
      //LAB_800496bc
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

        if(spu66.used_00.get()) {
          if(spu66._1a.get() == 1) {
            if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
              if(spu66._3e.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x4L).get()) {
                if(spu66._02.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x5L).get()) {
                  if(spu66.channelIndex_06.get() == channelIndex) {
                    spu66._46.incr();
                    spu66._52.set(spu66._2c);
                    spu66._50.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x3L).get());
                    spu66._54.set((int)(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get() * 4 * spu44._42.get() / 60));
                    spu66._56.set((int)(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get() * 4 * spu44._42.get() / 60));
                  }
                }
              }
            }
          }
        }

        //LAB_800497dc
      }

      //LAB_80049950
      spu124.sssqOffset_00c.add(0x6L);
    } else {
      //LAB_800497fc
      sssqDataPointer_800c6680.deref(1).offset(0x3L).setu(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());
      sssqDataPointer_800c6680.deref(1).offset(0xeL).setu(sssqPtr_800c667c.deref(1).offset(0x0L).get() * sssqDataPointer_800c6680.deref(1).offset(0x3L).get() / 0x80);

      //LAB_8004985c
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

        if(spu66.used_00.get()) {
          if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
            if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
              if(spu66._08.get() != 1) {
                if(spu66.channelIndex_06.get() == channelIndex) {
                  spu124._003.set(spu66._2c.get());
                  voicePtr_800c4ac4.deref().voices[voiceIndex].LEFT.set(FUN_80048ab8(spu66.channelIndex_06.get(), FUN_80048b90(0, spu66._0e.get()), 0));
                  voicePtr_800c4ac4.deref().voices[voiceIndex].RIGHT.set(FUN_80048ab8(spu66.channelIndex_06.get(), FUN_80048b90(0, spu66._0e.get()), 1));
                }
              }
            }
          }
        }

        //LAB_80049930
      }

      //LAB_80049950
      spu124.sssqOffset_00c.add(0x3L);
    }
  }

  @Method(0x80049980L)
  public static void sssqHandlePan(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
    final SpuStruct44 spu44 = _800c6630;

    if(spu124._02a.get() != 0) {
      //LAB_80049a08
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);
        if(spu66.used_00.get()) {
          if(spu66._1a.get() == 1) {
            if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
              if(spu66._3e.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x4L).get()) {
                if(spu66._02.get() == sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x5L).get()) {
                  if(spu66.channelIndex_06.get() == channelIndex) {
                    spu66._48.set(1);
                    spu66._5a.set(spu66._4c);
                    spu66._58.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x3L).get());
                    spu66._5c.set((int)(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get() * 4 * spu44._42.get() / 60));
                    spu66._5e.set((int)(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get() * 4 * spu44._42.get() / 60));
                  }
                }
              }
            }
          }
        }

        //LAB_80049b28
      }

      //LAB_80049c88
      spu124.sssqOffset_00c.add(0x6L);
    } else {
      //LAB_80049b48
      sssqDataPointer_800c6680.deref(1).offset(0x4L).setu(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());

      //LAB_80049b80
      for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
        final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);
        if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
          if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
            if(spu66.channelIndex_06.get() == channelIndex) {
              if(spu66._08.get() != 1) {
                if(spu66.used_00.get()) {
                  spu66._4c.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());
                  spu124._003.set(spu66._2c.get());
                  voicePtr_800c4ac4.deref().voices[voiceIndex].LEFT.set(FUN_80048ab8(channelIndex, FUN_80048b90(0, spu66._0e.get()), 0));
                  voicePtr_800c4ac4.deref().voices[voiceIndex].RIGHT.set(FUN_80048ab8(channelIndex, FUN_80048b90(0, spu66._0e.get()), 1));
                }
              }
            }
          }
        }

        //LAB_80049c68
      }

      //LAB_80049c88
      spu124.sssqOffset_00c.add(0x3L);
    }
  }

  @Method(0x80049cbcL)
  public static void sssqHandleSustain(final int channelIndex) {
    assert false;
  }

  @Method(0x80049e2cL)
  public static void FUN_80049e2c(final int channelIndex) {
    final SpuStruct124 struct124 = _800c4ac8.get(channelIndex);
    struct124._037.set(1);
    struct124._0e6.set(1);

    final long v1 = sssqPtr_800c4aa4.get() + struct124.sssqOffset_00c.get();
    final long a0 = MEMORY.ref(1, v1).offset(0x4L).get();

    if(a0 == 0) {
      //LAB_80049ecc
      struct124._02c.set(MEMORY.ref(1, v1).offset(0x2L).get() | MEMORY.ref(1, v1).offset(0x3L).get() << 8);
      final long v0 = sssqPtr_800c4aa4.get() + struct124._02c.get();
      struct124._039.set((int)MEMORY.ref(1, v0).offset(0x0L).get());
    } else if(struct124._035.get() == a0) {
      struct124._035.set(0);
      struct124._037.set(0);
      struct124._0e6.set(0);
    } else {
      //LAB_80049ea0
      struct124._02c.set(MEMORY.ref(1, v1).offset(0x2L).get() + (MEMORY.ref(1, v1).offset(0x3L).get() << 8));
      struct124._035.incr();
      final long v0 = sssqPtr_800c4aa4.get() + struct124._02c.get();
      struct124._039.set((int)MEMORY.ref(1, v0).offset(0x0L).get());
    }

    //LAB_80049f00
    struct124.sssqOffset_00c.add(0x5L);
  }

  @Method(0x80049f14L)
  public static void sssqHandleDataEntry(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    switch(spu124._11f.get()) {
      case 0 -> {
        spu124._11f.set(0);
        spu124._11d.set(spu124._003.get());

        //LAB_8004a2a0
        spu124.sssqOffset_00c.add(0x3L);
        return;
      }

      //LAB_8004a050
      case 4 ->
        sshd10Ptr_800c6678.deref().adsrLo_06
          .and(0xff)
          .or(0x7f - spu124._003.get() << 8);

      case 5 ->
        sshd10Ptr_800c6678.deref().adsrLo_06
          .and(0xff)
          .or(0x7f - spu124._003.get() << 8)
          .or(0x8000);

      case 6 ->
        sshd10Ptr_800c6678.deref().adsrLo_06
          .and(0xff0f)
          .or((0x7f - spu124._003.get()) / 0x8 * 0x10);

      //LAB_8004a050
      case 7 ->
        sshd10Ptr_800c6678.deref().adsrLo_06
          .and(0xfff0)
          .or(spu124._003.get() / 0x8);

      //LAB_8004a114
      case 8 ->
        sshd10Ptr_800c6678.deref().adsrHi_08
          .and(0x3f)
          .or((0x7f - spu124._003.get()) * 0x40)
          .or(0x4000 - spu124._122.get());

      //LAB_8004a114
      case 9 ->
        sshd10Ptr_800c6678.deref().adsrHi_08
          .and(0x3f)
          .or((0x7f - spu124._003.get()) * 0x40)
          .or(0x8000)
          .or(0x4000 - spu124._122.get());

      //LAB_8004a114
      case 0xa ->
        sshd10Ptr_800c6678.deref().adsrHi_08
          .and(0xffc0)
          .or((0x7f - spu124._003.get()) / 4);

      case 0xb ->
        sshd10Ptr_800c6678.deref().adsrHi_08
          .and(0xffc0)
          .or((0x7f - spu124._003.get()) / 4)
          .or(0x20);

      case 0xc -> {
        if(spu124._003.get() > 0x40L) {
          spu124._122.set(0x4000);
        } else {
          //LAB_8004a178
          spu124._122.set(0);
        }
      }

      case 0xf -> {
        sssqSetReverbType(spu124._003.get());

        //LAB_8004a2a0
        spu124.sssqOffset_00c.add(0x3L);
        return;
      }

      case 0x10 -> {
        SsSetRVol(spu124._003.get(), spu124._003.get());

        //LAB_8004a2a0
        spu124.sssqOffset_00c.add(0x3L);
        return;
      }

      case 0x11 -> {
        FUN_8004c690(spu124._003.get());

        //LAB_8004a2a0
        spu124.sssqOffset_00c.add(0x3L);
        return;
      }

      case 0x12, 0x13 -> {
        FUN_8004c5e8(spu124._003.get());

        //LAB_8004a2a0
        spu124.sssqOffset_00c.add(0x3L);
        return;
      }
    }

    //LAB_8004a1d0
    //LAB_8004a1f0
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

      if(spu66._02.get() == 1) {
        if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
          if(spu66._1a.get() == 0) {
            if(spu66.channelIndex_06.get() == channelIndex) {
              if(spu124._120.get() == 0xff || spu66._0e.get() == spu124._120.get()) {
                //LAB_8004a274
                voicePtr_800c4ac4.deref().voices[voiceIndex].ADSR_LO.set(sshd10Ptr_800c6678.deref().adsrLo_06.get());
                voicePtr_800c4ac4.deref().voices[voiceIndex].ADSR_HI.set(sshd10Ptr_800c6678.deref().adsrHi_08.get());
              }
            }
          }
        }
      }

      //LAB_8004a28c
    }

    //LAB_8004a2a0
    spu124.sssqOffset_00c.add(0x3L);
  }

  @Method(0x8004a2c0L)
  public static void FUN_8004a2c0(final int channelIndex) {
    assert false;
  }

  @Method(0x8004a34cL)
  public static void FUN_8004a34c(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    final long v1 = spu124._003.get();
    if(v1 >= 0 && v1 < 0x10L) {
      //LAB_8004a44c
      spu124._11e.set(0x10);
      spu124._120.set(spu124._003.get());
    } else if(v1 == 0x10L) {
      //LAB_8004a430
      spu124._11e.set(1);
      //LAB_8004a398
    } else if(v1 == 0x14L) {
      //LAB_8004a3cc
      spu124._11e.set(0);
      spu124._11f.set(0);
      spu124._039.set(spu124.command_000.get());
      spu124._02c.set(spu124.sssqOffset_00c.get());
    } else if(v1 == 0x1eL) {
      //LAB_8004a3e8
      if(spu124._11d.get() == 0x7fL) {
        //LAB_8004a424
        spu124._037.set(1);
      } else if(spu124._035.get() >= spu124._11d.get()) {
        spu124._02c.set(0);
        spu124._037.set(0);
        spu124._035.set(0);
      } else {
        //LAB_8004a41c
        spu124._035.incr();

        //LAB_8004a424
        spu124._037.set(1);
      }

      //LAB_8004a428
      spu124._11e.set(0);
      //LAB_8004a3b8
    } else if(v1 == 0x7fL) {
      //LAB_8004a43c
      spu124._11e.set(2);
      spu124._120.set(0xff);
    }

    //LAB_8004a458
    spu124.sssqOffset_00c.add(0x3L);
  }

  @Method(0x8004a46cL)
  public static void sssqHandlePitchBend(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    sssqDataPointer_800c6680.deref(1).offset(0xaL).setu(sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x1L).get());

    //LAB_8004a4e4
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);
      if(spu66.channel_04.get() == (spu124.command_000.get() & 0xf)) {
        if(spu66.playableSoundIndex_22.get() == spu124.playableSoundIndex_020.get()) {
          if(spu66.channelIndex_06.get() == channelIndex) {
            if(spu66.used_00.get()) {
              voicePtr_800c4ac4.deref().voices[voiceIndex].ADPCM_SAMPLE_RATE.set(calculateSampleRate(spu66._40.get(), spu66._02.get(), spu66._36.get(), sssqDataPointer_800c6680.deref(1).offset(0xaL).get(), spu66._3a.get()));
              spu66._38.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x1L).get());
            }
          }
        }
      }

      //LAB_8004a598
    }

    spu124.sssqOffset_00c.add(0x2L);
  }

  @Method(0x8004a5e0L)
  public static void FUN_8004a5e0(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    //LAB_8004a618
    // Read varint
    for(int i = 0; i < 4; i++) {
      final long a0 = sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).get();
      spu124.sssqOffset_00c.incr();
      spu124._118.shl(7);

      if((a0 & 0x80L) == 0) {
        //LAB_8004a720
        spu124._118.or(a0);
        break;
      }

      spu124._118.or(a0 & 0x7fL);
    }

    //LAB_8004a664
    if(spu124._02a.get() != 0) {
      spu124.tempo_108.set(60);
      spu124.deltaTime_10a.set(480);
    }

    //LAB_8004a680
    if(spu124.tempo_108.get() == 0) {
      return;
    }

    spu124._114.set(spu124._118.get() * 10);
    long a0 = (long)spu124.tempo_108.get() * spu124.deltaTime_10a.get();

    //LAB_8004a6e8
    //LAB_8004a700
    final long a2 = a0 * 10 / (_800c6630._42.get() * 60L);
    final long v1;
    if(spu124._10c.get() != 0) {
      spu124._10c.set(0);
      v1 = spu124._114.get() - (a2 - spu124._110.get());
    } else {
      //LAB_8004a72c
      v1 = spu124._114.get() + spu124._110.get();
    }

    //LAB_8004a738
    //LAB_8004a748
    //LAB_8004a760
    a0 = (int)v1 % (int)a2;

    if((int)a2 < (int)(a0 * 0x2L)) {
      spu124._114.set(v1 + a2 - a0);
      spu124._10c.incr();
    } else {
      //LAB_8004a78c
      spu124._114.set(v1 - a0);
    }

    //LAB_8004a794
    //LAB_8004a7a4
    //LAB_8004a7bc
    //LAB_8004a7d8
    spu124._110.set((int)v1 % (int)a2);
    spu124._118.set(spu124._114.get() / (a2 & 0xffff_ffffL));

    //LAB_8004a7e4
  }

  @Method(0x8004a7ecL)
  public static void sssqReadEvent(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    final int command = (int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).get();
    if((command & 0x80) != 0) {
      spu124.command_000.set(command);
      spu124._001.set(command);
    } else {
      //LAB_8004a854
      spu124.sssqOffset_00c.decr();
      spu124.command_000.set(spu124._001.get());
    }

    //LAB_8004a860
    spu124._002.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x1L).get());
    spu124._003.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());
    spu124._005.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x3L).get());
  }

  @Method(0x8004a8b8L)
  public static void FUN_8004a8b8() {
    LAB_8004a8dc:
    for(int channelIndex = 0; channelIndex < 24; channelIndex++) {
      final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
      if(spu124._0e7.get() == 1 && spu124._029.get() == 1) {
        //LAB_8004a908
        for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
          if(_800c3a40.get(voiceIndex).channelIndex_06.get() == channelIndex) {
            continue LAB_8004a8dc;
          }
        }

        spu124._118.set(0);
        spu124._0e7.set(0);
        spu124.pitchShiftVolRight_0f0.set((short)0);
        spu124.pitchShiftVolLeft_0ee.set((short)0);
        spu124._104.set(0);
        spu124._105.set(0);
        spu124.playableSoundIndex_020.set(0);
        spu124._02a.set(0);
        spu124._029.set(0);
        _800c6630.pitchShifted_22.set(0);
      }

      //LAB_8004a96c
    }

    //LAB_8004a99c
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      if((voicePtr_800c4ac4.deref().voices[voiceIndex].ADSR_CURR_VOL.get() & 0x7fff) < 16) {
        final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);
        if(spu66._08.get() == 1) {
          if(spu66._1a.get() != 0 && _800c6630._0d.get() > 0) {
            _800c6630._0d.decr();
          }

          //LAB_8004aa04
          //LAB_8004aa0c
          for(int voiceIndex2 = 0; voiceIndex2 < 24; voiceIndex2++) {
            if(_800c3a40.get(voiceIndex2)._0a.get() > spu66._0a.get() && _800c3a40.get(voiceIndex2)._0a.get() != 0xffff) {
              _800c3a40.get(voiceIndex2)._0a.decr();
            }

            //LAB_8004aa48
          }

          //LAB_8004aa7c
          bzero(spu66.getAddress(), 0x66);
          spu66.channelIndex_06.set((short)-1);
          spu66._26.set(0xffff);
          spu66._24.set(0xffff);
          spu66.playableSoundIndex_22.set(0xffff);
          spu66._0a.set(0xffff);
          spu66._4e.set(120);

          if(_800c6630.voiceIndex_00.get() > 0) {
            _800c6630.voiceIndex_00.decr();
          }
        }
      }

      //LAB_8004aaec
    }
  }

  @Method(0x8004ab2cL)
  public static void spuDmaCallback() {
    voicePtr_800c4ac4.deref().SPUCNT.and(0xffcf);

    //LAB_8004ab5c
    if(_800c6630.hasCallback_38.get() != 0) {
      spuDmaCompleteCallback_800c6628.run();
    }
  }

  @Method(0x8004ad2cL)
  public static void FUN_8004ad2c(final int voiceIndex) {
    final SpuStruct66 struct66 = _800c3a40.get(voiceIndex);
    final SpuStruct124 struct124 = _800c4ac8.get(struct66._1a.get());

    if(struct66._1a.get() != 0) {
      final long a1 = playableSoundPtrArr_800c43d0.get(struct124.playableSoundIndex_020.get()).sshdPtr_04.getPointer();
      sshdPtr_800c4ac0.setPointer(a1);
      sssqDataPointer_800c6680.setu(playableSoundPtrArr_800c43d0.get(struct124.playableSoundIndex_020.get()).sshdPtr_04.getPointer() + MEMORY.ref(4, a1).offset(0x20L).get() + struct66.channel_04.get() * 0x10L + 0x10L);
    } else {
      //LAB_8004adf4
      sssqDataPointer_800c6680.setu(struct124.sssqPtr_010.getPointer() + struct66.channel_04.get() * 0x10L + 0x10L);
    }

    //LAB_8004ae10
    _800c3a40.get(voiceIndex)._28.set((int)sssqDataPointer_800c6680.deref(1).offset(0xeL).get());
    voicePtr_800c4ac4.deref().voices[voiceIndex].LEFT.set(FUN_8004ae94(voiceIndex, 0));
    voicePtr_800c4ac4.deref().voices[voiceIndex].RIGHT.set(FUN_8004ae94(voiceIndex, 1));
  }

  @Method(0x8004ae94L)
  public static int FUN_8004ae94(final int voiceIndex, final int a1) {
    final SpuStruct66 struct66 = _800c3a40.get(voiceIndex);

    final short a0 = (short)((struct66._28.get() * struct66._2a.get() * struct66._2c.get() * struct66._2e.get() >> 14) * (int)MEMORY.ref(2, struct66._30.getAddress()).offset(a1 * 2).get() >> 7);
    final short v0;
    if(struct66._4a.get() == 0) {
      v0 = a0;
    } else {
      v0 = (short)(struct66._4a.get() << 8 | a0 >> 7);
    }

    //LAB_8004af30
    return v0;
  }

  @Method(0x8004af3cL)
  public static int FUN_8004af3c(final long a0, final long a1, final long a2, final long a3) {
    //LAB_8004af6c
    //LAB_8004af84
    return (int)(a0 + (((a1 & 0xffL) - (a0 & 0xffL)) * a3 & 0xffL) / a2 & 0xffL);
  }

  @Method(0x8004af98L)
  public static void FUN_8004af98(final int channelIndex) {
    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    if(spu124._03c.get() != 0) {
      long s4 = 0;

      if(spu124._03e.get(5).get(0).get() != 0) {
        sssqPtr_800c667c.setu(spu124.sssqPtr_010.getPointer());

        if(sssqPtr_800c667c.deref(1).get() != spu124._03e.get(6).get(0).get()) {
          if(spu124._03e.get(7).get(0).get() != 0) {
            sssqPtr_800c667c.deref(1).setu(FUN_8004af3c(spu124._03e.get(6).get(0).get(), spu124._03e.get(9).get(0).get(), spu124._03e.get(8).get(0).get(), spu124._03e.get(7).get(0).get()));
            spu124._03e.get(7).get(0).decr();
          } else {
            //LAB_8004b064
            sssqPtr_800c667c.deref(1).setu(spu124._03e.get(6).get(0).get());
          }

          //LAB_8004b068
          FUN_8004c8dc((short)channelIndex, (int)sssqPtr_800c667c.deref(1).get());
          s4++;
        }
      }

      //LAB_8004b084
      //LAB_8004b088
      sssqDataPointer_800c6680.setu(spu124.sssqPtr_010.getPointer() + 0x10L);

      //LAB_8004b0a0
      for(int i = 0; i < 16; i++) {
        if(sssqDataPointer_800c6680.deref(1).offset(0x3L).get() != spu124._03e.get(1).get(i).get() && spu124._03e.get(0).get(i).get() == 0x1L) {
          if(spu124._03e.get(2).get(i).get() != 0) {
            sssqDataPointer_800c6680.deref(1).offset(0x3L).setu(FUN_8004af3c(spu124._03e.get(1).get(i).get(), spu124._03e.get(4).get(i).get(), spu124._03e.get(3).get(i).get(), spu124._03e.get(2).get(i).get()));
            spu124._03e.get(2).get(i).decr();
          } else {
            //LAB_8004b110
            sssqDataPointer_800c6680.deref(1).offset(0x3L).setu(spu124._03e.get(1).get(i).get());
          }

          //LAB_8004b114
          FUN_8004b464((short)channelIndex, (short)i, (short)sssqDataPointer_800c6680.deref(1).offset(0x3L).get());
          s4++;
        }

        //LAB_8004b130
        sssqDataPointer_800c6680.addu(0x10L);
      }

      if(s4 == 0) {
        //LAB_8004b15c
        for(int i = 0; i < 10; i++) {
          //LAB_8004b16c
          for(int n = 0; n < 16; n++) {
            spu124._03e.get(i).get(n).set(0);
          }
        }

        if(spu124._03a.get() != 0) {
          spu124._03a.set(0);
          FUN_8004d034((short)channelIndex, 1);
        }

        //LAB_8004b1c0
        spu124._03c.set(0);
      }
    }

    //LAB_8004b1c4
  }

  @Method(0x8004b1e8L)
  public static short FUN_8004b1e8(final int channelIndex, final long a1, final short a2, final long a3) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    final short ret;
    if(a2 == -1) {
      sssqPtr_800c667c.setu(spu124.sssqPtr_010.getPointer());
      spu124._03e.get(5).get(0).set(1);
      spu124._03e.get(6).get(0).set((int)a3);
      spu124._03e.get(7).get(0).set((int)a1);
      spu124._03e.get(8).get(0).set((int)a1);
      spu124._03e.get(9).get(0).set((int)sssqPtr_800c667c.deref(1).get());

      ret = (short)sssqPtr_800c667c.deref(1).get();
    } else {
      //LAB_8004b268
      sssqDataPointer_800c6680.setu(spu124.sssqPtr_010.getPointer() + (a2 + 1) * 0x10L);
      spu124._03e.get(0).get(a2).set(1);
      spu124._03e.get(1).get(a2).set((int)a3);
      spu124._03e.get(2).get(a2).set((int)a1);
      spu124._03e.get(3).get(a2).set((int)a1);
      spu124._03e.get(4).get(a2).set((int)sssqDataPointer_800c6680.deref(1).offset(0x3L).get());

      ret = (short)sssqDataPointer_800c6680.deref(1).offset(0x3L).get();
    }

    //LAB_8004b2b8
    spu124._03c.set(1);
    return ret;
  }

  @Method(0x8004b2c4L)
  public static void FUN_8004b2c4() {
    final SpuStruct44 spu44 = _800c6630;

    if(spu44.fadingIn_2a.get() != 0) {
      if(sssqFadeCurrent_8005a1ce.get() == spu44.fadeTime_2c.get()) {
        sssqFadeCurrent_8005a1ce.setu(0);
        spu44.fadingIn_2a.set(0);
      } else {
        //LAB_8004b310
        //LAB_8004b33c
        //LAB_8004b354
        final short vol = (short)(spu44.fadeInVol_2e.get() * sssqFadeCurrent_8005a1ce.get() / spu44.fadeTime_2c.get());
        setMainVolume(vol, vol);
        sssqFadeCurrent_8005a1ce.addu(0x1L);
      }
    }

    //LAB_8004b370
    if(spu44.fadingOut_2b.get() != 0) {
      if(sssqFadeCurrent_8005a1ce.get() == spu44.fadeTime_2c.get()) {
        sssqFadeCurrent_8005a1ce.setu(0);
        spu44.fadingOut_2b.set(0);
      } else {
        //LAB_8004b3a0
        //LAB_8004b3cc
        //LAB_8004b3e4
        //LAB_8004b410
        //LAB_8004b428
        final short l = (short)(spu44.fadeOutVolL_30.get() * (spu44.fadeTime_2c.get() - sssqFadeCurrent_8005a1ce.get()) / spu44.fadeTime_2c.get());
        final short r = (short)(spu44.fadeOutVolR_32.get() * (spu44.fadeTime_2c.get() - sssqFadeCurrent_8005a1ce.get()) / spu44.fadeTime_2c.get());
        setMainVolume(l, r);
        sssqFadeCurrent_8005a1ce.addu(0x1L);
      }
    }

    //LAB_8004b450
  }

  @Method(0x8004b464L)
  public static void FUN_8004b464(final short channelIndex, final short sssqEntry, final short a2) {
    final SpuStruct124 struct124 = _800c4ac8.get(channelIndex);
    final long sssq = struct124.sssqPtr_010.getPointer();
    sssqPtr_800c667c.setu(sssq);
    final long entry = sssq + 0x10 + sssqEntry * 0x10;
    sssqDataPointer_800c6680.setu(entry);
    MEMORY.ref(1, entry).offset(0x03L).setu(a2);
    sssqDataPointer_800c6680.deref(1).offset(0xeL).setu(a2 * sssqPtr_800c667c.deref(1).get() >> 7);

    //LAB_8004b514
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 struct66 = _800c3a40.get(voiceIndex);

      if(struct66.used_00.get()) {
        if(struct66._1a.get() == 0) {
          if(struct66.playableSoundIndex_22.get() == struct124.playableSoundIndex_020.get()) {
            if(struct66.channelIndex_06.get() == channelIndex) {
              if(struct66.sssqEntry_44.get() == sssqEntry) {
                FUN_8004ad2c(voiceIndex);
              }
            }
          }
        }
      }
    }
  }

  @Method(0x8004b5bcL)
  public static int maxShort(final long a, final long b) {
    return Math.max((short)a, (short)b);
  }

  @Method(0x8004b5e4L)
  public static short FUN_8004b5e4(final short a0, final short a1) {
    final short a2;
    if(a1 < 0) {
      if(a0 < -a1) {
        a2 = (short)-a0;
      } else {
        a2 = a1;
      }

      //LAB_8004b618
      //LAB_8004b620
    } else if(a0 < a1) {
      a2 = a0;
    } else {
      a2 = a1;
    }

    //LAB_8004b638
    return a2;
  }

  /**
   * Scale a value using a 12-bit fixed-point multiplier.
   *
   * Dunno if negative scaling is right. It seems to match the ASM but does not yield the results I would expect.
   */
  @Method(0x8004b644L)
  public static short scaleValue12(final short value, final short multiplier12) {
    final int v0;
    final int v1;
    if(value >= 0) {
      //LAB_8004b660
      v0 = multiplier12 & 0x7fff;
      v1 = value & 0xffff;
    } else {
      v0 = (value & 0x7f) << 7;
      v1 = multiplier12 & 0x7fff;
    }

    //LAB_8004b668
    final int ret;
    if(multiplier12 >= 0) {
      ret = v1 * v0 >> 12;
    } else {
      ret = 0x7fff - (v1 * v0 >> 12);
    }

    //LAB_8004b688
    return (short)ret;
  }

  @Method(0x8004b694L)
  public static void spuDmaTransfer(final int transferDirection, final byte[] data, final int addressInSoundBuffer) {
    if(transferDirection != 0) {
      throw new RuntimeException("Read from SPU not supported");
    }

    SPU.directWrite(addressInSoundBuffer, data);
  }

  /**
   * @param status 1 means "in sequenced audio tick", 0 means "not in sequenced audio tick", any other value gets the current status
   *
   * @return The current status (if changed, it returns the value it was just changed to, not the old value)
   */
  @Method(0x8004b7c0L)
  public static long sssqStatus(final long status) {
    if((int)status < 2) {
      sssqStatus_8005a1d0.setu(status);
    }

    //LAB_8004b7d0
    return sssqStatus_8005a1d0.get();
  }

  @Method(0x8004b7e0L)
  public static long sssqWaitForTickToFinish() {
    //LAB_8004b7fc
    for(int s0 = 0; s0 < 0x8_9d00; s0++) {
      if(sssqStatus(0x2L) == 0) {
        return 0;
      }
    }

    //LAB_8004b81c
    //LAB_8004b820
    return 0x1L;
  }

  @Method(0x8004b834L)
  public static void initSpu() {
    final SpuStruct44 spu44 = _800c6630;

    //LAB_8004b8ac
    for(int registerIndex = 0; registerIndex < 0x100; registerIndex++) {
      if(registerIndex != 0xd7) { // Status register is read-only
        MEMORY.ref(2, SPU.getAddress()).offset(registerIndex * 2).setu(0);
      }
    }

    voicePtr_800c4ac4.set(SPU);

    spu44._03.set(8);
    spu44.voiceIndex_00.set(0);
    spu44._0d.set(0);
    spu44._42.set(60);
    voicePtr_800c4ac4.deref().SPUCNT.set(0xc000); // SPU control - unmute; enable
    spuDmaTransfer(0, MEMORY.getBytes(_80011db0.getAddress(), 0x10), 0x1010);

    //LAB_8004b9e8
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final Voice voice = voicePtr_800c4ac4.deref().voices[voiceIndex];
      voice.LEFT.set(0);
      voice.RIGHT.set(0);
      voice.ADPCM_SAMPLE_RATE.set(0x1000);
      voice.ADPCM_START_ADDR.set(0x1010);
      voice.ADSR_LO.set(0);
      voice.ADSR_HI.set(0);
    }

    voicePtr_800c4ac4.deref().VOICE_KEY_ON.set(0xff_ffffL);
    voicePtr_800c4ac4.deref().VOICE_KEY_OFF.set(0xff_ffffL);

    //LAB_8004ba58
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      //LAB_8004ba74
      bzero(_800c3a40.get(voiceIndex).getAddress(), 0x64); //Not sure why this wasn't clearing the last one
    }

    //LAB_8004bab8
    for(int soundIndex = 0; soundIndex < 127; soundIndex++) {
      //LAB_8004bacc
      playableSoundPtrArr_800c43d0.get(soundIndex).used_00.set(false);
      playableSoundPtrArr_800c43d0.get(soundIndex).sshdPtr_04.clear();
      playableSoundPtrArr_800c43d0.get(soundIndex).soundBufferPtr_08.set(0);
    }

    //LAB_8004bb14
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      _800c3a40.get(voiceIndex)._4e.set(120);
    }
  }

  @Method(0x8004be7cL)
  public static void setSpuDmaCompleteCallback(@Nullable final Runnable callback) {
    if(callback == null) {
      _800c6630.hasCallback_38.set(0);
    } else {
      spuDmaCompleteCallback_800c6628 = callback;
      _800c6630.hasCallback_38.set(1);
    }
  }

  /**
   * @return Index into {@link Scus94491BpeSegment_800c#playableSoundPtrArr_800c43d0}, or -1 on error
   */
  @Method(0x8004bea4L)
  public static short loadSshdAndSoundbank(final byte[] soundbank, final SshdFile sshd, final int addressInSoundBuffer) {
    if(addressInSoundBuffer > 0x8_0000) {
      assert false : "Error";
      return -1;
    }

    if((addressInSoundBuffer & 0xf) != 0) {
      assert false : "Error";
      return -1;
    }

    if(sshd.magic_0c.get() != SshdFile.MAGIC) {
      assert false : "Error";
      return -1;
    }

    //LAB_8004bfac
    if(sshd.size_04.get() != 0) {
      spuDmaTransfer(0, soundbank, addressInSoundBuffer);
    }

    //LAB_8004bfe0
    //LAB_8004bfe4
    //LAB_8004bff0
    for(short i = 0; i < 127; i++) {
      final PlayableSoundStruct sound = playableSoundPtrArr_800c43d0.get(i);

      if(!sound.used_00.get()) {
        //LAB_8004bfc8
        sound.used_00.set(true);
        sound.sshdPtr_04.set(sshd);
        sound.soundBufferPtr_08.set(addressInSoundBuffer / 8);
        return i;
      }
    }

    //LAB_8004c024
    //LAB_8004c02c
    //LAB_8004c030
    assert false : "Error";
    return -1;
  }

  @Method(0x8004c114L)
  public static long sssqUnloadPlayableSound(final int playableSoundIndex) {
    if((playableSoundIndex & 0xff80) != 0) {
      //LAB_8004c1f0
      assert false : "Error";
      return -0x1L;
    }

    if(!playableSoundPtrArr_800c43d0.get(playableSoundIndex).used_00.get()) {
      //LAB_8004c1f0
      assert false : "Error";
      return -0x1L;
    }

    //LAB_8004c160
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

      if(spu66.used_00.get() && spu66.playableSoundIndex_22.get() == playableSoundIndex) {
        //LAB_8004c1e8
        LOGGER.error("Tried to unload PlayableSound %d while still in use", playableSoundIndex);
        LOGGER.error("", new Throwable());
        return -0x1L;
      }

      //LAB_8004c19c
    }

    final PlayableSoundStruct sound = playableSoundPtrArr_800c43d0.get((short)playableSoundIndex);
    sound.used_00.set(false);
    sound.sshdPtr_04.clear();
    sound.soundBufferPtr_08.set(0);
    return 0;
  }

  @Method(0x8004c1f8L)
  public static short FUN_8004c1f8(final int playableSoundIndex, final SssqFile sssq) {
    if((playableSoundIndex & 0xff80) != 0) {
      throw new IllegalArgumentException("Invalid playableSoundIndex " + playableSoundIndex);
    }

    if(sssq.magic_0c.get() != SssqFile.MAGIC) {
      throw new IllegalArgumentException("Invalid magic " + sssq.magic_0c.get());
    }

    //LAB_8004c258
    for(int channelIndex = 0; channelIndex < 24; channelIndex++) {
      final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

      if(spu124._027.get() == 0) {
        if(spu124._029.get() == 0) {
          if(!playableSoundPtrArr_800c43d0.get(playableSoundIndex).used_00.get()) {
            break;
          }

          sssqPtr_800c4aa4.setu(sssq.getAddress());
          spu124._027.set(1);
          spu124._028.set(0);
          spu124._029.set(0);
          spu124._02a.set(0);
          spu124._118.set(0);
          spu124.sssqPtr_010.set(sssq);
          spu124.sssqOffset_00c.set(0x110L);
          spu124.command_000.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).get());
          spu124._001.set(spu124.command_000.get());
          spu124._002.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x1L).get());
          spu124._003.set((int)sssqPtr_800c4aa4.deref(1).offset(spu124.sssqOffset_00c.get()).offset(0x2L).get());
          spu124.playableSoundIndex_020.set(playableSoundIndex);

          //LAB_8004c308
          for(int n = 0; n < 16; n++) {
            sssqPtr_800c4aa4.deref(1).offset(n * 0x10L).offset(0x1eL).setu(sssqPtr_800c4aa4.deref(1).get() * sssqPtr_800c4aa4.deref(1).offset(n * 0x10L).offset(0x13L).get() / 0x100L);
          }

          spu124.deltaTime_10a.set(sssq.deltaTime_02.get());
          spu124.tempo_108.set(sssq.tempo_04.get());
          return (short)channelIndex;
        }
      }

      //LAB_8004c364
    }

    //LAB_8004c380
    //LAB_8004c384
    //LAB_8004c388
    throw new RuntimeException("Didn't find sound");
  }

  @Method(0x8004c390L)
  public static long FUN_8004c390(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);

    if(spu124._028.get() != 0) {
      assert false : "Error";
      return -0x1L;
    }

    //LAB_8004c3d0
    spu124._027.set(0);
    spu124._028.set(0);
    spu124._029.set(0);
    spu124._02a.set(0);

    //LAB_8004c3e4
    return 0;
  }

  @Method(0x8004c3f0L)
  public static long FUN_8004c3f0(final int a0) {
    assert a0 >= 0;

    if(a0 >= 24) {
      assert false;
      return -0x1L;
    }

    //LAB_8004c420
    int a1 = 0;
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      if(_800c3a40.get(voiceIndex)._1a.get() != 0) {
        a1++;
      }

      //LAB_8004c44c
    }

    if(a0 < a1) {
      //LAB_8004c484
      assert false;
      return -0x1L;
    }

    _800c6630._03.set(a0);

    //LAB_8004c488
    return 0;
  }

  @Method(0x8004c494L)
  public static void sssqSetReverbType(final int type) {
    _800c6630.reverbType_34.set(type);

    if(type != 0) {
      SPU.VOICE_CHN_REVERB_MODE.set(0);
      SPU.SPUCNT.or(0x80); // Reverb enable
      SPU.SOUND_RAM_REVERB_WORK_ADDR.set((int)_80059f7c.offset((type - 1) * 0x42L).get());

      //LAB_8004c4fc
      for(int i = 0; i < 32; i++) {
        //TODO reverb setup
        MEMORY.ref(2, SPU.getAddress()).offset(0x1c0L).offset(i * 0x2L).setu(_80059f7c.offset(((type - 0x1L) * 0x21 + i + 0x1L) * 0x2L).get());
      }

      return;
    }

    //LAB_8004c538
    SPU.VOICE_KEY_ON.set(0);
    SPU.REVERB_OUT_L.set(0);
    SPU.REVERB_OUT_R.set(0);
    SPU.SPUCNT.and(0xff7f); // Reverb disable
  }

  /**
   * Sets the reverb volume for left and right channels. The value ranges from 0 to 127.
   */
  @Method(0x8004c558L)
  public static void SsSetRVol(final int left, final int right) {
    if(_800c6630.reverbType_34.get() != 0 && left < 0x80 && right < 0x80) {
      final int r;
      final int l;
      if(_800c6630.mono_36.get() != 0) {
        l = maxShort(left << 8, right << 8);
        r = l;
      } else {
        l = left << 8;
        r = right << 8;
      }

      //LAB_8004c5d0
      SPU.REVERB_OUT_L.set(l);
      SPU.REVERB_OUT_R.set(r);
    }

    //LAB_8004c5d8
  }

  @Method(0x8004c5e8L)
  public static void FUN_8004c5e8(final long a0) {
    assert false;
  }

  @Method(0x8004c690L)
  public static void FUN_8004c690(final long a0) {
    assert false;
  }

  @Method(0x8004c6f8L)
  public static void setMono(final int mono) {
    _800c6630.mono_36.set(mono);
  }

  @Method(0x8004c894L)
  public static void setMainVolume(final int left, final int right) {
    final int l;
    if((left & 0x80L) != 0) {
      l = (left << 7) + 0x7fff;
    } else {
      //LAB_8004c8a8
      l = left << 7;
    }

    //LAB_8004c8ac
    final int r;
    if((right & 0x80L) != 0) {
      r = (right << 7) + 0x7fff;
    } else {
      //LAB_8004c8c0
      r = right << 7;
    }

    //LAB_8004c8c4
    voicePtr_800c4ac4.deref().MAIN_VOL_L.set(l);
    voicePtr_800c4ac4.deref().MAIN_VOL_R.set(r);
  }

  @Method(0x8004c8dcL)
  public static long FUN_8004c8dc(final int channelIndex, final int a1) {
    assert channelIndex >= 0;

    if(channelIndex >= 24) {
      assert false : "Error";
      return -0x1L;
    }

    if(a1 >= 128) {
      assert false : "Error";
      return -0x1L;
    }

    if(_800c4ac8.get(channelIndex)._027.get() == 0) {
      // This is normal
//      assert false : "Error";
      return -0x1L;
    }

    sssqPtr_800c667c.setu(_800c4ac8.get(channelIndex).sssqPtr_010.getPointer());
    final long ret = sssqPtr_800c667c.deref(1).offset(0x0L).get();
    sssqPtr_800c667c.deref(1).offset(0x0L).setu(a1);
    sssqDataPointer_800c6680.setu(_800c4ac8.get(channelIndex).sssqPtr_010.getPointer() + 0x10L);

    //LAB_8004c97c
    for(int i = 0; i < 16; i++) {
      sssqDataPointer_800c6680.deref(1).offset(0xeL).setu(sssqDataPointer_800c6680.deref(1).offset(0x3L).get() * a1 >> 7);
      sssqDataPointer_800c6680.addu(0x10L);
    }

    //LAB_8004c9d8
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

      if(spu66.used_00.get() && spu66.playableSoundIndex_22.get() == _800c4ac8.get(channelIndex).playableSoundIndex_020.get() && spu66._1a.get() == 0 && spu66.channelIndex_06.get() == channelIndex) {
        FUN_8004ad2c(voiceIndex);
      }

      //LAB_8004ca44
    }

    //LAB_8004ca64
    //LAB_8004ca6c
    return (short)ret;
  }

  @Method(0x8004cb0cL)
  public static long FUN_8004cb0c(final int playableSoundIndex, int volume) {
    if(playableSoundIndex < 0) {
      throw new IllegalArgumentException("Negative playableSoundIndex");
    }

    if(volume < 0) {
      //TODO GH#3, GH#193
      // This happens during the killing blow in the first virage fight. In retail, a1 counts down from 0x7f to 0 (I'm assuming it's volume).
      // In the decomp, it jumps down from 0x7f to 0 to -3. It seems like the previous script frame is setting it to a position vector value...?
      LOGGER.error("Negative volume, changing to 0");
      volume = 0;

//      throw new RuntimeException("Negative a1");
    }

    final PlayableSoundStruct sound = playableSoundPtrArr_800c43d0.get(playableSoundIndex);
    sshdPtr_800c4ac0.set(sound.sshdPtr_04.deref());

    if(!sound.used_00.get()) {
      assert false : "Error";
      return -0x1L;
    }

    if(sound.sshdPtr_04.deref().ptr_20.get() == -1) {
      assert false : "Error";
      return -0x1L;
    }

    if((playableSoundIndex & 0xff80L) != 0) {
      assert false : "Error";
      return -0x1L;
    }

    if(volume >= 0x80) {
      assert false : "Error";
      return -0x1L;
    }

    sssqPtr_800c667c.setu(sound.sshdPtr_04.getPointer() + sound.sshdPtr_04.deref().ptr_20.get());
    final long ret = sssqPtr_800c667c.deref(1).offset(0x0L).get();
    sssqPtr_800c667c.deref(1).offset(0x0L).setu(volume);
    sssqDataPointer_800c6680.setu(sound.sshdPtr_04.getPointer() + sshdPtr_800c4ac0.deref().ptr_20.get() + 0x10L);

    //LAB_8004cbc8
    for(int i = 0; i < 24; i++) {
      final long v0 = sssqDataPointer_800c6680.deref(1).offset(0x3L).get();
      sssqDataPointer_800c6680.deref(1).offset(0xeL).setu((int)v0 * volume >> 7);
      sssqDataPointer_800c6680.addu(0x10L);
    }

    //LAB_8004cc1c
    for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
      final SpuStruct66 spu66 = _800c3a40.get(voiceIndex);

      if(spu66.used_00.get() && spu66._1a.get() == 1 && spu66.playableSoundIndex_22.get() == playableSoundIndex) {
        FUN_8004ad2c(voiceIndex);
      }

      //LAB_8004cc6c
    }

    //LAB_8004cc90
    return ret;
  }

  @Method(0x8004ccb0L)
  public static long sssqFadeIn(final int fadeTime, final int maxVol) {
    assert fadeTime >= 0;
    assert maxVol >= 0;

    final SpuStruct44 spu44 = _800c6630;

    if(fadeTime >= 0x100) {
      assert false : "Error";
      return -0x1L;
    }

    if(maxVol >= 0x80L) {
      assert false : "Error";
      return -0x1L;
    }

    if(spu44.fadingOut_2b.get() != 0) {
      assert false : "Error";
      return -0x1L;
    }

    setMainVolume(0, 0);
    spu44.fadingIn_2a.set(1);
    spu44.fadeTime_2c.set(fadeTime);
    spu44.fadeInVol_2e.set(maxVol);

    //LAB_8004cd30
    //LAB_8004cd34
    return 0;
  }

  @Method(0x8004cd50L)
  public static long sssqFadeOut(final short fadeTime) {
    if(fadeTime < 256 && _800c6630.fadingIn_2a.get() == 0) {
      final Spu spu = voicePtr_800c4ac4.deref();
      _800c6630.fadingOut_2b.set(1);
      _800c6630.fadeTime_2c.set(fadeTime);
      _800c6630.fadeOutVolL_30.set(spu.CURR_MAIN_VOL_L.get() >>> 8);
      _800c6630.fadeOutVolR_32.set(spu.CURR_MAIN_VOL_R.get() >>> 8);
      return 0;
    }

    //LAB_8004cdb0
    return -1;
  }

  @Method(0x8004cdbcL)
  public static void enableAudioSource(final long enabled, final long useCdAudio) {
    if(enabled != 0) {
      if(useCdAudio != 0) {
        voicePtr_800c4ac4.deref().SPUCNT.or(0x1); // Enable CD audio
        return;
      }

      //LAB_8004cdec
      voicePtr_800c4ac4.deref().SPUCNT.or(0x2); // Enable external audio
      return;
    }

    //LAB_8004ce08
    if(useCdAudio != 0) {
      voicePtr_800c4ac4.deref().SPUCNT.and(0xfffe); // Disable CD audio
      return;
    }

    //LAB_8004ce2c
    voicePtr_800c4ac4.deref().SPUCNT.and(0xfffd); // Disable external audio
  }

  @Method(0x8004ced4L)
  public static void setCdVolume(final int left, final int right) {
    final int l;
    final int r;
    if(_800c6630.mono_36.get() != 0) {
      l = maxShort((byte)left << 8, (byte)right << 8);
      r = l;
    } else {
      l = left << 8;
      r = right << 8;
    }

    //LAB_8004cf0c
    voicePtr_800c4ac4.deref().CD_VOL_L.set(l);
    voicePtr_800c4ac4.deref().CD_VOL_R.set(r);
  }

  @Method(0x8004cf8cL)
  public static void FUN_8004cf8c(final int channelIndex) {
    assert channelIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(channelIndex);
    final PlayableSoundStruct sound = playableSoundPtrArr_800c43d0.get(spu124.playableSoundIndex_020.get());

    sshdPtr_800c4ac0.setNullable(sound.sshdPtr_04.derefNullable());

    if(spu124._027.get() == 1) {
      if(sshdPtr_800c4ac0.deref()._10.get() != -1) {
        if(sound.used_00.get()) {
          spu124._028.set(1);
          spu124._0e8.set(0);
        }
      }
    }

    //LAB_8004d02c
    spu124._018.set(0);
  }

  @Method(0x8004d034L)
  public static void FUN_8004d034(final int voiceIndex, final int a1) {
    boolean s5 = false;
    final SpuStruct124 spu124 = _800c4ac8.get(voiceIndex);
    final PlayableSoundStruct playableSound = playableSoundPtrArr_800c43d0.get(spu124.playableSoundIndex_020.get());
    final SshdFile sshd = playableSound.sshdPtr_04.deref();
    sshdPtr_800c4ac0.set(sshd);

    if(spu124._027.get() == 1) {
      if(sshd._10.get() != -1) {
        if(playableSound.used_00.get()) {
          boolean doIt = false;

          if(a1 == 0) {
            //LAB_8004d13c
            spu124.sssqOffset_00c.set(0x110);
            spu124._028.set(0);
            spu124._018.set(1);
            spu124._0e8.set(0);
            spu124._035.set(0);
            doIt = true;
          } else if(a1 == 1) {
            //LAB_8004d134
            s5 = true;

            //LAB_8004d13c
            spu124.sssqOffset_00c.set(0x110);
            spu124._028.set(0);
            spu124._018.set(1);
            spu124._0e8.set(0);
            spu124._035.set(0);
            doIt = true;
            //LAB_8004d11c
          } else if(a1 == 2) {
            //LAB_8004d154
            if(spu124._028.get() != 0) {
              spu124._028.set(0);
              spu124._0e8.set(1);
              doIt = true;
              //LAB_8004d170
            } else if(spu124._018.get() == 1) {
              doIt = true;
            } else {
              spu124._028.set(1);
              spu124._0e8.set(0);
            }
          } else if(a1 == 3) {
            //LAB_8004d188
            if(spu124._028.get() != 0) {
              //LAB_8004d1b4
              spu124._028.set(0);
              spu124._0e8.set(1);
            } else if(spu124._018.get() != 1) {
              spu124._028.set(1);
              spu124._0e8.set(0);
            }
          }

          if(doIt) {
            //LAB_8004d1c0
            //LAB_8004d1c4
            //LAB_8004d1d8
            for(int i = 0; i < 24; i++) {
              final SpuStruct66 struct66 = _800c3a40.get(i);

              if(struct66.channelIndex_06.get() == voiceIndex) {
                if(struct66._1a.get() == 0) {
                  struct66.used_00.set(false);
                  struct66._08.set(1);
                  struct66._38.set(64);
                  struct66._14.set(0);
                  struct66._16.set(0);
                  setKeyOff(voiceIndex, i);

                  if(s5) {
                    final Voice voice = voicePtr_800c4ac4.deref().voices[i];
                    voice.ADSR_LO.set(0);
                    voice.ADSR_HI.set(0);
                  }
                }
              }
            }

            sssqDataPointer_800c6680.setu(spu124.sssqPtr_010.deref().entries_10.getAddress());

            //LAB_8004d27c
            for(int i = 0; i < 16; i++) {
              sssqDataPointer_800c6680.deref(1).offset(0x9L).setu(0);
              sssqDataPointer_800c6680.addu(0x10L);
            }

            voicePtr_800c4ac4.deref().VOICE_KEY_OFF_LO.set(spu124.keyOffLo_0e2.get());
            voicePtr_800c4ac4.deref().VOICE_KEY_OFF_HI.set(spu124.keyOffHi_0e4.get());
            spu124.keyOffLo_0e2.set(0);
            spu124.keyOffHi_0e4.set(0);
          }
        }
      }
    }

    //LAB_8004d2d4
  }

  @Method(0x8004d2fcL)
  public static short FUN_8004d2fc(final int channelIndex, final short a1, final short a2) {
    final SpuStruct124 struct124 = _800c4ac8.get(channelIndex);
    final long a3 = struct124.sssqPtr_010.getPointer(); // Seems this can be 1-byte aligned, so we can't deref without causing alignment crashes
    sssqPtr_800c667c.setu(a3);

    short ret = -1;

    if(a1 >= 0x100 || a2 >= 0x80) {
      throw new IllegalArgumentException();
    }

    final int v1 = struct124._028.get();

    if(v1 == 0) {
      //LAB_8004d3b0
      FUN_8004c8dc(channelIndex, 0);
      FUN_8004cf8c(channelIndex);
      struct124._03a.set(0);

      //LAB_8004d3c8
      ret = FUN_8004b1e8(channelIndex, a1, (short)-1, a2);
    } else if(v1 == 1 && MEMORY.ref(1, a3).offset(0x0L).get() < a2) {
      struct124._03a.set(0);
      ret = FUN_8004b1e8(channelIndex, a1, (short)-1, a2);
    }

    //LAB_8004d3f4
    //LAB_8004d3f8
    return ret;
  }

  @Method(0x8004d41cL)
  public static long FUN_8004d41c(final int channelIndex, final long a1, final long a2) {
    assert (short)channelIndex >= 0;
    assert (short)a1 >= 0;
    assert (short)a2 >= 0;

    if((int)a1 >= 0x100L) {
      //LAB_8004d49c
      assert false : "Error";
      return -0x1L;
    }

    if((int)a2 >= 0x80L) {
      assert false : "Error";
      return -0x1L;
    }

    if(_800c4ac8.get(channelIndex)._028.get() == 0) {
      assert false : "Error";
      return -0x1L;
    }

    if(a2 == 0) {
      _800c4ac8.get(channelIndex)._03a.set(1);
    }

    //LAB_8004d48c
    //LAB_8004d4a4
    return FUN_8004b1e8(channelIndex, a1, (short)-1, a2);
  }

  @Method(0x8004d4b4L)
  public static void sssqSetTempo(final int channelIndex, final int tempo) {
    if(tempo <= 960) {
      _800c4ac8.get(channelIndex).tempo_108.set(tempo);
    }
  }

  @Method(0x8004d4f8L)
  public static short sssqGetTempo(final int channelIndex) {
    return (short)_800c4ac8.get(channelIndex).tempo_108.get();
  }

  @Method(0x8004d52cL)
  public static int FUN_8004d52c(final int voiceIndex) {
    assert voiceIndex >= 0;

    final SpuStruct124 spu124 = _800c4ac8.get(voiceIndex);

    int a0 = spu124._0e8.get() << 1 | spu124._028.get();

    if(spu124._03c.get() != 0) {
      if(spu124._03a.get() == 0) {
        a0 = a0 | 1 << 2;
      }

      //LAB_8004d58c
      a0 = spu124._03a.get() << 3 | a0;
    }

    //LAB_8004d59c
    final SpuStruct44 spu44 = _800c6630;
    return spu44.fadingOut_2b.get() << 5 | spu44.fadingIn_2a.get() << 4 | a0;
  }

  @Method(0x8004d648L)
  public static long FUN_8004d648(final int playableSoundIndex, final long a1, final long a2) {
    if(sssqWaitForTickToFinish() == 0) {
      return (short)FUN_80048d44(playableSoundIndex, a1, a2);
    }

    //LAB_8004d68c
    //LAB_8004d690
    return -0x1L;
  }

  @Method(0x8004d6a8L)
  public static long sssqPitchShift(final int playableSoundIndex, final long a1, final long a2, final short pitchShiftVolLeft, final short pitchShiftVolRight, final short pitch) {
    final SpuStruct44 s1 = _800c6630;

    if(sssqWaitForTickToFinish() != 0) {
      return -1;
    }

    final int soundIndex;
    if((playableSoundIndex & 0x80) == 0) {
      soundIndex = playableSoundIndex;
    } else {
      soundIndex = playableSoundIndex & 0x7f;
      s1._23.set(1);
    }

    //LAB_8004d714
    s1.pitchShiftVolLeft_26.set(FUN_8004b5e4((short)0x1000, pitchShiftVolLeft));
    s1.pitchShiftVolRight_28.set(FUN_8004b5e4((short)0x1000, pitchShiftVolRight));
    s1.pitch_24.set(pitch);
    s1.pitchShifted_22.set(1);

    //LAB_8004d760
    return (short)FUN_80048d44(soundIndex, a1 & 0xffff, a2 & 0xffff);
  }

  @Method(0x8004d78cL)
  public static void FUN_8004d78c(final short a0) {
    if(sssqWaitForTickToFinish() == 0) {
      final int v1 = a0 & 0x7fff;

      if(v1 < 24 && a0 < 24) {
        final SpuStruct124 struct124 = _800c4ac8.get(v1);

        if(struct124._029.get() != 0) {
          struct124._118.set(0);
          struct124._02a.set(0);
          struct124._029.set(0);
          struct124._0e7.set(0);
          struct124._105.set(0);
          struct124._104.set(0);
          struct124._0e6.set(0);
          struct124._035.set(0);
          struct124._037.set(0);
        }

        //LAB_8004d824
        //LAB_8004d83c
        for(int voiceIndex = 0; voiceIndex < 24; voiceIndex++) {
          final SpuStruct66 struct66 = _800c3a40.get(voiceIndex);

          if(struct66._1a.get() != 0 && (struct66.channelIndex_06.get() == a0 || struct66.channelIndex_06.get() == v1)) {
            //LAB_8004d880
            if((a0 & 0x8000) != 0) {
              final Voice voice = voicePtr_800c4ac4.deref().voices[voiceIndex];
              voice.ADSR_LO.set(0);
              voice.ADSR_HI.set(0);
              struct66.used_00.set(false);
            }

            //LAB_8004d8a0
            struct66._08.set(1);

            voicePtr_800c4ac4.deref().VOICE_KEY_OFF.set(1 << voiceIndex);
          }
        }
      }
    }

    //LAB_8004d8fc
  }

  @Method(0x8004d91cL)
  public static void FUN_8004d91c(final long a0) {
    if(sssqWaitForTickToFinish() == 0) {
      //LAB_8004d96c
      for(int i = 0; i < 24; i++) {
        final SpuStruct66 spu66 = _800c3a40.get(i);
        final SpuStruct124 spu124 = _800c4ac8.get(i);

        if(spu66._1a.get() != 0) {
          spu66._08.set(1);

          if(a0 != 0) {
            final Voice voice = voicePtr_800c4ac4.deref().voices[i];
            voice.ADSR_LO.set(0);
            voice.ADSR_HI.set(0);
            spu66.used_00.set(false);
          }

          //LAB_8004d9b8
          voicePtr_800c4ac4.deref().VOICE_KEY_OFF.set(1L << i);

          //LAB_8004d9e8
//          wasteSomeCycles(0x2L);
        }

        //LAB_8004d9f0
        if(spu124._029.get() != 0) {
          spu124._118.set(0);
          spu124._029.set(0);
          spu124._02a.set(0);
          spu124._105.set(0);
          spu124._104.set(0);
          spu124.pitchShiftVolRight_0f0.set((short)0);
          spu124.pitchShiftVolLeft_0ee.set((short)0);
          spu124.pitchShifted_0e9.set(0);
          spu124._0e7.set(0);
        }

        //LAB_8004da24
      }
    }

    //LAB_8004da38
  }
}
