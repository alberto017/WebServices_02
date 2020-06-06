package com.example.sanchez.webservices_02.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sanchez.webservices_02.R;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaCompletaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {


    public ConsultaCompletaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consulta_completa, container, false);
        return view;
    }//onCreateView

    @Override
    public void onErrorResponse(VolleyError error) {

    }//onErrorResponse

    @Override
    public void onResponse(JSONObject response) {

    }//onResponse
}//ConsultaCompletaFragment
