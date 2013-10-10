package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

public class Comment {

    private Reader poster;
	/**
	 * @uml.property  name="pageCommented"
	 * @uml.associationEnd  inverse="comments:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
	private Page pageCommented;
	
    public Comment() {
    	
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
    
}
