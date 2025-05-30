package com.enote.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enote.entity.FileDetails;

@Repository
public interface FileRepositry extends JpaRepository<FileDetails, Integer> {

}
