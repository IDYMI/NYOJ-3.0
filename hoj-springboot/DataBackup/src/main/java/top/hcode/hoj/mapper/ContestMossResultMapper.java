package top.hcode.hoj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import top.hcode.hoj.pojo.entity.contest.ContestMossResult;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 */
@Mapper
@Repository
public interface ContestMossResultMapper extends BaseMapper<ContestMossResult> {

}
