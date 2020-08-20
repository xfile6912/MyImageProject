package com.ulsan.project.Model.Entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@ToString(exclude = {"company","wharf"})
@Accessors(chain=true)
public class Order {//TODO:ENTITY완성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String type;
    private String approvedStatus;
    private String wharfStartDate;
    private String wharfEndDate;
    private String applicantName;
    private String applicantPhoneNumber;
    private String applicantAddress;
    private String shipNumber;
    private int shipYear;
    private int shipInCount;
    private int shipApplyCount;
    private String shipName;
    private int shipWeight;
    private int shipLength;
    private int shipWidth;
    private String shipExpected;
    private int usageType;
    private int usageWork;
    private String facilityCode;
    private String approvedNumber;
    private int area;
    private String location;
    private String purpose;
    private String description;
    private String condition;
    private String downCargo1Kind;
    private String downCargo1Weight;
    private String downCargo2Kind;
    private String downCargo2Weight;
    private String downCargo3Kind;
    private String downCargo3Weight;
    private String upCargo1Kind;
    private String upCargo1Weight;
    private String upCargo2Kind;
    private String upCargo2Weight;
    private String upCargo3Kind;
    private String upCargo3Weight;
    private String deviceCargo1Kind;
    private String deviceCargo1Weight;
    private String deviceCargo1Number;
    private String deviceCargo2Kind;
    private String deviceCargo2Weight;
    private String deviceCargo2Number;
    private String feeExemptionReason;
    private LocalDate feeExemptionStartDate;
    private LocalDate feeExemptionEndDate;
    private LocalDate feeUsageStartDate;
    private LocalDate feeUsageEndDate;
    private String calculatedDescription;
    private int partExemption;
    private String partExemptionReason;
    private int totalFee;
    private LocalDate collectionRequestDate;
    private LocalDate dateOfPayment;
    private String note;
    private String appliedStatus;
    private String division;
    private LocalDate registeredAt;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Wharf wharf;
}
