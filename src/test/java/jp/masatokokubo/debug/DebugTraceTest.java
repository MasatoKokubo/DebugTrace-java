/*
	DebugTraceTest.java

	Created on 2014/06/21.
	(C) Masato Kokubo
*/
package jp.masatokokubo.debug;

/**
	Test class for DebugTrace class.
	@since 1.0.0
	@author Masato Kokubo
*/
public class DebugTraceTest {
	public static void main(String[] args) {
	/**/DebugTrace.enter();
	/**/DebugTrace.println("args", args);

		DebugTraceTest test = new DebugTraceTest();
		test.test();

	/**/DebugTrace.leave();
	}

	private void test() {
	/**/DebugTrace.enter();

		TestBean testBean = new TestBean();
		testBean.booleanValue = true;
		testBean.charValue    = 'A';
		testBean.byteValue    = (byte)   127;
		testBean.shortValue   = (short)32767;
		testBean.intValue     = 123456789;
		testBean.longValue    = 123456789123456789L;
		testBean.floatValue   = 1234.5678F;
		testBean.doubleValue  = 123456789.123456789D;
	/**/DebugTrace.println("testBean", testBean);

	/**/DebugTrace.leave();
	}
}

class TestBean {
	public boolean booleanValue;
	public char    charValue;
	public byte    byteValue;
	public short   shortValue;
	public int     intValue;
	public long    longValue;
	public float   floatValue;
	public double  doubleValue;
}
