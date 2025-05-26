package com.enote.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enote.dto.NotesDto;
import com.enote.exception.ResourceNotFoundException;
import com.enote.service.NotesService;
import com.enote.util.CommonUtil;

@RestController()
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService noteService;
	
	@Autowired
	private NotesDto notesDto;
	
	@PostMapping("/")	
	public ResponseEntity<?> saveNotes(@RequestBody NotesDto noteDto) throws ResourceNotFoundException {
		Boolean savedNotes = noteService.saveNotes(noteDto);
		
		if(savedNotes) {
			return CommonUtil.createBuildResponseMessage("Notes saved successfullly", HttpStatus.CREATED);
		} 
		
		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getNotes() {
		List<NotesDto> allNotes = noteService.getAllNotes();
		
		if(CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		} 
		
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}
	
	
}
