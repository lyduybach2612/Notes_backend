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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Page<Note> notes = noteRepository.findAllByUserId(user.getId(), pageable);
        return notes.map(noteMapper::toNoteResponse);

    }

    @Override
    @PostAuthorize("returnObject.getUser().username == authentication.name")
    public NoteResponse getNote(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() ->new AppException(ErrorCode.NOTE_NOT_FOUND));
        if (!note.getUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new AppException(ErrorCode.NOTE_NOT_BELONG);
        }
        return noteMapper.toNoteResponse(note);
    }

    @Override
    @Transactional
    public NoteResponse createNote(NoteCreationRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_FOUND));
        request.setUser(user);
        Note note = noteRepository.save(noteMapper.toNote(request));
        return noteMapper.toNoteResponse(note);

    }

    @Override
    @PreAuthorize("@noteRepository.findById(#id).get().getUser().getUsername() == authentication.name")
    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOTE_NOT_FOUND));
        noteRepository.delete(note);

    }

    @Override
    public Page<NoteResponse> getNotesByTitle(String title, Pageable pageable) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_FOUND));
        Page<Note> notes = noteRepository.findAllByUserIdAndTitleContaining(user.getId(), title, pageable);
        return notes.map(noteMapper::toNoteResponse);

    }

    @Override
    @PostAuthorize("returnObject.getUser().username == authentication.name")
    public NoteResponse updateNote(Long id, NoteUpdateRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Note note = noteRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOTE_NOT_FOUND));
        request.setUser(user);
        noteMapper.updateNote(request, note);
        return noteMapper.toNoteResponse(noteRepository.save(note));

    }
}
