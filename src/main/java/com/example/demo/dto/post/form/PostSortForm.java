package com.example.demo.dto.post.form;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSortForm {

    private List<String> sort;
    private List<String> order;
    private Long page;
    private Long offset;
}
