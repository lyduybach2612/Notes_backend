package com.bach.notes.dtos.requests.notes;

import com.bach.notes.models.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteCreationRequest {

    @NotEmpty(message = "NOT_EMPTY_VALIDATION")
    String title;
    @NotEmpty(message = "NOT_EMPTY_VALIDATION")
    String content;
    User user;

}
