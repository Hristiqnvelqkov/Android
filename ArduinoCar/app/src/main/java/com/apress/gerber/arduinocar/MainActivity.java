package com.apress.gerber.arduinocar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    BluetoothDevice arduino = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Toast.makeText(this, "nemash bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (!adapter.isEnabled()) {
            Toast.makeText(this, "pusni bluetooth-a", Toast.LENGTH_SHORT).show();
        } else {
            Set<BluetoothDevice> devices = adapter.getBondedDevices();
            for (BluetoothDevice device : devices) {
                if (device.getName().equals("HC-06")) {
                    arduino = device;
                }
            }
        }
    }

    public void onLed(View view) {
        BluetoothSocket socket = null;
        UUID uuid = UUID.randomUUID();
        Connection connection = new Connection(arduino,1);
        connection.execute(uuid);
    }
    public void offLed(View view){
        BluetoothSocket socket = null;
        UUID uuid = UUID.randomUUID();
        Connection connection = new Connection(arduino,2);
        connection.execute(uuid);
    }
    public void stopCar(View view){
        BluetoothSocket socket = null;
        UUID uuid = UUID.randomUUID();
        Connection connection = new Connection(arduino,0);
        connection.execute(uuid);
    }
}