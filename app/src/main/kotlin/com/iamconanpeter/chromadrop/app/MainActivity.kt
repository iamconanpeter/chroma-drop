package com.iamconanpeter.chromadrop.app

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.iamconanpeter.chromadrop.core.GameEngine
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {
    private lateinit var surfaceView: SurfaceView
    private lateinit var holder: SurfaceHolder
    private val paint = Paint()
    private val scope = MainScope()
    private val engine = GameEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        surfaceView = SurfaceView(this)
        setContentView(surfaceView)
        holder = surfaceView.holder
        holder.addCallback(this)
        surfaceView.setOnClickListener {
            engine.lockCurrentBall()
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Start engine loop
        scope.launch {
            launch { engine.start() }
            launch { renderLoop() }
        }
    }

    private suspend fun renderLoop() {
        while (isActive) {
            val canvas: Canvas? = holder.lockCanvas()
            canvas?.let { drawGame(it) }
            holder.unlockCanvasAndPost(canvas)
            delay(16L)
        }
    }

    private fun drawGame(canvas: Canvas) {
        // Clear background
        canvas.drawColor(Color.BLACK)
        val state = engine.state.value
        // Draw current ball as a circle at top center
        state.currentBall?.let { color ->
            paint.color = when (color) {
                com.iamconanpeter.chromadrop.core.GameColor.RED -> Color.RED
                com.iamconanpeter.chromadrop.core.GameColor.GREEN -> Color.GREEN
                com.iamconanpeter.chromadrop.core.GameColor.BLUE -> Color.BLUE
                com.iamconanpeter.chromadrop.core.GameColor.YELLOW -> Color.YELLOW
                com.iamconanpeter.chromadrop.core.GameColor.PURPLE -> Color.MAGENTA
            }
            canvas.drawCircle(canvas.width / 2f, 200f, 50f, paint)
        }
        // Draw slot colors at bottom
        val slotY = canvas.height - 200f
        state.slotColors.forEachIndexed { index, color ->
            paint.color = when (color) {
                com.iamconanpeter.chromadrop.core.GameColor.RED -> Color.RED
                com.iamconanpeter.chromadrop.core.GameColor.GREEN -> Color.GREEN
                com.iamconanpeter.chromadrop.core.GameColor.BLUE -> Color.BLUE
                com.iamconanpeter.chromadrop.core.GameColor.YELLOW -> Color.YELLOW
                com.iamconanpeter.chromadrop.core.GameColor.PURPLE -> Color.MAGENTA
            }
            val x = (index + 1) * 120f
            canvas.drawCircle(x, slotY, 40f, paint)
        }
        // Draw score
        paint.color = Color.WHITE
        paint.textSize = 48f
        canvas.drawText("Score: ${state.score}", 20f, 60f, paint)
        canvas.drawText("Lives: ${state.lives}", 20f, 120f, paint)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        scope.cancel()
    }
}
