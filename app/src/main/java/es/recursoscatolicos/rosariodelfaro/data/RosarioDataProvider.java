package es.recursoscatolicos.rosariodelfaro.data;

import android.content.Context;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.recursoscatolicos.rosariodelfaro.R;
import es.recursoscatolicos.rosariodelfaro.model.Misterio;
import es.recursoscatolicos.rosariodelfaro.model.Oracion;

public class RosarioDataProvider {

    private Context context;

    public RosarioDataProvider(Context context) {
        this.context = context;
    }

    private List<Oracion> getIntroOracionesInternal() {
        return Arrays.asList(
                new Oracion(context.getString(R.string.intro_en_el_nombre), R.raw.enelnombredelpadre, false, "INTRO_EN_EL_NOMBRE"),
                new Oracion(context.getString(R.string.intro_padrenuestro_intencion_papa), R.raw.padrenuestro, false, "INTRO_PADRE"),
                new Oracion(context.getString(R.string.intro_ave_maria_1), R.raw.avemaria, true, "INTRO_AVEMARIA"),
                new Oracion(context.getString(R.string.intro_ave_maria_2), R.raw.avemaria, true, "INTRO_AVEMARIA"),
                new Oracion(context.getString(R.string.intro_ave_maria_3), R.raw.avemaria, true, "INTRO_AVEMARIA"),
                new Oracion(context.getString(R.string.intro_gloria), R.raw.gloria, false, "INTRO_GLORIA")
        );
    }

    private List<Oracion> getDecadaTemplateOracionesInternal() {
        return Arrays.asList(
                new Oracion(context.getString(R.string.decada_padrenuestro_parte1), R.raw.padrenuestro, false, "DECADA_PADRE"),
                new Oracion(context.getString(R.string.decada_padrenuestro_parte2), R.raw.padrenuestro_continua, false, "DECADA_PADRE_CONT"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_ave_maria), R.raw.avemaria, true, "AVEMARIA"),
                new Oracion(context.getString(R.string.decada_gloria), R.raw.gloria, false, "DECADA_GLORIA"),
                new Oracion(context.getString(R.string.decada_jaculatoria_ns_rosario), R.raw.nuestrasenoradelrosario, false, "JACULATORIA_NS_ROSARIO")
        );
    }

    private List<Oracion> getConclusionOracionesInternal() {
        return Arrays.asList(
                new Oracion(context.getString(R.string.conclusion_salve_regina), R.raw.salveregina, false, "CONCLUSION_SALVE"),
                new Oracion(context.getString(R.string.conclusion_oracion_final), R.raw.oracionfinal, false, "CONCLUSION_FINAL")
        );
    }

    private List<Misterio> getGozososInternal() {
        return Arrays.asList(
                new Misterio("1. La Anunciación", R.drawable.gozosos1, new Oracion(context.getString(R.string.gozoso_1_meditacion), R.raw.gozoso_1_meditacion, false, "MEDITACION")),
                new Misterio("2. La Visitación", R.drawable.gozosos2, new Oracion(context.getString(R.string.gozoso_2_meditacion), R.raw.gozoso_2_meditacion, false, "MEDITACION")),
                new Misterio("3. El Nacimiento de Jesús", R.drawable.gozosos3, new Oracion(context.getString(R.string.gozoso_3_meditacion), 0, false, "MEDITACION")),
                new Misterio("4. La Presentación en el Templo", R.drawable.gozosos4, new Oracion(context.getString(R.string.gozoso_4_meditacion), 0, false, "MEDITACION")),
                new Misterio("5. El Niño perdido y hallado", R.drawable.gozosos5, new Oracion(context.getString(R.string.gozoso_5_meditacion), 0, false, "MEDITACION"))
        );
    }

    private List<Misterio> getDolorososInternal() {
        return Arrays.asList(
                new Misterio("1. La Agonía en el Huerto", R.drawable.dolorosos1, new Oracion(context.getString(R.string.doloroso_1_meditacion), 0, false, "MEDITACION")),
                new Misterio("2. La Flagelación", R.drawable.dolorosos2, new Oracion(context.getString(R.string.doloroso_2_meditacion), 0, false, "MEDITACION")),
                new Misterio("3. La Coronación de Espinas", R.drawable.dolorosos3, new Oracion(context.getString(R.string.doloroso_3_meditacion), 0, false, "MEDITACION")),
                new Misterio("4. Jesús carga con la Cruz", R.drawable.dolorosos4, new Oracion(context.getString(R.string.doloroso_4_meditacion), 0, false, "MEDITACION")),
                new Misterio("5. La Crucifixión y Muerte", R.drawable.dolorosos5, new Oracion(context.getString(R.string.doloroso_5_meditacion), 0, false, "MEDITACION"))
        );
    }

    private List<Misterio> getGloriososInternal() {
        return Arrays.asList(
                new Misterio("1. La Resurrección", R.drawable.gloriosos1, new Oracion(context.getString(R.string.glorioso_1_meditacion), 0, false, "MEDITACION")),
                new Misterio("2. La Ascensión", R.drawable.gloriosos2, new Oracion(context.getString(R.string.glorioso_2_meditacion), 0, false, "MEDITACION")),
                new Misterio("3. La Venida del Espíritu Santo", R.drawable.gloriosos3, new Oracion(context.getString(R.string.glorioso_3_meditacion), 0, false, "MEDITACION")),
                new Misterio("4. La Asunción de María", R.drawable.gloriosos4, new Oracion(context.getString(R.string.glorioso_4_meditacion), 0, false, "MEDITACION")),
                new Misterio("5. La Coronación de María", R.drawable.gloriosos5, new Oracion(context.getString(R.string.glorioso_5_meditacion), 0, false, "MEDITACION"))
        );
    }

    private List<Misterio> getLuminososInternal() {
        return Arrays.asList(
                new Misterio("1. El Bautismo de Jesús", R.drawable.luminosos1, new Oracion(context.getString(R.string.luminoso_1_meditacion), 0, false, "MEDITACION")),
                new Misterio("2. Las Bodas de Caná", R.drawable.luminosos2, new Oracion(context.getString(R.string.luminoso_2_meditacion), 0, false, "MEDITACION")),
                new Misterio("3. El Anuncio del Reino", R.drawable.luminosos3, new Oracion(context.getString(R.string.luminoso_3_meditacion), 0, false, "MEDITACION")),
                new Misterio("4. La Transfiguración", R.drawable.luminosos4, new Oracion(context.getString(R.string.luminoso_4_meditacion), 0, false, "MEDITACION")),
                new Misterio("5. La Institución de la Eucaristía", R.drawable.luminosos5, new Oracion(context.getString(R.string.luminoso_5_meditacion), 0, false, "MEDITACION"))
        );
    }

    public List<Misterio> getMisteriosDelDia() {
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        if (diaActual == DayOfWeek.MONDAY || diaActual == DayOfWeek.SATURDAY) {
            return getGozososInternal();
        } else if (diaActual == DayOfWeek.TUESDAY || diaActual == DayOfWeek.FRIDAY) {
            return getDolorososInternal();
        } else if (diaActual == DayOfWeek.WEDNESDAY || diaActual == DayOfWeek.SUNDAY) {
            return getGloriososInternal();
        } else {
            return getLuminososInternal();
        }
    }

    public List<Oracion> getIntroOraciones() { return getIntroOracionesInternal(); }
    public List<Oracion> getDecadaTemplateOraciones() { return getDecadaTemplateOracionesInternal(); }
    public List<Oracion> getConclusionOraciones() { return getConclusionOracionesInternal(); }
}
