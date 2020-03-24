package am.developway.serverp.controller;

import am.developway.serverp.dto.ServerResponse;
import am.developway.serverp.model.AnimalType;
import am.developway.serverp.service.ProtobufService;


import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
public class MainController {

    private ProtobufService protobufService;

    public MainController(ProtobufService protobufService) {
        this.protobufService = protobufService;

    }

    /**
     * Download protobuf file
     *
     * @param httpResponse //
     * @created 15.02.2020
     */
    @GetMapping("/pb")
    @ApiOperation("Download protobuf file")
    public void downloadPBFile(HttpServletResponse httpResponse,
                               @RequestParam(name = "animal-type")AnimalType animalType) {
        protobufService.downloadPBFile(httpResponse, animalType);
    }


    /**
     * Upload video and train data
     *
     * @param multipartFile //
     * @return httpStatus 200, body {@link ServerResponse}
     * @created 15.02.2020
     */
    @PostMapping("/video")
    @ApiOperation("Train new image")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ServerResponse.class)})
    public ResponseEntity uploadVideoAndTrain(@RequestParam(name = "animal-type")AnimalType animalType, @RequestPart("file") MultipartFile multipartFile) {
        return protobufService.uploadVideoAndTrain(multipartFile, animalType);
    }



}
