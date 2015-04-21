package com.uninorte.sensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Any_Sensor extends Fragment implements SensorEventListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private SensorManager mSensorManager;
    private Sensor mAcelSensor;
    private TextView mTextView;
    private EditText mEditTextX;
    private EditText mEditTextY;
    private EditText mEditTextZ;
    private Button mButton;
    private boolean mStarted;
    private static int wSensor;

    public static Any_Sensor newInstance(int sectionNumber, int wSensors) {
        wSensor = wSensors;
        Any_Sensor fragment = new Any_Sensor();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.textView);
        mEditTextX = (EditText) rootView.findViewById(R.id.editText);
        mEditTextY = (EditText) rootView.findViewById(R.id.editText1);
        mEditTextZ = (EditText) rootView.findViewById(R.id.editText2);
        mButton = (Button) rootView.findViewById(R.id.buttonAccel);
        if(wSensor == 0){
            mTextView.setText("Acelerometro");
        }else {
            if (wSensor == 1){
                mTextView.setText("Sensor de Campo Magnetico");
            }else{
                if (wSensor == 4){

                }
            }
        }
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
        mStarted = false;
        mButton.setText("Iniciar Sensor");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mStarted) {
                    startCapture(getActivity());
                } else {
                    stopCapturing();
                }
            }
        });

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        stopCapturing();
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
    }
    @Override
    public void onStop() {
        super.onStop();
        stopCapturing();
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
    }

    public void startCapture(Context context) {
        mStarted = true;
        mButton.setText("Detener Sensor");

        mSensorManager = (SensorManager) getActivity().getSystemService(context.SENSOR_SERVICE);
        if (wSensor == 0) {
            mAcelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }else {
            mAcelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        mSensorManager.registerListener(this, mAcelSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void stopCapturing() {
        mStarted = false;
        mButton.setText("Iniciar Sensor");
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mAcelSensor);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        stopCapturing();
        mEditTextX.setText("");
        mEditTextY.setText("");
        mEditTextZ.setText("");
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        mEditTextX.setText(event.values[0] + "");
        mEditTextY.setText(event.values[1] + "");
        mEditTextZ.setText(event.values[2] + "");
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
