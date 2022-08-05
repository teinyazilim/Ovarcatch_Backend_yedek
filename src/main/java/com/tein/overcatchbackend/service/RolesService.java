package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.Roles;
import com.tein.overcatchbackend.repository.RolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;


    public Roles saveRoles(Roles roles)
    { return rolesRepository.save(roles);
    }


}

