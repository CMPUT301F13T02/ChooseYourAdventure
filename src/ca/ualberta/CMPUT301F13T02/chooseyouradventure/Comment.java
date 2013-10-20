package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.io.Reader;

public class Comment {

    private Reader poster;
    private String text;

	public Comment(String text) {
		this.text = text;
    }

	/**
	 * @uml.property  name="reader"
	 * @uml.associationEnd  aggregation="shared" inverse="comments:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Reader"
	 */
	private Reader reader;

	/**
	 * Getter of the property <tt>reader</tt>
	 * @return  Returns the reader.
	 * @uml.property  name="reader"
	 */
	public Reader getReader() {
		return reader;
	}

	/**
	 * Setter of the property <tt>reader</tt>
	 * @param reader  The reader to set.
	 * @uml.property  name="reader"
	 */
	public void setReader(Reader reader) {
		this.reader = reader;
	}

	/**
	 * @uml.property  name="page"
	 * @uml.associationEnd  inverse="comments:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
	private Page page;
	/**
	 * Getter of the property <tt>page</tt>
	 * @return  Returns the page.
	 * @uml.property  name="page"
	 */
	public Page getPage() {
		return page;
	}
	
	public String getText() {
		return text;
	}

	/**
	 * Setter of the property <tt>page</tt>
	 * @param page  The page to set.
	 * @uml.property  name="page"
	 */
	public void setPage(Page page) {
		this.page = page;
	}
    
    public boolean equals(Comment comment) {
    	//return poster.equals(comment.getReader());
    	return text.equals(comment.getText());
    }
}
