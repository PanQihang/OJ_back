package com.oj.service.serviceImpl.other;

//import com.oj.entity.education.Student;
import com.oj.entity.education.Student;
import com.oj.service.education.StudentService;
import com.oj.service.other.ImportService;
import com.oj.service.serviceImpl.education.StudentServicelmpl;
/*
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * @author xielanning
 * @Time 2019年4月4日 10点41分
 * @Description 处理前端传来的excel文件
 */
public class ImportServicelmpl implements ImportService {

    private StudentService service = new StudentServicelmpl();



    //检查对应字符串是否符号规范，并返回一个student对象
    public Student checkB(String account, String name, String classId ) throws Exception{

        //Pattern p=Pattern.compile("^[0-9]*[1-9][0-9]*$");
        //Map<String, String> map=null;
        //Matcher m=p.matcher(bot);
        if(null==account || null==name || account.equals("") || name.equals("")) throw new Exception("学号、姓名都不能为空");
        if( account.getBytes().length>64) throw new Exception("学号不符合不能超过64位");
        if(name.getBytes().length>50) throw new Exception("姓名长度不能超过50位");
        return new Student(account, " ", name, classId);

    }
    public Student check(String account, String name, String className ) throws Exception{
        //暂时未用到的函数
        Pattern p=Pattern.compile("^[0-9]*[1-9][0-9]*$");
        //Map<String, String> map=null;
        //Matcher m=p.matcher(bot);
        if(null==account || null==name || null==className) throw new Exception("学号、姓名和班级名称都不能为空");
        System.out.println("#length:"+account.getBytes().length);
        if( account.getBytes().length>64) throw new Exception("学号不符合不能超过64位");
        if(name.getBytes().length>50) throw new Exception("姓名长度不能超过50位");
        if(className.getBytes().length>40) throw new Exception("班级长度不能超过40位");
        System.out.println("#flag2");
        if(null==service)
            System.out.println("#service is null");
        className = service.getTheClassIdByName(className); //这里有问题！！！！
        System.out.println("#flag1");
        if(null==className) throw new Exception("未找到对应名称的班级");
        System.out.println("#flag");
        return new Student(account, " ", name, className);

    }
}
