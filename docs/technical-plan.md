# Chroma Drop – Technical Plan

## Architecture Overview
- **Language:** Kotlin (Android SDK)
- **UI Framework:** Jetpack Compose for declarative UI, enabling rapid UI iteration and easy state handling.
- **Game Loop:** A `ChromaGameEngine` singleton driven by a `Handler`/`Coroutine` ticking at 60 fps.
- **Data Model:** `Board` (2‑D array of `Cell?`), `Block` (shape + color), `GameState` (score, level, nextBlock, seeds).
- **Persistence:** `DataStore` to store daily seed, high‑score, unlocked palettes.
- **Testing:** Local unit tests for `Board` logic with JUnit5, instrumented UI tests with Espresso.

## Core Modules
| Module | Responsibility |
|---|---|
| `engine` | Game state updates, collision detection, line clear logic. |
| `ui` | Jetpack Compose screens: MainGameScreen, GameOverScreen, Settings. |
| `data` | Persistence of seeds, scores, cosmetics. |
| `audio` | Simple sound player using `SoundPool`. |
| `network` (future) | Sync daily leaderboard (optional). |

## Performance Targets
- **CPU:** <5% on a mid‑range device during active gameplay.
- **Memory:** <30 MB heap usage.
- **Battery:** Minimal wake‑locks; pause engine on `onPause`.

## Build System
- Gradle Kotlin DSL (`build.gradle.kts`).
- Use Android Gradle Plugin 8.2.0.
- Enable R8 minification for release builds.

## Development Steps (Codex‑driven)
1. Scaffold project with `codex exec "android create project ..."`.
2. Generate `engine` package with core board logic.
3. Implement Compose UI skeleton.
4. Add unit tests for `Board` clearance rules.
5. Wire audio feedback.
6. Configure DataStore persistence.
7. Set up CI workflow (GitHub Actions) to run `./gradlew test`.

## Risk Mitigation
- **Seed determinism:** Validate that given a seed, the block sequence is reproducible across devices.
- **Device fragmentation:** Test on API 21+ emulator configurations.
- **Ad integration:** Defer until after MVP validation.
