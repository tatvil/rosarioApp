package es.recursoscatolicos.rosariodelfaro;

// Definición de la clase Misterio
public class Misterio {
    private String nombre;
    private int imagenResId; // ID del recurso de la imagen (ej: R.drawable.misterio1)
    private String descripcion;
    // Puedes añadir una lista de oraciones específicas si lo necesitas en el futuro
    // private List<String> oraciones;

    public Misterio(String nombre, int imagenResId, String descripcion) {
        this.nombre = nombre;
        this.imagenResId = imagenResId;
        this.descripcion = descripcion;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
