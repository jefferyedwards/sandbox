package net.edwardsonthe.vending;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;

import lombok.NoArgsConstructor;

/**
 * Override the default shell application runner to avoid getting stuck in the jline loop.
 * 
 * @author jeff
 */
@TestConfiguration
@NoArgsConstructor
public class CliRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // ignore ...
  }

}
