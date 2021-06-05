package com.edu.hutech.services.implementation;

import com.edu.hutech.entities.TraineeCourse;
import com.edu.hutech.repositories.TraineeCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeCourseService {

    @Autowired
    TraineeCourseRepository traineeCourseRepository;

    public Double getScoreByTraineeId(Integer id){
        TraineeCourse traineeCourse = traineeCourseRepository.findAvgScoreByTraineeId(id);

        if(traineeCourse != null){
            return traineeCourse.getScore();
        }
        return (double) 0;
    }

    public boolean checkExistTrainee(Integer courseId, Integer traineeId){
        return traineeCourseRepository.getTraineeCourseByTraineeIdAndCourseId(courseId, traineeId) != null;
    }
}
