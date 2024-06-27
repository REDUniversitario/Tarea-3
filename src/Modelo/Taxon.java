package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Taxon {
    private String nombre;
    private Optional<Taxon> padre;
    private List<Taxon> hijos;

    public Taxon(String nombre) {
        this.nombre = nombre;
        this.padre = Optional.empty();
        this.hijos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarHijo(Taxon hijo) {
        hijo.setPadre(this);
        hijos.add(hijo);
    }

    public List<Taxon> getHijos() {
        return hijos;
    }

    public void setPadre(Taxon padre) {
        this.padre = Optional.of(padre);
    }

    public Optional<Taxon> getPadre() {
        return padre;
    }
}
