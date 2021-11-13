package com.marioparrillamaroto.myeventsapp.ui.chats;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.marioparrillamaroto.myeventsapp.Chat;
import com.marioparrillamaroto.myeventsapp.DispositivoBluetooth;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.databinding.FragmentChatsBinding;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChatsFragment extends Fragment {
        private @NonNull
        FragmentChatsBinding binding;
        private RecyclerView recView;
        private ArrayList<Chat> datos;
        private ArrayList<Chat> dispositivosConocidos, temp;
        private LinearLayoutManager lym;
        private Toolbar toolbar;
        private ActionMenuItemView enable, disable, search, info;
        private BluetoothAdapter bluetoothAdapter;

        private final int DISCOVERABLE_DURATION = 300;

        @SuppressLint("RestrictedApi")
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

                binding = FragmentChatsBinding.inflate(inflater, container, false);
                View root = binding.getRoot();
                super.onCreate(savedInstanceState);
                toolbar = root.findViewById(R.id.toolBarChat);
                binding.toolBarChat.inflateMenu(R.menu.bluetooth_options);
                recView = (RecyclerView) root.findViewById(R.id.recViewChats);
                lym = new LinearLayoutManager(root.getContext().getApplicationContext());

                enable = (ActionMenuItemView) toolbar.findViewById(R.id.enableBluetooth);
                disable = (ActionMenuItemView) toolbar.findViewById(R.id.disableBluetooth);
                search = (ActionMenuItemView)toolbar.findViewById(R.id.searchBluetooth);
                info = (ActionMenuItemView)toolbar.findViewById(R.id.infoItemsB);

                info.setEnabled(false);
                info.setTextColor(Color.WHITE);

                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                bluetoothAdapter.cancelDiscovery();

                IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                getActivity().registerReceiver(bReceiver, filtro);

                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                getActivity().registerReceiver(bReceiver, filter);

                // Register for broadcasts when discovery has finished
                filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                getActivity().registerReceiver(bReceiver, filter);

                binding.toolBarChat.setOnMenuItemClickListener(item -> {
                        switch (item.getItemId()) {
                                case R.id.searchBluetooth:
                                        // searchBluetooth
                                        Toast.makeText(root.getContext(), "Buscando dispositivos...", Toast.LENGTH_SHORT).show();
                                        startSearch();
                                        return true;
                                case R.id.enableBluetooth:
                                        if (!bluetoothAdapter.isEnabled()){
                                                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                                bluetoothToOn.launch(enableIntent);
                                        }
                                        return true;
                                case R.id.disableBluetooth:
                                        if (bluetoothAdapter.isEnabled()){
                                                bluetoothAdapter.disable();
                                                setBluetoothToOff();
                                        }
                                        return true;
                                default:
                                        return false;
                        }
                });

                if (bluetoothAdapter == null) {
                        datos = new ArrayList<Chat>();
                        datos.add(new Chat("MyEventsApp", new DispositivoBluetooth("❌Tu movil no tiene bluetooth❌",null,0,0)));
                        setApater(datos);
                        enable.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.INVISIBLE);
                        disable.setVisibility(View.INVISIBLE);
                } else{
                        if (bluetoothAdapter.isEnabled()) {
                                setBluetoothToOn();
                        }else{
                                setBluetoothToOff();
                        }

                }

                return root;
        }

        private ActivityResultLauncher<Intent> bluetoothToOn = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                                setBluetoothToOn();
                        }else{
                                setBluetoothToOff();
                        }
                }
        });

        private void setBluetoothToOn(){
                datos = new ArrayList<Chat>();
                datos.add(new Chat("MyEventsApp", new DispositivoBluetooth("🔎 Debes buscar dispositivos 🔍",null,0,0)));
                setApater(datos);
                itemBluetoothOn();
        }

        private void setBluetoothToOff(){
                datos = new ArrayList<Chat>();
                datos.add(new Chat("MyEventsApp", new DispositivoBluetooth("❌Debes activar el bluetooth para funcionar❌",null, 0, 0)));
                setApater(datos);
                itemBluetoothOff();
        }

        private void startSearch(){
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
                startActivity(discoverableIntent);
                dispositivosConocidos = new ArrayList<>();
                temp = new ArrayList<>();

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                                dispositivosConocidos.add(new Chat("Admin", new DispositivoBluetooth(device.getName(), device.getAddress(), device.getBluetoothClass().getDeviceClass(),device.getBluetoothClass().getMajorDeviceClass())));
                        }
                        setApater(dispositivosConocidos);
                }

                if (bluetoothAdapter.isDiscovering()) {
                        bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();

        }


        private void setApater(ArrayList<Chat> datos){
                AdaptadorChats adapterData = new AdaptadorChats(datos);
                lym.setOrientation(LinearLayoutManager.VERTICAL);
                recView.setHasFixedSize(true);
                recView.setLayoutManager(lym);
                recView.setAdapter(adapterData);
                adapterData.notifyDataSetChanged();
        }

        private void itemBluetoothOn(){
                enable.setVisibility(View.INVISIBLE);
                search.setVisibility(View.VISIBLE);
                disable.setVisibility(View.VISIBLE);
        }

        private void itemBluetoothOff(){
                enable.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                disable.setVisibility(View.INVISIBLE);
        }

        private final BroadcastReceiver bReceiver = new BroadcastReceiver()
        {

                @Override
                public void onReceive(Context context, Intent intent) {
                        final String action = intent.getAction();
                        if(BluetoothDevice.ACTION_FOUND.equals(action)){
                              BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                              if (device.getBondState()!=BluetoothDevice.BOND_BONDED) {
                                      if (device.getName() != null && device.getAddress() != null) {
                                              boolean encontrado = false;
                                              Chat c = new Chat("Admin", new DispositivoBluetooth(device.getName(), device.getAddress(), device.getBluetoothClass().getDeviceClass(), device.getBluetoothClass().getMajorDeviceClass()));

                                              for (Chat d : dispositivosConocidos) {
                                                if (d.getUser2().getAddressDispositivo().equals(c.getUser2().getAddressDispositivo()))encontrado = true;
                                              }
                                              if (!encontrado) dispositivosConocidos.add(c);

                                              setApater(dispositivosConocidos);
                                      }
                              }
                        }
                        else if (bluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                                if (dispositivosConocidos!=null){
                                        if (dispositivosConocidos.size() == 0){
                                                datos.clear();
                                                datos.add(new Chat("MyEventsApp", new DispositivoBluetooth("❌No se ha encontrado nada❌",null,0,0)));
                                                setApater(datos);
                                        }
                                        Toast.makeText(getContext(), "Busqueda terminada", Toast.LENGTH_SHORT).show();
                                }
                        }

                        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                                final int estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                                switch (estado) {

                                        case BluetoothAdapter.STATE_OFF:
                                        {
                                                setBluetoothToOff();
                                                break;
                                        }

                                        case BluetoothAdapter.STATE_ON:
                                        {
                                                setBluetoothToOn();

                                                break;
                                        }
                                        default:
                                                break;
                                }
                        }
                }
        };

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                getActivity().unregisterReceiver(bReceiver);
                bluetoothAdapter.cancelDiscovery();
                binding = null;
        }
}

