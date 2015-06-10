package dcc.agent.server.service.swget.regExpression.parser;

import dcc.agent.server.service.swget.regExpression.automaton.RegularExpressionBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Insert Expression (type exit for quit)");
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(System.in));
		String exp = null;
		try {
			exp = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (!exp.equals("exit")) {
			System.out.println(exp);
			PathExpression p = new PathExpression(exp);
			try {
				p.startParsing();
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (TokenMgrError e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			RegularExpressionBase e = p.getRegExpress();
			System.out.println(e.getLanguageDescription());
			System.out.println("Insert Expression (type exit for quit)");
			try {
				exp = br.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
