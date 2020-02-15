package am.developway.serverp.service;

import javax.servlet.http.HttpServletResponse;

public interface ProtobufService {

    void downloadPBFile(HttpServletResponse httpResponse);
}
