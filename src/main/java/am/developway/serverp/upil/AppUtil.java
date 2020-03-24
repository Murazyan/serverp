package am.developway.serverp.upil;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintWriter;

@Component
public class AppUtil {


    public void runCommand(String command) {
        String[] processName = {"bash",};
        Process process;
        try {
            process = Runtime.getRuntime().exec(processName);
            PrintWriter stdin = new PrintWriter(process.getOutputStream());
            stdin.println(command);
            InputStream inputStream = process.getInputStream();
            int i;
            char c;
            while((i = inputStream.read())!=-1) {

                // converts integer to character
                c = (char)i;

                // prints character
                System.out.print(c);
            }
            stdin.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }
}
