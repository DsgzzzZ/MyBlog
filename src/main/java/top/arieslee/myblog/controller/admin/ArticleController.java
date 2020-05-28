/**
 * FileName: ArticleController
 * Author:   Aries
 * Date:     2018/8/26 18:10
 * Description: 文章编辑页控制器
 */
package top.arieslee.myblog.controller.admin;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.arieslee.myblog.constant.LogAction;
import top.arieslee.myblog.constant.Types;
import top.arieslee.myblog.constant.WebConstant;
import top.arieslee.myblog.controller.BaseController;
import top.arieslee.myblog.dto.ResponseDto;
import top.arieslee.myblog.exception.TipException;
import top.arieslee.myblog.modal.VO.ContentVo;
import top.arieslee.myblog.modal.VO.ContentVoExample;
import top.arieslee.myblog.modal.VO.MetaVo;
import top.arieslee.myblog.modal.VO.UserVo;
import top.arieslee.myblog.service.IContentService;
import top.arieslee.myblog.service.ILogService;
import top.arieslee.myblog.service.IMetaService;
import top.arieslee.myblog.utils.WebKit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/article")
public class ArticleController extends BaseController {

    private final static Logger LOGGER=LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public IMetaService metaService;

    @Autowired
    public IContentService contentService;

    @Resource
    private ILogService logService;

    /**
     * @Description 创建新文章
     **/

    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit, HttpServletRequest request) {
        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.setOrderByClause("created desc");
        contentVoExample.createCriteria().andTypeEqualTo(Types.ARTICLE.getType());
        PageInfo<ContentVo> contentsPaginator = contentService.getArticlesWithpage(contentVoExample, page, limit);
        request.setAttribute("articles", contentsPaginator);
        return "admin/article_list";
    }

    @GetMapping(value = "/{cid}")
    public String editArticle(@PathVariable String cid, HttpServletRequest request) {
        ContentVo contents = contentService.getContent(cid);
        request.setAttribute("contents", contents);
        List<MetaVo> categories = metaService.getMetas(Types.CATEGORY.getType());
        request.setAttribute("categories", categories);
        request.setAttribute("active", "article");
        return "admin/article_edit";
    }

    @GetMapping("/publish")
    public String newArticle(HttpServletRequest request){
        List<MetaVo> categories=metaService.getMetas(Types.CATEGORY.getType());
        request.setAttribute("categories",categories);
        return "admin/article_edit";
    }

    @PostMapping("/publish")
    @ResponseBody
    @Transactional(rollbackFor=TipException.class)
    public ResponseDto saveArticle(ContentVo content,HttpServletRequest request){
        UserVo user=WebKit.getUser(request);
        content.setAuthorId(user.getUid());
        content.setType(Types.ARTICLE.getType());
        if (StringUtils.isBlank(content.getCategories())) {
            content.setCategories("默认分类");
        }
        String result = contentService.publish(content);
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return ResponseDto.fail(result);
        }
        return ResponseDto.ok();
    }

    @PostMapping(value = "/modify")
    @ResponseBody
    public ResponseDto modifyArticle(ContentVo contents, HttpServletRequest request) {
        UserVo users = this.user(request);
        contents.setAuthorId(users.getUid());
        contents.setType(Types.ARTICLE.getType());
        String result = contentService.updateArticle(contents);
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return ResponseDto.fail(result);
        }
        return ResponseDto.ok();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseDto delete(@RequestParam int cid, HttpServletRequest request) {
        String result = contentService.deleteByCid(cid);
        logService.insertLog(LogAction.DEL_ARTICLE.getAction(), cid + "",  this.getUid(request),request.getRemoteAddr());
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return ResponseDto.fail(result);
        }
        return ResponseDto.ok();
    }

}
