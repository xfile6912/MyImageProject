package com.ulsan.project.Service;

import com.ulsan.project.Model.CrudInterface;
import com.ulsan.project.Model.Entity.UseInfo;
import com.ulsan.project.Model.Network.Header;
import org.springframework.stereotype.Service;

@Service
public class UseInfoService implements CrudInterface<UseInfo> {
    @Override
    public Header<UseInfo> create(Header<UseInfo> request) {
        return null;
    }

    @Override
    public Header<UseInfo> read(Long id) {
        return null;
    }

    @Override
    public Header<UseInfo> update(Header<UseInfo> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
