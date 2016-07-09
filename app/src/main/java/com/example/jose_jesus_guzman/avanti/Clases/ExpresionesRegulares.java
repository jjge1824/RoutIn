package com.example.jose_jesus_guzman.avanti.Clases;

/**
 * Created by jose_jesus_guzman on 9/07/16.
 */

public class ExpresionesRegulares {
    private final String expresionPassword = "[A-Za-z0-9]{4,16}";
    private final String expresionNombreCompleto = "[A-Za-záéíóúÁÉÍÓÚ ]{4,100}";
    private final String expresionNombreUsuario = "[A-Za-z0-9 ]{4,16}";//fALTAN ACENTOS
    private final String expresionEmail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
            + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
            + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private final String expresionUrl = "^(https?|ftp|file)://" +
            "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private final String expresionTelefono = "\\d{10}";

    public ExpresionesRegulares(){    }

    public String getExpresionPassword(){
        return this.expresionPassword;
    }

    public String getExpresionEmail(){
        return this.expresionEmail;
    }

    public String getExpresionUrl(){
        return this.expresionUrl;
    }

    public String getExpresionNombreCompleto() {
        return expresionNombreCompleto;
    }

    public String getExpresionNombreUsuario() {
        return expresionNombreUsuario;
    }

    public String getExpresionTelefono() {
        return expresionTelefono;
    }
}
