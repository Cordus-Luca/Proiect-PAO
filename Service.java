import java.text.ParseException;
import java.util.*;

import Clase.*;
import DB.*;

public class Service {

    private List<Client> clienti = new ArrayList<>();
    private List<ContCapitalSocial> conturiCapitalSocial = new ArrayList<>();
    private List<ContSavings> conturiSavings = new ArrayList<>();
    private List<Transfer> transferuri = new ArrayList<>();
    private HashSet<String> IBANuri = new HashSet<String>();
    private Map<String, Cont> contMap = new HashMap<>();

    private TransferDB transferDB;
    private ContCapitalSocialDB contCapitalSocialDB;
    private ContSavingsDB ContSavingsDB;
    private ClientDB clientDB;
    private CardDB cardDB;

    //public Service(){}

    public Service(TransferDB _transferDB, ContCapitalSocialDB _contCapitalSocialDB, ContSavingsDB _ContSavingsDBB, ClientDB _clientDB, CardDB _cardDB) {
        this.transferDB = _transferDB;
        this.contCapitalSocialDB = _contCapitalSocialDB;
        this.ContSavingsDB = _ContSavingsDBB;
        this.clientDB = _clientDB;
        this.cardDB = _cardDB;
    }

    public List<Client> getClienti()
    {
        return clienti;
    }

    public void setClienti(List<Client> clienti)
    {
        this.clienti = clienti;
    }

    public List<ContCapitalSocial> getConturiCapitalSocial()
    {
        return conturiCapitalSocial;
    }

    public void setConturiCapitalSocial(List<ContCapitalSocial> conturi)
    {
        this.conturiCapitalSocial = conturi;
    }

    public List<ContSavings> getConturiSavings()
    {
        return conturiSavings;
    }

    public void setConturiSavings(List<ContSavings> conturi)
    {
        this.conturiSavings = conturi;
    }

    public List<Transfer> getTransferuri()
    {
        return transferuri;
    }

    public void setTransferuri(List<Transfer> transferuri)
    {
        this.transferuri = transferuri;
    }

    public void adaugaClient(Scanner in) throws ParseException {
        Client c = new Client();
        c.readClient(in);
        clienti.add(c);
        clientDB.create(c);
    }

    public void stergeClient(Scanner in){
        System.out.println("Introduceti ID client");
        int id = in.nextInt();
        boolean ok = false;
        for(Client client : clienti){
            if (client.getId() == id){
                ok = true;
                clientDB.delete(client);
            }

        }
        if(ok == false)
            System.out.println("Clientul cu id-ul dat nu exista\n");
    }

    public void schimbaNumePrenumeClient(Scanner in){
        System.out.println("Introduceti ID client");
        int id = in.nextInt();
        boolean ok = false;
        for(Client client : clienti){
            if(client.getId() == id){
                System.out.println("Introduceti numele nou: ");
                in = new Scanner(System.in);
                String nume_nou = in.nextLine();
                System.out.println("Introduceti prenumele nou: ");
                String prenume_nou = in.nextLine();
                clientDB.update(client, nume_nou, prenume_nou);
                System.out.println("Nume si prenume actualizate");
                ok = true;
            }
        }
        if(ok == false)
            System.out.println("Nu exista niciun client cu id-ul dat");
    }

    public void stergeContDupaIdSiIBAN(){
        Scanner in = new Scanner(System.in);
        System.out.println("Introduceti ID client: ");
        int id = in.nextInt();
        System.out.println("Introduceti IBAN cont: ");
        in = new Scanner(System.in);
        String IBAN = in.nextLine();
        boolean ok = false;
        for (ContSavings cont : conturiSavings)
            if(cont.getIBAN().equals(IBAN) == true && cont.getIdClient() == id){
                IBANuri.remove(cont.getIBAN());
                ContSavingsDB.delete(cont);
                ok = true;
            }
        for (ContCapitalSocial cont : conturiCapitalSocial)
            if(cont.getIBAN().equals(IBAN) == true && cont.getIdClient() == id){
                IBANuri.remove(cont.getIBAN());
                contCapitalSocialDB.delete(cont);
                ok = true;
            }

        if(ok == false){
            System.out.println("Nu s-a gasit un client si un cont cu datele primite\n");
        }else{
            System.out.println("Cont sters!\n");
        }
    }

