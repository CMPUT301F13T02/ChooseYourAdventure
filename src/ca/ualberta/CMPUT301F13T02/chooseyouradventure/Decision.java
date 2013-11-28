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

import java.util.UUID;

/**
 * This class will encapsulate the data of a page's decisions which link two pages of a 
 * story together with a prompt.
 * 
 * This class is part of the model of the application; as such it is serialized with the
 * rest of the story to be stored using a Handler implementation.
 */

public class Decision {
	private String text;
	private UUID pageID;
	private Counters choiceModifiers = new Counters();
	

	
	/**
	 * This sets the link for the decision
	 * @param The text of the decision and it's corresponding page
	 */
	public Decision(String text, Page page) {
		this.text = text;
		this.pageID = page.getId();
	}

	/**
	 * This gets the current Page ID
	 * @return The current PageID
	 */
	public UUID getPageID() {
		return pageID;
	}

	/**
	 * This gets the current text of the decision
	 * @return The text of the decision
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Basically a setter for the variables of Decision, however it requires
	 * that you set both the text and the destination page at the same time.
	 * @param text
	 * @param page
	 */

	
	public void updateDecision(String text, Page page, Counters counter) {
		this.text = text;
		this.pageID = page.getId();
		this.choiceModifiers = counter;
	}
	
	/**
	 * Compares this Decision with the passed decision for equality
	 * @param decision The Decision to compare against
	 * @return Whether or not the two decisions are considered equal
	 */
	public boolean equals(Decision decision) {
		if (!text.equals(decision.getText()) || !pageID.equals(decision.getPageID()))
				return false;

		return true;
	}

/**
 * This reflects updates to the Stories counters that will occur when following a link
 * @return choiceModifiers
 */
	public Counters getChoiceModifiers() {
		return choiceModifiers;
	}

/**
 * This reflects updates to the Stories counters that will occur when following a link
 * @param choiceModifiers
 */
	public void setChoiceModifiers(Counters choiceModifiers) {
		this.choiceModifiers = choiceModifiers;
	}
}
