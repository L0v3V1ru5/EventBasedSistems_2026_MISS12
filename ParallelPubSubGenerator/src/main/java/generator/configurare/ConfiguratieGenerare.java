package generator.configurare;

import generator.model.Camp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

@Getter
@Setter
public class ConfiguratieGenerare {

    private int numarPublicatii;
    private int numarSubscriptii;

    private double valoareMinima;
    private double valoareMaxima;

    private double scadereMinima;
    private double scadereMaxima;

    private double variatieMinima;
    private double variatieMaxima;

    private LocalDate dataInceput;
    private LocalDate dataSfarsit;

    private final Map<Camp, Double> frecventaCampuriSubscriptii = new EnumMap<>(Camp.class);
    private double procentMinimEgalitateCompany;
}