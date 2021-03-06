package Garaj;

import Angajati.Mecanic;
import java.util.List;

public class Garaj {

    private List<Piesa> piese ;
    private List<Mecanic> mecanici;

    public Garaj(List<Piesa> piese, List<Mecanic> mecanici) {
        this.piese = piese;
        this.mecanici = mecanici;
    }

    public Garaj(Garaj garaj) {
        this.piese = garaj.piese;
        this.mecanici = garaj.mecanici;
    }

    public List<Piesa> getPiese() {
        return piese;
    }

    public void adaugaPiesa(Piesa piesa) {
        piese.add(piesa);
    }

    public List<Mecanic> getMecanici() {
        return mecanici;
    }

    public void adaugaMecanic(Mecanic mecanic) {
        mecanici.add(mecanic);
    }

    public int cheltuieliGaraj() {
        int cheltuieli = 0;
        for (Mecanic mecanic: mecanici) {
            mecanic.calculSalariu();
            cheltuieli += mecanic.getSalariuMecanic();
        }
        for (Piesa piesa : piese) {
            cheltuieli += piesa.getPret();
        }
        return cheltuieli;
    }

    @Override
    public String toString() {
        return "Garaj{\n" +
                "piese=" + piese +
                ",\nmecanici=" + mecanici +
                '}';
    }
}