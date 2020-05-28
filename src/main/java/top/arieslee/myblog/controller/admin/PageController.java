package top.arieslee.myblog.controller.admin;

import com.github.pagehelper.PageInfo;
import top.arieslee.myblog.constant.LogAction;
import top.arieslee.myblog.constant.WebConstant;
import top.arieslee.myblog.controller.BaseController;
import top.arieslee.myblog.constant.Types;
import top.arieslee.myblog.dto.ResponseDto;
import top.arieslee.myblog.modal.VO.ContentVo;
import top.arieslee.myblog.modal.VO.ContentVoExample;
import top.arieslee.myblog.modal.VO.UserVo;
import top.arieslee.myblog.service.IContentService;
import top.arieslee.myblog.service.ILogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 13 on 2019/5/11.
 */
@Controller()
@RequestMapping("admin/page")
public class PageController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @Resource
    private IContentService contentService;

    @Resource
    private ILogService logService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.setOrderByClause("created desc");
        contentVoExample.createCriteria().andTypeEqualTo(Types.PAGE.getType());
        PageInfo<ContentVo> contentsPaginator = contentService.getArticlesWithpage(contentVoExample, 1, WebConstant.MAX_POSTS);
        request.setAttribute("articles", contentsPaginator);
        return "admin/page_list";
    }

    @GetMapping(value = "new")
    public String newPage(HttpServletRequest request) {
        return "admin/page_edit";
    }

    @GetMapping(value = "/{cid}")
    public String editPage(@PathVariable String cid, HttpServletRequest request) {
        ContentVo contents = contentService.getContent(cid);
        request.setAttribute("contents", contents);
        return "admin/page_edit";
    }

    @PostMapping(value = "publish")
    @ResponseBody
    public ResponseDto publishPage(@RequestParam String title, @RequestParam String content,
                                   @RequestParam String status, @RequestParam String slug,
                                   @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        UserVo users = this.user(request);
        ContentVo contents = new ContentVo();
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(Types.PAGE.getType());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(users.getUid());
        String result = contentService.publish(contents);
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return ResponseDto.fail(result);
        }
        return ResponseDto.ok();
    }

    @PostMapping(value = "modify")
    @ResponseBody
    public ResponseDto modifyArticle(@RequestParam Integer cid, @RequestParam String title,
                                        @RequestParam String content,
                                        @RequestParam String status, @RequestParam String slug,
                                        @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        UserVo users = this.user(request);
        ContentVo contents = new ContentVo();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(Types.PAGE.getType());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(users.getUid());
        String result = contentService.updateArticle(contents);
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return ResponseDto.fail(result);
        }
        return ResponseDto.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public ResponseDto delete(@RequestParam int cid, HttpServletRequest request) {
        String result = contentService.deleteByCid(cid);
        logService.insertLog(LogAction.DEL_ARTICLE.getAction(), cid + "",this.getUid(request), request.getRemoteAddr());
        if (!WebConstant.SUCCESS_RESULT.equals(result)) {
            return ResponseDto.fail(result);
        }
        return ResponseDto.ok();
    }
}
