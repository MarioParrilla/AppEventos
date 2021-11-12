package com.marioparrillamaroto.myeventsapp.ui.chats;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        private ImageView iconDevice;

        public ChatsViewHolder(View itemView) {
            super(itemView);

            lblUsernameChat = itemView.findViewById(R.id.lblUsernameChat);
            iconDevice = itemView.findViewById(R.id.iconDevice);
        }

        public void bindChats(Chat c) {
            if (c.getUser1().equals("MyEventsApp")) {
                lblUsernameChat.setText("\t\t"+c.getUser1()+"\n\t"+c.getUser2().getNombreDispositivo());
                lblUsernameChat.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f);
            }else{
                lblUsernameChat.setText(c.getUser2().getNombreDispositivo()+" - "+c.getUser2().getAddressDispositivo());
                lblUsernameChat.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f);
                switch (c.getUser2().getTypeOfDevice()){
                    case 1028://Cascos
                        iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bheadphones_24));
                        break;

                    case 1032://Cascos
                        iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bspeaker_24));
                        break;

                    case 1408://Raton
                        iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bmouse_24));
                        break;

                    case 524://Smartphone
                        iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bsmartphone_24));
                        break;

                    case 268://Portatil
                        iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bpc_24));
                        break;

                    case 260://PC
                        iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bdesktop_mac_24));
                        break;
                    default:
                        switch (c.getUser2().getMayorType()){
                            case 1080://Periferico
                                iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bkeyboard_24));
                                break;

                            case 7936://Band
                                if (c.getUser2().getNombreDispositivo().contains("Band") || c.getUser2().getNombreDispositivo().contains("band")){
                                    iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bband_24));
                                }else if (c.getUser2().getNombreDispositivo().contains("pencil") || c.getUser2().getNombreDispositivo().contains("Pencil")) {
                                    iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bpencil_24));
                                }else iconDevice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_bother_24));
                                break;
                        }
                        break;
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemView.getContext(), ChatActivity.class);
                    itemView.getContext().startActivity(i);
                }
            });

        }
    }
}
