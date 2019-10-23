package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry newEntry = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(newEntry,HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(name = "id") long timeEntryId) {
        TimeEntry newEntry = timeEntryRepository.find(timeEntryId);
        if (newEntry == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(newEntry, HttpStatus.OK);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return new ResponseEntity(timeEntryList, HttpStatus.OK);
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable(name = "id") long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updatedEntry = timeEntryRepository.update(timeEntryId, expected);
        if (updatedEntry == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedEntry,HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
