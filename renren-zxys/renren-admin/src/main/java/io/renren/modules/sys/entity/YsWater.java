package io.renren.modules.sys.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ys_water")
public class YsWater {
    @TableId
    private Long id;
    private String name;//项目名称
    private String xmType;//项目类型
    private String xmDizhi;//项目地址
    private String username;//录入人
    private String lrTime;//录入日期
    private String gzUsername;//工地负责人
    private String sjs;//设计师
    private String jlry;//监理人员
    private String money;
    private String sgTime;//施工日期
    private String jsTime;//结束日期
    private String clpp;//材料品牌
    private String sgUsername;//施工人员
    private String khjg;//核结果
    private String remark;
    private String ysType;
}



