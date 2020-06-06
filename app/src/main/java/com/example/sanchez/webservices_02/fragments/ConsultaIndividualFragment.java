package com.example.sanchez.webservices_02.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanchez.webservices_02.R;
import com.example.sanchez.webservices_02.model.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaIndividualFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText edt_codigo;
    private TextView lbl_nombre;
    private TextView lbl_precio;
    private TextView lbl_fabricante;
    private Button btn_buscar;
    private ProgressDialog pd_listar;

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    public ConsultaIndividualFragment() {
        // Required empty public constructor
    }//ConsultaIndividualFragment


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consulta_individual, container, false);

        edt_codigo = view.findViewById(R.id.consultaIndividual_codigo);
        lbl_nombre = view.findViewById(R.id.consultaIndividual_nombre);
        lbl_precio = view.findViewById(R.id.consultaIndividual_precio);
        lbl_fabricante = view.findViewById(R.id.consultaIndividual_fabricante);
        btn_buscar = view.findViewById(R.id.consultaIndividual_buscar);

        //Iniciamos la solicitud
        requestQueue = Volley.newRequestQueue(getContext());

        return view;
    }//onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarProducto();
            }//onClick
        });

    }//onViewCreated

    private void listarProducto() {

        pd_listar = new ProgressDialog(getContext());
        pd_listar.setMessage("Consultando...");
        pd_listar.show();

        String URL = "http://192.168.1.68:8080/webservice_01/consultaIndividual_producto.php?codigo=" + edt_codigo.getText().toString() + "";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        requestQueue.add(jsonObjectRequest);

    }//listarProducto

    @Override
    public void onErrorResponse(VolleyError error) {
        pd_listar.hide();
        Toast.makeText(getContext(), "¡Error al listar el producto!", Toast.LENGTH_LONG).show();
    }//onErrorResponse

    @Override
    public void onResponse(JSONObject response) {
        pd_listar.hide();
        Toast.makeText(getContext(), "¡Mensaje! " + response, Toast.LENGTH_LONG).show();

        //Obtenemos nuestro JSON de retorno y lo agregamos a nuestro objeto Producto
        Producto producto = new Producto(); //Instanciamos nuestro objeto producto
        JSONArray jsonArray = response.optJSONArray("producto"); //Asignamos identificador a nuestro arreglo JSON
        JSONObject jsonObject = null; //Declaramos un objeto para nuestros atributos clave del JSON

        try {
            jsonObject = jsonArray.getJSONObject(0); //Asignamos nuestro objeto en primer y unico valor de retorno
            producto.setNombre(jsonObject.optString("nombre"));
            producto.setPrecio(jsonObject.optString("precio"));
            producto.setFabricante(jsonObject.optString("fabricante"));
        } catch (JSONException e) {
            e.printStackTrace();
        }//catch

        lbl_nombre.setText("Nombre:   " + producto.getNombre());
        lbl_precio.setText("Precio:   " +producto.getPrecio());
        lbl_fabricante.setText("Fabricante:   " +producto.getFabricante());
    }//onResponse

}//ConsultaIndividualFragment
