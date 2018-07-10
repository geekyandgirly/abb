import java.util.*;

public class Pagination {

	static class Listing {
		int hostId;
		int listingId;
		float score;
		String city;


		public Listing(int hostId, int listingId, float score, String city) {
			this.hostId = hostId;
			this.listingId = listingId;
			this.score = score;
			this.city = city;
		}

		@Override
		public String toString() {
			return hostId + "," + listingId + "," + score + "," + city;
		}
	}

	static List<Listing> displayPages(List<String> input, int pageSize) {
		List<Listing> results = new ArrayList<>();
		LinkedHashMap<Integer, LinkedList<Listing>> dups = new LinkedHashMap<>();
		Set<Integer> curPageHostIds = new HashSet<>();

		int curPageSize = 0;
		for (String s : input) {
			Listing listing = parse(s);
			int hostId = listing.hostId;
			if (!curPageHostIds.contains(hostId)) {
				results.add(listing);
				curPageSize++;
				curPageHostIds.add(hostId);
			} else {
				LinkedList<Listing> dupsForHostId = dups.getOrDefault(hostId, new LinkedList<Listing>());
				dupsForHostId.add(listing);
				dups.put(hostId, dupsForHostId);
			}

			if (curPageSize == pageSize) {
				curPageSize = 0;
				curPageHostIds.clear();
			}
		}

		// finish parsing. add what's in dups into remaining page.
		PriorityQueue<Listing> q = new PriorityQueue<>(pageSize, new Comparator<Listing>() {
			@Override
			public int compare(Listing o1, Listing o2) {
				return Float.compare(o2.score, o1.score);
			}
		});

		while (!dups.isEmpty()) {
			fillQueue(q, dups, curPageHostIds, pageSize - curPageSize);

			while (!q.isEmpty()) {
				Listing next = q.poll();
				int hostId = next.hostId;

				results.add(next);
				curPageSize++;
				curPageHostIds.add(hostId);

				dups.get(hostId).removeFirst();
				if (dups.get(hostId).size() == 0) {
					dups.remove(hostId);
				}

				if (curPageSize == pageSize) {
					curPageSize = 0;
					curPageHostIds.clear();
					q.clear();
				}
			}
		}

		return results;
	}

	private static void fillQueue(PriorityQueue<Listing> q, LinkedHashMap<Integer, LinkedList<Listing>> dups, Set<Integer> curPageHostIds, int remainingPageSize) {
		Set<Integer> keySet = new LinkedHashSet<>(dups.keySet());
		keySet.removeAll(curPageHostIds);
		if (keySet.size() == 0) {
			keySet = dups.keySet();
		}
		for (int hostId : keySet) {
			Listing l = dups.get(hostId).getFirst();
			q.offer(l);
//            if (q.size() == remainingPageSize) {
//                break;
//            }
		}
	}

	private static Listing parse(String s) {
		String[] parts = s.split(",");
		return new Listing(
				Integer.parseInt(parts[0].trim()),
				Integer.parseInt(parts[1].trim()),
				Float.parseFloat(parts[2].trim()),
				parts[3].trim());
	}

	public static void main(String[] args) {
		int PER_PAGE = 6;

		ArrayList<String> input = new ArrayList<String>();
//        input.add("host_id,listing_id,score,city");
//        input.add("1,28,300.1,San Francisco");
//        input.add("4,5,209.1,San Francisco");
//        input.add("2,7,208.1,San Francisco");
//        input.add("3,8,207.1,San Francisco");
//        input.add("6,10,206.1,Oakland");
//        input.add("1,16,205.1,San Francisco");
//        input.add("6,29,204.1,San Francisco");
//        input.add("7,20,203.1,San Francisco");
//        input.add("8,21,202.1,San Francisco");
//        input.add("2,18,201.1,San Francisco");
//        input.add("2,30,200.1,San Francisco");
//        input.add("5,27,109.1,Oakland");
//        input.add("10,13,108.1,Oakland");
//        input.add("1,26,107.1,Oakland");
//        input.add("2,9,106.1,Oakland");
//        input.add("3,1,105.1,Oakland");
//        input.add("2,17,104.1,Oakland");
//        input.add("1,2,103.1,Oakland");
//        input.add("8,24,102.1,Oakland");
//        input.add("8,14,11.1,San Jose");
//        input.add("6,25,10.1,Oakland");
//        input.add("9,15,9.1,San Jose");
//        input.add("3,19,8.1,San Jose");
//        input.add("3,11,7.1,Oakland");
//        input.add("7,12,6.1,Oakland");
//        input.add("1,3,5.1,Oakland");
//        input.add("5,4,4.1,San Jose");
//        input.add("5,6,3.1,San Jose");
//        input.add("9,22,2.1,San Jose");
//        input.add("10,23,1.1,San Jose");
//
//        List<Listing> result = displayPages(input, PER_PAGE);
//
//        System.out.println("------------");
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i).toString());
//            if ((i +1) % PER_PAGE == 0) {
//                System.out.println();
//            }
//        }

		input.add("1,28,300.1,San Francisco");
		input.add("1,5,209.1,San Francisco");
		input.add("2,7,208.1,San Francisco");
		input.add("2,8,207.1,San Francisco");
		input.add("1,10,206.1,Oakland");
		input.add("1,16,205.1,San Francisco");
		input.add("2,29,204.1,San Francisco");
		input.add("2,20,203.1,San Francisco");
		input.add("1,21,202.1,San Francisco");
		input.add("2,18,201.1,San Francisco");
		input.add("1,30,200.1,San Francisco");
		input.add("2,31,200.1,San Francisco");

		List<Listing> result = displayPages(input, PER_PAGE);

		System.out.println("------------");
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).toString());
			if ((i + 1) % PER_PAGE == 0) {
				System.out.println();
			}
		}
	}
}