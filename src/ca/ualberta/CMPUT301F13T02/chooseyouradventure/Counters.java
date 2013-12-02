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

import java.util.Random;

/**
 * This class is a part of the model in MVC, and acts as the model for the fighting addition
 * of our application.
 * 
 * @author James Cadek
 */

public class Counters {
	
	private int treasureStat = 0;
	private int playerHpStat = 0;
	private int enemyHpStat = 0;
	
	private int treasureChange = 0;
	private int playerHpChange = 0;
	private int enemyHpChange = 0;
	
	private int enemyHitPercent = 100;
	private int playerHitPercent = 100;
	
	private int thresholdSign = 1;
	private int thresholdValue = 0;
	private int thresholdType = 0;
	
	private String damageMessage = "";
	private String treasureMessage = "";
	private String hitMessage = "";
	
	private boolean isEnemyRange = false;
	
	public Counters() {}

	/**
	 * This method sets all the messages to be displayed in the event
	 * that there is some change in the counters.
	 *
	 * @param damage
	 * @param treasure
	 * @param hit
	 */
	public void setMessages(String damage, String treasure, String hit){
		this.damageMessage = damage;
		this.hitMessage = hit;
		this.treasureMessage = treasure;
	}

	/**
	 * @return the damageMessage
	 */
	public String getDamageMessage() {
		return damageMessage;
	}

	/**
	 * @return the treasureMessage
	 */
	public String getTreasureMessage() {
		return treasureMessage;
	}

	/**
	 * @return the hitMessage
	 */
	public String getHitMessage() {
		return hitMessage;
	}

	/**
	 * This set method updates all traits changed in the edit menu
	 * @param treasureStat
	 * @param playerHpStat
	 * @param enemyHpStat
	 * @param enemyHitPercentage
	 * @param playerHitPercentage
	 */
	public void setStats(String treasureStat, String playerHpStat, String enemyHpStat, String enemyHitPercentage, String playerHitPercentage
			) {
		try{
			this.treasureStat = Integer.parseInt(treasureStat);
		} catch(Exception e){}
		try{
			this.playerHpStat = Integer.parseInt(playerHpStat);
		} catch(Exception e){}
		try{
			this.enemyHpStat = Integer.parseInt(enemyHpStat);
		} catch(Exception e){}
		try{
			this.enemyHitPercent = Integer.parseInt(enemyHitPercentage);
		} catch(Exception e){}
		try{
			this.playerHitPercent = Integer.parseInt(playerHitPercentage);
		} catch(Exception e){}
		
		
	}
	
	/**
	 * This setter updates all changes made in the Conditionals menu
	 * @param thresholdSign
	 * @param thresholdType
	 * @param thresholdValue
	 */
	public void setThresholds(int thresholdSign, int thresholdType, String thresholdValue){
		
		try{
			this.thresholdValue = Integer.parseInt(thresholdValue);
		} catch(Exception e){}
		
		
		this.thresholdSign = thresholdSign;
		
		this.thresholdType = thresholdType;
		
	}
	
	/**
	 * This method is for refreshing theplayers health and gold at the start of a story
	 * @param treasureStat
	 * @param playerHpStat
	 */
	public void setBasic(String treasureStat, String playerHpStat) {
		try{
			this.treasureStat = Integer.parseInt(treasureStat);
		} catch(Exception e){}
		try{
			this.playerHpStat = Integer.parseInt(playerHpStat);
		} catch(Exception e){}
		
	}
	
	/**
	 * This method updates all stats in a stats-based fighting fragment
	 * @param choiceModifiers
	 */
	public void invokeUpdateComplex(Counters choiceModifiers){
		Random rn = new Random();
		int randomFlagP = rn.nextInt(100) + 1;
		int randomFlagE = rn.nextInt(100) + 1;
		this.treasureStat = treasureStat + choiceModifiers.treasureStat ;
		this.treasureChange = choiceModifiers.treasureStat;
		
		this.damageMessage = choiceModifiers.getDamageMessage();
		this.hitMessage = choiceModifiers.getHitMessage();
		this.treasureMessage = choiceModifiers.getTreasureMessage();
		
		this.enemyHpChange = 0;
		this.playerHpChange = 0;
		if(choiceModifiers.enemyHpStat > 0){
			if(randomFlagP <= choiceModifiers.getPlayerHitPercent()){
				
				int eChange = rn.nextInt(choiceModifiers.enemyHpStat) + 1;
				this.enemyHpChange = eChange;
				this.enemyHpStat = enemyHpStat - eChange;			
				
			}
		}
		if(choiceModifiers.playerHpStat > 0){
			if(randomFlagE <= choiceModifiers.getEnemyHitPercent() ){
				if(isEnemyRange == true){
					int pChange = rn.nextInt(choiceModifiers.playerHpStat) + 1;
					this.playerHpChange = pChange;
					this.playerHpStat = playerHpStat - pChange;			
				}
				
			}
		}

	}

