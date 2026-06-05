# Chroma Drop - Technical Specifications

## Game Concept
Chroma Drop is a color-matching puzzle game where players drop colored orbs to match groups of 3+ same-colored orbs.

## Core Mechanics
- **Drop System**: Players drag and drop colored orbs from inventory to play field
- **Color Matching**: 3+ adjacent orbs of same color disappear with cascade effect
- **Score System**: Points awarded for matches with bonus for larger groups
- **Level Progression**: Increasing difficulty with more colors and time limits

## Technical Requirements
- Android SDK API 21+ (Android 5.0+)
- OpenGL ES 2.0 for graphics rendering
- Kotlin for main implementation
- JUnit for testing

## Key Features
- 60 FPS smooth gameplay
- Touch controls with drag/drop
- Particle effects for matches
- Sound effects and background music
- Leaderboard integration

## Q&A Discovery
1. **What is the target audience?** Casual mobile gamers aged 15-45
2. **How many colors initially?** Start with 6 colors, increase to 8-10 in later levels
3. **What's the monetization strategy?** Free with optional ad removal and power-ups
4. **Offline capability?** Yes, but leaderboard requires internet
5. **Save system?** Progress saved locally with cloud backup option