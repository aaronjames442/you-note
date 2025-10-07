package com.notes_app.dao;

import com.notes_app.model.Note;

import java.util.List;

public interface NoteDao {
    List<Note> getAllNotes();
    Note getNoteById(int noteId);
    Note createNote(Note note);
    boolean updateNote(Note note);
    boolean deleteNoteById(int id);
}
