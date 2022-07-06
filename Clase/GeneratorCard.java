package Clase;

import java.util.Calendar;
import java.util.Random;
import java.util.Date;

public class GeneratorCard {

    public static int genereazaCVV(){
        Random rand = new Random();
        int cvv = rand.nextInt(900) + 100;
        return cvv;
    }

    public static String genereazaNumar(){
        Random rand = new Random();
        String id_1 = String.format("%04d", rand.nextInt(10000));
        String id_2 = String.format("%04d", rand.nextInt(10000));
        String id_3 = String.format("%04d", rand.nextInt(10000));
        String id_4 = String.format("%04d", rand.nextInt(10000));
        return id_1 + " " + id_2 + " " + id_3 + " " + id_4;
    }

    public static Date genereazaEXP(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        year = year + 6;
        calendar.set(Calendar.YEAR, year);

        Date data = calendar.getTime();
        return data;
    }

    public static Card genereazaCard() {
        int CVV = genereazaCVV();
        String numar = genereazaNumar();
        Date EXP = genereazaEXP();
        return new Card(CVV, numar, EXP);
    }
}