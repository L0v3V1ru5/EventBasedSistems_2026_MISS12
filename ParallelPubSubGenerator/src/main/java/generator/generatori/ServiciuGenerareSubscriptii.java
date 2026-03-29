package generator.generatori;

import generator.model.Subscriptie;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class ServiciuGenerareSubscriptii implements Callable<List<Subscriptie>> {

    private final GeneratorSubscriptii generatorSubscriptii;
    private final int indexStart;
    private final int indexSfarsit;
    private final long seed;

    @Override
    public List<Subscriptie> call() {
        List<Subscriptie> rezultat = new ArrayList<>(indexSfarsit - indexStart);

        for (int i = indexStart; i < indexSfarsit; i++) {
            rezultat.add(generatorSubscriptii.genereazaSubscriptie(i, seed));
        }

        return rezultat;
    }
}