package com.example.sanchez.webservices_02.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanchez.webservices_02.R;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText edt_codigo;
    private EditText edt_nombre;
    private EditText edt_precio;
    private EditText edt_fabricante;
    private Button btn_agregar;
    private ProgressDialog pd_registrar;

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;


    public RegistrarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar, container, false);

        edt_codigo = view.findViewById(R.id.registrar_codigo);
        edt_nombre = view.findViewById(R.id.registrar_nombre);
        edt_precio = view.findViewById(R.id.registrar_precio);
        edt_fabricante = view.findViewById(R.id.registrar_fabricante);
        btn_agregar = view.findViewById(R.id.registrar_agregar);

        //Inicializamos la solicitud
        requestQueue = Volley.newRequestQueue(getContext());

        return view;
    }//onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarProducto();
            }
        });

    }//onViewCreated

    private void registrarProducto() {

        //Instanciamos y condiguramos el progressDialog
        pd_registrar = new ProgressDialog(getContext());
        pd_registrar.setMessage("Cargando...");
        pd_registrar.show();

        //Enviamos parametros de insercion por URL
        String URL = "http://192.168.1.68:8080/webservice_01/registrar_producto.php?codigo=" + edt_codigo.getText().toString() + "&nombre=" + edt_nombre.getText().toString() + "&precio=" + edt_precio.getText().toString() + "&fabricante=" + edt_fabricante.getText().toString();
        //Validacion que nos permita insertar informacion con espacios
        URL = URL.replace(" ", "%20");
        //Instanciamos un objeto estableciendo la forma de envio
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        //Agregamos la peticion
        requestQueue.add(jsonObjectRequest);
    }//cargarWebService



    /*  Heredamos estos metodos para utilizar Volley  */

    //Cuando la peticion en erronea
    @Override
    public void onErrorResponse(VolleyError error) {
        pd_registrar.hide();
        Toast.makeText(getContext(), "Ocurrio un error al registrar " + error.getMessage(), Toast.LENGTH_LONG).show();
    }//onErrorResponse

    //Cuando la peticion es correcta
    @Override
    public void onResponse(JSONObject response) {
        pd_registrar.hide();
        Toast.makeText(getContext(), "Se ha registrado satisfactoriamente", Toast.LENGTH_LONG).show();
        limpiarCampos();
    }//onResponse

    public void limpiarCampos() {
        edt_codigo.setText("");
        edt_nombre.setText("");
        edt_precio.setText("");
        edt_fabricante.setText("");
    }//limpiarCampos
}
