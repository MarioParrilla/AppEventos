package com.marioparrillamaroto.myeventsapp.ui.chats;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.Chat;
import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpCitarEventoMeeting;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpCitarEventoPresencial;

import java.util.ArrayList;

public class AdaptadorChats extends RecyclerView.Adapter<AdaptadorChats.ChatsViewHolder>{

    private ArrayList<Chat> datos;

    public interface OnItemClickListener{
        void onItemClick(Chat item);
    }

    public AdaptadorChats(ArrayList<Chat> datos) {
        this.datos = datos;
    }

    @Override
    public ChatsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_chat, viewGroup, false);

        ChatsViewHolder tvh = new ChatsViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ChatsViewHolder viewHolder, int pos) {
        Chat item = datos.get(pos);

        viewHolder.bindChats(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ChatsViewHolder extends RecyclerView.ViewHolder {

        private TextView lblUsernameChat;

        public ChatsViewHolder(View itemView) {
            super(itemView);

            lblUsernameChat = itemView.findViewById(R.id.lblUsernameChat);
        }

        public void bindChats(Chat c) {
            lblUsernameChat.setText("@"+c.getUser2());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
