package modelos;

public class RangoBusqueda {
    private int inicio;
    private int fin;

    public RangoBusqueda(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

}
