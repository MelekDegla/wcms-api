package com.wecode.repository;

import com.wecode.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface ProjectRepository  extends JpaRepository<Project, Long> {

}
