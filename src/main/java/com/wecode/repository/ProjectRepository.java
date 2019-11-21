package com.wecode.repository;

import com.wecode.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProjectRepository  extends JpaRepository<Project, Long> {

    List<Project> findByName(String name);

}
