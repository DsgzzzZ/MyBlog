package top.arieslee.myblog.service;

import top.arieslee.myblog.modal.VO.OptionVo;

import java.util.List;
import java.util.Map;

/**
 * options的接口
 * Created by Aries on 2019/5/7.
 */
public interface IOptionService {

    void insertOption(OptionVo optionVo);

    void insertOption(String name, String value);

    List<OptionVo> getOptions();


    /**
     * 保存一组配置
     *
     * @param options
     */
    void saveOptions(Map<String, String> options);

    OptionVo getOptionByName(String name);
}
