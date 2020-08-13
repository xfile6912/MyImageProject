package com.ulsan.project.Controller;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.Wharf;
import com.ulsan.project.Model.Network.Header;
import com.ulsan.project.Service.WharfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wharf")
public class WharfController implements CrudInterface<Wharf> {

    @Autowired
    private WharfService wharfService;

    @Override
    public Header<Wharf> create(Header<Wharf> request) {
        return wharfService.create(request);
    }

    @Override
    public Header<Wharf> read(Long id) {
        return wharfService.read(id);
    }

    @Override
    public Header<Wharf> update(Header<Wharf> request) {
        return wharfService.update(request);
    }

    @Override
    public Header delete(Long id) {
        return wharfService.delete(id);
    }
}
