package top.arieslee.myblog.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.arieslee.myblog.modal.VO.AttachVo;
import top.arieslee.myblog.modal.VO.AttachVoExample;
import java.util.List;

@Component
public interface AttachVoDao {
    long countByExample(AttachVoExample example);

    int deleteByExample(AttachVoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AttachVo record);

    int insertSelective(AttachVo record);

    List<AttachVo> selectByExample(AttachVoExample example);

    AttachVo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    int updateByExample(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    int updateByPrimaryKeySelective(AttachVo record);

    int updateByPrimaryKey(AttachVo record);
}