package Controlador;

import Modelo.Taxon;
import Vista.TaxonVista;

import javax.swing.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TaxonControlador {
    private TaxonVista vista;
    private Map<String, Taxon> taxones;

    // Buscar el padre de un taxón
    private Function<String, Optional<Taxon>> busquedaTaxonPadre = nombre ->
            buscarTaxon(nombre).flatMap(Taxon::getPadre);

    // Verificar si dos taxones pertenecen al mismo grupo
    private BiFunction<Taxon, Taxon, Boolean> mismoGrupo = (t1, t2) ->
            t1.getPadre().equals(t2.getPadre());

    // Constructor
    public TaxonControlador() {
        this.vista = new TaxonVista();
        this.taxones = new HashMap<>();

        // Iniciar Taxones
        inicializarTaxones();

        // Listeners
        vista.addBuscarPadreButtonListener(e -> {
            String input = vista.getInput();
            busquedaTaxonPadre.apply(input).ifPresentOrElse(
                    padre -> vista.setOutput("Padre de " + input + ": " + padre.getNombre()),
                    () -> vista.setOutput("El taxón " + input + " no tiene padre.")
            );
        });

        vista.addMismoGrupoButtonListener(e -> {
            String[] inputs = vista.getInput().split(",");
            if (inputs.length == 2) {
                String taxon1 = inputs[0].trim();
                String taxon2 = inputs[1].trim();
                buscarTaxon(taxon1).flatMap(t1 ->
                        buscarTaxon(taxon2).map(t2 ->
                                mismoGrupo.apply(t1, t2)
                        )
                ).ifPresentOrElse(
                        mismoGrupo -> vista.setOutput(mismoGrupo ? "Pertenecen al mismo grupo." : "No pertenecen al mismo grupo."),
                        () -> vista.setOutput("Uno o ambos taxones no fueron encontrados.")
                );
            } else {
                vista.setOutput("Por favor, ingrese dos taxones separados por una coma.");
            }
        });

        vista.addMostrarGrupoButtonListener(e -> {
            String input = vista.getInput();
            buscarTaxon(input).ifPresentOrElse(
                    taxon -> mostrarGrupoMonofiletico(taxon),
                    () -> vista.setOutput("Taxón no encontrado.")
            );
        });

        vista.setVisible(true);
    }

    // Método para inicializar los taxones
    private void inicializarTaxones() {
        // Crear taxones
        Taxon mammalia = new Taxon("Mammalia");
        Taxon monotremata = new Taxon("Monotremata");
        Taxon theria = new Taxon("Theria");
        Taxon marsupialia = new Taxon("Marsupialia");
        Taxon eutheria = new Taxon("Eutheria");

        Taxon carnivora = new Taxon("Carnivora");
        Taxon primates = new Taxon("Primates");
        Taxon rodentia = new Taxon("Rodentia");

        Taxon canidae = new Taxon("Canidae");
        Taxon felidae = new Taxon("Felidae");

        Taxon homo = new Taxon("Homo");
        Taxon pan = new Taxon("Pan");

        // Establecer relaciones jerárquicas
        mammalia.agregarHijo(monotremata);
        mammalia.agregarHijo(theria);
        theria.agregarHijo(marsupialia);
        theria.agregarHijo(eutheria);

        eutheria.agregarHijo(carnivora);
        eutheria.agregarHijo(primates);
        eutheria.agregarHijo(rodentia);

        carnivora.agregarHijo(canidae);
        carnivora.agregarHijo(felidae);

        primates.agregarHijo(homo);
        primates.agregarHijo(pan);

        // Agregar taxones al mapa
        agregarTaxon(mammalia);
        agregarTaxon(monotremata);
        agregarTaxon(theria);
        agregarTaxon(marsupialia);
        agregarTaxon(eutheria);
        agregarTaxon(carnivora);
        agregarTaxon(primates);
        agregarTaxon(rodentia);
        agregarTaxon(canidae);
        agregarTaxon(felidae);
        agregarTaxon(homo);
        agregarTaxon(pan);
    }

    // Método para agregar un taxón al mapa
    private void agregarTaxon(Taxon taxon) {
        taxones.put(taxon.getNombre(), taxon);
    }

    // Método para buscar un taxón por nombre
    private Optional<Taxon> buscarTaxon(String nombre) {
        return Optional.ofNullable(taxones.get(nombre));
    }

    // Método para construir y mostrar el grupo monofilético de un taxón
    private void mostrarGrupoMonofiletico(Taxon taxon) {
        List<Taxon> grupo = new ArrayList<>();
        construirGrupoMonofiletico(taxon, grupo);
        vista.mostrarGrupoMonofiletico(taxon);
    }

    // Método recursivo para construir el grupo monofilético
    private void construirGrupoMonofiletico(Taxon taxon, List<Taxon> grupo) {
        grupo.add(taxon);
        taxon.getHijos().forEach(hijo -> construirGrupoMonofiletico(hijo, grupo));
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaxonControlador());
    }
}
