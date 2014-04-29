import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;

import cl.infraestructura.util.TablaValores;

public class GestionOpinion {
	private static ArrayList<Opinion> opinion_lst;
	public static ArrayList<Opinion> getLstOpinion () {
		opinion_lst = new ArrayList<Opinion> ();
		File f = new File (TablaValores.getValor(tableParametros, "archivo", "path"));
		BufferedReader input;
		Opinion opinion = null;

		try {
			input = new BufferedReader (new FileReader (f));
			
			while (input.ready ()) {
				opinion = new Opinion ((input.readLine ().split (":"))[1].trim (),
					Integer.parseInt((input.readLine ().split (":"))[1].trim ()),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim ());
					input.readLine (); //empty line

				opinion_lst.add (opinion);
			} 	

		} catch (IOException ioEx) {
			ioEx.printStackTrace ();
		}

		return opinion_lst;
	}
}
