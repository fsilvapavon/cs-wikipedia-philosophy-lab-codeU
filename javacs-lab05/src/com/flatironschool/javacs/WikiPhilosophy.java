package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started

		Boolean foundPhil=false;
		String url = "/wiki/Java_(programming_language)";
		ArrayList<String> urlFound=new ArrayList<String>();
		while(!foundPhil) {
			Elements paragraphs = wf.fetchWikipedia("https://en.wikipedia.org"+url);
			
			Element firstPara = paragraphs.get(0);
			Iterable<Node> iter = new WikiNodeIterable(firstPara);
			Boolean foundLink=false;
			Boolean isItalic=false;
			Boolean repeatLink=false;
			for (Node node: iter) {
				if (node instanceof Element) {
					Elements links=((Element) node).select("a");
					for(Element e : links) {
						url=((Element) node).attr("href");
						if(url.equals("/wiki/Philosophy")) {
							System.out.println(url);
							foundPhil=true;
							return;
						}
						if(url.startsWith("/wiki/")) {
							Elements parents=((Element) e).parents();
							for (Element element : parents) {
								if(Tag.valueOf(element.tag().getName()).equals("i")||
										Tag.valueOf(element.tag().getName()).equals("em")) {
									isItalic=true;
									break;
								}

							}
							if(isItalic)
								break;
							for(String s: urlFound) {
								if(s.equals(url)) {
									repeatLink=true;
									break;
								}
							}
							if(!repeatLink) {
								System.out.println(url);
								urlFound.add(url);
								foundLink=true;
							}
							break;
						}
						if(foundLink)
							break;
					}
				}
				if(foundLink) {
					foundLink=false;
					break;

				}
				repeatLink=false;
			}
		}

	}

	// the following throws an exception so the test fails
	// until you update the code

}




