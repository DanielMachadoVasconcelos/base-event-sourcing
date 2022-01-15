package br.ead.home.events;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@Document(indexName = "event-store")
public class EventModel {

    @Id
    String id;

    @NotNull
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'")
    Date createdAt;

    @NotBlank
    @Field(type = FieldType.Keyword)
    String aggregatedIdentifier;

    @NotBlank
    @Field(type = FieldType.Keyword)
    String aggregateType;

    @Min(0)
    @Field(type = FieldType.Integer)
    int version;

    @NotBlank
    @Field(type = FieldType.Keyword)
    String eventType;

    @NotNull
    @Field(type = FieldType.Object)
    BaseEvent eventData;

}
