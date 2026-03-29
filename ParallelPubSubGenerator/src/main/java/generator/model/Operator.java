package generator.model;

public enum Operator {
    EGAL("="),
    DIFERIT("!="),
    MAI_MARE(">"),
    MAI_MARE_SAU_EGAL(">="),
    MAI_MIC("<"),
    MAI_MIC_SAU_EGAL("<=");

    private final String simbol;

    Operator(String simbol) {
        this.simbol = simbol;
    }

    public String getSimbol() {
        return simbol;
    }
}