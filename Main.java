import DB.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static Connection getConexiune() {
        try {
            String url = "jdbc:mysql://localhost:3306/pao";
            String user = "root";
            String password = "";

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static void afiseazaServicii() {
        System.out.println("1) Adauga un client");
        System.out.println("2) Sterge un client");
        System.out.println("3) Afiseaza toti clientii");
        System.out.println("4) Adauga un cont de capital social");
        System.out.println("5) Adauga un cont savings");
        System.out.println("6) Afiseaza toate conturile");
        System.out.println("7) Afiseaza sold-ul unui client cu un ID dat");
        System.out.println("8) Creaza un card pentru un client (dupa ID si dupa IBAN-ul unui cont)");
        System.out.println("9) Afiseaza cardurile unui client (dupa ID)");
        System.out.println("10) Fa un transfer");
        System.out.println("11) Afiseaza toate transferurile");
        System.out.println("12) Schimbati numele si prenumele unui client cu un ID dat");
        System.out.println("13) Stergeti un cont dupa ID-ul clientului si IBAN-ul contului");
        System.out.println("14) Stergeti tranzactii sursaIBAN -> destIBAN");
        System.out.println("15) Schimba moneda tranzactiilor sursaIBAN -> destIBAN in EURO");
        System.out.println("0) OPRIRE");
    }

    public static void main(String[] args) {

        boolean end = false;
        Connection connection = Main.getConexiune();
        TransferDB transferDB = new TransferDB(connection);
        ContCapitalSocialDB contCapitalSocialDB = new ContCapitalSocialDB(connection);
        ContSavingsDB contSavingsDB = new ContSavingsDB(connection);
        ClientDB clientDB = new ClientDB(connection);
        CardDB cardDB = new CardDB(connection);
        Service service = new Service(transferDB, contCapitalSocialDB, contSavingsDB, clientDB, cardDB);


        Scanner in = new Scanner(System.in);
        while (!end) {
            afiseazaServicii();
            System.out.println("Alege serviciul: ");
            int nr_serviciu = -1;
            int id;
            String IBAN;
            try {

                service.setConturiCapitalSocial(contCapitalSocialDB.read());
                service.setClienti(clientDB.read());
                service.setTransferuri(transferDB.read());
                service.setConturiSavings(contSavingsDB.read());

                nr_serviciu = in.nextInt();
                switch (nr_serviciu) {
                    case 1 -> service.adaugaClient(in);
                    case 2 -> service.stergeClient(in);
                    case 3 -> service.afiseazaClienti();
                    case 4 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        service.adaugaContCapitalSocial(id, in);
                    }
                    case 5 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        service.adaugaContSavings(id, in);
                    }
                    case 6 -> {
                        service.afiseazaConturi();
                    }
                    case 7 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        System.out.println(service.getSoldTotalInRON(id));
                    }
                    case 8 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        in = new Scanner(System.in);
                        System.out.println("Introduceti IBAN-ul contului: ");
                        IBAN = in.nextLine();
                        service.creazaCardClient(id, IBAN);
                    }
                    case 9 -> {
                        System.out.println("Introduceti ID client: ");
                        id = in.nextInt();
                        service.afiseazaCarduriDupaIdClient(id);
                    }
                    case 10 -> {
                        service.mapCont();
                        service.creazaTransfer();
                    }
                    case 11 -> {
                        service.mapCont();
                        service.afiseazaTransferuri();
                    }
                    case 12 -> {
                        service.schimbaNumePrenumeClient(in);
                    }
                    case 13 -> {
                        service.stergeContDupaIdSiIBAN();
                    }
                    case 14 -> {
                        service.stergeTransfer();
                    }
                    case 15 -> {
                        service.schimbaMonedaTransfer();
                    }
                    case 0 -> end = true;
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}

