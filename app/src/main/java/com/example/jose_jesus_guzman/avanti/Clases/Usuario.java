package com.example.jose_jesus_guzman.avanti.Clases;

/**
 * Created by jose_jesus_guzman on 9/07/16.
 */
public class Usuario {
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String password;

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String email, String telefono, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }
}

