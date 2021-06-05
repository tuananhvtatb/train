package com.edu.hutech.services.implementation;

import java.util.List;

import com.edu.hutech.dtos.CourseDto;
import com.edu.hutech.entities.Course;
import com.edu.hutech.functiondto.CourseSearchDto;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.services.core.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager manager;

    @Override
    public void save(Course t) {
        courseRepository.save(t);
    }

    @Override
    public void update(Course t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(long theId) {
        // TODO Auto-generated method stub

    }

    @Override
    public Course findById(Integer id) {
        return courseRepository.getOne(id);
    }

    @Override
    public List<Course> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<CourseDto> searchByDto(CourseSearchDto dto) {
        if (dto == null) {
            return null;
        }
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = " where (1=1 ) ";
        String orderBy = " ";
        String sqlCount = "select count(co.id) from Course as co ";
        String sql = "select new com.edu.hutech.dtos.CourseDto(co) from Course as co ";

        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            sqlCount += " JOIN co.trainer as tra";
            sql += " JOIN co.trainer as tra";
            whereClause += " AND (co.name LIKE :text " + "OR tra.name LIKE :text) ";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;
        Query q = manager.createQuery(sql, CourseDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            q.setParameter("text", '%' + dto.getText().trim() + '%');
            qCount.setParameter("text", '%' + dto.getText().trim() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<CourseDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return new PageImpl<>(entities, pageable, count);
    }



}
