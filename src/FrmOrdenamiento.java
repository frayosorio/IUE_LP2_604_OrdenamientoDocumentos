import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

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

        cargarDatos();
    }

    private void cargarDatos() {
        String nombreArchivo = System.getProperty("user.dir")
                + "/src/datos/Datos.csv";
        DocumentosServicio.cargar(nombreArchivo);
        DocumentosServicio.mostrar(tblDocumentos);
    }

    private boolean ejecutando;

    private void btnOrdenarBurbujaClick(ActionEvent evt) {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            ejecutando = true;

            new Thread(() -> {
                DocumentosServicio.ordenarBurbuja(cmbCriterio.getSelectedIndex());
                ejecutando = false;
                DocumentosServicio.mostrar(tblDocumentos);
            }).start();

            new Thread(() -> {
                while (ejecutando) {
                    Util.pausarMilisegundos(250);
                    txtTiempo.setText(Util.getTextoTiempoCronometro());
                }
            }).start();
        }
    }

    private void btnOrdenarRapidoClick(ActionEvent evt) {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            ejecutando = true;

            new Thread(() -> {
                DocumentosServicio.ordenarRapido(cmbCriterio.getSelectedIndex());
                ejecutando = false;
                DocumentosServicio.mostrar(tblDocumentos);
            }).start();

            new Thread(() -> {
                while (ejecutando) {
                    Util.pausarMilisegundos(50);
                    txtTiempo.setText(Util.getTextoTiempoCronometro());
                }
            }).start();
        }
    }

    private void btnOrdenarInsercionClick(ActionEvent evt) {

    }

    private void btnBuscar(ActionEvent evt) {

    }

}