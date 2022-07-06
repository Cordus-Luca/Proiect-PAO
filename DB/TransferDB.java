package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import Clase.*;

public class TransferDB {

    Connection conexiune;

    public TransferDB(Connection conexiune){
        this.conexiune = conexiune;
    }

    public List<Transfer> read(){
        List<Transfer> tranzactii = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("select * from transfer");
            while(result.next()) {
                Transfer transfer = new Transfer(result);
                tranzactii.add(transfer);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return tranzactii;
    }

    public void create(Transfer transfer){
        try{
            String query = "insert into transfer (suma, moneda, sursaIBAN, destIBAN, data) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setDouble(1, transfer.getSuma());
            preparedStmt.setString(2, transfer.getMoneda());
            preparedStmt.setString(3, transfer.getSursaIBAN());
            preparedStmt.setString(4, transfer.getDestIBAN());
            preparedStmt.setString(5, (new SimpleDateFormat("yyyy-MM-dd h:m:s")).format(transfer.getDate()));
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(Transfer transfer){
        try{
            String query = "delete from transfer where sursaIBAN = ? and destIBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, transfer.getSursaIBAN());
            preparedStmt.setString(2, transfer.getDestIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void update(Transfer transfer){
        try{
            String query = "update transfer set moneda = ? where sursaIBAN = ? and destIBAN = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setString(1, transfer.getMoneda());
            preparedStmt.setString(2, transfer.getSursaIBAN());
            preparedStmt.setString(3, transfer.getDestIBAN());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
