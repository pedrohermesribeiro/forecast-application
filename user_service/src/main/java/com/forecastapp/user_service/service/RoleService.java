package com.forecastapp.user_service.service;

import com.forecastapp.user_service.model.Role;
import com.forecastapp.user_service.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Criar uma nova role
    public Role createRole(Role role) {
        // Validação simples: garantir que o nome não seja nulo ou vazio
        if (role.getName() == null || role.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da role é obrigatório");
        }
        // Normalmente o nome vem como "ROLE_USER", "ROLE_ADMIN", etc.
        // String normalizedName = role.getName().toUpperCase();
        // if (!normalizedName.startsWith("ROLE_")) {
        //     normalizedName = "ROLE_" + normalizedName;
        // }
        // role.setName(normalizedName);

        // Verifica se já existe (evita duplicidade por unique constraint)
        // Optional<Role> existing = roleRepository.findByName(normalizedName);
        // if (existing.isPresent()) {
        //     return existing.get();
        // }

        return roleRepository.save(role);
    }

    // Listar todas as roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Buscar role por ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Buscar role por nome (útil para atribuição em usuários)
    public Optional<Role> getRoleByName(String name) {
        String normalizedName = name.toUpperCase();
        if (!normalizedName.startsWith("ROLE_")) {
            normalizedName = "ROLE_" + normalizedName;
        }
        return roleRepository.findByName(normalizedName);
    }

    // Atualizar role
    public Optional<Role> updateRole(Long id, Role updatedRole) {
        return roleRepository.findById(id).map(role -> {
            if (updatedRole.getName() != null && !updatedRole.getName().trim().isEmpty()) {
                String normalizedName = updatedRole.getName().toUpperCase();
                if (!normalizedName.startsWith("ROLE_")) {
                    normalizedName = "ROLE_" + normalizedName;
                }
                role.setName(normalizedName);
            }
            return roleRepository.save(role);
        });
    }

    // Deletar role
    public void deleteRole(Long id) {
        // Atenção: se houver usuários com essa role, pode dar erro de constraint.
        // Em produção, considere desativar ou tratar com soft delete.
        roleRepository.deleteById(id);
    }
}