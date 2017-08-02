package cn.cherish.springcloud.service;

import cn.cherish.springcloud.service.dto.UserDTO;
import cn.cherish.springcloud.service.req.UserReq;
import me.cherish.web.MResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cherish
 * @version 1.0
 * @date 2017/6/13 21:58
 */
@FeignClient(value = "service-user")
public interface FeignUserService {

    @GetMapping("/user/list")
    MResponse<List<UserDTO>> getList();

    @GetMapping("/user/findByUsername/{username}")
    MResponse<UserDTO> findByUsername(@PathVariable("username") String username);

    @GetMapping("/user/{id}")
    MResponse<UserDTO> findOne(@PathVariable("id") Long id);

    @PostMapping("/user/save")
    MResponse save(@RequestBody UserReq userReq);

    @PutMapping("/user/update")
    MResponse update(@RequestBody UserReq userReq);

    @DeleteMapping("/user/{id}")
    MResponse delete(@PathVariable("id") Long id);


}
