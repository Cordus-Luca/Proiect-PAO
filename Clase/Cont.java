package Clase;

import java.util.*;

public class Cont {

    protected String IBAN;
    protected int sold;
    protected String moneda;
    protected int idClient;

    private List<Card> carduri = new ArrayList<>();

    public Cont(String IBAN, int sold, String moneda, int idClient){
        this.IBAN = IBAN;
        this.sold = sold;
        this.moneda = moneda;
        this.idClient = idClient;
    }

    public Cont(int idClient){
        this.idClient = idClient;
    }
    public Cont(){}

    public List<Transfer> getTransferuriFacute(List<Transfer> lista){
        List<Transfer> transferuri = new ArrayList<>();
        for(Transfer Transfer : lista)
            if(Transfer.getSursaIBAN().equals(this.IBAN))
                transferuri.add(Transfer);
        return transferuri;
    }

    public List<Transfer> getTransferuriPrimite(List<Transfer> lista){
        List<Transfer> transferuri = new ArrayList<>();
        for(Transfer Transfer : lista)
            if(Transfer.getDestIBAN().equals(this.IBAN))
                transferuri.add(Transfer);
        return transferuri;
    }

    public List<Transfer> getTransferuriFacuteDupaZi(List<Transfer> lista, int an, int luna, int zi){
        List<Transfer> transferuri = new ArrayList<>();
        for(Transfer Transfer : lista) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Transfer.getDate());

            int _an = calendar.get(Calendar.YEAR);
            int _luna = calendar.get(Calendar.MONTH);
            int _zi = calendar.get(Calendar.DAY_OF_MONTH);

            if(Transfer.getSursaIBAN().equals(this.IBAN) && an == _an && luna == _luna && zi == _zi)
                transferuri.add(Transfer);
        }
        return transferuri;
    }

    public List<Transfer> getTransferuriPrimiteDupaZi(List<Transfer> lista, int an, int luna, int zi){
        List<Transfer> transferuri = new ArrayList<>();
        for(Transfer Transfer : lista) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Transfer.getDate());

            int _an = calendar.get(Calendar.YEAR);
            int _luna = calendar.get(Calendar.MONTH);
            int _zi = calendar.get(Calendar.DAY_OF_MONTH);

            if(Transfer.getDestIBAN().equals(this.IBAN) && an == _an && luna == _luna && zi == _zi)
                transferuri.add(Transfer);
        }
        return transferuri;
    }

    public void read(Scanner in) {
        System.out.println("Introduceti datele contului:");
        in = new Scanner(System.in);
        System.out.println("IBAN: ");
        this.IBAN = in.nextLine();
        System.out.println("Sold: ");
        this.sold = Integer.parseInt(in.nextLine());
        System.out.println("Moneda: ");
        this.moneda = in.nextLine();
    }

    public void adaugaCard(Card card) {
        this.carduri.add(card);
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public int getSold() {
        return sold;
    }

    public void setsold(int sold) {
        this.sold = sold;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public List<Card> getCarduri() {
        return carduri;
    }

    public void setCarduri(List<Card> carduri) {
        this.carduri = carduri;
    }

    public String toString() {
        String rez = "";
        rez = rez + "IBAN: " + IBAN + "\n";
        rez = rez + "Sold: " + sold + "\n";
        rez = rez + "Moneda: " + moneda + "\n";
        rez = rez + "Id client: " + idClient;
        return rez;
    }

}
