package com.example.demo.dto.post.form;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostIdListForm {
    List<Long> postIdList = new ArrayList<>();
}
