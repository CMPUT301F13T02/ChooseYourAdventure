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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * This is the Adapter class for Decisions that allows them to be displayed in list-views. 
 * 
 * The DecisionAdapter aggregates Decisions and is responsible for generating the views
 * to visualize a decision. 
 */

public class DecisionAdapter extends BaseAdapter {
	private ArrayList<Decision> decisions;
	private Context context;
	/**
	 * Constructor for this Adapter to take an array of decisions
	 * @param commentsList The Decisions from a page
	 * @param ctx The android Context for this adapter
	 */
	public DecisionAdapter(ArrayList<Decision> decisionList, Context ctx) {
		decisions = decisionList;
		context = ctx;
	}
	/**
	 * This gets the current number of decisions
	 * @return The number of decisions
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return decisions.size();
	}
	/**
	 * This gets the current decision at the provided position
	 * @param Position in list
	 * @return The corresponding decision
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * This gets the ID of the item at the provided position
	 * @param Position in the list
	 * @return The ID
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * @param Input from the listview
	 * @return The row of the requested decision
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.decision_adapter_layout, parent, false); // Layout of row in ListView
		TextView decisionTextView = (TextView) row.findViewById(R.id.decisionTextView);
		Decision decision = decisions.get(position);
		decisionTextView.setText(decision.getText());
		return row;
	}

}
