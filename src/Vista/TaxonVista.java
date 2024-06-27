package Vista;

import Modelo.Taxon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TaxonVista extends JFrame {
    private JTextArea textArea;
    private JTextField inputField;
    private JButton buscarPadreButton;
    private JButton mismoGrupoButton;
    private JButton mostrarGrupoButton;

    public TaxonVista() {
        setTitle("Visor de Taxones");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(20);
        buscarPadreButton = new JButton("Buscar Padre");
        mismoGrupoButton = new JButton("Mismo Grupo");
        mostrarGrupoButton = new JButton("Mostrar Grupo");

        inputPanel.add(inputField);
        inputPanel.add(buscarPadreButton);
        inputPanel.add(mismoGrupoButton);
        inputPanel.add(mostrarGrupoButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem buscarPadreMenuItem = new JMenuItem("Buscar Padre");
        buscarPadreMenuItem.addActionListener(e -> buscarPadreButton.doClick());
        menu.add(buscarPadreMenuItem);

        JMenuItem mismoGrupoMenuItem = new JMenuItem("Mismo Grupo");
        mismoGrupoMenuItem.addActionListener(e -> mismoGrupoButton.doClick());
        menu.add(mismoGrupoMenuItem);

        JMenuItem mostrarGrupoMenuItem = new JMenuItem("Mostrar Grupo");
        mostrarGrupoMenuItem.addActionListener(e -> mostrarGrupoButton.doClick());
        menu.add(mostrarGrupoMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public String getInput() {
        return inputField.getText();
    }

    public void setOutput(String output) {
        textArea.setText(output);
    }

    public void addBuscarPadreButtonListener(ActionListener listener) {
        buscarPadreButton.addActionListener(listener);
    }

    public void addMismoGrupoButtonListener(ActionListener listener) {
        mismoGrupoButton.addActionListener(listener);
    }

    public void addMostrarGrupoButtonListener(ActionListener listener) {
        mostrarGrupoButton.addActionListener(listener);
    }

    public void mostrarGrupoMonofiletico(Taxon taxon) {
        textArea.setText("");
        mostrarTaxon(taxon, 0);
    }

    private void mostrarTaxon(Taxon taxon, int nivel) {
        for (int i = 0; i < nivel; i++) {
            textArea.append("  ");
        }
        textArea.append(taxon.getNombre() + "\n");
        for (Taxon hijo : taxon.getHijos()) {
            mostrarTaxon(hijo, nivel + 1);
        }
    }
}
