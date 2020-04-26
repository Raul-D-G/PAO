package Companie;

import Angajati.Angajat;
import Flota.Flota;
import Garaj.Garaj;
import Ruta.Ruta;
import Ruta.Cursa;
import Flota.Camion;
import Servicii.Stergere.StergereCursa;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Companie {

    private String nume;
    private String adresa;
    private int cui;
    private int IBAN;

    private Garaj garaj;
    private List<Ruta> rute;
    private List<Angajat> angajati;
    private Flota flota;


    public Companie(String nume, String adresa, int cui, int IBAN, Garaj garaj, List<Ruta> rute, List<Angajat> angajati, Flota flota) {
        this.nume = nume;
        this.adresa = adresa;
        this.cui = cui;
        this.IBAN = IBAN;
        this.garaj = new Garaj(garaj);
        this.rute = rute;
        this.angajati = angajati;
        this.flota = new Flota(flota);
    }

    public void adaugaRuta(Ruta ruta) {
        rute.add(ruta);
    }

    public void adaugaAngajat(Angajat angajat) {angajati.add(angajat); }

    public String getNume() {
        return nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public int getCui() {
        return cui;
    }

    public int getIBAN() {
        return IBAN;
    }

    public Garaj getGaraj() {
        return garaj;
    }

    public List<Ruta> getRute() {
        return rute;
    }

    public List<Angajat> getAngajati() {
        return angajati;
    }

    public Flota getFlota() {
        return flota;
    }

    //    Calculeaza cheltuielile din garaj + salariul soferilor
    public int cheltuieliGarajplusSoferi() {
        int cheltuieli = 0;
        cheltuieli += garaj.cheltuieliGaraj();
        cheltuieli += flota.celtuieliSoferi();
        return cheltuieli;
    }

//    Afiseaza cursele disponibile pentru o ruta si returneaza cursa selectata
    public Cursa cautaCurse(@NotNull Ruta ruta) {
        int ok = 0;
        for (Cursa cursa : ruta.getCurse()) {
            ok = 1;
            System.out.println(cursa);
        }
        if (ok == 0) {
            System.out.println("Nu sunt curse disponibile!\n");
        }

        System.out.println("\nIntroduceti id-ul cursei:\n");
        Scanner scanner = new Scanner(System.in);

        int idCursa = scanner.nextInt();

        return selectCursa(ruta, idCursa);
    }

//    Afiseaza toate camioanele disponibile si returneaza camionul
//    selectat dupa nr de imatriculare.
    public Camion cautaCamionDisponibil() {
        int ok = 0;
        for(Camion camion : flota.getCamioane()) {
            if (camion.isDisponibil()) {
                ok = 1;
                System.out.println(camion);
            }
        }
        if (ok == 0) {
            System.out.println("Nu sunt camione disponibile");
        }
        System.out.println("\nIntroduceti numarul de imatriculare:\n");
        Scanner scanner = new Scanner(System.in);
        String camion = scanner.nextLine();
        return selectCamion(camion);
    }

//    Returneaza profitul obtinut in urma cursei acceptate
//    Sterge cursa acceptata din fiserul csv
//    Seteaza camionul selecata indisponibil.
    public double acceptaCursa(@NotNull Cursa cursa, @NotNull Camion camion) {
        double profit;
        int pretCursa = cursa.getPret();
        double consumCamion = camion.getConsumPeKm();
        int km = cursa.getKm();
        double cheltuieli = km * consumCamion;

        profit = pretCursa - cheltuieli;

        StergereCursa.stergereCursa(Integer.toString(cursa.getId()), cursa.getId());

        camion.setDisponibil(false);

        return  profit;
    }

//    Returneaza cursa selectata
    protected Cursa selectCursa(@NotNull Ruta ruta, int id) {
        for (Cursa cursa : ruta.getCurse()) {
            if (cursa.getId() == id)
                return cursa;
        }
        return null;
    }

//    Returneaza camionul selectat
    protected Camion selectCamion(String nr) {
        for (Camion camion : flota.getCamioane()) {
            if (camion.getNumarImatriculare().equalsIgnoreCase(nr))
                return camion;
        }
        return null;
    }


    public Ruta afisareRute() {
        for (Ruta ruta : rute) {
            System.out.println(ruta.getTaraIncarcare() + " -> " + ruta.getTaraDescarcare());
        }
        System.out.println("Selectati ruta dorita.");
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);
        while (!validInput) {
            try {

                String linie = scanner.nextLine();
                String[] tari = linie.split(" ");
                for (Ruta ruta : rute) {
                    if (ruta.getTaraIncarcare().equalsIgnoreCase(tari[0]) && ruta.getTaraDescarcare().equalsIgnoreCase(tari[1])) {
                        validInput = true;
                        return ruta;
                    }
                }

                throw new InputMismatchException("ruta gresita");
            }
            catch (InputMismatchException e) {
                System.out.println("Ruta nu exista !");
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Companie.Companie{" +
                "nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", cui=" + cui +
                ", IBAN=" + IBAN +
                ",\n" + garaj +
                ",\nRute{\n" + rute +
                ",\n" + flota +
                '}';
    }
}
