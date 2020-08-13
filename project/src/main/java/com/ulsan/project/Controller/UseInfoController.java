package com.ulsan.project.Controller;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.UseInfo;
import com.ulsan.project.Model.Network.Header;
import com.ulsan.project.Service.UseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/useinfo")
public class UseInfoController implements CrudInterface<UseInfo> {

    @Autowired
    private UseInfoService useInfoService;

    @Override
    public Header<UseInfo> create(Header<UseInfo> request) {
        return useInfoService.create(request);
    }

    @Override
    public Header<UseInfo> read(Long id) {
        return useInfoService.read(id);
    }

    @Override
    public Header<UseInfo> update(Header<UseInfo> request) {
        return useInfoService.update(request);
    }

    @Override
    public Header delete(Long id) {
        return useInfoService.delete(id);
    }
}
