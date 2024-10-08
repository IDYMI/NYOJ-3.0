package top.hcode.hoj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.hcode.hoj.pojo.entity.user.UserPreferences;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
@Repository
public interface UserPreferencesMapper extends BaseMapper<UserPreferences> {

}
