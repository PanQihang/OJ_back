/*


一次性定时器的持久化类
 */


package com.oj.entity.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import static java.lang.System.out;

public class OJTimerCell {

    private String testId;
    private Timer timer;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public OJTimerCell(String testId, String timeString, TimerTask progress) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timer timer=null;
        try {
            Date time;
            //System.out.println("定时器内部：时间字符串转换前："+timeString);
            //timeString="2019-05-04 17:30:29";
            time = sdf.parse(timeString);  //正确格式:"2019-04-25 17:19:20"
            timer = new Timer();
            //out.println("定时器内部，时间字符串转换后的时间:"+timer);
            timer.schedule(progress, time);
        }catch(Exception e){
            throw e;
        }
        this.testId = testId;
        this.timer = timer;

    }
}
