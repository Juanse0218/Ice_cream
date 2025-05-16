package com.example.IceCream_SpringBoot.model;

public class ItemVenta {
    private HeladoDocument helado;
    private int cantidad;
    private double precioUnitario;
    private double subTotal;

    public ItemVenta(HeladoDocument helado, int cantidad, double precioUnitario, double subTotal) {
        this.helado = helado;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
    }

    public HeladoDocument getHelado() {
        return helado;
    }

    public void setHelado(HeladoDocument helado) {
        this.helado = helado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
