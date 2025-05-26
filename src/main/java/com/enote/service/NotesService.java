package com.enote.service;

import com.enote.dto.NotesDto;
import com.enote.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

public interface NotesService {
	
	public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;
	
	public List<NotesDto> getAllNotes();
}
