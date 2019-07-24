package de.ditti4.restapiissuemanager.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeveloperController {
    @Autowired
    private DeveloperRepository repository;

    Logger logger = LoggerFactory.getLogger(DeveloperController.class);

    @GetMapping("/developers")
    public List<Developer> getAllDevelopers() {
        return repository.findAll();
    }

    @GetMapping("/developers/{id}")
    public Developer getDeveloperById(@PathVariable long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    @PostMapping("/developers")
    public Developer addDeveloper(@RequestBody Developer developer) {
        return repository.save(developer);
    }

    @PutMapping("/developers/{id}")
    public Developer updateDeveloper(@RequestBody Developer newDeveloper, @PathVariable long id) {
        return repository.findById(id)
                .map(developer -> {
                    developer.setName(newDeveloper.getName());
                    return repository.save(developer);
                })
                .orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    @DeleteMapping("/developers/{id}")
    public ResponseEntity<Object> deleteDeveloperById(@PathVariable long id) {
        return repository.findById(id)
                .map(developer -> {
                    repository.delete(developer);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
