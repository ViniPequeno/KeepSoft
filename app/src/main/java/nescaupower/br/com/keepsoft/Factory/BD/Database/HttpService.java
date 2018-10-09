package nescaupower.br.com.keepsoft.Factory.BD.Database;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HttpService extends AsyncTask<String, Void, String> {
    private final String IP = "192.168.0.24";
    private final String PORTA = "8084";
    private final String URL = "http://"+IP+":"+PORTA+"/api";
    private StringBuilder resposta;

    public String getT(String parte){
        resposta  = new StringBuilder();
        try{
            URL url = new URL(URL+parte);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            Log.e("entrei", "5");
            return resposta.toString();
        }catch (MalformedURLException e) {
            Log.e("entrei", "3");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("entrei", "4");
            e.printStackTrace();
        }
        return null;
    }


    public String postT(String parte, String t){
        resposta  = new StringBuilder();
        try{
            URL url = new URL(URL+parte);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.println(t);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            return new Scanner(connection.getInputStream()).next();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String putT(String parte, String t){
        resposta  = new StringBuilder();
        try{
            URL url = new URL(URL+parte);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.println(t);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            return new Scanner(connection.getInputStream()).next();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteT(String parte){
        resposta  = new StringBuilder();
        try{
            URL url = new URL(URL+parte);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            return "OKY";
        }catch (MalformedURLException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        String parte = strings[0];
        String metodo = strings[1];
        String tJson = strings[2];
        switch(metodo){
            case "Get":{
                Log.e("entrei", "1");
                return getT(parte);
            }
            case "Post":{
                return postT(parte, tJson);
            }
            case "Put":{
                return putT(parte, tJson);
            }
            case "Delete":{
                return deleteT(parte);
            }
        }
        return null;
    }
}
