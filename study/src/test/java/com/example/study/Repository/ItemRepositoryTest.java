package com.example.study.Repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        Item item=new Item();
        item.setStatus("UNREGISTERED");
        item.setName("삼성노트북");
        item.setTitle("삼성노트북 A100");
        item.setContent("2019년형 노트북입니다.");
        item.setPrice(900000);
        item.setBrandName("삼성");
        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("Partner01");
//        item.setPartnerId(1L);
        Item newItem=itemRepository.save(item);
                Assert.assertNotNull(newItem);
    }
    @Test
    @Transactional
    public void read(){
        Long id=2L;
        Optional<Item> item=itemRepository.findById(id);

        Assert.assertTrue(item.isPresent());
       // item.ifPresent(i->{
       //    System.out.println(i);
       // });
    }
}
