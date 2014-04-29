/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.infraestructura.util;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author proyectosbeta
 */
public class StopWords {
    // Objetos de clase.
    /*
     * Aca se incluyen los StopWords de la clase SpanishAnalyzer que trae Apache
     * Lucene con las etiquetas html y demas cosas que no sirven para la busqueda.
     */
    private static String[] s_stopWords = {"mucho", "tuviese", "m??os", "seremos", 
        "del", "ser??n", "vuestros", "hube", "tus", "desde", "habr??amos", "les", 
        "est??", "estuvierais", "hubieras", "durante", "otras", "m??", "habido", 
        "est??", "estar??n", "fuera", "algunas", "tendr??as", "estar??s", "habr??is", 
        "al", "tengan", "quien", "son", "habr??an", "tengas", "habr??as", "estar", 
        "estas", "sobre", "estar??is", "unos", "nosotros", "tuvieron", "m??a", 
        "estuviste", "estar??an", "se??is", "hab??amos", "todo", "tiene", "estar??as", 
        "tuyas", "tenemos", "hubi??ramos", "m??o", "sus", "se", "algo", "tendr??n", 
        "mis", "por", "ser??as", "sea", "estuvi??ramos", "ella", "vuestras", "entre", 
        "tendr??s", "a", "su", "teniendo", "esas", "fuiste", "tuvieseis", "o", "te", 
        "ti", "habr??n", "tendremos", "tuve", "y", "habr??s", "hubimos", "fueran", 
        "tu", "fuese", "tendr??", "est??is", "sois", "hasta", "suyos", "ser??", "fueras", 
        "nosotras", "tuvieran", "tendr??an", "tuvieras", "estaban", "han", "habidas", 
        "tuvi??semos", "estabas", "has", "fueron", "ten??an", "estuve", "hay", "tendr??", 
        "ten??as", "??l", "el", "estuvo", "en", "hab??is", "poco", "es", "est??s", 
        "hab??an", "est??n", "teng??is", "hab??as", "ser??s", "hubisteis", "otros", 
        "hubiera", "que", "vosotros", "ser??is", "era", "suyas", "e", "tuviesen", 
        "tambi??n", "hayan", "nuestra", "tuvieses", "hayas", "otra", "antes", 
        "vuestra", "sin", "s??", "fueseis", "nuestro", "esta", "m??s", "eras", 
        "contra", "otro", "hubiese", "vuestro", "tenidos", "una", "estada", 
        "cuando", "estuvisteis", "todos", "estuvieron", "muchos", "pero", "hemos", 
        "uno", "habidos", "habr??", "ha", "tuvo", "tuvimos", "he", "estuviesen", 
        "habr??", "donde", "t??", "tienen", "ya", "fueses", "estemos", "tienes", 
        "ten??is", "como", "esos", "estad", "fuisteis", "habremos", "yo", "nuestros", 
        "seamos", "estaba", "siendo", "ser??amos", "fuimos", "estos", "ser??", "fuesen", 
        "estadas", "tendr??ais", "hay??is", "tuvi??ramos", "estuvimos", "somos", 
        "est??bamos", "ten??ais", "hubieses", "estuvieran", "hubo", "un", "estuvieras", 
        "estaremos", "qu??", "hab??ais", "tuya", "hubi??semos", "fuerais", "hubieseis", 
        "nos", "los", "est??is", "ser??ais", "tuvierais", "tuyo", "estuvi??semos", 
        "quienes", "hab??a", "ante", "no", "hubieron", "sido", "nuestras", "estado", 
        "tenida", "tenido", "nada", "estuviera", "esa", "ser??a", "la", "fue", "ese", 
        "le", "ser??an", "tened", "habida", "estuvieses", "estuvieseis", "ten??amos", 
        "eso", "con", "lo", "vosotras", "tuviera", "fui", "tuviste", "estar??", 
        "estar??a", "erais", "porque", "hubiste", "estar??", "estuviese", "estamos", 
        "ellas", "me", "eran", "de", "mi", "soy", "las", "este", "cual", "estar??amos", 
        "tenga", "esto", "hubieran", "hayamos", "est??n", "m??as", "est??s", "ni", 
        "tendr??amos", "tendr??a", "haya", "tengo", "estabais", "fu??semos", "habr??ais", 
        "estados", "tenidas", "muy", "??ramos", "seas", "eres", "algunos", "tuvisteis", 
        "tanto", "habr??a", "ellos", "para", "suya", "os", "fu??ramos", "tendr??is", 
        "hubierais", "hubiesen", "estoy", "ten??a", "habiendo", "estar??ais", "tengamos", 
        "tuyos", "suyo", "estando", "sean", "sitio.css", "mediacalcd", "www.infomedical.cl",
        "meta", "center", "you", "bottom", "set", "cccccc", "html", "g", "bottom", "by",
        "b.htm", "br", "cajit", "becaus", "boundri", "c", "c.htm", "addressed",
        "address", "b", "all", "border", "action", "includ", "activ", "siti",
        "codeoutsidehtmlislocked", "contenid", "cellpadding", "class", "colspan", 
        "confirm", "boundari", "as", "cajit", "cellspacing", "color", "afaramacolo.asp", 
        "_includ", "activ", "_siti",  "align", "a.htm", "84", "becaus", "body", 
        "7px", "acci", "896", "charset", "3344413", "_blank", "2390", "2315203", 
        "804", "_imag", "0", "11", "alt", "100", "10px", "8859", "562", "3331719", 
        "3", "1", "all.htm", "666666", "1999", "1px", "11px", "1.1", "6", 
        "200.63.97.72", "137", "content", "http", "htm", "file", "from", "head",
        "head", "href", "fax", "font", "form2", "g.htm", "h.htm", "hr", "doctyp",
        "cfe6ff", "div", "d", "domain", "f", "fon", "doctitl", "form", "fals",
        "function", "e.htm", "h", "height", "home", "d.htm", "css", "cajit",
        "22", "bgcolor", "cum", "contenid", "2", "boundari", "default.asp",
        "eacut", "editor", "dtd", "f.htm", "siti", "activ", "equiv", "for",
        "email", "becaus", "includ", "i" 
    
    };
    
    private HashSet<String> stopWords;

    // Constructor predeterminado.
    public StopWords() {
        stopWords = new HashSet<String>(s_stopWords.length);

        for(String stopWord : s_stopWords) {
            stopWords.add(stopWord);
        }
    } // Fin del constructor por predeterminado.
    
    /*
     * Metodo publico para saber si la palabra es un stopWord.
     */
    public boolean isStopWord(String word) {
        return stopWords.contains(word.toLowerCase());
    } // Fin del metodo publico isStopWord.
 
    /*
     * Metodo publico que devuelve todos los stopWords.
     */
    public Set<?> getS_stopWords(){
        return stopWords;
    } // Fin del metodo publico getS_stopWords.
} // Fin de la clase StopWords.