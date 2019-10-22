package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private long timeEntryID = 1L;
    private HashMap<Long,TimeEntry> timeEntryList = new HashMap<>();

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(timeEntryID);
        timeEntryList.put(timeEntryID, timeEntry);
        timeEntryID++;
        return timeEntry;
    }

    public TimeEntry find(long id) {
       return timeEntryList.get(id);
    }

    public List<TimeEntry> list() {
        System.out.println(timeEntryList.values());
        return new ArrayList<>(timeEntryList.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (timeEntryList.get(id) == null) {
            return null;
        }
        timeEntry.setId(id);
        timeEntryList.put(id, timeEntry);
        return timeEntryList.get(id);
    }

    public void delete(long id) {
        timeEntryList.remove(id);

    }

}
