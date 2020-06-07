package com.example.sanchez.webservices_02.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanchez.webservices_02.R;
import com.example.sanchez.webservices_02.adapter.ProductoAdapter;
import com.example.sanchez.webservices_02.model.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaCompletaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {


    RecyclerView rv_consultaCompleta;
    ArrayList<Producto> productoArrayList;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;


    public ConsultaCompletaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consulta_completa, container, false);
        rv_consultaCompleta = view.findViewById(R.id.rv_consultaCompleta);

        productoArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

        rv_consultaCompleta.setLayoutManager(new LinearLayoutManager(getContext()));

        listarProductos();
        return view;
    }//onCreateView

    private void listarProductos() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Consultando...");
        progressDialog.show();

        String URL = "http://192.168.1.68:8080/webservice_01/consultaCompleta_producto.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }//onViewCreated

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(getContext(), "¡Error! " + error.getMessage(), Toast.LENGTH_LONG).show();

    }//onErrorResponse

    @Override
    public void onResponse(JSONObject response) {
        Producto producto = null;

        JSONArray jsonArray = response.optJSONArray("producto");

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                producto = new Producto();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);

                producto.setCodigo(jsonObject.optInt("codigo"));
                producto.setNombre(jsonObject.optString("nombre"));
                producto.setPrecio(jsonObject.optString("precio"));
                producto.setFabricante(jsonObject.optString("fabricante"));

                productoArrayList.add(producto);
            }//for
            progressDialog.hide();
            ProductoAdapter productoAdapter = new ProductoAdapter(productoArrayList);
            rv_consultaCompleta.setAdapter(productoAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.hide();
            Toast.makeText(getContext(), "¡Error! " + response, Toast.LENGTH_LONG).show();
        }//catch

    }//onResponse
}//ConsultaCompletaFragment
