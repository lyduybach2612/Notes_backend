package com.bach.notes.services;

import com.bach.notes.dtos.requests.notes.NoteCreationRequest;
import com.bach.notes.dtos.requests.notes.NoteUpdateRequest;
import com.bach.notes.dtos.responses.notes.NoteResponse;
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
