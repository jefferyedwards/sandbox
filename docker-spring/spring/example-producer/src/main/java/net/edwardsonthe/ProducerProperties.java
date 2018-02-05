package net.edwardsonthe;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties("producer")
@NoArgsConstructor
@Data
public class ProducerProperties {
  
  /**
   * The message returned by the <code>Producer</code> via <code>/message</code> endpoint.
   */
  private String message;
  
}
