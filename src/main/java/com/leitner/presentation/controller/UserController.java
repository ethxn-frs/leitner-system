package com.leitner.presentation.controller;

import com.leitner.application.service.UserService;
import com.leitner.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Gestion des utilisateurs")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Créer un utilisateur", description = "Ajoute un nouvel utilisateur à la base de données.", responses = {@ApiResponse(responseCode = "201", description = "Utilisateur créé", content = @Content(schema = @Schema(implementation = User.class))), @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @Operation(summary = "Récupérer tous les utilisateurs", description = "Retourne une liste de tous les utilisateurs enregistrés.", responses = {@ApiResponse(responseCode = "200", description = "Liste des utilisateurs", content = @Content(schema = @Schema(implementation = User.class)))})
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Mettre à jour un utilisateur", description = "Modifie les informations d'un utilisateur spécifique.", responses = {@ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"), @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur de la base de données.", responses = {@ApiResponse(responseCode = "204", description = "Utilisateur supprimé"), @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Connexion utilisateur", description = "Vérifie les identifiants d'un utilisateur et renvoie un token JWT.", responses = {@ApiResponse(responseCode = "200", description = "Connexion réussie, token retourné"), @ApiResponse(responseCode = "401", description = "Identifiants invalides")})
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String token = userService.authenticate(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(token);
    }
}
