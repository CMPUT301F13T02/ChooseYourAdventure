package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FightView {
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
