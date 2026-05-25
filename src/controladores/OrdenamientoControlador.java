package controladores;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import servicios.DocumentosServicio;
import servicios.Util;

public class OrdenamientoControlador {

    private static JTable tblDocumentos;
    private static JTextField txtTiempo;
    private static int criterio;

    public static void setCriterio(JComboBox cmbCriterio) {
        OrdenamientoControlador.criterio = cmbCriterio.getSelectedIndex();
    }

    public static void setTxtTiempo(JTextField txtTiempo) {
        OrdenamientoControlador.txtTiempo = txtTiempo;
    }

    public static void cargarDatos() {
        String nombreArchivo = System.getProperty("user.dir")
                + "/src/datos/Datos.csv";
        DocumentosServicio.cargar(nombreArchivo);
        DocumentosServicio.mostrar(tblDocumentos);
    }

    public static void setTblDocumentos(JTable tblDocumentos) {
        OrdenamientoControlador.tblDocumentos = tblDocumentos;
    }

    private static boolean ejecutando;

    public static void procesar(Runnable operacion) {
        Util.iniciarCronometro();
        ejecutando = true;

        new Thread(() -> {
            operacion.run();
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

    public static void ordenarBurbuja() {
        if (criterio < 0) {
            return;
        }
        procesar(() -> {
            DocumentosServicio.ordenarBurbuja(criterio);
        });
    }

    public static void ordenarRapido() {
        if (criterio < 0) {
            return;
        }
        procesar(() -> {
            DocumentosServicio.ordenarRapido(criterio);
        });
    }

}
