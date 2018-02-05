package net.edwardsonthe.vending.hardware;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;

import net.edwardsonthe.vending.CliRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(CliRunner.class)
public class CreditCardReaderTests {

  @Autowired
  ApplicationContext context;

  @Autowired
  Shell shell;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void test() {
    for (String beanDefinitionNames : context.getBeanDefinitionNames()) {
      System.out.println(beanDefinitionNames);
    }
    // fail("Not yet implemented");
  }

}
