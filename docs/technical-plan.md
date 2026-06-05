# Technical Plan – Chroma Drop (Android)

## Architecture
- **Kotlin + Jetpack Compose** for UI.
- **MVVM** with ViewModel + LiveData.
- **Room** for persisting daily seeds and high‑scores.
- **WorkManager** to fetch next day seed.
- **Google Play Billing** (optional) for ad‑free purchase.

## Core Modules
| Module | Responsibility |
|--------|----------------|
| `:app` | Android entry point, DI setup. |
| `:game` | Game logic (board state, piece generation, combo detection). |
| `:data` | Repository, Room DB, seed download. |
| `:ui` | Compose screens, animations. |
| `:ads` | AdMob rewarded video wrapper. |

## Game Loop
1. Load today’s seed from DB or network.
2. Initialise empty board.
3. On user tap, rotate piece to match target colour.
4. Place piece, check for matches ≥4, clear, award combo.
5. If board overflow → game‑over.
6. Persist score, update leaderboard.

## Performance
- All calculations on **background coroutine** (Dispatchers.Default).
- UI updates on **Main** via StateFlow.
- Limit allocations: reuse `IntArray` for board grid.

## Testing Strategy
- **Unit tests** for `GameEngine` (piece placement, match detection).
- **Instrumented tests** for Compose UI flow.
- **Snapshot tests** for board rendering.

## CI / Build
- Gradle wrapper present.
- GitHub Actions: `./gradlew test assembleDebug`.
- Lint + Detekt.

## Milestones
1. Scaffold project + modules (2 days).
2. Implement core engine with deterministic seed (3 days).
3. Compose UI + animation (3 days).
4. Ads integration & billing stub (2 days).
5. Test coverage ≥80 % (2 days).
6. Release candidate, QA, ship.
