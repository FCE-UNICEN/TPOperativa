package com.example.maxi.tpoperativa;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lucho on 26/01/2018.
 */

public class ServiceCaller extends IntentService {

    public static final String RESPONSE_ACTION = "Respuesta del servidor";
    public static final String RESPONSE = "DATA RESPONSE";
    public static final String SERVICE_TYPE = "SERVICE_TYPE";
    public static final String OPERACION = "OPERATION_SERVICE";
    public static final String RUTA = "RUTA";


    final String BASE_URL = "http://172.19.240.106:8080/TrazaAppServer/trazaapp/";


    static final String TAG = ServiceCaller.class.getCanonicalName();

    public ServiceCaller() {
        super("CallerServiceTest");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String operation = intent.getStringExtra(OPERACION);
        String ruta = intent.getStringExtra(RUTA);
        Uri builtURI = Uri.parse(BASE_URL + ruta).buildUpon().build();
        InputStream is = null;
        HttpURLConnection conn = null;
        Intent response;
        String contentAsString;
        int responseCode = -1;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        SimpleDateFormat sdf = null;
        JSONObject json = new JSONObject();

        try {
            URL requestURL = new URL(builtURI.toString());
            conn = (HttpURLConnection) requestURL.openConnection();
            switch (operation) {
                case "addPackage":
                    //preparacion para envio de json
                    json.put("idResource",intent.getIntExtra("resource",-1));
                    json.put("cantidad",intent.getDoubleExtra("cantidad",-1));
                    json.put("id_user",intent.getIntExtra("id_user",-1));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);

                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));

                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                break;

                case "attemptlogin":
                    json.put("email",intent.getStringExtra("email"));
                    json.put("password",intent.getStringExtra("password"));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                    break;

                case "registeruser":
                    json.put("nombre",intent.getStringExtra("nombre"));
                    json.put("email",intent.getStringExtra("email"));
                    json.put("password",intent.getStringExtra("password"));
                    json.put("usuario",intent.getStringExtra("usuario"));
                    json.put("direccion",intent.getStringExtra("direccion"));
                    json.put("telefono",intent.getStringExtra("telefono"));
                    //json.put("ciudad",intent.getIntExtra("ciudad",-1));
                    json.put("web",intent.getStringExtra("web"));
                    json.put("institucion",intent.getIntExtra("institucion", 1));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);

                    break;
                case "getownpackagebyresource" :
                    json.put("user",intent.getIntExtra("user",-1));
                    json.put("resource",intent.getIntExtra("resource",-1));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                    break;

                case "sendpackage":
                    json.put("id_package",intent.getIntExtra("id_package",-1));
                    json.put("id_resource",intent.getIntExtra("id_resource",-1));
                    json.put("cantidad",intent.getDoubleExtra("cantidad",-1));
                    json.put("id_origen",intent.getIntExtra("id_origen",-1));
                    json.put("id_destino",intent.getIntExtra("id_destino",-1));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);

                    break;
                case "addpeticion" :
                    json.put("idResource",intent.getIntExtra("idResource",-1));
                    json.put("cantidad",intent.getDoubleExtra("cantidad",-1));
                    json.put("idUser",intent.getIntExtra("idUser",-1));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                    break;

                case "getcurrentownpackagebyresource":
                    json.put("user",intent.getIntExtra("user",-1));
                    json.put("resource",intent.getIntExtra("resource",-1));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                    break;

                case "actualizarpackage" :
                    json.put("id_package",intent.getIntExtra("id_package",-1));
                    json.put("enUso",intent.getDoubleExtra("enUso",-1));

                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                    break;

                case "brokenobject" :
                    json.put("id_package",intent.getIntExtra("id_package",-1));
                    json.put("enUso",intent.getDoubleExtra("enUso",-1));
                    json.put("cantidad",intent.getDoubleExtra("cantidad",-1));
                    json.put("resource",intent.getIntExtra("resource",-1));
                    json.put("user",intent.getIntExtra("user",-1));


                    Log.d(TAG,json.toString());

                    response = new Intent(RESPONSE_ACTION);
                    response.putExtra(ServiceCaller.OPERACION, operation);
                    response.putExtra(RESPONSE, this.post(BASE_URL + ruta,json));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                    break;


                default:
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    responseCode = conn.getResponseCode();
                    is = conn.getInputStream();
                    contentAsString = convertIsToString(is);
                    Log.d(TAG, contentAsString);

                    response = new Intent(RESPONSE_ACTION);

                    response.putExtra(OPERACION, operation);
                    response.putExtra(RESPONSE, contentAsString);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(response);

                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public String convertIsToString(InputStream stream)
            throws IOException, UnsupportedEncodingException {


        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader buffer = new BufferedReader(reader);
        String line = buffer.readLine();
        return line;
    }

    public String post(String posturl, JSONObject json){

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(posturl);
            httppost.setHeader("accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            //AÑADIR PARAMETROS

            httppost.setEntity(new StringEntity(json.toString())); // cambiar el formato de los datos a String (un json) <--------------

            //Finalmente ejecutamos enviando la info al server/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/
            Log.d(TAG,resp.getEntity().toString() );

            String text = EntityUtils.toString(ent);
            Log.e(TAG ,resp.getStatusLine()+text + "<----------");
            return text;

        }
        catch(Exception e) { return "error";}

    }

}

