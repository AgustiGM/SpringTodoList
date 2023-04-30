package com.techtest.test.services;

import com.techtest.test.entities.TODO;
import com.techtest.test.entities.User;
import com.techtest.test.repos.TODORepository;
import com.techtest.test.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class TODOService {

    private final TODORepository todoRepository;

    private final UserRepository userRepository;

    public TODOService(TODORepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public TODO getByID(Long todoID) {
        return todoRepository.findById(todoID).orElse(null);
    }

    @Transactional
    public TODO createTODO(String name, Long userId, boolean completed) {
        User u = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        TODO t = new TODO(name,u);
        t.setCompleted(completed);
        return todoRepository.save(t);
    }

    public Page<TODO> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }


    @Transactional(rollbackOn = Exception.class)
    public void updateTODO(Long id, String title) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TODO t = todoRepository.findById(id).orElseThrow(RuntimeException::new);
        if (t.getUser().getUsername().equals(user.getUsername())) {
            t.setTitle(title);
        }
    }

    public Page<TODO> getByTitle(String title, Pageable pageable) {
        return todoRepository.findByTitleContainsIgnoreCase(title,pageable);
    }

    public Page<TODO> getByUser(String username, Pageable pageable) {
        return todoRepository.findByUser_Username(username,pageable);

    }

    public void deleteTODO(Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TODO t = todoRepository.findById(id).orElseThrow(RuntimeException::new);
        if (t.getUser().getUsername().equals(user.getUsername())) {
            todoRepository.deleteById(id);
        }

    }
}
