// 代码生成时间: 2025-09-22 04:20:44
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.List;
import java.util.Set;

// Quartz定时任务调度器
public class ScheduledTaskManager {

    // 定义常量
    private static final String JOB_GROUP = "myJobGroup";
    private static final String TRIGGER_GROUP = "myTriggerGroup";

    // 创建并配置调度器
    public static void scheduleJob() throws SchedulerException {
        // 创建调度器工厂
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // 获取调度器实例
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 启动调度器
        scheduler.start();

        // 定义一个JobDetail实例，绑定Job实现类
        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", JOB_GROUP)
                .build();

        // 定义一个Trigger实例，设置触发规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", TRIGGER_GROUP)
                .startNow() // 立即启动
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")) // 每10秒执行一次
                .build();

        // 调度任务
        scheduler.scheduleJob(job, trigger);
    }

    // MyJob类实现Job接口
    public static class MyJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // 任务执行逻辑
            System.out.println("Executing scheduled job...");
            // 可以在这里添加任务逻辑代码
        }
    }

    // 主方法，用于启动调度器和调度任务
    public static void main(String[] args) {
        try {
            scheduleJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 添加一个方法来暂停所有任务
    public static void pauseAllJobs() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.pauseJobs(GroupMatcher.jobGroupEquals(JOB_GROUP));
        System.out.println("All jobs are paused.");
    }

    // 添加一个方法来恢复所有任务
    public static void resumeAllJobs() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.resumeJobs(GroupMatcher.jobGroupEquals(JOB_GROUP));
        System.out.println("All jobs are resumed.");
    }

    // 添加一个方法来删除所有任务
    public static void deleteAllJobs() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(JOB_GROUP));
        for (JobKey jobKey : jobKeys) {
            scheduler.deleteJob(jobKey);
        }
        System.out.println("All jobs are deleted.");
    }

    // 添加一个方法来获取所有任务
    public static void listAllJobs() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(JobKey.jobKey("myJob", JOB_GROUP));
        for (Trigger trigger : triggers) {
            System.out.println("Trigger: " + trigger.getKey());
        }
        System.out.println("Total jobs: " + triggers.size());
    }
}