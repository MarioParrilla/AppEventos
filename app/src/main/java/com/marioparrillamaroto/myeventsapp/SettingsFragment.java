package com.marioparrillamaroto.myeventsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentHomeBinding;

import com.marioparrillamaroto.myeventsapp.databinding.FragmentPerfilBinding;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentSettingsBinding;
import com.marioparrillamaroto.myeventsapp.ui.login.LoginActivity;


public class SettingsFragment extends Fragment {
    private @NonNull FragmentSettingsBinding binding;
    TextView acercaDe, soporte, cerrarSesion;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        acercaDe = (TextView) root.findViewById(R.id.lblAcerdaDe);
        soporte = (TextView) root.findViewById(R.id.lblSoporte);
        cerrarSesion = (TextView) root.findViewById(R.id.lblCerrarSesion);

        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link = Uri.parse("https://myeventsapp-mario.herokuapp.com/soporte");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(_link);
                startActivity(i);
            }
        });

        acercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"App Creada por: Mario Parrilla Maroto | 2021",Toast.LENGTH_SHORT).show();
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FunctionsDatabase fd = new FunctionsDatabase(root.getContext().getApplicationContext());
                fd.closeSession();

                Intent nuevaPantalla = new Intent(root.getContext(), LoginActivity.class);
                startActivity(nuevaPantalla);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
