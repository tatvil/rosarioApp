package es.recursoscatolicos.rosariodelfaro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// Importaciones de las nuevas clases
import es.recursoscatolicos.rosariodelfaro.audio.RosarioAudioManager;
import es.recursoscatolicos.rosariodelfaro.data.RosarioDataProvider;
import es.recursoscatolicos.rosariodelfaro.logic.RosarioLogicManager;
import es.recursoscatolicos.rosariodelfaro.model.Oracion;

public class RosarioActivity extends AppCompatActivity {

    // UI elements
    private ImageView imageMisterio;
    private TextView textNombreMisterio;
    private TextView textOracionActual;
    private LinearLayout layoutCuentasAveMaria;
    private Button buttonSiguienteOracion;
    private ImageButton btnPlayPause;
    private TextView textMisteriosTitulo; // Añade este TextView si existe en tu XML

    // Managers
    private RosarioLogicManager rosarioLogicManager;
    private RosarioAudioManager rosarioAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rosario);

        // 1. Initialize UI elements
        imageMisterio = findViewById(R.id.image_misterio);
        textNombreMisterio = findViewById(R.id.text_nombre_misterio);
        textOracionActual = findViewById(R.id.text_oracion_actual);
        layoutCuentasAveMaria = findViewById(R.id.layout_cuentas_avemaria);
        buttonSiguienteOracion = findViewById(R.id.button_siguiente_oracion);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        textMisteriosTitulo = findViewById(R.id.text_misterios);

        // 2. Initialize Managers
        RosarioDataProvider dataProvider = new RosarioDataProvider(this);
        rosarioLogicManager = new RosarioLogicManager(dataProvider);
        rosarioAudioManager = new RosarioAudioManager(this);

        // Set listener for audio completion (optional, for auto-advance)
        rosarioAudioManager.setAudioCompletionListener(() -> {
            // If you want the rosary to advance automatically after each audio:
            // avanzarRosario();
            // Or simply reset the play/pause button state
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
        });

        // 3. Set up button click listeners
        buttonSiguienteOracion.setOnClickListener(v -> {
            // Detener el audio al avanzar manualmente
            rosarioAudioManager.stopAudio();
            avanzarRosario();
        });

        btnPlayPause.setOnClickListener(v -> togglePlayPause());

        // 4. Start the rosary display
        avanzarRosario(); // Call it once to display the first prayer
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (rosarioAudioManager != null) {
            rosarioAudioManager.release();
        }
    }

    /**
     * Advances the rosary to the next prayer and updates the UI.
     */
    private void avanzarRosario() {
        Oracion nextOracion = rosarioLogicManager.getNextOracion();

        if (nextOracion != null) {
            textOracionActual.setText(nextOracion.getTexto());

            // Update UI based on the current stage and prayer type
            updateRosaryUI();

            // Play the audio for the current prayer
            rosarioAudioManager.playAudio(nextOracion.getAudioResId());
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause); // Update icon to pause
        } else {
            // Rosario completed
            Log.d("Rosario", "¡Rosario completado!");
            textOracionActual.setText(R.string.rosario_completado);
            buttonSiguienteOracion.setEnabled(false);
            btnPlayPause.setEnabled(false);
            rosarioAudioManager.stopAudio(); // Ensure audio is stopped
            layoutCuentasAveMaria.setVisibility(View.GONE);
            updateRosaryUI(); // Update final state
        }
    }

    /**
     * Toggles play/pause state for the current audio.
     */
    private void togglePlayPause() {
        if (rosarioAudioManager.isPlaying()) {
            rosarioAudioManager.pauseAudio();
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
        } else {
            // Obtener la oración actual del LogicManager
            Oracion currentOracion = rosarioLogicManager.getCurrentOracion();

            if (currentOracion != null) {
                rosarioAudioManager.playAudio(currentOracion.getAudioResId());
                btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            } else {
                Log.w("RosarioActivity", "No hay una oración actual para reproducir.");
            }
        }
    }


    /**
     * Updates the UI elements based on the current state of the rosary.
     * This method combines the logic previously in actualizarInterfazRosario and actualizarInterfazProgreso.
     */
    private void updateRosaryUI() {
        int etapa = rosarioLogicManager.getEtapaRosario();

        // Update Misterio Image and Name
        if (etapa >= 1 && etapa <= 5) {
            textNombreMisterio.setText(rosarioLogicManager.getNombreMisterioActual());
            imageMisterio.setImageResource(rosarioLogicManager.getImagenMisterioActualResId());
            textMisteriosTitulo.setText(rosarioLogicManager.getTipoMisterioDelDia());
        } else {
            imageMisterio.setImageResource(R.drawable.rosario_general); // Default image
            textNombreMisterio.setText("");
            if (etapa == 0) {
                textMisteriosTitulo.setText(R.string.inicio_del_rosario);
            } else {
                textMisteriosTitulo.setText(R.string.fin_del_rosario);
            }
        }

        // Update Ave Maria beads
        Oracion currentOracion = rosarioLogicManager.getCurrentOracion(); // Assuming this method exists
        if (currentOracion != null && currentOracion.esAveMaria()) {
            layoutCuentasAveMaria.setVisibility(View.VISIBLE);
            actualizarCuentasAveMaria(rosarioLogicManager.getAveMariasRezadaCount());
        } else {
            layoutCuentasAveMaria.setVisibility(View.GONE);
        }
        // Update progress text if you decide to re-introduce it
        // textProgresoRosario.setText(rosarioLogicManager.getProgresoRosarioText());
    }


    // Método para crear y actualizar dinámicamente las cuentas del rosario
    private void actualizarCuentasAveMaria(int aveMariasRezadaCount) {
        layoutCuentasAveMaria.removeAllViews(); // Limpiar vistas anteriores

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                dpToPx(16), // Ancho de la cuenta en dp
                dpToPx(16)  // Alto de la cuenta en dp
        );
        params.setMargins(dpToPx(2), 0, dpToPx(2), 0); // Márgenes entre cuentas

        for (int i = 0; i < RosarioLogicManager.NUM_AVEMARIAS_DECADA; i++) { // Usa la constante del Manager
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
}