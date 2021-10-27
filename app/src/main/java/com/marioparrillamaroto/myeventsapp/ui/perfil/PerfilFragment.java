package com.marioparrillamaroto.myeventsapp.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentPerfilBinding;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpCrearEventoMeeting;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpCrearEventoPresencial;

import java.util.ArrayList;

public class PerfilFragment extends Fragment {

    private RecyclerView recView;
    private FragmentPerfilBinding binding;
    private FloatingActionButton fabAddEvent, fabAddPresencial, fabAddOnline;
    private TextView lblUsername, lblPhonenumber;
    private boolean isOpen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        isOpen = false;
        lblUsername = root.findViewById(R.id.lblUsernameProfile);
        lblPhonenumber = root.findViewById(R.id.lblPhoneNumberProfile);
        fabAddEvent = root.findViewById(R.id.btn_new_add);
        fabAddPresencial = root.findViewById(R.id.btn_add_event_presencial);
        fabAddOnline = root.findViewById(R.id.btn_add_event_videomeeting);

        PerfilModel pm = new PerfilModel();

        Usuario usuario = pm.getLoginUser(root.getContext());

        lblUsername.setText(usuario.getUsername());
        lblPhonenumber.setText(usuario.getPhonenumber());

        ArrayList<Evento> datos = new ArrayList<Evento>();

        /*datos.add(new Evento("Prueba3", LocalDateTime.now(),LocalDateTime.now().plusHours(1),"admin3","Pruebas3", true,true,0,0,0,"Preuba"));
        datos.add(new Evento("Prueba4", LocalDateTime.now(),LocalDateTime.now().plusHours(1),"admin4","Pruebas4", false,true,0,0,0,"Preuba"));
        datos.add(new Evento("¡No tiene Eventos!", LocalDateTime.now(),LocalDateTime.now(),"null","", false,true,0,0,0,""));*/

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
                Intent i = new Intent(root.getContext(), PopUpCrearEventoPresencial.class);
                startActivity(i);
                fabAddPresencial.setVisibility(View.INVISIBLE);
                fabAddOnline.setVisibility(View.INVISIBLE);
                isOpen = false;
            }
        });

        fabAddOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(root.getContext(), PopUpCrearEventoMeeting.class);
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