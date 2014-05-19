package luceneBusqueda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GestionOpinion {
	private static String rutaOpinion = "/home/nnarria/magister/2do_anno/1er_semestre/search_web/libs/out_opinion.txt";
	private static ArrayList<Opinion> opinion_lst;
	
	public static ArrayList<Opinion> getLstOpinion () {
		opinion_lst = new ArrayList<Opinion> ();
		File f = new File (rutaOpinion);
		BufferedReader input;
		Opinion opinion = null;

		try {
			input = new BufferedReader (new FileReader (f));
			
			while (input.ready ()) {
				input.readLine (); //empty line
				opinion = new Opinion (
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					Integer.parseInt((input.readLine ().split (":"))[1].trim ()),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim ());
				input.readLine (); //empty line

				if (exist (opinion) == 0) 
					opinion_lst.add (opinion);
			}
			input.close();
			
		} catch (IOException ioEx) {
			ioEx.printStackTrace ();
		}

		
		return opinion_lst;
	}

	private static int exist (Opinion opinion) {
		int i;
		Opinion opinion_tmp;
		for (i = 0; i < opinion_lst.size(); i++) {
			opinion_tmp = opinion_lst.get (i);
			if (opinion_tmp.getMarca ().equals (opinion.getMarca ()) &&
				opinion_tmp.getModelo ().equals (opinion.getModelo ()) &&
				opinion_tmp.getAuthor ().equals (opinion.getAuthor ()) &&
				opinion_tmp.getValoracion () == opinion.getValoracion () &&
				opinion_tmp.getLoMejor ().equals (opinion.getLoMejor ()) &&
				opinion_tmp.getLoPeor ().equals (opinion.getLoPeor ()) &&
				opinion_tmp.getOpinion ().equals (opinion.getOpinion ()))
				return 1;
		}

		return 0;
	}  
}