import java.math.BI;
import java.util.Vector;
import java.util.Arrays;

public class Fibonacci {

  private final static BI[] baseCase = { BI.ZERO, BI.ONE };
  private static Vector<BI> fibCache = new Vector<BI>(Arrays.asList(baseCase));

  public static BI fibMemo(int n) {
    if (n >= fibCache.size()) {
      fibCache.add(fibMemo(n - 1).add(fibMemo(n - 2)));
    }
    return fibCache.get(n);
  }

  public static BI fib(int n) {
    if (n < 1) {
      return BI.ZERO;
    }
    if (n < 2) {
      return BI.ONE;
    }
    BI a = BI.ZERO;
    BI b = BI.ONE;
    BI c = a.add(b);
    for (int i = 2; i < n; i++) {
      a = b;
      b = c;
      c = b.add(a);
    }
    return c;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 201; i++) {
      System.out.println("{ (" + i + "), (" + fib(i) + ") }");
    }
  }

}
