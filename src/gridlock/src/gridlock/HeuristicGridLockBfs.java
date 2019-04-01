package gridlock;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.Problem;
import ar.edu.itba.sia.gps.api.Rule;
import ar.edu.itba.sia.gps.api.State;

import java.util.*;

public class HeuristicGridLockBfs implements Heuristic {
    Queue<Node> open;
    Map<State, Integer> bestEstimation;
    Set<State> analized;
    Problem problem;
    int depth;
    Heuristic heuristic;

    public HeuristicGridLockBfs(int maxDepth, Problem problem, Heuristic heuristic) {
        this.depth          = maxDepth;
        this.open           = new LinkedList<>();
        this.bestEstimation = new HashMap<>();
        this.problem        = problem;
        this.heuristic      = heuristic;
        this.analized       = new HashSet<>();
    }

    @Override
    public Integer getValue(State state) {
        if(bestEstimation.containsKey(state)) {
            return bestEstimation.get(state);
        }

        Node root = new Node(state, 0, null);
        return processBfs(root);
    }

    private Integer processBfs(Node root) {
        open.add(root);

        boolean foundSolution = false;
        while (!foundSolution && open.size() > 0 && open.peek().getDepth() < depth) {
            Node currentNode = open.remove();
            if (problem.isGoal(currentNode.getState())) {
                updateFathers(currentNode, 0);
                foundSolution = true;
            }
            else {
                explodeBfs(currentNode);
            }
        }

        analized.clear();
        int estimationAux;
        Node node;
        while (open.size() > 0) {
            node = open.remove();
            estimationAux = heuristic.getValue(node.getState());
            if(!isBest(node.getState(), estimationAux)) {
                estimationAux =  bestEstimation.get(node.getState());
            }

            updateFathers(node.getParent(), estimationAux+1);
        }
        int ans = bestEstimation.get(root.getState());
        return ans;
    }

    private void explodeBfs(Node node) {
        if(analized.contains(node.getState())) {
            return;
        }

        analized.add(node.getState());
        addCandidates(node);
    }

    private void addCandidates(Node node) {
        for (Rule rule : problem.getRules()) {
            Optional<State> newState = rule.apply(node.getState());
            if (newState.isPresent()) {
                Node newNode = new Node(newState.get(), node.getCost() + rule.getCost(), rule);
                newNode.setParent(node);
                newNode.setLevel(node.getDepth() + 1);
                open.add(newNode);
            }
        }
    }

    private void updateFathers(Node node, int estimation) {
        Node aux = node;
        int estimationAux = estimation;
        while (aux != null) {
            if(isBest(aux.getState(), estimationAux)) {
                bestEstimation.put(aux.getState(), estimationAux);
            }
            estimationAux++;
            aux = aux.parent;
        }
    }

    private boolean isBest(State state, Integer estimation) {
        return !bestEstimation.containsKey(state) || estimation < bestEstimation.get(state);
    }

    @Override
    public String toString() {
        return "Makes a BFS using the state as the root";
    }

    private class Node {
        private State state;
        private Node parent;
        private Integer cost;
        private int level;
        private Rule generationRule;

        public Node(State state, Integer cost, Rule generationRule) {
            this.state = state;
            this.cost = cost;
            this.generationRule = generationRule;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public State getState() {
            return state;
        }

        public Integer getCost() {
            return cost;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        @Override
        public String toString() {
            return state.toString();
        }

        public String getSolution() {
            if (this.parent == null) {
                return this.state.toString();
            }
            return this.parent.getSolution() + this.state.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (state == null) {
                if (other.state != null)
                    return false;
            } else if (!state.equals(other.state))
                return false;
            return true;
        }

        public Rule getGenerationRule() {
            return generationRule;
        }

        public void setGenerationRule(Rule generationRule) {
            this.generationRule = generationRule;
        }

        public Integer getDepth() {
            return level;
        }
    }
}
