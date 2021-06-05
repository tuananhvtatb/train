package com.edu.hutech.services.implementation;

import com.edu.hutech.entities.Trainee;
import com.edu.hutech.repositories.TraineeRepository;
import com.edu.hutech.services.core.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    TraineeRepository traineeRepository;

    @Override
    public void save(Trainee trainee) {
        traineeRepository.save(trainee);
    }

    @Override
    public void update(Trainee trainee) {

    }

    @Override
    public void delete(long theId) {

    }

    @Override
    public Trainee findById(Integer id) {
        return traineeRepository.getOne(id);
    }

    @Override
    public List<Trainee> getAll() {
        return traineeRepository.findAll();
    }

    public Trainee getTraineeByAccount(String account){
        if(traineeRepository.getTraineeByAccount(account) != null){
            return traineeRepository.getTraineeByAccount(account);
        }
        return null;
    }



}
