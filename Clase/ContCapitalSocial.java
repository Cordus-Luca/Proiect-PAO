package Clase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ContCapitalSocial extends Cont {

    private int sumaNecesara;

    public ContCapitalSocial(int sumaNecesara, String IBAN, int sold, String moneda, int idClient){
        super(IBAN, sold, moneda, idClient);
        this.sumaNecesara = sumaNecesara;
    }

    public ContCapitalSocial(int idClient){
        super(idClient);
    }

    public ContCapitalSocial(ResultSet in) throws SQLException {
        this.IBAN = in.getString("IBAN");
        this.sold = in.getInt("sold");
        this.moneda = in.getString("moneda");
        this.idClient = in.getInt("idClient");
        this.sumaNecesara = in.getInt("sumaNecesara");
    }

    public int getSumaNecesara(){
        return sumaNecesara;
    }

    public void setSumaNecesara(int sumaNecesara){
        this.sumaNecesara = sumaNecesara;
    }

    public void read(Scanner in) {
        super.read(in);
        System.out.println("Suma Necesara: ");
        in = new Scanner(System.in);
        this.sumaNecesara = Integer.parseInt(in.nextLine());
    }

    @Override
    public String toString(){
        String rez = super.toString();
        rez = rez + "\nSuma Necesara: " + sumaNecesara + "\n";
        return rez;
    }

}
