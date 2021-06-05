package com.edu.hutech.controllers;

import com.edu.hutech.entities.Attendance;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.TraineeCourse;
import com.edu.hutech.entities.Trainer;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.AttendanceRepository;
import com.edu.hutech.repositories.TraineeCourseRepository;
import com.edu.hutech.repositories.TraineeRepository;
import com.edu.hutech.services.core.TraineeService;
import com.edu.hutech.services.implementation.RoleService;
import com.edu.hutech.services.implementation.TraineeCourseService;
import com.edu.hutech.services.implementation.TraineeServiceImpl;
import com.edu.hutech.utils.page.Pagination;
import com.edu.hutech.utils.sort.GenericComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/trainee-management")
public class TraineeController {
    @Autowired
    TraineeRepository traineeRepository;

    @Autowired
    TraineeServiceImpl traineeService;

    @Autowired
    RoleService roleService;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    TraineeCourseService traineeCourseService;


    @GetMapping()
    public String displayTraineeManagement(Model model,
                                           @RequestParam("page") Optional<Integer> page,
                                           @RequestParam("size") Optional<Integer> size,
                                           @RequestParam("field") Optional<String> field,
                                           @RequestParam("view") Optional<String> view,
                                           @RequestParam("search") Optional<String> search) {

        int cPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String sortField = field.orElse("default");
        String keyword = search.orElse("");
        String modeView = view.orElse("list");


        pageSize = pageSize < 5 ? 5 : pageSize > 500 ? 500 : pageSize;

        List<Trainee> listTrainees = traineeRepository.findScoreByAllTrainee();

        if (sortField.contains("-asc")) {
            String[] splits = sortField.split("-asc", 2);
            Collections.sort(listTrainees, new GenericComparator(true, splits[0]));
        } else {
            if (sortField.equals("default")) {
            } else {
                Collections.sort(listTrainees, new GenericComparator(false, sortField));
            }
        }

        List<Trainee> trainees = Pagination.getPage(listTrainees, cPage, pageSize);


        int totalPages = (int) Math.ceil((double) listTrainees.size() / (double) pageSize);

        model.addAttribute("modeView", modeView);
        model.addAttribute("trainees", trainees);
        model.addAttribute("cPage", cPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("totalElements", listTrainees.size());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("field", sortField);
        model.addAttribute("search", keyword);

        PaginationRange p = Pagination.paginationByRange(cPage, listTrainees.size(), pageSize, 5);
        model.addAttribute("paginationRange", p);

        return "pages/trainee-views/trainee-management";
    }

    @GetMapping("/add-trainee")
    public String addView(final ModelMap model, final HttpServletRequest request){
        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if(messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Thêm mới thành công." +
                    "</div>");
        }
        model.addAttribute("trainee", new Trainee());
        return "pages/trainee-views/trainee-create-new";
    }

    @PostMapping("/add-trainee")
    public String add(final ModelMap model,
                      @ModelAttribute("trainee") Trainee trainee) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        trainee.getUser().setPassword(encoder.encode(trainee.getUser().getPassword()));

        String account = trainee.getEmail();

        trainee.getUser().setAccount(account.substring(0, account.indexOf("@")));

        trainee.getUser().setRoles(roleService.findByName("ROLE_TRAINEE"));
        traineeService.save(trainee);
        model.addAttribute("trainer", new Trainer());
        return "redirect:/trainee-management/add-trainee?message=success";
    }

//@GetMapping("/export")
//    public void exportToExcel(HttpServletResponse response,
//                              @RequestParam("page") Optional<Integer> page,
//                              @RequestParam("size") Optional<Integer> size,
//                              @RequestParam("field") Optional<String> field) throws IOException {
//
//        int cPage = page.orElse(1);
//        int pageSize = size.orElse(10);
//        String sortField = field.orElse("default");
//
//
//        pageSize = pageSize < 5 ? 5 : pageSize > 500 ? 500 : pageSize;
//
//        List<TraineeScoreDto> listTrainees = traineeRepository.findScoreByAllTrainee();
//
//        if (sortField.contains("-asc")) {
//            String[] splits = sortField.split("-asc", 2);
//            Collections.sort(listTrainees, new GenericComparator(true, splits[0]));
//        } else {
//            Collections.sort(listTrainees, new GenericComparator(false, sortField));
//        }
//
//        List<TraineeScoreDto> trainees = Pagination.getPage(listTrainees, cPage, pageSize);
//
//        response.setContentType("application/octet-stream");
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=trainee-management_" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//
//
//        List<TraineeDto> traineeDtoList = new ArrayList<>();
//
//        for (TraineeScoreDto tsd : trainees) {
//            traineeDtoList.add(new TraineeDto(tsd.getId(), tsd.getName(), tsd.getAccount(), tsd.getScore(), tsd.getEmail(), tsd.getUniversity()));
//        }
//
//
//        ExcelExporter<TraineeDto> excelExporter = new ExcelExporter<>(traineeDtoList, TraineeDto.class);
//
//        excelExporter.export(response);
//
//    }


    @GetMapping("/trainee-details")
    public String displayAllTraineeDetails(Model model, @RequestParam("id") Integer id) {

        Trainee trainee = traineeService.findById(id);

        Double finalScore = traineeCourseService.getScoreByTraineeId(id);;
//        int presentAttendance = attendanceRepository.findPresentAttendanceByTraineeId(traineeId);
//        int totalAttendance = attendanceRepository.findTotalAttendanceByTraineeId(traineeId);
//        List<TOScoreDto> listNameAndScore = scoreRepository.findScoreEachTOByTraineeId(traineeId);
//        List<Attendance> listDateAndAttendance = attendanceRepository.findAttendanceByTraineeId(traineeId);

        double scale = Math.pow(10, 1);

        model.addAttribute("trainee", trainee);
        model.addAttribute("finalScore", (int) (Math.round(finalScore * scale) / scale) * 10);
//        model.addAttribute("presentAttendance", presentAttendance);
//        model.addAttribute("totalAttendance", totalAttendance);
//        model.addAttribute("listNameAndScore", listNameAndScore);
//        model.addAttribute("listDateAndAttendance", listDateAndAttendance);

        return "pages/trainee-views/trainee-details";
    }

}
