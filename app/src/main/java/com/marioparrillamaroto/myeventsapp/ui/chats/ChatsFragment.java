package com.marioparrillamaroto.myeventsapp.ui.chats;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marioparrillamaroto.myeventsapp.Chat;
import com.marioparrillamaroto.myeventsapp.MensajeChat;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentChatsBinding;
import java.util.ArrayList;

public class ChatsFragment extends Fragment {
        private @NonNull
        FragmentChatsBinding binding;
        private RecyclerView recView;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

                binding = FragmentChatsBinding.inflate(inflater, container, false);
                View root = binding.getRoot();

                super.onCreate(savedInstanceState);

                ArrayList<Chat> datos = new ArrayList<Chat>();
                datos.add(new Chat("Admin","admin2",new ArrayList< MensajeChat >()));
                datos.add(new Chat("Admin","pepe",new ArrayList< MensajeChat >()));
                datos.add(new Chat("Admin","pruebe",new ArrayList< MensajeChat >()));

                AdaptadorChats adapterData = new AdaptadorChats(datos);
                LinearLayoutManager lym = new LinearLayoutManager(root.getContext().getApplicationContext());
                lym.setOrientation(LinearLayoutManager.VERTICAL);
                recView = (RecyclerView) root.findViewById(R.id.recViewChats);
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

