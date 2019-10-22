package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;
    }

    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        TimeEntry newEntry = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(newEntry,HttpStatus.CREATED);
    }

    public ResponseEntity<TimeEntry> read(long timeEntryId) {
        TimeEntry newEntry = timeEntryRepository.find(timeEntryId);
        if (newEntry == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(newEntry, HttpStatus.OK);
    }


    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return new ResponseEntity(timeEntryList, HttpStatus.OK);
    }


    public ResponseEntity update(long timeEntryId, TimeEntry expected) {
        TimeEntry updatedEntry = timeEntryRepository.update(timeEntryId, expected);
        if (updatedEntry == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedEntry,HttpStatus.OK);
    }


    public ResponseEntity delete(long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
