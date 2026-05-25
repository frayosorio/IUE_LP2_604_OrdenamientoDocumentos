package servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.Documento;

public class DocumentosServicio {

    private static List<Documento> documentos = Collections.emptyList();
    private static String[] encabezados = new String[] { "#", "Primer Apellido", "Segundo Apellido", "Nombres",
            "Documento" };

    public static String[] getEncabezados() {
        return encabezados;
    }

    public static void cargar(String nombreArchivo) {
        try {
            var lineas = Files.lines(Paths.get(nombreArchivo));
            documentos = lineas.skip(1)
                    .map(linea -> linea.split(";"))
                    .map(textos -> new Documento(textos[0], textos[1], textos[2], textos[3]))
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            Collections.emptyList();
        }
    }

    public static void mostrar(JTable tbl) {
        AtomicInteger fila = new AtomicInteger(1);
        var datos = documentos.stream()
                .map(documento -> new String[] {
                        String.valueOf(fila.getAndIncrement()),
                        documento.getApellido1(),
                        documento.getApellido2(),
                        documento.getNombre(),
                        documento.getDocumento() })
                .collect(Collectors.toList())
                .toArray(String[][]::new);

        /*
         * String[][] datos = new String[documentos.size()][encabezados.length];
         * IntStream.range(0, datos.length).forEach(fila -> {
         * Documento dato = documentos.get(fila);
         * datos[fila][0] = String.valueOf(fila);
         * datos[fila][1] = dato.getApellido1();
         * datos[fila][2] = dato.getApellido2();
         * datos[fila][3] = dato.getNombre();
         * datos[fila][4] = dato.getDocumento();
         * });
         */

        /*
         * String[][] datos = new String[documentos.size()][encabezados.length];
         * AtomicInteger fila = new AtomicInteger(0);
         * documentos.forEach(dato -> {
         * int i = fila.getAndIncrement();
         * datos[fila][0] = String.valueOf(i);
         * datos[i][1] = dato.getApellido1();
         * datos[i][2] = dato.getApellido2();
         * datos[i][3] = dato.getNombre();
         * datos[i][4] = dato.getDocumento();
         * });
         */
        var dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }

    /********** Ordenamientos **********/

    private static boolean esMayor(Documento d1, Documento d2, int criterio) {
        if (criterio == 0) // primero nombre completo y luego el tipo de documento
        {
            return d1.getNombreCompleto().compareTo(d2.getNombreCompleto()) > 0 ||
                    (d1.getNombreCompleto().equals(d2.getNombreCompleto()) &&
                            d1.getDocumento().compareTo(d2.getDocumento()) > 0);
        } else // primero el tipo de documento y luego el nombre completo
        {
            return d1.getDocumento().compareTo(d2.getDocumento()) > 0 ||
                    (d1.getDocumento().equals(d2.getDocumento()) &&
                            d1.getNombreCompleto().compareTo(d2.getNombreCompleto()) > 0);
        }
    }

    private static void intercambiar(int i, int j) {
        if (0 <= i && i < documentos.size() && 0 <= j && j < documentos.size()) {
            var aux = documentos.get(i);
            documentos.set(i, documentos.get(j));
            documentos.set(j, aux);
        }
    }

    public static void ordenarBurbuja(int criterio) {
        for (int i = 0; i < documentos.size() - 1; i++) {
            for (int j = i + 1; j < documentos.size(); j++) {
                if (esMayor(documentos.get(i), documentos.get(j), criterio)) {
                    intercambiar(i, j);
                }
            }
        }
    }

    private static int ubicarPivote(int inicio, int fin, int criterio) {
        int pivote = inicio;
        var documentoPivote = documentos.get(pivote);
        for (int i = inicio + 1; i <= fin; i++) {
            if (esMayor(documentoPivote, documentos.get(i), criterio)) {
                pivote++;
                if (i != pivote)
                    intercambiar(i, pivote);
            }
        }
        if (inicio != pivote)
            intercambiar(inicio, pivote);
        return pivote;
    }

    private static void ordenarRapido(int inicio, int fin, int criterio) {
        if (inicio >= fin) {
            return;
        } else {
            int pivote = ubicarPivote(inicio, fin, criterio);
            ordenarRapido(inicio, pivote - 1, criterio);
            ordenarRapido(pivote + 1, fin, criterio);
        }
    }

    public static void ordenarRapido(int criterio) {
        ordenarRapido(0, documentos.size() - 1, criterio);
    }

    /********** Busquedas **********
     * 
     * Busqueda BINARIA (los datos deben estar ordenados)
     * 
     * Lista Completa
     * ┌───────┬─────────┬───────┬────────┬────────┬────────┬────────┐
     * │ Ana   │ Carlos  │ Juan  │ Maria  │ Pedro  │ Sofia  │ Zulema │
     * └───────┴─────────┴───────┴────────┴────────┴────────┴────────┘
     *     0        1        2        3        4        5        6
     * 
     * Buscar -> "Pedro"
     * 
     * ┌───────┬─────────┬───────┬────────┬────────┬────────┬────────┐
     * │ Ana   │ Carlos  │ Juan  │ Maria  │ Pedro  │ Sofia  │ Zulema │
     * └───────┴─────────┴───────┴────────┴────────┴────────┴────────┘
     *     0        1        2        3        4        5        6
     *                                ↑
     *                              medio
     * 
     * "Pedro" > "Maria"
     * 
     * Nueva búsqueda
     * ┌────────┬────────┬────────┐
     * │ Pedro  │ Sofia  │ Zulema │
     * └────────┴────────┴────────┘
     *      4        5       6
     *               ↑
     *             medio
     * 
     * "Pedro" < "Sofia"
     * 
     * Última búsqueda
     * ┌────────┐
     * │ Pedro  │
     * └────────┘
     *     4
     *     ↑
     *  medio
     * 
     * "Pedro" = "Pedro" (ENCONTRADO)
     * 
     * ------------------------------
     * Arbol de recursividad (llamadas recursivas)
     * buscar("Pedro", 0, 6)
     *          │
     *          ▼
     *       medio=3
     *       "Maria"
     * │
     * Pedro > Maria
     * │
     * ▼
     * buscar("Pedro", 4, 6)
     * │
     * ▼
     * medio=5
     * Sofia
     * │
     * Pedro < Sofia
     * │
     * ▼
     * buscar("Pedro", 4, 4)
     * │
     * ▼
     * medio=4
     * Pedro
     * │
     * ▼
     * ENCONTRADO
     * 
     */

    public static int buscarBinariaPorNombre(String texto) {
        texto = ignorarTildesyMayusculas(texto);
        return buscarBinariaPorNombre(texto, 0, documentos.size() - 1);
    }

    private static String ignorarTildesyMayusculas(String texto){
        return texto.toLowerCase()
            .replace("á", "a")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ó", "o")
            .replace("ú", "u")
            .replace("ü", "u");
    }

    private static int buscarBinariaPorNombre(String texto, int inicio, int fin) {
        if (inicio > fin)
            return -1;

        // hallar el punto medio
        int medio = (inicio + fin) / 2;
        var datoAComparar=ignorarTildesyMayusculas(documentos.get(medio).getNombreCompleto());

        //comparar para la llamada recursiva
        if (datoAComparar.startsWith(texto)) {
            return medio;
        }
        if (texto.compareTo(datoAComparar) < 0) {
            return buscarBinariaPorNombre(texto, inicio, medio - 1);
        }

        return buscarBinariaPorNombre(texto, medio + 1, fin);
    }

}
