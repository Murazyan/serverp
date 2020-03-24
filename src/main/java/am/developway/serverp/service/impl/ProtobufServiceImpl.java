package am.developway.serverp.service.impl;

import am.developway.serverp.dto.ServerResponse;
import am.developway.serverp.model.AnimalType;
import am.developway.serverp.service.ProtobufService;
import am.developway.serverp.upil.AppUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Service
public class ProtobufServiceImpl implements ProtobufService {


    @Value("${protobuf.file.name}")
    public String protobufFileName;

    @Value("${protobuf.file.path}")
    public String protobufPath;


    @Value("${python.project.path}")
    public String pythonProjectPath;

    @Value("${training.images.folder.path}")
    public String trainingImagesFolderPath;

    private AppUtil appUtil;

    private static String trainFolderPath;

    public ProtobufServiceImpl(AppUtil appUtil){
        this.appUtil = appUtil;

    }


    @Override
    public void downloadPBFile(HttpServletResponse response, AnimalType animalType) {
        response.setHeader("Content-Disposition", "attachment;filename="
                + "data.zip");
        String filePath = protobufPath +animalType.name()+"/"+ animalType.name()+"data.zip";
        File protobuf = new File(filePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(protobuf);
            org.apache.tomcat.util.http.fileupload.IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }




    }



    @Override
    public byte [] downloadZipFile( AnimalType animalType) {
        byte [] result;
        try {
            File zip = new File(protobufPath +animalType.name()+"/"+"data.zip");
            FileInputStream fileInputStream = new FileInputStream(zip);
            result = org.apache.commons.io.IOUtils.toByteArray(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            result=null;
        }
        return result;


    }

    @Override
    public ResponseEntity uploadVideoAndTrain(MultipartFile multipartFile, AnimalType animalType) {
        HttpStatus httpStatus = HttpStatus.OK;
        String videoName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File video = new File(pythonProjectPath+"/videos/" + videoName);
        video.mkdirs();
        try {
            multipartFile.transferTo(video);
            trainFolderPath = trainingImagesFolderPath +"/" +animalType.name()+"/myAnimal";
            File trainFolder =new File(trainFolderPath);
            trainFolder.mkdirs();
            String ffmpegCommang = "cd "+pythonProjectPath+"/videos/ \n ffmpeg -i "+videoName+" -vf fps=1 "+trainFolderPath+"/out%d.jpg";
            appUtil.runCommand(ffmpegCommang);
            String trainCommand = "cd "+pythonProjectPath+" 7:38 PM\n" +
                    "(wave)\n" +
                    "2:24 PM\n" +
                    "python -m scripts.retrain \\--bottleneck_dir=tf_files/bottlenecks \\--how_many_training_steps=500 \\--model_dir=tf_files/models/ \\--summaries_dir=tf_files/training_summaries/mobilenet_0.50_224 \\--output_graph=tf_files"+"/"+animalType.name()+"/retrained_graph.pb \\--output_labels=tf_files"+"/"+animalType.name()+"/retrained_labels.txt \\--architecture=mobilenet_0.50_224 \\--image_dir=training_data/data/"+animalType.name();
            appUtil.runCommand(trainCommand);
            String optimazedCmd="cd " +protobufPath+animalType.name()+"/ \n"+
                    " toco \\--input_file=retrained_graph.pb \\--output_file="+animalType.name()+"_optimized_graph.tflite \\--input_format=TENSORFLOW_GRAPHDEF \\--output_format=TFLITE " +
                    "\\--input_shape=1,224,224,3 " +
                    "\\--input_array=input " +
                    "\\--output_array=final_result " +
                    "\\--inference_type=FLOAT " +
                    "\\--input_data_type=FLOAT";
            appUtil.runCommand(optimazedCmd);
            appUtil.createZipFile(Arrays.asList(animalType.name()+"_optimized_graph.tflite","retrained_labels.txt"),protobufPath+animalType.name()+"/");

        } catch (IOException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return ResponseEntity.status(httpStatus).build();
    }


}
