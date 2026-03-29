package generator.paralelizare;

import generator.configurare.ConfiguratieGenerare;
import generator.distributie.PlanificatorDistributieSubscriptii;
import generator.evaluare.RezultatMasurare;
import generator.fisier.ScriitorFisiere;
import generator.furnizori.FurnizorCompanii;
import generator.furnizori.FurnizorDateCalendaristice;
import generator.generatori.GeneratorPublicatii;
import generator.generatori.GeneratorSubscriptii;
import generator.generatori.ServiciuGenerarePublicatii;
import generator.generatori.ServiciuGenerareSubscriptii;
import generator.model.Camp;
import generator.model.Operator;
import generator.model.Publicatie;
import generator.model.Subscriptie;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExecutorGenerareParalela {

    private final long SEED_SUB_CAMPURI = 2000L;
    private final long SEED_SUB_OPERATORI = 3000L;
    private final long SEED_SUB = 4000L;
    private final long SEED_PUB = 1000L;

    public RezultatMasurare genereazaTot(ConfiguratieGenerare configuratie,
                                         int numarFire,
                                         Path directorIesire) throws Exception {
        long timpStartTotal = System.currentTimeMillis();

        FurnizorCompanii furnizorCompanii = new FurnizorCompanii();
        FurnizorDateCalendaristice furnizorDateCalendaristice =
                new FurnizorDateCalendaristice(configuratie.getDataInceput(), configuratie.getDataSfarsit());
        ScriitorFisiere scriitorFisiere = new ScriitorFisiere();

        // publicatii
        long timpStartPublicatii = System.currentTimeMillis();
        List<Publicatie> publicatii = genereazaPublicatii(configuratie, numarFire, furnizorCompanii, furnizorDateCalendaristice);
        long timpFinalPublicatii = System.currentTimeMillis();

        // subscriptii
        long timpStartSubscriptii = System.currentTimeMillis();
        List<Subscriptie> subscriptii = genereazaSubscriptii(configuratie, numarFire, furnizorCompanii, furnizorDateCalendaristice);
        long timpFinalSubscriptii = System.currentTimeMillis();

        // rezultate
        scriitorFisiere.scriePublicatii(
                directorIesire.resolve("publicatii_" + numarFire + "_fire.txt"),
                publicatii
        );
        scriitorFisiere.scrieSubscriptii(
                directorIesire.resolve("subscriptii_" + numarFire + "_fire.txt"),
                subscriptii
        );

        long timpFinalTotal = System.currentTimeMillis();

        return new RezultatMasurare(
                numarFire,
                configuratie.getNumarPublicatii(),
                configuratie.getNumarSubscriptii(),
                timpFinalPublicatii - timpStartPublicatii,
                timpFinalSubscriptii - timpStartSubscriptii,
                timpFinalTotal - timpStartTotal
        );
    }

    private List<Publicatie> genereazaPublicatii(ConfiguratieGenerare configuratie,
                                                 int numarFire,
                                                 FurnizorCompanii furnizorCompanii,
                                                 FurnizorDateCalendaristice furnizorDateCalendaristice)
            throws InterruptedException, ExecutionException {
        GeneratorPublicatii generatorPublicatii =
                new GeneratorPublicatii(configuratie, furnizorCompanii, furnizorDateCalendaristice);

        ExecutorService executorService = Executors.newFixedThreadPool(numarFire);
        List<Future<List<Publicatie>>> rezultate = new ArrayList<>();

        int total = configuratie.getNumarPublicatii();
        int baza = total / numarFire;
        int rest = total % numarFire;

        // impartim cat mai echilibrat munca intre fire
        // primele "rest" fire primesc cate un element in plus
        //  aici nu avem vreun plan, deoarece nu avem conditii de procentaj global
        //  conteaza sa generam doar numarul total de publicatii
        for (int i = 0; i < numarFire; i++) {
            int numarElemente = baza + (i < rest ? 1 : 0);
            rezultate.add(executorService.submit(
                    new ServiciuGenerarePublicatii(generatorPublicatii, numarElemente, SEED_PUB + i)
            ));
        }

        List<Publicatie> toatePublicatiile = new ArrayList<>(total);
        for (Future<List<Publicatie>> rezultat : rezultate) {
            toatePublicatiile.addAll(rezultat.get());
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        return toatePublicatiile;
    }

    private List<Subscriptie> genereazaSubscriptii(ConfiguratieGenerare configuratie,
                                                   int numarFire,
                                                   FurnizorCompanii furnizorCompanii,
                                                   FurnizorDateCalendaristice furnizorDateCalendaristice)
            throws InterruptedException, ExecutionException {
        PlanificatorDistributieSubscriptii planificator = new PlanificatorDistributieSubscriptii();

        Map<Camp, boolean[]> planPrezentaCampuri =
                planificator.construiestePlanPrezentaCampuri(configuratie, SEED_SUB_CAMPURI);
        Operator[] planOperatoriCompany =
                planificator.construiestePlanOperatoriCompany(
                        configuratie,
                        planPrezentaCampuri.get(Camp.company),
                        SEED_SUB_OPERATORI
                );

        GeneratorSubscriptii generatorSubscriptii = new GeneratorSubscriptii(
                configuratie,
                furnizorCompanii,
                furnizorDateCalendaristice,
                planPrezentaCampuri,
                planOperatoriCompany
        );

        ExecutorService executorService = Executors.newFixedThreadPool(numarFire);
        List<Future<List<Subscriptie>>> rezultate = new ArrayList<>();

        int total = configuratie.getNumarSubscriptii();
        int baza = total / numarFire;
        int rest = total % numarFire;

        // pentru subscriptii trebuie sa generam conform fiecarei intrari din planuri
        // asadar trebuie impartite listele planurilor in secvente
        int indexCurent = 0;
        for (int i = 0; i < numarFire; i++) {
            int dimensiuneSegment = baza + (i < rest ? 1 : 0);
            int indexStart = indexCurent;
            int indexSfarsit = indexCurent + dimensiuneSegment;

            rezultate.add(executorService.submit(
                    new ServiciuGenerareSubscriptii(generatorSubscriptii, indexStart, indexSfarsit, SEED_SUB + i)
            ));

            indexCurent = indexSfarsit;
        }

        List<Subscriptie> toateSubscriptiile = new ArrayList<>(total);
        for (Future<List<Subscriptie>> rezultat : rezultate) {
            toateSubscriptiile.addAll(rezultat.get());
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        return toateSubscriptiile;
    }
}