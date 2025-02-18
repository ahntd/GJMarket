package kr.ac.hansung.cse.gjmarekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GjMarektApplication {

    public static void main(String[] args) {
        SpringApplication.run(GjMarektApplication.class, args);
    }

}
