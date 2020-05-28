package top.arieslee.myblog.dao;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.arieslee.myblog.dto.ArchiveDto;
import top.arieslee.myblog.modal.VO.ContentVo;
import top.arieslee.myblog.modal.VO.ContentVoExample;

import java.util.List;

/**
 * @ClassName ContentVoDao
 * @Description content表数据访问层接口
 * @Author Aries
 * @Date 2019/5/10 12:00
 * @Version 1.0
 **/
@Component
public interface ContentVoDao {

    /**
     * @Description : 依据模板进行查询，包括内容
     * @Date : 16:21 2018/7/11
     * @Param [example：模板]
     **/
    List<ContentVo> selectByExampleWithBLOBs(ContentVoExample example);

    /**
     * @return java.util.List<top.arieslee.myblog.modal.VO.ContentVo>
     * @Description 依据模板进行查询，不包括内容
     * @Param [example]
     **/
    List<ContentVo> selectByExample(ContentVoExample example);

    /**
     * @Description :根据主键查找
     **/
    ContentVo selectByPrimaryKey(Integer cid);

    /**
     * @Description : 根据cid更新文章数据（覆盖更新）
     **/
    void updateByPrimaryKey(ContentVo contentVo);

    /**
     * @Description 根据cid有选择地更新文章内容
     **/
    void updateByPrimaryKeySelective(ContentVo contentVo);

    /**
     * @Description 根据分类获取文章
     **/
    List<ContentVo> findByMid(Integer mid);

    /**
     * @return java.util.List<top.arieslee.myblog.dto.ArchiveDto>
     * @Description 按月份查询归档列表
     * @Param []
     **/
    List<ArchiveDto> findArchiveDtoByMonth();

    /**
     * @Description 查询数量
     **/
    long countByExample(ContentVoExample example);

    int deleteByPrimaryKey(Integer cid);

    int deleteByExample(ContentVoExample example);

    int insert(ContentVo record);

    int insertSelective(ContentVo record);

    List<ContentVo> findByCatalog(Integer mid);

    int updateByExampleSelective(@Param("record") ContentVo record, @Param("example") ContentVoExample example);
}
