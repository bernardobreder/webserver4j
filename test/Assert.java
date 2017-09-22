import java.io.IOException;
import java.io.InputStream;

public class Assert extends junit.framework.Assert {

	public static void assertEquals(InputStream input1, InputStream input2) throws IOException {
		for (;;) {
			int c1 = input1.read();
			int c2 = input2.read();
			if (c1 != c2) {
				throw new AssertionError();
			}
			if (c1 < 0) {
				break;
			}
		}
	}

}
