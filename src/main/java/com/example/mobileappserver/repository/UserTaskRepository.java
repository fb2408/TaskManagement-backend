package com.example.mobileappserver.repository;

import com.example.mobileappserver.model.Usertask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<Usertask, Integer> {

    List<Usertask> findAllByUserId(Integer id);

    @Modifying
    @Query(value= "delete from UserTask e  where e.user_id =:userId and e.task_id =:taskId",nativeQuery=true)
    void deleteByUserIdAndTaskId(@Param("userId") int userId, @Param("taskId") int taskId);
}
