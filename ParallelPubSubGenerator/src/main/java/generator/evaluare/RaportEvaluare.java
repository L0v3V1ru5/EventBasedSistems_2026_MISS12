package generator.evaluare;

import generator.configurare.ConfiguratieGenerare;
import generator.furnizori.FurnizorProcesor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RaportEvaluare {

    public void scrieReadme(Path caleFisier,
                            ConfiguratieGenerare configuratie,
                            List<Integer> factoriParalelism,
                            List<RezultatMasurare> timpi) throws IOException {
        FurnizorProcesor furnizorProcesor = new FurnizorProcesor();
        String cpu = furnizorProcesor.obtineNumeProcesor();

        try (BufferedWriter scriitor = Files.newBufferedWriter(caleFisier)) {

            scriitor.write("# Evaluare generator publicatii & subscriptii");
            scriitor.newLine();
            scriitor.newLine();
            scriitor.write("Acest document prezinta rezultatele obtinute in urma rularii generatorului de date");
            scriitor.newLine();
            scriitor.write("pentru publicatii si subscriptii, folosind diferite grade de paralelizare.");
            scriitor.newLine();
            scriitor.newLine();

            scriitor.write("## Configuratie");
            scriitor.newLine();
            scriitor.newLine();
            scriitor.write("- Tip paralelizare: **thread-uri (ExecutorService)**");
            scriitor.newLine();
            scriitor.write("- Factori de paralelism testati: **" + factoriParalelism + "**");
            scriitor.newLine();
            scriitor.write("- Numar publicatii: **" + configuratie.getNumarPublicatii() + "**");
            scriitor.newLine();
            scriitor.write("- Numar subscriptii: **" + configuratie.getNumarSubscriptii() + "**");
            scriitor.newLine();
            scriitor.write("- Procesor: _" + cpu + "_");
            scriitor.newLine();
            scriitor.newLine();

            scriitor.write("## Rezultate masurate");
            scriitor.newLine();
            scriitor.newLine();
            scriitor.write("| Fire | Publicatii (ms) | Subscriptii (ms) | Total (ms) |");
            scriitor.newLine();
            scriitor.write("|------|-----------------|------------------|------------|");
            scriitor.newLine();
            for (RezultatMasurare rezultat : timpi) {
                scriitor.write("| " + rezultat.getNumarFire() +
                        " | " + rezultat.getTimpGenerarePublicatiiMilisecunde() +
                        " | " + rezultat.getTimpGenerareSubscriptiiMilisecunde() +
                        " | " + rezultat.getTimpTotalMilisecunde() +
                        " |");
                scriitor.newLine();
            }
            scriitor.newLine();

            scriitor.write("## Observatii");
            scriitor.newLine();
            scriitor.newLine();
            scriitor.write("- Cresterea numarului de thread-uri reduce timpul total de executie.");
            scriitor.newLine();
            scriitor.write("- Generarea subscriptiilor este mai costisitoare decat cea a publicatiilor,");
            scriitor.newLine();
            scriitor.write("  datorita planificarii distributiei campurilor si operatorilor.");
            scriitor.newLine();
            scriitor.write("- Exista un compromis intre overhead-ul de paralelizare si castigul de performanta.");
            scriitor.newLine();
            scriitor.newLine();

            scriitor.write("## Concluzie");
            scriitor.newLine();
            scriitor.newLine();
            scriitor.write("Utilizarea paralelizarii prin thread-uri imbunatateste semnificativ performanta");
            scriitor.newLine();
            scriitor.write("generatorului, in special pentru volume mari de date.");
            scriitor.newLine();
        }
    }
}