package generator.furnizori;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FurnizorProcesor {

    public String obtineNumeProcesor() {
        String sistemOperare = System.getProperty("os.name").toLowerCase();

        if (!sistemOperare.contains("win")) {
            return "Necunoscut";
        }

        try {
            String rezultat = ruleazaComanda(
                    "powershell",
                    "-Command",
                    "(Get-CimInstance Win32_Processor).Name"
            );

            rezultat = rezultat.trim();

            if (!rezultat.isEmpty()) {
                return rezultat;
            }
        } catch (Exception exceptie) {
            return "Necunoscut";
        }

        return "Necunoscut";
    }

    private String ruleazaComanda(String... comanda) throws Exception {
        ProcessBuilder constructorProces = new ProcessBuilder(comanda);
        constructorProces.redirectErrorStream(true);

        Process proces = constructorProces.start();

        StringBuilder rezultat = new StringBuilder();

        try (BufferedReader cititor = new BufferedReader(
                new InputStreamReader(proces.getInputStream(), StandardCharsets.UTF_8))) {

            String linie;
            while ((linie = cititor.readLine()) != null) {
                rezultat.append(linie).append(System.lineSeparator());
            }
        }

        int codIesire = proces.waitFor();

        if (codIesire != 0) {
            return "";
        }

        return rezultat.toString();
    }
}