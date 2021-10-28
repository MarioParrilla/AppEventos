package com.marioparrillamaroto.myeventsapp.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpInfoEventoMeeting;
import com.marioparrillamaroto.myeventsapp.ui.popUpEventos.PopUpInfoEventoPresencial;
import com.marioparrillamaroto.myeventsapp.R;

import java.time.LocalTime;
import java.util.ArrayList;

public class AdaptadorProximoEvento extends RecyclerView.Adapter<AdaptadorProximoEvento.ProximoEventoViewHolder>{

    private ArrayList<Evento> datos;

    public interface OnItemClickListener{
        void onItemClick(Evento item);
    }

    public AdaptadorProximoEvento(ArrayList<Evento> datos) {
        this.datos = datos;
    }

    @Override
    public ProximoEventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_evento, viewGroup, false);

        ProximoEventoViewHolder tvh = new ProximoEventoViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(ProximoEventoViewHolder viewHolder, int pos) {
        Evento item = datos.get(pos);

        viewHolder.bindProximoEvento(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ProximoEventoViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMensaje;
        private TextView txtHorario;
        private TextView txtNombreEvento;
        private TextView txtTipo;
        private HomeModel hm = new HomeModel();

        public ProximoEventoViewHolder(View itemView) {
            super(itemView);

            txtHorario = (TextView)itemView.findViewById(R.id.lblUsernameNotification);
            txtMensaje = (TextView)itemView.findViewById(R.id.lblMessageNotification);
            txtNombreEvento = (TextView)itemView.findViewById(R.id.lblNombreEvento);
            txtTipo = (TextView)itemView.findViewById(R.id.lblTipo);
        }

        public void bindProximoEvento(Evento e) {

            if (!e.getTema().equals("null")){
                txtHorario.setText(e.getHoraInicioParsed()+" - "+e.getHoraInicioParsed());
                txtMensaje.setText("Cita con @"+hm.getUsername(itemView.getContext(), e.getUserOwnerID())+", hablarás sobre: \n #"+e.getTema());
                txtNombreEvento.setText(e.getNombreEvento());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i;
                        if (e.getEventPreference()){
                            i = new Intent(itemView.getContext(), PopUpInfoEventoMeeting.class);
                            i.putExtra("infoEvento",e);
                            itemView.getContext().startActivity(i);
                        }
                        else if(!e.getEventPreference()){
                            i = new Intent(itemView.getContext(), PopUpInfoEventoPresencial.class);
                            i.putExtra("infoEvento",e);
                            itemView.getContext().startActivity(i);
                        };

                    }
                });
                if (e.getEventPreference())txtTipo.setText("M");
                else if (!e.getEventPreference())txtTipo.setText("P");
            }else{
                txtNombreEvento.setText(LocalTime.now().toString().substring(0,5));
                txtTipo.setText("");
                txtHorario.setText("");
                txtMensaje.setText(e.getNombreEvento());
            }

        }
    }
}
