package org.zjw.web.util;

import org.zjw.web.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


public class POIExcelExport {
    public static final String[] FIN_ACCOUNT = {"序号", "列1", "列2", "列3", "列4", "列5"};
    public static final String[] FIN_AUTO_RECHARGE = {"序号", "网点简称", "充值状态", "商户订单号", "支付类型", "充值金额",
            "手续费", "充值人", "充值时间", "备注"};

    public static final String[] FIN_RECHARGE = {"序号", "充值网点简称", "充值网点编号", "收款网点简称", "账户类型", "充值类型", "充值状态", "充值金额",
            "手续费", "充值人", "充值时间", "备注"};

    //代收款明细
    public static final String[] Fin_SUPPER_CORE = {"序号", "运单号", "发货网点", "发货公司", "代收货款金额", "到账金额",
            "手续费", "户名", "银行", "银行卡号", "开户银行编号", "开户行网点(支行)", "委托书图片查看", "发货人电话", "发货时间",
            "签收时间", "支付时间", "返款成功时间", "稽核时间", "稽核人", "稽核人工号", "返款人", "返款人工号", "确认返款时间", "状态", "收款标识", "备注"};

    /**
     * 设置输出响应下载流
     *
     * @param fileName
     * @param response
     * @return
     * @throws IOException
     */
    public static OutputStream setExcelRespone(String fileName, HttpServletResponse response) throws IOException {
        //输出流
        OutputStream os = response.getOutputStream();
        //重置输出流
        response.reset();

        response.setContentType("application/vnd.ms-excel");
        //设置响应标题>这里浏览器会提示用户选择下载文件需要存放的路径>
        response.setHeader("Content-disposition", "attachment; fileName=" + new String((fileName + ".xlsx").getBytes(), "iso8859-1"));
        return os;
    }

    /**
     * excel通用模板下载
     *
     * @param response
     * @throws IOException
     */
    public static void exportFinAccount(List<User> result, String fileName, HttpServletResponse response) throws IOException {
        List<List<String>> bodyRow = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            User item = result.get(i);
            List<String> list = new ArrayList<>();
            list.add((i + 1) + "");
            list.add(item.getName() + "");
            list.add(item.getAge() + "");
            bodyRow.add(list);
        }
        List<String> titleRow = Arrays.asList(FIN_ACCOUNT);
        setExcelRespone(fileName, response);
        POIUitl.createExcel(response.getOutputStream(), "账户列表", titleRow, bodyRow);

    }

}
