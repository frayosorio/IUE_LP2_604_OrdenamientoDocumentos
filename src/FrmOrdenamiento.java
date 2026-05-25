import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import controladores.OrdenamientoControlador;
import servicios.DocumentosServicio;
import servicios.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class FrmOrdenamiento extends JFrame {

    private JButton btnOrdenarBurbuja;
    private JButton btnOrdenarRapido;
    private JButton btnOrdenarInsercion;
    private JToolBar tbOrdenamiento;
    private JComboBox cmbCriterio;
    private JTextField txtTiempo;
    private JButton btnBuscar;
    private JTextField txtBuscar;

    private JTable tblDocumentos;

    public FrmOrdenamiento() {

        tbOrdenamiento = new JToolBar();
        btnOrdenarBurbuja = new JButton();
        btnOrdenarInsercion = new JButton();
        btnOrdenarRapido = new JButton();
        cmbCriterio = new JComboBox();
        txtTiempo = new JTextField();

        btnBuscar = new JButton();
        txtBuscar = new JTextField();

        tblDocumentos = new JTable();
        var dtm = new DefaultTableModel(null, DocumentosServicio.getEncabezados());
        tblDocumentos.setModel(dtm);
        JScrollPane spDocumentos = new JScrollPane(tblDocumentos);

        setSize(600, 400);
        setTitle("Ordenamiento Documentos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnOrdenarBurbuja.setIcon(new ImageIcon(getClass().getResource("/iconos/Ordenar.png")));
        btnOrdenarBurbuja.setToolTipText("Ordenar Burbuja");
        btnOrdenarBurbuja.addActionListener(evt -> {
            btnOrdenarBurbujaClick(evt);
        });
        tbOrdenamiento.add(btnOrdenarBurbuja);

        btnOrdenarRapido.setIcon(new ImageIcon(getClass().getResource("/iconos/OrdenarRapido.png")));
        btnOrdenarRapido.setToolTipText("Ordenar Rapido");
        btnOrdenarRapido.addActionListener(evt -> {
            btnOrdenarRapidoClick(evt);
        });
        tbOrdenamiento.add(btnOrdenarRapido);

        btnOrdenarInsercion.setIcon(new ImageIcon(getClass().getResource("/iconos/OrdenarInsercion.png")));
        btnOrdenarInsercion.setToolTipText("Ordenar Inserción");
        btnOrdenarInsercion.addActionListener(evt -> {
            btnOrdenarInsercionClick(evt);
        });
        tbOrdenamiento.add(btnOrdenarInsercion);

        cmbCriterio.setModel(new DefaultComboBoxModel(
                new String[] { "Nombre Completo, Tipo de Documento", "Tipo de Documento, Nombre Completo" }));
        cmbCriterio.addActionListener(evt -> {
            OrdenamientoControlador.setCriterio(cmbCriterio);
        });

        tbOrdenamiento.add(cmbCriterio);

        tbOrdenamiento.add(txtTiempo);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/iconos/Buscar.png")));
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(evt -> {
            btnBuscar(evt);
        });
        tbOrdenamiento.add(btnBuscar);
        tbOrdenamiento.add(txtBuscar);

        getContentPane().add(tbOrdenamiento, BorderLayout.NORTH);
        getContentPane().add(spDocumentos, BorderLayout.CENTER);

        OrdenamientoControlador.setTblDocumentos(tblDocumentos);
        OrdenamientoControlador.setTxtTiempo(txtTiempo);
        OrdenamientoControlador.cargarDatos();
    }

    private void btnOrdenarBurbujaClick(ActionEvent evt) {
        OrdenamientoControlador.ordenarBurbuja();
    }

    private void btnOrdenarRapidoClick(ActionEvent evt) {
        OrdenamientoControlador.ordenarRapido();
    }

    private void btnOrdenarInsercionClick(ActionEvent evt) {

    }

    private void btnBuscar(ActionEvent evt) {
        /*
         * var dato = txtBuscar.getText().trim();
         * if (!dato.isEmpty()) {
         * Util.iniciarCronometro();
         * ejecutando = true;
         * 
         * new Thread(() -> {
         * var indice = DocumentosServicio.buscarBinariaPorNombre(dato);
         * ejecutando = false;
         * if (indice >= 0) {
         * tblDocumentos.setRowSelectionInterval(indice, indice);
         * tblDocumentos.scrollRectToVisible(tblDocumentos.getCellRect(indice, 0,
         * true));
         * } else {
         * tblDocumentos.clearSelection();
         * JOptionPane.showMessageDialog(null, "Dato no encontrado");
         * }
         * }).start();
         * 
         * new Thread(() -> {
         * while (ejecutando) {
         * Util.pausarMilisegundos(50);
         * txtTiempo.setText(Util.getTextoTiempoCronometro());
         * }
         * }).start();
         * } else {
         * JOptionPane.showMessageDialog(null,
         * "No ha especificado un dato para buscar");
         * }
         */
    }

}