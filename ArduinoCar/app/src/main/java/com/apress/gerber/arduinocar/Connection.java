package com.apress.gerber.arduinocar;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * Created by hriso on 5/20/2017.
 */

public class Connection extends AsyncTask<UUID,Void,Void> {
   private BluetoothDevice arduino;
    private int option;
    Connection(BluetoothDevice arduino,int option) {
        this.arduino=arduino;
        this.option=option;
    }
    @Override
    protected Void doInBackground(UUID... params) {
        BluetoothSocket socket=null;
       try{
        socket = arduino.createRfcommSocketToServiceRecord(params[0]);
           socket =(BluetoothSocket) arduino.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(arduino,1);
           socket.connect();
        OutputStream outstream=socket.getOutputStream();
           outstream.write(option);
    } catch (IOException e) {
        System.out.println("YEEee");
        e.printStackTrace();
    } catch (NoSuchMethodException e) {
           e.printStackTrace();
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       } catch (InvocationTargetException e) {
           e.printStackTrace();
       } finally {
           try {
               if(socket!=null)
                socket.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        return null;
    }
}
