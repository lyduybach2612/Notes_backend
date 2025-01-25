package com.bach.notes.repositories;

import com.bach.notes.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findAllByTitleContaining(String title, Pageable pageable);

}
