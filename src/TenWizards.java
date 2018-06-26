import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TenWizards {
    public class Wizard{
        int id, minCost;
        public Wizard(int idx, int dis){
            this.id = idx;
            this.minCost = dis;
        }
    }
    public int cost(List<List<Integer>> adjList){
        int n = adjList.size();

        Wizard[] wizards = new Wizard[n];
        for(int i = 0; i < n; i++) {
            wizards[i] = new Wizard(i, i == 0 ? 0 : Integer.MAX_VALUE);
        }

        Queue<Wizard> queue = new LinkedList<>();
        queue.offer(wizards[0]);
        while(!queue.isEmpty()){
            Wizard w = queue.poll();
            for(int nextW : adjList.get(w.id)){
                int newCost = w.minCost + (w.id - nextW) * (w.id - nextW);
                if(wizards[nextW].minCost > newCost){
                        wizards[nextW].minCost = newCost;
                    queue.offer(wizards[nextW]);
                }
            }
        }
        return wizards[n-1].minCost;
    }
}
