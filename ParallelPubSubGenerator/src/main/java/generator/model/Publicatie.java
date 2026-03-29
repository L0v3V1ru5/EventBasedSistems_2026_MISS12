package generator.model;

import lombok.Data;

@Data
public class Publicatie {

    private final String companie;
    private final double valoare;
    private final double scadere;
    private final double variatie;
    private final String data;

    @Override
    public String toString() {
        return "{(company,\"" + companie + "\");" +
                "(value," + String.format(java.util.Locale.US, "%.2f", valoare) + ");" +
                "(drop," + String.format(java.util.Locale.US, "%.2f", scadere) + ");" +
                "(variation," + String.format(java.util.Locale.US, "%.2f", variatie) + ");" +
                "(date," + data + ")}";
    }
}