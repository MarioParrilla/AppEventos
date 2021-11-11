package com.marioparrillamaroto.myeventsapp;

public class DispositivoBluetooth {
    private String nombreDispositivo;
    private String addressDispositivo;
    private int typeOfDevice;
    private int mayorType;
    public DispositivoBluetooth(String nombreDispositivo, String addressDispositivo, int typeOfDevice, int mayorType){
        this.nombreDispositivo = nombreDispositivo;
        this.addressDispositivo = addressDispositivo;
        this.typeOfDevice = typeOfDevice;
        this.mayorType = mayorType;
    }

    public String getNombreDispositivo() {
        return nombreDispositivo;
    }

    public void setNombreDispositivo(String nombreDispositivo) {
        this.nombreDispositivo = nombreDispositivo;
    }

    public String getAddressDispositivo() {
        return addressDispositivo;
    }

    public void setAddressDispositivo(String addressDispositivo) {
        this.addressDispositivo = addressDispositivo;
    }

    public int getTypeOfDevice() {
        return typeOfDevice;
    }

    public void setTypeOfDevice(int typeOfDevice) {
        this.typeOfDevice = typeOfDevice;
    }

    public int getMayorType() {
        return mayorType;
    }

    public void setMayorType(int mayorType) {
        this.mayorType = mayorType;
    }
}