    public void afiseazaClienti() {
        for (int i = 0; i < clienti.size(); i++) {
            System.out.println(clienti.get(i));

        }
    }

    public void afiseazaConturi() {
        System.out.println("Conturi de nevoi personale [" + conturiCapitalSocial.size() + "]: \n");
        for(ContCapitalSocial cont: conturiCapitalSocial) {
            System.out.println(cont);
        }

        System.out.println("Conturi de economii [" + conturiSavings.size() + "]: \n");
        for(ContSavings cont: conturiSavings) {
            System.out.println(cont);
        }
    }

    public Client getClientDupaId(int id) throws Exception {

        for(Client client : clienti)
            if(client.getId() == id)
                return client;

        throw new Exception("Nu exista niciun client cu id-ul dat\n");
    }

    public void adaugaContCapitalSocial(int idClient, Scanner in) throws Exception {

        int nr = 0;
        for(Client client : clienti)
            if(client.getId() != idClient)
                nr += 1;

        if(nr == clienti.size())
            throw new Exception("Nu exista niciun client cu id-ul dat\n");
        else {
            ContCapitalSocial cont = new ContCapitalSocial(idClient);
            cont.read(in);
            if (IBANuri.contains(cont.getIBAN()) == true) {
                throw new Exception("Nu pot fi mai multe conturi cu acelasi IBAN\n");
            }
            else {
                IBANuri.add(cont.getIBAN());
                this.conturiCapitalSocial.add(cont);
                this.mapCont();
                contCapitalSocialDB.create(cont);
            }
        }
    }

    public void adaugaContSavings(int idClient, Scanner in) throws Exception {

        int nr = 0;
        for(Client client : clienti)
            if(client.getId() != idClient)
                nr += 1;

        if(nr == clienti.size())
            throw new Exception("Nu exista niciun client cu id-ul dat\n");
        else {
            ContSavings cont = new ContSavings(idClient);
            cont.read(in);
            if (IBANuri.contains(cont.getIBAN()) == true) {
                throw new Exception("Nu pot fi mai multe conturi cu acelasi IBAN\n");
            }
            else {
                IBANuri.add(cont.getIBAN());
                this.conturiSavings.add(cont);
                this.mapCont();
                ContSavingsDB.create(cont);
            }
        }
    }

    public int getSoldTotalInRON(int idClient) {
        int rez = 0;
        for(ContCapitalSocial cont : conturiCapitalSocial){
            if (cont.getIdClient() == idClient) {
                if (cont.getMoneda().equals("RON"))
                    rez += cont.getSold();
                else if (cont.getMoneda().equals("EURO"))
                    rez += cont.getSold() * 5;
                else if (cont.getMoneda().equals("DOLAR"))
                    rez += cont.getSold() * 5;
            }
        }

        for(ContSavings cont : conturiSavings) {
            if (cont.getIdClient() == idClient) {
                if (cont.getMoneda().equals("RON"))
                    rez += cont.getSold();
                else if (cont.getMoneda().equals("EURO"))
                    rez += cont.getSold() * 5;
                else if (cont.getMoneda().equals("DOLAR"))
                    rez += cont.getSold() * 5;
            }
        }
        return rez;
    }

    public void creazaCardClient(int idClient, String IBAN) throws Exception {
        boolean ok = false;
        for(ContCapitalSocial cont: conturiCapitalSocial) {
            if (cont.getIdClient() == idClient && cont.getIBAN().equals(IBAN)) {
                Card card = GeneratorCard.genereazaCard();
                card.setIdClient(idClient);
                cardDB.create(card);
                cont.adaugaCard(card);
                ok = true;
            }
        }
        for(ContSavings cont: conturiSavings) {
            if (cont.getIdClient() == idClient && cont.getIBAN().equals(IBAN)) {
                Card card = GeneratorCard.genereazaCard();
                card.setIdClient(idClient);
                cardDB.create(card);
                cont.adaugaCard(card);
                ok = true;
            }
        }
        if(ok == false) {
            throw new Exception("Nu s-a gasit niciun cont cu IBAN-ul respectiv\n");
        }
    }

    public boolean verifica(List<Card> lista, Card card){

        for(Card c : lista){
            if(c.getCVV() == card.getCVV() && c.getNumar().equals(card.getNumar()) == true && c.getEXP().equals(card.getEXP()))
                return true;
        }
        return false;
    }

