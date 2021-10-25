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
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recView;
    private HomeModel homeViewModel;
    private FragmentHomeBinding binding;
    private static final String URLAPI = "http://192.168.90.66:8080/api/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        HomeModel hm = new HomeModel();
        FunctionsDatabase fd = new FunctionsDatabase(root.getContext());
        fd.syncronizingData(root.getContext(),URLAPI);

        ArrayList<Evento> datos = hm.eventsOfUser(root.getContext(), hm.getLoginUser(root.getContext()));

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