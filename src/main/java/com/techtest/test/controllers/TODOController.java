package com.techtest.test.controllers;

import com.techtest.test.dtos.TODODto;
import com.techtest.test.entities.TODO;
import com.techtest.test.entities.User;
import com.techtest.test.services.TODOService;
import com.techtest.test.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Scope("session")
public class TODOController {

    @Autowired
    private TODOService todoService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/todos";
    }

    @GetMapping("/todos")
    public String todoPage(Model model,
                       HttpServletResponse response,
                       HttpSession session,
                       Pageable pageable,
                       @RequestParam(required = false) String title,
                       @RequestParam(required = false) String username) {
        Page<TODO> todoPage;

        if (title != null) {
            todoPage = todoService.getByTitle(title, pageable);
        }
        else if (username != null) {
            todoPage = todoService.getByUser(username, pageable);
        }
        else {
            todoPage = todoService.getAllTodos(pageable);
        }
        List<TODO> todos = todoPage.getContent();
        int totalPages = todoPage.getTotalPages();
        int currentPage = todoPage.getNumber();
        List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());

        model.addAttribute("todos", todos);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);
        if (!pageable.getSort().isEmpty()) {
            model.addAttribute("sort", pageable.getSort().get().findFirst().get().getProperty());
        }
        else {
            model.addAttribute("sort",null);
        }


        return "home";
    }

    @PostMapping("/todos")
    public String insertTodo(@ModelAttribute("newTodo") TODODto todo, BindingResult bindingResult) {
        TODO t = todoService.createTODO(todo.getTitle(),todo.getUserID(), todo.isCompleted());
        return "redirect:/todos";
        //return ResponseEntity.status(HttpStatus.OK).body(TODOtoDto(t));
    }

    @GetMapping("/todos/new")
    public String createTodo(Model model, HttpServletResponse response, HttpSession session) {
        if (session.getAttribute("user") == null)
            return "register";
        model.addAttribute("newTodo", new TODODto());
        List<User> userList = userService.getAll();
        model.addAttribute("userlist", userList);
        return "newtodo";
    }

    @GetMapping("/todos/{id}/edit")
    public String getEditTodoPage(Model model, HttpSession session, @PathVariable Long id) {
        TODO t = todoService.getByID(id);
        if (t == null) {
            model.addAttribute("error", "Todo not found");
            return "error";
        }
        model.addAttribute("todo", TODOtoDto(t));
        return "edittodo";
    }

    @PostMapping("/todos/{id}")
    public String editTodo(Model model, @ModelAttribute("newTodo") TODODto todo, HttpSession session, @PathVariable Long id) {
        try {
            todoService.updateTODO(todo.getId(), todo.getTitle());
        } catch (Exception e) {
            model.addAttribute("error", "Todo not found");
            return "error";
        }
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(Model model,HttpSession session, @PathVariable Long id) {
        todoService.deleteTODO(id);
        return "redirect:/todos";
    }

    private TODODto TODOtoDto(TODO t) {
        return new TODODto(t);

    }



}
