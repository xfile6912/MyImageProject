package com.ulsan.project.Model.Entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@ToString(exclude = {"orderList","useInfoList"})
@Accessors(chain=true)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String code;
    private String name;
    private int warnCount;

    @OneToMany(fetch= FetchType.LAZY, mappedBy = "company")
    private List<Order> orderList;

    @OneToMany(fetch= FetchType.LAZY, mappedBy = "company")
    private List<UseInfo> useInfoList;
}
