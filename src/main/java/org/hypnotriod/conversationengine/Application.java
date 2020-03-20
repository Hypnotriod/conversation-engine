package org.hypnotriod.conversationengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.hypnotriod.conversationengine.engine.processor.UtteranceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Ilya Pikin
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    UtteranceProcessor utteranceProcessor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println();
        System.out.println("----------------------------");
        System.out.println("Ready. What is on your mind?");

        while (true) {
            String line = reader.readLine();
            if (line.equals("exit")) {
                return;
            }
            utteranceProcessor.process(line);
        }
    }
}
