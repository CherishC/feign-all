package cn.cherish.springcloud.web.controller;

import cn.cherish.springcloud.service.FeignOrderService;
import cn.cherish.springcloud.service.req.OrderReq;
import me.cherish.web.MResponse;
import me.cherish.web.controller.ABaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单接口
 * @author Cherish
 * @version 1.0
 * @date 2017/6/13 21:59
 */
@RestController
@RequestMapping("/order")
public class FeignOrderController extends ABaseController {

    private final FeignOrderService feignOrderService;

    @Autowired
    public FeignOrderController(FeignOrderService feignOrderService) {
        this.feignOrderService = feignOrderService;
    }

    @GetMapping(value = "/list")
    public MResponse getList() {
        MResponse response = feignOrderService.getList();
        log.info("【获取到的数据】 {}", response);
        return response;
    }

    @GetMapping("/my")
    public MResponse myOrders() {
        Long userId = 1L;
        MResponse response = feignOrderService.listByUserId(userId);
        log.info("【获取到的数据】 {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public MResponse show(@PathVariable Long id) {
        return feignOrderService.show(id);
    }

    @PostMapping("/create")
    public MResponse create(@RequestBody OrderReq orderReq) {
        log.info("【产生新订单】 {}", orderReq);
        return feignOrderService.create(orderReq);
    }

    @DeleteMapping(value="/{id}")
    public MResponse delete(@PathVariable Long id){
        return feignOrderService.delete(id);
    }


}
