# Chroma Drop - Product Specification

## Overview
Chroma Drop is a fast‑paced multiplayer color‑matching game where players drop falling colored balls into a vertical slot to match a sequence of target colors. The goal is to maintain high scores while avoiding mismatches.

## Core Gameplay Loop
1. A **target sequence** of 3‑5 colors appears at the top.
2. Balls of random colors fall from the top at a controlled speed.
3. The player taps the screen to **lock** the current ball’s color.
4. The locked ball is added to the bottom of the slot.
5. If the sequence at the bottom matches the target, it is cleared and the player scores points.
6. Mis‑match resets the slot (combo resets, penalty). The game ends when the player decides to quit or loses all lives.

## Key Mechanics
- **Color generation**: Randomly selected from a palette of 5 primary colors.
- **Drop speed**: Increases gradually as the score rises.
- **Combo**: Streaks of matched sequences award combo multipliers.
- **Lives**: 3 lives. Loss of life when less than two balls are matched.

## Platforms & Technology
- **Android** (API 21+)
- **Kotlin** for logic and UI
- **SurfaceView** for rendering
- **Coroutines** for game loop timing
- **JUnit5** for unit tests

## Deliverables
- Android APK (release candidates)
- Compute of tests
- Documentation in this repo.

---