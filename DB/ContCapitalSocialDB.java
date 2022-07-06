package DB;

import Clase.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContCapitalSocialDB {

    Connection conexiune;

    public ContCapitalSocialDB(Connection conexiune)
    {
        this.conexiune = conexiune;
    }

    public List<ContCapitalSocial> read(){
        List<ContCapitalSocial> conturiCapitalSocial = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet rezultat = statement.executeQuery("select * from contcapitalsocial");

            while(rezultat.next()) {
                ContCapitalSocial cont = new ContCapitalSocial(rezultat);
                Statement statement1 = conexiune.createStatement();
                String query = "select idClient, CVV, numar, EXP from card where idClient = " + cont.getIdClient();
                ResultSet result_carduri = statement1.executeQuery(query);

                List<Card> carduri = new ArrayList<>();

                while(result_carduri.next()){
                    Card card = new Card(result_carduri);
                    carduri.add(card);
                }

                cont.setCarduri(carduri);
                conturiCapitalSocial.add(cont);
                statement1.close();
            }
            statement.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return conturiCapitalSocial;
    }

    public void create(ContCapitalSocial conturiCapitalSocial){
        try{
            String query = "insert into contcapitalsocial (IBAN, sold, moneda, idClient, sumaNecesara) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, conturiCapitalSocial.getIBAN());
            preparedStmt.setInt(2, conturiCapitalSocial.getSold());
            preparedStmt.setString(3, conturiCapitalSocial.getMoneda());
            preparedStmt.setInt(4, conturiCapitalSocial.getIdClient());
            preparedStmt.setInt(5, conturiCapitalSocial.getSumaNecesara());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(ContCapitalSocial contCapitalSocial){
        try{
            String query = "delete from contcapitalsocial where IBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, contCapitalSocial.getIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void update(Cont cont) {
        try {
            String query = "update contcapitalsocial set sold = ? where IBAN = ?";
            PreparedStatement statement = conexiune.prepareStatement(query);
            statement.setInt(1, cont.getSold());
            statement.setString(2, cont.getIBAN());

            statement.execute();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
