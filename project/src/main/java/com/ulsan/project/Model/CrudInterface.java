package com.ulsan.project.Model;

import com.ulsan.project.Model.Network.Header;

public interface CrudInterface<T> {
    Header<T> create(Header<T> request);
    Header<T> read(Long id);
    Header<T> update(Header<T> request);
    Header delete(Long id);
}
