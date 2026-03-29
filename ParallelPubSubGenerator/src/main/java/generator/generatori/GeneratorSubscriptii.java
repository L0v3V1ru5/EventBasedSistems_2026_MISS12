package generator.generatori;

import generator.configurare.ConfiguratieGenerare;
import generator.furnizori.FurnizorCompanii;
import generator.furnizori.FurnizorDateCalendaristice;
import generator.model.Camp;
import generator.model.Operator;
import generator.model.Subscriptie;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.SplittableRandom;

@AllArgsConstructor
public class GeneratorSubscriptii {

    private final ConfiguratieGenerare configuratie;
    private final FurnizorCompanii furnizorCompanii;
    private final FurnizorDateCalendaristice furnizorDateCalendaristice;

    // se generera in afara clasei astfel incat 90% din intrari sa contina campul company
    //  respectiv macar 70% sa aiba operatorul egal
    private final Map<Camp, boolean[]> planPrezentaCampuri;
    private final Operator[] planOperatoriCompany;

    public Subscriptie genereazaSubscriptie(int index, long seed) {
        SplittableRandom generatorAleator = new SplittableRandom(seed + index); // ?
        Subscriptie subscriptie = new Subscriptie();

        if (planPrezentaCampuri.get(Camp.company)[index]) {
            Operator operator = planOperatoriCompany[index];
            String companie = furnizorCompanii.alegeCompanieAleator(generatorAleator);
            subscriptie.adaugaPredicatText("company", operator.getSimbol(), companie);
        }

        if (planPrezentaCampuri.get(Camp.value)[index]) {
            Operator operator = alegeOperatorNumeric(generatorAleator);
            double valoare = genereazaNumar(generatorAleator,
                    configuratie.getValoareMinima(), configuratie.getValoareMaxima());
            subscriptie.adaugaPredicatNumar("value", operator.getSimbol(), valoare);
        }

        if (planPrezentaCampuri.get(Camp.drop)[index]) {
            Operator operator = alegeOperatorNumeric(generatorAleator);
            double scadere = genereazaNumar(generatorAleator,
                    configuratie.getScadereMinima(), configuratie.getScadereMaxima());
            subscriptie.adaugaPredicatNumar("drop", operator.getSimbol(), scadere);
        }

        if (planPrezentaCampuri.get(Camp.variation)[index]) {
            Operator operator = alegeOperatorNumeric(generatorAleator);
            double variatie = genereazaNumar(generatorAleator,
                    configuratie.getVariatieMinima(), configuratie.getVariatieMaxima());
            subscriptie.adaugaPredicatNumar("variation", operator.getSimbol(), variatie);
        }

        if (planPrezentaCampuri.get(Camp.date)[index]) {
            Operator operator = Operator.EGAL;
            String data = furnizorDateCalendaristice.alegeDataAleator(generatorAleator);
            subscriptie.adaugaPredicatData("date", operator.getSimbol(), data);
        }

        // daca o subscriptie ar iesi goala fortam adaugarea campului company.
        if (subscriptie.esteGoala()) {
            String companie = furnizorCompanii.alegeCompanieAleator(generatorAleator);
            subscriptie.adaugaPredicatText("company", Operator.EGAL.getSimbol(), companie);
        }

        return subscriptie;
    }

    private Operator alegeOperatorNumeric(SplittableRandom generatorAleator) {
        Operator[] operatori = {
                Operator.EGAL,
                Operator.MAI_MARE,
                Operator.MAI_MARE_SAU_EGAL,
                Operator.MAI_MIC,
                Operator.MAI_MIC_SAU_EGAL
        };
        return operatori[generatorAleator.nextInt(operatori.length)];
    }

    private double genereazaNumar(SplittableRandom generatorAleator, double minim, double maxim) {
        return minim + generatorAleator.nextDouble() * (maxim - minim);
    }
}