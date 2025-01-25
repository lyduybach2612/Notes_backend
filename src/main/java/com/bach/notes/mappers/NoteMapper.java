package com.bach.notes.mappers;

import com.bach.notes.dtos.requests.NoteCreationRequest;
import com.bach.notes.dtos.requests.NoteUpdateRequest;
import com.bach.notes.dtos.responses.notes.NoteResponse;
import com.bach.notes.models.Note;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponse toNoteResponse(Note note);
    Note toNote(NoteCreationRequest noteCreationRequest);
    void updateNote(NoteUpdateRequest noteUpdateRequest, @MappingTarget Note note);

}
