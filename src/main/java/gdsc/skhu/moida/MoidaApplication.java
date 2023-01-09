package gdsc.skhu.moida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MoidaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoidaApplication.class, args);
    }

}
