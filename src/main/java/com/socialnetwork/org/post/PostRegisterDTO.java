package com.socialnetwork.org.post;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostRegisterDTO {
    private List<String> base64Photos;
    private String text;
}
