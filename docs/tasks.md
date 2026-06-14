# Chroma Drop - Task List

## Planning
- [ ] Draft product spec (done)
- [ ] Draft technical plan (done)
- [ ] Define Q&A discovery (see Q&A section below)

## Development Tasks
- [ ] Implement `core` module classes (`GameEngine`, `GameState`, `Ball`, `ColorPalette`).
- [ ] Write unit tests for engine logic.
- [ ] Create Android `app` module with `MainActivity` and `SurfaceView` renderer.
- [ ] Integrate engine with UI via `LiveData`/`StateFlow`.
- [ ] Implement input handling (tap to lock ball).
- [ ] Add scoring, combo, lives UI feedback.
- [ ] Add persistence for high scores (Room DB – optional).
- [ ] Write instrumented UI tests for main flow.
- [ ] Run lint, format, and CI checks.
- [ ] Assemble debug APK.

## Q&A Discovery (Game Planning Standard)
**Q:** What is the target audience?
**A:** Casual mobile gamers looking for quick 2‑minute sessions.

**Q:** How will monetization be handled?
**A:** Ads‑supported free version with optional ad‑free purchase.

**Q:** Any accessibility concerns?
**A:** Use color‑blind friendly palette and provide shape hints.

**Q:** What is the minimum device spec?
**A:** Android 5.0 (API 21) or later, 2 GB RAM, 100 MB free storage.

---