package com.example.my_application1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Recuperar el texto enviado desde MainActivity
        String eltexto = getIntent().getExtras().getString("pasandoeltexto");

        // Mostrar el texto en un TextView
        TextView textViewMensaje = findViewById(R.id.lbl_mensaje_menu);
        textViewMensaje.setText(eltexto);
    }



}
