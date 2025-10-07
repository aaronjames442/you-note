package com.notes_app.dao;

import com.notes_app.model.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Repository
public class JdbcNoteDao implements NoteDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNoteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> allNotes = new ArrayList<>();
        String sql = "SELECT * FROM note";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
           allNotes.add(mapRowToNote(result));
        }
        return allNotes;
    }

    @Override
    public Note getNoteById(int id) {
        String sql = "SELECT * FROM note WHERE id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        while (result.next()) {
            return mapRowToNote(result);
        }
        return null;
    }

    @Override
    public Note createNote(Note note) {
        String sql = "INSERT INTO note (user_id, content) VALUES (?,?) RETURNING id";
        int result = jdbcTemplate.queryForObject(sql, Integer.class, note.getUserId(), note.getContent());
        note.setId(result);
        return note;
    }

    @Override
    public boolean updateNote(Note note) {
        String sql = "UPDATE note SET content = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, note.getContent(), note.getId());
        return rowsAffected == 1;
    }

    @Override
    public boolean deleteNoteById(int id) {
        String sql = "DELETE FROM note WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected == 1;
    }

    private Note mapRowToNote(SqlRowSet rowSet) {
        java.sql.Date createdDate = rowSet.getDate("created_at");
        java.sql.Date updatedDate = rowSet.getDate("updated_at");

        return new Note(
                rowSet.getInt("id"),
                rowSet.getInt("user_id"),
                rowSet.getString("content"),
                (createdDate != null) ? createdDate.toLocalDate() : null,
                (updatedDate != null) ? updatedDate.toLocalDate() : null
        );
    }

}
