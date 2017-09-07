package lab.mvc;

import lab.model.User;
import lab.model.simple.SimpleUser;
import lab.mvc.form.bean.UserFormBean;
import lab.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/adduser.form")
@Log4j2
@AllArgsConstructor
@SuppressWarnings("SpellCheckingInspection")
public class UserFormController {

    private UserService userService;

    @ModelAttribute("userFormBean")
    public UserFormBean getUserFormBean() {
        return new UserFormBean();
    }

    @GetMapping
    public String get() {
        return "/adduserform.jsp";
    }

    @PostMapping
    public ModelAndView processSubmit(@Valid UserFormBean userFormBean, Errors errors) {

        if (errors.hasErrors()) {
            log.info("Adduserform validation failed.");
            return new ModelAndView("/adduserform.jsp");
        }

        User simpleUser = new SimpleUser(
                userFormBean.getFirstName(),
                userFormBean.getLastName());

        log.info("Adding new {}", simpleUser);

        userService.saveUser(simpleUser);

        return new ModelAndView("/userlistview.jsp", "userList", userService.loadAllUsers());
    }
}