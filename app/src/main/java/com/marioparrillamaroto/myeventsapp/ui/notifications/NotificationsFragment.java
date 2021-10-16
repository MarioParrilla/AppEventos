package com.marioparrillamaroto.myeventsapp.ui.notifications;

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
import com.marioparrillamaroto.myeventsapp.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private RecyclerView recView;

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Notifications> datos = new ArrayList<Notifications>();
        datos.add(new Notifications("admin","¡En 10 minutos tendemos el evento presencial!"));
        datos.add(new Notifications("admin2","He anulado el evento videomeeting"));


        AdaptadorNotifications adapterData = new AdaptadorNotifications(datos);
        LinearLayoutManager lym = new LinearLayoutManager(root.getContext());
        lym.setOrientation(LinearLayoutManager.VERTICAL);
        recView = (RecyclerView) root.findViewById(R.id.recViewNotificaciones);
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