package am.developway.serverp.service.impl;

import am.developway.serverp.service.ProtobufService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

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

        String filePath = protobufPath+protobufFileName;
        File protobuf = new File(filePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(protobuf);
            IOUtils.copy(fileInputStream,response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
