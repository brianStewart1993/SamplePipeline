package com.ete.repo;

import org.springframework.data.repository.CrudRepository;

import com.ete.models.User;

public interface UserRepository extends CrudRepository<User, Integer>, UserRepositoryCustom {
}
