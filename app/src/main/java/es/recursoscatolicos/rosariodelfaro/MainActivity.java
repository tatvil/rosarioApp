package es.recursoscatolicos.rosariodelfaro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout; // Importar LinearLayout
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
    // private TextView textProgresoRosario; // Para "Misterio X/5"
    // private TextView textCuentaRosario; // Ya no lo usaremos para las cuentas, sino el LinearLayout
    private LinearLayout layoutCuentasAveMaria; // Nuevo LinearLayout para las cuentas visuales
    private Button buttonSiguienteOracion;
    private ImageButton btnPlayPause;

    // --- Variables de lógica del rosario ---
    private List<Misterio> misterios; // Lista de misterios del día (5 misterios)

    // Variables de estado para el progreso del rosario
    // etapaRosario: 0=Intro, 1-5=Misterio (decena), 6=Conclusión
    private int etapaRosario = 0;
    // oracionEnEtapaIndex: Índice de la oración dentro de la etapa actual
    private int oracionEnEtapaIndex = 0;

    // --- Secuencias de Oraciones Estáticas ---
    private static final List<String> INTRO_ORACIONES = Arrays.asList(
            "En el nombre del Padre, \n del Hijo \n y del Espíritu Santo. \n Amén.",
            "Padrenuestro \n (por las intenciones del Papa)",
            "Ave María (1/3)", "Ave María (2/3)", "Ave María (3/3)", // 3 Ave Marías
            "Gloria al Padre, al hijo y al Espíritu Santo \n como era en el principio, ahora y siempre, por los siglos de los siglos. \n Amén."
    );

    private static final List<String> DECADA_ORACIONES_TEMPLATE = Arrays.asList(
            "Padrenuestro, que estas en el cielo, santificado sea tu nombre. \n Venga tu reino. \n Hágase tu voluntad, así en la tierra como en el cielo.",
            "Danos hoy el pan nuestro de cada día. \n Perdona nuestras ofensas, así como nosotros perdonamos a los que nos ofenden. \n No nos dejes caer en la tentación y líbranos de mal.",
            "Ave María", "Ave María", "Ave María", "Ave María", "Ave María",
            "Ave María", "Ave María", "Ave María", "Ave María", "Ave María", // 10 Ave Marías
            "Gloria al Padre, al hijo y al Espíritu Santo \n como era en el principio, ahora y siempre, por los siglos de los siglos. \n Amén.",
            "Nuestra señora del Rosario, \n ruega por nosotros.",
            "Santo Rosario, \n ruega por nosotros."
    );

    private static final int NUM_AVEMARIAS_DECADA = 10;
    // El índice donde empieza el primer "Ave María" en DECADA_ORACIONES_TEMPLATE
    // "Padrenuestro" es 0, así que "Ave María" empieza en 1.
    private static final int INDEX_PRIMER_AVEMARIA_TEMPLATE = 1;


    private static final List<String> CONCLUSION_ORACIONES = Arrays.asList(
            "Salve Regina",
            "Oración final: Ruega por nosotros Santa Madre de Dios, \n para que seamos dignos de alcanzar las promesas de nuestro Señor Jesucristo."
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicialización de Vistas
        imageMisterio = findViewById(R.id.image_misterio);
        textNombreMisterio = findViewById(R.id.text_nombre_misterio);
        textOracionActual = findViewById(R.id.text_oracion_actual);
      //  textProgresoRosario = findViewById(R.id.text_progreso_rosario);
        layoutCuentasAveMaria = findViewById(R.id.layout_cuentas_avemaria); // ¡Inicializar el nuevo LinearLayout!
        buttonSiguienteOracion = findViewById(R.id.button_siguiente_oracion);
        // btnPlayPause = findViewById(R.id.btn_play_pause); // Descomentar si tienes este botón

        // 2. Inicialización de datos del rosario
        inicializarMisteriosDelDia();

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

    private void inicializarMisteriosDelDia() {
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        misterios = new ArrayList<>();

        // Meditaciones Gozosos (Lunes y Sábado)
        String mGozosos1 = "Lucas 1,26-38: El ángel Gabriel fue enviado a María para anunciarle que concebiría a Jesús por obra del Espíritu Santo. María aceptó: «He aquí la esclava del Señor; hágase en mí según tu palabra.»";
        String mGozosos2 = "Lucas 1,39-56: María visitó a su prima Isabel, quien, llena del Espíritu Santo, la reconoció como la Madre de su Señor. María proclamó el Magníficat.";
        String mGozosos3 = "Lucas 2,1-20: Jesús nació en Belén en un pesebre. Ángeles anunciaron a los pastores: «Hoy os ha nacido un Salvador, que es el Mesías, el Señor.»";
        String mGozosos4 = "Lucas 2,22-40: María y José presentaron a Jesús en el Templo. Simeón profetizó sobre el Niño y María: «Una espada te traspasará el alma.»";
        String mGozosos5 = "Lucas 2,41-52: Jesús, de doce años, se quedó en el Templo, conversando con los maestros. María y José lo encontraron, y Él les dijo: «¿No sabíais que debía estar en la casa de mi Padre?»";

// Meditaciones Dolorosos (Martes y Viernes)
        String mDolorosos1 = "Mateo 26,36-46: Jesús oró en Getsemaní, angustiado hasta la muerte, y aceptó la voluntad del Padre: «No se haga mi voluntad, sino la tuya.»";
        String mDolorosos2 = "Juan 19,1-3: Pilato hizo flagelar a Jesús. Los soldados lo azotaron cruelmente y se burlaron de Él.";
        String mDolorosos3 = "Marcos 15,16-20: Los soldados vistieron a Jesús de púrpura, le tejieron una corona de espinas y se la pusieron, burlándose de Él.";
        String mDolorosos4 = "Juan 19,17: Jesús cargó su propia cruz y salió hacia el lugar llamado la Calavera, en su camino al Calvario.";
        String mDolorosos5 = "Juan 19,25-30: Jesús fue crucificado. Desde la cruz, entregó a su madre a Juan. Después de decir «Todo está cumplido», inclinó la cabeza y entregó el espíritu.";

// Meditaciones Gloriosos (Miércoles y Domingo)
        String mGloriosos1 = "Juan 20,1-10: Jesús resucitó al tercer día, venciendo a la muerte. Los discípulos encontraron el sepulcro vacío y creyeron.";
        String mGloriosos2 = "Hechos 1,6-11: Jesús ascendió al cielo ante sus discípulos. Dos ángeles anunciaron: «Este Jesús volverá del mismo modo que lo habéis visto ir al cielo.»";
        String mGloriosos3 = "Hechos 2,1-13: El Espíritu Santo descendió sobre los apóstoles y María en Pentecostés, capacitándolos para proclamar el Evangelio en todas las lenguas.";
        String mGloriosos4 = "Apocalipsis 12,14: María fue llevada al cielo en cuerpo y alma. Es figura de la Iglesia que un día también participará plenamente de la gloria de Cristo.";
        String mGloriosos5 = "Apocalipsis 12,1: María, asunta al cielo, fue coronada como Reina del universo, con una corona de doce estrellas, el sol y la luna bajo sus pies.";

// Meditaciones Luminosos (Jueves)
        String mLuminosos1 = "Mateo 3,13-17: Jesús fue bautizado por Juan en el Jordán. Se abrió el cielo y el Espíritu Santo descendió sobre Él, y se oyó la voz del Padre: «Este es mi Hijo amado.»";
        String mLuminosos2 = "Juan 2,1-11: En las bodas de Caná, Jesús convirtió el agua en vino a petición de María, manifestando su gloria y creyendo en Él sus discípulos.";
        String mLuminosos3 = "Marcos 1,14-15: Jesús proclamó el Reino de Dios: «Convertíos y creed en el Evangelio.» Invitó a la penitencia y al seguimiento de su Palabra.";
        String mLuminosos4 = "Lucas 9,28-36: Jesús se transfiguró en el monte, revelando su gloria a Pedro, Santiago y Juan. Moisés y Elías aparecieron, y el Padre dijo: «Este es mi Hijo, el Elegido; escuchadle.»";
        String mLuminosos5 = "Lucas 22,19-20: Jesús instituyó la Eucaristía en la Última Cena, dando su Cuerpo y Sangre como alimento y memorial de su sacrificio por nosotros.";

        if (diaActual == DayOfWeek.MONDAY || diaActual == DayOfWeek.SATURDAY) {
            misterios.add(new Misterio("1. La Anunciación", R.drawable.gozosos1, mGozosos1));
            misterios.add(new Misterio("2. La Visitación", R.drawable.gozosos2, mGozosos2));
            misterios.add(new Misterio("3. El Nacimiento de Jesús", R.drawable.gozosos3, mGozosos3));
            misterios.add(new Misterio("4. La Presentación de Jesús en el Templo", R.drawable.gozosos4, mGozosos4));
            misterios.add(new Misterio("5. El Niño Jesús en el Templo", R.drawable.gozosos5, mGozosos5));
        } else if (diaActual == DayOfWeek.TUESDAY || diaActual == DayOfWeek.FRIDAY) {
            misterios.add(new Misterio("1. La Oración en el Huerto", R.drawable.dolorosos1, mDolorosos1));
            misterios.add(new Misterio("2. La Flagelación", R.drawable.dolorosos2, mDolorosos2));
            misterios.add(new Misterio("3. La Coronación de Espinas", R.drawable.dolorosos3, mDolorosos3));
            misterios.add(new Misterio("4. El Camino al Calvario", R.drawable.dolorosos4, mDolorosos4));
            misterios.add(new Misterio("5. La Crucifixión y Muerte de Jesús", R.drawable.dolorosos5, mDolorosos5));
        } else if (diaActual == DayOfWeek.WEDNESDAY || diaActual == DayOfWeek.SUNDAY) {
            misterios.add(new Misterio("1. La Resurrección", R.drawable.gloriosos1, mGloriosos1));
            misterios.add(new Misterio("2. La Ascensión", R.drawable.gloriosos2, mGloriosos2));
            misterios.add(new Misterio("3. La Venida del Espíritu Santo", R.drawable.gloriosos3, mGloriosos3));
            misterios.add(new Misterio("4. La Asunción de María", R.drawable.gloriosos4, mGloriosos4));
            misterios.add(new Misterio("5. La Coronación de María", R.drawable.gloriosos5, mGloriosos5));
        } else if (diaActual == DayOfWeek.THURSDAY) {
            misterios.add(new Misterio("1. El Bautismo en el Jordán", R.drawable.luminosos1, mLuminosos1));
            misterios.add(new Misterio("2. Las Bodas de Caná", R.drawable.luminosos2, mLuminosos2));
            misterios.add(new Misterio("3. La Proclamación del Reino", R.drawable.luminosos3, mLuminosos3));
            misterios.add(new Misterio("4. La Transfiguración", R.drawable.luminosos4, mLuminosos4));
            misterios.add(new Misterio("5. La Institución de la Eucaristía", R.drawable.luminosos5, mLuminosos5));
        } else {
            Log.w("Misterios", "Día de la semana no válido para el Rosario: " + diaActual);
            misterios = new ArrayList<>();
        }
    }

    private void avanzarRosario() {
        oracionEnEtapaIndex++;

        List<String> currentStageOraciones;
        int totalOracionesInStage;

        if (etapaRosario == 0) {
            currentStageOraciones = INTRO_ORACIONES;
            totalOracionesInStage = currentStageOraciones.size();
        } else if (etapaRosario >= 1 && etapaRosario <= 5) {
            currentStageOraciones = DECADA_ORACIONES_TEMPLATE;
            totalOracionesInStage = 1 + currentStageOraciones.size(); // +1 por la meditación
        } else if (etapaRosario == 6) {
            currentStageOraciones = CONCLUSION_ORACIONES;
            totalOracionesInStage = currentStageOraciones.size();
        } else {
            Log.e("Rosario", "Etapa del Rosario inválida: " + etapaRosario);
            return;
        }

        if (oracionEnEtapaIndex >= totalOracionesInStage) {
            etapaRosario++;
            oracionEnEtapaIndex = 0;

            if (etapaRosario > 6) {
                Log.d("Rosario", "¡Rosario completado!");
                textOracionActual.setText("¡Rosario completado! Gracias por rezar con nosotros.");
                buttonSiguienteOracion.setEnabled(false);
                if (btnPlayPause != null) {
                    btnPlayPause.setEnabled(false);
                }
                actualizarInterfazProgreso(true); // Pasar true para indicar que el rosario ha finalizado
                layoutCuentasAveMaria.setVisibility(View.GONE); // Ocultar las cuentas al finalizar
                return;
            }
        }

        actualizarInterfazRosario();
    }

    private void actualizarInterfazRosario() {
        // 1. Actualizar el Título de Misterios, Imagen y Nombre del Misterio
        if (etapaRosario >= 1 && etapaRosario <= 5) {
            int currentMisterioListIndex = etapaRosario - 1;
            if (currentMisterioListIndex < misterios.size()) {
                Misterio misterioActual = misterios.get(currentMisterioListIndex);
                textNombreMisterio.setText(misterioActual.getNombre());
                imageMisterio.setImageResource(misterioActual.getImagenResId());

                String tipoMisterio = "Misterios del Día";
                DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
                if (diaActual == DayOfWeek.MONDAY || diaActual == DayOfWeek.SATURDAY) {
                    tipoMisterio = "Misterios Gozosos";
                } else if (diaActual == DayOfWeek.TUESDAY || diaActual == DayOfWeek.FRIDAY) {
                    tipoMisterio = "Misterios Dolorosos";
                } else if (diaActual == DayOfWeek.WEDNESDAY || diaActual == DayOfWeek.SUNDAY) {
                    tipoMisterio = "Misterios Gloriosos";
                } else if (diaActual == DayOfWeek.THURSDAY) {
                    tipoMisterio = "Misterios Luminosos";
                }
                TextView textMisteriosTitulo = findViewById(R.id.text_misterios);
                if (textMisteriosTitulo != null) {
                    textMisteriosTitulo.setText(tipoMisterio);
                }
            } else {
                Log.e("Rosario", "Índice de misterio fuera de rango: " + currentMisterioListIndex);
                textNombreMisterio.setText("Error en Misterio");
                imageMisterio.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            }
        } else {
            imageMisterio.setImageResource(R.drawable.rosario_general);
            textNombreMisterio.setText("");
            TextView textMisteriosTitulo = findViewById(R.id.text_misterios);
            if (textMisteriosTitulo != null) {
                if (etapaRosario == 0) {
                    textMisteriosTitulo.setText("Inicio del Rosario");
                } else {
                    textMisteriosTitulo.setText("Fin del Rosario");
                }
            }
        }

        // 2. Actualizar la Oración Actual
        String oracionDisplay = "";

        if (etapaRosario == 0) {
            oracionDisplay = INTRO_ORACIONES.get(oracionEnEtapaIndex);
            layoutCuentasAveMaria.setVisibility(View.GONE); // Ocultar cuentas en la intro
        } else if (etapaRosario >= 1 && etapaRosario <= 5) {
            if (oracionEnEtapaIndex == 0) { // Meditación del misterio
                Misterio misterioActual = misterios.get(etapaRosario - 1);
                oracionDisplay = misterioActual.getMeditation();
                layoutCuentasAveMaria.setVisibility(View.GONE); // Ocultar cuentas en la meditación
            } else {
                String oracionBase = DECADA_ORACIONES_TEMPLATE.get(oracionEnEtapaIndex - 1);
                oracionDisplay = oracionBase; // Muestra solo "Ave María"

                // Si es una Ave María, mostrar las cuentas visuales
                if (oracionBase.equals("Ave María")) {
                    layoutCuentasAveMaria.setVisibility(View.VISIBLE);
                    actualizarCuentasAveMaria(oracionEnEtapaIndex - INDEX_PRIMER_AVEMARIA_TEMPLATE); // Calcula cuántas Ave María se han rezado
                } else {
                    layoutCuentasAveMaria.setVisibility(View.GONE); // Ocultar para otras oraciones de la década
                }
            }
        } else if (etapaRosario == 6) {
            oracionDisplay = CONCLUSION_ORACIONES.get(oracionEnEtapaIndex);
            layoutCuentasAveMaria.setVisibility(View.GONE); // Ocultar cuentas en la conclusión
        }

        textOracionActual.setText(oracionDisplay);
        actualizarInterfazProgreso(false); // Actualizar progreso general y de cuentas
    }

    // Método para crear y actualizar dinámicamente las cuentas del rosario
    private void actualizarCuentasAveMaria(int aveMariasRezadaCount) {
        layoutCuentasAveMaria.removeAllViews(); // Limpiar vistas anteriores

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                dpToPx(16), // Ancho de la cuenta en dp
                dpToPx(16)  // Alto de la cuenta en dp
        );
        params.setMargins(dpToPx(2), 0, dpToPx(2), 0); // Márgenes entre cuentas

        for (int i = 0; i < NUM_AVEMARIAS_DECADA; i++) {
            ImageView bead = new ImageView(this);
            bead.setLayoutParams(params);
            if (i < aveMariasRezadaCount) {
                bead.setImageResource(R.drawable.bead_filled); // Cuenta llena
            } else {
                bead.setImageResource(R.drawable.bead_empty); // Cuenta vacía
            }
            layoutCuentasAveMaria.addView(bead);
        }
    }

    // Método auxiliar para convertir dp a px
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    // Método para manejar la actualización de los TextViews de progreso
    private void actualizarInterfazProgreso(boolean rosarioFinalizado) {
        if (rosarioFinalizado) {
            //textProgresoRosario.setText("Rosario Finalizado");
            // textCuentaRosario.setText(""); // Si aún lo usas, puedes limpiar aquí
            return;
        }

        String progresoRosarioText = "";
        // String cuentaRosarioText = ""; // Ya no se usa directamente para el contador de Ave María
        int totalOracionesEnEtapa;

        if (etapaRosario == 0) { // Introducción
            progresoRosarioText = "Introducción";
            totalOracionesEnEtapa = INTRO_ORACIONES.size();
            // cuentaRosarioText = "Oración " + (oracionEnEtapaIndex + 1) + "/" + totalOracionesEnEtapa;
        } else if (etapaRosario >= 1 && etapaRosario <= 5) { // Misterios
            progresoRosarioText = "Misterio " + etapaRosario + "/5";
            totalOracionesEnEtapa = 1 + DECADA_ORACIONES_TEMPLATE.size(); // +1 por la meditación

            if (oracionEnEtapaIndex == 0) { // Meditación
                // cuentaRosarioText = "Meditación (1/" + totalOracionesEnEtapa + ")";
            } else if (DECADA_ORACIONES_TEMPLATE.get(oracionEnEtapaIndex - 1).equals("Ave María")) {
                // Aquí, el progreso de las Ave Marías lo maneja layoutCuentasAveMaria
                // cuentaRosarioText = ""; // O dejarlo vacío si textCuentaRosario no se usa
            } else {
                // cuentaRosarioText = "Paso " + (oracionEnEtapaIndex + 1) + "/" + totalOracionesEnEtapa;
            }
        } else if (etapaRosario == 6) { // Conclusión
            progresoRosarioText = "Conclusión";
            totalOracionesEnEtapa = CONCLUSION_ORACIONES.size();
            // cuentaRosarioText = "Oración " + (oracionEnEtapaIndex + 1) + "/" + totalOracionesEnEtapa;
        }

        //textProgresoRosario.setText(progresoRosarioText);
        // Si tienes textCuentaRosario y quieres usarlo para algo más o limpiarlo, hazlo aquí:
        // if (textCuentaRosario != null) {
        //     textCuentaRosario.setText(cuentaRosarioText);
        // }
    }
}