package core;

public class Entrenamiento {

        String fecha;
        double horas;
        double minutos;
        double segundos;
        double metros;
        double minutosporkm;
        double velocidadmediakmporhora;

    public String getFecha() {
        return fecha;
    }

    public double getHoras() {
        return horas;
    }

    public double getMinutos() {
        return minutos;
    }

    public double getSegundos() {
        return segundos;
    }

    public double getMetros() {
        return metros;
    }

    public double getMinutosporkm() {
        return minutosporkm;
    }

    public double getVelocidadmediakmporhora() {
        return velocidadmediakmporhora;
    }

    public Entrenamiento(String fecha, double horas, double minutos, double segundos, double metros, double r1, double r2) {
        this.fecha= fecha;
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
        this.metros = metros;
        this.minutosporkm = r1;
        this.velocidadmediakmporhora = r2;
    }

    @Override
    public String toString() {
        return "Entrenamiento: \n" +
                "Fecha=" + fecha +
                ", Horas=" + horas +
                ", Minutos=" + minutos +
                ", Segundos=" + segundos +
                ", Metros=" + metros +
                ", Minutos por km=" + minutosporkm +
                ", Velocidad media=" + velocidadmediakmporhora;
    }
}
