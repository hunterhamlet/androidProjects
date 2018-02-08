package com.a99minutos.a99minutos.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a99minutos.a99minutos.R;
import com.a99minutos.a99minutos.classes.Driver;
import com.a99minutos.a99minutos.classes.Vehicle;
import com.a99minutos.a99minutos.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoVehicleFragment extends Fragment {
    //Widgets
    @BindView(R.id.info_vehicle_brand)EditText brand;
    @BindView(R.id.info_vehicle_plates)EditText plates;
    @BindView(R.id.info_vehicle_year)EditText year;
    @BindView(R.id.info_vehicle_color)EditText color;

    //Static

    //Variable

    //Entities
    //Entidad creada para el manejo de el cick del boton
    private BtnVehicleListener listener;
    private Vehicle vehicle;
    private View view;

    //Constructor
    public InfoVehicleFragment() {
        // Required empty public constructor
    }

    //---------------------------------Metodos Override---------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info_vehicle, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (BtnVehicleListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    "Se debe implementar la interface BtnVehicleListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //---------------------------------Metodos ButterKnife-------------------------------------------
    @OnClick(R.id.info_vehicle_btn_next)
    public void next() {
        vehicle = new Vehicle();
        vehicle.setBrand(brand.getText().toString());
        vehicle.setPlates(plates.getText().toString());
        vehicle.setYear(year.getText().toString());
        vehicle.setColor(color.getText().toString());
        //logInfoState(vehicle);
        onButtonPressed(vehicle);
    }


    //---------------------------------Metodos Generales--------------------------------------------
    public interface BtnVehicleListener{
        void onClickVehicleBtn(Vehicle vehicle);
    }

    private void logInfoState(Vehicle vehicle){
        Log.i(Constants.TAG,vehicle.getBrand());
        Log.i(Constants.TAG,vehicle.getPlates());
        Log.i(Constants.TAG,vehicle.getYear());
        Log.i(Constants.TAG,vehicle.getColor());
    }

    public void onButtonPressed(Vehicle vehicle) {
        if (listener != null) {
            listener.onClickVehicleBtn(vehicle);
        }
    }

}
