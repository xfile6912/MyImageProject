package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController implements CrudInterface<UserApiRequest,UserApiResponse> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @GetMapping("")
    public Header<List<UserApiResponse>> search(@PageableDefault(sort="id", direction = Sort.Direction.ASC, size = 15) Pageable pageable)
    {
        log.info("{}", pageable);
        return userApiLogicService.search(pageable);
    }

    @Override
    @PostMapping("")///api/user
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
       log.info("{}",request);
       return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("/{id}")///api/user/{id}
    public Header<UserApiResponse> read(@PathVariable("id") Long id)
    {
        log.info("read id : {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("") // /api/user
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {
        return userApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("/{id}")
    public Header delete(@PathVariable("id") Long id) {
        return userApiLogicService.delete(id);
    }
}
