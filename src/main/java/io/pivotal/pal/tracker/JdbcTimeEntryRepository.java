package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private JdbcTemplate database;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.database = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            String sql = "INSERT INTO time_entries (project_id,user_id,date,hours) VALUES (?, ?, ?, ?)";
            database.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setLong(1, timeEntry.getProjectId());
                        ps.setLong(2, timeEntry.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                        ps.setInt(4, timeEntry.getHours());
                        return ps;
                    }, keyHolder);

            return new TimeEntry(
                    keyHolder.getKey().longValue(),
                    timeEntry.getProjectId(),
                    timeEntry.getUserId(),
                    timeEntry.getDate(),
                    timeEntry.getHours()
            );
        } catch (Exception e) {return null;}
    }
    @Override
    public TimeEntry find(long timeEntryId) {
        try {
            TimeEntry returnEntry = database.queryForObject("Select * from time_entries where id = ?", new Object[]{timeEntryId}, (rs, rowNum) ->
                new TimeEntry(
                        rs.getLong("id"),
                        rs.getLong("project_id"),
                        rs.getLong("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("hours"))
            );
            return returnEntry;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public List<TimeEntry> list() {

        try {
            String sql = "SELECT id, project_id, user_id, date, hours FROM time_entries";

            return database.query(sql, (rs, rowNum) ->
                    new TimeEntry(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getLong(3),
                            rs.getDate(4).toLocalDate(),
                            rs.getInt(5)
                    )
            );
        } catch (Exception e) {return null;}
    }

    @Override
    public TimeEntry update(long KeyId, TimeEntry timeEntry) {
        try {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            String sql = "UPDATE time_entries SET project_id=?,user_id=?,date=?,hours=? WHERE id=?";
            database.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setLong(1, timeEntry.getProjectId());
                        ps.setLong(2, timeEntry.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                        ps.setInt(4, timeEntry.getHours());
                        ps.setLong(5, KeyId);
                        return ps;
                    }, keyHolder);

            return new TimeEntry(
                    KeyId,
                    timeEntry.getProjectId(),
                    timeEntry.getUserId(),
                    timeEntry.getDate(),
                    timeEntry.getHours()
            );
        } catch (Exception e) {return null;}
    }

    @Override
    public void delete(long timeEntryId) {
        try {
            String sql = "DELETE FROM time_entries WHERE id=?";
            database.update(sql, timeEntryId);
        } catch (Exception e) {
        }
    }
}
