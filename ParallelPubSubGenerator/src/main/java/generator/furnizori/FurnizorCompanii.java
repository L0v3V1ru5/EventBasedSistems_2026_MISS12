package generator.furnizori;

import java.util.List;
import java.util.SplittableRandom;

public class FurnizorCompanii {

    List<String> companii = List.of(
            "Dedeman",
            "Banca Transilvania",
            "Digi Communications",
            "eMAG",
            "UiPath",
            "MedLife",
            "Bitdefender",
            "Romgaz",
            "Hidroelectrica",
            "Transgaz",
            "Electrica",
            "Terapia",
            "Altex",
            "FAN Courier",
            "Aquila",
            "Arabesque",
            "One United Properties",
            "CEC Bank",
            "Antibiotice Iasi",
            "Farmacia Tei"
    );

    // SplittableRandom permite seed-uri diferite pentru fiecare thread
    // pe cand altele (ThreadLocalRandom) nu
    // ?
    public String alegeCompanieAleator(SplittableRandom generatorAleator) {
        int index = generatorAleator.nextInt(companii.size());
        return companii.get(index);
    }
}