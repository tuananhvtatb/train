package com.edu.hutech.controllers;

import java.util.Optional;

import com.edu.hutech.dtos.AjaxResponse;
import com.edu.hutech.dtos.CourseDto;
import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.TraineeCourse;
import com.edu.hutech.functiondto.CourseSearchDto;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.repositories.TraineeRepository;


import com.edu.hutech.services.core.TrainerService;
import com.edu.hutech.services.implementation.CourseServiceImpl;
import com.edu.hutech.services.implementation.TraineeCourseService;
import com.edu.hutech.services.implementation.TraineeServiceImpl;
import com.edu.hutech.utils.page.Pagination;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/class-management")
public class
ClassController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    TrainerService trainerService;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    TraineeServiceImpl traineeService;

    @Autowired
    TraineeCourseService traineeCourseService;

    @Autowired
    private CourseServiceImpl courseService;

    /**
     * Handing the request representing classes infor
     *
     * @param model
     * @param page  is page number in paging
     * @param size  is the quantity of element in a page
     * @param field is the field that user want to sorted by
     * @return class-management.html
     */
    @GetMapping()
    public String displayCourseList(Model model, @RequestParam("page") Optional<Integer> page,
                                    @RequestParam(value = "size") Optional<Integer> size, @RequestParam("field") Optional<String> field,
                                    @RequestParam(value = "search") Optional<String> search) {

        int cPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String sortField = field.orElse("default");
        String searchX = search.orElse(null);

        Page<CourseDto> classPage;
        CourseSearchDto x = new CourseSearchDto();
        x.setPageIndex(cPage);
        x.setPageSize(pageSize);
        x.setText(searchX);

        classPage = courseService.searchByDto(x);


        model.addAttribute("classPage", classPage);
        model.addAttribute("cPage", cPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("field", sortField);


        PaginationRange p = Pagination.paginationByRange(cPage, classPage.getTotalElements(), pageSize, 5);
        model.addAttribute("paginationRange", p);

        return "pages/class-views/class-management";
    }

    @GetMapping("/add-class")
    public String addClass(final ModelMap model, final HttpServletRequest request) {
        model.addAttribute("trainers", trainerService.getAll());

        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if (messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Thêm mới thành công." +
                    "</div>");
        }
        model.addAttribute("classes", new Course());
        return "pages/class-views/class-create-new";
    }

    @PostMapping("/add-class")
    public String add(final ModelMap model,
                      @ModelAttribute("classes") Course course,
                      @ModelAttribute("openDate") String openDate,
                      @ModelAttribute("endDate") String endDate) {
        courseService.save(course);
        model.addAttribute("classes", new Course());
        return "redirect:/class-management/add-class?message=success";
    }

    @GetMapping("/class-details")
    public String detail(@RequestParam("id") Integer id,
                         final ModelMap model) {
        Course course = courseService.findById(id);
        model.addAttribute("class", course);
//        model.addAttribute("trainee", new Trainee());

        return "pages/class-views/class-details";
    }

    @PostMapping("/add-trainee")
    public ResponseEntity<AjaxResponse> addTrainee(@RequestBody String account,
                                                   final HttpServletRequest request){
        JSONObject json = new JSONObject(account);

        if(traineeService.getTraineeByAccount(json.get("account").toString()) == null){
            return ResponseEntity.ok(new AjaxResponse(401, null));
        }

        Trainee trainee = traineeService.getTraineeByAccount(json.get("account").toString());

        if(traineeCourseService.checkExistTrainee(json.getInt("classId"), trainee.getId())){
            return ResponseEntity.ok(new AjaxResponse(402, null));
        }



        Course courseInDB = courseService.findById(json.getInt("classId"));
        TraineeCourse traineeCourse = new TraineeCourse();


        traineeCourse.setTrainee(trainee);

        courseInDB.addTraineeCourses(traineeCourse);
        courseService.save(courseInDB);
        return ResponseEntity.ok(new AjaxResponse(200, account));
    }
}
