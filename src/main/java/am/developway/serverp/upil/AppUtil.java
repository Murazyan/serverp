package am.developway.serverp.upil;

import org.springframework.stereotype.Component;

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
            stdin.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }
}
