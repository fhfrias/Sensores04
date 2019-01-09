package com.iesvirgendelcarmen.dam.sensores04;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sensores04 extends AppCompatActivity implements SensorEventListener {

    int contador;
    boolean continuar;
    double x, y, z, a, amax;
    double campoTierraMax;
    double campoTierraMin;
    TextView tvax, tvay, tvaz, tva, tvaMax, tvG, cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores04);

        continuar = true;
        x = 0;
        y = 0;
        z = 0;
        a = 0;
        amax = 0;
        campoTierraMax = SensorManager.MAGNETIC_FIELD_EARTH_MAX;
        campoTierraMin = SensorManager.MAGNETIC_FIELD_EARTH_MIN;

        tvax = findViewById(R.id.campo_x);
        tvay = findViewById(R.id.campo_y);
        tvaz = findViewById(R.id.campo_z);
        tva = findViewById(R.id.campo_mag_total);
        tvaMax = findViewById(R.id.campo_terrestre);
        tvG = findViewById(R.id.campo_tierra);
        cont = findViewById(R.id.contador);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor campo = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, campo, SensorManager.SENSOR_DELAY_NORMAL);

        new MiAsyncTask().execute();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        continuar = true;
        new MiAsyncTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        continuar = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        a = Math.sqrt(x * x + y* y + z * z);
        if (a > amax){
            amax = a;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class MiAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onProgressUpdate(Void... progress) {
            super.onProgressUpdate(progress);
            tvax.setText("" + x);
            tvay.setText("" + y);
            tvaz.setText("" + z);
            tva.setText("" + a);
            tvaMax.setText("" + amax);
            tvG.setText("" + campoTierraMin + "-" + campoTierraMax);
            cont.setText("" + contador);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            while (true){
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                contador++;
                publishProgress();
            }
        }


    }
}
