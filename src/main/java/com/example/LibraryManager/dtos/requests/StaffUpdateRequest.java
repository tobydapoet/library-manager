package com.example.LibraryManager.dtos.requests;

import com.example.LibraryManager.enums.StaffPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffUpdateRequest {
    private String name;
    private String phone;
    private StaffPosition position;
    private Integer salary;
    private boolean isActive;
    private String avatar_url;
    private MultipartFile file;

}
