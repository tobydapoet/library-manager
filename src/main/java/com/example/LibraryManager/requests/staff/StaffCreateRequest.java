package com.example.LibraryManager.requests.staff;

import com.example.LibraryManager.enums.StaffPosition;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffCreateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private StaffPosition position;

    private Integer salary;

    private MultipartFile file;
}
