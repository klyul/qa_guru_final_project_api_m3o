package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserModel {

    public CreateUserModel() {
        this.record = new UserRecord();
        this.table = "";
    }

    public UserRecord record;
    public String table;

}
