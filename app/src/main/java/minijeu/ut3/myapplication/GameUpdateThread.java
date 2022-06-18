package minijeu.ut3.myapplication;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameUpdateThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private Canvas canvas;

    private Handler mHandler;

    private Runnable mUpdateTimeTask = new Runnable(){
        public void run(){
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    gameView.update();
                }
            } catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mHandler.postDelayed(mUpdateTimeTask, 50);
        }
    };



    public GameUpdateThread(SurfaceHolder surfaceHolder, GameView gameView) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        mHandler = new Handler();

    }

    @Override
    public void run() {

        //while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    mHandler.postDelayed(mUpdateTimeTask, 50);

                }
            } catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        //}
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
