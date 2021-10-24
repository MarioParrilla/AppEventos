package com.marioparrillamaroto.myeventsapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentHomeBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recView;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ArrayList<Evento> datos = new ArrayList<Evento>();
        /*datos.add(new Evento("Prueba1", LocalDateTime.now(),LocalDateTime.now().plusHours(1),"admin","Pruebas", true,true,0,0,0,"a"));
        datos.add(new Evento("Prueba2",LocalDateTime.now(),LocalDateTime.now().plusHours(1),"admin2","Pruebas2", false,true,0,0,0,"a"));
        datos.add(new Evento("¡No tiene Eventos!", LocalDateTime.now(),LocalDateTime.now(),"null","", false,true,0,0,0,""));*/


        AdaptadorProximoEvento adapterData = new AdaptadorProximoEvento(datos);
        LinearLayoutManager lym = new LinearLayoutManager(root.getContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) root.findViewById(R.id.recViewEventosProximos);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}