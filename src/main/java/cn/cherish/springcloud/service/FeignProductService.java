package cn.cherish.springcloud.service;

import cn.cherish.springcloud.service.dto.ProductDTO;
import cn.cherish.springcloud.service.req.ProductReq;
import me.cherish.web.MResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cherish
 * @version 1.0
 * @date 2017/6/13 21:58
 */
@FeignClient(value = "service-product")
public interface FeignProductService {

    @GetMapping("/product/{id}")
    MResponse<ProductDTO> show(@PathVariable("id") Long id);

    @GetMapping("/product/list")
    MResponse<List<ProductDTO>> getList();

    @PostMapping("/product/save")
    MResponse save(@RequestBody ProductReq productReq);

    @DeleteMapping("/product/{id}")
    MResponse delete(@PathVariable("id") Long id);

    /**
     * 刷新商品库存在redis中的缓存
     */
    @GetMapping("/product/quantity/flush")
    void flushQuantity();

    @GetMapping("/product/quantity/{id}")
    MResponse findQuantity(@PathVariable("id") Long id);

    @PostMapping("/product/order")
    MResponse order(@RequestParam("userId") Long userId,
                    @RequestParam("productId") Long productId,
                    @RequestParam("num") Integer num);

}
