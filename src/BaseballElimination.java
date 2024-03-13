import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BaseballElimination {
    private int TeamCount;
    private ArrayList<String> TeamNames;
    private int[][] g;
    private HashMap<String,Team> map;
    private HashMap<Integer,String> v2Name;
    private int flow;
    private class Team{
        String TeamName;
        int TeamId;
        int Wins;
        int Los;
        int Left;
        public Team(String TeamName,int Id,int Wins,int Los,int Left){
            this.TeamName = TeamName;
            this.Left = Left;
            this.Los = Los;
            this.Wins = Wins;
            this.TeamId = Id;
        }
    }
    private void isValid(String team) {
        if (team == null)
            throw new java.lang.IllegalArgumentException("the teamName is null");

        if (!TeamNames.contains(team))
            throw new java.lang.IllegalArgumentException("the tameName is wrong");
    }
    public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
    {
        if(filename == null)throw new IllegalArgumentException("The filename is Null!");
        In file = new In(filename);
        TeamCount = Integer.parseInt(file.readLine());
        g = new int[TeamCount][TeamCount];
        TeamNames = new ArrayList<String>();
        map = new HashMap<>();
        for(int i = 0;i<TeamCount;i++){
            String readLine = file.readLine().trim();   // 读取当前行
            String[] strs = readLine.split(" +");
            TeamNames.add(strs[0]);
            Team team = new Team(strs[0],i,Integer.parseInt(strs[1]),Integer.parseInt(strs[2]),Integer.parseInt(strs[3]));
            map.put(strs[0],team);
            for(int j = 4;j < 4 + TeamCount;j++){
                g[i][j-4] = Integer.parseInt(strs[j]);
            }
        }
    }
    public              int numberOfTeams()                        // number of teams
    {
        return TeamCount;
    }
    public Iterable<String> teams()                                // all teams
    {
        return TeamNames;
    }
    public              int wins(String team)                      // number of wins for given team
    {
        isValid(team);
        return map.get(team).Wins;
    }
    public              int losses(String team)                    // number of losses for given team
    {
        isValid(team);
        return map.get(team).Los;
    }
    public              int remaining(String team)                 // number of remaining games for given team
    {
        isValid(team);
        return map.get(team).Left;
    }
    public              int against(String team1, String team2)    // number of remaining games between team1 and team2
    {
        isValid(team1);
        isValid(team2);
        return g[map.get(team1).TeamId][map.get(team2).TeamId];
    }

    private FlowNetwork CreateNet(String team){
        int V = (TeamCount - 1) * (TeamCount - 2) / 2 + TeamCount + 1;
        int S = V - 2;
        int T = V - 1;
        int MatchV = TeamCount - 1;
        int TheMostWins = map.get(team).Wins + map.get(team).Left;
        int TeamV = 0;
        FlowNetwork flowNetwork = new FlowNetwork(V);
        for(int i = 0;i<TeamCount;i++){
            if(i == map.get(team).TeamId)continue;
            int OtherMatchV = i + 1;
            for(int j = i+1;j<TeamCount;j++){
                if(j == map.get(team).TeamId)continue;
                flow += g[i][j];
                FlowEdge flowEdge = new FlowEdge(S,MatchV,g[i][j]);
                flowNetwork.addEdge(flowEdge);
                flowEdge = new FlowEdge(MatchV,TeamV,Double.MAX_VALUE);
                flowNetwork.addEdge(flowEdge);
                flowEdge = new FlowEdge(MatchV,OtherMatchV,Double.MAX_VALUE);
                flowNetwork.addEdge(flowEdge);
                MatchV++;
                OtherMatchV++;
            }
            flowNetwork.addEdge(new FlowEdge(TeamV,T,TheMostWins - map.get(TeamNames.get(i)).Wins));
            TeamV++;
        }
        return flowNetwork;
    }

    public          boolean isEliminated(String team)              // is given team eliminated?
    {
//        int TeamId = map.get(team).TeamId;
        isValid(team);
        flow = 0;
        int ThisMostWins = map.get(team).Wins + map.get(team).Left;
        for(int i = 0;i<TeamCount;i++){
            if(TeamNames.get(i).equals(team))continue;
            String curr = TeamNames.get(i);
            int count = map.get(curr).Wins;
            if(ThisMostWins<count)return true;
        }
        int V = (TeamCount - 1) * (TeamCount - 2) / 2 + TeamCount + 1;
        int S = V - 2;
        int T = V - 1;
        FlowNetwork flowNetwork = CreateNet(team);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork,S,T);
        if(flow > fordFulkerson.value())return true;
        return false;
    }
    public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
    {
        isValid(team);
        if(!isEliminated(team))return null;
        //ArrayList<String> arr = new ArrayList<>();
        HashSet<String> arr = new HashSet<>();
        int ThisMostWins = map.get(team).Wins + map.get(team).Left;
        for(int i = 0;i<TeamCount;i++){
            if(TeamNames.get(i).equals(team))continue;
            String curr = TeamNames.get(i);
            int count = map.get(curr).Wins;
            if(ThisMostWins<count){
                arr.add(curr);
            }
        }
        if(!arr.isEmpty())return arr;
        int V = (TeamCount - 1) * (TeamCount - 2) / 2 + TeamCount + 1;
        int S = V - 2;
        int T = V - 1;
        FlowNetwork flowNetwork = CreateNet(team);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork,S,T);
        int CurrId = map.get(team).TeamId;
        for(int i = 0;i<TeamCount;i++){
            if(TeamNames.get(i) == team)continue;
            if(CurrId>i) {
                if (fordFulkerson.inCut(i)) arr.add(TeamNames.get(i));
            }
            else {
                if(fordFulkerson.inCut(i-1))arr.add(TeamNames.get(i));
            }
        }
        if(arr.contains(team)){
            arr.remove(team);
        }
        return arr;
    }
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams5.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
