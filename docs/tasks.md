# Chroma Drop – Task List

## High‑Priority Tasks (MVP)
1. **Project Scaffold** – Create Android project structure via Codex CLI.
2. **Game Engine** – Implement `Board`, `Block`, and `GameEngine` with deterministic seed logic.
3. **UI Layer** – Build main game screen using Jetpack Compose (grid display, next block preview, score HUD).
4. **Particle Effects** – Simple burst animation on clear (Compose Canvas).
5. **Audio Feedback** – Integrate block drop, rotation, and clear sounds via `SoundPool`.
6. **Persistence** – Store daily seed, high‑score, and unlocked palettes using DataStore.
7. **Unit Tests** – Write JUnit tests for board clear detection and seed reproducibility.
8. **Gradle Validation** – Ensure `./gradlew test assembleDebug` passes with no errors.

## Post‑MVP (Backlog)
- Leaderboard sync (Firebase).
- Rewarded ads integration.
- Additional cosmetic skins.
- Accessibility improvements (talkback support).

## Dependencies & Tools
- Kotlin 1.9
- Jetpack Compose 1.6.0
- AndroidX DataStore
- JUnit5 + Truth
- Espresso (optional UI test scaffold)
