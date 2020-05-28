package top.arieslee.myblog.service;

import com.github.pagehelper.PageInfo;
import top.arieslee.myblog.modal.VO.AttachVo;

/**
 * Created by Aries on 2019/5/20.
 */
public interface IAttachService {
    /**
     * 分页查询附件
     * @param page
     * @param limit
     * @return
     */
    PageInfo<AttachVo> getAttachs(Integer page, Integer limit);

    /**
     * 保存附件
     *
     * @param fname
     * @param fkey
     * @param ftype
     * @param author
     */
    void save(String fname, String fkey, String ftype, Integer author);

    /**
     * 根据附件id查询附件
     * @param id
     * @return
     */
    AttachVo selectById(Integer id);

    /**
     * 删除附件
     * @param id
     */
    void deleteById(Integer id);
}