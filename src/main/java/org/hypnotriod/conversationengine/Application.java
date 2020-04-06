package org.hypnotriod.conversationengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.hypnotriod.conversationengine.engine.ConversationEngine;
import org.hypnotriod.conversationengine.engine.entity.ReplyVariant;
import org.hypnotriod.conversationengine.engine.vo.CommandHandlerResult;
import org.hypnotriod.conversationengine.engine.vo.UtteranceCommandHandlerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Ilya Pikin
 */
@ComponentScan
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ConversationEngine conversationEngine;

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
            UtteranceCommandHandlerResult handlerResult = conversationEngine.processUtterance(line);
            if (handlerResult.getResult() == CommandHandlerResult.REJECT) {
                System.out.println("Sorry?");
            } else {
                String languageCode = handlerResult.getUtteranceRecognitionResult().getLanguageCode();
                ReplyVariant replyVariant = handlerResult.getDialogReply().getSpokenReply(languageCode);
                if (replyVariant != null) {
                    System.err.println(replyVariant.getText());
                }
            }
        }
    }
}
