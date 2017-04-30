package de.koetter.robin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.koetter.robin.mapper.Entry;

/**
 * Parser core
 * 
 * @author Robin Kötter
 */
public class Parser {

	/**
	 * Parse the input.
	 * 
	 * @param input
	 *            the user input
	 * @param list
	 *            list of regex entries
	 * @return parsed input
	 */
	public final String parseInput(final String input, final List<Entry> list) {
		String returner = "<p>";

		for (String line : input.split("\\n")) {
			if (line.isEmpty()) {
				line = "</p>\n<p>";
			} else {
				boolean newLine = true;
				for (final Entry entry : list) {
					final Pattern pattern = Pattern.compile(entry.getRegex());
					final Matcher matcher = pattern.matcher(line);

					if (matcher.find()) {
						newLine = !entry.isBlock();
						if (entry.isBlock() && returner.equals("<p>")) {
							// remove p
							returner = "";
							line = matcher.replaceAll(entry.getReplacement() + "\n<p>");
						} else if (entry.isBlock() && !returner.equals("<p>")) {
							// close p
							line = "</p>" + matcher.replaceAll(entry.getReplacement()) + "<p>";
						} else {
							// other matches
							line = matcher.replaceAll(entry.getReplacement());
						}

						break;
					}
				}

				// general add a new line
				if (newLine) {
					line = line + "<br />";
				}
			}

			returner += line;
		}

		// check for lists
		returner = handleLists(returner);
		return returner + "</p>";
	}

	/**
	 * Handles lists in the input. The content has to be parsed already.
	 * 
	 * @param returner
	 *            the already parsed input, that may contain lists
	 * @return returns the full html-Code with handles lists
	 */
	private String handleLists(final String returner) {
		final Pattern pattern = Pattern.compile("\\*(.*?)\\<br \\/\\>");
		final Matcher matcher = pattern.matcher(returner);

		String test = "";
		if (matcher.find()) {
			test += matcher.replaceAll("<li>$1</li>");
		} else {
			test = returner;
		}
		return test;
	}

}
