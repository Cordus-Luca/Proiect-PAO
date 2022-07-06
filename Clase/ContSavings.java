package Clase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ContSavings extends Cont {

    private int dobanda;

    public ContSavings(int dobanda, String IBAN, int sold, String moneda, int idClient){
        super(IBAN, sold, moneda, idClient);
        this.dobanda = dobanda;
    }

    public ContSavings(int idClient){
        super(idClient);
    }

    public ContSavings(ResultSet in) throws SQLException {
        this.IBAN = in.getString("IBAN");
        this.sold = in.getInt("sold");
        this.moneda = in.getString("moneda");
        this.idClient = in.getInt("idClient");
        this.dobanda = in.getInt("dobanda");
    }

    public int getDobanda(){
        return dobanda;
    }

    public void setDobanda(int dobanda){
        this.dobanda = dobanda;
    }


    public void read(Scanner in) {
        super.read(in);
        System.out.println("Dobanda: ");
        in = new Scanner(System.in);
        this.dobanda = Integer.parseInt(in.nextLine());
    }

    @Override
    public String toString(){
        String rez = super.toString();
        rez = rez + "\nDobanda: " + dobanda + "\n";
        return rez;
    }

}
