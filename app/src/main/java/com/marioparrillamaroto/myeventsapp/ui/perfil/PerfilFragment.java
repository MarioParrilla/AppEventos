package com.marioparrillamaroto.myeventsapp.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.R;

import com.marioparrillamaroto.myeventsapp.databinding.FragmentPerfilBinding;
import com.marioparrillamaroto.myeventsapp.popUpCrearEventoMeeting;
import com.marioparrillamaroto.myeventsapp.popUpCrearEventoPresencial;

public class PerfilFragment extends Fragment {

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
            }
        });

        fabAddOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(root.getContext(), popUpCrearEventoMeeting.class);
                startActivity(i);
                fabAddPresencial.setVisibility(View.INVISIBLE);
                fabAddOnline.setVisibility(View.INVISIBLE);
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