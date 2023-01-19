package PrimNumberGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{
	
	List<Integer> list = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();

	public PrimListModel() {
		list.add(1);
	}

	public List<Integer> getList() {
		return list;
	}



	public void next() {
		int last = list.get(list.size() - 1);
		int next = last + 1;
		while (!isPrime(next)) {
			next++;
		}
		list.add(next);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, list.size(), list.size());
		listeners.forEach(l -> l.intervalAdded(event));
	}

	private boolean isPrime(int number) {
		if (number <= 1) {
			return false;
		}
		if (number == 2) {
			return true;
		}
		int max = (int) Math.sqrt(number);
		for (int i = 2; i <= max; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getSize() {
	
		return list.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return list.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}




}
