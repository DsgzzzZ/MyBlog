package top.arieslee.myblog.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.arieslee.myblog.modal.VO.LogVo;
import top.arieslee.myblog.modal.VO.LogVoExample;

import java.util.List;

/**
 * @ClassName LogVoDao
 * @Description 日志表数据访问层
 * @Author Aries
 * @Date 2019/5/3 10:21
 * @Version 1.0
 **/
@Repository
public interface LogVoDao {

    /**
     * @Description 插入一条日志数据
     **/
    int insert(LogVo logVo);

    List<LogVo> selectByExample(LogVoExample example);

    long countByExample(LogVoExample example);

    int deleteByExample(LogVoExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(LogVo record);

    LogVo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogVo record, @Param("example") LogVoExample example);

    int updateByExample(@Param("record") LogVo record, @Param("example") LogVoExample example);

    int updateByPrimaryKeySelective(LogVo record);

    int updateByPrimaryKey(LogVo record);
}
