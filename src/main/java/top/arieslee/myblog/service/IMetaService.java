package top.arieslee.myblog.service;

import top.arieslee.myblog.dto.MetaDto;
import top.arieslee.myblog.modal.VO.MetaVo;

import java.util.List;

/**
 * @ClassName IMetaService
 * @Description meta表业务逻辑
 * @Author Aries
 * @Date 2019/5/13 11:35
 * @Version 1.0
 **/
public interface IMetaService {

    /**
     * 根据类型和名字查询项
     *
     * @param type
     * @param name
     * @return
     */
    MetaDto getMeta(String type, String name);

    /**
     * @return top.arieslee.myblog.dto.MetaDto
     * @Description 根据分类类型和分类关键字获取分类文章数量
     * @Param [type, name]
     **/
    MetaDto getMetaCount(String type, String name);

    /**
     * @Param []
     * @Return java.util.List<top.arieslee.myblog.modal.VO.MetaVo>
     * @Description 获取指定类型列表
     **/
    List<MetaVo> getMetas(String type);

    /**
     * 根据类型查询项目列表，带项目下面的文章数
     * @return
     */
    List<MetaDto> getMetaList(String type, String orderby, int limit);

    /**
     * 保存多个项目
     * @param cid
     * @param names
     * @param type
     */
    void saveMetas(Integer cid, String names, String type);

    /**
     * @return java.util.List<top.arieslee.myblog.modal.VO.MetaVo>
     * @Description 获取友情链接
     * @Param
     **/
    List<MetaVo> getLinks(String type);

    /**
     * 保存项目
     * @param type
     * @param name
     * @param mid
     */
    void saveMeta(String type, String name, Integer mid);

    /**
     * 删除项目
     * @param mid
     */
    void delete(int mid);

    /**
     * 保存项目
     * @param metas
     */
    void saveMeta(MetaVo metas);

    /**
     * 更新项目
     * @param metas
     */
    void update(MetaVo metas);
}
