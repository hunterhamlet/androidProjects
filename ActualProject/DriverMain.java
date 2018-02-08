package com.a99minutos.a99minutos.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.a99minutos.a99minutos.R;
import com.a99minutos.a99minutos.ui.fragments.GainFragment;
import com.a99minutos.a99minutos.ui.fragments.HistoryFragment;
import com.a99minutos.a99minutos.ui.fragments.OrdersFragment;
import com.a99minutos.a99minutos.ui.fragments.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class DriverMain extends AppCompatActivity {
    //Widgets
    @BindView(R.id.driver_main_bottom_navigation_menu)BottomNavigationView bottomNavigationView;
    @BindView(R.id.driver_main_toolbar_text_view)TextView diverConection;
    @BindView(R.id.driver_main_toolbar_switch)Switch btnDriverConection;

    //Static

    //Variables

    //Entities
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    //---------------------------------Metodos Override---------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        //ButerKnife init in this context
        ButterKnife.bind(this);

        //Check NavigationBottomBar
        checkNavigationBottom();

        //Show te firstFragmment(Orders)
        showFragment(new OrdersFragment(),R.id.driver_main_container);

        //Probe with Bundle with GainFragment


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //-------------------------------Metodos ButterKnife--------------------------------------------
    @OnCheckedChanged(R.id.driver_main_toolbar_switch)
    void isChangerState(boolean state){
        if(state){
            Toast.makeText(DriverMain.this, "btn " + state, Toast.LENGTH_SHORT).show();
            diverConection.setText("Conectado");
        }else{
            Toast.makeText(DriverMain.this, "btn " + state, Toast.LENGTH_SHORT).show();
            diverConection.setText("Desconectado");
        }
    }

    //---------------------------------Metodos Generales--------------------------------------------
    private void checkNavigationBottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_action_orders:
                                showFragment(new OrdersFragment(),R.id.driver_main_container);
                                Toast.makeText(DriverMain.this, "Ordenes", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_action_gain:
                                Bundle arguments = new Bundle();
                                arguments.putString("keystore","No tienes ganancias");
                                GainFragment gainFragment = GainFragment.newInstance(arguments);
                                showFragment(gainFragment,R.id.driver_main_container);

                                //Toast.makeText(DriverMain.this, "Ganancias", Toast.LENGTH_SHORT).show();
                                break;
                            case  R.id.menu_action_history:
                                showFragment(new HistoryFragment(),R.id.driver_main_container);
                                Toast.makeText(DriverMain.this, "Historial", Toast.LENGTH_SHORT).show();
                                break;
                            case  R.id.menu_action_profile:
                                showFragment(new ProfileFragment(),R.id.driver_main_container);
                                Toast.makeText(DriverMain.this, "Perfil", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
    }

    private void showFragment(Fragment fragment, int idContainer){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idContainer,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }


}
