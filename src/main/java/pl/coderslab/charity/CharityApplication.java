package pl.coderslab.charity;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CharityApplication {
    public static final boolean SEND_MAIL = false;
    private static final Logger logger = Logger.getLogger(CharityApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(CharityApplication.class, args);

    }

    @PostConstruct
    private void init() {
        if (logger.isInfoEnabled()) {
            logger.info("Application start");
        }
    }

}
