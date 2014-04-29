import java.util.ArrayList;

public class Main {
	public static void main (String args[]) {
		ArrayList<Opinion> opinion_lst;
		opinion_lst = GestionOpinion.getLstOpinion ();
		
		System.out.println ("n: " + opinion_lst.size ());
		int to = Integer.parseInt (args[0]);
		for (int i = 0; i < to && to < opinion_lst.size () ; i++) {
			System.out.println (opinion_lst.get (i));
		}
	}
}
