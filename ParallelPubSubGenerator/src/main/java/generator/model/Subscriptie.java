package generator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Subscriptie {

    private final List<String> predicate = new ArrayList<>();

    public void adaugaPredicatText(String numeCamp, String operator, String valoare) {
        predicate.add("(" + numeCamp + "," + operator + ",\"" + valoare + "\")");
    }

    public void adaugaPredicatNumar(String numeCamp, String operator, double valoare) {
        predicate.add("(" + numeCamp + "," + operator + "," +
                String.format(java.util.Locale.US, "%.2f", valoare) + ")");
    }

    public void adaugaPredicatData(String numeCamp, String operator, String valoare) {
        predicate.add("(" + numeCamp + "," + operator + "," + valoare + ")");
    }

    public boolean esteGoala() {
        return predicate.isEmpty();
    }

    @Override
    public String toString() {
        StringJoiner unificator = new StringJoiner(";", "{", "}");
        for (String predicat : predicate) {
            unificator.add(predicat);
        }
        return unificator.toString();
    }
}