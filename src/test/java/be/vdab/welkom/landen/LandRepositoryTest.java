package be.vdab.welkom.landen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@PropertySource("application.properties")
@Import(LandRepository.class)
public class LandRepositoryTest {
    private final LandRepository landRepository;
    private final String pad;

    public LandRepositoryTest(LandRepository landRepository,
                              @Value("${landenCsvPad}") String pad) {
        this.landRepository = landRepository;
        this.pad = pad;
    }

    @Test
    void erZijnEvenveelLandenAlsHetAantalRegelsInHetBestand() throws IOException {
        try (var stream = Files.lines(Path.of(pad))) {
            assertThat(landRepository.findAll().size()).isEqualTo(stream.count());
        }
    }

    @Test
    void ehetEersteLandBevatDeDataUitDeEersteRegel() throws IOException {
        try (var stream = Files.lines(Path.of(pad))) {
            stream.findFirst().ifPresent(eersteRegel -> {
                var eersteLand = landRepository.findAll().get(0);
                assertThat(eersteLand.getCode() + "," + eersteLand.getNaam() +
                        "," + eersteLand.getOppervlakte()).isEqualTo(eersteRegel);
            });
        }
    }
}
