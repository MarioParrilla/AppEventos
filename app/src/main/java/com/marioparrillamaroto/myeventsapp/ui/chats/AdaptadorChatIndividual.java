package com.marioparrillamaroto.myeventsapp.ui.chats;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.Chat;
import com.marioparrillamaroto.myeventsapp.MensajeChat;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

import java.util.ArrayList;

public class AdaptadorChatIndividual extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<MensajeChat> datos;
    private FunctionsDatabase db;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(Chat item);
    }

    public AdaptadorChatIndividual(ArrayList<MensajeChat> datos, Context context) {
        this.datos = datos;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        db = new FunctionsDatabase(context);
        String username = db.getUsernameLoginUser();
        
        if (datos.get(position).getUsuarioSender().equalsIgnoreCase(username)){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view;

        if (viewType == 0){
            View itemView =  layoutInflater.inflate(R.layout.tarjeta_chatindividual1, viewGroup, false);
            return new ChatsViewHolderUser(itemView);
        }else{
            View itemView =  layoutInflater.inflate(R.layout.tarjeta_chatindividual2, viewGroup, false);
            return new ChatsViewHolderBluetooth(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (datos.get(position).getUsuarioSender().equalsIgnoreCase(db.getUsernameLoginUser())){
            ChatsViewHolderUser chatsViewHolderUser = (ChatsViewHolderUser) holder;
            chatsViewHolderUser.bindChats(datos.get(position));
        }else{
            ChatsViewHolderBluetooth chatsViewHolderBluetooth = (ChatsViewHolderBluetooth) holder;
            chatsViewHolderBluetooth.bindChats(datos.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ChatsViewHolderUser extends RecyclerView.ViewHolder {

        private TextView ownerMessage;
        private TextView messageCI;
        private TextView dateTimeMessage;

        public ChatsViewHolderUser(View itemView) {
            super(itemView);

            ownerMessage = itemView.findViewById(R.id.ownerMessage);
            messageCI = itemView.findViewById(R.id.messageCI);
            dateTimeMessage = itemView.findViewById(R.id.dateTimeMessage);
        }

        public void bindChats(MensajeChat c) {
            ownerMessage.setText(c.getUsuarioSender());
            messageCI.setText(c.getMensaje());
            dateTimeMessage.setText(c.getFechaMensaje().toString().substring(0,c.getFechaMensaje().toString().length()-7).replace("T"," : "));
        }
    }

    public static class ChatsViewHolderBluetooth extends RecyclerView.ViewHolder {

        private TextView ownerMessage;
        private TextView messageCI;
        private TextView dateTimeMessage;

        public ChatsViewHolderBluetooth(View itemView) {
            super(itemView);

            ownerMessage = itemView.findViewById(R.id.ownerMessage);
            messageCI = itemView.findViewById(R.id.messageCI);
            dateTimeMessage = itemView.findViewById(R.id.dateTimeMessage);
        }

        public void bindChats(MensajeChat c) {
            ownerMessage.setText(c.getUsuarioReceptor());
            messageCI.setText(c.getMensaje());
            dateTimeMessage.setText(c.getFechaMensaje().toString().substring(0,c.getFechaMensaje().toString().length()-7).replace("T"," : "));
        }
    }
}
