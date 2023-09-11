package com.springsecurity.jwt.repository;

import com.springsecurity.jwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD함수를 JpaRepository 가 들고있음
//@Repository라는 어노테이션 없어도 IoC가 된다 이유는 jparepository를 상속 했기 때문에
public interface UserRepository extends JpaRepository<Users,Integer> {
    //findBy 규칙 -> Username 문법
    //select*from user where username = ?
    public Users findByUsername(String username); //jpa query method 검색하면 자세히 볼 수 있음


}
