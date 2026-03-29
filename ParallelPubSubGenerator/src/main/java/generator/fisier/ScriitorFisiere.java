package generator.fisier;

import generator.model.Publicatie;
import generator.model.Subscriptie;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ScriitorFisiere {

    public void scriePublicatii(Path caleFisier, List<Publicatie> publicatii) throws IOException {
        try (BufferedWriter scriitor = Files.newBufferedWriter(caleFisier)) {
            for (Publicatie publicatie : publicatii) {
                scriitor.write(publicatie.toString());
                scriitor.newLine();
            }
        }
    }

    public void scrieSubscriptii(Path caleFisier, List<Subscriptie> subscriptii) throws IOException {
        try (BufferedWriter scriitor = Files.newBufferedWriter(caleFisier)) {
            for (Subscriptie subscriptie : subscriptii) {
                scriitor.write(subscriptie.toString());
                scriitor.newLine();
            }
        }
    }
}