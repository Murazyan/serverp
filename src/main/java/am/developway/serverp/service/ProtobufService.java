package am.developway.serverp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ProtobufService {

    void downloadPBFile(HttpServletResponse httpResponse);

    ResponseEntity uploadVideoAndTrain(MultipartFile multipartFile);

}
