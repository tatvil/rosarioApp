package es.recursoscatolicos.rosariodelfaro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDate;
    private TextView textViewSantoName;
    private TextView textViewSantoDescription;
    private Button buttonVerMasSanto;
    private TextView textViewNovenasStatus;
    private Button buttonExplorarNovenas;
    private Button buttonRezarRosario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Conecta la actividad con tu layout XML

        // 1. Enlazar elementos de UI
        textViewDate = findViewById(R.id.textView_date);
        textViewSantoName = findViewById(R.id.textView_santo_name);
        textViewSantoDescription = findViewById(R.id.textView_santo_description);
        buttonVerMasSanto = findViewById(R.id.button_ver_mas_santo);
        textViewNovenasStatus = findViewById(R.id.textView_novenas_status);
        buttonExplorarNovenas = findViewById(R.id.button_explorar_novenas);
        buttonRezarRosario = findViewById(R.id.button_rezar_rosario);

        // 2. Mostrar la fecha actual
        displayCurrentDate();

        // 3. Cargar y mostrar el Santo del Día
        loadSantoDelDia();

        // 4. Actualizar el estado de las Novenas
        updateNovenasStatus();

        // 5. Configurar los Click Listeners para los botones
        setupButtonListeners();
    }

    /**
     * Muestra la fecha actual en el TextView correspondiente.
     */
    private void displayCurrentDate() {
        // Obtener la fecha actual del sistema
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String formattedDate = dateFormat.format(calendar.getTime());
        textViewDate.setText(formattedDate);
    }

    /**
     * Carga y muestra la información del Santo del Día.
     * En una aplicación real, esta información vendría de una base de datos local (SQLite)
     * o un servicio que gestiones.
     */
    private void loadSantoDelDia() {
        // --- Datos de ejemplo para el Santo del Día ---
        // En tu implementación real:
        // 1. Consulta tu base de datos local para el santo correspondiente a la fecha actual.
        // 2. Si no tienes un santo específico para la fecha (ej. es una festividad general),
        //    puedes tener un default o dejar el campo vacío.

        String santoName = "Nuestra Señora del Carmen"; // Este texto debería venir de tu lógica de BD
        String santoDescription = "Protectora de los marineros y Patrona de muchas ciudades."; // Este también

        textViewSantoName.setText(santoName);
        textViewSantoDescription.setText(santoDescription);
    }

    /**
     * Actualiza el estado de las Novenas.
     * De forma similar al Santo del Día, esta lógica sería más compleja en producción,
     * gestionando novenas activas, próximas o finalizadas.
     */
    private void updateNovenasStatus() {
        // --- Lógica de ejemplo para las Novenas ---
        // Aquí puedes tener una lógica para verificar si alguna novena que el usuario está siguiendo
        // termina hoy, o si alguna importante empieza pronto.
        boolean novenaCarmenFinishesToday = true; // Simula una condición
        boolean noNovenasActivas = false; // Otra simulación

        if (novenaCarmenFinishesToday) {
            textViewNovenasStatus.setText("¡Hoy finaliza la Novena a la Virgen del Carmen!");
        } else if (noNovenasActivas) {
            textViewNovenasStatus.setText("No hay novenas en curso. ¡Explora nuevas!");
        } else {
            // Ejemplo para una novena en curso
            // textViewNovenasStatus.setText("Día 5 de la Novena a San José");
            textViewNovenasStatus.setText("Explora las novenas disponibles para comenzar una.");
        }
    }

    /**
     * Configura los listeners para los botones de la interfaz.
     */
    private void setupButtonListeners() {
        buttonVerMasSanto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iniciarías una nueva Activity para el detalle del Santo
                // Por ejemplo:
                // Intent intent = new Intent(MainActivity.this, SantoDetailActivity.class);
                // intent.putExtra("santo_id", "ID_DEL_SANTO"); // Pasar un ID para cargar el santo específico
                // startActivity(intent);
                // Por ahora, solo un mensaje Toast
                // Toast.makeText(MainActivity.this, "Abriendo detalles del Santo...", Toast.LENGTH_SHORT).show();
            }
        });

        buttonExplorarNovenas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iniciarías una nueva Activity para el módulo de Novenas
                // Por ejemplo:
                // Intent intent = new Intent(MainActivity.this, NovenasActivity.class);
                // startActivity(intent);
                // Toast.makeText(MainActivity.this, "Abriendo módulo de Novenas...", Toast.LENGTH_SHORT).show();
            }
        });

        buttonRezarRosario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RosarioActivity.class);
                startActivity(intent);
            }
        });
    }
}