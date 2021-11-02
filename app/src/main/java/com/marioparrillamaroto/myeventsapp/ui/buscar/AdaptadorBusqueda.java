package com.marioparrillamaroto.myeventsapp.ui.buscar;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.Evento;
import com.marioparrillamaroto.myeventsapp.ui.ExternalProfile.ExternalProfileActivity;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.Usuario;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdaptadorBusqueda extends RecyclerView.Adapter<AdaptadorBusqueda.BusquedaViewHolder> {

    private ArrayList<Usuario> datos, datosOriginales;

    public interface OnItemClickListener{
        void onItemClick(Evento item);
    }

    public AdaptadorBusqueda(ArrayList<Usuario> datos) {
        this.datos = datos;
        this.datosOriginales = new ArrayList<Usuario>();
        datosOriginales.addAll(datos);
    }

    @Override
    public BusquedaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_busqueda, viewGroup, false);

        BusquedaViewHolder tvh = new BusquedaViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(BusquedaViewHolder viewHolder, int pos) {
        Usuario item = datos.get(pos);

        viewHolder.bindBusqueda(item);
    }

    public void filtrado(String usuarioABuscar){
        int longitud = usuarioABuscar.length();
        if (longitud==0){
            datos.clear();
            datos.addAll(datosOriginales);
        }else{
            datos.clear();
            datos.addAll(datosOriginales.stream().filter(usuario -> usuario.getUsername().toLowerCase().startsWith(usuarioABuscar.toLowerCase())).collect(Collectors.toList()));
            ArrayList<Usuario> collection = (ArrayList<Usuario>) datos.stream().filter(usuario -> usuario.getUsername().toLowerCase().startsWith(usuarioABuscar.toLowerCase())).collect(Collectors.toList());
            datos.clear();
            datos.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class BusquedaViewHolder extends RecyclerView.ViewHolder {

        private TextView lblUsernameBsq;
        private TextView lblDescripcionBsq;

        public BusquedaViewHolder(View itemView) {
            super(itemView);

            lblUsernameBsq = (TextView)itemView.findViewById(R.id.lblUsernameNotificationP);
            lblDescripcionBsq = (TextView)itemView.findViewById(R.id.lblMessageNotificationP);

        }

        public void bindBusqueda(Usuario UB) {
            lblUsernameBsq.setText("@"+UB.getUsername());
            lblDescripcionBsq.setText(UB.getPhonenumber());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemView.getContext(), ExternalProfileActivity.class);
                    i.putExtra("infoUsuario",UB);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