	/**
	 * Similar to the above method, this updates the model, but only the basic counters not used in fighting
	 * @param choiceModifiers
	 */
	public void invokeUpdateSimple(Counters choiceModifiers){
		this.treasureStat = treasureStat + choiceModifiers.treasureStat ;
		this.treasureChange = choiceModifiers.treasureStat;
		
		this.playerHpChange = choiceModifiers.playerHpStat;
		this.playerHpStat = playerHpStat - choiceModifiers.playerHpStat;
	}

	/**
	 * Get if the enemies damage is a range of values or not.
	 * @return the isEnemyRange
	 */
	public boolean isEnemyRange() {
		return isEnemyRange;
	}

	/**
	 * Set if enemies damage is a range of values or not.
	 * @param isEnemyRange the isEnemyRange to set
	 */
	public void setEnemyRange(boolean isEnemyRange) {
		this.isEnemyRange = isEnemyRange;
	}

	/**
	 * Get the amount to change the treasure by.
	 * @return the treasureChange
	 */
	public int getTreasureChange() {
		return treasureChange;
	}

	/**
	 * Set the amount to change the treasure by.
	 * @param treasureChange the treasureChange to set
	 */
	public void setTreasureChange(int treasureChange) {
		this.treasureChange = treasureChange;
	}

	/**
	 * Get the amount to change the player's hp by.
	 * @return the playerHpChange
	 */
	public int getPlayerHpChange() {
		return playerHpChange;
	}

	/**
	 * Set the amount to change the player's hp by.
	 * @param playerHpChange the playerHpChange to set
	 */
	public void setPlayerHpChange(int playerHpChange) {
		this.playerHpChange = playerHpChange;
	}

	/**
	 * Get the amount to change the enemies hp by.
	 * @return the enemyHpChange
	 */
	public int getEnemyHpChange() {
		return enemyHpChange;
	}

	/**
	 * Set the amount to change the enemies hp by.
	 * @param enemyHpChange the enemyHpChange to set
	 */
	public void setEnemyHpChange(int enemyHpChange) {
		this.enemyHpChange = enemyHpChange;
	}

	/**
	 * Get the amount of treasure the person has
	 * @return the treasureStat
	 */
	public int getTreasureStat() {
		return treasureStat;
	}

	/**
	 * Set the amount of treasure a person has.
	 * @param treasureStat the treasureStat to set
	 */
	public void setTreasureStat(int treasureStat) {
		this.treasureStat = treasureStat;
	}

	/**
	 * Get the amount of hp a player has
	 * @return the playerHpStat
	 */
	public int getPlayerHpStat() {
		return playerHpStat;
	}

	/**
	 * Set the amount of hp a player has
	 * @param playerHpStat the playerHpStat to set
	 */
	public void setPlayerHpStat(int playerHpStat) {
		this.playerHpStat = playerHpStat;
	}

	/**
	 * Get the amount of hp an enemy has
	 * @return the enemyHpStat
	 */
	public int getEnemyHpStat() {
		return enemyHpStat;
	}

	/**
	 * Set the amount of hp an enemy has.
	 * @param enemyHpStat the enemyHpStat to set
	 */
	public void setEnemyHpStat(int enemyHpStat) {
		this.enemyHpStat = enemyHpStat;
	}

	/**
	 * Get the sign of the threshold
	 * @return the thresholdSign
	 */
	public int getThresholdSign() {
		return thresholdSign;
	}

	/**
	 * Set the sign of the threshold
	 * @param thresholdSign the thresholdSign to set
	 */
	public void setThresholdSign(int thresholdSign) {
		this.thresholdSign = thresholdSign;
	}

	/**
	 * Get the value of the threshold
	 * @return the thresholdValue
	 */
	public int getThresholdValue() {
		return thresholdValue;
	}

	/**
	 * Set the value of the threshold
	 * @param thresholdValue the thresholdValue to set
	 */
	public void setThresholdValue(int thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	/** 
	 * Get the type of the threshold
	 * @return the trait (Health, Enemy, Treasure) being checked
	 */
	public int getThresholdType() {
		return thresholdType;
	}

	/**
	 * Set the type of the threshold
	 * @param the trait (Health, Enemy, Treasure) being checked
	 */
	public void setThresholdType(int thresholdType) {
		this.thresholdType = thresholdType;
	}

	/** 
	 * Get what percent of their max health an enemy has.
	 * @return The percent chance the enemy will damage you in a fighting fragment
	 */
	public int getEnemyHitPercent() {
		return enemyHitPercent;
	}

	/**
	 * Set the percent of the the enemy's max hp they have.
	 * @param enemyHitPercent, The percent chance the enemy will damage you in a fighting fragment
	 */
	public void setEnemyHitPercent(int enemyHitPercent) {
		this.enemyHitPercent = enemyHitPercent;
	}

	/**
	 * Get the percent of their max health a player has.
	 * @return The percent chance the player will damage the opponent in a fighting fragment
	 */
	public int getPlayerHitPercent() {
		return playerHitPercent;
	}

	/** 
	 * Set the percent of their max hp the player has.
	 * @param playerHitPercent, The percent chance the player will damage the opponent in a fighting fragment
	 */
	public void setPlayerHitPercent(int playerHitPercent) {
		this.playerHitPercent = playerHitPercent;
	}
	
}
