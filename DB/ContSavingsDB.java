package DB;

import Clase.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContSavingsDB {

    Connection conexiune;

    public ContSavingsDB(Connection conexiune) {
        this.conexiune = conexiune;
    }

    public List<ContSavings> read(){
        List<ContSavings> conturiSavings = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("select * from contsavings");

            while(result.next()) {
                ContSavings cont = new ContSavings(result);
                Statement statement1 = conexiune.createStatement();
                String query = "select idClient, CVV, numar, EXP from card where idClient = " + cont.getIdClient();
                ResultSet result_carduri = statement1.executeQuery(query);

                List<Card> carduri = new ArrayList<>();

                while(result_carduri.next()){
                    Card card = new Card(result_carduri);
                    carduri.add(card);
                }

                cont.setCarduri(carduri);
                conturiSavings.add(cont);
                statement1.close();
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return conturiSavings;
    }

    public void create(ContSavings conturiSavings){
        try{
            String query = "insert into contsavings (IBAN, sold, moneda, idClient, dobanda) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, conturiSavings.getIBAN());
            preparedStmt.setInt(2, conturiSavings.getSold());
            preparedStmt.setString(3, conturiSavings.getMoneda());
            preparedStmt.setInt(4, conturiSavings.getIdClient());
            preparedStmt.setInt(5, conturiSavings.getDobanda());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(ContSavings contSavings){
        try{
            String query = "delete from contsavings where IBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, contSavings.getIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void update(Cont cont) {
        try {
            String query = "update contsavings set sold = ? where IBAN = ?";
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
