package com.wecode.repository;

import com.wecode.entity.UserProject;
import com.wecode.entity.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {

   /* @Modifying
    @Transactional
    @Query(value = "insert into user_project values(:ismanager,:uid, :pid)", nativeQuery = true)
    void saveUP(@Param("uid") Long uid, @Param("pid") Long pid, @Param("ismanager") boolean ismanager );
*/
}
