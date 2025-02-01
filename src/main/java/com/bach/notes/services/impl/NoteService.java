package com.bach.notes.services.impl;

import com.bach.notes.dtos.requests.notes.NoteCreationRequest;
import com.bach.notes.dtos.requests.notes.NoteUpdateRequest;
import com.bach.notes.dtos.responses.notes.NoteResponse;
import com.bach.notes.exceptions.AppException;
import com.bach.notes.exceptions.ErrorCode;
import com.bach.notes.mappers.NoteMapper;
import com.bach.notes.models.Note;
import com.bach.notes.models.User;
import com.bach.notes.repositories.NoteRepository;
import com.bach.notes.repositories.UserRepository;
import com.bach.notes.services.INoteService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NoteService implements INoteService {

    NoteRepository noteRepository;
    UserRepository userRepository;
    NoteMapper noteMapper;

    @Override
    public Page<NoteResponse> getNotes(Pageable pageable) {

        Page<Note> notes = noteRepository.findAll(pageable);
        return notes.map(noteMapper::toNoteResponse);

    }

    @Override
    public NoteResponse getNote(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() ->new AppException(ErrorCode.NOTE_NOT_FOUND));
        return noteMapper.toNoteResponse(note);
    }

    @Override
    @Transactional
    public NoteResponse createNote(NoteCreationRequest request) {

        User user = userRepository.findById(1L).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        request.setUser(user);
        Note note = noteRepository.save(noteMapper.toNote(request));
        return noteMapper.toNoteResponse(note);

    }

    @Override
    public void deleteNote(Long id) {

        Note note = noteRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOTE_NOT_FOUND));
        noteRepository.delete(note);

    }

    @Override
    public Page<NoteResponse> getNotesByTitle(String title, Pageable pageable) {

        Page<Note> notes = noteRepository.findAllByTitleContaining(title, pageable);
        return notes.map(noteMapper::toNoteResponse);

    }

    @Override
    public NoteResponse updateNote(Long id, NoteUpdateRequest request) {

        User user = userRepository.findById(1L).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Note note = noteRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOTE_NOT_FOUND));
        request.setUser(user);
        noteMapper.updateNote(request, note);
        return noteMapper.toNoteResponse(noteRepository.save(note));

    }
}
