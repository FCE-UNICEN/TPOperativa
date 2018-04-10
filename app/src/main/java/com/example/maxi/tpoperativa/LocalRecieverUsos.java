package com.example.maxi.tpoperativa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import Funcionalidad.Paquete;
import Funcionalidad.PointInfo;

/**
 * Created by Flia. Ferreira on 07/04/2018.
 */

public class LocalRecieverUsos extends BroadcastReceiver {

    private UsosActivity activity;

    public LocalRecieverUsos(UsosActivity activity){
        this.activity = activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String operation = intent.getStringExtra(ServiceCaller.OPERACION);
        switch (operation) {
            case "getcurrentownresources":
                try {
                    JSONObject jsona = new JSONObject(intent.getStringExtra(ServiceCaller.RESPONSE));

                    HashMap<String,Integer> recursos = new HashMap<String,Integer>();

                    JSONArray json = new JSONArray(jsona.getString("resources"));

                    for(int i = 0; i < json.length(); i++) {
                        JSONObject jsonRec = json.getJSONObject(i);
                        recursos.put(jsonRec.getString("nombre"),jsonRec.getInt("id"));
                    }
                    activity.setRecursos(recursos);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "getcurrentownpackagebyresource":
                try {
                    JSONObject jsona = new JSONObject(intent.getStringExtra(ServiceCaller.RESPONSE));

                    HashMap<Integer, Paquete> packages = new HashMap<Integer,Paquete>();

                    JSONArray json = new JSONArray(jsona.getString("packages"));

                    for(int i = 0; i < json.length(); i++) {
                        JSONObject jsonRec = json.getJSONObject(i);
                        Integer id = jsonRec.getInt("id");
                        Paquete p = new Paquete();
                        p.setId(id);
                        p.setCantidad(jsonRec.getInt("cantidad"));
                        p.setEnUso(jsonRec.getInt("enUso"));
                        p.setIdResource(jsonRec.getInt("idResource"));
                        packages.put(id,p);

                    }
                    activity.setPackages(packages);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "getpackageroute" :
                try {
                    JSONObject jsona = new JSONObject(intent.getStringExtra(ServiceCaller.RESPONSE));

                    ArrayList<PointInfo> nodos = new ArrayList<>();

                    JSONArray json = new JSONArray(jsona.getString("nodos"));

                    for(int i = 0; i < json.length(); i++) {
                        JSONObject jsonRec = json.getJSONObject(i);
                        PointInfo nodo = new PointInfo(jsonRec.getInt("id"),jsonRec.getInt("id_origen"),jsonRec.getString("nombre_origen"),jsonRec.getInt("id_destino"),jsonRec.getString("nombre_destino"),1,jsonRec.getString("address"),jsonRec.getDouble("latitude"),jsonRec.getDouble("longitude"),jsonRec.getString("fecha"));
                        nodos.add(nodo);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default :
                try {
                    JSONObject jsona = new JSONObject(intent.getStringExtra(ServiceCaller.RESPONSE));
                    String mensaje = jsona.getString("mensaje");
                    activity.notifySuccess(mensaje);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
