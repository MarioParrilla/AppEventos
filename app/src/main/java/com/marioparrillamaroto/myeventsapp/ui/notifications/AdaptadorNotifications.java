package com.marioparrillamaroto.myeventsapp.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marioparrillamaroto.myeventsapp.R;

import java.util.ArrayList;

public class AdaptadorNotifications
        extends RecyclerView.Adapter<AdaptadorNotifications.NotificationsViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Notifications> datos;

    public static class NotificationsViewHolder
            extends RecyclerView.ViewHolder {

        private TextView lblUsernameEventOwner;
        private TextView lblMessage;

        public NotificationsViewHolder(View itemView) {
            super(itemView);

            lblUsernameEventOwner = (TextView)itemView.findViewById(R.id.lblUsernameNotification);
            lblMessage = (TextView)itemView.findViewById(R.id.lblMessageNotification);

        }

        public void bindNotifications(Notifications n) {
            lblUsernameEventOwner.setText("@"+n.getUsernameEventOwner());
            lblMessage.setText(n.getMessage());
        }
    }

    public AdaptadorNotifications(ArrayList<Notifications> datos) {
        this.datos = datos;
    }

    @Override
    public NotificationsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjeta_notifications, viewGroup, false);

        itemView.setOnClickListener(this);
        //android:background="?android:attr/selectableItemBackground"

        NotificationsViewHolder tvh = new NotificationsViewHolder(itemView);

        return tvh;
    }


    @Override
    public void onBindViewHolder(NotificationsViewHolder viewHolder, int pos) {
        Notifications item = datos.get(pos);

        viewHolder.bindNotifications(item);
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
