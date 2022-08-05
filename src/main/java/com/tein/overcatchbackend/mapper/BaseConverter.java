package com.tein.overcatchbackend.mapper;

import java.util.List;
import java.util.Set;

public interface BaseConverter<Entity, Dto> {

    Entity toEntity(Dto dto);

    Dto toDto(Entity entity);

    List<Entity> toEntity(List<Dto> dto);

    List<Dto> toDto(List<Entity> entity);

    Set<Entity> toEntity(Set<Dto> dto);

    Set<Dto> toDto(Set<Entity> entity);

}
