package top.arieslee.myblog.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.arieslee.myblog.dto.MetaDto;
import top.arieslee.myblog.modal.VO.MetaVo;
import top.arieslee.myblog.modal.VO.MetaVoExample;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MetaDao
 * @Description Meta表数据访问接口
 * @Author Aries
 * @Date 2019/5/3 15:04
 * @Version 1.0
 **/
@Repository
public interface MetaVoDao {

    /**
     * @return java.lang.Integer
     * @Description 获取指定类别信息数量
     * @Param [mid]
     **/
    Integer countByMid(Integer mid);

    /**
     * @return top.arieslee.myblog.dto.MetaDto
     * @Description 根据分类类别和分类关键字获取MetaDto对象
     * @Param [type, name]
     **/
    MetaDto selectByTypeAndName(@Param("type") String type, @Param("name") String name);

    /**
     * @return java.util.List<top.arieslee.myblog.modal.VO.MetaVo>
     * @Description 获取链接meta
     * @Param
     **/
    List<MetaVo> selectByExample(MetaVoExample example);

    /**
     * @Description 查询数量
     **/
    long countByExample(MetaVoExample example);

    List<MetaDto> selectFromSql(Map<String,Object> paraMap);

    MetaVo selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(MetaVo record);

    int insertSelective(MetaVo record);

    int deleteByPrimaryKey(Integer mid);

    MetaDto selectDtoByNameAndType(@Param("name") String name,@Param("type") String type);
}
