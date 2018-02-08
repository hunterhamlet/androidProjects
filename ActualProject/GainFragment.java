package com.a99minutos.a99minutos.ui.fragments;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a99minutos.a99minutos.R;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GainFragment extends Fragment {
    //Widgets
    @BindView(R.id.gain_plot)XYPlot gainPlot;

    //Entities



    public static GainFragment newInstance(Bundle arguments){
        GainFragment gainFragment = new GainFragment();
        if (arguments != null){
            gainFragment.setArguments(arguments);
        }
        return gainFragment;
    }


    public GainFragment() {
        // Required empty public constructor
    }

    //-------------------------------------Metodo Overrride-----------------------------------------
    //El fragment se ha adjuntado a la actividad
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*Iniciar aqui los callbacks que interactuaran con la actividad*/
    }

    //El fragment ha sido creado
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Instanciar todos los objetos que no sean vistas(Buttons, ListViews, TextViews)
        en el método onCreate()*/
    }

    //El fragment va a cargar su layout, el cual podemos especifica
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Cargar el layout del fragment
        * Instanciar los objetos que si son vistas, también durante onCreateView()*/
        View view = inflater.inflate(R.layout.fragment_gain, container, false);
        ButterKnife.bind(this,view);

        Toast.makeText(getActivity(),getArguments().getString("keystore"), Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        return view;
    }

    //La vista del layout a sido crada
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /*Fijar todos los parámetros que queramos de nuestras vistas y restaurar
        estados en onViewCreated():*/
        super.onViewCreated(view, savedInstanceState);

        Number [] domainLabels = {1, 2, 3, 4, 5, 6, 7};
        Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 8};

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(domainLabels),
                Arrays.asList(series1Numbers),"mySeries");

        LineAndPointFormatter series1format = new LineAndPointFormatter(getContext(),
                R.xml.line_gain_formatter);

        gainPlot.addSeries(series1,series1format);

    }

    //La vista ah sido creada y cualquier configuracion guardada esta cargada
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //La actividad la cual aloja el fragment ha sido creada
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        /*Fijar los parámetros que tengan que ver con el Activity*/
        super.onActivityCreated(savedInstanceState);
    }

    //el fragmen ha sido quitado de su actividad y ya no esta disponible
    @Override
    public void onDetach() {
        /*Eliminar la referencia al Callback durante onDetach():*/
        super.onDetach();
    }


    //-------------------------------------Metodo Generales-----------------------------------------


}
