package models;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

}