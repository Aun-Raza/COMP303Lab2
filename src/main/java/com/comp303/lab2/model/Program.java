package com.comp303.lab2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "programs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Program {
    @Id
    @Min(value = 100, message = "Program Code must be at least 3 digits")
    @Max(value = 1000000, message = "Program Code cannot be over 6 digits")
    private int programCode;

    @NotBlank(message = "Program Name is mandatory")
    @Size(min = 5, message = "Program Name should be at least 5 characters")
    private String programName;

    @Min(value = 1, message = "Duration length should be at 1 semester")
    private int duration;

    @Min(value = 0, message = "Fee should be at least 0")
    private Double fee;
}
