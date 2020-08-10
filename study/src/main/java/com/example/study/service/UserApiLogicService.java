package com.example.study.service;

import com.example.study.Repository.UserRepository;
import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest=request.getData();
        User user=User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser=userRepository.save(user);

        return response(user);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        //id->repository getOne, getById;
        return userRepository.findById(id).map(user->response(user)).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest=request.getData();
        Optional<User> optionalUser=userRepository.findById(userApiRequest.getId());
        return optionalUser.map(user->{
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setStatus(userApiRequest.getStatus())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
        })
                .map(user->userRepository.save(user))//update -> new User
                .map(user->response(user))//update된 유저
                .orElseGet(()-> Header.ERROR("데이터없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<User> optional =userRepository.findById(id);
        return optional.map(user->{
            userRepository.delete(user);
            return Header.OK();})
                .orElseGet(()->Header.ERROR("데이터없음"));
    }
    private Header<UserApiResponse> response(User user)
    {
        UserApiResponse userApiResponse=UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())//todo 암호화, 길이
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();
        //Header + data
        return Header.OK(userApiResponse);
    }
    //1.request data;
    //2. user생성
    //3. 생성된 데이터 ->UserApiResponse return;
}
