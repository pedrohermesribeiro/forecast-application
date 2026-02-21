package com.forecastapp.user_service.controller;

import com.forecastapp.user_service.model.Role;
import com.forecastapp.user_service.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = {"http://localhost:8085", "https://forecast-frontend-gm1d.onrender.com"})
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Criar nova role
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        System.out.println("📩 PARÂMETRO CRIAÇÃO DE ROLE: " + role.getName());
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    // Listar todas as roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // Buscar role por ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar role por nome (ex.: /roles/search?name=ADMIN)
    @GetMapping("/search")
    public ResponseEntity<Role> getRoleByName(@RequestParam("name") String name) {
        System.out.println("📩 BUSCA DE ROLE POR NOME: " + name);
        return roleService.getRoleByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar role
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role updatedRole) {
        return roleService.updateRole(id, updatedRole)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
