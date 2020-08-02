package com.example.test.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Folder {
    private String name;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private String withDescription;
}
