package gps;

import java.util.*;
import gps.api.Heuristic;
import gps.api.Problem;
import gps.api.Rule;
import gps.api.State;

import static gps.SearchStrategy.*;

public class GPSEngine {

	Queue<GPSNode> open;
	Map<State, Integer> bestCosts;
	Problem problem;
	long explosionCounter;
	boolean finished;
	boolean failed;
	GPSNode solutionNode;
	Optional<Heuristic> heuristic;

	// Use this variable in open set order.
	protected SearchStrategy strategy;

	public GPSEngine(Problem problem, SearchStrategy strategy, Heuristic heuristic) {
		if(strategy == ASTAR) {
			Comparator<GPSNode> comparator = new CostPlusHeuristicComparator();
			open = new PriorityQueue<>(comparator);
		} else {
			open = new LinkedList<>();
		}
        bestCosts = new HashMap<>();
        this.problem = problem;
		this.strategy = strategy;
		this.heuristic = heuristic == null? Optional.empty() : Optional.of(heuristic);
		explosionCounter = 0;
		finished = false;
		failed = false;
	}

	public void findSolution() {
		GPSNode rootNode = new GPSNode(problem.getInitState(), 0, null);
		rootNode.setLevel(0);
		open.add(rootNode);

		if (strategy == IDDFS) {
			int maxDepth = 50;
			IDDFS(maxDepth, rootNode);
			return;
		} else {
			while (open.size() > 0) {
				GPSNode currentNode = open.remove();
				if (problem.isGoal(currentNode.getState())) {
					finished = true;
					solutionNode = currentNode;
					return;
				} else {
					explode(currentNode);
				}
			}
		}

		failed = true;
		finished = true;
	}

	private void IDDFS(int maxDepth, GPSNode rootNode) {
		for (int depthBound = 0; depthBound <= maxDepth; depthBound++) {
			DFSWithDepthBound(depthBound);

			if (finished) {
				return;
			}

			bestCosts.clear();
			((LinkedList<GPSNode>)open).push(rootNode);
		}

		failed = true;
		finished = true;
	}

	private void DFSWithDepthBound(int depthBound) {
		while (!open.isEmpty() && !finished) {
			GPSNode currentNode = ((LinkedList<GPSNode>) open).pop();

			if (problem.isGoal(currentNode.getState())) {
				finished = true;
				solutionNode = currentNode;
			} else if (currentNode.getDepth() < depthBound) {
				explode(currentNode);
			}
		}
	}

	private void explode(GPSNode node) {
		Collection<GPSNode> newCandidates;
		switch (strategy) {
		case BFS:
			if (bestCosts.containsKey(node.getState())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			for (GPSNode n : newCandidates)
				open.offer(n);
			break;
		case DFS:
			if (bestCosts.containsKey(node.getState())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			for (GPSNode n : newCandidates)
				((LinkedList<GPSNode>)open).push(n);
			break;
		case IDDFS:
			if (!bestCosts.containsKey(node.getState()))
                addCandidates(node, open);
			break;
		case GREEDY:
			greedy(node);
			break;
		case ASTAR:
			aStar(node);
            break;
		}
	}

    private void aStar(GPSNode nodeToExplode) {
        if (!isBest(nodeToExplode.getState(), nodeToExplode.getCost()))
            return;

        Comparator<GPSNode> comparator = new CostPlusHeuristicComparator();
        PriorityQueue<GPSNode> candidates = new PriorityQueue<>(comparator);
        addCandidates(nodeToExplode, candidates);


		for (GPSNode candidate:candidates) {
			open.add(candidate);
		}
//        while (!open.isEmpty())
//            candidates.add(open.remove());
//
//        while (!candidates.isEmpty()) {
//            GPSNode node = candidates.poll();
//            List<GPSNode> tiedNodes = new ArrayList<>();
//            tiedNodes.add(node);
//
//            while (!candidates.isEmpty() && comparator.compare(candidates.peek(), node) == 0)
//                tiedNodes.add(candidates.poll());
//
//            if (tiedNodes.size() > 1)
//                Collections.shuffle(tiedNodes);
//
//            for (GPSNode n : tiedNodes)
//                open.offer(n);
//        }
    }

	private void addCandidates(GPSNode node, Collection<GPSNode> candidates) {
		explosionCounter++;
		updateBest(node);
		for (Rule rule : problem.getRules()) {
			Optional<State> newState = rule.apply(node.getState());
			if (newState.isPresent()) {
				GPSNode newNode = new GPSNode(newState.get(), node.getCost() + rule.getCost(), rule);
				newNode.setParent(node);
				newNode.setLevel(node.getDepth() + 1);
				candidates.add(newNode);
			}
		}
	}

	private Integer getCostPlusHeuristic(GPSNode node) {
        if (!heuristic.isPresent())
            throw new RuntimeException("Not heuristic found for A* algorithm");

        return heuristic.get().getValue(node.getState()) + node.getCost();
    }

	private boolean isBest(State state, Integer cost) {
		return !bestCosts.containsKey(state) || cost < bestCosts.get(state);
	}

	private void updateBest(GPSNode node) {
		bestCosts.put(node.getState(), node.getCost());
	}

	private void greedy(GPSNode node) {
		if (bestCosts.containsKey(node.getState())) {
			return;
		}

		Comparator<GPSNode> comparator;

		if (heuristic.isPresent()) {
			comparator =
					(n1, n2) -> {
						int aux = heuristic.get().getValue(n2.getState())
								.compareTo(heuristic.get().getValue(n1.getState()));
						return aux;
					};
		}
		else {
			throw new RuntimeException("Cannot perform Greedy without heuristic");
		}

		Collection<GPSNode> newCandidates = new PriorityQueue<>(comparator);
		addCandidates(node, newCandidates);

		GPSNode last = null;
		LinkedList<GPSNode> aux = new LinkedList<>();
		for (GPSNode candidate: newCandidates) {
			if(last != null && comparator.compare(last, candidate) != 0) {
				Collections.shuffle(aux); //Random equals
				for (GPSNode nodeEquals : aux) {
					((LinkedList<GPSNode>) open).push(nodeEquals);
				}
				aux.clear();
			}
			last = candidate;
			aux.add(candidate);
		}
		Collections.shuffle(aux); //Random equals
		for (GPSNode nodeEquals : aux) {
			((LinkedList<GPSNode>) open).push(nodeEquals);
		}
	}

	// GETTERS FOR THE PEOPLE!

	public Queue<GPSNode> getOpen() {
		return open;
	}

	public Map<State, Integer> getBestCosts() {
		return bestCosts;
	}

	public Problem getProblem() {
		return problem;
	}

	public long getExplosionCounter() {
		return explosionCounter;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isFailed() {
		return failed;
	}

	public GPSNode getSolutionNode() {
		return solutionNode;
	}

	public SearchStrategy getStrategy() {
		return strategy;
	}

    /**
     * Order by less HEURISTIC + COST
     * If they are equal, try to order by less HEURISTIC
     */
    class CostPlusHeuristicComparator implements Comparator<GPSNode> {

        @Override
        public int compare(GPSNode node1, GPSNode node2) {
            if (!heuristic.isPresent())
                throw new RuntimeException("Not heuristic found for A* algorithm");

            Heuristic h = heuristic.get();
            int firstComparison = getCostPlusHeuristic(node1).compareTo(getCostPlusHeuristic(node2));

            if (firstComparison != 0)
                return firstComparison;

            return h.getValue(node1.getState()).compareTo(h.getValue(node2.getState()));
        }
    }
}
