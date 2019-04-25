package cn.edu.pku.hcst.kincoder.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeUtil {
    /**
     * 从类的全限定名称中提取简化名称，即以'.'分割后的最后一段字符串
     * 例：
     * Object => Object
     * java.lang.Object => Object
     * org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener => SheetRecordCollectingListener
     * @param qualifiedName 类的全限定名称
     * @return 类的简化名称
     */
    public String qualifiedName2Simple(String qualifiedName) {
        var segments = qualifiedName.split("\\.");
        return segments[segments.length - 1];
    }
}
