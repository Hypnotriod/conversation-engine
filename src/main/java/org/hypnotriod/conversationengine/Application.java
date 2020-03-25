package org.hypnotriod.conversationengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.hypnotriod.conversationengine.engine.ConversationEngine;
import org.hypnotriod.conversationengine.engine.vo.CommandHandlerResult;
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
    private ConversationEngine conversationEngine;

    @Autowired
    CommandsInitializer commandsInitializer;

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
            CommandHandlerResult result = conversationEngine.processUtterance(line).getResult();
            if (result == CommandHandlerResult.UNHANDLED) {
                System.out.println("Command was unhandled");
            } else if (result != CommandHandlerResult.SUCCEED) {
                System.out.println("Command not recognized");
            }
        }
    }
}
