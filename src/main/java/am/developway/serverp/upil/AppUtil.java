package am.developway.serverp.upil;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
//            int i;
//            char c;
//            while((i = inputStream.read())!=-1) {
//
//                // converts integer to character
//                c = (char)i;
//
//                // prints character
//                System.out.print(c);
//            }
            stdin.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }


    public void createZipFile(List<String> files, String zipFilePath) {
        String fileName = "data.zip";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(zipFilePath + fileName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (String srcFile : files) {
                File fileToZip = new File(zipFilePath  + srcFile);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            zipOut.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
