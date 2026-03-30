package generator.furnizori;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// Clasa furnizeaza informatii despre procesorul local.
// Comentariile sunt in romana, fara diacritice, pentru a fi lizibile pe orice editor.
public class FurnizorProcesor {

    // Obtine numele procesorului de pe masina curenta.
    // Daca nu este Windows sau apare vreo eroare, se returneaza "Necunoscut".
    public String obtineNumeProcesor() {
        // Preluam numele sistemului de operare si il punem in lower-case
        String sistemOperare = System.getProperty("os.name").toLowerCase();

        // Daca nu este Windows, nu incercam comanda PowerShell
        if (!sistemOperare.contains("win")) {
            return "Necunoscut";
        }

        try {
            // Rulam o comanda PowerShell care citeste proprietatea Name din Win32_Processor
            String rezultat = ruleazaComanda(
                    "powershell",
                    "-Command",
                    "(Get-CimInstance Win32_Processor).Name"
            );

            // Eliminam eventuale spatii la inceput/sfarsit
            rezultat = rezultat.trim();

            // Daca am obtinut ceva, il returnam
            if (!rezultat.isEmpty()) {
                return rezultat;
            }
        } catch (Exception exceptie) {
            // Daca ceva merge prost la rularea comenzii, intoarcem "Necunoscut"
            return "Necunoscut";
        }

        // Daca nu s-a gasit un nume valid, intoarcem "Necunoscut"
        return "Necunoscut";
    }

    // Rulare generica a unei comenzi externe.
    // Primeste argumentele comenzii si returneaza output-ul ca String.
    // Daca procesul iese cu cod diferit de zero, se returneaza sir gol.
    private String ruleazaComanda(String... comanda) throws Exception {
        // Construim procesul cu argumentele primite si redirectionam erorile catre stream-ul de iesire
        ProcessBuilder constructorProces = new ProcessBuilder(comanda);
        constructorProces.redirectErrorStream(true);

        // Pornim procesul
        Process proces = constructorProces.start();

        StringBuilder rezultat = new StringBuilder();

        // Citim output-ul procesului folosind UTF-8
        try (BufferedReader cititor = new BufferedReader(
                new InputStreamReader(proces.getInputStream(), StandardCharsets.UTF_8))) {

            String linie;
            // Citim linie cu linie si concatenam cu separator de linie
            while ((linie = cititor.readLine()) != null) {
                rezultat.append(linie).append(System.lineSeparator());
            }
        }

        // Asteptam terminarea procesului si luam codul de iesire
        int codIesire = proces.waitFor();

        // Daca codul de iesire indica eroare, returnam sir gol
        if (codIesire != 0) {
            return "";
        }

        // Returnam tot output-ul colectat
        return rezultat.toString();
    }
}