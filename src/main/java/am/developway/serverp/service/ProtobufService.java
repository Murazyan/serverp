package am.developway.serverp.service;

import am.developway.serverp.model.AnimalType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ProtobufService {

    void downloadPBFile(HttpServletResponse httpResponse, AnimalType animalType);

    ResponseEntity uploadVideoAndTrain(MultipartFile multipartFile, AnimalType animalType);

    byte [] downloadZipFile( AnimalType animalType);

}
