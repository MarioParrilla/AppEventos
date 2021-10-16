package com.marioparrillamaroto.myeventsapp.ui.buscar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentBuscarBinding;

import java.util.ArrayList;

public class BuscarFragment extends Fragment {

    private RecyclerView recView;

    private BuscarViewModel buscarViewModel;
    private FragmentBuscarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscarViewModel =
                new ViewModelProvider(this).get(BuscarViewModel.class);

        binding = FragmentBuscarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Usuario> datos = new ArrayList<Usuario>();
        datos.add(new Usuario("admin","Pruebas"));
        datos.add(new Usuario("admin2","Pruebas2"));
        datos.add(new Usuario("admin","Pruebas"));
        datos.add(new Usuario("admin2","Pruebas2"));
        datos.add(new Usuario("admin","Pruebas"));
        datos.add(new Usuario("admin2","Pruebas2"));
        datos.add(new Usuario("admin","Pruebas"));
        datos.add(new Usuario("admin2","Pruebas2"));
        datos.add(new Usuario("admin","Pruebas"));
        datos.add(new Usuario("admin2","Pruebas2"));

        AdaptadorBusqueda adapterData = new AdaptadorBusqueda(datos);
        LinearLayoutManager lym = new LinearLayoutManager(root.getContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) root.findViewById(R.id.recViewBusq);
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