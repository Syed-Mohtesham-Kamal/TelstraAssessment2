package assessment2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SMSRecord {
	private String senderNumber;
	private String receiverNumber;
	private int messageLength;
	private String messageType;
	private String message;

	public SMSRecord(String senderNumber, String receiverNumber, int messageLength, String messageType,
			String message) {
		this.senderNumber = senderNumber;
		this.receiverNumber = receiverNumber;
		this.messageLength = messageLength;
		this.messageType = messageType;
		this.message = message;
	}

	public int getMessageLength() {
		return messageLength;
	}

	public String getSenderNumber() {
		return senderNumber;
	}

	public String getMessage() {
		return message;
	}

	// Setters and getters
}

public class SMSAnalytics {
	public static void main(String[] args) {
		List<SMSRecord> smsRecords = new ArrayList<>();
		smsRecords.add(new SMSRecord("1234567890", "9876543210", 35, "outgoing", "Hello, it's a great day!"));
		smsRecords.add(new SMSRecord("1111111111", "2222222222", 50, "incoming", "How are you?"));
		smsRecords.add(new SMSRecord("3333333333", "4444444444", 20, "outgoing", "See you later!"));
		smsRecords.add(new SMSRecord("5555555555", "6666666666", 15, "incoming", "Goodbye!"));
		smsRecords.add(new SMSRecord("7777777777", "8888888888", 42, "outgoing", "Have a nice day!"));
		smsRecords.add(new SMSRecord("9999999999", "0000000000", 28, "incoming", "What's up?"));
		smsRecords.add(new SMSRecord("1234567890", "2222222222", 60, "outgoing", "Long message here..."));
		smsRecords.add(new SMSRecord("3333333333", "4444444444", 10, "incoming", "Short message!"));
		smsRecords.add(new SMSRecord("5555555555", "6666666666", 18, "outgoing", "Another message."));
		smsRecords.add(new SMSRecord("7777777777", "8888888888", 32, "incoming", "Chilling at home."));

		double averageMessageLength = calculateAverageMessageLength(smsRecords);
		System.out.println("Average message Length: " + averageMessageLength);

		List<String> topSenders = identifyTopSenders(smsRecords);
		System.out.println("Top Senders: " + topSenders);

		String sentiment = performSentimentAnalysis(smsRecords);
		System.out.println("Sentiment Analysis: " + sentiment);
	}

	public static double calculateAverageMessageLength(List<SMSRecord> smsRecords) {
		return smsRecords.stream().mapToInt(SMSRecord::getMessageLength).average().orElse(0.0);
	}

	public static List<String> identifyTopSenders(List<SMSRecord> smsRecords) {
		return smsRecords.stream()
				.collect(Collectors.groupingBy(SMSRecord::getSenderNumber,
						Collectors.summingInt(SMSRecord::getMessageLength)))
				.entrySet().stream().sorted(Comparator.comparingInt(entry -> -entry.getValue())).limit(3)
				.map(entry -> entry.getKey()).collect(Collectors.toList());
	}

	public static String performSentimentAnalysis(List<SMSRecord> smsRecords) {
		long positiveCount = smsRecords.stream().map(SMSRecord::getMessage)
				.filter(message -> analyzeSentiment(message) == Sentiment.POSITIVE).count();

		long negativeCount = smsRecords.stream().map(SMSRecord::getMessage)
				.filter(message -> analyzeSentiment(message) == Sentiment.NEGATIVE).count();

		if (positiveCount > negativeCount) {
			return "positive";
		} else if (negativeCount > positiveCount) {
			return "negative";
		} else {
			return "neutral";
		}
	}

	public enum Sentiment {
		POSITIVE, NEGATIVE, NEUTRAL
	}

	public static Sentiment analyzeSentiment(String message) {
		String[] positiveWords = { "good", "great", "excellent" };
		String[] negativeWords = { "bad", "terrible", "awful" };

		long positiveWordCount = Stream.of(positiveWords).filter(word -> message.toLowerCase().contains(word)).count();

		long negativeWordCount = Stream.of(negativeWords).filter(word -> message.toLowerCase().contains(word)).count();

		if (positiveWordCount > negativeWordCount) {
			return Sentiment.POSITIVE;
		} else if (negativeWordCount > positiveWordCount) {
			return Sentiment.NEGATIVE;
		} else {
			return Sentiment.NEUTRAL;
		}
	}
}