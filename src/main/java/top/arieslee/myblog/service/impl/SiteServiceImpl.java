package top.arieslee.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.arieslee.myblog.constant.Types;
import top.arieslee.myblog.controller.admin.AttachController;
import top.arieslee.myblog.dao.CommentVoDao;
import top.arieslee.myblog.dao.ContentVoDao;
import top.arieslee.myblog.dao.MetaVoDao;
import top.arieslee.myblog.dto.ArchiveDto;
import top.arieslee.myblog.dto.BackResponseDto;
import top.arieslee.myblog.dto.MetaDto;
import top.arieslee.myblog.dto.StatisticsDto;
import top.arieslee.myblog.exception.TipException;
import top.arieslee.myblog.modal.VO.*;
import top.arieslee.myblog.service.ISiteService;
import top.arieslee.myblog.utils.DateKit;
import top.arieslee.myblog.utils.TaleUtils;
import top.arieslee.myblog.utils.ZipUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName SiteService
 * @Description 站点业务逻辑实现类
 * @Author Aries
 * @Date 2019/5/4 10:07
 * @Version 1.0
 **/
@Service
public class SiteServiceImpl implements ISiteService {

    @Autowired
    private ContentVoDao contentVoDao;

    @Autowired
    private CommentVoDao commentVoDao;

    @Autowired
    private MetaVoDao metaVoDao;

    @Override
    public List<ArchiveDto> getArchives() {
        //获取归档列表
        List<ArchiveDto> archives = contentVoDao.findArchiveDtoByMonth();
        //获取所属归档月的文章列表
        if (archives != null) {
            archives.forEach(archive -> {
                ContentVoExample example = new ContentVoExample();
                ContentVoExample.Criteria criteria = example.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
                example.setOrderByClause("created desc");
                //获取归档日期
                String date = archive.getDate();
                //将字符串按指定格式解析为Date对象
                Date sd = DateKit.dateParse(date, "yyyy年MM月");
                //设定开始和结束时间
                int start = DateKit.getUnixTimeByDate(sd);
                int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_MONTH, sd, 1));
                criteria.andCreatedGreaderThan(start);
                criteria.andCreatedLessThan(end);
                //查询该月份的文章列表
                List<ContentVo> contentVos = contentVoDao.selectByExample(example);
                archive.setArticles(contentVos);
            });
        }
        return archives;
    }

    @Override
    public List<CommentVo> recentComment(int limit) {
        if (limit < 0 || limit > 5) {
            limit = 5;
        }
        CommentVoExample example = new CommentVoExample();
        example.setOrderByClause("created desc");
        PageHelper.startPage(1, limit);
        List<CommentVo> byPage = commentVoDao.selectByExampleWithBLOBs(example);
        return byPage;
    }

    @Override
    public List<ContentVo> recentContent(int limit) {
        if (limit < 0 || limit > 5) {
            limit = 5;
        }
        ContentVoExample example=new ContentVoExample();
        example.setOrderByClause("created desc");
        example.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
        PageHelper.startPage(1,limit);
        List<ContentVo> byPage = contentVoDao.selectByExampleWithBLOBs(example);
        return byPage;
    }

    @Override
    public StatisticsDto currentStatistics() {

        StatisticsDto statistics = new StatisticsDto();

        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
        Long articles = contentVoDao.countByExample(contentVoExample);

        Long comments = commentVoDao.countByExample(new CommentVoExample());

//        Long attachs = attachVoDao.countByExample(new AttachVoExample());

        MetaVoExample metaVoExample = new MetaVoExample();
        metaVoExample.createCriteria().andTypeEqualTo(Types.LINK.getType());
        Long links = metaVoDao.countByExample(metaVoExample);

        statistics.setArticles(articles);
        statistics.setComments(comments);
//        statistics.setAttachs(attachs);
        statistics.setLinks(links);
        return statistics;
    }

    @Override
    public BackResponseDto backup(String bk_type, String bk_path, String fmt) throws Exception {
        BackResponseDto backResponse = new BackResponseDto();
        if (bk_type.equals("attach")) {
            if (StringUtils.isBlank(bk_path)) {
                throw new TipException("请输入备份文件存储路径");
            }
            if (!(new File(bk_path)).isDirectory()) {
                throw new TipException("请输入一个存在的目录");
            }
            String bkAttachDir = AttachController.CLASSPATH + "upload";
            String bkThemesDir = AttachController.CLASSPATH + "templates/themes";

            String fname = DateKit.dateFormat(new Date(), fmt) + "_" + TaleUtils.getRandomNumber(5) + ".zip";

            String attachPath = bk_path + "/" + "attachs_" + fname;
            String themesPath = bk_path + "/" + "themes_" + fname;

            ZipUtils.zipFolder(bkAttachDir, attachPath);
            ZipUtils.zipFolder(bkThemesDir, themesPath);

            backResponse.setAttachPath(attachPath);
            backResponse.setThemePath(themesPath);
        }
//        if (bk_type.equals("db")) {
//
//            String bkAttachDir = AttachController.CLASSPATH + "upload/";
//            if (!(new File(bkAttachDir)).isDirectory()) {
//                File file = new File(bkAttachDir);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//            }
//            String sqlFileName = "tale_" + DateKit.dateFormat(new Date(), fmt) + "_" + TaleUtils.getRandomNumber(5) + ".sql";
//            String zipFile = sqlFileName.replace(".sql", ".zip");
//
//            Backup backup = new Backup(TaleUtils.getNewDataSource().getConnection());
//            String sqlContent = backup.execute();
//
//            File sqlFile = new File(bkAttachDir + sqlFileName);
//            write(sqlContent, sqlFile, Charset.forName("UTF-8"));
//
//            String zip = bkAttachDir + zipFile;
//            ZipUtils.zipFile(sqlFile.getPath(), zip);
//
//            if (!sqlFile.exists()) {
//                throw new TipException("数据库备份失败");
//            }
//            sqlFile.delete();
//
//            backResponse.setSqlPath(zipFile);
//
//            // 10秒后删除备份文件
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    new File(zip).delete();
//                }
//            }, 10 * 1000);
//        }
        return backResponse;
    }


}
