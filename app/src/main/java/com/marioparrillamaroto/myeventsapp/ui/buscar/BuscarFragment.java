package com.marioparrillamaroto.myeventsapp.ui.buscar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentBuscarBinding;

import java.util.ArrayList;

public class BuscarFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recView;
    private SearchView barraBusqueda;

    private FragmentBuscarBinding binding;
    private AdaptadorBusqueda adapterData;
    private BuscarModel bm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBuscarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        barraBusqueda = (SearchView)root.findViewById(R.id.barraBusqueda);



        bm = new BuscarModel();
        FunctionsDatabase fd = new FunctionsDatabase(root.getContext());
        fd.syncronizingData();
        fd.checkUserLoginExists();

        ArrayList<Usuario> datos = bm.usersToSearch(getContext());

        adapterData = new AdaptadorBusqueda(datos);
        LinearLayoutManager lym = new LinearLayoutManager(root.getContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) root.findViewById(R.id.recViewBusq);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);

        barraBusqueda.setOnQueryTextListener(this);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterData.filtrado(newText);
        return false;
    }
}