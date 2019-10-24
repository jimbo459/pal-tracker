package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private JdbcTemplate template;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO time_entries (project_id,user_id,date,hours) VALUES (?, ?, ?, ?)";
        template.update(
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

    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return template.queryForObject("Select * from time_entries where id = ?", new Object[]{timeEntryId}, (rs, rowNum) ->
                new TimeEntry(
                    rs.getLong("id"),
                    rs.getLong("project_id"),
                    rs.getLong("user_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getInt("hours"))
        );
    }

    @Override
    public List<TimeEntry> list() {

        String sql = "SELECT id, project_id, user_id, date, hours FROM time_entries";

        return template.query(sql, (rs, rowNum) ->
                 new TimeEntry(
                    rs.getLong(1),
                    rs.getLong(2),
                    rs.getLong(3),
                    rs.getDate(4).toLocalDate(),
                    rs.getInt(5)
            )
        );
    }

    @Override
    public TimeEntry update(long KeyId, TimeEntry timeEntry) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "UPDATE time_entries SET project_id=?,user_id=?,date=?,hours=? WHERE id=?";
        template.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setLong(1, timeEntry.getProjectId());
                    ps.setLong(2, timeEntry.getUserId());
                    ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                    ps.setInt( 4, timeEntry.getHours());
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
    }

    @Override
    public void delete(long timeEntryId) {

    }
}
