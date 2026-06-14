# Chroma Drop - Technical Plan

## Architecture Overview
- **Modular Packages**
  - `app` – Android UI layer (Activities, Views)
  - `core` – Pure Kotlin game engine (state machine, physics, scoring)
  - `data` – Repository for high‑score persistence (Room DB, optional network sync)
- **Dependency Injection** – Simple Service Locator for `GameEngine`.
- **Threading** – Game loop runs on a `CoroutineScope(Dispatchers.Default)` updating the engine state at 60 Hz.

## Core Engine (`core` module)
| Class | Responsibility |
|-------|-----------------|
| `GameEngine` | Holds `GameState`, updates ball positions, checks matches, emits events. |
| `GameState` | Immutable snapshot: `targetColors`, `slotColors`, `score`, `lives`, `combo`. |
| `Ball` | Data class (`color: Color`, `y: Float`). |
| `ColorPalette` | Enum of supported colors, provides random selection. |
| `GameConfig` | Drop speed curve, max lives, combo thresholds. |

### Update Cycle (pseudo‑code)
```kotlin
while (running) {
    val delta = frameTime()
    engine.update(delta)
    // Dispatch UI events on Main thread via LiveData/StateFlow
    delay(frameDelay)
}
```

## UI Layer (`app` module)
- **MainActivity** hosts a `SurfaceView` for rendering.
- **Renderer** draws falling balls and the slot background.
- **Input**: Single tap locks current ball. Handled via `onTouchEvent`.
- **LiveData**: `engineState` observed to trigger UI redraws.

## Build System
- Gradle Kotlin DSL (`build.gradle.kts`).
- Apply `com.android.application` plugin with `kotlin-android`.
- Set `minSdk = 21`, `targetSdk = 33`.
- Use `org.jetbrains.kotlin:kotlin-stdlib` and `androidx.appcompat`.
- Add test dependencies: `junit:junit`, `androidx.test.ext:junit`, `androidx.test.espresso:espresso-core`.
- Configure `testOptions.unitTests.all {
    useJUnitPlatform()
}` for JUnit5.

## Testing Strategy
- **Unit Tests** (`core` module) – Pure Kotlin, no Android dependencies.
  - Validate match detection logic.
  - Verify drop speed scaling.
- **Instrumented Tests** – Basic UI sanity (launch MainActivity, tap interaction).
- Use `./gradlew test` for unit tests and `connectedAndroidTest` for instrumented tests.

## CI / Validation
- Run `./gradlew clean test assembleDebug`.
- Ensure test tasks succeed before committing.
- Lint (`./gradlew lint`) and format (`./gradlew ktlintFormat`).

## Timeline (MVP)
1. **Day 1‑2** – Core engine implementation + unit tests.
2. **Day 3‑4** – Android UI skeleton + render loop.
3. **Day 5** – Input handling and match logic integration.
4. **Day 6** – Scoring, combo, lives, UI polish.
5. **Day 7** – Build & test validation, bug‑fixes.
6. **Day 8** – Release candidate APK.

---