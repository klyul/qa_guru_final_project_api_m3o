package models;

/*

{
    "id": "db.create",
    "code": 400,
    "detail": "missing record",
    "status": "Bad Request"
}

 */

import lombok.Data;

@Data
public class ErrorModel {

    public String id;
    public int code;
    public String detail;
    public String status;

}

