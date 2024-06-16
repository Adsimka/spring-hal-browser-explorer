package com.spring_rest_hal_browser.service;

import com.spring_rest_hal_browser.exception.UserNotFoundException;
import com.spring_rest_hal_browser.model.User;
import com.spring_rest_hal_browser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id" + id));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(Long id, User user) {
        return userRepository.findById(id)
                .map(mbUser -> {
                    mbUser.setFirstname(user.getFirstname());
                    mbUser.setLastname(user.getLastname());
                    mbUser.setBirthDate(user.getBirthDate());

                    return userRepository.saveAndFlush(mbUser);
                }).orElseThrow(() -> new UserNotFoundException("User not found with id" + id));
    }

    public void delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with id" + id);
        }
    }
}
