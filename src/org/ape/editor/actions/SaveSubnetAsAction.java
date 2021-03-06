/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * Copyright (C) 2013 Hillit Saathoff <mail at hillit.de>
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

package org.ape.editor.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.ape.editor.controller.Root;
import org.ape.editor.filechooser.FileChooserDialog;
import org.ape.editor.filechooser.FileTypeException;
import org.ape.editor.filechooser.PflowxFileType;
import org.ape.petrinet.model.Document;
import org.ape.petrinet.model.PetriNet;
import org.ape.petrinet.model.Subnet;
import org.ape.util.GraphicsTools;

/**
 * 
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class SaveSubnetAsAction extends AbstractAction {

	private Root root;

	public SaveSubnetAsAction(Root root) {
		this.root = root;
		String name = "Save subnet as...";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		putValue(SMALL_ICON, GraphicsTools.getIcon("ape/savesubnetas.gif"));
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (root.getClickedElement() instanceof Subnet) {
			Subnet subnet = (Subnet) root.getClickedElement();

			FileChooserDialog chooser = new FileChooserDialog();

			String subnetLabel = subnet.getLabel();
			if (subnetLabel != null && !subnetLabel.equals("")) {
				chooser.setSelectedFile(new File(chooser.getCurrentDirectory()
						.getAbsolutePath() + "/" + subnetLabel));
			}

			chooser.setFileFilter(new PflowxFileType());
			chooser.setCurrentDirectory(root.getCurrentDirectory());
			chooser.setDialogTitle("Save subnet as...");

			if (chooser.showSaveDialog(root.getParentFrame()) == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				if (!file.exists()
						|| JOptionPane.showConfirmDialog(root.getParentFrame(),
								"Selected file exists. Overwrite?") == JOptionPane.YES_OPTION) {
					try {
						exportSubnet(subnet, file);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(root.getParentFrame(),
								ex.getMessage());
					}
				}
			}
			root.setCurrentDirectory(chooser.getCurrentDirectory());
		}
	}

	private void exportSubnet(Subnet subnet, File file)
			throws FileTypeException {
		Document document = new Document();
		PetriNet petriNet = document.petriNet;
		petriNet.setRootSubnet(subnet);
		new PflowxFileType().save(document, file);
	}
}
