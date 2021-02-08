package edu.xxxxxx.webevo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FaultInjection {
	static String oldFilePath;
	static File oldFile;
	static String newFilePath;
	static File newFile;
	static Document doc;
	static StringBuilder res = new StringBuilder();

	public static void main(String[] args) {

		try {
			// init
			oldFilePath = "/Users/me/Desktop/2016.html";
			oldFile = new File(oldFilePath);
			newFilePath = "/Users/me/Desktop/case.html";
			newFile = new File(newFilePath);
//			oldFilePath = "/Users/me/Desktop/case.html";
//			oldFile = new File(oldFilePath);
//			newFilePath = "/Users/me/Desktop/case1.html";
//			newFile = new File(newFilePath);
			doc = Jsoup.parse(oldFile, "UTF-8");

			setHTMLofAnElement();

			FileOutputStream fos = new FileOutputStream(newFile, false);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(doc.html());
			osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setHTMLofAnElement() throws IOException {

		List<Element> elements = new ArrayList<>();
		String[] tags = { "h1", "h2", "h3", "h4", "h5", "h6", "p", "img", "div", "a" };
		String[] types = { "add", "delete", "update", "change" };
		// String[] positions = { "before", "after" };
		String[] updateTypes = { "text", };
		String[] newTags = { "p", "div", "button", "img", "a", "audio", "label", "textarea" };
		// Random randTags=new Random();
		Random randTypes = new Random();
		Random randUpdateType = new Random();
		Random randNewTags = new Random();

		for (int i = 0; i < tags.length; i++) {
			elements.addAll(doc.select(tags[i]));
		}

		int type = 3;
		int updateType = randUpdateType.nextInt(updateTypes.length);
		int newTag = randNewTags.nextInt(newTags.length);

		if (type == 0) {
			add(1, 3, elements);
		} else if (type == 1) {
			delete(1, elements);
		} else if (type == 2) {
			update(1, elements);
		} else if (type == 3) {

			 changeLocation(1,"before" , elements);

		}

		System.out.println();

	}

	public static void add(int changes, int newTag, List<Element> eletest) {
		Random rand = new Random();
		int total = eletest.size();
		List<Integer> visited = new ArrayList<>();

		while (visited.size() != changes) {
			int curr = rand.nextInt(total);
			if (!visited.contains(curr)) {
				visited.add(curr);
				String currText = eletest.get(curr).text();
				try {
					if (newTag == 0) {
						eletest.get(curr).before("<p>CATCH ME!</p>");
						System.out.println("added a p");
					} else if (newTag == 1) {
						eletest.get(curr)
								.before("<div style=\"background-color:lightblue\">\n"
										+ "  <h3>This is a h3 in new div</h3>\n"
										+ "  <p>This is a paragraph int new div.</p>\n" + "</div>");
						System.out.println("added a div");
					} else if (newTag == 2) {
						eletest.get(curr).before("<button type=\"button\">Click Me!</button>");
						System.out.println("added a button");
					} else if (newTag == 3) {
						eletest.get(curr)
								.before("<img src=\"/Users/me/Desktop/Research-CWRU/ImageCompare/ImageCompare/src/image.png\" alt=\"Smiley face\" height=\"42\" width=\"42\">");
						 
						System.out.println("added a img");
					} else if (newTag == 4) {
						eletest.get(curr).before("<a href=\"https://www.w3schools.com\">Visit Case Western!</a>");
						System.out.println("added a link");
					} else if (newTag == 5) {
						eletest.get(curr)
								.before("<audio controls>\n" + "  <source src=\"horse.ogg\" type=\"audio/ogg\">\n"
										+ "  <source src=\"horse.mp3\" type=\"audio/mpeg\">\n"
										+ "  Your browser does not support the audio tag.\n" + "</audio>");
						System.out.println("added a audio");
					} else if (newTag == 6) {
						eletest.get(curr).before("<label for=\"male\">Catch me!</label>");
						System.out.println("added a lable");
					} else if (newTag == 7) {
						eletest.get(curr).before("<textarea id=\"w3mission\" rows=\"4\" cols=\"20\">\n"
								+ "At Case Western, you will learn how to Coding Java.\n" + "</textarea>");
						System.out.println("added a textarea");
					}

					System.out.println("Added new element before: " + currText+", tag type"+eletest.get(curr).tagName());

				} catch (Exception e) {
					visited.remove(0);
					System.out.println("current is null, continue next one");

					continue;
				}

			}
		}
		System.out.println("Add done");

	}

	public static void delete(int changes, List<Element> eletest) {
		Random rand = new Random();
		int total = eletest.size();
		List<Integer> visited = new ArrayList<>();

		while (visited.size() != changes) {
			int curr = rand.nextInt(total);
			if (!visited.contains(curr)) {
				visited.add(curr);
				String currText = eletest.get(curr).text();
				System.out.println("curr node: " + eletest.get(curr).tag() + ", " + currText);

				try {
					eletest.get(curr).remove();

				} catch (Exception e) {
					visited.remove(0);
					System.out.println("current is null, continue next one");

					continue;
				}

			}
		}
		System.out.println("Remove done");

	}

	public static void update(int changes, List<Element> eletest) {
		Random rand = new Random();
		int total = eletest.size();
		List<Integer> visited = new ArrayList<>();

		String[] str = { "id", "class", "text" };
		while (visited.size() != changes) {
			int curr = rand.nextInt(total);

			visited.add(curr);
			Element currentElement = eletest.get(curr);
			String currText = eletest.get(curr).text();
			Random tempRand = new Random();
			if (currentElement.tagName().equals("img")) {
				currentElement.after(
						"<img id=\"new_img\" src=\"smiley.gif\" alt=\"Smiley face\" height=\"42\" width=\"42\">");
				currentElement.remove();
				System.out.println("updated <img>");
			} else if (currentElement.tagName().charAt(0) == 'h') {
				currentElement.after("<p>" + currText + "</p>");
				currentElement.remove();
				System.out.println("updated <h>" + currentElement.text() + "</h> to p");
			} else if (currentElement.tagName().charAt(0) == 'p') {
				currentElement.after("<h3>" + currText + "</h3>");
				currentElement.remove();
				System.out.println("updated <p>" + currentElement.text() + "</p> to h3");
			} else {
				int i = tempRand.nextInt(str.length);
				if (i == 0) {
					String old_id=currentElement.id();
					currentElement.attr("id", "casewestern_id");
					System.out.println("updated id= " + old_id+ " to casewestern_id");
				} else if (i == 1) {
					String old_class=currentElement.className();
					currentElement.attr("class", "casewestern_class");
					System.out.println("updated class= " + old_class + " to casewestern_class");
				} else {
					eletest.get(curr).after(
							"<" + currentElement.tagName() + ">" + "catchme" + "</" + currentElement.tagName() + ">");
					eletest.get(curr).remove();
					System.out.println("updated element text=" + currentElement.text() + " to text=catchme");
				}
			}
			System.out.println("updated elements:[" + currText + "] with tag: " + eletest.get(curr).tag().toString());
		}
		System.out.println("update done");

	}

	public static void changeLocation(int changes, String type, List<Element> eletest) {
		Random rand = new Random();
		int total = eletest.size();
		List<Integer> visited = new ArrayList<>();
		// System.out.println("size :"+eletest.size());
		while (visited.size() != changes) {
			int curr = rand.nextInt(total);

			if (!visited.contains(curr)) {
				visited.add(curr);
				String currText = eletest.get(curr).text();

				System.out.println("location changed element: " + currText);
				try {
					eletest.get(curr).remove();
					int random = curr;
					while (random == curr) {
						random = new Random().nextInt(eletest.size());
					}
					if (type.equals("before")) {
						System.out
								.println("Moved target element:" + currText + " before: " + eletest.get(random).text());

						eletest.get(random).before("<p>" + currText + "</p>");

					} else {
						System.out
								.println("Moved target element:" + currText + " after: " + eletest.get(random).text());

						eletest.get(random).after("<p>" + currText + "</p>");

					}
				} catch (Exception e) {
					visited.remove(0);
					System.out.println("current is null, continue next one");

					continue;
				}

			}
		}
		System.out.println("changeLocation done");

	}

}
