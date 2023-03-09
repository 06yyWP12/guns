package cn.stylefeng.guns.modular.patient.controller;

import cn.stylefeng.guns.modular.system.dao.PatientInfoMapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.PatientInfo;
import cn.stylefeng.guns.modular.patient.service.IPatientInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 居民管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-29 15:57:18
 */
@Controller
@RequestMapping("/patientInfo")
public class PatientInfoController extends BaseController {

    private String PREFIX = "/patient/patientInfo/";

    @Autowired
    private IPatientInfoService patientInfoService;

    @Resource
    private PatientInfoMapper patientInfoMapper;
    /**
     * 跳转到居民管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientInfo.html";
    }

    /**
     * 跳转到添加居民管理
     */
    @RequestMapping("/patientInfo_add")
    public String patientInfoAdd() {
        return PREFIX + "patientInfo_add.html";
    }

    /**
     * 跳转到修改居民管理
     */
    @RequestMapping("/patientInfo_update/{paientIdcard}")
    public String patientInfoUpdate(@PathVariable String paientIdcard, Model model) {
        PatientInfo patientInfo = patientInfoService.selectById(paientIdcard);
        model.addAttribute("item",patientInfo);
        LogObjectHolder.me().set(patientInfo);
        return PREFIX + "patientInfo_edit.html";
    }

    /**
     * 获取居民管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        if(StringUtils.isEmpty(condition)){
            return patientInfoService.selectList(null);
        }else {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("paient_name",condition);
            List<PatientInfo> patientInfoList = patientInfoMapper.selectByMap(map);
            return patientInfoList;
        }
    }

    /**
     * 新增居民管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientInfo patientInfo) {
        patientInfoService.insert(patientInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除居民管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String paientIdcard) {
        patientInfoService.deleteById(paientIdcard);
        return SUCCESS_TIP;
    }

    /**
     * 修改居民管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientInfo patientInfo) {
        patientInfoService.updateById(patientInfo);
        return SUCCESS_TIP;
    }

    /**
     * 居民管理详情
     */
    @RequestMapping(value = "/detail/{patientInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("patientInfoId") Integer patientInfoId) {
        return patientInfoService.selectById(patientInfoId);
    }
}
