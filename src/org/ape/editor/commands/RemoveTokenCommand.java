/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ape.editor.commands;

import org.ape.petrinet.model.Marking;
import org.ape.petrinet.model.PlaceNode;
import org.ape.util.Command;

/**
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class RemoveTokenCommand implements Command {
	
	private PlaceNode placeNode;
	private Marking marking;


	public RemoveTokenCommand(PlaceNode placeNode, Marking marking) {
		this.placeNode = placeNode;
		this.marking = marking;
	}

	public void execute() {
		if (marking.getTokens(placeNode) >= 1) {
			marking.setTokens(placeNode, marking.getTokens(placeNode) - 1);
		}
	}

	public void undo() {
		new AddTokenCommand(placeNode, marking).execute();
	}

	public void redo() {
		execute();
	}

	@Override
	public String toString() {
		return "Remove token";
	}
	
	
}
