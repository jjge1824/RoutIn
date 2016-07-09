package com.example.jose_jesus_guzman.avanti.ClasesViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jose_jesus_guzman.avanti.ClasesFirebase.FirebaseControl;
import com.example.jose_jesus_guzman.avanti.ClasesValidaciones.ValidacionesLogin;
import com.example.jose_jesus_guzman.avanti.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class PantallaInicialActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnCrear;
    private Button btnEntrar;
    private Button btnReset;

    FirebaseControl firebaseControl;
    boolean mostartInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);
        Firebase.setAndroidContext(this);

        mostartInternetDialog = false;

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }
        else{
            mostartInternetDialog= true;
        }


        //Ocultar status bar y action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        incializaComponentes();
        validarInternet();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistroUsuarioActivity.class));
            }
        });//end listener btn crear

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Firebase ref = new Firebase(firebaseControl.obtieneUrlFirebase());
                final String correoElectronico = txtEmail.getText().toString().trim();
                if (correoElectronico.equals(null) || correoElectronico.equals("")){
                    dialog(getResources().getString(R.string.java_alert_no_correo));
                }
                else{
                    ref.resetPassword(correoElectronico, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            dialogReset(correoElectronico);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            switch (firebaseError.getCode()){
                                case FirebaseError.USER_DOES_NOT_EXIST:
                                    dialog(getResources().getString(R.string.java_alert_inexistente));
                                    break;
                                case FirebaseError.NETWORK_ERROR:
                                    dialog(getResources().getString(R.string.java_error_network));
                                    limpiaPassword();
                                default:
                                    limpiaCampos();
                                    break;
                            }
                        }
                    });
                }
            }
        });//end listener btn reset

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo_electronico = txtEmail.getText().toString().trim();
                final String password = txtPassword.getText().toString();
                final Firebase ref = new Firebase(firebaseControl.obtieneUrlFirebase());

                //Instancia para acceder a las validaciones propias de los campos
                ValidacionesLogin validacionesLogin = new ValidacionesLogin();

                if (validacionesLogin.validacionEmail(correo_electronico) &&
                        validacionesLogin.validacionContrasena(password)){

                    ClaseAsyncTask asyncTask = new ClaseAsyncTask("Iniciando sesión",
                            "Por favor espere...",
                            correo_electronico,
                            password,
                            ref);
                    asyncTask.execute();
                    /*ref.authWithPassword(correo_electronico, password, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            startActivity (new Intent(getApplicationContext(), MapsActivity.class));
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            // there was an error
                            switch (firebaseError.getCode()) {
                                case FirebaseError.USER_DOES_NOT_EXIST:
                                    dialog(getResources().getString(R.string.java_alert_inexistente));
                                    limpiaCampos();
                                    break;
                                case FirebaseError.INVALID_PASSWORD:
                                    dialog(getResources().getString(R.string.java_alert_password_incorrecta));
                                    limpiaPassword();
                                    break;
                                case FirebaseError.NETWORK_ERROR:
                                    dialog(getResources().getString(R.string.java_error_network));
                                    limpiaPassword();
                                default:
                                    dialog(getResources().getString(R.string.java_error_unknown));
                                    limpiaCampos();
                                    break;
                            }

                        }
                    });*/

                }
                else{
                    if (correo_electronico.equals("")){
                        dialog(getResources().getString(R.string.java_alert_no_correo));
                        limpiaCampos();
                    }//End if no correo
                    else if (password.equals("")){
                        dialog(getResources().getString(R.string.java_alert_no_password));
                        limpiaPassword();
                    }//End if no contraseña
                    if (validacionesLogin.validacionEmail(correo_electronico) == false){
                        dialog(getResources().getString(R.string.java_alert_email_novalido));
                        limpiaCampos();
                    }//end ifCorreo mal
                    if (validacionesLogin.validacionContrasena(password) == false){
                        dialog(getResources().getString(R.string.java_alert_password_novalido));
                        limpiaPassword();
                    }//end if Contraseña mal
                }

            }
        });//End listener btn entrar

    }//End on create

    private void incializaComponentes(){
        txtEmail = (EditText) findViewById(R.id.inicial_et_email);
        txtPassword = (EditText) findViewById(R.id.inicial_et_password);
        btnCrear = (Button) findViewById(R.id.inicial_btn_crear);
        btnEntrar = (Button) findViewById(R.id.inicial_btn_entrar);
        btnReset = (Button) findViewById(R.id.inicial_btn_reset);
        firebaseControl = new FirebaseControl();
    }//End inicializa

    public void validarInternet(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            btnCrear.setEnabled(true);
            btnCrear.setClickable(true);
            btnEntrar.setEnabled(true);
            btnEntrar.setClickable(true);
            btnReset.setEnabled(true);
            btnReset.setClickable(true);

        }//End if esta conectado
        else{

            btnCrear.setEnabled(false);
            btnCrear.setClickable(false);
            btnCrear.setTextColor(getResources().getColor(R.color.colorDisabled));
            btnEntrar.setEnabled(false);
            btnEntrar.setClickable(false);
            btnEntrar.setTextColor(getResources().getColor(R.color.colorDisabled));
            btnEntrar.setBackgroundColor(getResources().getColor(R.color.colorButtonDisabled));
            btnReset.setEnabled(false);
            btnReset.setClickable(false);
            btnReset.setTextColor(getResources().getColor(R.color.colorDisabled));

            if (mostartInternetDialog){
                dialog(getResources().getString(R.string.java_error_network));
            }


        }//End else no esta conectado
    }//end validar internet

    private void dialog(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.java_alert_title_error);
        builder.setMessage(mensaje);
        builder.setPositiveButton(R.string.java_alert_positivebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void dialogBienvenido(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.java_alert_title_bienvenido);
        builder.setPositiveButton(R.string.java_alert_positivebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void dialogReset(String email){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.java_alert_title_reset);
        builder.setMessage(R.string.java_alert_mensaje + ": "+ email+"");
        builder.setPositiveButton(R.string.java_alert_positivebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }


    private void limpiaCampos(){
        txtEmail.setText("");
        txtPassword.setText("");
    }

    private void limpiaPassword(){
        txtPassword.setText("");
    }



    class ClaseAsyncTask extends AsyncTask {

        ProgressDialog pDialog;
        private String progressTitle;
        private String progressMessage;
        private Firebase ref;
        private String correo_electronico;
        private String password;

        public ClaseAsyncTask( String progressTitle, String progressMessage,
                               String correo_electronico, String password, Firebase ref) {
            //this.activity = activity;
            this.progressTitle = progressTitle;
            this.progressMessage = progressMessage;
            this.correo_electronico = correo_electronico;
            this.password = password;
            this.ref = ref;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            ref.authWithPassword(correo_electronico, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    startActivity (new Intent(getApplicationContext(), MapsActivity.class));
                    finish();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                    switch (firebaseError.getCode()) {
                        case FirebaseError.USER_DOES_NOT_EXIST:
                            dialog(getResources().getString(R.string.java_alert_inexistente));
                            limpiaCampos();
                            break;
                        case FirebaseError.INVALID_PASSWORD:
                            dialog(getResources().getString(R.string.java_alert_password_incorrecta));
                            limpiaPassword();
                            break;
                        case FirebaseError.NETWORK_ERROR:
                            dialog(getResources().getString(R.string.java_error_network));
                            limpiaPassword();
                        default:
                            dialog(getResources().getString(R.string.java_error_unknown));
                            limpiaCampos();
                            break;
                    }
                    return;
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pDialog.dismiss();
            Toast.makeText(PantallaInicialActivity.this, "Bienvenido a avanti", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(PantallaInicialActivity.this);
            pDialog.setTitle(progressTitle);
            pDialog.setMessage(progressMessage);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

}
