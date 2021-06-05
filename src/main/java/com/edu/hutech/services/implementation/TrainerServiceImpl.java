package com.edu.hutech.services.implementation;

import java.util.List;

import com.edu.hutech.entities.Trainer;
import com.edu.hutech.repositories.TrainerRepository;
import com.edu.hutech.repositories.UserRepository;
import com.edu.hutech.services.core.TrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void save(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    @Override
    public void update(Trainer trainer) {

    }

    @Override
    public void delete(long theId) {

    }

    @Override
    public Trainer findById(Integer theId) {
        return null;
    }

    @Override
    public List<Trainer> getAll() {
        return trainerRepository.findAll();
    }
}
