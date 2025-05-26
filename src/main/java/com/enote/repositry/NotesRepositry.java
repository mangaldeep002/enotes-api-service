package com.enote.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enote.entity.Notes;

@Repository
public interface NotesRepositry extends JpaRepository<Notes, Integer> {

}
