package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.Item;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.service.ItemApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/item")
public class ItemApiController extends CrudController<ItemApiRequest, ItemApiResponse, Item> {
   @Autowired
   private ItemApiLogicService itemApiLogicService;

   @PostConstruct
   public void init(){
        this.baseService=itemApiLogicService;
   }
}
