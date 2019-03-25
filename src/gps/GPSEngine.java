package gps;

import java.util.*;

import gps.api.Heuristic;
import gps.api.Problem;
import gps.api.Rule;
import gps.api.State;

import static gps.SearchStrategy.GREEDY;

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
		open = new LinkedList<>();
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
		open.add(rootNode);
		// TODO: ¿Lógica de IDDFS?
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
		failed = true;
		finished = true;
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
			for(GPSNode n : newCandidates)
				open.offer(n);
			break;
		case DFS:
			if (bestCosts.containsKey(node.getState())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			for(GPSNode n : newCandidates)
				((LinkedList<GPSNode>)open).push(n);
			break;
		case IDDFS:
			if (bestCosts.containsKey(node.getState())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			// TODO: ¿Cómo se agregan los nodos a open en IDDFS?
			break;
		case GREEDY:
			greedy(node);
			break;
		case ASTAR:
			if (!isBest(node.getState(), node.getCost())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			// TODO: ¿Cómo se agregan los nodos a open en A*?
			break;
		}
	}

	private void addCandidates(GPSNode node, Collection<GPSNode> candidates) {
		explosionCounter++;
		updateBest(node);
		for (Rule rule : problem.getRules()) {
			Optional<State> newState = rule.apply(node.getState());
			if (newState.isPresent()) {
				GPSNode newNode = new GPSNode(newState.get(), node.getCost() + rule.getCost(), rule);
				newNode.setParent(node);
				candidates.add(newNode);
			}
		}
	}

	private boolean isBest(State state, Integer cost) {
		return !bestCosts.containsKey(state) || cost < bestCosts.get(state);
	}

	private void updateBest(GPSNode node) {
		bestCosts.put(node.getState(), node.getCost());
	}

	private void greedy(GPSNode node) {
		Collection<GPSNode> newCandidates;
		if (bestCosts.containsKey(node.getState())) {
			return;
		}

		Comparator<GPSNode> comparator;
		if(heuristic.isPresent()) {
			comparator =
					(n1, n2) -> {
						int aux = heuristic.get().getValue(n1.getState())
								.compareTo(heuristic.get().getValue(n2.getState()));
						return aux;
					};
		}
		else {
			throw new RuntimeException("Cannot perform Greedy without heuristic");
		}

		newCandidates =  new PriorityQueue<>(comparator);
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

}
