package top.arieslee.myblog.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.arieslee.myblog.modal.VO.RelationshipVoExample;
import top.arieslee.myblog.modal.VO.RelationshipVoKey;

import java.util.List;

@Component
public interface RelationshipVoDao {
    long countByExample(RelationshipVoExample example);

    int deleteByExample(RelationshipVoExample example);

    int deleteByPrimaryKey(RelationshipVoKey key);

    int insert(RelationshipVoKey record);

    int insertSelective(RelationshipVoKey record);

    List<RelationshipVoKey> selectByExample(RelationshipVoExample example);

    int updateByExampleSelective(@Param("record") RelationshipVoKey record, @Param("example") RelationshipVoExample example);

    int updateByExample(@Param("record") RelationshipVoKey record, @Param("example") RelationshipVoExample example);
}