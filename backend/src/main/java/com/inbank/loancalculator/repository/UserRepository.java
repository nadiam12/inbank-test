package com.inbank.loancalculator.repository;

import com.inbank.loancalculator.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

}
