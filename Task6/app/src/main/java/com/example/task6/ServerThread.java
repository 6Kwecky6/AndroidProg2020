package com.example.task6;

import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static android.content.Context.WIFI_SERVICE;

public class ServerThread extends Thread{
    private final static String TAG = "ServerThread";
    private NsdManager.RegistrationListener registrationListener;
    private String serviceName = "Server side";
    private Context context;
    private NsdManager nsdManager;
    private ServerSocket ss=null;
    private Socket s = null;
    private PrintWriter out=null;
    private BufferedReader in=null;
    private TextView number1,number2,answer;
    private String[] numbers;
    private int added;

    ServerThread(Context _context, TextView number1,TextView number2, TextView answer){
        context=_context;
        this.number1=number1;
        this.number2=number2;
        this.answer=answer;
    }

    @Override
    public void run(){
        initializeRegistrationListener();
        connect();
    }


    public void connect(){
        try{
            Log.i(TAG,"start server....");
            ss=new ServerSocket(0);//Creates a random port.
            Log.i(TAG,"serversocket created, waiting for client...");
            Log.d(TAG,"Port: "+ss.getLocalPort()+"\nHost address: "+ss.getLocalSocketAddress());
            registerService(ss.getLocalPort());
            s=ss.accept();
            Log.i(TAG,"Client connected...");
            while(!isInterrupted()) {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String response = in.readLine();
                out = new PrintWriter(s.getOutputStream(), true);
                int res = inputConverter(response);
                out.println(res);
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                Log.d(TAG,"Destroying socket");
                if(out!=null)out.close();
                if(in!=null)in.close();
                if(s!=null)s.close();
                if(ss!=null)ss.close();

            }catch(Exception e){
                Log.d(TAG,"Destroying socket");
            }

        }
    }

    public void disconnect(){
        if(registrationListener!=null) {
            try {
                nsdManager.unregisterService(registrationListener);
            }finally {
            }
            registrationListener = null;
        }
    }

    //Method to translate the bufferedReader to a list of two ints that are to be added together
    private int inputConverter(String inp)throws IOException{
        Log.d(TAG,"Input: "+inp);
        numbers = inp.split(" ");
        if(numbers[0]!=null&&numbers[1]!=null) {
            number1.post(new Runnable() {
                @Override
                public void run() {
                    number1.setText(numbers[0]);
                }
            });
            number2.post(new Runnable() {
                @Override
                public void run() {
                    number2.setText(numbers[1]);
                }
            });
            Log.d(TAG,"Answer list: [0]: "+numbers[0]+" [1]: " + numbers[1]);
            added = (Integer.parseInt(numbers[0]) + Integer.parseInt(numbers[1]));
            answer.post(new Runnable() {
                @Override
                public void run() {
                    answer.setText(String.valueOf(added));
                }
            });
            return added;
        }else{
            Log.e(TAG,"incorrect format");
            return -1;
        }
    }

    private void registerService(int port){
        NsdServiceInfo serviceInfo =new NsdServiceInfo();
        serviceInfo.setServiceName(serviceName);
        serviceInfo.setServiceType("_addition._tcp.");
        Log.d(TAG,"registered port: "+port);
        serviceInfo.setPort(port);//Broadcasts the port number

        nsdManager = (NsdManager)context.getSystemService(Context.NSD_SERVICE);
        nsdManager.registerService(serviceInfo,NsdManager.PROTOCOL_DNS_SD,registrationListener);
    }

    private void initializeRegistrationListener(){
        registrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG,"Failed to register NsdService. Error code: "+errorCode);
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG,"Failed to unregister Nsdservice. Error code: "+errorCode);
            }

            @Override
            public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                serviceName=serviceInfo.getServiceName();
                Log.i(TAG,"NsdService registered");
                Log.d(TAG,"Host IP:"+serviceInfo.getHost()+"\nPort: "+serviceInfo.getPort());
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                Log.i(TAG,"NsdService unregistered");
            }
        };
    }

}

