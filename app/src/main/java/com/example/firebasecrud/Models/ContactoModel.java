package com.example.firebasecrud.Models;

import java.io.Serializable;

public class ContactoModel implements Serializable {

    private String _id;
    private String _nombre;
    private String _numero;

    public ContactoModel() {
    }

    public ContactoModel(String _id, String _nombre, String _numero) {
        this._id = _id;
        this._nombre = _nombre;
        this._numero = _numero;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_numero() {
        return _numero;
    }

    public void set_numero(String _numero) {
        this._numero = _numero;
    }
}
