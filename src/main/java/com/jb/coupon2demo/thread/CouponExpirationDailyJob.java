package com.jb.coupon2demo.thread;

import com.jb.coupon2demo.repositories.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@Component
@EnableScheduling
public class CouponExpirationDailyJob {
    @Autowired
    private CouponRepo couponRepo;

//    @Autowired
//    TaskScheduler task;
//
//    public void scheduledJob(){
//        task.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    couponRepo.deleteCouponsByDate();
//                }catch (Exception err){
//                    System.out.println(err.getMessage());
//                    System.exit(1);
//                }
//            }
//        }, 86400000);
//    }
    /**
     * this function will run every day at 02:00 AM and erase every coupon that expired on the current date.
     */
    //TODO: SINGLETON, double check, synchronisation?
    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteByDate(){
        System.out.println("im start");
        couponRepo.deleteCouponsByDate();
    }
}

