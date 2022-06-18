package minijeu.ut3.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity  {

    int w;
    int h;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(new GameView(this));
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        int nb_partie_joue = sharedPref.getInt("nb_partie_joue", 0);
        nb_partie_joue ++;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("nb_partie_joue", nb_partie_joue);
        editor.apply();
        Log.d("nb_partie_joue", String.valueOf(nb_partie_joue));




    }



}