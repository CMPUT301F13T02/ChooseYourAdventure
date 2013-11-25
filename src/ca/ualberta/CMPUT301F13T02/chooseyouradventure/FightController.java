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

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FightController {
	protected void updateFightView(LinearLayout fightingLayout, ControllerApp app) {
		StoryController storyController = app.getStoryController();
        PageController pageController = app.getPageController();
		fightingLayout.removeAllViews();
		Story story = storyController.getStory();
		Page page = pageController.getPage();
		if(pageController.isOnEntry() == true){
			
			if(story.getFirstpage().getId().equals(page.getId())){
				Counters counter = new Counters();
				counter.setBasic("0","100");
				story.setPlayerStats(counter);
			}
			story.getPlayerStats().setEnemyHpStat(page.getEnemyHealth());
		}
		
		TextView fightingUpdate = new TextView(app);
		TextView healthView = new TextView(app);
		TextView treasureView = new TextView(app);
		TextView enemyView = new TextView(app);
		Counters stat = story.getPlayerStats();
		
		healthView.setTextColor(Color.BLUE);
		healthView.setText(app.getString(R.string.currentHealth) + " " + stat.getPlayerHpStat());
		fightingLayout.addView(healthView);
		
		treasureView.setTextColor(Color.YELLOW);
		treasureView.setText(app.getString(R.string.currentTreasure) + " " + stat.getTreasureStat());
		fightingLayout.addView(treasureView);
		
		if(page.isFightingFrag() == true){

			enemyView.setTextColor(Color.RED);
			enemyView.setText(app.getString(R.string.enemyHealthColon) + " " + stat.getEnemyHpStat());
			fightingLayout.addView(enemyView);
			story.getPlayerStats().setEnemyRange(true);
		}
		else {
			story.getPlayerStats().setEnemyRange(false);
		}
		
		String displayChanges = "\n";

		if(stat.getEnemyHpChange() != 0) {
			displayChanges += stat.getHitMessage() + "\n";
			displayChanges += page.getEnemyName();

			if(stat.getEnemyHpChange() <= 0) 
				displayChanges += " " + app.getString(R.string.gained) + " ";
			else 
				displayChanges += " " + app.getString(R.string.lost) + " ";

			displayChanges += stat.getEnemyHpChange() + " " + app.getString(R.string.hitpoints) + "\n";
		}
		if(stat.getPlayerHpChange() != 0){
			displayChanges += stat.getDamageMessage() + "\n";
			displayChanges += app.getString(R.string.you);

			if(stat.getPlayerHpChange() <= 0)
				displayChanges += " " + app.getString(R.string.gained) + " ";
			else
				displayChanges += " " + app.getString(R.string.lost) + " ";

			displayChanges += stat.getPlayerHpChange() +" " +  app.getString(R.string.hitpoints) + "\n";
		}
		if(stat.getTreasureChange() != 0) {
			displayChanges += stat.getTreasureMessage() + "\n";
			displayChanges += app.getString(R.string.you);

			if(stat.getTreasureChange() <= 0)
				displayChanges += " " + app.getString(R.string.lost) + " ";
			else 
				displayChanges += " " + app.getString(R.string.gained) + " ";

			displayChanges += stat.getTreasureChange() + " " +  app.getString(R.string.coinsWorth);
		}
		
		fightingUpdate.setTextColor(Color.GREEN);
		fightingUpdate.setText(displayChanges);
		fightingLayout.addView(fightingUpdate);
	}


}