    public void afiseazaCarduriDupaIdClient(int idClient) {
        List<Card> lista = new ArrayList<>();
        List<Card> carduri = new ArrayList<>();
        for(ContCapitalSocial cont: conturiCapitalSocial) {
            if (cont.getIdClient() == idClient) {
                carduri = cont.getCarduri();
                for(Card card : carduri)
                    if(this.verifica(lista, card) == false)
                        lista.add(card);
            }
        }
        for(ContSavings cont: conturiSavings) {
            if (cont.getIdClient() == idClient) {
                carduri = cont.getCarduri();
                for(Card card : carduri)
                    if(this.verifica(lista, card) == false)
                        lista.add(card);
            }
        }

        for(Card card : lista) {
            System.out.println(card.toString());
        }
    }

    public void mapCont(){
        for(ContCapitalSocial cont : this.conturiCapitalSocial)
            this.contMap.put(cont.getIBAN(), cont);
        for(ContSavings cont : this.conturiSavings)
            this.contMap.put(cont.getIBAN(), cont);
    }

    public void creazaTransfer() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Cont sursa [IBAN]: ");
        String sursaIBAN = in.nextLine();
        System.out.println("Cont destinatie [IBAN]: ");
        String dstIBAN = in.nextLine();
        System.out.println("Suma transferata: ");
        int suma_transferata = in.nextInt();
        Cont cont_sursa = null, cont_destinatie = null;

        if(contMap.containsKey(sursaIBAN))
            cont_sursa = contMap.get(sursaIBAN);
        if(contMap.containsKey(dstIBAN))
            cont_destinatie = contMap.get(dstIBAN);

        if(cont_sursa.getSold() < suma_transferata)
            throw new Exception("Sold insuficient!");

        cont_sursa.setsold(cont_sursa.getSold() - suma_transferata);
        cont_destinatie.setsold(cont_destinatie.getSold() + suma_transferata);

        if(cont_sursa instanceof ContSavings)
            ContSavingsDB.update(cont_sursa);
        else
            contCapitalSocialDB.update(cont_sursa);


        if(cont_destinatie instanceof ContSavings)
            ContSavingsDB.update(cont_destinatie);
        else
            contCapitalSocialDB.update(cont_destinatie);


        Transfer transfer = new Transfer(suma_transferata, cont_sursa.getMoneda(), sursaIBAN, dstIBAN, new Date());
        this.transferuri.add(transfer);
        transferDB.create(transfer);
    }

    public void stergeTransfer(){
        System.out.println("Introduceti sursa IBAN: ");
        Scanner in = new Scanner(System.in);
        String sursa_iban = in.nextLine();
        System.out.println("Introduceti destinatie IBAN: ");
        in = new Scanner(System.in);
        String dest_iban = in.nextLine();
        boolean ok = false;
        for(Transfer transfer : transferuri)
            if(transfer.getSursaIBAN().equals(sursa_iban) == true && transfer.getDestIBAN().equals(dest_iban) == true){
                ok = true;
                transferDB.delete(transfer);
            }
        if(ok == false){
            System.out.println("Nu s-a gasit o transfer cu datele primite");
        }
        else{
            System.out.println("Transferuri sterse\n");
        }
    }

    public void schimbaMonedaTransfer(){
        System.out.println("Introduceti sursa IBAN: ");
        Scanner in = new Scanner(System.in);
        String sursa_iban = in.nextLine();
        System.out.println("Introduceti destinatie IBAN: ");
        in = new Scanner(System.in);
        String dest_iban = in.nextLine();
        boolean ok = false;
        for(Transfer transfer : transferuri)
            if(transfer.getSursaIBAN().equals(sursa_iban) == true && transfer.getDestIBAN().equals(dest_iban) == true){
                transfer.setMoneda("EURO");
                ok = true;
                transferDB.update(transfer);
            }
        if(ok == false){
            System.out.println("Nu s-a gasit o transfer cu datele primite");
        }
        else{
            System.out.println("Transferuri actualizate\n");
        }
    }
    public void afiseazaTransferuri() {
        for(Transfer tran: transferuri) {
            System.out.println(tran);
        }
    }

}
