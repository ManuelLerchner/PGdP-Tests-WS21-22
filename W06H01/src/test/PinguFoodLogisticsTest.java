package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pgdp.saleuine2.AmountOrder;
import pgdp.saleuine2.PinguFoodLogistics;
import pgdp.saleuine2.WeightOrder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PinguFoodLogisticsTest {
  private PrintStream old;
  private ByteArrayOutputStream baos = new ByteArrayOutputStream();
  
  @BeforeEach
  void PrepareConsole() {
    baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    System.setOut(ps);
  }
  
  @AfterEach
  void ResetConsole() {
    System.out.flush();
    System.setOut(old);
  }
  
  @Test
  void registerUnusedFoodTest() {
    PinguFoodLogistics pfl1 = new PinguFoodLogistics(BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(15));
    pfl1.printWasteStatistics();
    
    assertTrue(baos.toString().contains("Bisher konnten 0 Tiere mit einem Gesamtgewicht von 0g nicht verwertet werden.\n" +
            "Claudia und Karl-Heinz ist dadurch ein Profit von 0PD entgangen."));
    
    pfl1.acceptNewOrder(new AmountOrder(1, 2, 3));
    pfl1.clearOrderBook();
    PrepareConsole();
    pfl1.printWasteStatistics();
    
    //assertEquals("", baos.toString());
    assertTrue(baos.toString().contains("Bisher konnten 15 Tiere mit einem Gesamtgewicht von 2373g nicht verwertet werden.\n" +
            "Claudia und Karl-Heinz ist dadurch ein Profit von 35595PD entgangen."));
    
    pfl1.acceptNewOrder(new WeightOrder(1000));
    pfl1.clearOrderBook();
    PrepareConsole();
    pfl1.printWasteStatistics();
    
    assertTrue(baos.toString().contains("Bisher konnten 37 Tiere mit einem Gesamtgewicht von 4959g nicht verwertet werden.\n" +
            "Claudia und Karl-Heinz ist dadurch ein Profit von 73735PD entgangen."));
  }
}
