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

package ca.ualberta.CMPUT301F13T02.chooseyouradventure.test;

import java.util.ArrayList;

import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Comment;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Decision;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Page;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.TextTile;
import ca.ualberta.CMPUT301F13T02.chooseyouradventure.Tile;
import android.test.InstrumentationTestCase;

public class TestPage extends InstrumentationTestCase {
	
	private Page page;
	
	protected void setUp() {
		page = new Page();
	}
	
	public void testSetTitle() {
		String title = "title";
		page.setTitle(title);
		assertTrue(title.equals(page.getTitle()));
	}
	
	public void testGetTitle() {
		String title = "A title";
		page.setTitle(title);
		String pageTitle = page.getTitle();
		assertTrue(title.equals(pageTitle));
	}
	
	public void testSetEnding() {
		String ending = "The End";
		page.setPageEnding(ending);
		assertTrue(ending.equals(page.getPageEnding()));
	}
	
	public void testGetEnding() {
		String ending = "The End";
		page.setPageEnding(ending);
		String pageEnding = page.getPageEnding();
		assertTrue(ending.equals(pageEnding));
	}
	
	public void testSetRefNumber() {
		int ref = 3;
		page.setRefNum(ref);
		assertEquals(ref, page.getRefNum());
	}
	
	public void testGetRefNumber() {
		int ref = 3;
		page.setRefNum(ref);
		int pageRef = page.getRefNum();
		assertEquals(ref, pageRef);
	}
	
	public void testGetTiles() {
		ArrayList<Tile> tiles = page.getTiles();
		assertTrue(tiles instanceof ArrayList<?>);
	}
	
	public void testAddTile() {
		page.getTiles().clear();
		page.addTile(new TextTile());
		int l = page.getTiles().size();
		assertEquals(l, 1);
	}
	
	public void testRemoveTile() {
		page.getTiles().clear();
		page.addTile(new TextTile());
		page.removeTile(0);
		int l = page.getTiles().size();
		assertEquals(l, 0);
	}
	
	public void testAddDecision() {
		page.getDecisions().clear();
		page.addDecision(new Decision("A decision", page));
		int l = page.getDecisions().size();
		assertEquals(l, 1);
	}
	
	public void testDeleteDecision() {
		page.getDecisions().clear();
		page.addDecision(new Decision("A decision", page));
		page.deleteDecision(0);
		int l = page.getDecisions().size();
		assertEquals(l, 0);
	}
	
	public void testAddComment() {
		page.getComments().clear();
		page.addComment(new Comment(null, null));
		int l = page.getComments().size();
		assertEquals(l, 1);
	}
	
	public void testGetFightingFrag() {
		page.setFightingFrag(true);
		assertTrue(page.getFightingState());
	}
	
	public void testSetFightingState() {
		page.setFightingFrag(true);
		boolean isFighting = page.getFightingState();
		assertTrue(isFighting);
	}
	
	public void testGetEnemyHealth() {
		page.setEnemyHealth(100);
		assertEquals(100, page.getEnemyHealth());
	}
	
	public void testSetEnemyHealth() {
		int health = 100;
		page.setEnemyHealth(health);
		int pageHealth = page.getEnemyHealth();
		assertEquals(health, pageHealth);
	}
	
	public void testGetEnemyName() {
		page.setEnemyName("Attila the Hun");
		assertTrue(page.getEnemyName().equals("Attila the Hun"));
	}
	
	public void testSetEnemyName() {
		String enemy = "Enemy";
		page.setEnemyName(enemy);
		String pageEnemy = page.getEnemyName();
		assertTrue(enemy.equals(pageEnemy));
	}
	
}
