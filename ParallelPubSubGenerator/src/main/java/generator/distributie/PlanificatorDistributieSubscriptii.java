package generator.distributie;

import generator.configurare.ConfiguratieGenerare;
import generator.model.Camp;
import generator.model.Operator;

import java.util.*;

public class PlanificatorDistributieSubscriptii {

    public Map<Camp, boolean[]> construiestePlanPrezentaCampuri(ConfiguratieGenerare configuratie, long seed) {
        int numarSubscriptii = configuratie.getNumarSubscriptii();
        Map<Camp, boolean[]> plan = new EnumMap<>(Camp.class);
        SplittableRandom generatorAleator = new SplittableRandom(seed);

        for (Camp camp : Camp.values()) {
            boolean[] prezente = new boolean[numarSubscriptii];

            double procent = configuratie.getFrecventaCampuriSubscriptii().getOrDefault(camp, 0.0);
            int numarAparitii = (int) Math.round(numarSubscriptii * procent / 100.0);

            List<Integer> pozitii = new ArrayList<>(numarSubscriptii);
            for (int i = 0; i < numarSubscriptii; i++) {
                pozitii.add(i);
            }

            // pentru fiecare camp, creez un alt seed pentru shuffle
            Collections.shuffle(pozitii, new Random(generatorAleator.nextLong()));

            for (int i = 0; i < numarAparitii && i < numarSubscriptii; i++) {
                prezente[pozitii.get(i)] = true;
            }

            plan.put(camp, prezente);
        }

        return plan;
    }

    public Operator[] construiestePlanOperatoriCompany(ConfiguratieGenerare configuratie,
                                                       boolean[] planCompany,
                                                       long seed) {
        int numarSubscriptii = configuratie.getNumarSubscriptii();
        Operator[] operatori = new Operator[numarSubscriptii];

        // lista index-uri subscriptii care au company field
        List<Integer> pozitiiCompany = new ArrayList<>();
        for (int i = 0; i < numarSubscriptii; i++) {
            if (planCompany[i]) {
                pozitiiCompany.add(i);
            }
        }

        int numarCuCompany = pozitiiCompany.size();
        int numarMinimCuEgal = (int) Math.ceil(numarCuCompany * configuratie.getProcentMinimEgalitateCompany() / 100.0);

        Collections.shuffle(pozitiiCompany, new Random(seed));

        for (int i = 0; i < pozitiiCompany.size(); i++) {
            int index = pozitiiCompany.get(i);

            if (i < numarMinimCuEgal) {
                operatori[index] = Operator.EGAL;
            } else {
                operatori[index] = Operator.DIFERIT;
            }
        }

        return operatori;
    }
}