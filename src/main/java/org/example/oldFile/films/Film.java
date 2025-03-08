package org.example.oldFile.films;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    private String name;
    private String annotation;
    private double rating;

}
