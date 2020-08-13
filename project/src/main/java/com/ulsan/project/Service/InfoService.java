package com.ulsan.project.Service;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.Info;
import com.ulsan.project.Model.Network.Header;
import org.springframework.stereotype.Service;

@Service
public class InfoService implements CrudInterface<Info> {
    @Override
    public Header<Info> create(Header<Info> request) {
        return null;
    }

    @Override
    public Header<Info> read(Long id) {
        return null;
    }

    @Override
    public Header<Info> update(Header<Info> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
