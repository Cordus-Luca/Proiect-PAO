package Clase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {

    private int id;
    private String nume;
    private String prenume;
    private String email;
    private String numarTelefon;
    private Date dataNastere;
    private String CNP;
    private Adresa adresa;
    public static int id_static;

    static {
        id_static = 0;
    }

    {
        ++id_static;
    }
    public Client(){
        this.id = id_static;
        this.dataNastere = new Date();
    }

    public Client(String nume, String prenume, String email, String numarTelefon, Date dataNastere, String CNP, Adresa adresa){
        this.id = id_static;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.numarTelefon = numarTelefon;
        this.dataNastere = dataNastere;
        this.CNP = CNP;
        this.adresa = adresa;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNume()
    {
        return nume;
    }

    public void setNume(String nume)
    {
        this.nume = nume;
    }

    public String getPrenume()
    {
        return prenume;
    }

    public void setPrenume(String prenume)
    {
        this.prenume = prenume;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getNumarTelefon()
    {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon)
    {
        this.numarTelefon = numarTelefon;
    }

    public String getCNP()
    {
        return CNP;
    }

    public Date getDataNastere()
    {
        return dataNastere;
    }

    public Adresa getAdresa()
    {
        return adresa;
    }
    public void setAdresa(Adresa adresa){ this.adresa = adresa; }

    @Override
    public String toString(){
        String rez = "";
        rez = rez + "\nId client: " + id + "\n";
        rez = rez + "Nume: " + nume + "\n";
        rez = rez + "Prenume: " + prenume + "\n";
        rez = rez + "Numar telefon: " + numarTelefon + "\n";
        rez = rez + "Email: " + email + "\n";
        rez = rez + "Data nasterii: " + dataNastere.toString() + "\n";
        return rez;
    }

    public void readClient(Scanner in) throws ParseException {
        System.out.println("Introduceti datele clientului: ");
        in = new Scanner(System.in);
        System.out.println("Nume: ");
        this.nume = in.nextLine();
        System.out.println("Prenume: ");
        this.prenume = in.nextLine();
        System.out.println("Email: ");
        this.email = in.nextLine();
        System.out.println("Numar de telefon: ");
        this.numarTelefon = in.nextLine();
        System.out.println("CNP: ");
        this.CNP = in.nextLine();
        System.out.println("Data nasterii (yyyy-MM-dd): ");
        this.dataNastere = new SimpleDateFormat("yyyy-MM-dd").parse(in.nextLine());
        this.adresa = new Adresa();
        this.adresa.citire(in);
    }

    public void readFromResultSet(ResultSet in) throws SQLException {
        this.id = in.getInt("id");
        this.nume = in.getString("nume");
        this.prenume = in.getString("prenume");
        this.CNP = in.getString("CNP");
        this.dataNastere = in.getDate("dataNastere");
        this.email = in.getString("email");
        this.numarTelefon = in.getString("numarTelefon");
        this.adresa = new Adresa();
    }


}




