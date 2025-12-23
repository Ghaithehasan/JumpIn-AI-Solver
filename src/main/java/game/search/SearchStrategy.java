package game.search;

import model.Level;
import model.Node;

public interface SearchStrategy {

    Node search(Level initialLevel);
}
