package util;

import java.text.DecimalFormat;

/**
 * Created by violetMoon on 2015/10/24.
 */
public class NumberUtil {
    /**
     * 对数据进行四舍五入
     * @param data 要舍入的浮点数
     * @param decimalLength 舍入后的小数长度
     * @return 四舍五入后的数据
     */
    public static double round (double data,int decimalLength){//@methodBegin
        double mul = (int)Math.pow(10, decimalLength);
        int intValue = (int)(data * mul);
        return intValue / mul;
       /* char[] formatStr=new char[1+decimalLength];
        formatStr[0]='.';
        for(int i=1;i<formatStr.length;i++)
            formatStr[i]='#';
        DecimalFormat dr=new DecimalFormat(new String(formatStr));
        int result = Double.valueOf(dr.format(data));*/

    }
}
