package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.UserDO;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<UserDO> {

}
