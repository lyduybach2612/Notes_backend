package com.bach.notes.services;

import com.bach.notes.dtos.requests.NoteCreationRequest;
import com.bach.notes.dtos.requests.NoteUpdateRequest;
import com.bach.notes.dtos.responses.notes.NoteResponse;
import com.bach.notes.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INoteService {

    Page<NoteResponse> getNotes(Pageable pageable);
    NoteResponse getNote(Long id);
    NoteResponse createNote(NoteCreationRequest note);
    void deleteNote(Long id);
    Page<NoteResponse> getNotesByTitle(String title, Pageable pageable);
    NoteResponse updateNote(Long id, NoteUpdateRequest request);

}
