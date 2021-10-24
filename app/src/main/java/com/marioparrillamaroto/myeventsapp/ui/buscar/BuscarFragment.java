package com.marioparrillamaroto.myeventsapp.ui.buscar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.Usuario;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentBuscarBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuscarFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recView;
    private SearchView barraBusqueda;

    private BuscarViewModel buscarViewModel;
    private FragmentBuscarBinding binding;
    private AdaptadorBusqueda adapterData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscarViewModel =
                new ViewModelProvider(this).get(BuscarViewModel.class);

        binding = FragmentBuscarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        barraBusqueda = (SearchView)root.findViewById(R.id.barraBusqueda);

        ArrayList<Usuario> datos = new ArrayList<Usuario>();

        adapterData = new AdaptadorBusqueda(datos);
        LinearLayoutManager lym = new LinearLayoutManager(root.getContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) root.findViewById(R.id.recViewBusq);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);

        /*datos.add(new Usuario("admin","Pruebas"));
        datos.add(new Usuario("admin2","Pruebas2"));
        datos.add(new Usuario("3admin","Pruebas"));
        datos.add(new Usuario("4admin2","Pruebas2"));
        datos.add(new Usuario("5admin","Pruebas"));
        datos.add(new Usuario("6admin2","Pruebas2"));
        datos.add(new Usuario("mario","Pruebas"));
        datos.add(new Usuario("7admin2","Pruebas2"));
        datos.add(new Usuario("pepe","Pruebas"));
        datos.add(new Usuario("admin2","Pruebas2"));*/


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