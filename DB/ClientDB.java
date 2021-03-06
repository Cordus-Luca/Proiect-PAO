package DB;

import Clase.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class ClientDB {
    Connection conexiune;

    public ClientDB(Connection conexiune) {
        this.conexiune = conexiune;
    }

    public List<Client> read(){
        List<Client> clients = new ArrayList<>();
        try{
            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("select * from client");
            while(result.next()) {
                Client client = new Client();
                client.readFromResultSet(result);
                clients.add(client);
            }
            ((Statement) statement).close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return clients;
    }

    public void create(Client client){
        try{
            String query = "insert into client (id, nume, prenume, CNP, dataNastere, email, numarTelefon) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setInt(1, client.getId());
            preparedStmt.setString(2, client.getNume());
            preparedStmt.setString(3, client.getPrenume());
            preparedStmt.setString(4, client.getCNP());
            preparedStmt.setString(5, (new SimpleDateFormat("yyyy-MM-dd")).format(client.getDataNastere()));
            preparedStmt.setString(6, client.getEmail());
            preparedStmt.setString(7, client.getNumarTelefon());
            preparedStmt.execute();
            preparedStmt.close();
            System.out.println("Adaugat la baza de date");
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(Client client){
        try{
            String query = "delete from client where id = ?";
            PreparedStatement preparedStmt = conexiune.prepareStatement(query);
            preparedStmt.setInt(1, client.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void update(Client client, String nume, String prenume) {
        try {
            String query = "update client set nume = ? where id = ?";
            PreparedStatement statement = conexiune.prepareStatement(query);
            statement.setString(1, nume);
            statement.setInt(2, client.getId());

            String query1 = "update client set prenume = ? where id = ?";
            PreparedStatement statement1 = conexiune.prepareStatement(query1);
            statement1.setString(1, prenume);
            statement1.setInt(2, client.getId());

            statement.execute();
            statement1.execute();
            statement.close();
            statement1.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
