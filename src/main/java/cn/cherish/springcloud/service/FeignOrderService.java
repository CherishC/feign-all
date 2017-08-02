package cn.cherish.springcloud.service;

import cn.cherish.springcloud.service.dto.OrderDTO;
import cn.cherish.springcloud.service.req.OrderReq;
import me.cherish.web.MResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cherish
 * @version 1.0
 * @date 2017/6/13 21:58
 */
@FeignClient(value = "service-order")
public interface FeignOrderService {

    @GetMapping("/order/{id}")
    MResponse<OrderDTO> show(@PathVariable("id") Long id);

    @GetMapping("/order/list")
    MResponse<List<OrderDTO>> getList();

    @GetMapping("/order/listByUserId/{userId}")
    MResponse listByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/order/create")
    MResponse create(@RequestBody OrderReq orderReq);

    @DeleteMapping("/order/{id}")
    MResponse delete(@PathVariable("id") Long id);

}
