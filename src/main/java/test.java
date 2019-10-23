import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class test {
    public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException {
        String create_date = "31-12-2019";
        Calendar calendar = new GregorianCalendar(
                Integer.parseInt(create_date.substring(6)),
                        Integer.parseInt(create_date.substring(3, 5)) - 1,
                                Integer.parseInt(create_date.substring(0, 2))
        ); //22-09-2019



//        InputStream inStream = new FileInputStream("C:\\Users\\User\\IdeaProjects\\TestTask5\\web\\DBparams.txt");

//        URL location = Class.forName("com.Singleton").getProtectionDomain().getCodeSource().getLocation();
//        int n = location.getPath().lastIndexOf("WEB-INF");
//        web_location = location.getPath().substring(0, n);
//        web_location = web_location.replaceAll("%20", " ");

        String projectRoot = test.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        projectRoot = projectRoot.substring(0, projectRoot.lastIndexOf("TestTask5") + "TestTask5".length());
        projectRoot = projectRoot.replaceAll("%20", " ");
        projectRoot = projectRoot.replaceAll("/", "\\\\");
        projectRoot = projectRoot.replaceFirst("\\\\", "");
        String path = projectRoot + "\\web\\DBparams.txt";
String pathToParams = "/C:/Users/User/IdeaProjects/TestTask5/out/artifacts/TestTask5_war_exploded/DBparams.txt";

        if (pathToParams.matches("/[a-zA-Z]:.*"))
        pathToParams = pathToParams.replaceFirst("/", "");
        System.out.println(pathToParams);

//        String[] params = Files.lines(Paths.get(path)).toArray(String[]::new);
//        System.out.println(params[0]);
//        System.out.println(params[1]);
//        System.out.println(params[2]);


//        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(create_date.substring(0, 1)));
//        calendar.set(Calendar.MONTH, Integer.parseInt(create_date.substring(3, 4)));
//        calendar.set(Calendar.YEAR, Integer.parseInt(create_date.substring(6)));
//
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
//        System.out.println(calendar.get(Calendar.MONTH));
//        System.out.println(calendar.get(Calendar.YEAR));
//
//        Date date = new Date(calendar.getTimeInMillis());
//        System.out.println(date);
//
//        Calendar calendar2 = new GregorianCalendar();
//        calendar2.setTimeInMillis(date.getTime());
//        System.out.println(calendar2);
//        System.out.println((calendar2.MONTH + 1));
//        String dateForSQL = calendar2.get(Calendar.YEAR) + "-0" + (calendar2.MONTH + 1) + "-0" + calendar2.DAY_OF_MONTH;
//        dateForSQL = dateForSQL.replaceAll("-00", "-0");
//        System.out.println(dateForSQL);
    }
}
