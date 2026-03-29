package generator.evaluare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RezultatMasurare {

    private final int numarFire;
    private final int numarPublicatii;
    private final int numarSubscriptii;
    private final long timpGenerarePublicatiiMilisecunde;
    private final long timpGenerareSubscriptiiMilisecunde;
    private final long timpTotalMilisecunde;

    @Override
    public String toString() {
        return "RezultatMasurare{" +
                "numarFire=" + numarFire +
                ", numarPublicatii=" + numarPublicatii +
                ", numarSubscriptii=" + numarSubscriptii +
                ", timpGenerarePublicatiiMilisecunde=" + timpGenerarePublicatiiMilisecunde +
                ", timpGenerareSubscriptiiMilisecunde=" + timpGenerareSubscriptiiMilisecunde +
                ", timpTotalMilisecunde=" + timpTotalMilisecunde +
                '}';
    }
}