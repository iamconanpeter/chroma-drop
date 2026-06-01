# Technical Plan for Chroma Drop

## Architecture
- Kotlin Android app, single-activity architecture using Jetpack Compose for UI.
- Game engine: custom lightweight physics loop handling block falling and collision.
- Data layer: Repository pattern with Room for storing daily seed records and high scores.
- Networking: Retrofit to fetch daily seed config from a simple JSON endpoint (placeholder).
- Ads: Google Mobile Ads SDK (optional, deferred).

## Build
- Gradle with Kotlin DSL.
- Minimum SDK 21, target SDK 34.
- ProGuard/R8 rules to shrink size.
- CI: GitHub Actions executing `./gradlew test assembleDebug`.

## Performance Budgets
- APK size <30 MB.
- Frame time <16 ms (60 FPS) on mid‑range device.
- Memory <150 MB.

## Testing
- Unit tests for game logic (match detection, seed generation).
- UI tests with Compose test framework.
- CI runs tests and assembles debug APK.

## Milestones
1. Scaffold project (gradle init + Compose setup).
2. Implement block grid and physics.
3. Add match detection and scoring.
4. Implement daily seed system.
5. Add UI screens (main, game over, leaderboard).
6. Write unit tests.
7. CI pipeline.
8. Release candidate.