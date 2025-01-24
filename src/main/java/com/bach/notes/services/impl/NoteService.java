package com.bach.notes.services.impl;

import com.bach.notes.repositories.NoteRepository;
import com.bach.notes.services.INoteService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NoteService implements INoteService {

    NoteRepository noteRepository;

}
