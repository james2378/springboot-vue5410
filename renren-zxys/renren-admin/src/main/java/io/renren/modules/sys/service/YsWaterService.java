
package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.YsWater;

import java.util.Map;

public interface YsWaterService extends IService<YsWater> {

    PageUtils queryPage(Map<String, Object> params);

}

