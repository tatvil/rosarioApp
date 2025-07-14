package es.recursoscatolicos.rosariodelfaro;

public class Misterio {
    private String nombre;
    private int imagenResId;
    private String meditation; // Nuevo campo para la meditación

    // Constructor actualizado para incluir la meditación
    public Misterio(String nombre, int imagenResId, String meditation) {
        this.nombre = nombre;
        this.imagenResId = imagenResId;
        this.meditation = meditation;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    // Nuevo método getter para la meditación
    public String getMeditation() {
        return meditation;
    }
}
