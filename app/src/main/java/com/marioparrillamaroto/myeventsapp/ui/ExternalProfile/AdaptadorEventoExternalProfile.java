package com.marioparrillamaroto.myeventsapp.ui.ExternalProfile;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpCitarEventoMeeting;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpCitarEventoPresencial;

import java.util.ArrayList;

public class AdaptadorEventoExternalProfile extends RecyclerView.Adapter<AdaptadorEventoExternalProfile.EventoViewHolder>{

    private ArrayList<Evento> datos;

    public interface OnItemClickListener{
        void onItemClick(Evento item);
    }

    public AdaptadorEventoExternalProfile(ArrayList<Evento> datos) {
        this.datos = datos;
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_evento_perfil, viewGroup, false);

        EventoViewHolder tvh = new EventoViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(EventoViewHolder viewHolder, int pos) {
        Evento item = datos.get(pos);

        viewHolder.bindEvento(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMensaje;
        private TextView txtHorario;
        private TextView txtNombreEvento;
        private TextView txtTipo;

        public EventoViewHolder(View itemView) {
            super(itemView);

            txtHorario = (TextView)itemView.findViewById(R.id.lblUsernameNotificationP);
            txtMensaje = (TextView)itemView.findViewById(R.id.lblMessageNotificationP);
            txtNombreEvento = (TextView)itemView.findViewById(R.id.lblNombreEventoP);
            txtTipo = (TextView)itemView.findViewById(R.id.lblTipoP);
        }

        public void bindEvento(Evento e) {
            txtHorario.setText(e.getHoraInicio()+" - "+e.getHoraFinal());
            txtMensaje.setText("Cita con @"+e.getUserOwnerID()+", hablarás sobre: \n #"+e.getTema());
            txtNombreEvento.setText(e.getNombreEvento());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i;
                    if (e.getEventPreference()){
                        i = new Intent(itemView.getContext(), PopUpCitarEventoMeeting.class);
                        i.putExtra("infoEvento",e);
                        itemView.getContext().startActivity(i);
                    }
                    else if (!e.getEventPreference() && e.getUserOwnerID().equals("null")) i = null;
                    else if(!e.getEventPreference()){
                        i = new Intent(itemView.getContext(), PopUpCitarEventoPresencial.class);
                        i.putExtra("infoEvento",e);
                        itemView.getContext().startActivity(i);
                    };
                }
            });

            if (e.getEventPreference())txtTipo.setText("M");
            else if (!e.getEventPreference() && e.getUserOwnerID().equals("null")) {
                txtTipo.setText("");
                txtHorario.setText("");
                txtMensaje.setText("");
            }
            else if (!e.getEventPreference())txtTipo.setText("P");

        }
    }
}