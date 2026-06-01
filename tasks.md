# Chroma Drop Tasks

## 1. Design & Specification
- [x] Define product spec with Q&A discovery.
- [x] Finalize technical plan (architecture, modules, testing).
- [x] Map user stories → acceptance criteria.

## 2. Environment Setup
- [x] Initialize Android project (minSdk 24).
- [x] Add Compose, Hilt if needed, DataStore.
- [x] Configure Gradle, ProGuard/R8.

## 3. Core Engine Implementation
- [ ] Implement immutable `Board` and `Piece` data types.
- [ ] Implement `GameState` model.
- [ ] Implement `Engine` with tick handling, rotation, drop, clear detection, gravity, combo, and score.
- [ ] Implement undo stack (single snapshot).
- [ ] Write deterministic PRNG logic for seeded daily.

## 4. UI Layer
- [ ] Compose board UI (Canvas grid).
- [ ] Piece rendering & rotation animation.
- [ ] HUD: score, combo, timer, undo button.
- [ ] Settings toggle for sound and vibration.
- [ ] Daily challenge screen.

## 5. Persistence & Data
- [ ] Save high scores, daily records, settings.
- [ ] Persist undo availability per run.

## 6. Testing
- [ ] Unit tests for engine logic (rotation, placement, cluster detection, clearing, gravity, combo, undo, seed determinism).
- [ ] Instrumentation tests for UI flow (play a short game, undo, daily mode).

## 7. Validation
- [ ] Run `./gradlew test assembleDebug`.
- [ ] Verify build doesn’t crash; unit tests pass.

## 8. CI/CD
- [ ] Set up GitHub Actions to run tests on PR.
- [ ] Ensure code linting (ktlint).

## 8. Repository Operations
- [ ] Commit changes.
- [ ] Create remote repo: `https://github.com/iamconanpeter/chroma-drop`.
- [ ] Push.

## 9. Documentation
- [ ] Fill README.md with build/run instructions and gameplay notes.
- [ ] Package assets (sprites, sounds).
