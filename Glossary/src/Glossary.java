import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Takes an input text file and outputs a series of html files to a specified
 * folder in the form of an alphabetized glossary with hyperlinked terms that
 * lead to pages for their definitions, with a button leading back to the index.
 * If a term appears in a definition, it will be a hyperlink leading to that
 * term's definition
 *
 * @author Jesse Shields
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
        // no code needed here
    }

    /**
     * Prompts the user for the relative path to the file location and then
     * returns that address in a string.
     *
     * @param in
     *            SimpleReader data stream from the keyboard
     *
     * @return String with the file name as its value
     * @req in is open
     * @ensures fileLocation = the relative address path of index.txt
     */
    public static String getInputFile(SimpleReader in) {
        System.out.println("What is the file path to your input file?");
        String fileLocation = in.nextLine();
        return fileLocation;
    }

    /**
     * Prompts the user for the relative path to the file location and then
     * returns that address in a string.
     *
     * @param in
     *            SimpleReader data stream from the keyboard
     *
     * @return String with the file name as its value
     * @req in is open
     * @ensures fileLocation = the relative address path of the output folder
     */
    public static String getOutputFolder(SimpleReader in) {
        System.out.println("What is the file path to your output folder?");
        String fileLocation = in.nextLine();
        return fileLocation;
    }

    /**
     * Takes an input file and a map named dictionary and reads each line of the
     * input file (while not at eos). On each loop, reads the first line as
     * String term, then the next lines until the next empty line as String
     * definition.
     *
     * @param in
     *            SimpleReader data stream from the input file
     *
     * @param dictionary
     *            the map variable that will hold all our dictionary data
     * @requires dictionary is empty, in is open
     * @replaces dictionary
     * @ensures for each definition in index.txt, dictionary = <term,
     *          definition> where definition is a queue with every word in the
     *          definition as an ordered entry in the queue
     */
    public static void processInputFile(Map<String, Queue<String>> dictionary,
            SimpleReader in) {
        while (!in.atEOS()) {
            String term = in.nextLine();
            String definition = in.nextLine();
            boolean done = false;
            while (!done && !in.atEOS()) {
                String next = in.nextLine();
                definition += next;
                if (next.isEmpty()) {
                    done = true;
                }
            }
            Queue<String> defQueue = definitionToQueue(definition);
            dictionary.add(term, defQueue);
        }
    }

    /**
     * Splits definition along any empty space into a String[] and then enqueues
     * each element of the String[] to Queue output and returns it.
     *
     * @param definition
     *            String containing the definition for a term
     *
     * @return Queue<String> output
     * @ensures output = a queue with every word in definition as an element in
     *          order as read
     */
    public static Queue<String> definitionToQueue(String definition) {
        Queue<String> output = new Queue1L<>();

        if (definition.contains(" ")) {
            String[] temp = definition.split(" ");
            for (String element : temp) {
                output.enqueue(element);
            }
        } else {
            output.enqueue(definition);
        }

        return output;
    }

    /**
     * Takes a master Map and outputs a well formatted index.html file with the
     * header Index and an alphabetized list of hyperlinks to each term.
     *
     * @param dictionary
     *            Map<String, Queue<String>> dictionary master map
     * @param folderName
     *            folder path where index.html goes
     *
     * @requires dictionary is the master Map containing all glossary data,
     *           folderName is the name of the output folder, which exists
     * @ensures index.html is a well formatted html file with a header,
     *          alphabetized items, and a footer
     */
    public static void outputIndexFile(Map<String, Queue<String>> dictionary,
            String folderName) {
        SimpleWriter out = new SimpleWriter1L(folderName + "/index.html");
        outputIndexHeader(out);
        outputIndexItems(dictionary, out, folderName);
        outputIndexFooter(out);
        out.close();
    }

    /**
     * Takes an output stream and prints the header text for the index html.
     *
     * @param out
     *            The output stream to the file index.html
     * @req out is open
     * @ensures A well formatted html header for index.html written to out
     */
    public static void outputIndexHeader(SimpleWriter out) {
        String glossaryName = "Glossary";
        String indexName = "Index";
        out.println("<html>\n<head>\n<title>" + glossaryName + "</title>\n</head>");
        out.println("<body>\n<h2>" + glossaryName + "</h2>\n<hr>\n<main>\n<h3>"
                + indexName + "</h3>\n<ul>");
    }

    /**
     * Takes our master Map, an output stream to index.html, and a string with
     * the output folder path, and prints the text for each term in index.html
     * in alphabetical order.
     *
     * @param dictionary
     *            Map<String, Queue<String>> dictionary master map
     * @param out
     *            SimpleWriter object that writes to index.html
     * @param folderName
     *            The relative filepath to the output folder where index.html
     *            resides
     * @req out is open, folderName is the path to the output folder which
     *      exists
     * @ensures A well formatted html header for index.html written to out
     */
    public static void outputIndexItems(Map<String, Queue<String>> dictionary,
            SimpleWriter out, String folderName) {
        Map<String, Queue<String>> temp = dictionary.newInstance();
        Queue<String> terms = new Queue1L<>();
        temp.transferFrom(dictionary);
        while (temp.size() > 0) {
            Map.Pair<String, Queue<String>> p = temp.removeAny();
            String tempString = p.key();
            terms.enqueue(tempString);
            dictionary.add(p.key(), p.value());
        }
        Comparator<String> order = new AlphabetComparator();
        terms.sort(order);
        while (terms.length() > 0) {
            out.println("<li>\n<a href =\"");
            String term = terms.dequeue();
            out.print(term + ".html\">" + term + "</a>\n</li>");
        }
    }

    /**
     * Takes an output stream to index.html and prints footer text to it.
     *
     * @param out
     *            The output stream to the file index.html
     *
     * @req out is open
     * @ensures A well formatted html footer for index.html written to out
     */
    public static void outputIndexFooter(SimpleWriter out) {
        out.println("</ul>\n</main>\n</body>\n</html>");
    }

    /**
     * Takes a master Map with all of our glossary data and outputs a String
     * array with every term of the glossary as an element.
     *
     * @param dictionary
     *            The output stream to the file index.html
     *
     * @ensures output = a String[] with every term in glossary as an element
     * @return output Returns a String[] with every term as an element
     */
    public static String[] getTermList(Map<String, Queue<String>> dictionary) {
        String str = "";
        Map<String, Queue<String>> temp = dictionary.newInstance();
        temp.transferFrom(dictionary);
        while (temp.size() > 0) {
            Map.Pair<String, Queue<String>> p = temp.removeAny();
            //Uses ';' to split our term list into an array.
            str += ";";
            str += p.key();
            dictionary.add(p.key(), p.value());
        }
        String[] output = str.split(";");
        return output;

    }

    /**
     * Takes a master Map and outputs a well formatted html file with the header
     * as the term in red font italics and the body as the definition, and with
     * a hyperlink to any other glossary term that appears in the definition.
     * The footer includes a link to return to the index.
     *
     * @param dictionary
     *            Map<String, Queue<String>> dictionary master map
     * @param outFolder
     *            folder path where term.html goes for each term
     *
     * @requires outFolder is the path to the output folder, which exists
     * @ensures term.txt is a well formatted html file with the term as header
     *          in red font italics, the definition as the body with a hyperlink
     *          to any other term, and a Return to Index link
     */
    public static void outputTermFiles(Map<String, Queue<String>> dictionary,
            String outFolder) {
        String[] termList = getTermList(dictionary);
        Map<String, Queue<String>> temp = dictionary.newInstance();
        temp.transferFrom(dictionary);
        while (temp.size() > 0) {
            Map.Pair<String, Queue<String>> p = temp.removeAny();
            SimpleWriter outFile = new SimpleWriter1L(
                    outFolder + "/" + p.key() + ".html");
            outputTermHeader(p.key(), outFile);
            outputTermDefinition(p, termList, outFile);
            outputTermFooter(outFile);
            dictionary.add(p.key(), p.value());
            outFile.close();
        }

    }

    /**
     * Takes a string with the term name and an output stream and prints the
     * header text for term.html.
     *
     * @param term
     *            String variable with the term we are making a .html page for
     * @param out
     *            The output stream to the file term.html
     * @req out is open
     * @ensures A well formatted html header for term.html written to out
     */
    public static void outputTermHeader(String term, SimpleWriter out) {
        String color = "red";
        out.println(
                "<html>\n<head>\n<body>\n<h2>\n<b>\n<i>\n<font color=\"" + color + "\">");
        out.print(term + "</font>\n</i>\n</b>\n</h2>\n<blockquote>");
    }

    /**
     * Takes a master Map and outputs a well formatted html file with the header
     * as the term in red font italics and the body as the definition, and with
     * a hyperlink to any other glossary term that appears in the definition.
     * The footer includes a link to return to the index.
     *
     * @param p
     *            A random Map.Pair<String, Queue<String>> entry from the master
     *            Map which contains the term as its key and the definition as
     *            its value
     * @param termList
     *            The String[] variable that holds every term in the glossary as
     *            its elements
     * @param out
     *            folder path where term.html goes for each term
     *
     * @req out is open
     * @ensures term.txt is a well formatted html file with the body as the
     *          term's definition with any word that is also a term being a
     *          hyperlink to that term.
     */
    public static void outputTermDefinition(Map.Pair<String, Queue<String>> p,
            String[] termList, SimpleWriter out) {
        Queue<String> temp = new Queue1L<>();
        temp.transferFrom(p.value());
        while (temp.length() > 0) {
            boolean hasSeparator = false;
            boolean inTermSet = false;
            String word = temp.dequeue();
            for (int i = 0; i < termList.length && !inTermSet; i++) {
                if (word.equals(termList[i])) {
                    inTermSet = true;
                }
            }
            //Line to check whether there's a non-space separator that has to be
            //added back. Currently only commas matter, but if in the future more
            //separators need to be added, all we need to do is make a set of the
            //separators we want to exist in definition check for those instead
            //, store the specific separator in a variable, and then add it back
            //at the end
            if (word.contains(",")) {
                hasSeparator = true;
            }
            if (inTermSet && !p.key().equals(word)) {
                if (hasSeparator) {
                    word = word.substring(0, word.length());
                }
                out.print("<a href = \"" + word + ".html\">" + word);
                if (hasSeparator) {
                    //This is where we would add a different separator back if we
                    //were concerned with more than commas
                    out.print(",");
                    word += ",";
                }
                out.print("</a>");
            } else {
                out.print(word);
            }
            //If there's more words in definition, prints a space
            if (temp.length() > 0) {
                out.print(" ");
            }
            //Puts word back in the master queue
            p.value().enqueue(word);
        }
    }

    /**
     * Takes an output stream and prints the footer text for term.html which
     * includes a hyperlink to index.html.
     *
     * @param out
     *            The output stream to the file term.html
     * @req out is open
     * @ensures A well formatted html footer for term.html with a hyperlinked
     *          button back to index.html
     */
    public static void outputTermFooter(SimpleWriter out) {
        out.println("</blockquote>\n<hr>\n<main>");
        out.println("<p>Return to <a href = \"index.html\">index</a>.\n</p>\n</main>");
        out.println("</body>\n</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        //SimpleReader object that reads from the keyboard
        SimpleReader in = new SimpleReader1L();
        //Gets the relative file path for the input file name.
        String inputFile = getInputFile(in);
        //Gets the relative path for the output folder.
        String outputFolder = getOutputFolder(in);
        //Closes keyboard input stream
        in.close();
        //SimpleReader object that reads from the inputfile
        SimpleReader inFile = new SimpleReader1L(inputFile);
        //Initializes the master map of all terms and definitions
        Map<String, Queue<String>> dictionary = new Map1L<>();
        //Processes the input file and adds each appropriate term and definition pair
        //to dictionary, where each word in the definition is an element in the queue.
        //The definitions in queue retain commas if the word had one.
        processInputFile(dictionary, inFile);
        //Uses the dictionary map to output the index html file
        outputIndexFile(dictionary, outputFolder);
        //Uses the dictionary map to output an html file for each term
        outputTermFiles(dictionary, outputFolder);
        //Closes the input stream from the input file
        inFile.close();

    }

}
