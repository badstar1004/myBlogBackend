package com.example.demo.mapper.user;

import com.example.demo.domain.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    User getUserById(Long userId);

    List<User> getAllUser();

    List<User> getUserPaging(Map<String, Object> pagingMap);

    int deleteUser(Long userId);
}