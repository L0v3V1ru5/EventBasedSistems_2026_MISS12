package generator.generatori;

import generator.configurare.ConfiguratieGenerare;
import generator.furnizori.FurnizorCompanii;
import generator.furnizori.FurnizorDateCalendaristice;
import generator.model.Publicatie;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

@AllArgsConstructor
public class GeneratorPublicatii {

    private final ConfiguratieGenerare configuratie;
    private final FurnizorCompanii furnizorCompanii;
    private final FurnizorDateCalendaristice furnizorDateCalendaristice;

    public List<Publicatie> genereaza(int numarElemente, long seed) {
        SplittableRandom generatorAleator = new SplittableRandom(seed);
        List<Publicatie> publicatii = new ArrayList<>(numarElemente);

        for (int i = 0; i < numarElemente; i++) {
            String companie = furnizorCompanii.alegeCompanieAleator(generatorAleator);
            double valoare = genereazaNumar(generatorAleator,
                    configuratie.getValoareMinima(), configuratie.getValoareMaxima());
            double scadere = genereazaNumar(generatorAleator,
                    configuratie.getScadereMinima(), configuratie.getScadereMaxima());
            double variatie = genereazaNumar(generatorAleator,
                    configuratie.getVariatieMinima(), configuratie.getVariatieMaxima());
            String data = furnizorDateCalendaristice.alegeDataAleator(generatorAleator);

            publicatii.add(new Publicatie(companie, valoare, scadere, variatie, data));
        }

        return publicatii;
    }

    private double genereazaNumar(SplittableRandom generatorAleator, double minim, double maxim) {
        return minim + generatorAleator.nextDouble() * (maxim - minim);
    }
}