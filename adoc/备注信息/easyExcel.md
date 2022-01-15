easyexcel注解

参考地址：https://www.cnblogs.com/bluekang/p/13438666.html

```xml
<!--office工具-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>2.2.10</version>
</dependency>
```
11个注解
```java
@ExcelProperty
@ColumnWith 列宽
@ContentFontStyle 文本字体样式
@ContentLoopMerge 文本合并
@ContentRowHeight 文本行高度
@ContentStyle 文本样式
@HeadFontStyle 标题字体样式
@HeadRowHeight 标题高度
@HeadStyle 标题样式
@ExcelIgnore 忽略项
@ExcelIgnoreUnannotated 忽略未注解
```
@ExcelProperty
必要的一个注解，注解中有三个参数value,index,converter分别代表列明，列序号，数据转换方式
value和index只能二选一，通常不用设置converter
1.value 通过标题文本对应
2.index 通过文本行号对应
3.converter 转换器，通常入库和出库转换使用，如性别入库0和1，出库男和女

最佳实践
```
public class ImeiEncrypt {
    @ExcelProperty(value = "值")
    private String valueField;
    
    @ExcelProperty(value = 1,converter =IndustryIdConverter.class)
    private String indexField;
    
    @ExcelProperty(value = "值对应和转换器",converter =IndustryIdConverter.class)
    private String valueAndConverterField;
}
```

