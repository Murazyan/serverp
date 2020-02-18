package am.developway.serverp.service.impl;

import am.developway.serverp.dto.ServerResponse;
import am.developway.serverp.service.ProtobufService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

@Service
public class ProtobufServiceImpl implements ProtobufService {


    @Value("${protobuf.file.name}")
    public String protobufFileName;

    @Value("${protobuf.file.path}")
    public String protobufPath;


    @Override
    public void downloadPBFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename="
                + protobufFileName);

        String filePath = protobufPath + protobufFileName;
        File protobuf = new File(filePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(protobuf);
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity trainImage(MultipartFile multipartFile) {
        String[] command = {"cmd",};
        HttpStatus status = HttpStatus.OK;
        ServerResponse responseBody ;
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            PrintWriter stdin = new PrintWriter(process.getOutputStream());
            stdin.println("\"" + protobufPath + "\" \"" + input_file + "\" \"" + output_file + "\" -l eng");
            stdin.close();
            process.waitFor();
            responseBody = ServerResponse.builder()
                    .message("Ok")
                    .success(true)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody= ServerResponse.builder()
                    .message("Something went wrong!")
                    .build();

        }
        return ResponseEntity.status(status).body(responseBody);
    }


}
