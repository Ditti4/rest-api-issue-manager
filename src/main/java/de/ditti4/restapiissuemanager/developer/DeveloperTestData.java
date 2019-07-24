package de.ditti4.restapiissuemanager.developer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeveloperTestData {
    @Bean
    CommandLineRunner initializeDatabase(DeveloperRepository repository) {
        return args -> {
            repository.save(new Developer("King Benbow"));
            repository.save(new Developer("Nettie Grady"));
            repository.save(new Developer("Raleigh Barranco"));
            repository.save(new Developer("Cordia Buggs"));
            repository.save(new Developer("Marcelino Hilgefort"));
            repository.save(new Developer("Dylan Hossain"));
            repository.save(new Developer("Susie Gee"));
            repository.save(new Developer("Thurman Olmo"));
            repository.save(new Developer("Easter Vandorn"));
            repository.save(new Developer("Lorean Debnam"));
            repository.save(new Developer("Darell Matheson"));
            repository.save(new Developer("Briana Masten"));
            repository.save(new Developer("Tarsha Palacio"));
            repository.save(new Developer("Trinidad Trego"));
            repository.save(new Developer("Cherlyn Minott"));
            repository.save(new Developer("Lupe Ogletree"));
            repository.save(new Developer("Kermit Polson"));
            repository.save(new Developer("Elfrieda Gargano"));
            repository.save(new Developer("Eli Milbourn"));
            repository.save(new Developer("Annett Toups"));
        };
    }
}
