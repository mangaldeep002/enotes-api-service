package com.enote.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enote.dto.NotesDto;
import com.enote.entity.FileDetails;
import com.enote.entity.Notes;
import com.enote.exception.ResourceNotFoundException;
import com.enote.repositry.CategoryRepositry;
import com.enote.repositry.FileRepositry;
import com.enote.repositry.NotesRepositry;
import com.enote.service.NotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService {
		
	@Autowired
	private NotesRepositry notesRepo;
	
	@Autowired
	private CategoryRepositry categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private FileRepositry fileRepo;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Override
	public Boolean saveNotes(String note, MultipartFile file) throws Exception {
		
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(note, NotesDto.class);
		
		// Category Validation
		checkCategoryExist(notesDto.getCategory().getId());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDetails = saveFileDetails(file);
		
		if (!ObjectUtils.isEmpty(fileDetails)) {
			notes.setFileDetails(fileDetails);
		} else {
			notes.setFileDetails(null);
		}
		
		Notes savedNote = notesRepo.save(notes);
		
		if(!ObjectUtils.isEmpty(savedNote)) {
			return true;
		}
		
		return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws Exception {
		
		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			
			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);
			
			List<String> allowedTypeList = Arrays.asList("pdf", "xlsx", "jpg");
			
			if(!allowedTypeList.contains(extension)) {
				throw new IllegalArgumentException("Invalid file formal!");
			}
			
			String randomId = UUID.randomUUID().toString();
			
			String uploadFileName = randomId + "." + extension;
			
			File saveFile = new File(uploadPath);
			if (!saveFile.exists()) {
				saveFile.mkdir();
			}
			
			String storePath = uploadPath.concat(uploadFileName);
			
			// upload file
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			
			if (upload != 0) {
				FileDetails fileDtls = new FileDetails();
				fileDtls.setUploadFileName(uploadFileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setOriginalFileName(originalFileName);
				fileDtls.setDisplayFileName(getDisplayName(originalFileName));
				fileDtls.setPath(storePath);
				
				FileDetails savedFileDetails = fileRepo.save(fileDtls);
				
				return savedFileDetails;
			}
		}
		
		return null;
	}

	private String getDisplayName(String originalFileName) {
		
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);
		
		if(fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		
		fileName = fileName + "." + extension;
		return fileName;
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}
	
	private void checkCategoryExist(Integer id) throws ResourceNotFoundException {
		boolean existsById = categoryRepo.existsById(id);
		
		if (!existsById) {
			throw new ResourceNotFoundException("Category Id is invalid");
		}
		
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		
		InputStream io = new FileInputStream(fileDetails.getPath());
		
		byte[] copyToByteArray = StreamUtils.copyToByteArray(io);
		
		return copyToByteArray;
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		
		FileDetails fileDetails = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("File Not found"));
		
		return fileDetails;
	}

}
