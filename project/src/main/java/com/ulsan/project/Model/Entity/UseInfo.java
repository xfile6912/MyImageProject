package com.ulsan.project.Model.Entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"company","wharf"})
@Entity
@Builder
@Accessors(chain=true)
public class UseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private int area;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Wharf wharf;
}
