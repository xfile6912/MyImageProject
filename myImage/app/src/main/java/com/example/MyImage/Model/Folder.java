package com.example.MyImage.Model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Folder implements Serializable {
    private String name;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private String withDescription;
    private String repImage;
}
