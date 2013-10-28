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
package ca.ualberta.CMPUT301F13T02.chooseyouradventuretest;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;

public class CommentTest {
	/**
	 *  tests equals method from comment 
	 **/
	@Test
	public void equalsTest() {
		Comment comment1 = new Comment("Ben commented");
		Comment comment2 = new Comment("Conrad critiqued");
		assertFalse(comment1.equals(comment2));
		
		comment1 = new Comment("Ben commented", "Ben");
		comment2 = new Comment("Ben commented", "Konrad");
		assertFalse(comment1.equals(comment2));
		
		comment1 = new Comment("Ben commented", "Ben");
		comment2 = new Comment("Konrad commented", "Ben");
		assertFalse(comment1.equals(comment2));
		
		comment1 = new Comment("Ben commented", "Ben");
		comment2 = new Comment("Ben commented", "Ben");
		assertTrue(comment1.equals(comment2));
	}

}
