package top.arieslee.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.arieslee.myblog.constant.Types;
import top.arieslee.myblog.constant.WebConstant;
import top.arieslee.myblog.dao.ContentVoDao;
import top.arieslee.myblog.dao.MetaVoDao;
import top.arieslee.myblog.exception.TipException;
import top.arieslee.myblog.modal.VO.ContentVo;
import top.arieslee.myblog.modal.VO.ContentVoExample;
import top.arieslee.myblog.service.IContentService;
import top.arieslee.myblog.service.IMetaService;
import top.arieslee.myblog.service.IRelationshipService;
import top.arieslee.myblog.utils.DateKit;
import top.arieslee.myblog.utils.PatternKit;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ContentServiceImpl
 * @Description Content业务层
 * @Author Aries
 * @Date 2019/5/10 15:52
 * @Version 1.0
 **/
@Service
public class ContentServiceImpl implements IContentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private ContentVoDao contentVoDao;

    @Autowired
    private MetaVoDao metaVoDao;

    @Resource
    private IRelationshipService relationshipService;

    @Resource
    private IMetaService metasService;


    @Override
    public String publish(ContentVo content) {
        if (content == null) {
            return "文章对象不存在";
        }
        if (StringUtils.isBlank(content.getTitle())) {
            return "文章标题不能为空";
        }
        if (StringUtils.isBlank(content.getContent())) {
            return "文章内容不能为空";
        }
        if (content.getTitle().length() > WebConstant.MAX_TITLE_LENGTH) {
            return "文章标题太长了";
        }
        if (content.getContent().length() > WebConstant.MAX_CONTENT_LENGTH) {
            return "文章内容太长了";
        }
        if (content.getAuthorId() == null) {
            return "请先登录";
        }
        if (StringUtils.isNotBlank(content.getSlug())) {
            String slug = content.getSlug();
            if (slug.length() < 5) {
                return "路径长度不小于5喔~";
            }
            if (!PatternKit.isPath(slug)) {
                return "非法路径啦";
            }
            ContentVoExample example = new ContentVoExample();
            example.createCriteria().andSlugEqualTo(slug);
            if (contentVoDao.countByExample(example) > 0) {
                return "该路径已经存在了";
            }
        } else {
            content.setSlug(null);
        }

        content.setContent(EmojiParser.parseToAliases(content.getContent()));

        int time = DateKit.getCurrentUnixTime();
        //填充content对象属性
        content.setCreated((long) DateKit.getCurrentUnixTime());
        content.setModified((long)DateKit.getCurrentUnixTime());
        content.setHits(0);
        content.setCommentsNum(0);
        content.setAllowComment(true);
        content.setAllowPing(true);
        content.setAllowFeed(true);

        String tags = content.getTags();
        String categories = content.getCategories();
        contentVoDao.insert(content);
        Integer cid = content.getCid();
        metasService.saveMetas(cid, tags, Types.TAG.getType());
        metasService.saveMetas(cid, categories, Types.CATEGORY.getType());
        return WebConstant.SUCCESS_RESULT;
    }


    @Override
    //获取分页列表
    public PageInfo<ContentVo> getContent(Integer p, Integer limit) {
        LOGGER.debug("Enter the getContents(Integer p,Integer limit) method");
        //创建查询模板
        ContentVoExample example = new ContentVoExample();
        //排序方式
        example.setOrderByClause("created desc");
        //指定查询标准
        example.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
        //执行分页插件（三部曲）：设定分页条件->获取分页内容->执行分页操作
        PageHelper.startPage(p, limit);
        List<ContentVo> contentVos = contentVoDao.selectByExampleWithBLOBs(example);
        PageInfo<ContentVo> pageInfo = new PageInfo<>(contentVos);
        LOGGER.debug("Exit the getContents(Integer p,Integer limit) method");
        return pageInfo;
    }

    @Override
    //按主键查询content
    public ContentVo getContent(String cid) {
        LOGGER.debug("Enter the getContent(String cid) method");
        if (StringUtils.isNotBlank(cid)) {
            //两种情况：按照cid查找和按照slug查找
            if (PatternKit.isNum(cid)) {
                ContentVo contentVo = contentVoDao.selectByPrimaryKey(Integer.valueOf(cid));
                //每次请求都更新点击率，考虑到性能优化，不采用这种方式，而是定时批量更新
//                if (contentVo != null) {
//                    contentVo.setHits(contentVo.getHits() + 1);
//                    contentVoDao.updateByPrimaryKey(contentVo);
//                }
                LOGGER.debug("Exit the getContent(String cid) method");
                return contentVo;
            } else {
                //创建查询模板
                ContentVoExample example = new ContentVoExample();
                example.createCriteria().andSlugEqualTo(cid);
                List<ContentVo> contentVoList = contentVoDao.selectByExampleWithBLOBs(example);
                if (contentVoList.size() > 1) {
                    //slug冲突
                    throw new TipException("The Slug you selected is more than 1");
                }
                LOGGER.debug("Exit the getContent(String cid) method");
                return contentVoList.get(0);
            }
        }
        LOGGER.debug("Exit the getContent(String cid) method");
        return null;
    }

    @Override
    //获取分类文章
    public PageInfo<ContentVo> getArticles(Integer mid, int page, int limit) {
        //获取文章总数
        int total = metaVoDao.countByMid(mid);
        //分页插件三部曲
        PageHelper.startPage(page, limit);
        List<ContentVo> contentVos = contentVoDao.findByMid(mid);
        PageInfo<ContentVo> contentVoPageInfo = new PageInfo<>(contentVos);
        contentVoPageInfo.setTotal(total);
        return contentVoPageInfo;
    }

    @Override
    public PageInfo<ContentVo> getArticles(String keyword, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        ContentVoExample contentVoExample = new ContentVoExample();
        ContentVoExample.Criteria criteria = contentVoExample.createCriteria();
        criteria.andTypeEqualTo(Types.ARTICLE.getType());
        criteria.andStatusEqualTo(Types.PUBLISH.getType());
        criteria.andTitleLike("%" + keyword + "%");
        contentVoExample.setOrderByClause("created desc");
        List<ContentVo> contentVos = contentVoDao.selectByExampleWithBLOBs(contentVoExample);
        return new PageInfo<>(contentVos);
    }

    @Override
    public PageInfo<ContentVo> getArticlesWithpage(ContentVoExample commentVoExample, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ContentVo> contentVos = contentVoDao.selectByExampleWithBLOBs(commentVoExample);
        return new PageInfo<>(contentVos);
    }

    @Override
    @Transactional
    public String deleteByCid(Integer cid) {
        ContentVo contents = this.getContent(cid + "");
        if (null != contents) {
            contentVoDao.deleteByPrimaryKey(cid);
//            relationshipService.deleteById(cid, null);
            return WebConstant.SUCCESS_RESULT;
        }
        return "数据为空";
    }



    @Override
    //更新文章内容
    public void updateContentByCid(ContentVo content) {
        if (content != null && content.getCid() != null) {
            contentVoDao.updateByPrimaryKeySelective(content);
        }
    }

    @Override
    @Transactional
    public String updateArticle(ContentVo contents) {
        if (null == contents) {
            return "文章对象为空";
        }
        if (StringUtils.isBlank(contents.getTitle())) {
            return "文章标题不能为空";
        }
        if (StringUtils.isBlank(contents.getContent())) {
            return "文章内容不能为空";
        }
        int titleLength = contents.getTitle().length();
        if (titleLength > WebConstant.MAX_TITLE_LENGTH) {
            return "文章标题过长";
        }
        int contentLength = contents.getContent().length();
        if (contentLength > WebConstant.MAX_CONTENT_LENGTH) {
            return "文章内容过长";
        }
        if (null == contents.getAuthorId()) {
            return "请登录后发布文章";
        }
        if (StringUtils.isBlank(contents.getSlug())) {
            contents.setSlug(null);
        }
        long time = DateKit.getCurrentUnixTime();
        contents.setModified(time);
        Integer cid = contents.getCid();
        contents.setContent(EmojiParser.parseToAliases(contents.getContent()));

        contentVoDao.updateByPrimaryKeySelective(contents);
        relationshipService.deleteById(cid, null);
        metasService.saveMetas(cid, contents.getTags(), Types.TAG.getType());
        metasService.saveMetas(cid, contents.getCategories(), Types.CATEGORY.getType());
        return WebConstant.SUCCESS_RESULT;
    }

    @Override
    public void updateCategory(String ordinal, String newCatefory) {
        ContentVo contentVo = new ContentVo();
        contentVo.setCategories(newCatefory);
        ContentVoExample example = new ContentVoExample();
        example.createCriteria().andCategoriesEqualTo(ordinal);
        contentVoDao.updateByExampleSelective(contentVo, example);
    }
}
