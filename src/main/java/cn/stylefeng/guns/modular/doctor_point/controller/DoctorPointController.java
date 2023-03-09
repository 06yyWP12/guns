package cn.stylefeng.guns.modular.doctor_point.controller;

import cn.stylefeng.guns.modular.system.dao.DoctorPointMapper;
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
import cn.stylefeng.guns.modular.system.model.DoctorPoint;
import cn.stylefeng.guns.modular.doctor_point.service.IDoctorPointService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医生预约控制器
 *
 * @author fengshuonan
 * @Date 2023-03-10 17:57:59
 */
@Controller
@RequestMapping("/doctorPoint")
public class DoctorPointController extends BaseController {

    private String PREFIX = "/doctor_point/doctorPoint/";

    @Resource
    private DoctorPointMapper doctorPointMapper;

    @Autowired
    private IDoctorPointService doctorPointService;

    /**
     * 跳转到医生预约首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "doctorPoint.html";
    }

    /**
     * 跳转到添加医生预约
     */
    @RequestMapping("/doctorPoint_add")
    public String doctorPointAdd() {
        return PREFIX + "doctorPoint_add.html";
    }

    /**
     * 跳转到修改医生预约
     */
    @RequestMapping("/doctorPoint_update/{doctorPointId}")
    public String doctorPointUpdate(@PathVariable Integer doctorPointId, Model model) {
        DoctorPoint doctorPoint = doctorPointService.selectById(doctorPointId);
        model.addAttribute("item",doctorPoint);
        LogObjectHolder.me().set(doctorPoint);
        return PREFIX + "doctorPoint_edit.html";
    }

    /**
     * 获取医生预约列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        if (StringUtils.isEmpty(condition)){
            return doctorPointService.selectList(null);
        }else {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("patient_name",condition);
            List<DoctorPoint> doctorPointList = doctorPointMapper.selectByMap(map);
            return doctorPointList;
        }
    }

    /**
     * 新增医生预约
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DoctorPoint doctorPoint) {
        doctorPointService.insert(doctorPoint);
        return SUCCESS_TIP;
    }

    /**
     * 删除医生预约
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer doctorPointId) {
        doctorPointService.deleteById(doctorPointId);
        return SUCCESS_TIP;
    }

    /**
     * 修改医生预约
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DoctorPoint doctorPoint) {
        doctorPointService.updateById(doctorPoint);
        return SUCCESS_TIP;
    }

    /**
     * 医生预约详情
     */
    @RequestMapping(value = "/detail/{doctorPointId}")
    @ResponseBody
    public Object detail(@PathVariable("doctorPointId") Integer doctorPointId) {
        return doctorPointService.selectById(doctorPointId);
    }
}
