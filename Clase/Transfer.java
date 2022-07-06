package Clase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Transfer {

    private int suma;
    private String moneda;
    private String sursaIBAN;
    private String destIBAN;
    private Date data;

    public Transfer(int suma, String moneda, String sursaIBAN, String destIBAN, Date data){
        this.suma = suma;
        this.moneda = moneda;
        this.sursaIBAN = sursaIBAN;
        this.destIBAN = destIBAN;
        this.data = data;
    }

    public Transfer(ResultSet in) throws SQLException {
        this.sursaIBAN = in.getString("sursaIBAN");
        this.destIBAN = in.getString("destIBAN");
        this.suma = in.getInt("suma");
        this.data = in.getDate("data");
        this.moneda = in.getString("moneda");
    }

    public int getSuma()
    {
        return suma;
    }

    public void setSuma(int suma)
    {
        this.suma = suma;
    }

    public String getMoneda()
    {
        return moneda;
    }

    public void setMoneda(String moneda)
    {
        this.moneda = moneda;
    }

    public String getSursaIBAN()
    {
        return sursaIBAN;
    }

    public void setSursaIBAN(String sursaIBAN)
    {
        this.sursaIBAN = sursaIBAN;
    }

    public String getDestIBAN()
    {
        return destIBAN;
    }

    public void setDestIBAN(String destIBAN)
    {
        this.destIBAN = destIBAN;
    }

    public Date getDate()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        String rez = "";
        rez = rez + "Suma: " + suma + "\n";
        rez = rez + "Moneda: " + moneda + "\n";
        rez = rez + "Sursa IBAN: " + sursaIBAN + "\n";
        rez = rez + "Destinatie IBAN: " + destIBAN + "\n";
        return rez;
    }

}
