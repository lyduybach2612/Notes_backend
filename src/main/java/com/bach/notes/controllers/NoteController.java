package com.bach.notes.controllers;


import com.bach.notes.dtos.requests.notes.NoteCreationRequest;
import com.bach.notes.dtos.requests.notes.NoteUpdateRequest;
import com.bach.notes.dtos.responses.ApiResponse;
import com.bach.notes.dtos.responses.notes.NoteResponse;
import com.bach.notes.services.impl.NoteService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("api/notes")
public class NoteController {

    NoteService noteService;

    @GetMapping
    public ApiResponse<Page<NoteResponse>> getAllNotes(Pageable pageable) {

        if(pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("updatedAt").descending());
        }
        Page<NoteResponse> notes = noteService.getNotes(pageable);
        return ApiResponse.<Page<NoteResponse>>builder()
                .code(1000)
                .result(notes)
                .build();

    }

    @GetMapping("/{noteId}")
    public ApiResponse<NoteResponse> getNoteById(@PathVariable Long noteId) {

        return ApiResponse.<NoteResponse>builder()
                .code(1000)
                .result(noteService.getNote(noteId))
                .build();

    }

    @GetMapping("/search/{title}")
    public ApiResponse<Page<NoteResponse>> getNoteByTitle(@PathVariable String title, Pageable pageable) {

        if(pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("updatedAt").descending());
        }
        return ApiResponse.<Page<NoteResponse>>builder()
                .code(1000)
                .result(noteService.getNotesByTitle(title, pageable))
                .build();
    }

    @PostMapping
    public ApiResponse<NoteResponse> createNote(@RequestBody @Valid NoteCreationRequest note){

        return ApiResponse.<NoteResponse>builder()
                .code(1000)
                .result(noteService.createNote(note))
                .build();

    }

    @PutMapping("/{noteId}")
    public ApiResponse<NoteResponse> updateNote(@PathVariable Long noteId, NoteUpdateRequest request){

        return ApiResponse.<NoteResponse>builder()
                .code(1000)
                .result(noteService.updateNote(noteId, request))
                .build();

    }

    @DeleteMapping("/{noteId}")
    public ApiResponse<String> deleteNote(@PathVariable Long noteId) {

        noteService.deleteNote(noteId);
        return ApiResponse.<String>builder()
                .code(1000)
                .result("Note has been deleted")
                .build();

    }

}
