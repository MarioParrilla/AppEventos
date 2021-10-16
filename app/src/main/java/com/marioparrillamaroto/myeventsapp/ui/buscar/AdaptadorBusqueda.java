package com.marioparrillamaroto.myeventsapp.ui.buscar;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.Usuario;

import java.util.ArrayList;

public class AdaptadorBusqueda
        extends RecyclerView.Adapter<AdaptadorBusqueda.BusquedaViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Usuario> datos;

    public static class BusquedaViewHolder
            extends RecyclerView.ViewHolder {

        private TextView lblUsernameBsq;
        private TextView lblDescripcionBsq;

        public BusquedaViewHolder(View itemView) {
            super(itemView);

            lblUsernameBsq = (TextView)itemView.findViewById(R.id.lblUsernameNotificationP);
            lblDescripcionBsq = (TextView)itemView.findViewById(R.id.lblMessageNotificationP);

        }

        public void bindBusqueda(Usuario UB) {
            lblUsernameBsq.setText("@"+UB.getUsername());
            lblDescripcionBsq.setText(UB.getDescription());
        }
    }

    public AdaptadorBusqueda(ArrayList<Usuario> datos) {
        this.datos = datos;
    }

    @Override
    public BusquedaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_busqueda, viewGroup, false);

        itemView.setOnClickListener(this);
        //android:background="?android:attr/selectableItemBackground"

        BusquedaViewHolder tvh = new BusquedaViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(BusquedaViewHolder viewHolder, int pos) {
        Usuario item = datos.get(pos);

        viewHolder.bindBusqueda(item);
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
