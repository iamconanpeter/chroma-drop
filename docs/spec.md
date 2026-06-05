# Chroma Drop – Game Specification

## Core Fantasy / Hook
A simple, satisfying color‑matching block‑drop puzzle: colorful falling pieces, clear combos, and quick combo‑driven progress. 

> **3‑second hook**: “Drop a block, chain a combo, feel the satisfaction!”

## Retention
- **Daily seed**: deterministic piece sequence for replay and leaderboard.
- **Combo multiplier**: higher combos give more score, encouraging mastery.
- **Progression**: new colors and board variations unlock after reaching thresholds.

## Session Length Target
- 30 s quick loops, 2 min for replay/score‑attack.

## Skill vs. Luck
- Piece drop order is deterministic per day‑seed; skill in planning placements.

## Fail‑State / Frustration Control
- Perfect safety: placing any piece never ends the level; only board overflow ends.
- Retrograde: counters can’t cross‑day.

## Difficulty Ramp / Onboarding
- Start board 4×4; progressively 6×6 and 8×8.
- Early play shows three sample placements.

## Distinctive Mechanic
- **Color‑match rotation**: pieces rotate to match target; simulates a physics‑free pickup.

## Art/Animation Scope
- 10‑color palette, clean flat icons, minimal particle effects.

## Audio
- 1‑minute loop, simple chiptune, highlight beat on clear.

## Monetization
- Opt‑in rewarded video every 6th play.

## Technical Constraints & Performance
- Target coreference: <15 FPS drop animations; <200 ms per input.
- Heap < 30 MB on low‑end devices.
