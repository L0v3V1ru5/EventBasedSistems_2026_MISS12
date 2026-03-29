package generator;



import generator.configurare.ConfiguratieGenerare;
import generator.evaluare.RaportEvaluare;
import generator.evaluare.RezultatMasurare;
import generator.model.Camp;
import generator.paralelizare.ExecutorGenerareParalela;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aplicatie {

    private static final int NUMAR_PUBLICATII = 50000;
    private static final int NUMAR_SUBSCRIPTII = 50000;

    private static final double VALOARE_MINIMA = 10.0;
    private static final double VALOARE_MAXIMA = 500.0;

    private static final double SCADERE_MINIMA = 0.0;
    private static final double SCADERE_MAXIMA = 100.0;

    private static final double VARIATIE_MINIMA = -1.0;
    private static final double VARIATIE_MAXIMA = 1.0;

    private static final LocalDate DATA_INCEPUT = LocalDate.of(2022, 1, 1);
    private static final LocalDate DATA_SFARSIT = LocalDate.of(2026, 12, 31);

    private static final double FRECVENTA_COMPANY = 90.0;
    private static final double FRECVENTA_VALUE = 70.0;
    private static final double FRECVENTA_DROP = 60.0;
    private static final double FRECVENTA_VARIATION = 75.0;
    private static final double FRECVENTA_DATE = 50.0;

    private static final double PROCENT_MINIM_EGAL_COMPANY = 70.0;

    public static void main(String[] argumente) throws Exception {
        ConfiguratieGenerare configuratie = construiesteConfiguratie();

        Path directorIesire = Path.of("iesire");
        Files.createDirectories(directorIesire);

        ExecutorGenerareParalela executorGenerareParalela = new ExecutorGenerareParalela();

        List<Integer> factoriParalelism = List.of(1, 4);
        List<RezultatMasurare> timpi = new ArrayList<>();
        for (int numarFire : factoriParalelism) {
            RezultatMasurare rezultatMasurare =
                    executorGenerareParalela.genereazaTot(configuratie, numarFire, directorIesire);

            timpi.add(rezultatMasurare);
            System.out.println(rezultatMasurare);
        }

        RaportEvaluare raportEvaluare = new RaportEvaluare();
        raportEvaluare.scrieReadme(directorIesire.resolve("readme.md"), configuratie, factoriParalelism, timpi);

        System.out.println("Generarea a fost finalizata.");
    }

    private static ConfiguratieGenerare construiesteConfiguratie() {
        ConfiguratieGenerare configuratie = new ConfiguratieGenerare();

        configuratie.setNumarPublicatii(NUMAR_PUBLICATII);
        configuratie.setNumarSubscriptii(NUMAR_SUBSCRIPTII);

        configuratie.setValoareMinima(VALOARE_MINIMA);
        configuratie.setValoareMaxima(VALOARE_MAXIMA);

        configuratie.setScadereMinima(SCADERE_MINIMA);
        configuratie.setScadereMaxima(SCADERE_MAXIMA);

        configuratie.setVariatieMinima(VARIATIE_MINIMA);
        configuratie.setVariatieMaxima(VARIATIE_MAXIMA);

        configuratie.setDataInceput(DATA_INCEPUT);
        configuratie.setDataSfarsit(DATA_SFARSIT);

        configuratie.getFrecventaCampuriSubscriptii().put(Camp.company, FRECVENTA_COMPANY);
        configuratie.getFrecventaCampuriSubscriptii().put(Camp.value, FRECVENTA_VALUE);
        configuratie.getFrecventaCampuriSubscriptii().put(Camp.drop, FRECVENTA_DROP);
        configuratie.getFrecventaCampuriSubscriptii().put(Camp.variation, FRECVENTA_VARIATION);
        configuratie.getFrecventaCampuriSubscriptii().put(Camp.date, FRECVENTA_DATE);

        configuratie.setProcentMinimEgalitateCompany(PROCENT_MINIM_EGAL_COMPANY);

        return configuratie;
    }
}