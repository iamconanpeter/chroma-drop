package com.example.chromadrop

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState.initial())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    fun onRotateLeft() = handleCommand(GameCommand.RotateLeft)
    fun onRotateRight() = handleCommand(GameCommand.RotateRight)
    fun onDrop() = handleCommand(GameCommand.Drop)

    fun handleCommand(command: GameCommand) {
        _gameState.update { current ->
            when (command) {
                GameCommand.RotateLeft -> current.copy(
                    rotation = (current.rotation + 3) % 4 // stub: 4 rotation states
                )
                GameCommand.RotateRight -> current.copy(
                    rotation = (current.rotation + 1) % 4
                )
                GameCommand.Drop -> current.copy(
                    score = current.score + 10,          // stub scoring
                    activePieceY = current.activePieceY + 1 // stub drop movement
                )
            }
        }
    }
}

sealed interface GameCommand {
    data object RotateLeft : GameCommand
    data object RotateRight : GameCommand
    data object Drop : GameCommand
}

data class GameState(
    val score: Int,
    val rotation: Int,   // 0..3
    val activePieceX: Int,
    val activePieceY: Int,
    val isGameOver: Boolean
) {
    companion object {
        fun initial() = GameState(
            score = 0,
            rotation = 0,
            activePieceX = 4,
            activePieceY = 0,
            isGameOver = false
        )
    }
}
