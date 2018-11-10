package com.crexative.cronometro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Chronometer chronometer;
    private long timePause;
    private boolean isStart;
    private boolean isPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chronometer = findViewById(R.id.chronometer);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnPause).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long result = SystemClock.elapsedRealtime() - chronometer.getBase();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            String strUrl = "https://johanmosquera.me";
            // Intent implícito, donde abre el navegador
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(strUrl));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                if (!isStart){
                    chronometer.setBase(SystemClock.elapsedRealtime() - timePause);
                    Log.e("test start", SystemClock.elapsedRealtime() + " - " +timePause);
                    long result = SystemClock.elapsedRealtime() - timePause;
                    Log.e("test start", result+"");
                    chronometer.start();
                    isStart = true;
                    isPause = false;
                }
                break;
            case R.id.btnPause:
                if (!isPause){
                    //    segundos_transcurridos = tiempo_transcurrido - tiempo_inicio
                    //    10s = 35 (Actual) - 25 (Inicia)
                    timePause = SystemClock.elapsedRealtime() - chronometer.getBase();
                    Log.e("test stop", SystemClock.elapsedRealtime() + " - " + chronometer.getBase());
                    Log.e("test stop", timePause + "");
                    chronometer.stop();
                    isPause = true;
                    isStart = false;
                }
                break;
            case R.id.btnStop:
                if (isPause){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.stop();
                    timePause = 0;
                } else {
                    Toast.makeText(this, "Pausa el tiempo", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
