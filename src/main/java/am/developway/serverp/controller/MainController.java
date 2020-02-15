package am.developway.serverp.controller;

import am.developway.serverp.service.ProtobufService;


import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void downloadPBFile(HttpServletResponse httpResponse) {
        protobufService.downloadPBFile(httpResponse);
    }
}
