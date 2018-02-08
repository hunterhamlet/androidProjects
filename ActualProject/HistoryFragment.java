package com.a99minutos.a99minutos.ui.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a99minutos.a99minutos.R;
import com.a99minutos.a99minutos.adapters.OrdersAdapter;
import com.a99minutos.a99minutos.classes.Trip;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView tripRecycler = view.findViewById(R.id.history_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        tripRecycler.setLayoutManager(linearLayoutManager);

        OrdersAdapter ordersAdapter = new OrdersAdapter(buildTrips(),R.layout.order_view_adapter,getActivity());
        tripRecycler.setAdapter(ordersAdapter);

        return view;
    }

    public ArrayList<Trip> buildTrips(){
        ArrayList<Trip> trips = new ArrayList<>();
        for (int i = 0; i < 100;i++) {
            trips.add(new Trip("Soriana", "345678",
                    "Col.El camellito, CP. 05050", "Juan Carlos"));
            trips.add(new Trip("Virgin Mobile", "345678",
                    "Col.El camellito, CP. 05050", "Ramiro"));
            trips.add(new Trip("Festo", "345678",
                    "Col.El camellito, CP. 05050", "Carlos Hernandez"));

        }
        return trips;
    }

}
