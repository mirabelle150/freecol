/**
 *  Copyright (C) 2002-2007  The FreeCol Team
 *
 *  This file is part of FreeCol.
 *
 *  FreeCol is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FreeCol is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */



package net.sf.freecol.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.swing.KeyStroke;

import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.GUI;
import net.sf.freecol.client.gui.i18n.Messages;
import net.sf.freecol.common.model.Tile;
import net.sf.freecol.common.model.Unit;


/**
* An action for changing the view. This action will:
*
* <ol>
*     <li>Open a colony panel if the active unit is located on a tile with a colony.</li>
*     <li>If onboard a carrier then the carrier will be the active unit..</li>
*     <li>In other cases: switch to another unit on the same tile.
* </ol>
*/
public class ChangeAction extends MapboardAction {
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ChangeAction.class.getName());


    public static final String ID = "changeAction";


    /**
     * Creates this action.
     * @param freeColClient The main controller object for the client.
     */
    ChangeAction(FreeColClient freeColClient) {
        super(freeColClient, "menuBar.orders.nextUnitOnTile", null, KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
    }
    
    
    /**
     * Updates the "enabled"-status with the value
     * returned by {@link #shouldBeEnabled} and
     * updates the name of the action.
     */
    public void update() {
        super.update();
        
        GUI gui = getFreeColClient().getGUI();
        if (gui != null) {
        if (getFreeColClient().getGUI().getActiveUnit() != null) {
            Unit unit = getFreeColClient().getGUI().getActiveUnit();
            if (unit.getTile() != null) {
                if (unit.getColony() != null) {
                    putValue(NAME, Messages.message("menuBar.orders.enterColony"));
                } else if (unit.getLocation() instanceof Unit) {
                    putValue(NAME, Messages.message("menuBar.orders.selectCarrier"));
                } else {
                    putValue(NAME, Messages.message("menuBar.orders.nextUnitOnTile"));
                }
            }
        }
        }
    }
    
    /**
     * Checks if this action should be enabled.
     * 
     * @return <code>false</code> if there is no active unit.
     */
    protected boolean shouldBeEnabled() {    
        if (!super.shouldBeEnabled()) {
            return false;
        }        
        GUI gui = getFreeColClient().getGUI();
        if (gui == null) return false;
        
        Unit unit = getFreeColClient().getGUI().getActiveUnit();
        if (unit == null) {
            return false;
        } else {
            return unit.getTile() != null;
        }
    }
    
    /**
    * Returns the id of this <code>Option</code>.
    * @return "changeAction"
    */
    public String getId() {
        return ID;
    }

    /**
     * Applies this action.
     * @param e The <code>ActionEvent</code>.
     */
    public void actionPerformed(ActionEvent e) {
        Unit unit = getFreeColClient().getGUI().getActiveUnit();
        Tile tile = unit.getTile();

        if (tile.getColony() != null) {
            getFreeColClient().getCanvas().showColonyPanel(tile.getColony());
        } else if (unit.getLocation() instanceof Unit) {
            getFreeColClient().getGUI().setActiveUnit(((Unit) unit.getLocation()));
        } else {
            Iterator<Unit> unitIterator = tile.getUnitIterator();
            boolean activeUnitFound = false;
            while (unitIterator.hasNext()) {
                Unit u = unitIterator.next();
                if (u == unit) {
                    activeUnitFound = true;
                } else if (activeUnitFound && u.getState() == Unit.ACTIVE && u.getMovesLeft() > 0) {
                    getFreeColClient().getGUI().setActiveUnit(u);
                    return;
                }
            }
            unitIterator = tile.getUnitIterator();
            while (unitIterator.hasNext()) {
                Unit u = unitIterator.next();
                if (u == unit) {
                    return;
                } else if (u.getState() == Unit.ACTIVE && u.getMovesLeft() > 0) {
                    getFreeColClient().getGUI().setActiveUnit(u);
                    return;
                }
            }
        }
    }
}
