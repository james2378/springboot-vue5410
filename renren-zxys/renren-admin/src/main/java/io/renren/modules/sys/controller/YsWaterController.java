package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.YsWater;
import io.renren.modules.sys.service.YsWaterService;
import io.renren.modules.sys.service.impl.YsWaterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys/ysWater")
public class YsWaterController extends AbstractController {
    @Autowired
    private YsWaterService ysWaterervice;

    @Autowired
    private YsWaterServiceImpl ysWaterServiceImpl;
    @RequestMapping("/waterList")
    public R waterList(@RequestParam Map<String, Object> params){
        params.put("ysType","水电装修验收");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }
    @RequestMapping("/waterDjList")
    public R waterDjList(@RequestParam Map<String, Object> params){
        params.put("ysType","水电业务单据");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/wgList")
    public R wgList(@RequestParam Map<String, Object> params){
        params.put("ysType","瓦工装修验收");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/wgDjList")
    public R wgDjList(@RequestParam Map<String, Object> params){
        params.put("ysType","瓦工业务单据");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/mgList")
    public R mgList(@RequestParam Map<String, Object> params){
        params.put("ysType","木工装修验收");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }
    @RequestMapping("/mgDjList")
    public R mgDjList(@RequestParam Map<String, Object> params){
        params.put("ysType","木工业务单据");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }
    @RequestMapping("/yqgList")
    public R yqgList(@RequestParam Map<String, Object> params){
        params.put("ysType","油漆工装修验收");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/yqgDjList")
    public R yqgDjList(@RequestParam Map<String, Object> params){
        params.put("ysType","油漆工业务单据");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }
    @RequestMapping("/sgjsList")
    public R sgjsList(@RequestParam Map<String, Object> params){
        params.put("ysType","施工结束验收");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/sgjsDjList")
    public R sgjsDjList(@RequestParam Map<String, Object> params){
        params.put("ysType","施工结束业务单据");
        PageUtils page = ysWaterervice.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YsWater ysWater = ysWaterervice.getById(id);
        return R.ok().put("ysWater", ysWater);
    }
    @RequestMapping("/export")
    public R save(HttpServletResponse response, HttpServletRequest request){
        try {
            ysWaterServiceImpl.outExcelByMealOrReserve(getParametersMap(request),response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok();
    }


    /**
     * 将所有参数组装成Map
     * @return
     */
    private   Map<String, String> getParametersMap(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        String encoding = request.getCharacterEncoding();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    if (!encoding.toUpperCase().equals("UTF-8")) {
                        map.put(paramName, new String(paramValue.getBytes("ISO-8859-1"),"UTF-8"));
                    } else {
                        map.put(paramName, paramValue);
                    }
                }
            }
        }
        return map;
    }

    @RequestMapping("/save")
    public R save(@RequestBody YsWater ysWater){
        ysWaterervice.save(ysWater);
        return R.ok();
    }
    @RequestMapping("/update")
    public R update(@RequestBody YsWater ysWater){
        ysWaterervice.updateById(ysWater);
        return R.ok();
    }
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        ysWaterervice.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}