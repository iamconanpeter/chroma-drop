# Task List – Chroma Drop MVP

- [ ] **Setup repo & CI** – init git, add Gradle wrapper, configure GitHub Actions.
- [ ] **Create modules** – `:app`, `:game`, `:data`, `:ui`, `:ads`.
- [ ] **Implement GameEngine** (Kotlin): board representation, piece generation, match detection, combo scoring.
- [ ] **Deterministic Seed Service** – fetch daily JSON from placeholder endpoint, store in Room.
- [ ] **Compose UI** – main game screen, board grid, piece preview, score overlay.
- [ ] **Animations** – drop animation, clear sparkle, combo burst.
- [ ] **Input handling** – tap to rotate, place piece.
- [ ] **Game‑over handling** – overflow detection, show summary.
- [ ] **Ads wrapper** – integrate AdMob rewarded video (placeholder ID).
- [ ] **Unit tests** – GameEngine logic, seed service.
- [ ] **Instrumented UI tests** – start game, place piece, verify clear.
- [ ] **Build & validation** – `./gradlew test assembleDebug` passes.
- [ ] **Create GitHub repo** – `iamconanpeter/chroma-drop` and push.
- [ ] **Update tracking JSON** – set status to `shipped` and repo URL.
