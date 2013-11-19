

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;


public class SampleGenerator {
	private Story outStory = new Story();
	
	public SampleGenerator() {
		setStory();
	}
	private void setStory(){
		Story newStory = new Story(); 
    	newStory.setTitle("SAMPLE - Unicorn Adventure 16");	 
    	String[] pageTitles = { "Our Story Begins.....","The Quest for Fries","The Quest for Food","The Quest for Onion Rings",
    							"Far out in the Arctic","Deep in the Great Deserts of Alalala","Somewhere in the Jungle...",
    							"In the Depths of a Deep Dark Cave","Industrial Wasteland","Death (Starvation)","Get Onion Rings!",
    							"Ancient Temple","Nazi Trap","Death (Anthrax)","Escape","Fries!"};
    	String[] endings = {"What food would Billy like?","Billy encounters a 3 way split in the road","Billy sees two different trail he can follow",
    						"Billy sees two different trail he can follow","Where does Billy go?","Where does Billy go?","Where does Billy go?","Where does Billy go?","Where does Billy go?",
    						"What does Billy do?", "You are Dead.","Which door should you enter?",
    						"What door do you enter?","You are Dead.","Now What?","Congratulations, You Win!"};
    	for (int i = 0; i < 16; i++){	
    		final Page newPage = new Page();
    		newPage.setTitle(pageTitles[i]);
    		newPage.setPageEnding(endings[i]);
    		switch(i){ 
    			case(0):
    				newStory.setFirstpage(newPage.getId());
    				TextTile tile0 = new TextTile("Billy the Unicorn feels like eating some greasy junk food");
    				newPage.addTile(tile0);
    				Comment comment = new Comment("Fries all the way!");
    				
    				newPage.addComment(comment);
    				break;
    			case(1):
    				
					TextTile tile1 = new TextTile("Billy the Unicorn continues his quest in search of some French Fries.");
					newPage.addTile(tile1);
					break;
    			case(2):
    				
					TextTile tile2 = new TextTile("Billy the Unicorn continues his quest in search of some Food.");
					newPage.addTile(tile2);
					break;
    			case(3):
    				
					TextTile tile3 = new TextTile("Billy the Unicorn continues his quest in search of some Onion Rings.");
					newPage.addTile(tile3);
					break;
    			case(4):
    				
					TextTile tile4 = new TextTile("Billy enters the vast arctic wasteland. Uninhabited by basically everything, there are only two directions to go");
					newPage.addTile(tile4);
					break;
    			case(5):
    				
					TextTile tile5 = new TextTile("Billy enters the vast desert. Uninhabited by basically everything, there are only two directions to go");
					newPage.addTile(tile5);
					break;
    			case(6):
    				
					TextTile tile6 = new TextTile("Billy enters the dense jungle. Uninhabited by basically everything, there are only two directions to go");
					newPage.addTile(tile6);
					break;
    			case(7):
    				
					TextTile tile7 = new TextTile("Billy enters the dark cave. Uninhabited by basically everything, there are only two directions to go");
					newPage.addTile(tile7);
					break;
    			case(8):
    				
					TextTile tile8 = new TextTile("Billy enters the abandoned industrial complex. Uninhabited by basically everything, there are only two directions to go");
					newPage.addTile(tile8);
					break;
    			case(9):
    				
					TextTile tile9 = new TextTile("As if the gods had answered his prayers for food, the Onion Ring shaped statue had a freshly baked batch of greasy onion rings");
					newPage.addTile(tile9);
					break;
    			case(10):
    				
					TextTile tile10 = new TextTile("After 37 hours of traversing brutal conditions, Billy's nervous system shut off due to lack of oxygen and nutrients from starvation. Billy's heart suddenly stopped and he collapsed into a heap. Vultures, Timber Wolves, Giant Cave Worms and Tigers travelled miles to eat meat from the magical unicorn.  ");
					newPage.addTile(tile10);
					break;
    			case(11):
    				
					TextTile tile11 = new TextTile("As you enter the temple, which has clearly been empty for thousands of years, you come across two doors  ");
					newPage.addTile(tile11);
					break;
    			case(12):
    				
					TextTile tile12 = new TextTile("Inside the Onion Ring factory was a trap set up be Neo-Nazi's! In order to escape you must act fast! There are a few supply doors you might be able to use to escape!  ");
					newPage.addTile(tile12);
					break;
    			case(13):
    				
					TextTile tile13 = new TextTile(   "Billy takes one last gasp as his latest breath is corrupted with the small Anthrax spores he was unaware of.\n\n"
													+ "Due to Billy's magical Unicorn magic, the Anthrax sets into his gastrointestinal system almost instantly,"
													+ " causing him to begin stumbling.\n\n As the pains sharped in his gut, he begins vomitted blood as his intenstines, esophagus,"
													+ "and bowels begin swelling and forming lesions.\n\n While this is going on, the pulminary spores that had made their way into his lungs are now"
													+ " filling his airways with fluid, and his breaths become shorter and shorter.\n\n The only air Billy can manage to get are the few breaths between"
													+ " the blood his is so rapidly vommiting up.\n\n At this point, Billy can no longer remain standing and collapses into a pool of his own blood.\n\n "
													+ "His lungs are now almost at full capacity, and the weight of breathing is nearly unbearable.\n\n His central nervous system begins shutting down due to"
													+ "a lack of oxygen in the little blood he has remaining, and his heart rate is at a near flatline.\n\n As his brain begins to starve, he "
													+ "begins twitching uncontrollably and random nerve impulses spread through his body.\n\n The extra impulses to his heart act like a "
													+ "Super-powered Defibrillator and cause his heart to burst and spew blood through his thoracic cavity.\n\n As his vision begins blurring, he"
													+ "can see the Onion Rings laughing at him.\n\n They shriek in his deaf ears as the world begins to fade from view.\n\n Then Billy is still.\n\n His body remains still for several hundred years,"
													+ " as unbeknownst to Billy, he was wandering through the lost Kingdom Atlantis, which was on it's periodic cycle of rise and falling into "
													+ "the ocean.\n\n After several years of decay, Billy's skeleton and what remained of his interal organs were washed into the Ocean as Atlantis sunk once more.\n\n "
													+ "His bones sunk through the oceans and were eventually discovered by scientists in the 27th century as mislabelled as part of a prehistoric crustaceon.\n\nThe Onion Rings laughed their viscous laugh. ");
					newPage.addTile(tile13);
					Comment comment13 = new Comment("Well that escalated fast...");
    				newPage.addComment(comment13);
					break;				
    			case(14):
    				
					TextTile tile14 = new TextTile("As you continue onward, walking into the bright sunset, you realize that the direction you've heading is doing no good.");
					newPage.addTile(tile14);
					break;
    			case(15):
    				
					TextTile tile15 = new TextTile("As Billy creeps into the old looking room, he realizes it is infact an ancient kitchen. Sitting on the table are fries made by Ancient Aliens using advanced biochemicistry. As a result they are still perfectly fresh. As you take your first bite, you are the happiest Unicorn ever. The End ");
					newPage.addTile(tile15);
					break;
    		}
    		newStory.addPage(newPage);
    	}
    	for (int i = 0; i < 16; i++){	
    		Page newPage = newStory.getPages().get(i);
    		switch(i){
    			case(0):
    				Decision decision01 = new Decision("Fries",newStory.getPages().get(1));
    				Decision decision02 = new Decision("Onion Rings",newStory.getPages().get(3));
    				Decision decision03 = new Decision("Either",newStory.getPages().get(2));
    				newPage.addDecision(decision01);
    				newPage.addDecision(decision02);
    				newPage.addDecision(decision03);
    				break;
    			case(1):
    				Decision decision11 = new Decision("Go Left",newStory.getPages().get(4));
    				Decision decision12 = new Decision("Go Straight",newStory.getPages().get(5));
    				Decision decision13 = new Decision("Go Right",newStory.getPages().get(6));
    				newPage.addDecision(decision11);
    				newPage.addDecision(decision12);
    				newPage.addDecision(decision13);
    				break;
    			case(2):
    				Decision decision21 = new Decision("Follow the trail into the bushes",newStory.getPages().get(6));
    				Decision decision22 = new Decision("Follow the trail into a cave",newStory.getPages().get(7));   				
    				newPage.addDecision(decision21);
    				newPage.addDecision(decision22);  				
    				break;
    			case(3):
    				Decision decision31 = new Decision("Follow the trail into an urban looking area",newStory.getPages().get(8));
    				Decision decision32 = new Decision("Follow the trail into a cave",newStory.getPages().get(7));   				
    				newPage.addDecision(decision31);
    				newPage.addDecision(decision32);  				
    				break;
    			case(4):
    				Decision decision41 = new Decision("Proceed outward into the tundra",newStory.getPages().get(10));
    				Decision decision42 = new Decision("Go towards the glowing golden Onion Ring shaped object in the distance",newStory.getPages().get(9));   				
    				newPage.addDecision(decision41);
    				newPage.addDecision(decision42);  				
    				break;
    			case(5):
    				Decision decision51 = new Decision("Proceed outward into the desert",newStory.getPages().get(10));
    				Decision decision52 = new Decision("Go towards the glowing golden French Fry shaped temple in the distance",newStory.getPages().get(11));   				
    				newPage.addDecision(decision51);
    				newPage.addDecision(decision52);  				
    				break;
    			case(6):
    				Decision decision61 = new Decision("Proceed outward into the jungle",newStory.getPages().get(10));
    				Decision decision62 = new Decision("Go towards the glowing golden French Fry shaped temple in the distance",newStory.getPages().get(11));
    				Decision decision63 = new Decision("Go towards the glowing golden Onion Ring shaped object in the distance",newStory.getPages().get(9)); 
    				newPage.addDecision(decision61);
    				newPage.addDecision(decision62);  	
    				newPage.addDecision(decision63);  	
    				break;
    			case(7):
    				Decision decision71 = new Decision("Proceed outward into a dark mine shaft",newStory.getPages().get(10));
    				Decision decision72 = new Decision("Go towards the glowing golden Onion Ring shaped object in the distance through a branch in the path",newStory.getPages().get(9));   				
    				newPage.addDecision(decision71);
    				newPage.addDecision(decision72);  				
    				break;
    			case(8):
    				Decision decision81 = new Decision("Proceed outward into the Histler & Co. Onion Ring Factory",newStory.getPages().get(12));
    				Decision decision82 = new Decision("Go towards the glowing golden Onion Ring shaped object in the distance through a branch in the path",newStory.getPages().get(9));   				
    				newPage.addDecision(decision81);
    				newPage.addDecision(decision82);  				
    				break;
    			case(9):
    				Decision decision91 = new Decision("Eat them",newStory.getPages().get(13));
    				Decision decision92 = new Decision("Leave",newStory.getPages().get(14));   				
    				newPage.addDecision(decision91);
    				newPage.addDecision(decision92);  				
    				break;
    			case(11):
    				Decision decision111 = new Decision("A collapsed and rotting wooden door, possible leading into a slaves quarters or storage area.",newStory.getPages().get(15));
    				Decision decision112 = new Decision("A grand golden door, possibly that of a great ancient ruler",newStory.getPages().get(14));   				
    				newPage.addDecision(decision111);
    				newPage.addDecision(decision112);  				
    				break;
    			case(12):
    				Decision decision121 = new Decision("'Emergency Exit'",newStory.getPages().get(14));
    				Decision decision122 = new Decision("'Chemical Storage {Biohazard}'",newStory.getPages().get(13));   				
    				newPage.addDecision(decision121);
    				newPage.addDecision(decision122);  				
    				break;
    			case(15):
    				Decision decision151 = new Decision("Walk back to the where you began?",newStory.getPages().get(0)); 				
    				newPage.addDecision(decision151);			
    				break;
    				
    		}
    	}
    	
    	outStory = newStory;
    	
	}

	public Story getStory() {
		return outStory;
	}

	
}
