package com.example.jose_jesus_guzman.avanti.ClasesValidaciones;

import com.example.jose_jesus_guzman.avanti.Clases.ExpresionesRegulares;

/**
 * Created by jose_jesus_guzman on 9/07/16.
 */
public class ValidacionesLogin {

    private ExpresionesRegulares expresionesRegulares = new ExpresionesRegulares();

    public ValidacionesLogin(){    }

    public boolean validacionEmail(String email){
        boolean respuesta;

        if (email.matches(expresionesRegulares.getExpresionEmail())){
            respuesta = true;
        }
        else { respuesta = false; }

        return respuesta;
    }

    public boolean validacionContrasena(String contrasena){
        boolean respuesta;

        if (contrasena.matches(expresionesRegulares.getExpresionPassword())){
            respuesta = true;
        }
        else { respuesta = false; }

        return respuesta;
    }

}