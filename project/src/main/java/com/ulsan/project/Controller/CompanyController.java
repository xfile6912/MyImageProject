package com.ulsan.project.Controller;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.Company;
import com.ulsan.project.Model.Network.Header;
import com.ulsan.project.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController implements CrudInterface<Company> {

    @Autowired
    private CompanyService companyService;

    @Override
    public Header<Company> create(Header<Company> request) {
        return companyService.create(request);
    }

    @Override
    public Header<Company> read(Long id) {
        return companyService.read(id);
    }

    @Override
    public Header<Company> update(Header<Company> request) {
        return companyService.update(request);
    }

    @Override
    public Header delete(Long id) {
        return companyService.delete(id);
    }
}
