package Clase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class Card {

    private int idClient;
    private int CVV;
    private String numar;
    private Date EXP;

    public Card(){}

    public Card(int CVV, String numar, Date EXP) {
        this.idClient = idClient;
        this.CVV = CVV;
        this.numar = numar;
        this.EXP = EXP;
    }

    public Card(ResultSet in) throws SQLException {
        this.idClient = in.getInt("idClient");
        this.CVV = in.getInt("CVV");
        this.numar = in.getString("numar");
        this.EXP = in.getDate("EXP");
    }
    public void setIdClient(int idClient)
    {
        this.idClient = idClient;
    }

    public int getIdClient()
    {
        return idClient;
    }

    public String getNumar()
    {
        return numar;
    }

    public void setNumar(String numar)
    {
        this.numar = numar;
    }

    public int getCVV()
    {
        return CVV;
    }

    public void setCVV(int CVV)
    {
        this.CVV = CVV;
    }

    public Date getEXP()
    {
        return EXP;
    }

    public void setEXP(Date EXP)
    {
        this.EXP = EXP;
    }

    @Override
    public String toString() {
        String rez = "";
        rez = rez + "CVV: " + this.CVV;
        rez = rez + "\n";
        rez = rez + "Numar Card: " + this.numar;
        rez = rez + "\n";
        rez = rez + "Data expirare: ";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.EXP);
        rez = rez + (calendar.get(Calendar.DAY_OF_MONTH)) + "-" + (calendar.get(Calendar.MONTH)) + "-" + (calendar.get(Calendar.YEAR)) + "\n";
        return rez;
    }

}

