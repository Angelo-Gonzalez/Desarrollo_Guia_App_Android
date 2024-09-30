package com.example.my_application1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    private TextView elmensaje;  // Para mostrar la ubicación
    private int locationUpdateCount = 0;  // Contador de actualizaciones de ubicación
    private static final int MAX_UPDATES = 2;  // Número máximo de actualizaciones que queremos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de los elementos UI
        EditText eltexto = findViewById(R.id.txt_mitexto);
        elmensaje = findViewById(R.id.lbl_mensaje_menu);
        Button miboton = findViewById(R.id.btn_leer);
        Button miboton2 = findViewById(R.id.btn_Frag);
        Button buttonGPS = findViewById(R.id.button5); // Botón para obtener GPS

        // Instancia LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Configuración del OnClickListener para el botón de fragmento
        miboton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Blank1, new OtroFragment()).commit();
            }
        });

        // Configuración del OnClickListener para el botón
        miboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Establecer el texto del TextView con el contenido del EditText
                elmensaje.setText(eltexto.getText());

                // Crear un Intent para iniciar MenuActivity
                Intent miintento = new Intent(MainActivity.this, MenuActivity.class);
                miintento.putExtra("pasandoeltexto", eltexto.getText().toString());

                // Iniciar la nueva actividad
                startActivity(miintento);
            }
        });

        // Configuración del OnClickListener para el botón GPS
        buttonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar permisos de ubicación
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Solicitar permisos
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 1);
                } else {
                    // Obtener la ubicación actual solo al hacer clic en el botón
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MainActivity.this);
                    Toast.makeText(MainActivity.this, "Actualizando ubicación...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Este método se llama cuando la ubicación cambia
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Mostrar la ubicación actual como un Toast
        if (locationUpdateCount < MAX_UPDATES) {
            Toast.makeText(this, "Latitud: " + latitude + ", Longitud: " + longitude, Toast.LENGTH_SHORT).show();
            elmensaje.setText("Latitud: " + latitude + "\nLongitud: " + longitude);
            locationUpdateCount++;  // Incrementar el contador

            // Si hemos alcanzado el límite, dejar de solicitar actualizaciones
            if (locationUpdateCount >= MAX_UPDATES) {
                locationManager.removeUpdates(this);
                Toast.makeText(this, "Actualizaciones de ubicación detenidas.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Este método se llama cuando el estado del proveedor cambia
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        // Este método se llama cuando el proveedor está habilitado
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        // Este método se llama cuando el proveedor está deshabilitado
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener las actualizaciones de ubicación cuando la actividad se destruye
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, solicitar actualizaciones de ubicación
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    Toast.makeText(this, "Actualizando ubicación...", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Permiso denegado, mostrar un mensaje
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
