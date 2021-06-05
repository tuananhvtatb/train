package com.edu.hutech.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.edu.hutech.dtos.TrainerDto;
import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Role;
import com.edu.hutech.entities.Trainer;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.repositories.TraineeRepository;
import com.edu.hutech.repositories.TrainerRepository;
import com.edu.hutech.services.core.TrainerService;
import com.edu.hutech.services.core.UserService;
import com.edu.hutech.services.implementation.RoleService;
import com.edu.hutech.utils.excel.ExcelExporter;
import com.edu.hutech.utils.page.Pagination;

import com.edu.hutech.utils.sort.GenericComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/trainer-management")
public class TrainerController {

    @Autowired
    TrainerService trainerService;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    /**
     * Playing trainer list
     * @param model
     * @return trainer-list view
     */
    @GetMapping()
    public String displayTrainerList(Model model, @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("field") Optional<String> field) {


        int cPage = page.orElse(1);
        int pageSize =size.orElse(5);
        String sortField = field.orElse("default");

        pageSize = pageSize < 5 ? 5 : pageSize > 500 ? 500 : pageSize;

        List<Trainer> trainers = trainerRepository.findAll();
        model.addAttribute("trainers", trainers);




        if (sortField.contains("-asc")) {
            String[] splits = sortField.split("-asc", 2);
            Collections.sort(trainers, new GenericComparator(true, splits[0]));
        } else {
            if (sortField.equals("default")) {
            } else {
                Collections.sort(trainers, new GenericComparator(false, sortField));
            }
        }

        List<Trainer> trainersAfterPaging = Pagination.getPage(trainers, cPage, pageSize);

        int currIndex = 0;
        if(trainers.size() > 0){
            currIndex = trainers.indexOf(trainersAfterPaging.get(0));
        }
        int totalPages = (int) Math.ceil( (double)trainers.size()/ (double) pageSize);
        int totalElements = trainers.size();

        model.addAttribute("trainers", trainersAfterPaging);
        model.addAttribute("cPage", cPage);
        model.addAttribute("currIndex", currIndex);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements",totalElements);
        model.addAttribute("size",pageSize);
        model.addAttribute("field",sortField);


        PaginationRange p = Pagination.paginationByRange(cPage, totalElements, pageSize, 5);
        model.addAttribute("paginationRange", p);

        return "pages/trainer-views/trainer-management";
    }

    @GetMapping("/add-trainer")
    public String addView(final ModelMap model, final HttpServletRequest request){
        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if(messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Thêm mới thành công." +
                    "</div>");
        }
        model.addAttribute("trainer", new Trainer());
        return "pages/trainer-views/trainer-create-new";
    }

    @PostMapping("/add-trainer")
    public String add(final ModelMap model,
                      @ModelAttribute("trainer") Trainer trainer) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        trainer.getUser().setPassword(encoder.encode(trainer.getUser().getPassword()));

        String account = trainer.getEmail();
        trainer.getUser().setAccount(account.substring(0, account.indexOf("@")));

        trainer.getUser().setRoles(roleService.findByName("ROLE_TRAINER"));
        trainerService.save(trainer);
        model.addAttribute("trainer", new Trainer());
        return "redirect:/trainer-management/add-trainer?message=success";
    }

}
