package cn.cherish.springcloud.web.controller;

import cn.cherish.springcloud.service.FeignProductService;
import cn.cherish.springcloud.service.req.ProductReq;
import me.cherish.web.MResponse;
import me.cherish.web.controller.ABaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品接口
 * @author Cherish
 * @version 1.0
 * @date 2017/6/13 21:59
 */
@RestController
@RequestMapping("/product")
public class FeignProductController extends ABaseController {

    private final FeignProductService feignProductService;

    @Autowired
    public FeignProductController(FeignProductService feignProductService) {
        this.feignProductService = feignProductService;
    }

    @GetMapping(value = "/list")
    public MResponse getList() {
        MResponse response = feignProductService.getList();
        log.info("【获取到的数据】 {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public MResponse show(@PathVariable Long id) {
        return feignProductService.show(id);
    }

    @PostMapping("/save")
    public MResponse save(@RequestBody ProductReq productReq) {
        log.info("【保存新商品】 {}", productReq);
        return feignProductService.save(productReq);
    }

    @DeleteMapping(value="/{id}")
    public MResponse delete(@PathVariable Long id){
        return feignProductService.delete(id);
    }

    @GetMapping("/quantity/{id}")
    public MResponse findQuantity(@PathVariable("id") Long id) {
        return feignProductService.findQuantity(id);
    }

    @PostMapping("/order")
    public MResponse order(@RequestParam Long productId,
                           @RequestParam Integer num) {
        Long userId = 1L;
        return feignProductService.order(userId, productId, num);
    }

    /**
     * 刷新商品库存在redis中的缓存
     */
    @GetMapping("/quantity/flush")
    public void flushQuantity(){
        feignProductService.flushQuantity();
    }


}
