package com.marioparrillamaroto.myeventsapp.ui.home;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.R;

import java.util.ArrayList;

public class AdaptadorProximoEvento
        extends RecyclerView.Adapter<AdaptadorProximoEvento.ProximoEventoViewHolder>
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

            txtHorario = (TextView)itemView.findViewById(R.id.lblUsernameNotification);
            txtMensaje = (TextView)itemView.findViewById(R.id.lblMessageNotification);

        }

        public void bindProximoEvento(ProximoEvento e) {
            txtHorario.setText(e.getHoraInicio()+" - "+e.getHoraFinal());
            txtMensaje.setText("Cita con "+e.getUsuarioCitado()+", hablarás sobre: \n #"+e.getTema());
        }
    }

    public AdaptadorProximoEvento(ArrayList<ProximoEvento> datos) {
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

        viewHolder.bindProximoEvento(item);
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
