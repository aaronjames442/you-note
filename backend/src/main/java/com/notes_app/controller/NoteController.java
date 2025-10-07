package com.notes_app.controller;

import com.notes_app.dao.NoteDao;
import com.notes_app.dao.UserDao;
import com.notes_app.model.Note;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteDao noteDao;
    private final UserDao userDao;


    public NoteController(NoteDao noteDao, UserDao userDao) {
        this.noteDao = noteDao;
        this.userDao = userDao;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Note> getAllNotes() {
        return noteDao.getAllNotes();
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable int id) {
        return noteDao.getNoteById(id);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Note createNote(@RequestBody Note note, Authentication authentication) {
        // Get username of the logged-in user
        String username = authentication.getName();

        // Find the userId from your UserDao
        int userId = userDao.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        // Set userId before saving
        note.setUserId(userId);
        note.setCreatedAt(LocalDate.now());
        note.setUpdatedAt(LocalDate.now());

        return noteDao.createNote(note);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public boolean updateNote(@PathVariable int id, @RequestBody Note note) {
        note.setId(id);
        return noteDao.updateNote(note);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteNote(@PathVariable int id) {
        return noteDao.deleteNoteById(id);
    }



}
