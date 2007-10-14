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

package net.sf.freecol.common.model;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

/**
* Represents a <code>TileItem</code> item on a <code>Tile</code>.
*/
public abstract class TileItem extends FreeColGameObject implements Locatable, Named {


    protected Tile tile;
    
    /**
    * Creates a new <code>TileItem</code>.
    *
    * @param game The <code>Game</code> in which this object belong.
    * @param owner The owner of this <code>Settlement</code>.
    * @param tile The location of the <code>Settlement</code>.    
    */
    public TileItem(Game game, Tile tile) {
        super(game);
        if (tile == null) {
            throw new NullPointerException();
        }
        this.tile = tile;
    }

    /**
     * Initiates a new <code>TileItem</code> from an <code>Element</code>.
     *
     * @param game The <code>Game</code> in which this object belong.
     * @param in The input stream containing the XML.
     * @throws XMLStreamException if a problem was encountered
     *      during parsing.
     */
    public TileItem(Game game, XMLStreamReader in) throws XMLStreamException {
        super(game, in);
    }

    /**
     * Initiates a new <code>TileItem</code> from an <code>Element</code>.
     *
     * @param game The <code>Game</code> in which this object belong.
     * @param e An XML-element that will be used to initialize
     *      this object.
     */
    public TileItem(Game game, Element e) {
        super(game, e);
    }

    /**
     * Initiates a new <code>TileItem</code> 
     * with the given ID. The object should later be
     * initialized by calling either
     * {@link #readFromXML(XMLStreamReader)} or
     * {@link #readFromXMLElement(Element)}.
     *
     * @param game The <code>Game</code> in which this object belong.
     * @param id The unique identifier for this object.
     */
    public TileItem(Game game, String id) {
        super(game, id);
    }

    /**
     * Sets the location for this <code>TileItem</code>.
     * @param newLocation The new <code>Location</code> for the <code>TileItem</code>.
     */
    public void setLocation(Location newLocation) {
        if (newLocation instanceof Tile) {
            tile = ((Tile) newLocation);
        } else {
            throw new IllegalArgumentException("newLocation is not a Tile");
        }
    }

    /**
     * Gets the location of this <code>TileItem</code>.
     * @return The location of this <code>TileItem</code>.
     */
    public Location getLocation() {
        return ((Location) tile);
    }

    /**
     * Returns the <code>Tile</code> where this <code>TileItem</code> is located,
     * or <code>null</code> if it's location is <code>Europe</code>.
     *
     * @return The Tile where this Unit is located. Or null if
     * its location is Europe.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * <code>TileItem</code>s do not take any space, and cannot be taken carried.
     * @return Always 0.
     */
    public int getTakeSpace() {
        return 0;
    }

    /**
     * Disposes this TileItem.
     */
    public void dispose() {
        super.dispose();
    }
}
