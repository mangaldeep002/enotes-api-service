package com.enote.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enote.dto.NotesDto;
import com.enote.entity.FileDetails;
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
	public ResponseEntity<?> saveNotes(@RequestParam String note, 
			@RequestParam(required = false) MultipartFile file) throws Exception {
		
		Boolean savedNotes = noteService.saveNotes(note, file);
		
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
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable("id") Integer id) throws Exception {
		
		FileDetails fileDetails = noteService.getFileDetails(id);
		byte[] data =  noteService.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
	
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	
}
