package pl.coderslab.charity;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CharityApplication {
    public static final boolean SEND_MAIL=false;
    final static Logger logger = Logger.getLogger(CharityApplication.class);

    public static void main(String[] args) {
        if(logger.isInfoEnabled()){
            logger.info("Application start");
        }
        SpringApplication.run(CharityApplication.class, args);

    }

}
