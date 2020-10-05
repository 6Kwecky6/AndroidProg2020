package com.example.task6;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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

    ServerThread(Context _context){
        context=_context;
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
            registerService(ss.getLocalPort());
            s=ss.accept();
            Log.i(TAG,"Client connected...");
            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            inputConverter(in);
            out = new PrintWriter(s.getOutputStream(),true);
//            int[] res = inputConverter(in);
//            out.println(res[0]+res[1]);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                Log.d(TAG,"Destroying socket");//Dette skjer aldri..?
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
    private void inputConverter(BufferedReader inp)throws IOException{
        System.out.println(inp.readLine());
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
                Log.e(TAG,"Failed to unregister Nservice. Error code: "+errorCode);
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
