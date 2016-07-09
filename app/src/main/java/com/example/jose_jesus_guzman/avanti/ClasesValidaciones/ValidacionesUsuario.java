package com.example.jose_jesus_guzman.avanti.ClasesValidaciones;

import com.example.jose_jesus_guzman.avanti.Clases.ExpresionesRegulares;

/**
 * Created by jose_jesus_guzman on 9/07/16.
 */
public class ValidacionesUsuario {


    private ExpresionesRegulares expresionesRegulares = new ExpresionesRegulares();

    public ValidacionesUsuario(){  }

    public boolean validacionNombreCompleto(String nombre){
        boolean respuesta;

        if (nombre.matches(expresionesRegulares.getExpresionNombreCompleto())){
            respuesta = true;
        }
        else{
            respuesta = false;
        }

        return respuesta;
    }

    public boolean validacionNombreUsuario(String nombre){
        boolean respuesta;

        if (nombre.matches(expresionesRegulares.getExpresionNombreUsuario())){
            respuesta = true;
        }
        else{
            respuesta = false;
        }

        return respuesta;
    }

    public boolean validacionTelefono(String telefono){
        boolean respuesta;

        if(telefono.matches(expresionesRegulares.getExpresionTelefono())){
            respuesta = true;
        }
        else {
            respuesta = false;
        }
        return respuesta;
    }

}
