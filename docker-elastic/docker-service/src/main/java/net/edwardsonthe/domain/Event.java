package net.edwardsonthe.domain;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {

  static final class InstantSerializer extends StdSerializer<Instant> {

    private static final long serialVersionUID = 1L;


    protected InstantSerializer() {
      this(null);
    }

    protected InstantSerializer(Class<Instant> t) {
      super(t);
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
      gen.writeString(value.toString());
    }

  };


  private String name;

  private String data;

  @JsonSerialize(using = InstantSerializer.class)
  private Instant timestamp;


  @JsonCreator
  public Event(@JsonProperty("name") String name, @JsonProperty("data") String data, @JsonProperty("timestamp") String timestamp) {
    this.timestamp = Instant.parse(timestamp);
    this.name = name;
    this.data = data;
  }

  public static void main(String... args) throws IOException {
    Event event = new Event("altitude", "35000", Instant.now());
    System.out.println("original: " + event);
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(event);
    System.out.println(json);
    Instant instant = Instant.parse("2019-09-18T17:17:33.420822Z");
    System.out.println(instant);
    Event event2 = mapper.readValue(json, Event.class);
    System.out.println("converted: " + event2);
  }

}