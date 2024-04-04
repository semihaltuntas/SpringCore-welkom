package be.vdab.welkom;

import be.vdab.welkom.landen.LandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    private final LandRepository repository;

    public MyRunner(LandRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        try {
            repository.findAll()
                    .forEach(land -> System.out.println(land.getNaam()));
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
