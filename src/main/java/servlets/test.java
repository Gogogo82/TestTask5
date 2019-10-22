package servlets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        String create_date = "31-12-2019";
        Calendar calendar = new GregorianCalendar(
                Integer.parseInt(create_date.substring(6)),
                        Integer.parseInt(create_date.substring(3, 5)) - 1,
                                Integer.parseInt(create_date.substring(0, 2))
        ); //22-09-2019

//        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(create_date.substring(0, 1)));
//        calendar.set(Calendar.MONTH, Integer.parseInt(create_date.substring(3, 4)));
//        calendar.set(Calendar.YEAR, Integer.parseInt(create_date.substring(6)));

        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.YEAR));

        Date date = new Date(calendar.getTimeInMillis());
        System.out.println(date);

        Calendar calendar2 = new GregorianCalendar();
        calendar2.setTimeInMillis(date.getTime());
        System.out.println(calendar2);
        System.out.println((calendar2.MONTH + 1));
        String dateForSQL = calendar2.get(Calendar.YEAR) + "-0" + (calendar2.MONTH + 1) + "-0" + calendar2.DAY_OF_MONTH;
        dateForSQL = dateForSQL.replaceAll("-00", "-0");
        System.out.println(dateForSQL);
    }
}
