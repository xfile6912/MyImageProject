package com.ulsan.project.Controller;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.Info;
import com.ulsan.project.Model.Network.Header;
import com.ulsan.project.Service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController implements CrudInterface<Info> {

    @Autowired
    private InfoService infoService;

    @Override
    public Header<Info> create(Header<Info> request) {
        return infoService.create(request);
    }

    @Override
    public Header<Info> read(Long id) {
        return infoService.read(id);
    }

    @Override
    public Header<Info> update(Header<Info> request) {
        return infoService.update(request);
    }

    @Override
    public Header delete(Long id) {
        return infoService.delete(id);
    }
}
