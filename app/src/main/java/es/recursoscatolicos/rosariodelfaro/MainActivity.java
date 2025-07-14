package es.recursoscatolicos.rosariodelfaro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.DayOfWeek;


public class MainActivity extends AppCompatActivity {

    // Variables de la interfaz de usuario
    private ImageView imageMisterio;
    private TextView textNombreMisterio;
    private TextView textOracionActual;
    private TextView textProgresoRosario;
    private TextView textCuentaRosario;
    private Button buttonSiguienteOracion;
    private ImageButton btnPlayPause;

    // Variables de lógica del rosario
    private int misterioActualIndex = 0;
    private int cuentaActual = 0;
    private List<String> oracionesDelRosario; // Aquí almacenarás la secuencia de oraciones
    private List<Misterio> misterios; // Lista de misterios del día

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicialización de Vistas
        imageMisterio = findViewById(R.id.image_misterio);
        textNombreMisterio = findViewById(R.id.text_nombre_misterio);
        textOracionActual = findViewById(R.id.text_oracion_actual);
        textProgresoRosario = findViewById(R.id.text_progreso_rosario);
        textCuentaRosario = findViewById(R.id.text_cuenta_rosario);
        buttonSiguienteOracion = findViewById(R.id.button_siguiente_oracion);
        btnPlayPause = findViewById(R.id.btn_play_pause);

        // 2. Inicialización de datos del rosario
        inicializarMisteriosDelDia(); // <--- Método renombrado
        inicializarOracionesDelRosario();

        // 3. Manejo del botón "Siguiente Oración"
        buttonSiguienteOracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avanzarRosario();
            }
        });

        // 4. Iniciar el rosario en el primer estado
        actualizarInterfazRosario();
    }

    // --- Métodos de Lógica del Rosario ---

    // Método corregido para inicializar los misterios según el día de la semana
    private void inicializarMisteriosDelDia() {

        // Obtiene el día de la semana actual
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        Log.d("Día de la semana actual", diaActual.toString());
        misterios = new ArrayList<>();

        if (diaActual == DayOfWeek.MONDAY || diaActual == DayOfWeek.SATURDAY) {
                // Misterios Gozosos
                misterios.add(new Misterio("Primer Misterio Gozoso: La Anunciación", R.drawable.gozosos1, ""));
                misterios.add(new Misterio("Segundo Misterio Gozoso: La Visitación", R.drawable.gozosos2, ""));
                misterios.add(new Misterio("Tercer Misterio Gozoso: El Nacimiento de Jesús", R.drawable.gozosos3, ""));
                misterios.add(new Misterio("Cuarto Misterio Gozoso: La Presentación de Jesús en el Templo", R.drawable.gozosos4, ""));
                misterios.add(new Misterio("Quinto Misterio Gozoso: El Encuentro de Jesús en el Templo", R.drawable.gozosos5, ""));
                Log.d("Misterios Gozosos inicializados", " para el Lunes y sabado");
        } else if (diaActual == DayOfWeek.TUESDAY || diaActual == DayOfWeek.FRIDAY) {
                // Misterios Dolorosos
                misterios.add(new Misterio("Primer Misterio Doloroso: La Oración en el Huerto", R.drawable.dolorosos1, ""));
                misterios.add(new Misterio("Segundo Misterio Doloroso: La Flagelación", R.drawable.dolorosos2, ""));
                misterios.add(new Misterio("Tercer Misterio Doloroso: La Coronación de Espinas", R.drawable.dolorosos3, ""));
                misterios.add(new Misterio("Cuarto Misterio Doloroso: El Camino al Calvario", R.drawable.dolorosos4, ""));
                misterios.add(new Misterio("Quinto Misterio Doloroso: La Crucifixión y Muerte de Jesús", R.drawable.dolorosos5, ""));
                Log.d("Misterios Dolorosos inicializados", " para el Martes");
        } else if (diaActual == DayOfWeek.WEDNESDAY || diaActual == DayOfWeek.SUNDAY) {
                // Misterios Gloriosos
                misterios.add(new Misterio("Primer Misterio Glorioso: La Resurrección", R.drawable.gloriosos1, ""));
                misterios.add(new Misterio("Segundo Misterio Glorioso: La Ascensión", R.drawable.gloriosos2, ""));
                misterios.add(new Misterio("Tercer Misterio Glorioso: La Venida del Espíritu Santo", R.drawable.gloriosos3, ""));
                misterios.add(new Misterio("Cuarto Misterio Glorioso: La Asunción de María", R.drawable.gloriosos4, ""));
                misterios.add(new Misterio("Quinto Misterio Glorioso: La Coronación de María", R.drawable.gloriosos5, ""));
                Log.d("Misterios Gloriosos", String.valueOf(diaActual));
        } else if (diaActual == DayOfWeek.THURSDAY) {
                // Misterios Luminosos
                misterios.add(new Misterio("Primer Misterio Luminoso: El Bautismo de Jesús en el Jordán", R.drawable.luminosos1, ""));
                misterios.add(new Misterio("Segundo Misterio Luminoso: La Autorevelación de Jesús en las Bodas de Caná", R.drawable.luminosos2, ""));
                misterios.add(new Misterio("Tercer Misterio Luminoso: La Predicación del Reino de Dios", R.drawable.luminosos3, ""));
                misterios.add(new Misterio("Cuarto Misterio Luminoso: La Transfiguración", R.drawable.luminosos4, ""));
                misterios.add(new Misterio("Quinto Misterio Luminoso: La Institución de la Eucaristía", R.drawable.luminosos5, ""));
                Log.d("Misterios Luminosos inicializados", " para el Jueves");

        } else {
            // Si no es un día válido, no se inicializan misterios
            Log.w("Misterios", "Día de la semana no válido para rosario: " + diaActual);
            misterios = new ArrayList<>(); // Limpia la lista si no es un día válido
        }
    }

    private void inicializarOracionesDelRosario() {
        // Define la secuencia de oraciones básicas (esto es una simplificación)
        oracionesDelRosario = Arrays.asList(
                "Señal de la Cruz", "Credo", "Padrenuestro", // Inicio
                "Misterio 1 - Meditación", "Padrenuestro", // Inicio de la década 1
                "Ave María", "Ave María", "Ave María", "Ave María", "Ave María",
                "Ave María", "Ave María", "Ave María", "Ave María", "Ave María",
                "Gloria al Padre", "Oración de Fátima"
                // Y así sucesivamente para las 5 decenas...
        );
    }

    private void avanzarRosario() {
        // Lógica para avanzar a la siguiente oración
        cuentaActual++;

        if (cuentaActual >= oracionesDelRosario.size()) {
            // Lógica para avanzar al siguiente misterio o fin del rosario
            cuentaActual = 0;
            misterioActualIndex = (misterioActualIndex + 1) % misterios.size();
        }

        actualizarInterfazRosario();
    }

    private void actualizarInterfazRosario() {
        // 1. Actualizar el misterio
        Misterio misterioActual = misterios.get(misterioActualIndex);
        textNombreMisterio.setText(misterioActual.getNombre());
        imageMisterio.setImageResource(misterioActual.getImagenResId());

        // 2. Actualizar la oración actual
        String oracion = oracionesDelRosario.get(cuentaActual);
        textOracionActual.setText(oracion);

        // 3. Actualizar el progreso (misterio y cuenta)
        textProgresoRosario.setText("Misterio " + (misterioActualIndex + 1) + "/" + misterios.size());
        textCuentaRosario.setText("Cuenta " + (cuentaActual + 1) + "/" + oracionesDelRosario.size());
    }
}