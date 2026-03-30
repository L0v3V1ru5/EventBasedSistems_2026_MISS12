package generator.furnizori;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.SplittableRandom;

@AllArgsConstructor
public class FurnizorDateCalendaristice {

    private final LocalDate dataInceput;
    private final LocalDate dataSfarsit;
    private final DateTimeFormatter formator = DateTimeFormatter.ofPattern("d.MM.yyyy");

    // SplittableRandom permite seed-uri diferite pentru fiecare thread
    // pe cand altele (ThreadLocalRandom) nu
    public String alegeDataAleator(SplittableRandom generatorAleator) {
        long ziInceput = dataInceput.toEpochDay();
        long ziSfarsit = dataSfarsit.toEpochDay();

        long ziAleasa = generatorAleator.nextLong(ziInceput, ziSfarsit + 1);
        LocalDate data = LocalDate.ofEpochDay(ziAleasa);
        return data.format(formator);
    }
}