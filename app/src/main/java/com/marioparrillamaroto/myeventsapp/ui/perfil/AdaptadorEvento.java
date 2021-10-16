package com.marioparrillamaroto.myeventsapp.ui.perfil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.R;

import java.util.ArrayList;

public class AdaptadorEvento
        extends RecyclerView.Adapter<AdaptadorEvento.EventoViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Evento> datos;

    public static class EventoViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtMensaje;
        private TextView txtHorario;
        private TextView txtNombreEvento;
        private ImageView imgIcon;

        public EventoViewHolder(View itemView) {
            super(itemView);

            txtHorario = (TextView)itemView.findViewById(R.id.lblUsernameNotificationP);
            txtMensaje = (TextView)itemView.findViewById(R.id.lblMessageNotificationP);
            txtNombreEvento = (TextView)itemView.findViewById(R.id.lblNombreEventoP);
            imgIcon = (ImageView)itemView.findViewById(R.id.imgEventoP);
        }

        public void bindEvento(Evento e) {
            txtHorario.setText(e.getHoraInicio()+" - "+e.getHoraFinal());
            txtMensaje.setText("Cita con "+e.getUsuarioCitado()+", hablarás sobre: \n #"+e.getTema());
            txtNombreEvento.setText(e.getNombreEvento());
            if (e.getEventPreference())imgIcon.setImageResource(R.drawable.ic_pc);
            else imgIcon.setImageResource(R.drawable.ic_walk);
        }
    }

    public AdaptadorEvento(ArrayList<Evento> datos) {
        this.datos = datos;
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_evento_perfil, viewGroup, false);

        itemView.setOnClickListener(this);
        //android:background="?android:attr/selectableItemBackground"

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

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}
