package Clase;

import java.util.Scanner;

public class Adresa {

    private String oras;
    private String strada;

    public Adresa(){}

    public Adresa(String tara, String judet, String oras, String strada){

        this.oras = oras;
        this.strada = strada;
    }

    public String getOras(){
        return oras;
    }

    public void setOras(String oras){
        this.oras = oras;
    }

    public String getStrada(){
        return strada;
    }

    public void setStrada(String strada){
        this.strada = strada;
    }

    @Override
    public String toString(){
        String rez = "";
        rez = rez + "Oras: " + oras + "\nStrada: " + strada + "\n";
        return rez;
    }

    public void citire(Scanner in){
        System.out.println("Introduceti adresa clientului");
        System.out.println("Oras: ");
        this.oras = in.nextLine();
        System.out.println("Strada: ");
        this.strada = in.nextLine();
    }

}
