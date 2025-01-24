package com.bach.notes.models;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.aspectj.weaver.ast.Not;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstname;
    String lastname;
    @Column(unique=true)
    String email;
    String username;
    String password;
    @CreationTimestamp
    LocalDateTime created_at;
    @UpdateTimestamp
    LocalDateTime updated_at;
    @OneToMany(mappedBy = "user")
    ArrayList<Note> notes;

}
