# Chroma Drop – Game Specification

## Core Fantasy & 10‑Second Hook
**Fantasy:** Players control a cascade of colored blocks falling from the sky, arranging them to create vibrant clusters that explode in satisfying bursts. The core loop is a quick “place‑and‑clear” puzzle that rewards pattern recognition and timing.

**Hook:** Within the first 10 seconds, the player sees a falling block, rotates it, and clears a cluster for an instant visual and haptic reward – a clear “win” feeling that encourages another round.

## Q&A Discovery (Assumptions)
| Question | Answer |
|---|---|
| **Target audience** | Casual puzzle players (ages 10‑45), commuters, short‑session seekers. |
| **Session length target** | 30 seconds to 2 minutes per play, with quick retries. |
| **Skill vs luck balance** | Primarily skill: pattern recognition and quick rotation. Luck only in initial block sequence, mitigated by deterministic seeds for daily challenges. |
| **Fail‑state fairness** | Game over only when the stack reaches the top; a “grace‑drop” mechanic gives one safe block after a near‑full board. |
| **Difficulty ramp** | Levels introduce new colors and block shapes every 5 clears, with subtle speed increase. |
| **Distinctive mechanic** | Deterministic seeded block sequences allowing daily challenge seeds, plus a “color‑burst” visual effect unique to this game. |
| **Art/animation scope** | Minimalist flat colors, simple particle bursts on clear, 2‑D sprites – feasible for a 1‑person sprint. |
| **Audio/feedback plan** | Light per‑block click, satisfying burst sound on clear, subtle background ambience. |
| **Monetization** | Optional non‑intrusive rewarded ads for extra lives, and a cosmetic skin store (no pay‑to‑win). |
| **Technical constraints** | Target Android API 21+, low CPU/GPU usage, <50 MB APK size. |

*Assumptions marked where no user input was available.*

## Retention Loops & Differentiation
- **USP:** Deterministic daily‑seeded puzzles give a fresh, fair challenge each day.
- **Differentiators:**
  1. Seeded block sequences for daily leaderboards.
  2. Clean flat‑design with crisp particle bursts.
  3. Grace‑drop safety net to reduce frustration.
- **Retention Hooks:**
  1. Daily challenge leaderboard with streak rewards.
  2. Unlockable color palettes as cosmetic progression.
  3. Staggered difficulty tiers encouraging repeated play to master each tier.
- **Quality Bars:**
  1. Smooth 60 fps animation on low‑end devices.
  2. Clear audio‑visual feedback on every clear.
  3. Consistent UI layout for one‑hand play.
