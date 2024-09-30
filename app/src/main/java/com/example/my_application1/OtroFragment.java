package com.example.my_application1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class OtroFragment extends Fragment {

    public OtroFragment() {
        // Constructor vacío obligatorio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.activity_otro_fragment, container, false);

        // Inicializar el botón dentro del fragmento
        Button button3 = view.findViewById(R.id.btn_ComFrag);

        // Asignar el OnClickListener al botón para volver a MainActivity
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el Intent para ir a MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                // Terminar la actividad actual (opcional)
                getActivity().finish();
            }
        });

        return view;
    }
}
