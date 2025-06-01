//src/algorithm/MaxFlow.java
package algorithm;
import network.Network;

public interface MaxFlow {
    int findMaxFlow(Network network, int source, int sink);
}
