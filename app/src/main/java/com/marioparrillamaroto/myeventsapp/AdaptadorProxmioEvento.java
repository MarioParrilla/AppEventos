package com.marioparrillamaroto.myeventsapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.ProximoEvento;

import java.util.ArrayList;

class AdaptadorProxmioEvento
        extends RecyclerView.Adapter<AdaptadorProxmioEvento.ProximoEventoViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<ProximoEvento> datos;

    public static class ProximoEventoViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtMensaje;
        private TextView txtHorario;
        private ImageView iconUser;

        public ProximoEventoViewHolder(View itemView) {
            super(itemView);

            txtHorario = (TextView)itemView.findViewById(R.id.horario);
            txtMensaje = (TextView)itemView.findViewById(R.id.mensaje);

        }

        public void bindTitular(ProximoEvento e) {
            txtHorario.setText(e.getHoraInicio()+" - "+e.getHoraFinal());
            txtMensaje.setText("Cita con "+e.getUsuarioCitado()+", hablarás sobre: \n #"+e.getTema());
        }
    }

    public AdaptadorProxmioEvento(ArrayList<ProximoEvento> datos) {
        this.datos = datos;
    }

    @Override
    public ProximoEventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_evento, viewGroup, false);

        itemView.setOnClickListener(this);
        //android:background="?android:attr/selectableItemBackground"

        ProximoEventoViewHolder tvh = new ProximoEventoViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(ProximoEventoViewHolder viewHolder, int pos) {
        ProximoEvento item = datos.get(pos);

        viewHolder.bindTitular(item);
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
