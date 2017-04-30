package de.koetter.robin.mapper;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Handler for the elements in xml.
 * 
 * @author Robin Kötter
 */
@Root
@AllArgsConstructor
@NoArgsConstructor
public class ElementsHandler {

	/** List of markables */
	@Getter
	@Setter
	@ElementList(inline = true)
	private List<Entry> list;

}
