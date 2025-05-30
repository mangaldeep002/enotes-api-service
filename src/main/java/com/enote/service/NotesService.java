package com.enote.service;

import com.enote.dto.NotesDto;
import com.enote.entity.FileDetails;
import com.enote.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface NotesService {
	
	public Boolean saveNotes(String note, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();
	
	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;
}
