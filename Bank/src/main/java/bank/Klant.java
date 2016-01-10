package bank;

public class Klant {

    private final int klantNummer;
    private final String naam;
    private final String woonplaats;

    /**
     * Constructor van Klant
     *
     * @param klantNummer klantnummer
     * @param naam naam van klant
     * @param woonplaats woonplaats van klant
     */
    public Klant(int klantNummer, String naam, String woonplaats) {
        this.klantNummer = klantNummer;
        this.naam = naam;
        this.woonplaats = woonplaats;
    }

    public int getKlantNummer() {
        return klantNummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    @Override
    public String toString() {
        return "Klant{" + "klantNummer=" + klantNummer + ", naam=" + naam + ", woonplaats=" + woonplaats + '}';
    }

}
