package com.a99minutos.a99minutos.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a99minutos.a99minutos.R;
import com.a99minutos.a99minutos.adapters.OrdersAdapter;
import com.a99minutos.a99minutos.classes.Trip;
import com.a99minutos.a99minutos.ui.OrdersMapsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class OrdersFragment extends Fragment {
    //Widgets
    @BindView(R.id.orders_search_text)EditText searchText;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        //ButterKnife
        ButterKnife.bind(this,view);

        //RecyclerView
        RecyclerView tripRecycler = view.findViewById(R.id.orders_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        tripRecycler.setLayoutManager(linearLayoutManager);

        OrdersAdapter ordersAdapter = new OrdersAdapter(buildTrips(),R.layout.order_view_adapter,getActivity());
        tripRecycler.setAdapter(ordersAdapter);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    //ButterKnife
    @OnClick(R.id.orders_map_btn)
    void goToMap(){
        Intent intent = new Intent(getActivity(), OrdersMapsActivity.class);
        startActivity(intent);
    }

    @OnTextChanged(R.id.orders_search_text)
    void textCganged(CharSequence text){
        Toast.makeText(getActivity(), "Text changed: " + text, Toast.LENGTH_SHORT).show();
    }

    //MetodosGenerales
    public ArrayList<Trip> buildTrips(){
        ArrayList<Trip> trips = new ArrayList<>();
        trips.add(new Trip("Soriana","345678",
                "Col.El camellito, CP. 05050","Juan Carlos"));
        trips.add(new Trip("Virgin Mobile","345678",
                "Col.El camellito, CP. 05050","Ramiro"));
        trips.add(new Trip("Festo","345678",
                "Col.El camellito, CP. 05050","Carlos Hernandez"));
        trips.add(new Trip("Soriana","345678",
                "Col.El camellito, CP. 05050","Juan Carlos"));
        trips.add(new Trip("Virgin Mobile","345678",
                "Col.El camellito, CP. 05050","Ramiro"));
        trips.add(new Trip("Festo","345678",
                "Col.El camellito, CP. 05050","Carlos Hernandez"));
        trips.add(new Trip("Soriana","345678",
                "Col.El camellito, CP. 05050","Juan Carlos"));
        trips.add(new Trip("Virgin Mobile","345678",
                "Col.El camellito, CP. 05050","Ramiro"));
        trips.add(new Trip("Festo","345678",
                "Col.El camellito, CP. 05050","Carlos Hernandez"));
        trips.add(new Trip("Soriana","345678",
                "Col.El camellito, CP. 05050","Juan Carlos"));
        return trips;
    }

    //
    public void showToolBar(String titleToolBar,boolean upBtn,View view){
        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(titleToolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upBtn);

    }

}
