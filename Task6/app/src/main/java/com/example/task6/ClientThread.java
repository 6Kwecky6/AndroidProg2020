package com.example.task6;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.nfc.Tag;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    private final static String TAG = "Client";
    private Context context;
    private final String name = "client side";
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager nsdManager;
    private NsdServiceInfo serverInfo;
    private PrintWriter out = null;
    private BufferedReader in=null;
    private Socket s=null;
    private TextView answer;
    private String res;

    ClientThread(Context _context, TextView _answer){
        answer=_answer;
        context=_context;
        nsdManager = (NsdManager)context.getSystemService(Context.NSD_SERVICE);
        connect();
    }

    @Override
    public void run(){
        nsdManager.discoverServices("_addition._tcp.",NsdManager.PROTOCOL_DNS_SD,discoveryListener);
    }

    public void connect(){
        initDiscoveryListener();
    }

    //Strings should come in the syntax of "[int] [int]"
    public void send(String numbers){
        out.println(numbers);
    }

    public void tearDown(){
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

    private void initDiscoveryListener(){

        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG,"Failed to start discovery listener. Error code: "+errorCode);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG,"Failed to stop discovery listener. Error code: "+errorCode);
            }

            @Override
            public void onDiscoveryStarted(String serviceType) {
                Log.i(TAG,"Service discovery started");
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG,"Discovery stopped: "+serviceType);
            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                Log.d(TAG,"Service found "+serviceInfo);
                if(!serviceInfo.getServiceType().equals("_addition._tcp.")){
                    Log.d(TAG,"Service not supported: " + serviceInfo.getServiceType());
                }else if (serviceInfo.getServiceName().equals(name)){
                    Log.d(TAG,"Same machine: "+serviceInfo.getServiceName());
                }else if (serviceInfo.getServiceName().contains("Server side")){
                    nsdManager.resolveService(serviceInfo, new NsdManager.ResolveListener() {
                        @Override
                        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                            Log.e(TAG,"Resolve failed: "+errorCode);
                        }

                        @Override
                        public void onServiceResolved(NsdServiceInfo serviceInfo) {
                            Log.i(TAG,"Resolve succeeded.");
                            if(serviceInfo.getServiceName().equals(name)){
                                Log.d(TAG,"Same IP");

                            }else{
                                serverInfo = serviceInfo;
                                try{
                                    Log.d(TAG,"Service resolve serviceInfo: "+serviceInfo);
                                    s=new Socket(serverInfo.getHost(),serverInfo.getPort());
                                    Log.i(TAG,"Connected to server");
                                    while(!isInterrupted()) {
                                        out = new PrintWriter(s.getOutputStream(), true);

                                        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                        res = in.readLine();
                                        Log.i(TAG, "Response from server: " + res);

                                        answer.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                answer.setText(res);
                                            }
                                        });
                                    }
                                }catch(IOException e){
                                    e.printStackTrace();
                                }finally{
                                    try {
                                        Log.d(TAG,"Destroying socket");
                                        if (out != null) out.close();
                                        if (in != null) in.close();
                                        if(s!=null)s.close();
                                    }catch(IOException e){
                                        Log.d(TAG,"Destroying socket");
                                    }
                                }
                            }

                        }
                    });
                }
        }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                Log.e(TAG,"Lost connection to service: "+serviceInfo);
            }
        };
    }
}
