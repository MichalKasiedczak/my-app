package com.myapp.myapp.view;

import com.myapp.myapp.service.UserService;
import com.myapp.myapp.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/view/")
public class UserViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ModelAndView displayUsersTable(){
        ModelAndView mv = new ModelAndView("admin/users_table");

        List<UserDto> users = userService.getAllUsers();
        mv.addObject("users", users);

        return mv;
    }


}
