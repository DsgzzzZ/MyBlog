package top.arieslee.myblog.service;

import com.github.pagehelper.PageInfo;
import top.arieslee.myblog.dto.CommentDto;
import top.arieslee.myblog.modal.VO.CommentVo;
import top.arieslee.myblog.modal.VO.CommentVoExample;

/**
 * @ClassName ICommentService
 * @Description 评论接口规范
 * @Author Aries
 * @Date 2019/5/17 11:10
 * @Version 1.0
 **/
public interface ICommentService {

    /**
     * @Description : 获取分页评论列表
     * @Date : 15:59 2018/7/17
     * @Param 
     * @return 
     **/
    PageInfo<CommentDto> getComment(Integer cid, Integer pageNum, Integer limit);

    /**
     * @Description 插入新的评论
     * @Param [commentVo]
     * @return void
     **/
    void insertComment(CommentVo commentVo);

    /**
     * 获取文章下的评论
     * @param commentVoExample
     * @param page
     * @param limit
     * @return CommentVo
     */
    PageInfo<CommentVo> getCommentsWithPage(CommentVoExample commentVoExample, int page, int limit);

    /**
     * 根据主键查询评论
     * @param coid
     * @return
     */
    CommentVo getCommentById(Integer coid);


    /**
     * 删除评论，暂时没用
     * @param coid
     * @param cid
     * @throws Exception
     */
    void delete(Integer coid, Integer cid);

    /**
     * 更新评论状态
     * @param comments
     */
    void update(CommentVo comments);
}
