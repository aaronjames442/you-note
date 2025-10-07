package com.notes_app.controller;

import com.notes_app.dao.UserDao;
import com.notes_app.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/users")
@CrossOrigin // allow your Vue app to call these endpoints (tweak as needed)
public class UserController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------- READ ALL ----------
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers(); // make sure your DAO method name matches
        // Never leak password hashes
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    // ---------- READ BY ID ----------
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    // ---------- CREATE ----------
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Basic validation (you can replace with @Valid + annotations later)
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User created = userDao.createUser(user);
        // Hide password in response
        if (created != null) {
            created.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // ---------- UPDATE (PUT replaces fields you send) ----------
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable int id, @RequestBody User user) {
        // Ensure path id is the source of truth
        user.setId(id);

        // Only hash password if caller sent one
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // If password wasn't provided, keep existing password (fetch and copy)
            User existing = userDao.getUserById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            user.setPassword(existing.getPassword());
        }

        boolean updated = userDao.updateUser(user);
        return ResponseEntity.ok(updated);
    }

    // ---------- DELETE ----------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable int id) {
        // Optional: forbid deleting yourself unless ADMIN — handle in security if needed
        boolean deleted = userDao.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }
}
