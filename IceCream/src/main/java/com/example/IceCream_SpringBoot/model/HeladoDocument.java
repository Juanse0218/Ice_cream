package com.example.IceCream_SpringBoot.model;

import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.Id;

@Document(collection = "helados")
public class HeladoDocument {

    @Id
    private String id;
    private String nombre;
    private String sabor;
    private String tipo;
    private double precio;
    private int unidades;
    private String ubicacion;

    public HeladoDocument() {
    }

    public HeladoDocument(String nombre, String sabor, String tipo, double precio, int unidades, String ubicacion) {
        this.nombre = nombre;
        this.sabor = sabor;
        this.tipo = tipo;
        this.precio = precio;
        this.unidades = unidades;
        this.ubicacion = ubicacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
