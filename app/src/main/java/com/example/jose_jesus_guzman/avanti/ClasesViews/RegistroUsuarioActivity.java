package com.example.jose_jesus_guzman.avanti.ClasesViews;

/**
 * Created by jose_jesus_guzman on 9/07/16.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jose_jesus_guzman.avanti.Clases.Usuario;
import com.example.jose_jesus_guzman.avanti.ClasesFirebase.FirebaseControl;
import com.example.jose_jesus_guzman.avanti.ClasesValidaciones.ValidacionesLogin;
import com.example.jose_jesus_guzman.avanti.ClasesValidaciones.ValidacionesUsuario;
import com.example.jose_jesus_guzman.avanti.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegistroUsuarioActivity extends AppCompatActivity {
    private TextInputLayout txtNombre;
    private TextInputLayout txtApellido;
    private TextInputLayout txtEmail;
    private TextInputLayout txtTelefono;
    private TextInputLayout txtPassword;
    private TextInputLayout txtPaswordConf;
    private TextView tvTerminos;
    private CheckBox chbTerminos;
    private Toolbar toolbar;

    FirebaseControl firebaseControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        Firebase.setAndroidContext(this);
        incializaComponentes();

        //Cambiar color de title en action bar
        setSupportActionBar(toolbar);

        tvTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jose1824/Avanti")));
                //CAMBIAR LA URL POR LA DE LOS TERMINOS

            }
        });
        chbTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chbTerminos.setTextColor(getResources().getColor(R.color.colorPrincipalTextBlack));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solo_palomita, menu);
        return true;
    }//End oncreate options

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Al apretar el icono de hecho en toolbar
        if (id == R.id.action_aceptar) {
            mandarUsuario();//Metodo para subir la informacion a firebase y dar de alta al usuario
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//End on options

    private void incializaComponentes(){
        txtNombre = (TextInputLayout) findViewById(R.id.registro_textLayout_nombre);
        txtApellido = (TextInputLayout) findViewById(R.id.registro_textLayout_apellidos);
        txtEmail = (TextInputLayout) findViewById(R.id.registro_textLayout_correo);
        txtTelefono = (TextInputLayout) findViewById(R.id.registro_textLayout_telefono);
        txtPassword = (TextInputLayout) findViewById(R.id.iregistro_textLayout_pass);
        txtPaswordConf = (TextInputLayout) findViewById(R.id.registro_textLayout_confPAss);
        tvTerminos = (TextView) findViewById(R.id.crear_tv_terminos);
        chbTerminos = (CheckBox) findViewById(R.id.crear_chb_terminos);
        toolbar = (Toolbar) findViewById(R.id.registro_toolbar);
        firebaseControl = new FirebaseControl();
    }

    private void mandarUsuario(){
        //Instancias para acceder a las validaciones propias de los campos
        ValidacionesLogin validacionesLogin = new ValidacionesLogin();
        ValidacionesUsuario validacionesUsuario = new ValidacionesUsuario();
        //Mandar todos los datos del formulario a la clase Usuario
        //Esto es porque se recuperaran de ahi y se mandará la clase completa a FireBase
        final Usuario user = new Usuario(txtNombre.getEditText().getText().toString().trim(),
                txtApellido.getEditText().getText().toString().trim(),
                txtEmail.getEditText().getText().toString().trim(),
                txtTelefono.getEditText().getText().toString().trim(),
                txtPassword.getEditText().getText().toString());

        //Se usa la misma expresion para apellido y nombre.
        if (validacionesUsuario.validacionNombreCompleto(user.getNombre()) &&
                validacionesUsuario.validacionNombreCompleto(user.getApellidos()) &&
                validacionesLogin.validacionEmail(user.getEmail()) &&
                validacionesUsuario.validacionTelefono(user.getTelefono()) &&
                validacionesLogin.validacionContrasena(user.getPassword()) &&
                user.getPassword().equals(txtPaswordConf.getEditText().getText().toString()) &&
                chbTerminos.isChecked()){

            final Firebase ref = new Firebase(firebaseControl.obtieneUrlFirebase());

            ref.createUser(user.getEmail(), user.getPassword(),
                    new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {
                            //Pone los datos en el child de usuarios con el uid del usuario como nombre de branch
                            Firebase usuario = ref.child("usuarios").child(stringObjectMap.get("uid") + "");

                            usuario.setValue(user);//Pasas toda la clase para ponerla en firebase con cualquiera que incluya

                            dialog(getResources().getString(R.string.java_alert_tile_crear),
                                    getResources().getString(R.string.java_alert_message_crear));

                            startActivity(new Intent(getApplicationContext(), PantallaInicialActivity.class));//Cambiar esto :3 <-----------
                            finish();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            switch (firebaseError.getCode()){
                                case FirebaseError.UNKNOWN_ERROR:
                                    dialog(getResources().getString(R.string.java_alert_title_error),
                                            getResources().getString(R.string.java_error_unknown));
                                    break;
                                case FirebaseError.NETWORK_ERROR:
                                    dialog(getResources().getString(R.string.java_alert_title_error),
                                            getResources().getString(R.string.java_error_network));
                                    break;
                                case FirebaseError.DISCONNECTED:
                                    dialog(getResources().getString(R.string.java_alert_title_error),
                                            getResources().getString(R.string.java_error_disconnected));
                                    break;
                                case FirebaseError.USER_CODE_EXCEPTION:
                                    dialog(getResources().getString(R.string.java_alert_title_error),
                                            getResources().getString(R.string.java_alert_message_usuarioExiste));
                                    break;
                                default:
                                    dialog(getResources().getString(R.string.java_alert_title_error),
                                            getResources().getString(R.string.java_error_unknown));
                                    break;
                            }
                            limpiarContraseña();
                        }
                    });

        }//end if principal
        else{
            //SI LOS CAMPOS SE ENCUENTRAN VACIOS
            if (user.getNombre().equals("")){
                txtNombre.setError(getResources().getString(R.string.java_alert_no_nombre));
                limpiarContraseña();
                return;
            }//end if nombre vacio
            else{
                txtNombre.setError(null);
            }//end  else
            if (user.getApellidos().equals("")){
                txtApellido.setError(getResources().getString(R.string.java_alert_no_apellido));
                limpiarContraseña();
                return;
            }//end if apellido vacio
            else{
                txtApellido.setError(null);
            }//end  else
            if (user.getEmail().equals("")){
                txtEmail.setError(getResources().getString(R.string.java_alert_no_email));
                limpiarContraseña();
                return;
            }//end if email vacio
            else{
                txtEmail.setError(null);
            }//end  else
            if (user.getTelefono().equals("")){
                txtTelefono.setError(getResources().getString(R.string.java_alert_no_telefono));
                limpiarContraseña();
                return;
            }//end if telefono vacio
            else{
                txtTelefono.setError(null);
            }//end  else
            if (user.getPassword().equals("")){
                txtPassword.setError(getResources().getString(R.string.java_alert_no_password));
                limpiarContraseña();
                return;
            }//end if contraseña vacia
            else{
                txtPassword.setError(null);
            }//end  else
            if (txtPaswordConf.getEditText().getText().toString().equals("")){
                txtPassword.setError(getResources().getString(R.string.java_alert_no_comfirmacion));
                limpiarContraseña();
                return;
            }//end if comfirmacion de la contraseña vacia
            else{
                txtPaswordConf.setError(null);
            }//end  else
            //SI NO ACEPTO LOS TERMINOS
            if (chbTerminos.isChecked() == false){
                dialog(getResources().getString(R.string.java_alert_title_error),
                        getResources().getString(R.string.java_alert_no_aceptar));
                chbTerminos.setTextColor(getResources().getColor(R.color.colorErrorRed));
                limpiarContraseña();
                return;
            }

            //SI LAS CONTRASEÑAS NO COINCIDEN
            if (user.getPassword().equals(txtPaswordConf.getEditText().getText().toString()) == false){
                txtPassword.setError(getResources().getString(R.string.java_alert_password_nocoincide));
                txtPaswordConf.setError(getResources().getString(R.string.java_alert_password_nocoincide));
                limpiarContraseña();
                return;
            }//end if contraseñas no coinciden
            else{
                txtPassword.setError(null);
                txtPaswordConf.setError(null);
            }//end  else
            //ERRORES DE VALIDACION
            if (validacionesUsuario.validacionNombreCompleto(user.getNombre()) == false){
                txtNombre.setError(getResources().getString(R.string.java_alert_nombre_novalido));
                limpiarNombre();
                limpiarContraseña();
            }//end if nombre no valido
            else{
                txtNombre.setError(null);
            }//end  else
            if (validacionesUsuario.validacionNombreCompleto(user.getApellidos()) == false){
                txtApellido.setError(getResources().getString(R.string.java_alert_apellido_novalido));
                limpiarApellidos();
                limpiarContraseña();
                return;
            }//end if apellidos no validos
            else{
                txtApellido.setError(null);
            }//end  else
            if (validacionesLogin.validacionEmail(user.getEmail()) == false){
                txtEmail.setError(getResources().getString(R.string.java_alert_email_novalido));
                limpiarEmail();
                limpiarContraseña();
            }//end if correo no valido
            else{
                txtEmail.setError(null);
            }//end  else
            if (validacionesUsuario.validacionTelefono(user.getTelefono()) == false){
                txtTelefono.setError(getResources().getString(R.string.java_alert_telefono_novalido));
                limpiarTelefono();
                limpiarContraseña();
                return;
            }//end if telefono no valido
            else{
                txtTelefono.setError(null);
            }//end  else
            if (validacionesLogin.validacionContrasena(user.getPassword()) == false){
                txtPassword.setError(getResources().getString(R.string.java_alert_password_novalido));
                limpiarContraseña();
                return;
            }//end if contraseña no valida
            else{
                txtPassword.setError(null);
            }//end  else

        }//End else principal
    }//End mandar usuario

    private void dialog(String tile, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tile);
        builder.setMessage(mensaje);
        builder.setPositiveButton(R.string.java_alert_positivebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void limpiarNombre(){
        txtNombre.getEditText().setText("");
    }//end limpiar nombre

    private void limpiarApellidos(){
        txtApellido.getEditText().setText("");
    } //end limpiar apelldos

    private void limpiarEmail(){
        txtEmail.getEditText().setText("");
    }//end limpiar email

    private void limpiarTelefono(){
        txtTelefono.getEditText().setText("");
    }//emd limpiar telefono

    private void limpiarContraseña(){
        txtPassword.getEditText().setText("");
        txtPaswordConf.getEditText().setText("");
    }//end limpiar contraseña

}//end class
