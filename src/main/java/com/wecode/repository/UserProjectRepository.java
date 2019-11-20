package com.wecode.repository;

import com.wecode.entity.Project;
import com.wecode.entity.UserProject;
import com.wecode.entity.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject,UserProjectId> {

   /* @Modifying
    @Transactional
    @Query(value = "insert into user_project values(:ismanager,:uid, :pid)", nativeQuery = true)
    void saveUP(@Param("uid") Long uid, @Param("pid") Long pid, @Param("ismanager") boolean ismanager );
*/
    @Query(value = "select * from user_project where project_id = :id ;", nativeQuery = true)
    List<UserProject> findAllByProjectId( @Param("id") Long id);
}
