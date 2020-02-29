package am.developway.serverp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintWriter;

@SpringBootApplication
public class ServerpApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ServerpApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] command = {"bash",};

        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            PrintWriter stdin = new PrintWriter(process.getOutputStream());
//            stdin.println("\"" + protobufPath + "\" \"" + input_file + "\" \"" + output_file + "\" -l eng");
            stdin.println("cd /home/developer1/Desktop/tensorflow-for-poets-2 7:38 PM\n" +
                    "(wave)\n" +
                    "2:24 PM\n" +
                    "python -m scripts.retrain \\--bottleneck_dir=tf_files/bottlenecks \\--how_many_training_steps=500 \\--model_dir=tf_files/models/ \\--summaries_dir=tf_files/training_summaries/mobilenet_0.50_224 \\--output_graph=tf_files/retrained_graph.pb \\--output_labels=tf_files/retrained_labels.txt \\--architecture=mobilenet_0.50_224 \\--image_dir=training_data/data");
            stdin.close();
            process.waitFor();
            System.out.println("uraaaaaa");

        } catch (Exception e) {
            e.printStackTrace();


        }
    }
}
