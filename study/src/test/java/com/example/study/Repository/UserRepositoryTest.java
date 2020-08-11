package com.example.study.Repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {
    //dependency injection(DI)
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String account = "Test03";
        String password="Test03";
        String status="REGISTERED";
        String email="Test01@gmail.com";
        String phoneNumber="010-1111-3333";
        LocalDateTime registeredAt=LocalDateTime.now();
        LocalDateTime createdAt=LocalDateTime.now();
        String createcBy="AdminServer";
        User user=new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        User newUser=userRepository.save(user);
        Assert.assertNotNull(newUser);
    }
    @Test
    @Transactional
    public void read(){
        User user=userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");
        if(user!=null) {
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("주문 묶음");

                System.out.println(orderGroup.getRevName());
                System.out.println(orderGroup.getRevAddress());
                System.out.println(orderGroup.getTotalPrice());
                System.out.println(orderGroup.getTotalQuantity());
                System.out.println("주문상세");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름: "+orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리: "+orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문상품: "+orderDetail.getItem().getName());
                    System.out.println("고객센터: "+orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문상태:"+orderDetail.getStatus());
                    System.out.println("도착예정일자:"+orderDetail.getArrivalDate());

                });

            });
        }
        Assert.assertNotNull(user);
    }
    @Test
    @Transactional
    public void update(){
        Optional<User> user=userRepository.findById(2L);
        user.ifPresent(selectUser->{
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");
            userRepository.save(selectUser);
        });
    }
    @Test
    @Transactional
    public void delete()
    {
        Optional<User> user=userRepository.findById(3L);

        Assert.assertTrue(user.isPresent());
        user.ifPresent(selectUser->{
           userRepository.delete(selectUser);
        });
        Optional<User> deleteUser=userRepository.findById(3L);
        Assert.assertFalse(deleteUser.isPresent());
    }
}
