# Chroma Drop Technical Plan

## 1. Architecture
- **Platform**: Android (minSdk 24, targetSdk 34).
- **Language**: Kotlin.
- **UI**: Jetpack Compose (declarative, reactive).
- **Architecture**: MVVM + Clean-ish layering (UI → ViewModel → Domain → Data).
  - `ui/` - Compose screens, theme, components.
  - `game/` - Core engine (pure Kotlin, no Android dependencies).
  - `data/` - Repositories, local persistence (DataStore).
- **Dependency Injection**: Manual (simple factories) or Hilt optional; start with manual.
- **Testing**: JUnit 5, Kotlin Coroutines Test, Turbine for flows.

## 2. Module Structure (Android modules)
Single `app` module for MVP. Could extract `:core` or `:game` if needed later.

```
app/
  src/main/
    java/com/iamconanpeter/chromadrop/
      ui/
        game/
          GameScreen.kt
          GameViewModel.kt
          Board.kt
          NextPiece.kt
          HUD.kt
          DailyChallengeScreen.kt
        settings/
          SettingsScreen.kt
      di/
        AppModule.kt
      ChromaDropApp.kt
      theme/
        Color.kt
        Theme.kt
        Type.kt
    res/
      ...
  src/test/
    java/com/iamconanpeter/chromadrop/
      game/
        GameEngineTest.kt
        PieceTest.kt
        BoardTest.kt
        ClusterClearTest.kt
        UndoTest.kt
        DailySeedTest.kt
```

## 3. Core Game Engine (pure Kotlin)
Design principles: deterministic, unit-testable, no random during seeded runs.

### Types
- `data class Pos(val x: Int, val y: Int)`
- `enum class Color { RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE }`
- `data class Cell(val pos: Pos, val color: Color)`
- `data class Piece(val id: Int, val shape: List<Pos>, val color: Color)` — shape in local coords (pivot at 0,0), color uniform across piece.
- `data class Board(val width: Int, val height: Int, val cells: Map<Pos, Color>)` — immutable ops.
- `sealed class Move { Rotate, Drop, Undo }`

### Engine State
```kotlin
data class GameState(
  val board: Board,
  val currentPiece: PieceWithPos,
  val nextPiece: Piece,
  val score: Long,
  val combo: Int,
  val undoAvailable: Boolean,
  val gameStatus: GameStatus, // PLAYING, GAME_OVER, PAUSED
  val tickCount: Long,
  val lastMoveTick: Long?,
  val pendingClears: Set<Pos>
)
```

### Determinism
- Piece generation driven by seeded PRNG (e.g., `java.util.Random(seed)`). For daily, seed derived from date + master salt. Same seed ⇒ same piece sequence.
- Color assignment: piece index -> deterministic palette index to ensure replayability.

### Ticks and Placement
- Fixed tick interval (e.g., 800 ms for easy, faster for harder modes). On tick, translate piece down one row if possible; if not, auto-place.
- Player can tap to rotate (immediate) and tap "drop" to hard drop (instant place). After placement, lock piece, spawn new piece; if spawn blocked ⇒ game over.

### Cluster Detection & Clearing
- After piece locks, detect clusters: BFS for ortho-connected same-color groups ≥4.
- Remove all cluster cells simultaneously. Compute score = base * (combo + 1). Combo window: if another clear occurs within N ticks (e.g., 2), increment combo; else reset to 0.
- Apply gravity: for each column, cells above fall down to fill empty spaces (process column-wise).
- Cascades: after gravity, check for new clusters; repeat until no clusters (batch processing per cascade level).

### Undo System
- Store previous `GameState` snapshot on each piece placement (excluding rotations). One-slot undo. Undo reverts state exactly (including random generator state?) — simpler: store deterministic event log (placed piece, cleared positions) and revert. MVP approach: store previous board + piece + score + combo before placement.

### Daily Challenge
- Daily uses seeded generator for piece sequence and color mapping.
- Score target computed from par heuristics (board efficiency, expected clears). MVP: fixed star thresholds by piece count.
- One attempt per calendar day; result stored with date.

## 4. UI / UX
- Board rendered via Compose `Canvas` or grid of `Box` for clarity.
- Controls: tap piece area to rotate, floating "drop" FAB or double-tap to hard drop.
- Visuals: particle burst on clear via `Canvas` draw or simple animating sprites; screen shake on big clears; color flash on combo.
- Colorblind mode: overlay small shapes (circle, square, triangle) inside cell colors.
- Daily screen: show next daily countdown, star summary, best score.

## 5. Persistence
- Use Jetpack DataStore (Proto or Preferences) for:
  - `endlessHighScore`
  - `dailyRecords: Map<LocalDate, DailyRecord>`
  - `settings`
- Backups: optional auto-backup via Android Auto Backup (enabled).

## 6. Performance & Quality
- Target 60fps on mid-range devices. Board updates minimal recompositions.
- Use coroutine scopes tied to ViewModel lifecycle.
- Prefer immutable ops and snapshotState for Compose reactivity.
- Memory: keep bitmaps minimal; use vector drawables or Canvas shapes.

## 7. Testing Strategy
- Unit tests for engine (pure Kotlin):
  - Piece rotation correctness.
  - Cluster detection (ortho, size ≥4).
  - Clearing and gravity logic.
  - Combo window behavior.
  - Undo correctness.
  - Seed determinism (same seed ⇒ same sequences).
- Instrumentation tests for UI flows (play 1 game, undo, daily attempt).
- Edge cases: full-board spawn block, cascading clears, max combo.

## 8. Build & CI
- Gradle with Kotlin DSL.
- Tasks: `./gradlew test assembleDebug` for local validation.
- CI: run unit tests + build debug APK on every push.

## 9. Milestones (MVP scope)
1. Implement core engine + unit tests (Week 1).
2. Basic Compose UI + gameplay loop (Week 2).
3. Daily system + persistence + undo (Week 3).
4. Polish: effects, settings, accessibility + QA (Week 4).
5. Validation + repo push.

## 10. Open Technical Questions
- Should gravity be column-wise sequential or simultaneous? Simultaneous is simpler and deterministic; adopt that.
- How to implement undo robustly? Keep previous state snapshot + RNG state. Or store event log (piece placed, clears). Snapshot is simpler and fine for one undo.
- Hard drop collision: piece instantly moves down as far as possible (standard hard drop). Implement via while-can-move loop.
- Should combo reset after a tick with no clears? Use tick-based decay: combo resets after M ticks (e.g., 2) with no clear event.
- Piece preview count: 1 next piece only for MVP.
- Pause: simple pause overlay that stops ticks.
