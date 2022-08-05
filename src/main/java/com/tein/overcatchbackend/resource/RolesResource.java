package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.RoleDTO;
import com.tein.overcatchbackend.domain.model.Roles;
import com.tein.overcatchbackend.mapper.RoleMapper;
import com.tein.overcatchbackend.repository.RolesRepository;
import com.tein.overcatchbackend.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class RolesResource {


    private final RolesRepository rolesRepository;
    private final RolesService rolesService;
    private final RoleMapper roleMapper;

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public Roles saveRoles(@RequestBody RoleDTO roleDTO) {
        Roles roles = roleMapper.toEntity(roleDTO);
        return rolesService.saveRoles(roles);
    }
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @RequestMapping(value = "/allroles", method = RequestMethod.GET)
    public List<Roles> allRoles() {
        return rolesRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ANONYMOUS')" )
    @RequestMapping(value = "/allroless", method = RequestMethod.GET)
    public List<Roles> allRoless() {
        return rolesRepository.findAll();
    }
}
