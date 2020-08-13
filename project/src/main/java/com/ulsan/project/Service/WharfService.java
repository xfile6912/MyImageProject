package com.ulsan.project.Service;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.Wharf;
import com.ulsan.project.Model.Network.Header;
import org.springframework.stereotype.Service;

@Service
public class WharfService implements CrudInterface<Wharf> {
    @Override
    public Header<Wharf> create(Header<Wharf> request) {
        return null;
    }

    @Override
    public Header<Wharf> read(Long id) {
        return null;
    }

    @Override
    public Header<Wharf> update(Header<Wharf> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
