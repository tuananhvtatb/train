package com.edu.hutech.repositories;

import com.edu.hutech.entities.TraineeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeCourseRepository extends JpaRepository<TraineeCourse, Integer> {

    @Query(name = "select * from trainee_course tc where tc.trainee_id = ?1", nativeQuery = true)
    TraineeCourse findAvgScoreByTraineeId(Integer id);

    @Query(name = "SELECT * FROM demo.trainee_course where course_id = ?1 and trainee_id = ?2", nativeQuery = true)
    TraineeCourse getTraineeCourseByTraineeIdAndCourseId(Integer courseId, Integer traineeId);
}
