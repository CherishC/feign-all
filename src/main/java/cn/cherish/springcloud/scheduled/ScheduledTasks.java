package cn.cherish.springcloud.scheduled;

import cn.cherish.springcloud.service.FeignProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTasks {

    private final FeignProductService feignProductService;

    @Autowired
    public ScheduledTasks(FeignProductService feignProductService) {
        this.feignProductService = feignProductService;
    }

    /**
     * 每十分钟的计划任务，把商品库存量的缓存搞掂
     */
    @Scheduled(cron = "30 0/10 * * * ?")
    public void flushQuantityTask(){
        log.info("把商品库存量的缓存搞掂，刷新商品库存在redis中的缓存");
        feignProductService.flushQuantity();
    }


}