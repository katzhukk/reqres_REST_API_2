package models.lombok;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoundResponceUserLombokModel {
    List<Data> data = new ArrayList<>();

    @lombok.Data
    public static class Data {
        int id;
        String email;
        String first_name;
        String last_name;
        String avatar;
    }
}
