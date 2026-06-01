# Chroma Drop Product Spec

## 1. Overview
Chroma Drop is a color-drive **drop puzzle game** (Android mobile) where colored blocks fall from the top of the screen at a steady pace; you rotate and place them to create clusters of 4+ same-colored blocks that then clear with satisfying effects. The board fills gradually; reaching a threshold triggers Game Over. The core product value is a polished, minimal, high-feedback color-match puzzle with satisfying clearing physics, deterministic daily challenge seeds, combo multipliers, and star-rated mastery.

This spec defines the MVP behavior, primary flows, and acceptance criteria.

## 2. Problem Statement
Android users looking for polished, satisfying color-match puzzle experiences often find games that are ad-heavy, cluttered, or mechanically stale. iOS has many premium-feel color-clear/puzzle games; Android has fewer that offer tight controls, juice, and deterministic fairness in a lightweight package. There is a gap for a clean, minimal, high-quality color-drop puzzle that feels great on mobile and respects player time.

Chroma Drop solves this by offering:
- One-touch rotation and placement (tap to rotate, tap-hold or second tap to place)
- Physics-lite satisfying clears with particle feedback
- Deterministic seeded daily challenges + score attack
- Par-based star rating and mastery progression

## 3. Goals
- Deliver a deterministic, replayable puzzle loop with clear mastery goals (par stars).
- Make each session fast (30–120s) and satisfying with clear feedback.
- Provide offline-first lightweight gameplay with no mandatory account.
- Support daily challenge + score attack modes in MVP.

## 4. Non-Goals (MVP)
- Marketplace, cosmetics, gacha, or paid currencies.
- Multiplayer or async leaderboards requiring accounts.
- Level editor (post-MVP).
- Complex narrative or meta-progression layers.
- Cloud sync.

## 5. Personas
- Puzzle Casual: 15–45, likes Tetris-ish and color-match, plays in short bursts.
- Mastery Seeker: wants to optimize moves and beat par times/scores.
- Daily Player: enjoys seeded daily challenge and streak tracking.

## 6. Core User Stories
- As a player, I can play an endless color-drop session where I clear clusters of 4+ same-colored blocks to keep the board from filling up.
- As a player, I can rotate and place blocks with simple taps (one-touch rotation or tap-to-rotate, tap-to-place).
- As a player, I can see my combo multiplier and clear effects when I make clusters disappear.
- As a player, I can play daily seeded challenge that measures score vs par and awards 1–3 stars.
- As a player, I can see my best scores and star ratings per mode.
- As a player, I can undo one recent move with a single undo token (fairness).

## 7. Functional Requirements
1. Game Board
   - 10×16 grid (example) with falling piece placement from top. Pieces are 3–4 colored blocks in L/I/T shapes (Tetris-like) but with color-cluster clearing.
   - Blocks fall one row per timed interval; player can rotate piece (tap) and drop (tap or auto-land). 
   - When 4+ same-colored orthogonally connected blocks form, they clear simultaneously with particle feedback and score.
   - Clearing may cause blocks above to fall; chain clears possible.

2. Controls
   - Tap piece to rotate clockwise (or cycle through orientations).
   - Tap "drop" (or auto-drop after short delay) to place.
   - One "undo" button per run (consumes single token) to revert last placement (fairness).

3. Modes
   - Endless: Board fills gradually; game over when blocks cross top threshold. Score and combo recorded.
   - Daily: Seeded piece sequence + scoring target. Player gets 1–3 stars based on score compared to par. One-attempt daily; results saved locally.

4. Scoring & Progression
   - Clear score = base * (combo multiplier). Combo increments when multiple clears happen within short window.
   - Star thresholds set per daily. Persist best star per daily.
   - Local leaderboard (top 10) for endless high score.

5. Undo & Fairness
   - One undo per run. Cannot undo after game over.

6. Settings
   - Sound toggle, vibration toggle, colorblind mode (shape overlays optional).

7. Persistence
   - Local save for high scores, daily star states, undo-used flags per daily.

## 8. UX Requirements
- Responsive one-thumb play with big tap targets.
- Immediate, juicy feedback on clears (particles, screen shake, color flash).
- Clear game over and restart flow.
- Daily indicator and countdown to next daily.
- Accessibility: readable contrast, optional colorblind shapes.

## 9. Data Model (Conceptual)
- DailyRecord { date: YYYY-MM-DD, seed: long, bestScore: int, stars: 0–3, undoUsed: bool }
- GlobalStats { endlessHighScore: int, totalClears: long }
- Piece { id, shape[coords], color }

## 10. Success Metrics (Post-launch)
- Session length median ~45–90s for endless.
- Daily attempt rate >50% of active users on launch days.
- Star distribution: ~30%/50%/20% across 1/2/3 stars (balanced).
- Crash-free sessions >99%.

## 11. Risks
- Gameplay too easy or too hard; requires tuning of fall speed and scoring par.
- Undo fairness could be abused (once per run only).
- Content exhaustion for daily; need many seeds and balanced piece sequences.

## 12. Dependencies
- Android SDK with Jetpack Compose (target minSdk 24).
- Local datastore (Jetpack DataStore) for saves.
- Analytics optional (post-MVP).

## 13. Q&A Discovery
1. Piece behavior: does piece auto-lock after landing or only on tap? Assumption: auto-lock after short delay OR tap-to-lock; player can also "drop fast" with second tap.
2. Are cascades triggered after every clear or processed in batches? Assumption: batch process all clusters simultaneously per tick for fairness.
3. How many piece types and colors? Assumption: 6 colors, 5 Tetris-like shapes (I/L/T/O/S) but colored uniformly (one color per piece) to make matching intuitive.
4. Daily seed usage: seed determines piece sequence + colors? Assumption: piece sequence is seeded, colors randomized per seed in a reproducible way.
5. Star thresholds: should they be dynamic based on player performance? Assumption: fixed per-daily par set at generation time.
6. Undo: is it one per run or one per session? Assumption: one per run (resets on new game).

## 14. Acceptance Criteria (MVP)
- [x] Player can rotate piece (tap) and place piece (tap/drop).
- [x] Clusters of 4+ same-colored ortho blocks clear with effects.
- [x] Combo multiplier increments on multi-clear windows and decays.
- [x] Game over triggers when blocks cross top threshold.
- [x] Endless mode saves high score locally.
- [x] Daily challenge uses seeded sequence; one attempt per day.
- [x] Star rating 1–3 assigned and persisted per daily.
- [x] One undo per run works correctly.
- [x] Settings persist sound/vibration preferences.
- [x] All unit tests pass and build produces debug APK.