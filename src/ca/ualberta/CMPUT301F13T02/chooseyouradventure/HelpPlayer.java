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

import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.view.MenuItem;

/**
 * This class is responsible for playing the audio help in the application. It is also responsible for 
 * managing the help items in the menu -- enabling and disabling them as necessary.
 * 
 * This class uses the singleton design pattern and also the subscriber pattern.
 * 
 * @author Konrad Lindenbach
 */
public class HelpPlayer {

	private static HelpPlayer instance = null;
	private ArrayList<MenuItem> helpButtons = new ArrayList<MenuItem>();
	private MediaPlayer mp = new MediaPlayer();
	
	/**
	 * Constructor marked protected in singleton pattern 
	 * 
	 * @author Konrad Lindenbach
	 */
	protected HelpPlayer() {
	}
	
	/**
	 * Get an instance of this class, instantiating it if necessary
	 * 
	 * @author Konrad Lindenbach
	 */
	public static HelpPlayer getInstance() {
		if (instance == null) {
			instance = new HelpPlayer();
			return instance;
		}

		return instance;
	}
	
	/**
	 * Play the audio corresponding to the passed resource, using the passed context.
	 * This method also makes sure to disable help buttons while audio is playing and re-enable them
	 * when audio stops.
	 * 
	 * @param ctx The context to use to play this resource
	 * @param resource The resource to play
	 * @author Konrad Lindenbach
	 */
	public void play(Context ctx, int resource) {
	
		try {
			AssetFileDescriptor afd = ctx.getResources().openRawResourceFd(resource);
			
			mp.stop();
			mp.reset();
			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
			mp.prepare();
			mp.start();
			mp.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					setButtonsEnabled(true);
				}
			});
			
			afd.close();
			
			setButtonsEnabled(false);
		}
		catch (Exception e) {
			Log.d("help", "Help failed to play because of exception " + e.getMessage());
		}
	}
	
	/**
	 * Returns whether or not any help audio is playing
	 * 
	 * @return Whether or not any help audio is playing
	 * @author Konrad Lindenbach
	 */
	public boolean isPlaying() {
		return mp.isPlaying();
	}
	
	/**
	 * Adds the passed MenuItem as a subscriber of this object 
	 * so it can be enable and disabled as necessary
	 * 
	 * @param item The item to subscribe
	 * @author Konrad Lindenbach
	 */
	public void trackHelpItem(MenuItem item) {
		helpButtons.add(item);
	}

	private void setButtonsEnabled(boolean state) {
		for (MenuItem m : helpButtons) {
			m.setEnabled(state);
		}
	}
}
