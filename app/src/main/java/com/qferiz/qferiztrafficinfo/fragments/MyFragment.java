package com.qferiz.qferiztrafficinfo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.qferiz.qferiztrafficinfo.R;
import com.qferiz.qferiztrafficinfo.network.VolleySingleton;

/**
 * Created by Qferiz on 30/03/2015.
 */
public class MyFragment extends Fragment {
    private TextView textView;

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        textView = (TextView) layout.findViewById(R.id.txtPosition);
        Bundle bundle = getArguments();

        if (bundle != null) {
            textView.setText("Halaman yang dipilih : " + bundle.getInt("position"));
        }

        // JSON REQUEST QUEUE
        //RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://php.net/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),"RESPONSE "+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"RESPONSE "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

        return layout;
    }
}
