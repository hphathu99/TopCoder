/*
// BEGIN CUT HERE
// PROBLEM STATEMENT
// A common way to rank athletic teams is based on each team's winning percentage. However, when two teams have similar percentages, the team that played against better opponents will be ranked above the team that had easier opponents. The measure of how difficult a team's opponents are, is known as the team's strength of schedule. A team that has faced tough opponents is said to have a strong schedule, and a team with poor opponents is said to have a weak schedule.


We will use the cumulative winning percentage of Team X's opponents (the teams that Team X played against) as a measure of Team X's strength of schedule. This percentage is the number of wins recorded by Team X's opponents divided by the total games they played, excluding games that were played against Team X.



Given a String[] of teams that contains the name of each team and a String[] results that indicates the outcome of games played during the season, return a String[] containing the names of the teams, ordered from strongest schedule to weakest. Teams with equivalent schedule strengths should appear in increasing alphabetical order.



Element i of results corresponds to team i whose name is found in teams at index i. Character j of element i of results will be one of 'W', 'L' or '-' respectively indicating whether team i won, lost, or did not play a game against team j.


DEFINITION
Class:ScheduleStrength
Method:calculate
Parameters:String[], String[]
Returns:String[]
Method signature:String[] calculate(String[] teams, String[] results)


CONSTRAINTS
-teams will contain between 3 and 50 elements, inclusive.
-teams and results will contain the same number of elements.
-Each element of teams will contain between 1 and 50 characters, inclusive.
-Each element of teams will only contain uppercase letters ('A'-'Z').
-Each element of teams will be distinct.
-Each element of results will contain N characters, where N is the number of elements in teams.
-Each element of results will only contain 'W', 'L' and '-'.
-The ith character of the ith element of results will be '-'.
-If the jth character of the ith element of results is 'W' then the ith character of the jth element of results will be 'L', and vice versa.
-Every team will play at least two games.


EXAMPLES

0)
{"BEARS",
 "GIANTS",
 "COWBOYS",
 "BRONCOS",
 "DOLPHINS",
 "LIONS"}
{"-WLWW-",
 "L-WL-W",
 "WL---W",
 "LW--L-",
 "L--W--",
 "-LL---"}

Returns: {"BEARS", "DOLPHINS", "BRONCOS", "COWBOYS", "GIANTS", "LIONS" }

This season's results gives us the following win/loss records:


        wins - losses
BEARS      3 - 1
GIANTS     2 - 2  (1 loss to the BEARS)
COWBOYS    2 - 1  (1 win against the BEARS)
BRONCOS    1 - 2  (1 loss to the BEARS)
DOLPHINS   1 - 1  (1 loss to the BEARS)
LIONS      0 - 2  (did not play the BEARS)



The BEARS's opponents are the GIANTS, COWBOYS, BRONCOS and DOLPHINS. Their combined win/loss record is 6-6 (6 wins, 6 losses), but when we exclude games played against the BEARS, this record becomes 5-3. So, the BEARS have a schedule strength of 5/8 = .625


Here are the cumulative records and winning percentages calculated for each team:

           Record    Winning %
BEARS      5 - 3    5/8 = .625
GIANTS     4 - 4    4/8 = .5
COWBOYS    4 - 3    4/7 = .5714
BRONCOS    4 - 3    4/7 = .5714
DOLPHINS   3 - 2    3/5 = .6
LIONS      2 - 3    2/5 = .4



1)
{"BEARS",
 "COWBOYS",
 "GIANTS",
 "PACKERS"}

{"-LLW",
 "W-WW",
 "WL--",
 "LL--"}

Returns: {"GIANTS", "BEARS", "COWBOYS", "PACKERS" }

2)
{"AZTECS",
 "COUGARS",
 "COWBOYS",
 "FALCONS",
 "HORNEDFROGS",
 "LOBOS",
 "RAMS",
 "REBELS",
 "UTES"}

{"---L-L--W",
 "--LL-LWL-",
 "-W--WWLLW",
 "WW---L--W",
 "--L--W-L-",
 "WWLWL-LW-",
 "-LW--W-L-",
 "-WW-WLW--",
 "L-LL-----"}


Returns: {"HORNEDFROGS", "COUGARS", "RAMS", "LOBOS", "REBELS", "UTES", "COWBOYS", "AZTECS", "FALCONS" }

3)
{"E", "D", "C", "B", "Z"}
{
"--LLL",
"---LL",
"W---L",
"WW---",
"WWW--"
}

Returns: {"D", "E", "C", "Z", "B" }

// END CUT HERE
*/
import java.util.*;

public class ScheduleStrength {
	public String[] calculate(String[] teams, String[] results) {
		HashMap<Integer, int[]> mapWL = new HashMap<Integer, int[]>();
        for (int i = 0; i < results.length; i++) {
            int[] winLoss = new int[2];
            for(int c = 0; c < results[i].length(); c++) {
                if(results[i].charAt(c) == 'W') {
                    winLoss[0]++;
                } else if (results[i].charAt(c) == 'L') {
                    winLoss[1]++;
                } 
            }
            // System.out.println(teams[i] + " " + winLoss[0]  + " " + winLoss[1]);
            mapWL.put(new Integer(i), winLoss);
		}
		
        double[] winPerArr = new double[teams.length];
        HashMap<Integer, Double> mapRes = new HashMap<Integer, Double>();
        for(int i = 0; i < teams.length; i++) {
            double win = 0.0;
            double loss = 0.0;
            int cur = 0;
            for (Integer index:mapWL.keySet()) {
                int[] winLoss = mapWL.get(index);
                if(index.intValue() == i) {
                    cur = i;
                    win -= winLoss[1];
                    loss -= winLoss[0];
                    // System.out.println("same " + index + " " + win + " " + loss);
                } else {
                    // check whether that team plays with the current team
                    if(results[i].charAt(index) == '-') {
                        // System.out.print(index + " ");
                        continue;
                    } else {
                        win += winLoss[0];
                        loss += winLoss[1];
                        // System.out.println("nope " + index + " " + win + " " + loss);
                    }
                   
                }
            }
            double winPer = win/(win+loss);
            // System.out.println(win + " " + loss
            //  + " " + teams[i]);
            mapRes.put(Integer.valueOf(i), winPer);
            winPerArr[i] = winPer;
        }
        java.util.Arrays.sort(winPerArr);
        ArrayList<String> res = new ArrayList<String>();
        int index = 0;
        for(int i = winPerArr.length-1; i >= 0; i--) {
			for(Integer num:mapRes.keySet()) {
				if(mapRes.get(num) == winPerArr[i]) {
					if(!res.contains(teams[num.intValue()])) {
						res.add(teams[num.intValue()]);
					}
				}
			}
			index++;
		}
		String[] finRes = new String[res.size()];
		finRes = res.toArray(finRes);
        return finRes;
	}
	public static void main(String[] args) {
		ScheduleStrength temp = new ScheduleStrength();
		String[] teams = {"BEARS",
		"GIANTS",
		"COWBOYS",
		"BRONCOS",
		"DOLPHINS",
		"LIONS"};
		String[] results = {"-WLWW-",
		"L-WL-W",
		"WL---W",
		"LW--L-",
		"L--W--",
		"-LL---"};
		System.out.println(temp.calculate(teams, results));
	}
}

// Time complexity: O(n^2)
// Space complexity: O(n)
