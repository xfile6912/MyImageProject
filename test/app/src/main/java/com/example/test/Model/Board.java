package com.example.test.Model;

import com.nostra13.universalimageloader.utils.L;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    private Long id;
    private String title;
    private String location;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
