/*
* Copyright (c) 2013, TeamCMPUT301F13T02
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
* 
* Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
* 
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
* 
* Neither the name of the {organization} nor the names of its
* contributors may be used to endorse or promote products derived from
* this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.io.Reader;

public class Comment {

    private Reader poster;
	/**
	 * @uml.property  name="pageCommented"
	 * @uml.associationEnd  inverse="comments:ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page"
	 */
    private Page pageCommented;
	
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
