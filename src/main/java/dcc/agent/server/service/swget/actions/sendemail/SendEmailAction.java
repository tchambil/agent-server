package dcc.agent.server.service.swget.actions.sendemail;

import dcc.agent.server.service.swget.actions.AbstractAction;
import dcc.agent.server.service.swget.utils.Constants;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedList;
import java.util.Properties;

public class SendEmailAction extends AbstractAction {

	String SMTP_HOST_NAME = "smtp.gmail.com";
	String SMTP_AUTH_USER = "nautilod.swget";
	String SMTP_AUTH_PWD = "krdb_dcc";

	private static class SingletonHolder {
		public static final SendEmailAction instance = new SendEmailAction();

	}

	public static SendEmailAction getInstance() {
		return SingletonHolder.instance;
	}

	public void sendEmail(String recipient, String subject, String command,
			String action, LinkedList<String> message, String from)
			throws MessagingException {
		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);
		try {

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InternetAddress addressTo = null;
		try {
			addressTo = new InternetAddress(recipient);
		} catch (AddressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * InternetAddress[] addressTo = new InternetAddress[recipients.length];
		 * for (int i = 0; i < recipients.length; i++) { try { addressTo[i] =
		 * new InternetAddress(recipients[i]); } catch (AddressException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } }
		 */
		try {
			msg.setRecipient(Message.RecipientType.TO, addressTo);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setting the Subject and Content Type
		try {
			msg.setSubject(subject);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object msg_converted = constructHTMLFormat(command, action, message);

		try {
			msg.setContent(msg_converted, "text/html");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			Transport.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * From a LIST of URI construct an HTML formatted LIST
	 * 
	 * @param message
	 * @return
	 */

	private Object constructHTMLFormat(String command, String action,
			LinkedList<String> message) {
		// put each URI in a row of an HTML table

		command = forHTML(command);
		action = forHTML(action);

		String msg_converted_base = Constants.SWGET_EMAIL_MSG_BEGIN;

		msg_converted_base = msg_converted_base + "<h4>swget command:</h4>"
				+ command + "\n" + "<h3>Results for the action:" + action
				+ "</h4><br>" + "\n" + "<table border='1'>" + "<tr>"
				+ "<th>Result</th>" + "</tr> \n";

		String msg_converted = "";

		for (String st : message) {
			msg_converted = msg_converted + "<tr> <td>" + forHTML(st)
					+ "</td></tr> \n";

		}

		msg_converted = msg_converted_base + msg_converted
				+ "</table><br><br>\n" + Constants.SWGET_EMAIL_MSG_END + "\n";

		return msg_converted;
	}

	public static String forHTML(String aText) {
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(
				aText);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '&') {
				result.append("&amp;");
			} else if (character == '\"') {
				result.append("&quot;");
			} else if (character == '\t') {
				addCharEntity(9, result);
			} else if (character == '!') {
				addCharEntity(33, result);
			} else if (character == '#') {
				addCharEntity(35, result);
			} else if (character == '$') {
				addCharEntity(36, result);
			} else if (character == '%') {
				addCharEntity(37, result);
			} else if (character == '\'') {
				addCharEntity(39, result);
			} else if (character == '(') {
				addCharEntity(40, result);
			} else if (character == ')') {
				addCharEntity(41, result);
			} else if (character == '*') {
				addCharEntity(42, result);
			} else if (character == '+') {
				addCharEntity(43, result);
			} else if (character == ',') {
				addCharEntity(44, result);
			} else if (character == '-') {
				addCharEntity(45, result);
			} else if (character == '.') {
				addCharEntity(46, result);
			} else if (character == '/') {
				addCharEntity(47, result);
			} else if (character == ':') {
				addCharEntity(58, result);
			} else if (character == ';') {
				addCharEntity(59, result);
			} else if (character == '=') {
				addCharEntity(61, result);
			} else if (character == '?') {
				addCharEntity(63, result);
			} else if (character == '@') {
				addCharEntity(64, result);
			} else if (character == '[') {
				addCharEntity(91, result);
			} else if (character == '\\') {
				addCharEntity(92, result);
			} else if (character == ']') {
				addCharEntity(93, result);
			} else if (character == '^') {
				addCharEntity(94, result);
			} else if (character == '_') {
				addCharEntity(95, result);
			} else if (character == '`') {
				addCharEntity(96, result);
			} else if (character == '{') {
				addCharEntity(123, result);
			} else if (character == '|') {
				addCharEntity(124, result);
			} else if (character == '}') {
				addCharEntity(125, result);
			} else if (character == '~') {
				addCharEntity(126, result);
			} else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	private static void addCharEntity(Integer aIdx, StringBuilder aBuilder) {
		String padding = "";
		if (aIdx <= 9) {
			padding = "00";
		} else if (aIdx <= 99) {
			padding = "0";
		} else {
			// no prefix
		}
		String number = padding + aIdx.toString();
		aBuilder.append("&#" + number + ";");
	}

	public static void main(String argv[]) {
		SendEmailAction act = SendEmailAction.getInstance();

		String st[] = new String[2];

		st[0] = "giuseppe.pirro@unibz.it";
		st[1] = "giuseppe.pirro@unibz.it";

		String command = "http://dbpedia.org/resource/Italy -p <dbpedia-owl:hometown>[ASK {?person <rdf:type> <dbpedia-owl:Person>. ?person <rdf:type> <dbpedia:MusicalArtist>.}]/<dbpedia-owl:birthPlace>[ASK {?town <dbpedia-owl:populationTotal> ?pop. FILTER (?pop <15000).}]/<owl:sameAs>";
		String action = "?person <rdf:type> <dbpedia-owl:Person>";
		String subject = "Results for the action activated on a swget command";
		LinkedList<String> results = new LinkedList<String>();

		results.add("http://dbpedia.org/resources/Montepaone");
		results.add("http://dbpedia.org/resources/Cosenza");

		try {
			act.sendEmail("antony_epis@gmail.com", subject, command, action,
					results, "tchambil@dcc.uchile.cl");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	class SMTPAuthenticator extends Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}

}
