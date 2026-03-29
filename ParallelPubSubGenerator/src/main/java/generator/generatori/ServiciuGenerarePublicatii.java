package generator.generatori;

import generator.model.Publicatie;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class ServiciuGenerarePublicatii implements Callable<List<Publicatie>> {

    private final GeneratorPublicatii generatorPublicatii;
    private final int numarElemente; // ?
    private final long seed;

    @Override
    public List<Publicatie> call() {
        return generatorPublicatii.genereaza(numarElemente, seed);
        // for
    }
}