package com.edu.hutech.services.core;

import java.util.List;

public interface IService<T> {

    void save(T t);

    void update(T t);

    void delete(long theId);

    T findById(Integer theId);

    List<T> getAll();

}
