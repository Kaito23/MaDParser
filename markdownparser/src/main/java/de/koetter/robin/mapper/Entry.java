package de.koetter.robin.mapper;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Regex entry
 * 
 * @author Robin Kötter
 */
@Root
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

	/** Headline / Name */
	@Attribute
	private String headline;
	/** Regex */
	@Element
	private String regex;
	/** replacement */
	@Element
	private String replacement;
	/** boolean for block element. Necessary for closing p tag */
	@Element
	private boolean block;
}
