package com.enote.service.impl;

import java.util.List;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enote.dto.NotesDto;
import com.enote.entity.Notes;
import com.enote.exception.ResourceNotFoundException;
import com.enote.repositry.CategoryRepositry;
import com.enote.repositry.NotesRepositry;
import com.enote.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {
		
	@Autowired
	private NotesRepositry notesRepo;
	
	@Autowired
	private CategoryRepositry categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException {
		
		// Category Validation
		checkCategoryExist(notesDto.getCategory().getId());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes savedNote = notesRepo.save(notes);
		
		if(!ObjectUtils.isEmpty(savedNote)) {
			return true;
		}
		
		return false;
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

}
