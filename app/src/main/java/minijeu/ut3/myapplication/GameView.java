package minijeu.ut3.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{

    private GameUpdateThread thread;
    private GameDrawThread thread2;
    private int x=0;
    private int y=0;
    private ArrayList<Carree> listdecarree;
    private int cpt;
    private int cpt_touch;
    private boolean viewAlreadyTouched = true;
    private int width;
    private int height;


    private Handler mHandler;
    private Random ran = new Random();

    private Runnable mUpdateTimeTask = new Runnable(){
        public void run(){
            int x = ran.nextInt();
            int y = ran.nextInt();
            listdecarree.add(new Carree(x, y));
            Log.d("runnable 1", "add carree"+listdecarree.toString());
            mHandler.postDelayed(mUpdateTimeTask, 1000);
        }
    };

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameUpdateThread(getHolder(), this);
        thread2 = new GameDrawThread(getHolder(), this);
        setFocusable(true);
        mHandler = new Handler();
        //mHandler.postDelayed(mUpdateTimeTask, 100);
        listdecarree = new ArrayList<>();
        listdecarree.add(new Carree(x, y));
        cpt = 0;
        cpt_touch = 1;
        setOnTouchListener(this);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        thread2.setRunning(true);
        thread2.start();
        width = this.getWidth();
        height = this.getHeight();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                thread2.setRunning(false);
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }


    public void update() {
        for (Carree c: listdecarree){
            if(cpt == 0) {
                c.setX((c.getX()+1*cpt_touch)%this.width);
            }else if (cpt == 1){
                c.setY((c.getY()+1*cpt_touch)%this.height);
            }else if (cpt == 2){
                if(c.getX()-1 < 0){
                    c.setX(this.width);
                }else{
                    c.setX((c.getX()-1*cpt_touch)%this.width);
                }
            }else if (cpt == 3){
                if(c.getY()-1 < 0){
                    c.setX(this.height);
                }else{
                    c.setY((c.getY()-1*cpt_touch)%this.height);
                }
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            for (Carree c: listdecarree){
                paint.setColor(Color.rgb(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
                canvas.drawRect(c.getX(), c.getY(), c.getX()+100, c.getY()+100, paint);
            }

        }
    }


    private Runnable mUpdateTouched = new Runnable(){
        public void run(){
            viewAlreadyTouched = true;
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(viewAlreadyTouched){
            viewAlreadyTouched = false;
            cpt = (cpt+1)%4;
            cpt_touch++;
            Log.d("touch", String.valueOf(cpt));
            mHandler.postDelayed(mUpdateTouched, 100);
        }
        return true;
    }

    /*
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        listEvent.add((int)motionEvent.getX());
        i++;
        Log.d("n° ", String.valueOf(i));
        if(motionEvent.getHistorySize() != 0){
            Log.d("touch", motionEvent.getHistoricalX(0)+ " getX : "+motionEvent.getX());
        }

        if(i>10){
            int direction = calculSwitchEvent.calculateDirection(listEvent);
            Log.d("switch direction", String.valueOf(direction));
            i=0;
        }
        return true;
    }
     */

}
