package com.ulsan.project.Model.Entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString(exclude = {"orderList","useInfoList"})
@Builder
@Accessors(chain=true)
public class Wharf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;//야적장 이름
    private String kind;//야적장 종류
    private int area;//야적장 면적
    private int exemptionArea;
    @OneToMany(fetch= FetchType.LAZY, mappedBy = "wharf")
    private List<Order> orderList;

    @OneToMany(fetch= FetchType.LAZY, mappedBy = "wharf")
    private List<UseInfo> useInfoList;
}
