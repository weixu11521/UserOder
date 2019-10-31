package com.zydsj.userorder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@FeignClient(name = "user-order-repomodule",fallback = RepoFeignCallBack.class)
public interface RepoFeignClient {
        @GetMapping("/findPrice/{id}")
        public Double findPrice(@PathVariable(value="id") Integer id);
}
@Component
class RepoFeignCallBack implements  RepoFeignClient{

        @Override
        public Double findPrice(Integer id) {
                return 1.0;
        }
}
