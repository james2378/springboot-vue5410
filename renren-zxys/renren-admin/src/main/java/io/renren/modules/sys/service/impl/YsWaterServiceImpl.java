package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.YsWaterDao;
import io.renren.modules.sys.entity.YsWater;
import io.renren.modules.sys.service.YsWaterService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("ysWaterService")
public class YsWaterServiceImpl extends ServiceImpl<YsWaterDao, YsWater> implements YsWaterService {
    @Autowired
    private YsWaterService ysWaterervice;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        String ysType = (String)params.get("ysType");
        IPage<YsWater> page = this.page(
            new Query<YsWater>().getPage(params),
            new QueryWrapper<YsWater>()
                .like(StringUtils.isNotBlank(name),"name", name)
                    .eq("ys_type", ysType)
        );

        return new PageUtils(page);
    }

    public void outExcelByMealOrReserve(Map<String, String> params,
                                        HttpServletResponse response) {
        QueryWrapper<YsWater> wrapper = new QueryWrapper<>();
        wrapper.eq("ys_type",params.get("ys_type").toString());
        List<YsWater> list = ysWaterervice.list(wrapper);
        if (list != null && list.size() > 0) {
            HSSFWorkbook wb = new HSSFWorkbook();
            String sheetName = "验收数据";
            HSSFSheet sheet = wb.createSheet(sheetName);
            sheet.setDefaultColumnWidth(15);

            HSSFRow row = sheet.createRow(0);
            HSSFCellStyle rowStyle = wb.createCellStyle();
            rowStyle.setAlignment(HorizontalAlignment.CENTER);
            rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            HSSFFont rowFont = wb.createFont();
            rowFont.setFontName("黑体");
            rowFont.setBold(true);
            rowStyle.setFont(rowFont);


            HSSFCellStyle cellStyle = wb.createCellStyle();
            rowStyle.setAlignment(HorizontalAlignment.CENTER);
            rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setWrapText(true);
            HSSFCell cell = null;
            createMealApply(list,sheet,row,cell,rowStyle,cellStyle);

            OutputStream outFis = null;
            try {
                response.addHeader(
                        "Content-Disposition",
                        "attachment;filename="
                                + new String(
                                (sheetName
                                        + "表"+ ".xls")
                                        .getBytes("gbk"), "iso-8859-1"));
                outFis = new BufferedOutputStream(response.getOutputStream());
                wb.write(outFis);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (outFis != null) {
                    try {
                        outFis.flush();
                        outFis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void createMealApply(List<?> list, HSSFSheet sheet, HSSFRow row,
                                 HSSFCell cell, HSSFCellStyle rowStyle, HSSFCellStyle cellStyle) {
        List<YsWater> meals = (List<YsWater>) list;
        String[] titles = new String[] { "ID", "验收类型","项目名称","项目类型","录入人",
                "录入日期","工地负责人","设计师","监理人员","合同金额",
                "施工日期","结束日期","材料品牌","施工人员","考核结果","备注"};
        // 行高度30px，1pt = 20twips，1px = 0.75pt(3/4)，1px = 15twips
            row.setHeight((short) (22.5 * 20)); // 单位为twips(缇)
            for (int i = 0; i < titles.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(rowStyle);
            }
            for (int i = 0; i < meals.size(); i++) {
                YsWater m = meals.get(i);
                YsWater user = ysWaterervice.getById(m.getId());
                row = sheet.createRow(i + 1);
                row.setHeight((short) (22.5 * 20));
                cell = row.createCell(0);
                cell.setCellValue(m.getId());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue(user.getName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(user.getXmType());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue(user.getXmType());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue(user.getUsername());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue(user.getLrTime());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue(user.getGzUsername());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(7);
                cell.setCellValue(user.getSjs());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(8);
                cell.setCellValue(user.getJlry());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(9);
                cell.setCellValue(user.getMoney());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(10);
                cell.setCellValue(user.getSgTime());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(11);
                cell.setCellValue(user.getJsTime());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(12);
                cell.setCellValue(user.getClpp());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(13);
                cell.setCellValue(user.getKhjg());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(14);
                cell.setCellValue(user.getRemark());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(15);
                cell.setCellValue(user.getYsType());
                cell.setCellStyle(cellStyle);

            }
        }

    }


