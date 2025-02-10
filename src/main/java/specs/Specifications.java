package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specifications {

    public static RequestSpecification unauthorizedUserSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

}
