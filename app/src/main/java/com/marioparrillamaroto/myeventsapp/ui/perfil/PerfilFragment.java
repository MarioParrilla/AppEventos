package com.marioparrillamaroto.myeventsapp.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

import com.marioparrillamaroto.myeventsapp.databinding.FragmentPerfilBinding;
import com.marioparrillamaroto.myeventsapp.popUpCrearEventoMeeting;
import com.marioparrillamaroto.myeventsapp.popUpCrearEventoPresencial;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PerfilFragment extends Fragment {

    private RecyclerView recView;
    private PerfilViewModel PerfilViewModel;
    private FragmentPerfilBinding binding;
    private FloatingActionButton fabAddEvent, fabAddPresencial, fabAddOnline;
    private boolean isOpen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        isOpen = false;
        fabAddEvent = root.findViewById(R.id.btn_new_add);
        fabAddPresencial = root.findViewById(R.id.btn_add_event_presencial);
        fabAddOnline = root.findViewById(R.id.btn_add_event_videomeeting);

        ArrayList<Evento> datos = new ArrayList<Evento>();
        datos.add(new Evento("Prueba3", LocalDateTime.now(),LocalDateTime.now().plusHours(1),"@admin3","Pruebas3", true,"a"));
        datos.add(new Evento("Prueba4",LocalDateTime.now(),LocalDateTime.now().plusHours(1),"@admin4","Pruebas4", false,"a"));

        AdaptadorEvento adapterData = new AdaptadorEvento(datos);
        LinearLayoutManager lym = new LinearLayoutManager(root.getContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) root.findViewById(R.id.recViewEventosP);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);

        fabAddEvent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (isOpen) {

                   fabAddPresencial.setVisibility(View.INVISIBLE);
                   fabAddOnline.setVisibility(View.INVISIBLE);

                   isOpen = false;
               } else {

                   fabAddPresencial.setVisibility(View.VISIBLE);
                   fabAddOnline.setVisibility(View.VISIBLE);

                   isOpen = true;
               }
           }});

        fabAddPresencial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(root.getContext(), popUpCrearEventoPresencial.class);
                startActivity(i);
                fabAddPresencial.setVisibility(View.INVISIBLE);
                fabAddOnline.setVisibility(View.INVISIBLE);
                isOpen = false;
            }
        });

        fabAddOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(root.getContext(), popUpCrearEventoMeeting.class);
                startActivity(i);
                fabAddPresencial.setVisibility(View.INVISIBLE);
                fabAddOnline.setVisibility(View.INVISIBLE);
                isOpen = false;
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