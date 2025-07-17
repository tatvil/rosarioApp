// Nuevo archivo: app/src/main/java/es/recursoscatolicos/rosariodelfaro/logic/RosarioLogicManager.java
package es.recursoscatolicos.rosariodelfaro.logic;

import java.util.List;

import es.recursoscatolicos.rosariodelfaro.data.RosarioDataProvider;
import es.recursoscatolicos.rosariodelfaro.model.Misterio;
import es.recursoscatolicos.rosariodelfaro.model.Oracion;

public class RosarioLogicManager {

    private RosarioDataProvider dataProvider;
    private List<Misterio> misteriosDelDia;

    // Variables de estado para el progreso del rosario
    // etapaRosario: 0=Intro, 1-5=Misterio (decena), 6=Conclusión
    private int etapaRosario = 0;
    // oracionEnEtapaIndex: Índice de la oración dentro de la etapa actual
    private int oracionEnEtapaIndex = 0;

    public static final int NUM_AVEMARIAS_DECADA = 10;
    public static final int INDEX_PRIMER_AVEMARIA_TEMPLATE = 2; // Índice del primer Ave María en DECADA_BASICA_TEMPLATE

    public RosarioLogicManager(RosarioDataProvider dataProvider) {
        this.dataProvider = dataProvider;
        this.misteriosDelDia = dataProvider.getMisteriosDelDia();
    }

    /**
     * Resetea el progreso del rosario al inicio.
     */
    public void resetRosario() {
        etapaRosario = 0;
        oracionEnEtapaIndex = 0;
        // Si los misterios del día pueden cambiar (ej. si el usuario cambia el día),
        // podrías volver a llamar a dataProvider.getMisteriosDelDia() aquí.
    }

    /**
     * Avanza a la siguiente oración del Rosario.
     * @return El objeto Oracion actual, o null si el rosario ha terminado.
     */
    public Oracion getNextOracion() {
        Oracion currentOracion = null;

        List<Oracion> currentStageOraciones;
        int totalOracionesInStage;

        if (etapaRosario == 0) { // Introducción
            currentStageOraciones = dataProvider.getIntroOraciones();
            totalOracionesInStage = currentStageOraciones.size();
            currentOracion = currentStageOraciones.get(oracionEnEtapaIndex);
        } else if (etapaRosario >= 1 && etapaRosario <= 5) { // Décadas
            if (oracionEnEtapaIndex == 0) { // Meditación del misterio
                Misterio misterioActual = misteriosDelDia.get(etapaRosario - 1);
                currentOracion = misterioActual.getMeditationOracion(); // Asume que Misterio ahora devuelve un objeto Oracion para la meditación
            } else {
                currentStageOraciones = dataProvider.getDecadaTemplateOraciones();
                // oracionEnEtapaIndex - 1 porque el índice 0 de la etapa es la meditación
                // y el resto de DECADA_BASICA_TEMPLATE empieza en el índice 0 del template
                currentOracion = currentStageOraciones.get(oracionEnEtapaIndex - 1);
            }
            totalOracionesInStage = 1 + dataProvider.getDecadaTemplateOraciones().size(); // +1 por la meditación

        } else if (etapaRosario == 6) { // Conclusión
            currentStageOraciones = dataProvider.getConclusionOraciones();
            totalOracionesInStage = currentStageOraciones.size();
            currentOracion = currentStageOraciones.get(oracionEnEtapaIndex);
        } else {
            // Rosario completado o etapa inválida
            return null; // Indica que el rosario ha terminado
        }

        oracionEnEtapaIndex++;

        // Verificar si la etapa actual ha terminado
        if (oracionEnEtapaIndex >= totalOracionesInStage) {
            etapaRosario++;
            oracionEnEtapaIndex = 0; // Reiniciar el índice para la próxima etapa
        }

        return currentOracion;
    }

    // Métodos getter para el estado actual
    public int getEtapaRosario() {
        return etapaRosario;
    }

    public int getOracionEnEtapaIndex() {
        return oracionEnEtapaIndex;
    }

    public List<Misterio> getMisteriosDelDia() {
        return misteriosDelDia;
    }

    public boolean isRosarioCompleted() {
        return etapaRosario > 6;
    }

    public String getNombreMisterioActual() {
        if (etapaRosario >= 1 && etapaRosario <= 5) {
            return misteriosDelDia.get(etapaRosario - 1).getNombre();
        }
        return ""; // O algún valor predeterminado
    }

    public int getImagenMisterioActualResId() {
        if (etapaRosario >= 1 && etapaRosario <= 5) {
            return misteriosDelDia.get(etapaRosario - 1).getImagenResId();
        }
        return R.drawable.rosario_general; // Imagen por defecto
    }

    public int getAveMariasRezadaCount() {
        if (etapaRosario >= 1 && etapaRosario <= 5) {
            // Contar Ave Marías solo si la oración actual es un Ave María
            Oracion currentOration = dataProvider.getDecadaTemplateOraciones().get(oracionEnEtapaIndex - 1);
            if (currentOration.esAveMaria()) {
                // El oracionEnEtapaIndex se incrementa al principio de getNextOracion
                // Entonces, si oracionEnEtapaIndex es 3, significa que la oracion en índice 2 es la actual
                // Si el primer Ave María está en el índice 2 de la plantilla, entonces:
                // oracionEnEtapaIndex - INDEX_PRIMER_AVEMARIA_TEMPLATE
                // Ejemplo: oracionEnEtapaIndex=3 (3a oracion de la década), INDEX_PRIMER_AVEMARIA_TEMPLATE=2
                // Resultado: 3 - 2 = 1. Sería la primera Ave María.
                return (oracionEnEtapaIndex - INDEX_PRIMER_AVEMARIA_TEMPLATE);
            }
        }
        return 0; // No estamos en una Ave María
    }


    public String getTipoMisterioDelDia() {
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        if (diaActual == DayOfWeek.MONDAY || diaActual == DayOfWeek.SATURDAY) {
            return "Misterios Gozosos";
        } else if (diaActual == DayOfWeek.TUESDAY || diaActual == DayOfWeek.FRIDAY) {
            return "Misterios Dolorosos";
        } else if (diaActual == DayOfWeek.WEDNESDAY || diaActual == DayOfWeek.SUNDAY) {
            return "Misterios Gloriosos";
        } else if (diaActual == DayOfWeek.THURSDAY) {
            return "Misterios Luminosos";
        }
        return "Misterios del Día";
    }
}
