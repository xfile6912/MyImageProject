package com.ulsan.project.Service;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.Company;
import com.ulsan.project.Model.Network.Header;
import org.springframework.stereotype.Service;

@Service
public class CompanyService implements CrudInterface<Company> {
    @Override
    public Header<Company> create(Header<Company> request) {
        return null;
    }

    @Override
    public Header<Company> read(Long id) {
        return null;
    }

    @Override
    public Header<Company> update(Header<Company> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
