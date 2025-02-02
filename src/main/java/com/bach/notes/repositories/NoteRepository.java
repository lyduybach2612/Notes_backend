package com.bach.notes.repositories;

import com.bach.notes.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findAllByUserIdAndTitleContaining(Long user_id, String title, Pageable pageable);
    Page<Note> findAllByUserId(Long user_id, Pageable pageable);

}
