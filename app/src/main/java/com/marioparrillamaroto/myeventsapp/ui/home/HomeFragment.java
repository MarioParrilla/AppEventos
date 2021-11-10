package com.marioparrillamaroto.myeventsapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentHomeBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recView;
    private FragmentHomeBinding binding;
    private SwipeRefreshLayout sw;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        HomeModel hm = new HomeModel();
        FunctionsDatabase fd = new FunctionsDatabase(root.getContext());
        fd.syncronizingData();
        fd.checkUserLoginExists();

        ArrayList<Evento> datos = hm.eventsOfUser(root.getContext(), hm.getLoginUser(root.getContext()));
        if (datos.size()==0) datos.add(new Evento(1, "No tiene eventos proximos", "", LocalDateTime.now(),LocalDateTime.now(),false,true,1,1,"",""));
        AdaptadorProximoEvento adapterData = new AdaptadorProximoEvento(datos);
        LinearLayoutManager lym = new LinearLayoutManager(root.getContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) root.findViewById(R.id.recViewEventosProximos);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(lym);
        recView.setAdapter(adapterData);

        sw = root.findViewById(R.id.swipeHome);
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fd.syncronizingData();
                fd.checkUserLoginExists();
                ArrayList<Evento> datos = hm.eventsOfUser(root.getContext(), hm.getLoginUser(root.getContext()));
                if (datos.size()==0) datos.add(new Evento(1, "No tiene eventos proximos", "", LocalDateTime.now(),LocalDateTime.now(),false,true,1,1,"",""));
                System.err.println("@@@@@@@@@"+datos.size());
                AdaptadorProximoEvento adapterData = new AdaptadorProximoEvento(datos);
                recView.setAdapter(adapterData);
                adapterData.notifyDataSetChanged();
                sw.setRefreshing(false);
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