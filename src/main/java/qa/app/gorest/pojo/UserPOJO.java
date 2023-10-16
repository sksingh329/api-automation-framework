package qa.app.gorest.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPOJO {
    private String name;
    private String email;
    private String gender;
    private String status;
}
