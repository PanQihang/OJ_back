package com.oj.entity.other;

import com.oj.service.exam.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class OJTimerLink {
    private static Logger log = LoggerFactory.getLogger("OJTimerLink");
    private static Map timerData=null;
    private static TestService FinalService;
    @Resource//@Autowired
    private TestService service;
    @PostConstruct //初始化操作
    public void init(){ //初始化操作----------------- web应用初始化调用
        FinalService = service;
        timerData = new HashMap<String, OJTimerCell>();
        if(null == FinalService || null == timerData){
            log.info("一次性定时器模块/初始失败");
            return ;
        }
        /*
            要进行的初始化操作（如获取正在进行的（实验/考试），并为其逐个添加一次性定时器
         */
        resestTimerLink();
    }
    public static boolean  addTimerCell(String timerId, String timeString, TimerTask progress){ //增加扩展性（调用的使用需要传入一个TimerTask类或其子类，该类内定义了使用者希望指定时间执行的操作）
        log.info("新增定时任务/"+timerId+"/"+timeString+"/"+progress.getClass());
        try{
            if(null!=timerData.get(timerId)) return false;
            timerData.put(timerId, new OJTimerCell(timerId, timeString, progress));
        }catch(Exception e){
            log.info("添加定时任务时错误/"+e);
            return false;
        }
        return true;
    }
    public static boolean addTimerCell(String testId, String timeString){//给定考试编号(唯一)与考试结束时间, 新增定时器
        class TimerProgress extends TimerTask {
            String testId;
            public TimerProgress(String tid){
                super();
                this.testId = tid;
            }
            public void run(){
                try {
                    Thread.sleep(1000 * 60 * 5); //通过sleep来实现延时执行
                    /*
                    这里添加一次性定时器要执行的任务
                     */
                    System.out.println("#################OJTimerLink/addTimerCell/TimerProgress/run: 一次性定时器("+testId+")准备执行");
                    FinalService.FunctionLY(testId);  //更新指定考试的成绩
                    FinalService.FunctionPQH(testId); //生成指定考试的相似性结果
                    System.out.println("#################OJTimerLink/addTimerCell/TimerProgress/run: 一次性定时器("+testId+")执行完成");
                }catch(Exception e){
                    log.info("定时器执行过程异常或错误/"+e);
                }finally{
                    OJTimerLink.delTimerCell(testId); //线程执行完后，必须注销与其对应的定时器
                }
            }
        }
        //System.out.println("一次性定时器的时间:“"+timeString);
        return addTimerCell(testId, timeString, new TimerProgress(testId));
    }
    public static boolean delTimerCell(String testId){//通过给定指定的考试编号，删除对应的定时器
        OJTimerCell temp = (OJTimerCell)(timerData.get(testId));
        if(null==temp){
            log.info("删除一次性定时器/"+testId+"失败/对应定时器未找到");
            return false;
        }
        Timer timer = temp.getTimer();
        timer.cancel(); //要先把定时器停了，不然即使没有任务了它也不会消失
        timerData.remove(testId);
        log.info("删除一次性定时器/"+testId+"成功");
        return true;
    }
    public static void resestTimerLink(){ //重置现有一次性定时器
        List<String> list = new LinkedList<String>(timerData.keySet());
        for(String key : list){
            delTimerCell(key);
        }
        List<Map> listA = FinalService.getCurrentTestList();
        for(Map<String, Object> temp : listA){
            //请特别注意时区问题，有很大影响
            //System.out.println("从数据库获取到的时间与考试编号："+temp.get("testId").toString()+", "+temp.get("testId").getClass()+", "+temp.get("testEDate").toString()+", "+temp.get("testEDate").getClass());
            //System.out.println("Java格式转换后结果："+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(temp.get("testEDate")));
            addTimerCell(temp.get("testId").toString(), temp.get("testEDate").toString());
        }
    }
    public static void queryTimerCell(List<Map> tdata){ //在这进行进行中（实验/考试）数据的获取调用似乎不妥
        //for(Map<String, String> temp : tdata)
            //addTimerCell(temp.get("testId"), temp.get("time"));
    }
}
