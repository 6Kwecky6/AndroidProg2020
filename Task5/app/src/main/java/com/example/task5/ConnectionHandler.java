package com.example.task5;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class ConnectionHandler extends Thread{
    ConnectionHandler(MainActivity parent,URL url){
        this.url = url;
        this.parent=parent;
    }
    private MainActivity parent;
    private HttpURLConnection connection;
    private InputStream stream;
    private URL url;

    public void run(){
        try {
            parent.setRes(register(url));
        } catch (IOException e) {
            System.out.println("Failed to send the registration GET request");
        }

        System.out.println("Thread: finished");
    }

    public String register(URL url) throws IOException{
        String res=null;
        try {
            connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.connect();

            stream = connection.getInputStream();
            if(stream!=null){
                res = readStream(stream,500);
                System.out.println("Reading stream:\n"+res);
            }
        }catch(IOException e){
            System.out.println("Error with the connection\n"+e);
        }finally {
            if (stream != null) {
                stream.close();
            }
            if(connection!= null){
                connection.disconnect();
            }
        }

        return res;
    }

    private String convertString(String inp){
        return inp.replace("Ã¥","å")
                .replace("Ã¦","æ")
                .replace("Ã¸","ø")
                .replace("Ã…","Å")// Caps ÆØÅ can't be differentiated, because …,† and ˜ are all treated like the same character, and that's what the server gives me. conclusion, that's a server issue
                .replace("Ã†","Æ")// I could probably keep the name locally to bypass the server side, but that feels a bit too hacky since we will overwrite the response with the original input. Why even use the server side input in that case?
                .replace("Ã˜","Ø");// In a production setting, it would be better to fix the server side if possible
    }

    public String readStream(InputStream stream, int maxReadSize)
            throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            System.out.println("Raw buffer: "+rawBuffer);

            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return convertString(buffer.toString());
    }
}
